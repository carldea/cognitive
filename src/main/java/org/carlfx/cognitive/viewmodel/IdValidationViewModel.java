/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright © 2023-2024 Carl Dea.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.carlfx.cognitive.viewmodel;

import javafx.beans.property.Property;
import org.carlfx.cognitive.validator.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
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
        super.reset();
        return this;
    }

    /**
     * Sets the model value layer based on the property name.
     * @param name property name
     * @param value Raw value to be set as the committed data.
     * @return Returns a ValidationViewModel following the builder pattern.
     */
    @Override
    public IdValidationViewModel setValue(String name, Object value) {
        super.setValue(name, value);
        return this;
    }

    @Override
    public IdValidationViewModel setValue(Object name, Object value) {
        super.setValue(name, value);
        return this;
    }


    /**
     * Sets the property value layer based on the property name.
     * @param name property name
     * @param value Raw value to be set as the committed data.
     * @return Returns a ValidationViewModel following the builder pattern.
     */
    @Override
    public IdValidationViewModel setPropertyValue(String name, Object value) {
        super.setPropertyValue(name, value);
        return this;
    }
    @Override
    public IdValidationViewModel setPropertyValue(Object name, Object value) {
        super.setPropertyValue(name, value);
        return this;
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

    /**
     * Allows derived classes to create global type validators.
     * @param name Property name or property id used to add a validator.
     * @param friendlyName A string of the validation message
     * @param validator Validator based on an object property.
     * @return ViewModel itself.
     *
     */
    public IdValidationViewModel addValidator(Object name, String friendlyName, ObjectConsumerValidator validator) {
        ifPresentOrElse(name,
                propertyIdentifier -> Validatable.super.addValidator(propertyIdentifier.idToString(), friendlyName, validator),
                propName -> Validatable.super.addValidator(propName, friendlyName, validator)
        );
        return this;
    }

    /**
     * Allows derived classes to create global type validators.
     * @param name Property name or property id used to add a validator.
     * @param friendlyName A string of the validation message
     * @param validator Validator based on the datatype Integer
     * @return ViewModel itself.
     *
     */
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

    /**
     * Create a validator for a long value based on a named property. When creating error messages the friendly name
     * is used instead of the property name.
     * @param name Property name or property id used to add a validator.
     * @param friendlyName A string of the validation message
     * @param validator Validator based on the datatype Long.
     * @return ViewModel itself.
     *
     */
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

    /**
     * Create a validator for a float value based on a named property. When creating error messages the friendly name
     * is used instead of the property name.
     * @param name Property name or property id used to add a validator.
     * @param friendlyName A string of the validation message
     * @param validator Validator based on the datatype float.
     * @return ViewModel itself.
     *
     */
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

    /**
     * Create a validator for a double value based on a named property. When creating error messages the friendly name
     * is used instead of the property name.
     * @param name Property name or property id used to add a validator.
     * @param friendlyName A string of the validation message
     * @param validator Validator based on the datatype float.
     * @return ViewModel itself.
     *
     */
    @Override
    public IdValidationViewModel addValidator(String name, String friendlyName, DoubleValidator validator) {
        return this.addValidator((Object) name, friendlyName, validator);
    }

    /**
     * Create a validator for a double value based on a named property. When creating error messages the friendly name
     * is used instead of the property name.
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

    /**
     * Create a validator for an object value based on a named property. When creating error messages the friendly name
     * is used instead of the property name.
     * @param name Property name or property id used to add a validator.
     * @param friendlyName A string of the validation message
     * @param validator Validator based on the datatype object (ObjectProperty).
     * @return ViewModel itself.
     *
     */
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

    /**
     * Create a validator for a list value based on a named property. When creating error messages the friendly name
     * is used instead of the property name.
     *
     * @param name         Property name or property id used to add a validator.
     * @param friendlyName A string of the validation message
     * @param validator    Validator based on the datatype list (ObservableList).
     * @return ViewModel itself.
     */
    @Override
    public IdValidationViewModel addValidator(String name, String friendlyName, ListValidator validator) {
        return this.addValidator((Object) name, friendlyName, validator);
    }

    /**
     * Create a validator for a list value based on a named property. When creating error messages the friendly name
     * is used instead of the property name.
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

    /**
     * Create a validator for a set value based on a named property. When creating error messages the friendly name
     * is used instead of the property name.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on the datatype List
     * @return ViewModel itself.
     *
     */
    @Override
    public IdValidationViewModel addValidator(String name, String friendlyName, SetValidator validator) {
        return this.addValidator((Object) name, friendlyName, validator);
    }

    /**
     * Create a validator for a set value based on a named property. When creating error messages the friendly name
     * is used instead of the property name.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on the datatype Set
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

    /**
     * Create a custom validator. When creating error messages the friendly name
     * is used instead of the property name.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on this view model.
     * @return ViewModel itself.
     *
     */
    @Override
    public IdValidationViewModel addValidator(String name, String friendlyName, CustomValidator validator) {
        return this.addValidator((Object) name, friendlyName, validator);
    }

    /**
     * Create a custom validator. When creating error messages the friendly name
     * is used instead of the property name.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on this view model.
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

    /**
     * Create a validator for a boolean value based on a named property. When creating error messages the friendly name
     * is used instead of the property name.
     * @param name Property name or property id used to add a validator.
     * @param friendlyName A string of the validation message
     * @param validator Validator based on the datatype boolean (BooleanProperty).
     * @return ViewModel itself.
     *
     */
    @Override
    public IdValidationViewModel addValidator(String name, String friendlyName, BooleanConsumerValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    /**
     * Create a validator for a double value based on a named property. When creating error messages the friendly name
     * is used instead of the property name.
     * @param name Property name or property id used to add a validator.
     * @param friendlyName A string of the validation message
     * @param validator Validator based on the datatype boolean (DoubleProperty).
     * @return ViewModel itself.
     *
     */
    @Override
    public IdValidationViewModel addValidator(String name, String friendlyName, DoubleConsumerValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    /**
     * Create a validator for a float value based on a named property. When creating error messages the friendly name
     * is used instead of the property name.
     * @param name Property name or property id used to add a validator.
     * @param friendlyName A string of the validation message
     * @param validator Validator based on the datatype float (FloatProperty).
     * @return ViewModel itself.
     *
     */
    @Override
    public IdValidationViewModel addValidator(String name, String friendlyName, FloatConsumerValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    /**
     * Create a validator for an integer value based on a named property. When creating error messages the friendly name
     * is used instead of the property name.
     * @param name Property name or property id used to add a validator.
     * @param friendlyName A string of the validation message
     * @param validator Validator based on the datatype integer (IntegerProperty).
     * @return ViewModel itself.
     *
     */
    @Override
    public IdValidationViewModel addValidator(String name, String friendlyName, IntegerConsumerValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    /**
     * Create multiple validation messages using Consumer validators.
     * Validation for read-only JavaFX LongProperty. When creating error messages the friendly name
     * is used instead of the property name.
     * @param name Property name or property id used to add a validator.
     * @param friendlyName A string of the validation message
     * @param validator Validator based on the datatype list (Long).
     * @return ViewModel itself.
     *
     */
    @Override
    public IdValidationViewModel addValidator(String name, String friendlyName, LongConsumerValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    @Override
    public IdValidationViewModel addProperty(Enum name, Property property) {
        super.addProperty(name, property);
        return this;
    }

    @Override
    public IdValidationViewModel setValue(Enum name, Object value) {
        super.setValue(name, value);
        return this;
    }

    @Override
    public IdValidationViewModel setPropertyValue(Enum name, Object value, boolean skip) {
        super.setPropertyValue(name, value, skip);
        return this;
    }

    @Override
    public IdValidationViewModel setPropertyValues(Enum name, Collection values, boolean skip) {
        super.setPropertyValues(name, values, skip);
        return this;
    }

    @Override
    public IdValidationViewModel setPropertyValue(Enum name, Object value) {
        super.setPropertyValue(name, value);
        return this;
    }

    @Override
    public IdValidationViewModel setPropertyValues(Enum name, Collection values) {
        super.setPropertyValues(name, values);
        return this;
    }

    @Override
    public IdValidationViewModel addProperty(Enum name, String value) {
        super.addProperty(name, value);
        return this;
    }

    @Override
    public IdValidationViewModel addProperty(Enum name, int value) {
        super.addProperty(name, value);
        return this;
    }

    @Override
    public IdValidationViewModel addProperty(Enum name, long value) {
        super.addProperty(name, value);
        return this;
    }

    @Override
    public IdValidationViewModel addProperty(Enum name, float value) {
        super.addProperty(name, value);
        return this;
    }

    @Override
    public IdValidationViewModel addProperty(Enum name, double value) {
        super.addProperty(name, value);
        return this;
    }

    @Override
    public IdValidationViewModel addProperty(Enum name, boolean value) {
        super.addProperty(name, value);
        return this;
    }

    @Override
    public IdValidationViewModel addProperty(Enum name, Collection value) {
        super.addProperty(name, value);
        return this;
    }

    @Override
    public IdValidationViewModel addProperty(Enum name, Collection value, boolean skip) {
        super.addProperty(name, value, skip);
        return this;
    }

    @Override
    public IdValidationViewModel addProperty(Enum name, Object value) {
        super.addProperty(name, value);
        return this;
    }

    @Override
    public IdValidationViewModel setPropertyValue(Object propId, Object value, boolean skip) {
        super.setPropertyValue(propId, value, skip);
        return this;
    }

    @Override
    public IdValidationViewModel setPropertyValues(Object propId, Collection values, boolean skip) {
        super.setPropertyValues(propId, values, skip);
        return this;
    }

    @Override
    protected IdValidationViewModel ifPresentOrElse(Object propId, Consumer<PropertyIdentifier> propIdPresent, Consumer<String> propName) {
        super.ifPresentOrElse(propId, propIdPresent, propName);
        return this;
    }

    @Override
    protected IdValidationViewModel ifNotPresentAddPropertyId(PropertyIdentifier propId, Consumer<String> consumer) {
        super.ifNotPresentAddPropertyId(propId, consumer);
        return this;
    }

    @Override
    public IdValidationViewModel addProperty(String name, Collection value, boolean skip) {
        super.addProperty(name, value, skip);
        return this;
    }

    @Override
    public IdValidationViewModel addProperty(PropertyIdentifier propId, Collection value, boolean skip) {
        super.addProperty(propId, value, skip);
        return this;
    }

    @Override
    public IdValidationViewModel addValidator(String name, String friendlyName, ObjectConsumerValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    @Override
    public IdValidationViewModel addValidator(String name, String friendlyName, SetConsumerValidator validator) {
        Validatable.super.addValidator(name, friendlyName, validator);
        return this;
    }

    @Override
    public IdValidationViewModel addValidator(String name, String friendlyName, StringConsumerValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    @Override
    public IdValidationViewModel addValidator(String name, String friendlyName, ConsumerValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    @Override
    public IdValidationViewModel addValidator(Enum name, String friendlyName, StringValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    @Override
    public IdValidationViewModel addValidator(Enum name, String friendlyName, BooleanValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    @Override
    public IdValidationViewModel addValidator(Enum name, String friendlyName, IntegerValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    @Override
    public IdValidationViewModel addValidator(Enum name, String friendlyName, LongValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    @Override
    public IdValidationViewModel addValidator(Enum name, String friendlyName, FloatValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    @Override
    public IdValidationViewModel addValidator(Enum name, String friendlyName, DoubleValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    @Override
    public IdValidationViewModel addValidator(Enum name, String friendlyName, ObjectValidator validator) {
        return null;
    }

    @Override
    public IdValidationViewModel addValidator(Enum name, String friendlyName, ListValidator validator) {
        Validatable.super.addValidator(name, friendlyName, validator);
        return this;
    }

    @Override
    public IdValidationViewModel addValidator(Enum name, String friendlyName, CustomValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    @Override
    public IdValidationViewModel addValidator(Enum name, String friendlyName, BooleanConsumerValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    @Override
    public IdValidationViewModel addValidator(Enum name, String friendlyName, DoubleConsumerValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    @Override
    public IdValidationViewModel addValidator(Enum name, String friendlyName, FloatConsumerValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    @Override
    public IdValidationViewModel addValidator(Enum name, String friendlyName, IntegerConsumerValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    @Override
    public IdValidationViewModel addValidator(Enum name, String friendlyName, ListConsumerValidator validator) {
        Validatable.super.addValidator(name, friendlyName, validator);
        return this;
    }

    @Override
    public IdValidationViewModel addValidator(Enum name, String friendlyName, LongConsumerValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    @Override
    public IdValidationViewModel addValidator(Enum name, String friendlyName, ObjectConsumerValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    @Override
    public IdValidationViewModel addValidator(Enum name, String friendlyName, SetConsumerValidator validator) {
        Validatable.super.addValidator(name, friendlyName, validator);
        return this;
    }

    @Override
    public IdValidationViewModel addValidator(Enum name, String friendlyName, StringConsumerValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    @Override
    public IdValidationViewModel addValidator(Enum name, String friendlyName, ConsumerValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
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
            super.save();
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

        super.save();
        return this;
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
            String found = Validatable.super.getFriendlyName(propertyIdentifier.get().idToString());
            if (found != null) {
                return found;
            }
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
