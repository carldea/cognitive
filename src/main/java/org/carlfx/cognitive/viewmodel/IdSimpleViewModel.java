package org.carlfx.cognitive.viewmodel;

import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class IdSimpleViewModel extends SimpleViewModel {
    private Map<Object, PropertyIdentifier> propertyIdLookupMap = null;

    public IdSimpleViewModel() {
        super();
    }

    @Override
    public void reloadProperty(String name, Property property) {
        this.reloadProperty((Object) name, property);
    }

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
    public IdSimpleViewModel addProperty(PropertyIdentifier propId, Property property) {
        return ifNotPresentAddPropertyId(propId, (propName) -> super.addProperty(propName, property));
    }
    @Override
    public IdSimpleViewModel addProperty(String name, String value) {
        return super.addProperty(name, value);
    }
    public IdSimpleViewModel addProperty(PropertyIdentifier propId, String value) {
        return ifNotPresentAddPropertyId(propId, (propName) -> super.addProperty(propName, value));
    }

    @Override
    public IdSimpleViewModel addProperty(String name, int value) {
        return super.addProperty(name, value);
    }
    public IdSimpleViewModel addProperty(PropertyIdentifier propId, int value) {
        return ifNotPresentAddPropertyId(propId, (propName) -> super.addProperty(propName, value));
    }

    @Override
    public IdSimpleViewModel addProperty(String name, long value) {
        return super.addProperty(name, value);
    }
    public IdSimpleViewModel addProperty(PropertyIdentifier propId, long value) {
        return ifNotPresentAddPropertyId(propId, (propName) -> super.addProperty(propName, value));
    }

    @Override
    public IdSimpleViewModel addProperty(String name, float value) {
        return super.addProperty(name, value);
    }
    public IdSimpleViewModel addProperty(PropertyIdentifier propId, float value) {
        return ifNotPresentAddPropertyId(propId, (propName) -> super.addProperty(propName, value));
    }

    @Override
    public IdSimpleViewModel addProperty(String name, double value) {
        return super.addProperty(name, value);
    }
    public IdSimpleViewModel addProperty(PropertyIdentifier propId, double value) {
        return ifNotPresentAddPropertyId(propId, (propName) -> super.addProperty(propName, value));
    }

    @Override
    public IdSimpleViewModel addProperty(String name, boolean value) {
        return super.addProperty(name, value);
    }
    public IdSimpleViewModel addProperty(PropertyIdentifier propId, boolean value) {
        return ifNotPresentAddPropertyId(propId, (propName) -> super.addProperty(propName, value));
    }

    @Override
    public IdSimpleViewModel addProperty(String name, Collection value) {
        return super.addProperty(name, value);
    }
    public IdSimpleViewModel addProperty(PropertyIdentifier propId, Collection value) {
        return ifNotPresentAddPropertyId(propId, (propName) -> super.addProperty(propName, value));
    }

    @Override
    public IdSimpleViewModel addProperty(String name, Collection value, boolean skip) {
        return super.addProperty(name, value, skip);
    }
    public IdSimpleViewModel addProperty(PropertyIdentifier propId, Collection value, boolean skip) {
        return ifNotPresentAddPropertyId(propId, (propName) -> super.addProperty(propName, value, skip));
    }
    @Override
    public IdSimpleViewModel addProperty(String name, Object value) {
        return super.addProperty(name, value);
    }
    public IdSimpleViewModel addProperty(PropertyIdentifier propId, Object value) {
        return ifNotPresentAddPropertyId(propId, (propName) -> super.addProperty(propName, value));
    }

    @Override
    public <T extends Property> T getProperty(String name) {
        return this.getProperty((Object) name);
    }

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

    public <T> ObservableSet<T> getObservableSet(Object propId) {
        return returnIfPresentOrElse(
                propId,
                propertyIdentifier -> super.getObservableSet(propertyIdentifier.idToString()),
                propName -> super.getObservableSet(propName)
        );
    }

    public <T> Optional<T> findPropertyIdentifierByKey(Object key) {
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
    public Observable removeObservableCollection(Object propId) {
        return returnIfPresentOrElse(
                propId,
                propertyIdentifier -> super.removeObservableCollection(propertyIdentifier.idToString()),
                propName -> super.removeObservableCollection(propName)
        );
    }

    /**
     * Returns a list of objects based on a property name. Overridden to delegate call to this.getList(Object propertyId).
     * @param name Property name
     * @return
     * @param <T>
     */
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

    protected Map<Object, PropertyIdentifier> getPropertyIdLookupMap() {
        if (propertyIdLookupMap == null) {
            propertyIdLookupMap = new HashMap<>();
        }
        return propertyIdLookupMap;
    }

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
