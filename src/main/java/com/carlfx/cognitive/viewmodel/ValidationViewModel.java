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

package com.carlfx.cognitive.viewmodel;

import com.carlfx.cognitive.validator.*;
import javafx.beans.property.Property;

import java.util.*;
import java.util.stream.Collectors;

import static com.carlfx.cognitive.validator.MessageType.*;

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
public class ValidationViewModel extends SimpleViewModel {
    public final static ValidationMessage VALID = null;

    private final Map<String, String> friendlyNameMap = new LinkedHashMap<>();
    protected Map<String, List<Validator<?>>> validatorsMap = new LinkedHashMap<>();
    protected List<ValidationMessage> validationMessages = new ArrayList<>();

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
     * @return
     */
    @Override
    public ValidationViewModel setValue(String name, Object value) {
        return (ValidationViewModel) super.setValue(name, value);
    }

    /**
     * Sets the property value layer based on the property name.
     * @param name property name
     * @param value Raw value to be set as the committed data.
     * @return
     */
    @Override
    public ValidationViewModel setPropertyValue(String name, Object value) {
        return (ValidationViewModel) super.setPropertyValue(name, value);
    }

    /**
     * Sets the property value layer based on the property name.
     * @param name property name
     * @param values Raw values to be set as a collection.
     * @return ValidationViewModel itself
     */
    @Override
    public ValidationViewModel setPropertyValues(String name, Collection values) {
        return (ValidationViewModel)super.setPropertyValues(name, values);
    }

    /**
     * Allows derived classes to create field (property) validators.
     * @param name Property name
     * @param friendlyName Friendly name of field
     * @param validator Validator based on datatype
     * @return ValidationViewModel itself.
     * @param <U> Any class derived from a ValidationViewModel
     * @param <T> Any class derived from a Validator
     */
    protected  <U extends ValidationViewModel, T extends Validator> U createFieldValidator(String name, String friendlyName, T validator) {
        String alreadyName = friendlyNameMap.get(name);
        if ((alreadyName != null && !alreadyName.isBlank() && (friendlyName == null || friendlyName.isBlank()))) {
            // ignore the caller's friendly name as null or empty
        } else {
            friendlyNameMap.putIfAbsent(name, friendlyName);
        }
        validatorsMap.putIfAbsent(name, new ArrayList<>());
        List<Validator<?>> validators = validatorsMap.get(name);
        validators.add(validator);
        return (U) this;
    }

    /**
     * Allows derived classes to create global type validators. e.g. saving to database a duplicate record.
     * @param errorCode an error code assigned to error message.
     * @param name Property name
     * @param errorMsg A string of the validation message
     * @param validator Validator based on datatype
     * @return ValidationViewModel itself.
     * @param <U> Any class derived from a ValidationViewModel
     * @param <T> Any class derived from a Validator
     */
    protected  <U extends ValidationViewModel, T extends Validator> U createGlobalValidator(int errorCode, String name, String errorMsg, T validator) {
        validatorsMap.putIfAbsent(name, new ArrayList());
        List validators = validatorsMap.get(name);
        validators.add(validator);
        return (U) this;
    }
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ValidationViewModel itself.
     * @param <U> Any class derived from a ValidationViewModel
     *
     */
    public <U extends ValidationViewModel> U addValidator(String name, String friendlyName, StringValidator validator) {
        return createFieldValidator(name, friendlyName, validator);
    }
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ValidationViewModel itself.
     * @param <U> Any class derived from a ValidationViewModel
     *
     */
    public <U extends ValidationViewModel> U addValidator(String name, String friendlyName, BooleanValidator validator) {
        return createFieldValidator(name, friendlyName, validator);
    }
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ValidationViewModel itself.
     * @param <U> Any class derived from a ValidationViewModel
     *
     */
    public <U extends ValidationViewModel> U addValidator(String name, String friendlyName, IntegerValidator validator) {
        return createFieldValidator(name, friendlyName, validator);
    }
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ValidationViewModel itself.
     * @param <U> Any class derived from a ValidationViewModel
     *
     */
    public <U extends ValidationViewModel> U addValidator(String name, String friendlyName, LongValidator validator) {
        return createFieldValidator(name, friendlyName, validator);
    }
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ValidationViewModel itself.
     * @param <U> Any class derived from a ValidationViewModel
     *
     */
    public <U extends ValidationViewModel> U addValidator(String name, String friendlyName, FloatValidator validator) {
        return createFieldValidator(name, friendlyName, validator);
    }
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ValidationViewModel itself.
     * @param <U> Any class derived from a ValidationViewModel
     *
     */
    public <U extends ValidationViewModel> U addValidator(String name, String friendlyName, DoubleValidator validator) {
        return createFieldValidator(name, friendlyName, validator);
    }
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ValidationViewModel itself.
     * @param <U> Any class derived from a ValidationViewModel
     *
     */
    public <U extends ValidationViewModel> U addValidator(String name, String friendlyName, ObjectValidator validator) {
        return createFieldValidator(name, friendlyName, validator);
    }
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ValidationViewModel itself.
     * @param <U> Any class derived from a ValidationViewModel
     *
     */
    public <U extends ValidationViewModel> U addValidator(String name, String friendlyName, ListValidator validator) {
        return createFieldValidator(name, friendlyName, validator);
    }

    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ValidationViewModel itself.
     * @param <U> Any class derived from a ValidationViewModel
     *
     */
    public <U extends ValidationViewModel> U addValidator(String name, String friendlyName, SetValidator validator) {
        return createFieldValidator(name, friendlyName, validator);
    }

    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ValidationViewModel itself.
     * @param <U> Any class derived from a ValidationViewModel
     *
     */
    public <U extends ValidationViewModel> U addValidator(String name, String friendlyName, CustomValidator validator) {
        friendlyNameMap.putIfAbsent(name, friendlyName);
        validatorsMap.putIfAbsent(name, new ArrayList());
        List validators = validatorsMap.get(name);
        validators.add(validator);
        return (U) this;
    }

    /**
     * Goes through all validators to execute creating error, warning, info
     * @return
     * @param <U>
     */
    public <U extends ValidationViewModel> U validate() {
        validationMessages.clear();
        // with each property contain a validator eval and build validation messages.
        validatorsMap.forEach( (name, validators) -> {
            // each property evaluate validators
            validationMessages.addAll(validators.stream().map(validator -> {
                // call each validator with property and this vm
                // 1. property?
                // 2. list?
                // 3. set?
                // 4. custom global name
                ValidationMessage validationMessage = switch (validator) {
                    case BooleanValidator boolValidator -> boolValidator.apply(getProperty(name), this);
                    case CustomValidator customValidator -> customValidator.apply(getProperty(name), this);
                    case DoubleValidator doubleValidator -> doubleValidator.apply(getProperty(name), this);
                    case FloatValidator floatValidator -> floatValidator.apply(getProperty(name), this);
                    case IntegerValidator intValidator -> intValidator.apply(getProperty(name), this);
                    case ListValidator listValidator -> listValidator.apply(getProperty(name), this);
                    case LongValidator longValidator -> longValidator.apply(getProperty(name), this);
                    case ObjectValidator objValidator -> objValidator.apply(getProperty(name), this);
                    case SetValidator setValidator -> setValidator.apply(getProperty(name), this);
                    case StringValidator stringValidator -> stringValidator.apply(getProperty(name), this);
                    default -> throw new IllegalStateException("Unexpected value: " + validator);
                };
                return validationMessage;
            }).filter( vMsg -> vMsg != null).toList());
        });
        return (U) this;
    }

    /**
     * Returns all validators based on property name
     * @param name property name
     * @return List of validators
     * @param <T> Derived Validators
     * @param <U> Derived JavaFX Property
     */
    public <T extends Validator<U>, U> List<T> getValidators(String name) {
        return (List<T>)validatorsMap.get(name);
    }

    /**
     * Returns all validation messages.
     * @return A list of validation messages.
     */
    public List<ValidationMessage> getValidationMessages() {
        return validationMessages;
    }

    /**
     * True is returned if there are error messages otherwise false.
     * @return True is returned if there are error messages otherwise false.
     */
    public boolean hasErrorMsgs() {
        return hasMsgType(ERROR);
    }

    /**
     * True is returned if there are no error messages otherwise false.
     * @return True is returned if there are no error messages otherwise false.
     */
    public boolean hasNoErrorMsgs() {
        return !hasErrorMsgs();
    }

    /**
     * True is returned if there are warning messages otherwise false.
     * @return True is returned if there are warning messages otherwise false.
     */
    public boolean hasWarningMsgs() {
        return hasMsgType(WARN);
    }

    /**
     * True is returned if there are no warning messages otherwise false.
     * @return True is returned if there are no warning messages otherwise false.
     */
    public boolean hasNoWarningMsgs() {
        return !hasWarningMsgs();
    }

    /**
     * True is returned if there are info messages otherwise false.
     * @return True is returned if there are info messages otherwise false.
     */
    public boolean hasInfoMsgs() {
        return hasMsgType(INFO);
    }

    /**
     * True is returned if there are no info messages otherwise false.
     * @return True is returned if there are no info messages otherwise false.
     */
    public boolean hasNoInfoMsgs() {
        return !hasInfoMsgs();
    }

    private boolean hasMsgType(MessageType type){
        if (getValidationMessages() != null) {
            return getValidationMessages().stream().filter(msg -> msg.messageType() == type).findAny().isPresent();
        }
        return false;
    }

    /**
     * Invalidate or clear error messages.
     * @return itself
     * @param <U> Derived ValidationViewModel
     */
    public <U extends ValidationViewModel> U invalidate() {
        getValidationMessages().clear();
        return (U) this;
    }

    /**
     * Returns friendly name of property name.
     * @param propertName property name
     * @return friendly name
     */
    public String getFriendlyName(String propertName) {
        return friendlyNameMap.get(propertName);
    }

    /**
     * True will force a save. The validation does not occur, property values copied to model values. false will call regular save().
     * @param force
     * @return
     * @param force True to save without validation.
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
        if (getValidationMessages().stream().filter(validationMessage -> validationMessage.messageType() == ERROR).findAny().isPresent()) {
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
                "friendlyNameMap=" + friendlyNameMap +
                ", validationMessages=[" + messages.stream().collect(Collectors.joining(",")) + "]" +
                '}';
    }
}
