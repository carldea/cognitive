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

import java.util.*;
import java.util.stream.Collectors;

/**
 * A validation view model manages validation rules for associated view properties and global type validations.
 * Use cases are the following:
 * <pre>
 *     1. Fields are required (not null or not empty string).
 *     2. Field has required input format. e.g. US Phone number format.
 *     3. Range values must be in(within) or outside of range. e.g. Date range or 1-100.
 *     4. Course grain or transactional type errors such as a Duplicate or Unknown (Runtime Exceptions). e.g. Already saved.
 * </pre>
 */
public class ValidationViewModel extends SimpleViewModel implements Validatable {

    private ValidationManager validationManager = new ValidationManager();

    /**
     * Default Constructor.
     */
    public ValidationViewModel(){
        super();
    }
    @Override
    public ValidationManager getValidationManager() {
        return validationManager;
    }
    /**
     * Adds a property based on a name.
     * @param name Name of property
     * @param property A JavaFX property
     * @return returns itself.
     */
    @Override
    public ValidationViewModel addProperty(String name, Property property) {
        return super.addProperty(name, property);
    }
    /**
     * Adds a property based on a name and sets a string as the value.
     * @param name Name of property
     * @param value A JavaFX property
     * @return returns itself.
     */
    @Override
    public ValidationViewModel addProperty(String name, String value) {
        return super.addProperty(name, value);
    }
    /**
     * Adds a property based on a name and sets an int as the value.
     * @param name Name of property
     * @param value A JavaFX property
     * @return returns itself.
     */
    @Override
    public ValidationViewModel addProperty(String name, int value) {
        return super.addProperty(name, value);
    }
    /**
     * Adds a property based on a name and sets a long as the value.
     * @param name Name of property
     * @param value A JavaFX property
     * @return returns itself.
     */
    @Override
    public ValidationViewModel addProperty(String name, long value) {
        return super.addProperty(name, value);
    }
    /**
     * Adds a property based on a name and sets a float as the value.
     * @param name Name of property
     * @param value A JavaFX property value
     * @return returns itself.
     */
    @Override
    public ValidationViewModel addProperty(String name, float value) {
        return super.addProperty(name, value);
    }
    /**
     * Adds a property based on a name and sets a double as the value.
     * @param name Name of property
     * @param value A JavaFX property value
     * @return returns itself.
     */
    @Override
    public ValidationViewModel addProperty(String name, double value) {
        return super.addProperty(name, value);
    }
    /**
     * Adds a property based on a name and sets a boolean as the value.
     * @param name Name of property
     * @param value A JavaFX property value
     * @return returns itself.
     */
    @Override
    public ValidationViewModel addProperty(String name, boolean value) {
        return super.addProperty(name, value);
    }
    /**
     * Adds a property based on a name and sets a collection as the value.
     * @param name Name of property
     * @param value A JavaFX property value
     * @return returns itself.
     */
    @Override
    public ValidationViewModel addProperty(String name, Collection value) {
        return super.addProperty(name, value);
    }
    /**
     * Adds a property based on a name and sets an object as the value.
     * @param name Name of property
     * @param value A JavaFX property value
     * @return returns itself.
     */
    @Override
    public ValidationViewModel addProperty(String name, Object value) {
        return super.addProperty(name, value);
    }

    /**
     * Sets a property based on a name and sets values as a collection.
     * @param name Name of property
     * @param values A collection
     * @param skip skip copying property values over to model values.
     * @return returns itself.
     */
    public ValidationViewModel setPropertyValues(String name, Collection values, boolean skip){
        return (ValidationViewModel) super.setPropertyValues(name, values, skip);
    }

    /**
     * Sets a property based on a name and sets values as a collection.
     * @param name Name of property
     * @param value An Object
     * @param skip skip copying property values over to model values.
     * @return returns itself.
     */
    @Override
    public ValidationViewModel setPropertyValue(String name, Object value, boolean skip) {
        return (ValidationViewModel) super.setPropertyValue(name, value, skip);
    }

    /**
     * Copies model values back into property values.
     * @return returns itself.
     */
    @Override
    public ValidationViewModel reset() {
        return (ValidationViewModel) super.reset();
    }

    /**
     * Sets the model value layer based on the property name.
     * @param name property name
     * @param value Raw value to be set as the committed data.
     * @return Returns a ValidationViewModel following the builder pattern.
     */
    @Override
    public ValidationViewModel setValue(String name, Object value) {
        return (ValidationViewModel) super.setValue(name, value);
    }

    /**
     * Sets the property value layer based on the property name.
     * @param name property name
     * @param value Raw value to be set as the committed data.
     * @return Returns a ValidationViewModel following the builder pattern.
     */
    @Override
    public ValidationViewModel setPropertyValue(String name, Object value) {
        return (ValidationViewModel) super.setPropertyValue(name, value);
    }

    /**
     * Sets the property value layer based on the property name.
     * @param name property name
     * @param values Raw values to be set as a collection.
     * @return Returns a ValidationViewModel following the builder pattern.
     */
    @Override
    public ValidationViewModel setPropertyValues(String name, Collection values) {
        return (ValidationViewModel)super.setPropertyValues(name, values);
    }

    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ValidationViewModel itself.
     *
     */
    public ValidationViewModel addValidator(String name, String friendlyName, StringValidator validator) {
        validationManager.createFieldValidator(name, friendlyName, validator);
        return this;
    }
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ValidationViewModel itself.
     *
     */
    public ValidationViewModel addValidator(String name, String friendlyName, BooleanValidator validator) {
        validationManager.createFieldValidator(name, friendlyName, validator);
        return this;
    }
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ValidationViewModel itself.
     *
     */
    public ValidationViewModel addValidator(String name, String friendlyName, IntegerValidator validator) {
        validationManager.createFieldValidator(name, friendlyName, validator);
        return this;
    }
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ValidationViewModel itself.
     *
     */
    public ValidationViewModel addValidator(String name, String friendlyName, LongValidator validator) {
        validationManager.createFieldValidator(name, friendlyName, validator);
        return this;
    }
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ValidationViewModel itself.
     *
     */
    public ValidationViewModel addValidator(String name, String friendlyName, FloatValidator validator) {
        validationManager.createFieldValidator(name, friendlyName, validator);
        return this;
    }
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ValidationViewModel itself.
     *
     */
    public ValidationViewModel addValidator(String name, String friendlyName, DoubleValidator validator) {
        validationManager.createFieldValidator(name, friendlyName, validator);
        return this;
    }
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ValidationViewModel itself.
     *
     */
    public ValidationViewModel addValidator(String name, String friendlyName, ObjectValidator validator) {
        validationManager.createFieldValidator(name, friendlyName, validator);
        return this;
    }
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ValidationViewModel itself.
     *
     */
    public ValidationViewModel addValidator(String name, String friendlyName, ListValidator validator) {
        validationManager.createFieldValidator(name, friendlyName, validator);
        return this;
    }

    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ValidationViewModel itself.
     *
     */
    public ValidationViewModel addValidator(String name, String friendlyName, SetValidator validator) {
        validationManager.createFieldValidator(name, friendlyName, validator);
        return this;
    }

    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ValidationViewModel itself.
     *
     */
    public ValidationViewModel addValidator(String name, String friendlyName, CustomValidator validator) {
        validationManager.createFieldValidator(name, friendlyName, validator);
        return this;
    }

    /**
     * Goes through all validators to execute creating error, warning, info
     * @return Returns a ValidationViewModel following the builder pattern.
     */
    public ValidationViewModel validate() {
        validationManager.validate(this);
        return this;
    }

    /**
     * Invalidate or clear error messages.
     * @return itself
     */
    public ValidationViewModel invalidate() {
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
    public ValidationViewModel save(boolean force) {
        if (force) {
            return (ValidationViewModel) super.save();
        }
        return this.save();
    }

    /**
     * Will call validate() before save(). If there are errors values won't be copied (saved).
     * @return itself
     */
    @Override
    public ValidationViewModel save() {
        // validate if any are errors, don't save.
        validate();
        if (getValidationManager().hasErrorMsgs()) {
            return this;
        }

        return (ValidationViewModel) super.save();
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
        return "ValidationViewModel{" +
                super.toString() +
                ", " +
                "friendlyNameMap=" + getValidationManager().getFriendlyNameMap() +
                ", validationMessages=[" + messages.stream().collect(Collectors.joining(",")) + "]" +
                '}';
    }
}
