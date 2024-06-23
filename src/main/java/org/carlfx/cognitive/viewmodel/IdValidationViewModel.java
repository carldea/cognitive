package org.carlfx.cognitive.viewmodel;

import javafx.beans.property.Property;
import org.carlfx.cognitive.validator.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A validation view model supporting both PropertyIdentifier objects and property names (as type string). An ID validation view model manages validation rules for associated view properties and global type validations.
 * Use cases are the following:
 * <pre>
 *     1. Fields are required (not null or not empty string).
 *     2. Field has required input format. e.g. US Phone number format.
 *     3. Range values must be in(within) or outside of range. e.g. Date range or 1-100.
 *     4. Course grain or transactional type errors such as a Duplicate or Unknown (Runtime Exceptions). e.g. Already saved.
 * </pre>
 */public class IdValidationViewModel extends IdSimpleViewModel implements Validatable {
    private ValidationManager validationManager = new ValidationManager();

    /**
     * Default constructor.
     */
    public IdValidationViewModel() {
        super();
    }
    /**
     * Adds a property based on a name.
     * @param name Name of property
     * @param property A JavaFX property
     * @return returns itself.
     */
    @Override
    public IdValidationViewModel addProperty(String name, Property property) {
        super.addProperty(name, property);
        return this;
    }

    @Override
    public IdValidationViewModel addProperty(PropertyIdentifier propertyIdentifier, Property property) {
        super.addProperty(propertyIdentifier, property);
        return this;
    }
    /**
     * Adds a property based on a name and sets a string as the value.
     * @param name Name of property
     * @param value A JavaFX property
     * @return returns itself.
     */
    @Override
    public IdValidationViewModel addProperty(String name, String value) {
        super.addProperty(name, value);
        return this;
    }

    @Override
    public IdValidationViewModel addProperty(PropertyIdentifier name, String value) {
        super.addProperty(name, value);
        return this;
    }

    /**
     * Adds a property based on a name and sets an int as the value.
     * @param name Name of property
     * @param value A JavaFX property
     * @return returns itself.
     */
    @Override
    public IdValidationViewModel addProperty(String name, int value) {
        super.addProperty(name, value);
        return this;
    }

    @Override
    public IdValidationViewModel addProperty(PropertyIdentifier name, int value) {
        super.addProperty(name, value);
        return this;
    }
    /**
     * Adds a property based on a name and sets a long as the value.
     * @param name Name of property
     * @param value A JavaFX property
     * @return returns itself.
     */
    @Override
    public IdValidationViewModel addProperty(String name, long value) {
        super.addProperty(name, value);
        return this;
    }

    @Override
    public IdValidationViewModel addProperty(PropertyIdentifier name, long value) {
        super.addProperty(name, value);
        return this;
    }
    /**
     * Adds a property based on a name and sets a float as the value.
     * @param name Name of property
     * @param value A JavaFX property value
     * @return returns itself.
     */
    @Override
    public IdValidationViewModel addProperty(String name, float value) {
        super.addProperty(name, value);
        return this;
    }
    @Override
    public IdValidationViewModel addProperty(PropertyIdentifier name, float value) {
        super.addProperty(name, value);
        return this;
    }

    /**
     * Adds a property based on a name and sets a double as the value.
     * @param name Name of property
     * @param value A JavaFX property value
     * @return returns itself.
     */
    @Override
    public IdValidationViewModel addProperty(String name, double value) {
        return this.addProperty(name, value);
    }
    public IdValidationViewModel addProperty(PropertyIdentifier name, double value) {
        super.addProperty(name, value);
        return this;
    }

    /**
     * Adds a property based on a name and sets a boolean as the value.
     * @param name Name of property
     * @param value A JavaFX property value
     * @return returns itself.
     */
    @Override
    public IdValidationViewModel addProperty(String name, boolean value) {
        super.addProperty(name, value);
        return this;
    }

    @Override
    public IdValidationViewModel addProperty(PropertyIdentifier name, boolean value) {
        super.addProperty(name, value);
        return this;
    }
    /**
     * Adds a property based on a name and sets a collection as the value.
     * @param name Name of property
     * @param value A JavaFX property value
     * @return returns itself.
     */
    @Override
    public IdValidationViewModel addProperty(String name, Collection value) {
        super.addProperty(name, value);
        return this;
    }

    @Override
    public IdValidationViewModel addProperty(PropertyIdentifier name, Collection value) {
        super.addProperty(name, value);
        return this;
    }
    /**
     * Adds a property based on a name and sets an object as the value.
     * @param name Name of property
     * @param value A JavaFX property value
     * @return returns itself.
     */
    @Override
    public IdValidationViewModel addProperty(String name, Object value) {
        super.addProperty(name, value);
        return this;
    }

    @Override
    public IdValidationViewModel addProperty(PropertyIdentifier propertyIdentifier, Object value) {
        super.addProperty(propertyIdentifier, value);
        return this;
    }

    /**
     * Sets a property based on a name and sets values as a collection.
     * @param name Name of property
     * @param values A collection
     * @param skip skip copying property values over to model values.
     * @return returns itself.
     */
    public IdValidationViewModel setPropertyValues(String name, Collection values, boolean skip){
        super.setPropertyValues(name, values, skip);
        return this;
    }

    /**
     * Sets a property based on a name and sets values as a collection.
     * @param name Name of property
     * @param value An Object
     * @param skip skip copying property values over to model values.
     * @return returns itself.
     */
    @Override
    public IdValidationViewModel setPropertyValue(String name, Object value, boolean skip) {
        super.setPropertyValue(name, value, skip);
        return this;
    }

    /**
     * Copies model values back into property values.
     * @return returns itself.
     */
    @Override
    public IdValidationViewModel reset() {
        return (IdValidationViewModel) super.reset();
    }

    /**
     * Sets the model value layer based on the property name.
     * @param name property name
     * @param value Raw value to be set as the committed data.
     * @return Returns a ValidationViewModel following the builder pattern.
     */
    @Override
    public IdValidationViewModel setValue(String name, Object value) {
        return (IdValidationViewModel) super.setValue(name, value);
    }

    @Override
    public IdValidationViewModel setValue(Object name, Object value) {
        return (IdValidationViewModel) super.setValue(name, value);
    }


    /**
     * Sets the property value layer based on the property name.
     * @param name property name
     * @param value Raw value to be set as the committed data.
     * @return Returns a ValidationViewModel following the builder pattern.
     */
    @Override
    public IdValidationViewModel setPropertyValue(String name, Object value) {
        return (IdValidationViewModel) super.setPropertyValue(name, value);
    }
    @Override
    public IdValidationViewModel setPropertyValue(Object name, Object value) {
        return (IdValidationViewModel) super.setPropertyValue(name, value);
    }
    /**
     * Sets the property value layer based on the property name.
     * @param name property name
     * @param values Raw values to be set as a collection.
     * @return Returns a ValidationViewModel following the builder pattern.
     */
    @Override
    public IdValidationViewModel setPropertyValues(String name, Collection values) {
        return (IdValidationViewModel)super.setPropertyValues(name, values);
    }
    @Override
    public ValidationManager getValidationManager() {
        return validationManager;
    }

    @Override
    public IdValidationViewModel addValidator(String name, String friendlyName, StringValidator validator) {
        return this.addValidator((Object) name, friendlyName, validator);
    }

    /**
     * Allows derived classes to create global type validators.
     * @param name Property name or property id used to add a validator.
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return IdValidationViewModel itself.
     *
     */
    public IdValidationViewModel addValidator(Object name, String friendlyName, StringValidator validator) {
        ifPresentOrElse(name,
                propertyIdentifier -> getValidationManager().createFieldValidator(propertyIdentifier.idToString(), friendlyName, validator),
                propName -> getValidationManager().createFieldValidator(propName, friendlyName, validator)
        );
        return this;
    }

    @Override
    public IdValidationViewModel addValidator(String name, String friendlyName, BooleanValidator validator) {
        return this.addValidator((Object) name, friendlyName, validator);
    }

    /**
     * Allows derived classes to create global type validators.
     * @param name Property name or property id used to add a validator.
     * @param friendlyName A string of the validation message
     * @param validator Validator based on the datatype Boolean
     * @return ViewModel itself.
     *
     */
    public IdValidationViewModel addValidator(Object name, String friendlyName, BooleanValidator validator) {
        ifPresentOrElse(name,
                propertyIdentifier -> getValidationManager().createFieldValidator(propertyIdentifier.idToString(), friendlyName, validator),
                propName -> getValidationManager().createFieldValidator(propName, friendlyName, validator)
        );
        return this;
    }

    @Override
    public IdValidationViewModel addValidator(String name, String friendlyName, IntegerValidator validator) {
        return this.addValidator((Object) name, friendlyName, validator);
    }

    /**
     * Allows derived classes to create global type validators.
     * @param name Property name or property id used to add a validator.
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype of Integer.
     * @return ViewModel itself.
     *
     */
    public IdValidationViewModel addValidator(Object name, String friendlyName, IntegerValidator validator) {
        ifPresentOrElse(name,
                propertyIdentifier -> getValidationManager().createFieldValidator(propertyIdentifier.idToString(), friendlyName, validator),
                propName -> getValidationManager().createFieldValidator(propName, friendlyName, validator)
        );
        return this;
    }

    @Override
    public IdValidationViewModel addValidator(String name, String friendlyName, LongValidator validator) {
        return this.addValidator((Object) name, friendlyName, validator);
    }

    /**
     * Allows derived classes to create global type validators.
     * @param name Property name or property id used to add a validator.
     * @param friendlyName A string of the validation message
     * @param validator Validator based on the datatype Long.
     * @return ViewModel itself.
     *
     */
    public IdValidationViewModel addValidator(Object name, String friendlyName, LongValidator validator) {
        ifPresentOrElse(name,
                propertyIdentifier -> getValidationManager().createFieldValidator(propertyIdentifier.idToString(), friendlyName, validator),
                propName -> getValidationManager().createFieldValidator(propName, friendlyName, validator)
        );
        return this;
    }

    @Override
    public IdValidationViewModel addValidator(String name, String friendlyName, FloatValidator validator) {
        return this.addValidator((Object) name, friendlyName, validator);
    }

    /**
     * Allows derived classes to create global type validators.
     * @param name Property name or property id used to add a validator.
     * @param friendlyName A string of the validation message
     * @param validator Validator based on the datatype Float.
     * @return ViewModel itself.
     *
     */
    public IdValidationViewModel addValidator(Object name, String friendlyName, FloatValidator validator) {
        ifPresentOrElse(name,
                propertyIdentifier -> getValidationManager().createFieldValidator(propertyIdentifier.idToString(), friendlyName, validator),
                propName -> getValidationManager().createFieldValidator(propName, friendlyName, validator)
        );
        return this;
    }

    @Override
    public IdValidationViewModel addValidator(String name, String friendlyName, DoubleValidator validator) {
        return this.addValidator((Object) name, friendlyName, validator);
    }

    /**
     * Allows derived classes to create global type validators.
     * @param name Property name or property id used to add a validator.
     * @param friendlyName A string of the validation message
     * @param validator Validator based on the datatype Double.
     * @return ViewModel itself.
     *
     */
    public IdValidationViewModel addValidator(Object name, String friendlyName, DoubleValidator validator) {
        ifPresentOrElse(name,
                propertyIdentifier -> getValidationManager().createFieldValidator(propertyIdentifier.idToString(), friendlyName, validator),
                propName -> getValidationManager().createFieldValidator(propName, friendlyName, validator)
        );
        return this;
    }

    @Override
    public IdValidationViewModel addValidator(String name, String friendlyName, ObjectValidator validator) {
        return this.addValidator((Object) name, friendlyName, validator);
    }

    /**
     * Allows derived classes to create global type validators.
     * @param name Property name or property id used to add a validator.
     * @param friendlyName A string of the validation message
     * @param validator Validator based on the datatype Object.
     * @return ViewModel itself.
     *
     */
    public IdValidationViewModel addValidator(Object name, String friendlyName, ObjectValidator validator) {
        ifPresentOrElse(name,
                propertyIdentifier -> getValidationManager().createFieldValidator(propertyIdentifier.idToString(), friendlyName, validator),
                propName -> getValidationManager().createFieldValidator(propName, friendlyName, validator)
        );
        return this;
    }

    @Override
    public IdValidationViewModel addValidator(String name, String friendlyName, ListValidator validator) {
        return this.addValidator((Object) name, friendlyName, validator);
    }

    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on the datatype List
     * @return ViewModel itself.
     *
     */
    public IdValidationViewModel addValidator(Object name, String friendlyName, ListValidator validator) {
        ifPresentOrElse(name,
                propertyIdentifier -> getValidationManager().createFieldValidator(propertyIdentifier.idToString(), friendlyName, validator),
                propName -> getValidationManager().createFieldValidator(propName, friendlyName, validator)
        );
        return this;
    }

    @Override
    public IdValidationViewModel addValidator(String name, String friendlyName, SetValidator validator) {
        return this.addValidator((Object) name, friendlyName, validator);
    }

    /**
     * Allows derived classes to create global type validators.
     * @param name Property name or property id used to add a validator.
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype of Set.
     * @return ViewModel itself.
     *
     */
    public IdValidationViewModel addValidator(Object name, String friendlyName, SetValidator validator) {
        ifPresentOrElse(name,
                propertyIdentifier -> getValidationManager().createFieldValidator(propertyIdentifier.idToString(), friendlyName, validator),
                propName -> getValidationManager().createFieldValidator(propName, friendlyName, validator)
        );
        return this;
    }

    @Override
    public IdValidationViewModel addValidator(String name, String friendlyName, CustomValidator validator) {
        return this.addValidator((Object) name, friendlyName, validator);
    }

    /**
     * Allows derived classes to create global type validators.
     * @param name Property name or property id used to add a validator.
     * @param friendlyName A string of the validation message
     * @param validator a custom validator.
     * @return ViewModel itself.
     *
     */
    public IdValidationViewModel addValidator(Object name, String friendlyName, CustomValidator validator) {
        ifPresentOrElse(name,
                propertyIdentifier -> getValidationManager().createFieldValidator(propertyIdentifier.idToString(), friendlyName, validator),
                propName -> getValidationManager().createFieldValidator(propName, friendlyName, validator)
        );
        return this;
    }

    @Override
    public IdValidationViewModel validate() {
        validationManager.validate(this);
        return this;
    }

    /**
     * Invalidate or clear error messages.
     * @return itself
     */
    public IdValidationViewModel invalidate() {
        validationManager.invalidate();
        return this;
    }
    /**
     * True will force a save and returns the view model itself following the builder pattern.
     * The validation does not occur, property values copied to model values. false will call regular save().
     * @param force True will copy property values over to model values regardless of validators.
     * @return Returns a ValidationViewModel (itself) following the builder pattern.
     *
     */
    public IdValidationViewModel save(boolean force) {
        if (force) {
            return (IdValidationViewModel) super.save();
        }
        return this.save();
    }

    /**
     * Will call validate() before save(). If there are errors values won't be copied (saved).
     * @return itself
     */
    @Override
    public IdValidationViewModel save() {
        // validate if any are errors, don't save.
        validate();
        if (getValidationManager().hasErrorMsgs()) {
            return this;
        }

        return (IdValidationViewModel) super.save();
    }

    @Override
    public String getFriendlyName(String propertName) {
        return this.getFriendlyName((Object) propertName);
    }

    /**
     * Returns the friendly name to be used instead of property name.
     * @param propertName The property name or property id is used to look up friendly name.
     * @return Returns the friendly name to be used instead of property name.
     */
    public String getFriendlyName(Object propertName) {
        Optional<PropertyIdentifier> propertyIdentifier = findPropertyIdentifierByKey(propertName);
        if (propertyIdentifier.isPresent()) {
            return Validatable.super.getFriendlyName(propertyIdentifier.get().idToString());
        }
        return Validatable.super.getFriendlyName(String.valueOf(propertName));
    }

    /**
     * Overridden toString() to output data for debugging.
     * @return String representing data.
     */
    @Override
    public String toString() {

        List<String> messages = new ArrayList<>();
        for (ValidationMessage vMsg : this.getValidationMessages()) {
            String msg = "{ msgType=%s, errorCode=%s, msg=\"%s\"}".formatted(vMsg.messageType(), vMsg.errorCode(), vMsg.interpolate(this));
            messages.add(msg);
        }
        return "IdValidationViewModel{" +
                super.toString() +
                ", " +
                "friendlyNameMap=" + getValidationManager().getFriendlyNameMap() +
                ", validationMessages=[" + messages.stream().collect(Collectors.joining(",")) + "]" +
                '}';
    }
}
