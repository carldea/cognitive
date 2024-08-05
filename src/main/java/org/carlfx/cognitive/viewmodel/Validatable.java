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

import javafx.beans.property.*;
import org.carlfx.cognitive.validator.*;

import java.util.List;

/**
 * The Validatable interface provides the validation behavior for Validation Model. These methods primarily are a pass through or a delegate to invoke similar methods on the validation manager.
 */
public interface Validatable {
    /**
     * When validators are successful (no errors, warnings, infos) Valid is returned.
     */
    ValidationMessage VALID = null;

    /**
     * Returns the validation manager.
     * @return Returns the validation manager.
     */
    ValidationManager getValidationManager();
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ViewModel itself.
     * @param <U> Any class derived from a ViewModel
     *
     */
    <U extends ViewModel> U addValidator(String name, String friendlyName, StringValidator validator);
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ViewModel itself.
     * @param <U> Any class derived from a ViewModel
     *
     */
    <U extends ViewModel> U addValidator(String name, String friendlyName, BooleanValidator validator);
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ViewModel itself.
     * @param <U> Any class derived from a ViewModel
     *
     */
    <U extends ViewModel> U addValidator(String name, String friendlyName, IntegerValidator validator);
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ViewModel itself.
     * @param <U> Any class derived from a ViewModel
     *
     */
    <U extends ViewModel> U addValidator(String name, String friendlyName, LongValidator validator);
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ViewModel itself.
     * @param <U> Any class derived from a ViewModel
     *
     */
    <U extends ViewModel> U addValidator(String name, String friendlyName, FloatValidator validator);

    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ViewModel itself.
     * @param <U> Any class derived from a ViewModel
     *
     */
    <U extends ViewModel> U addValidator(String name, String friendlyName, DoubleValidator validator);
    
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ViewModel itself.
     * @param <U> Any class derived from a ViewModel
     *
     */
    <U extends ViewModel> U addValidator(String name, String friendlyName, ObjectValidator validator);
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ViewModel itself.
     * @param <U> Any class derived from a ViewModel
     *
     */
    <U extends ViewModel> U addValidator(String name, String friendlyName, ListValidator validator);

    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ViewModel itself.
     * @param <U> Any class derived from a ViewModel
     *
     */
    <U extends ViewModel> U addValidator(String name, String friendlyName, SetValidator validator);

    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ViewModel itself.
     * @param <U> Any class derived from a ViewModel
     *
     */
    <U extends ViewModel> U addValidator(String name, String friendlyName, CustomValidator validator);

    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the boolean property is passed in to be used.
     * @param name Property name
     * @param friendlyName Friendly name of property name
     * @param validator consumer validator converts to an actual type validator.
     *                  e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @return returns itself following the builder pattern.
     * @param <U> U is a derived class of type ViewModel.
     */
    default <U extends ViewModel> U addValidator(String name, String friendlyName, BooleanConsumerValidator validator){
        BooleanValidator tValidator = (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
        getValidationManager().createFieldValidator(name, friendlyName, tValidator);
        return (U) this;
    }

    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the double property is passed in to be used.
     * @param name Property name
     * @param friendlyName Friendly name of property name
     * @param validator consumer validator converts to an actual type validator.
     *                  e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @return returns itself following the builder pattern.
     * @param <U> U is a derived class of type ViewModel.
     */
    default <U extends ViewModel> U addValidator(String name, String friendlyName, DoubleConsumerValidator validator){
        DoubleValidator tValidator = (ReadOnlyDoubleProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
        getValidationManager().createFieldValidator(name, friendlyName, tValidator);
        return (U) this;
    }

    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the float property is passed in to be used.
     * @param name Property name
     * @param friendlyName Friendly name of property name
     * @param validator consumer validator converts to an actual type validator.
     *                  e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @return returns itself following the builder pattern.
     * @param <U> U is a derived class of type ViewModel.
     */
    default <U extends ViewModel> U addValidator(String name, String friendlyName, FloatConsumerValidator validator){
        FloatValidator tValidator = (ReadOnlyFloatProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
        getValidationManager().createFieldValidator(name, friendlyName, tValidator);
        return (U) this;
    }

    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the integer property is passed in to be used.
     * @param name Property name
     * @param friendlyName Friendly name of property name
     * @param validator consumer validator converts to an actual type validator.
     *                  e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @return returns itself following the builder pattern.
     * @param <U> U is a derived class of type ViewModel.
     */
    default <U extends ViewModel> U addValidator(String name, String friendlyName, IntegerConsumerValidator validator){
        IntegerValidator tValidator = (ReadOnlyIntegerProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
        getValidationManager().createFieldValidator(name, friendlyName, tValidator);
        return (U) this;
    }

    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the list as a read-only property is passed in to be used.
     * @param name Property name
     * @param friendlyName Friendly name of property name
     * @param validator consumer validator converts to an actual type validator.
     *                  e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @return returns itself following the builder pattern.
     * @param <U> U is a derived class of type ViewModel.
     */
    default <U extends ViewModel> U addValidator(String name, String friendlyName, ListConsumerValidator validator){
        ListValidator tValidator = (ReadOnlyListProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
        getValidationManager().createFieldValidator(name, friendlyName, tValidator);
        return (U) this;
    }

    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the Long as a read-only property is passed in to be used.
     * @param name Property name
     * @param friendlyName Friendly name of property name
     * @param validator consumer validator converts to an actual type validator.
     *                  e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @return returns itself following the builder pattern.
     * @param <U> U is a derived class of type ViewModel.
     */
    default <U extends ViewModel> U addValidator(String name, String friendlyName, LongConsumerValidator validator){
        LongValidator tValidator = (ReadOnlyLongProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
        getValidationManager().createFieldValidator(name, friendlyName, tValidator);
        return (U) this;
    }

    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the object as a read-only property is passed in to be used.
     * @param name Property name
     * @param friendlyName Friendly name of property name
     * @param validator consumer validator converts to an actual type validator.
     *                  e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @return returns itself following the builder pattern.
     * @param <U> U is a derived class of type ViewModel.
     */
    default <U extends ViewModel> U addValidator(String name, String friendlyName, ObjectConsumerValidator validator){
        ObjectValidator tValidator = (ReadOnlyObjectProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
        getValidationManager().createFieldValidator(name, friendlyName, tValidator);
        return (U) this;
    }

    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the set as a read-only property is passed in to be used.
     * @param name Property name
     * @param friendlyName Friendly name of property name
     * @param validator consumer validator converts to an actual type validator.
     *                  e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @return returns itself following the builder pattern.
     * @param <U> U is a derived class of type ViewModel.
     */
    default <U extends ViewModel> U addValidator(String name, String friendlyName, SetConsumerValidator validator){
        SetValidator tValidator = (ReadOnlySetProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
        getValidationManager().createFieldValidator(name, friendlyName, tValidator);
        return (U) this;
    }

    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the string as a read-only property is passed in to be used.
     * @param name Property name
     * @param friendlyName Friendly name of property name
     * @param validator consumer validator converts to an actual type validator.
     *                  e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @return returns itself following the builder pattern.
     * @param <U> U is a derived class of type ViewModel.
     */
    default <U extends ViewModel> U addValidator(String name, String friendlyName, StringConsumerValidator validator){
        StringValidator stringValidator = (ReadOnlyStringProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
        getValidationManager().createFieldValidator(name, friendlyName, stringValidator);
        return (U) this;
    }

    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual custom validator.
     *
     * @param name Property name
     * @param friendlyName Friendly name of property name
     * @param validator consumer validator converts to an actual type validator.
     *                  e.g. CustomValidator customValidator = (Void prop, ViewModel vm) -> accumulateMessagesFunction(name, validator);
     * @return returns itself following the builder pattern.
     * @param <U> U is a derived class of type ViewModel.
     */
    default <U extends ViewModel> U addValidator(String name, String friendlyName, ConsumerValidator validator){
        CustomValidator customValidator = (Void prop, ViewModel vm) -> accumulateMessagesFunction(name, validator);
        getValidationManager().createFieldValidator(name, friendlyName, customValidator);
        return (U) this;
    }

    /**
     * Goes through all validators to execute creating error, warning, info
     * @return Returns a ViewModel following the builder pattern.
     * @param <U> U is a view model of type ViewModel
     */
    <U extends ViewModel> U validate();

    /**
     * Returns all validators based on property name
     * @param name property name
     * @return List of validators
     */
    default List<Validator<?, ViewModel>> getValidators(String name) {
        return (List<Validator<?, ViewModel>>) getValidationManager().getValidators(name);
    }

    /**
     * Returns all validation messages.
     * @return A list of validation messages.
     */
    default List<ValidationMessage> getValidationMessages() {
        return getValidationManager().getValidationMessages();
    }

    /**
     * True is returned if there are error messages otherwise false.
     * @return True is returned if there are error messages otherwise false.
     */
    default boolean hasErrorMsgs(){
        return getValidationManager().hasMsgType(MessageType.ERROR);
    }

    /**
     * True is returned if there are no error messages otherwise false.
     * @return True is returned if there are no error messages otherwise false.
     */
    default boolean hasNoErrorMsgs() {
        return getValidationManager().hasNoErrorMsgs();
    }

    /**
     * True is returned if there are warning messages otherwise false.
     * @return True is returned if there are warning messages otherwise false.
     */
    default boolean hasWarningMsgs(){
        return getValidationManager().hasWarningMsgs();
    }

    /**
     * True is returned if there are no warning messages otherwise false.
     * @return True is returned if there are no warning messages otherwise false.
     */
    default boolean hasNoWarningMsgs(){
        return getValidationManager().hasNoWarningMsgs();
    }

    /**
     * True is returned if there are info messages otherwise false.
     * @return True is returned if there are info messages otherwise false.
     */
    default boolean hasInfoMsgs(){
        return getValidationManager().hasInfoMsgs();
    }

    /**
     * True is returned if there are no info messages otherwise false.
     * @return True is returned if there are no info messages otherwise false.
     */
    default boolean hasNoInfoMsgs() {
        return getValidationManager().hasNoInfoMsgs();
    }


    /**
     * Invalidate or clear error messages.
     * @return itself
     * @param <U> Derived ViewModel
     */
    <U extends ViewModel> U invalidate();

    /**
     * Returns friendly name of property name.
     * @param propertName property name
     * @return friendly name
     */
    default String getFriendlyName(String propertName) {
        return getValidationManager().getFriendlyName(propertName);
    }

    /**
     * Based on the property name, consumer validator will pass caller an empty validation result (no messages).
     * @param name Property name
     * @param validator Consumer validator allows caller to add messages to validation result object.
     * @param prop the property as a read-only
     * @return ValidationMessage will be null or VALID. Messages will be in validation result.
     */
    default ValidationMessage accumulateMessagesFunction(String name, TypeConsumerValidator validator, ReadOnlyProperty prop) {
        ValidationResult inputValidationResult = new ValidationResult(name);
        // accumulate error messages
        validator.accept(prop, inputValidationResult, this);
        getValidationManager()
                .getValidationMessages()
                .addAll(inputValidationResult.getMessages());
        return VALID;
    }

    /**
     * Based on the property name, consumer validator will pass caller an empty validation result (no messages).
     * @param name Property name
     * @param validator Consumer validator allows caller to add messages to validation result object.
     * @return ValidationMessage will be null or VALID. Messages will be in validation result.
     */
    default ValidationMessage accumulateMessagesFunction(String name, ConsumerValidator validator) {
        ValidationResult inputValidationResult = new ValidationResult(name);
        // accumulate error messages
        validator.accept(inputValidationResult, (ViewModel) this);
        getValidationManager()
                .getValidationMessages()
                .addAll(inputValidationResult.getMessages());
        return VALID;
    }
}
