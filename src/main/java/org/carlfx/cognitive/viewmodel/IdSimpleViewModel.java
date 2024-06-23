package org.carlfx.cognitive.viewmodel;

import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A view model able to add properties using <code>PropertyIdentifier</code> objects instead of a property name of type <code>String</code> as a key to lookup property and model values.
 * This provides a way to reference (lookup) property and model values using the following:
 * <pre>
 *     - PropertyIdentifier instance
 *     - getPropertyId() unique id
 *     - getPropertyId() as a string
 *     - getPropertyName() as a string
 * </pre>
 *
 * PropertyIdentifier can store a user data type object for additional uses such as a field's metadata.
 * A use case could be a UI form's field label changes based on preferred language.
 */
public class IdSimpleViewModel extends SimpleViewModel {
    private Map<Object, PropertyIdentifier> propertyIdLookupMap = null;

    /**
     * Default constructor.
     */
    public IdSimpleViewModel() {
        super();
    }

    @Override
    public void reloadProperty(String name, Property property) {
        this.reloadProperty((Object) name, property);
    }

    /**
     * Copies value from valueMap to property. Updating view from model.
     * @param propId property name or property id
     * @param property The property to be reloaded from model value.
     */
    public void reloadProperty(Object propId, Property property) {
        ifPresentOrElse(propId,
                (propIdentifier) -> super.reloadProperty(propIdentifier.idToString(), property),
                (propName) -> super.reloadProperty(propName, property)
        );
    }

    @Override
    public IdSimpleViewModel reset() {
        super.reset();
        return this;
    }

    @Override
    public IdSimpleViewModel save() {
        super.save();
        return this;
    }

    @Override
    public IdSimpleViewModel setValue(String name, Object value) {
        this.setValue((Object) name, value);
        return this;
    }

    /**
     * Sets the model data (valueMap values).
     * @param propId property name or property id used to lookup model value to be set.
     * @param value Raw value to be set as the committed data.
     * @return returns itself of type SimpleViewModel.
     */
    public IdSimpleViewModel setValue(Object propId, Object value) {
        return ifPresentOrElse(propId,
                (propIdentifier) -> super.setValue(propIdentifier.idToString(), value),
                (propName) -> super.setValue(propName, value)
        );
    }

    @Override
    public IdSimpleViewModel setPropertyValue(String name, Object value, boolean skip) {
        this.setPropertyValue((Object) name, value, skip);
        return this;
    }

    /**
     * Sets the Property to contain the new value. It does not set the model value.
     * @param propId property name or property id is used to lookup and set property value.
     * @param value raw value
     * @param skip True to skip the process of copying the property value into the model value.
     * @return returns itself of type ViewModel following the builder pattern.
     */
    public IdSimpleViewModel setPropertyValue(Object propId, Object value, boolean skip) {
        return ifPresentOrElse(propId,
                (propIdentifier) -> super.setPropertyValue(propIdentifier.idToString(), value, skip),
                (propName) -> super.setPropertyValue(propName, value, skip)
        );
    }

    @Override
    public IdSimpleViewModel setPropertyValues(String name, Collection values, boolean skip) {
        this.setPropertyValues((Object) name, values, skip);
        return this;
    }

    /**
     * Sets the observable collection's with values.
     * @param propId The name of the property or property id used to lookup and set collection.
     * @param values The collection of values to be put into an observable collection.
     * @param skip True to skip the process of copying the property values into the model values. Saves on memory typically used to populate ComboBoxes and ListViews.
     * @return Returns the view model itself following the builder pattern.
     */
    public SimpleViewModel setPropertyValues(Object propId, Collection values, boolean skip) {
        return ifPresentOrElse(propId,
                (propIdentifier) -> super.setPropertyValues(propIdentifier.idToString(), values, skip),
                (propName) -> super.setPropertyValues(propName, values, skip)
        );
    }

    @Override
    public IdSimpleViewModel setPropertyValue(String name, Object value) {
        return this.setPropertyValue((Object) name, value, false);
    }

    /**
     * Sets the Property to contain the new value. It does not set the model value.
     * @param propId property name or property id.
     * @param value raw value
     * @return returns itself of type ViewModel.
     */
    public IdSimpleViewModel setPropertyValue(Object propId, Object value) {
        return ifPresentOrElse(propId,
                (propIdentifier) -> super.setPropertyValue(propIdentifier.idToString(), value),
                (propName) -> super.setPropertyValue(propName, value)
        );
    }

    @Override
    public IdSimpleViewModel setPropertyValues(String name, Collection values) {
        return this.setPropertyValues(name, values, false);
    }

    /**
     * Returns the model value based on a property identifier.
     * @param name propertyName
     * @return Returns the model value based on a property identifier.
     * @param <T> Model value data type
     */
    public <T> T getValue(String name) {
        return this.getValue((Object) name);
    }

    /**
     * Returns the model value based on a property identifier.
     * @param propId propertyName or property id.
     * @return Returns the model value based on a property identifier.
     * @param <T> Model value data type
     */
    public <T> T getValue(Object propId) {
        return returnIfPresentOrElse(propId,
                (propIdentifier) -> super.getValue(propIdentifier.idToString()),
                (propName) -> super.getValue(propName)
        );
    }

    private void addKeysToPropertyIdMap(PropertyIdentifier propertyIdentifier) {
        Map<?, PropertyIdentifier> map = Map.of(
                propertyIdentifier, propertyIdentifier,
                propertyIdentifier.idToString(), propertyIdentifier,
                propertyIdentifier.getPropertyId(), propertyIdentifier,
                propertyIdentifier.getPropertyName(), propertyIdentifier
        );
        getPropertyIdLookupMap().putAll(map);
    }

    /**
     * Generic way to check property identifiers, if it exists call consumer else call the standard property name.
     * @param propId A property identifier can be itself, string of property name, property id as string.
     * @param propIdPresent A consumer to be executed with a property identifier.
     * @param propName A consumer to be executed with a standard property name.
     * @return Returns itself (IdSimpleViewModel) following the builder pattern.
     */
    protected IdSimpleViewModel ifPresentOrElse(Object propId,
                                 Consumer<PropertyIdentifier> propIdPresent,
                                 Consumer<String> propName) {
        Optional<PropertyIdentifier> propertyIdentifier = findPropertyIdentifierByKey(propId);
        propertyIdentifier.ifPresentOrElse(
                propIdentifier -> propIdPresent.accept(propIdentifier), /* present */
                ()-> propName.accept(String.valueOf(propId))); /* else */
        return this;
    }

    /**
     * When a property id is not present perform consumer code block.
     * @param propId determines if a property id does not exist
     * @param consumer consumer code block with the property id as a string as the parameter.
     * @return Returns itself following the builder pattern.
     */
    protected IdSimpleViewModel ifNotPresentAddPropertyId(PropertyIdentifier propId,
                                           Consumer<String> consumer) {
        Optional<PropertyIdentifier> propertyIdentifier = findPropertyIdentifierByKey(propId);
        if (propertyIdentifier.isEmpty()) {
                addKeysToPropertyIdMap(propId);
                consumer.accept(propId.idToString());
        } else {
            consumer.accept(propertyIdentifier.get().idToString());
        }
        return this;
    }
    private <R> R returnIfPresentOrElse(Object propId,
                                        Function<PropertyIdentifier, R> propIdPresent,
                                        Function<String, R> propName) {
        Optional<PropertyIdentifier> propertyIdentifier = findPropertyIdentifierByKey(propId);
        if (propertyIdentifier.isPresent()) {
            return propIdPresent.apply(propertyIdentifier.get());
        }
        return propName.apply(String.valueOf(propId));
    }

    @Override
    public IdSimpleViewModel addProperty(String name, Property property) {
        return super.addProperty(name, property);
    }

    /**
     * Add a new property. Key off of a property identifier instead of a property name.
     * @param propId property identifier.
     * @param property The JavaFX Property instance referencing a property name.
     * @return Returns a SimpleViewModel itself following the builder pattern.
     */
    public IdSimpleViewModel addProperty(PropertyIdentifier propId, Property property) {
        return ifNotPresentAddPropertyId(propId, (propName) -> super.addProperty(propName, property));
    }
    @Override
    public IdSimpleViewModel addProperty(String name, String value) {
        return super.addProperty(name, value);
    }

    /**
     * Add a new property to the view model based on a <code>PropertyIdentifier</code> instance.
     * @param propId property identifier. This object contains a unique identifier and a user data type object to assist with addition field information.
     * @param value the value to be set initially for the property and model value.
     * @return Returns itself (IdSimpleViewModel) following the builder pattern.
     */
    public IdSimpleViewModel addProperty(PropertyIdentifier propId, String value) {
        return ifNotPresentAddPropertyId(propId, (propName) -> super.addProperty(propName, value));
    }

    @Override
    public IdSimpleViewModel addProperty(String name, int value) {
        return super.addProperty(name, value);
    }

    /**
     * Add a new property as an int value.
     * @param propId property identifier instance.
     * @param value The raw value type referencing a property name. This will create a JavaFX property to hold the raw value.
     * @return Returns a IdSimpleViewModel itself following the builder pattern.
     */
    public IdSimpleViewModel addProperty(PropertyIdentifier propId, int value) {
        return ifNotPresentAddPropertyId(propId, (propName) -> super.addProperty(propName, value));
    }

    @Override
    public IdSimpleViewModel addProperty(String name, long value) {
        return super.addProperty(name, value);
    }

    /**
     * Add a new property as an long value.
     * @param propId property identifier instance.
     * @param value The raw value type referencing a property name. This will create a JavaFX property to hold the raw value.
     * @return Returns a IdSimpleViewModel itself following the builder pattern.
     */
    public IdSimpleViewModel addProperty(PropertyIdentifier propId, long value) {
        return ifNotPresentAddPropertyId(propId, (propName) -> super.addProperty(propName, value));
    }

    @Override
    public IdSimpleViewModel addProperty(String name, float value) {
        return super.addProperty(name, value);
    }

    /**
     * Add a new property as a float value.
     * @param propId PropertyIdentifier.
     * @param value The raw value type referencing a property name. This will create a JavaFX property to hold the raw value.
     * @return Returns a SimpleViewModel itself following the builder pattern.
     */
    public IdSimpleViewModel addProperty(PropertyIdentifier propId, float value) {
        return ifNotPresentAddPropertyId(propId, (propName) -> super.addProperty(propName, value));
    }

    @Override
    public IdSimpleViewModel addProperty(String name, double value) {
        return super.addProperty(name, value);
    }

    /**
     * Add a new property as a double value.
     * @param propId PropertyIdentifier. Can be the instance or called by the getPropertyId() method's type.
     * @param value The raw value type referencing a property name. This will create a JavaFX property to hold the raw value.
     * @return Returns a SimpleViewModel itself following the builder pattern.
     */
    public IdSimpleViewModel addProperty(PropertyIdentifier propId, double value) {
        return ifNotPresentAddPropertyId(propId, (propName) -> super.addProperty(propName, value));
    }

    @Override
    public IdSimpleViewModel addProperty(String name, boolean value) {
        return super.addProperty(name, value);
    }

    /**
     * Add a new property as an Object value with a way to skip any copying to model values layer.
     * @param propId PropertyIdentifier.
     * @param value The raw value type referencing a property name. This will create a JavaFX property to hold the raw value.
     * @return Returns a SimpleViewModel itself following the builder pattern.
     */
    public IdSimpleViewModel addProperty(PropertyIdentifier propId, boolean value) {
        return ifNotPresentAddPropertyId(propId, (propName) -> super.addProperty(propName, value));
    }

    @Override
    public IdSimpleViewModel addProperty(String name, Collection value) {
        return super.addProperty(name, value);
    }

    /**
     * Add a new property as a Collection value.
     * @param propId PropertyIdentifier.
     * @param value The raw value type referencing a property name. This will create a JavaFX property to hold the raw value.
     * @return Returns a SimpleViewModel itself following the builder pattern.
     */    public IdSimpleViewModel addProperty(PropertyIdentifier propId, Collection value) {
        return ifNotPresentAddPropertyId(propId, (propName) -> super.addProperty(propName, value));
    }

    @Override
    public IdSimpleViewModel addProperty(String name, Collection value, boolean skip) {
        return super.addProperty(name, value, skip);
    }

    /**
     * Add a new property as a Collection value with a way to skip any copying to model values layer.
     * @param propId PropertyIdentifier.
     * @param value The raw value type referencing a property name. This will create a JavaFX property to hold the raw value.
     * @param skip True to avoid copying property value to model value layer.
     * @return Returns a SimpleViewModel itself following the builder pattern.
     */
    public IdSimpleViewModel addProperty(PropertyIdentifier propId, Collection value, boolean skip) {
        return ifNotPresentAddPropertyId(propId, (propName) -> super.addProperty(propName, value, skip));
    }
    @Override
    public IdSimpleViewModel addProperty(String name, Object value) {
        return super.addProperty(name, value);
    }

    /**
     * Add a new property as an Object value with a way to skip any copying to model values layer.
     * @param propId PropertyIdentifier.
     * @param value The raw value type referencing a property name. This will create a JavaFX property to hold the raw value.
     * @return Returns a SimpleViewModel itself following the builder pattern.
     */
    public IdSimpleViewModel addProperty(PropertyIdentifier propId, Object value) {
        return ifNotPresentAddPropertyId(propId, (propName) -> super.addProperty(propName, value));
    }

    @Override
    public <T extends Property> T getProperty(String name) {
        return this.getProperty((Object) name);
    }

    /**
     * Returns a Property instance to be used for the form UI.
     * @param propertyIdentifier The property name or unique property id. e.g. firstName, lastName
     * @return Returns a Property instance to be used for the form UI.
     * @param <T> T is a JavaFX Property object such as IntegerProperty, StringProperty, etc.
     */
    public <T extends Property> T getProperty(Object propertyIdentifier) {
        return returnIfPresentOrElse(propertyIdentifier,
                (pId) -> super.getProperty(pId.idToString()),
                (propName) -> super.getProperty(propName)
        );
    }

    @Override
    public Property removeProperty(String name) {
        return this.removeProperty((Object) name);
    }

    /**
     * Removes property and model values.
     * @param propId property name, or property id. Can also be the PropertyIdentifier instance.
     * @return Returns the Property that was removed based on property name lookup.
     */
    public Property removeProperty(Object propId) {
        return returnIfPresentOrElse(
                propId,
                propertyIdentifier -> {
                    getPropertyIdLookupMap().entrySet().removeIf(entry -> entry.getValue().getPropertyId().equals(propertyIdentifier.getPropertyId()));
                    return super.removeProperty(propertyIdentifier.idToString());
                },
                propName -> super.removeProperty(propName)
        );
    }

    @Override
    public Observable getObservableCollection(String name) {
        return this.getObservableCollection((Object) name);
    }

    /**
     * Returns an observable collection from the property layer. These collections are used in the UI. e.g. ListView containing user's selected items.
     * @param propId property name or property id.
     * @return Returns an observable collection from the property layer. These collections are used in the UI. e.g. ListView containing user's selected items.
     */
    public Observable getObservableCollection(Object propId) {
        return returnIfPresentOrElse(
                propId,
                propertyIdentifier -> super.getObservableCollection(propertyIdentifier.idToString()),
                propName -> super.getObservableCollection(propName)
        );
    }

    @Override
    public <T> ObservableList<T> getObservableList(String name) {
        return this.getObservableList((Object) name);
    }

    /**
     * Returns an observable list from the property layer. These collections are used in the UI. e.g. ListView containing user's selected items.
     * @param propId property name, property identifier instance, property id.
     * @return Returns an observable list from the property layer. These lists are used in the UI. e.g. ListView containing user's selected items.
     * @param <T> The type of each item in the list.
     */
    public <T> ObservableList<T> getObservableList(Object propId) {
        return returnIfPresentOrElse(
                propId,
                propertyIdentifier -> super.getObservableList(propertyIdentifier.idToString()),
                propName -> super.getObservableList(propName)
        );
    }

    @Override
    public <T> ObservableSet<T> getObservableSet(String name) {
        return this.getObservableSet((Object)name);
    }

    /**
     * Returns an observable set from the property layer. These collections are used in the UI. e.g. ListView containing user's selected items.
     * @param propId property name, property identifier instance, property id.
     * @return Returns an observable set from the property layer. These lists are used in the UI. e.g. ListView containing user's selected items.
     * @param <T> The type of each item in the set.
     */
    public <T> ObservableSet<T> getObservableSet(Object propId) {
        return returnIfPresentOrElse(
                propId,
                propertyIdentifier -> super.getObservableSet(propertyIdentifier.idToString()),
                propName -> super.getObservableSet(propName)
        );
    }

    /**
     * Lookup PropertyIdentifier based on property name, property id.
     * @param key property name, property id, or property identifier object to lookup.
     * @return An optional containing a property identifier instance.
     * @param <T> PropertyIdentifier instance
     */
    public <T extends PropertyIdentifier> Optional<T> findPropertyIdentifierByKey(Object key) {
        Map<Object, PropertyIdentifier> map = getPropertyIdLookupMap();
        if (key instanceof PropertyIdentifier propertyIdentifier) {
            return Optional.ofNullable((T)map.get(propertyIdentifier.getPropertyId().toString()));
        } else {
            return Optional.ofNullable((T)map.get(key));
        }
    }

    @Override
    public Observable removeObservableCollection(String name) {
        return this.removeObservableCollection((Object) name);
    }

    /**
     * Returns an observable collection from the property layer. These collections are used in the UI. e.g. ListView containing user's selected items.
     * @param propId property name, property identifier instance, property id.
     * @return Returns an observable collection from the property layer. These lists are used in the UI. e.g. ListView containing user's selected items.
     */
    public Observable removeObservableCollection(Object propId) {
        return returnIfPresentOrElse(
                propId,
                propertyIdentifier -> super.removeObservableCollection(propertyIdentifier.idToString()),
                propName -> super.removeObservableCollection(propName)
        );
    }
    @Override
    public <T> List<T> getList(String name) {
        return this.getList(name);
    }

    /**
     * Returns a list of objects based on a property name. This delegate will check for any propId as a key to lookup list.
     * @param propId Any value that represents a unique property id. PropertyIdentifier, propertyId, and string of propertyId.
     * @return Returns a list of objects based on a property name.
     * @param <T> Object types of list.
     */
    public <T> List<T> getList(Object propId) {
        return returnIfPresentOrElse(
                propId,
                propertyIdentifier -> super.getList(propertyIdentifier.idToString()),
                propName -> super.getList(propName)
        );
    }

    @Override
    public <T> Set<T> getSet(String name) {
        return this.getSet((Object) name);
    }

    /**
     * Returns a set of objects based on a property name. This delegate will check for any propId as a key to lookup list.
     * @param propId Any value that represents a unique property id. PropertyIdentifier, propertyId, and string of propertyId.
     * @return Returns a set of objects based on a property name or property id.
     * @param <T> Object type of items in set.
     */
    public <T> Set<T> getSet(Object propId) {
        return returnIfPresentOrElse(
                propId,
                propertyIdentifier -> super.getSet(propertyIdentifier.idToString()),
                propName -> super.getSet(propName)
        );
    }

    @Override
    public <T> Collection<T> getCollection(String name) {
        return this.getCollection((Object) name);
    }

    /**
     * Returns the raw model values as a Collection.
     * @param propId Property name or property id.
     * @return A list of objects from the model values.
     * @param <T> T is the object type in the collection.
     */
    public <T> Collection<T> getCollection(Object propId) {
        return returnIfPresentOrElse(
                propId,
                propertyIdentifier -> super.getCollection(propertyIdentifier.idToString()),
                propName -> super.getCollection(propName)
        );
    }

    @Override
    public String debugPropertyMessage(String name) {
        return this.debugPropertyMessage((Object) name);
    }

    /**
     * Return internal map of keys to PropertyIdentifiers.
     * @return Return internal map of keys to PropertyIdentifiers.
     */
    protected Map<Object, PropertyIdentifier> getPropertyIdLookupMap() {
        if (propertyIdLookupMap == null) {
            propertyIdLookupMap = new HashMap<>();
        }
        return propertyIdLookupMap;
    }

    /**
     * Returns a debugger string representing the property and model values based on property identifier.
     * @param propertyIdentifier Any property identifier or property name to represent the field.
     * @return Returns a debugger string representing the property and model values based on property identifier.
     */
    public String debugPropertyMessage(Object propertyIdentifier) {
        return returnIfPresentOrElse(propertyIdentifier,
                propId   -> "viewProperty:%s = %s | modelValue:%s = %s".formatted(propertyIdentifier, getProperty(propertyIdentifier).getValue(), propertyIdentifier.toString(), getValue(propertyIdentifier)),
                propName -> {
                    if (getSingleValueMap().containsKey(propName)){
                        return "viewProperty:%s = %s | modelValue:%s = %s".formatted(propName, this.getProperty(propName).getValue(), propName, this.getValue(propName));
                    } else if (getMultiValueMap().containsKey(propName)) {
                        return "viewProperty:%s = %s | modelValue:%s = %s".formatted(propName, this.getObservableCollection(propName), propName, this.getCollection(propName));
                    } else {
                        return "Unknown viewProperty:%s".formatted(propName);
                    }
                }
        );
    }

    @Override
    public String toString() {
        return "IdSimpleViewModel {\n" +
                getSingleValueMap()
                        .keySet()
                        .stream()
                        .map(name -> " " + debugPropertyMessage(name) + "\n")
                        .collect(Collectors.joining()) +
                getMultiValueMap()
                        .keySet()
                        .stream()
                        .map(name -> " " + debugPropertyMessage(name) + "\n")
                        .collect(Collectors.joining()) +
                "propertyIdLookupMap.keySet =" + propertyIdLookupMap.keySet() +
                '}';
    }
}
