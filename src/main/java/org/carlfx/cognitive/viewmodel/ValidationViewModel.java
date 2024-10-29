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

import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import org.carlfx.cognitive.validator.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
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
        super.addProperty(name, property);
        return this;
    }
    /**
     * Adds a property based on a name and sets a string as the value.
     * @param name Name of property
     * @param value A JavaFX property
     * @return returns itself.
     */
    @Override
    public ValidationViewModel addProperty(String name, String value) {
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
    public ValidationViewModel addProperty(String name, int value) {
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
    public ValidationViewModel addProperty(String name, long value) {
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
    public ValidationViewModel addProperty(String name, float value) {
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
    public ValidationViewModel addProperty(String name, double value) {
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
    public ValidationViewModel addProperty(String name, boolean value) {
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
    public ValidationViewModel addProperty(String name, Collection value) {
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
    public ValidationViewModel addProperty(String name, Object value) {
        super.addProperty(name, value);
        return this;
    }

    @Override
    public ValidationViewModel setValue(Enum name, Object value) {
        super.setValue(name, value);
        return this;
    }

    @Override
    public ValidationViewModel setPropertyValue(Enum name, Object value, boolean skip) {
        super.setPropertyValue(name, value, skip);
        return this;
    }

    @Override
    public ValidationViewModel setPropertyValues(Enum name, Collection values, boolean skip) {
        super.setPropertyValues(name, values, skip);
        return this;
    }

    @Override
    public ValidationViewModel setPropertyValue(Enum name, Object value) {
        super.setPropertyValue(name, value);
        return this;
    }

    @Override
    public ValidationViewModel setPropertyValues(Enum name, Collection values) {
        super.setPropertyValues(name, values);
        return this;
    }

    @Override
    public ValidationViewModel addProperty(Enum name, Property property) {
        super.addProperty(name, property);
        return this;
    }

    @Override
    public ValidationViewModel addProperty(Enum name, String value) {
        super.addProperty(name, value);
        return this;
    }

    @Override
    public ValidationViewModel addProperty(Enum name, int value) {
        super.addProperty(name, value);
        return this;
    }

    @Override
    public ValidationViewModel addProperty(Enum name, long value) {
        super.addProperty(name, value);
        return this;
    }

    @Override
    public ValidationViewModel addProperty(Enum name, float value) {
        super.addProperty(name, value);
        return this;
    }

    @Override
    public ValidationViewModel addProperty(Enum name, double value) {
        super.addProperty(name, value);
        return this;
    }

    @Override
    public ValidationViewModel addProperty(Enum name, boolean value) {
        super.addProperty(name, value);
        return this;
    }

    @Override
    public ValidationViewModel addProperty(Enum name, Collection value) {
        super.addProperty(name, value);
        return this;
    }

    @Override
    public ValidationViewModel addProperty(String name, Collection value, boolean skip) {
        super.addProperty(name, value, skip);
        return this;
    }

    @Override
    public ValidationViewModel addProperty(Enum name, Collection value, boolean skip) {
        super.addProperty(name, value, skip);
        return this;
    }

    @Override
    public <T extends ViewModel> T addProperty(Enum name, Function<T, Collection> value) {
        super.addProperty(name, value);
        return (T) this;
    }

    @Override
    public <T extends ViewModel> T addProperty(Enum name, Function<T, Collection> value, boolean skip) {
        super.addProperty(name, value, skip);
        return (T) this;
    }

    @Override
    public ValidationViewModel addProperty(Enum name, Object value) {
        super.addProperty(name, value);
        return this;
    }

    @Override
    public <T extends ViewModel> ValidationViewModel addProperty(String name, Function<T, Collection> value) {
        super.addProperty(name, value);
        return this;
    }

    @Override
    public <T extends ViewModel> ValidationViewModel addProperty(String name, Function<T, Collection> value, boolean skip) {
        super.addProperty(name, value, skip);
        return this;
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
     *
     * @param name         Property name
     * @param friendlyName A string of the validation message
     * @param validator    Validator based on datatype
     * @return ValidationViewModel itself.
     */
    @Override
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
        getValidationManager().createFieldValidator(name, friendlyName, validator);
        return this;
    }

    @Override
    public ValidationViewModel addValidator(String name, String friendlyName, BooleanConsumerValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    @Override
    public ValidationViewModel addValidator(String name, String friendlyName, DoubleConsumerValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    @Override
    public ValidationViewModel addValidator(String name, String friendlyName, FloatConsumerValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    @Override
    public ValidationViewModel addValidator(String name, String friendlyName, IntegerConsumerValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    @Override
    public ValidationViewModel addValidator(String name, String friendlyName, ListConsumerValidator validator) {
        Validatable.super.addValidator(name, friendlyName, validator);
        return this;
    }

    @Override
    public ValidationViewModel addValidator(String name, String friendlyName, LongConsumerValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    @Override
    public ValidationViewModel addValidator(String name, String friendlyName, ObjectConsumerValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    @Override
    public ValidationViewModel addValidator(String name, String friendlyName, SetConsumerValidator validator) {
        Validatable.super.addValidator(name, friendlyName, validator);
        return this;
    }

    @Override
    public ValidationViewModel addValidator(String name, String friendlyName, StringConsumerValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    @Override
    public ValidationViewModel addValidator(String name, String friendlyName, ConsumerValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    @Override
    public ValidationViewModel addValidator(Enum name, String friendlyName, StringValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    @Override
    public ValidationViewModel addValidator(Enum name, String friendlyName, BooleanValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    @Override
    public ValidationViewModel addValidator(Enum name, String friendlyName, IntegerValidator validator) {
        addValidator(name.toString(), friendlyName, validator);
        return this;
    }

    @Override
    public ValidationViewModel addValidator(Enum name, String friendlyName, LongValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    @Override
    public ValidationViewModel addValidator(Enum name, String friendlyName, FloatValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    @Override
    public ValidationViewModel addValidator(Enum name, String friendlyName, DoubleValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    @Override
    public ValidationViewModel addValidator(Enum name, String friendlyName, ObjectValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    @Override
    public ValidationViewModel addValidator(Enum name, String friendlyName, ListValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    @Override
    public ValidationViewModel addValidator(Enum name, String friendlyName, CustomValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    @Override
    public ValidationViewModel addValidator(Enum name, String friendlyName, BooleanConsumerValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    @Override
    public ValidationViewModel addValidator(Enum name, String friendlyName, DoubleConsumerValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    @Override
    public ValidationViewModel addValidator(Enum name, String friendlyName, FloatConsumerValidator validator) {
        return Validatable.super.addValidator(name, friendlyName, validator);
    }

    @Override
    public ValidationViewModel addValidator(Enum name, String friendlyName, IntegerConsumerValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    @Override
    public ValidationViewModel addValidator(Enum name, String friendlyName, ListConsumerValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    @Override
    public ValidationViewModel addValidator(Enum name, String friendlyName, LongConsumerValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    @Override
    public ValidationViewModel addValidator(Enum name, String friendlyName, ObjectConsumerValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    @Override
    public ValidationViewModel addValidator(Enum name, String friendlyName, SetConsumerValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    @Override
    public ValidationViewModel addValidator(Enum name, String friendlyName, StringConsumerValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    @Override
    public ValidationViewModel addValidator(Enum name, String friendlyName, ConsumerValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
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
     * Invokes validators associated with a property and passes the validation messages accumulated.
     * @param propName property owning validators to be performed.
     * @param validationMessagesConsumer after validation a list of validation messages are passed to the caller to be used to decorate the UI.
     * @return Returns itself ValidationViewModel. Follows a builder type pattern.
     */
    public ValidationViewModel validateOnChange(Enum propName, Consumer<List<ValidationMessage>> validationMessagesConsumer) {
        ChangeListener<?> propChecker = (obs, oldValue, newValue) -> {
            // clear any first name messages.
            getValidationManager()
                    .getValidationMessages()
                    .removeIf(validationMessage ->
                            validationMessage.propertyName().equals(propName.name()));

            // invoke validators.
            List<Validator<?, ViewModel>> validators = getValidators(propName);
            validators.forEach(validator -> {
                ValidationMessage message = validator.apply(getProperty(propName), this);
                if (message != null) {
                    getValidationManager().getValidationMessages().add(message);
                }
            });

            List<ValidationMessage> validationMessages = getValidationManager()
                    .getValidationMessages()
                    .stream()
                    .filter(validationMessage ->
                            validationMessage.propertyName().equals(propName.name()))
                    .toList();
            Platform.runLater(() -> validationMessagesConsumer.accept(validationMessages));
        };
        getProperty(propName).addListener(propChecker);
        return this;
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
