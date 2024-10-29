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
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
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
     *
     * @return Returns the validation manager.
     */
    ValidationManager getValidationManager();

    /**
     * A validator to evaluate against a String (ReadOnlyStringProperty). A StringValidator returns a single validation message.
     *
     * @param name         Property name
     * @param friendlyName A string of the validation message
     * @param validator    Validator based on datatype
     * @param <T>          Any class derived from a ViewModel
     * @return ViewModel itself.
     */
    <T extends ViewModel> T addValidator(String name, String friendlyName, StringValidator validator);

    /**
     * A validator to evaluate against a String (ReadOnlyStringProperty). A StringValidator returns a single validation message.
     *
     * @param name         Property name as an enum
     * @param friendlyName A string of the validation message
     * @param validator    Validator based on datatype
     * @param <T>          Any class derived from a ViewModel
     * @return ViewModel itself.
     */
    <T extends ViewModel> T addValidator(Enum name, String friendlyName, StringValidator validator);

    /**
     * A validator to evaluate against a boolean (ReadOnlyBooleanProperty). A BooleanValidator returns a single validation message.
     *
     * @param name         Property name
     * @param friendlyName A string of the validation message
     * @param validator    Validator based on datatype
     * @param <T>          Any class derived from a ViewModel
     * @return ViewModel itself.
     */
    <T extends ViewModel> T addValidator(String name, String friendlyName, BooleanValidator validator);

    /**
     * A validator to evaluate against a boolean (ReadOnlyBooleanProperty). A BooleanValidator returns a single validation message.
     *
     * @param name         Property name
     * @param friendlyName A string of the validation message
     * @param validator    Validator based on datatype
     * @param <T>          Any class derived from a ViewModel
     * @return ViewModel itself.
     */
    default <T extends ViewModel> T addValidator(Enum name, String friendlyName, BooleanValidator validator){
        return addValidator(name.toString(), friendlyName, validator);
    }

    /**
     * A validator to evaluate against an integer (ReadOnlyIntegerProperty). A IntegerValidator returns a single validation message.
     *
     * @param name         Property name
     * @param friendlyName A string of the validation message
     * @param validator    Validator based on datatype
     * @param <T>          Any class derived from a ViewModel
     * @return ViewModel itself.
     */
    <T extends ViewModel> T addValidator(String name, String friendlyName, IntegerValidator validator);

    /**
     * A validator to evaluate against an integer (ReadOnlyIntegerProperty). A IntegerValidator returns a single validation message.
     *
     * @param name         Property name
     * @param friendlyName A string of the validation message
     * @param validator    Validator based on datatype
     * @param <T>          Any class derived from a ViewModel
     * @return ViewModel itself.
     */
    default <T extends ViewModel> T addValidator(Enum name, String friendlyName, IntegerValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    /**
     * A validator to evaluate against a long (ReadOnlyLongProperty). A LongValidator returns a single validation message.
     *
     * @param name         Property name
     * @param friendlyName A string of the validation message
     * @param validator    Validator based on datatype
     * @param <T>          Any class derived from a ViewModel
     * @return ViewModel itself.
     */
    <T extends ViewModel> T addValidator(String name, String friendlyName, LongValidator validator);

    /**
     * A validator to evaluate against a long (ReadOnlyLongProperty). A LongValidator returns a single validation message.
     * @param name An Enum as a property name
     * @param friendlyName The friendly name used in the error message.
     * @param validator a validator evaluates and returns an error message.
     * @return Returns this validatable object.
     * @param <T> A derived ViewModel
     */
    default <T extends ViewModel> T addValidator(Enum name, String friendlyName, LongValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    /**
     * A validator to evaluate against a float (ReadOnlyFloatProperty). A FloatValidator returns a single validation message.
     *
     * @param name         Property name
     * @param friendlyName A string of the validation message
     * @param validator    Validator based on datatype float (FloatProperty)
     * @param <T>          Any class derived from a ViewModel
     * @return ViewModel itself.
     */
    <T extends ViewModel> T addValidator(String name, String friendlyName, FloatValidator validator);
    /**
     * A validator to evaluate against a Float (ReadOnlyFloatProperty). A FloatValidator returns a single validation message.
     *
     * @param name         Property name
     * @param friendlyName A string of the validation message
     * @param validator    Validator based on datatype float (FloatProperty)
     * @param <T>          Any class derived from a ViewModel
     * @return ViewModel itself.
     */
    default <T extends ViewModel> T addValidator(Enum name, String friendlyName, FloatValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    /**
     * A validator to evaluate against a double (ReadOnlyFloatProperty). A DoubleValidator returns a single validation message.
     *
     * @param name         Property name
     * @param friendlyName A string of the validation message
     * @param validator    Validator based on datatype (DoubleProperty)
     * @param <T>          Any class derived from a ViewModel
     * @return ViewModel itself.
     */
    <T extends ViewModel> T addValidator(String name, String friendlyName, DoubleValidator validator);

    /**
     * A validator to evaluate against a double (ReadOnlyDoubleProperty). A DoubleValidator returns a single validation message.
     *
     * @param name         Property name
     * @param friendlyName A string of the validation message
     * @param validator    Validator based on datatype (DoubleProperty)
     * @param <T>          Any class derived from a ViewModel
     * @return ViewModel itself.
     */
    default <T extends ViewModel> T addValidator(Enum name, String friendlyName, DoubleValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    /**
     * A validator to evaluate against an Object property (ReadOnlyObjectProperty). A ObjectValidator returns a single validation message.
     *
     * @param name         Property name
     * @param friendlyName A string of the validation message
     * @param validator    Validator based on datatype (ObjectProperty)
     * @param <T>          Any class derived from a ViewModel
     * @return ViewModel itself.
     */
    <T extends ViewModel> T addValidator(String name, String friendlyName, ObjectValidator validator);

    /**
     * A validator to evaluate against an Object property (ReadOnlyObjectProperty). A ObjectValidator returns a single validation message.
     *
     * @param name         Property name
     * @param friendlyName A string of the validation message
     * @param validator    Validator based on datatype (ObjectProperty)
     * @param <T>          Any class derived from a ViewModel
     * @return ViewModel itself.
     */
    <T extends ViewModel> T addValidator(Enum name, String friendlyName, ObjectValidator validator);

    /**
     * A validator to evaluate against a list property (ReadOnlyObservableList). A ListValidator returns a single validation message.
     *
     * @param name         Property name
     * @param friendlyName A string of the validation message
     * @param validator    Validator based on datatype
     * @param <T>          Any class derived from a ViewModel
     * @return ViewModel itself.
     */
    <T extends ViewModel> T addValidator(String name, String friendlyName, ListValidator validator);

    /**
     * A validator to evaluate against a list property (ReadOnlyObservableList). A ListValidator returns a single validation message.
     * @param name property name
     * @param friendlyName Friendly name of property
     * @param validator a list validator
     * @return returns a view model.
     * @param <T> Returns the current ViewModel
     */
    default <T extends ViewModel> T addValidator(Enum name, String friendlyName, ListValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    /**
     * A validator to evaluate against a Set (ReadOnlySetProperty). A SetValidator returns a single validation message.
     *
     * @param name         Property name
     * @param friendlyName A string of the validation message
     * @param validator    Validator based on datatype set
     * @param <T>          Any class derived from a ViewModel
     * @return ViewModel itself.
     */
    <T extends ViewModel> T addValidator(String name, String friendlyName, SetValidator validator);

    /**
     * A validator to evaluate against a custom property (ViewModel). A CustomValidator returns a single validation message.
     *
     * @param name         Property name
     * @param friendlyName A string of the validation message
     * @param validator    Validator based on view model.
     * @param <T>          Any class derived from a ViewModel
     * @return ViewModel itself.
     */
    <T extends ViewModel> T addValidator(String name, String friendlyName, CustomValidator validator);

    /**
     * A CustomValidator to evaluate against the current ViewModel. A CustomValidator returns a single validation message.
     *
     * @param name         Property name
     * @param friendlyName A string of the validation message
     * @param validator    Validator based on datatype
     * @param <T>          Any class derived from a ViewModel
     * @return ViewModel itself.
     */
    default <T extends ViewModel> T addValidator(Enum name, String friendlyName, CustomValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the boolean property is passed in to be used.
     *
     * @param name         Property name
     * @param friendlyName Friendly name of property name
     * @param validator    consumer validator converts to an actual type validator.
     *                     e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @param <T>          U is a derived class of type ViewModel.
     * @return returns itself following the builder pattern.
     */
    default <T extends ViewModel> T addValidator(String name, String friendlyName, BooleanConsumerValidator validator) {
        BooleanValidator tValidator = (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
        getValidationManager().createFieldValidator(name, friendlyName, tValidator);
        return (T) this;
    }

    /**
     * A validator will create a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the boolean property is passed in to be used.
     *
     * @param name         Property name
     * @param friendlyName Friendly name of property name
     * @param validator    consumer validator converts to an actual type validator.
     *                     e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @param <T>          U is a derived class of type ViewModel.
     * @return returns itself following the builder pattern.
     */
    default <T extends ViewModel> T addValidator(Enum name, String friendlyName, BooleanConsumerValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }
    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the double property is passed in to be used.
     *
     * @param name         Property name
     * @param friendlyName Friendly name of property name
     * @param validator    consumer validator converts to an actual type validator.
     *                     e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @param <T>          U is a derived class of type ViewModel.
     * @return returns itself following the builder pattern.
     */
    default <T extends ViewModel> T addValidator(String name, String friendlyName, DoubleConsumerValidator validator) {
        DoubleValidator tValidator = (ReadOnlyDoubleProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
        getValidationManager().createFieldValidator(name, friendlyName, tValidator);
        return (T) this;
    }

    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the double property is passed in to be used.
     *
     * @param name         Property name
     * @param friendlyName Friendly name of property name
     * @param validator    consumer validator converts to an actual type validator.
     *                     e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @param <T>          U is a derived class of type ViewModel.
     * @return returns itself following the builder pattern.
     */
    default <T extends ViewModel> T addValidator(Enum name, String friendlyName, DoubleConsumerValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the float property is passed in to be used.
     *
     * @param name         Property name
     * @param friendlyName Friendly name of property name
     * @param validator    consumer validator converts to an actual type validator.
     *                     e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @param <T>          U is a derived class of type ViewModel.
     * @return returns itself following the builder pattern.
     */
    default <T extends ViewModel> T addValidator(String name, String friendlyName, FloatConsumerValidator validator) {
        FloatValidator tValidator = (ReadOnlyFloatProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
        getValidationManager().createFieldValidator(name, friendlyName, tValidator);
        return (T) this;
    }

    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the float property is passed in to be used.
     *
     * @param name         Property name
     * @param friendlyName Friendly name of property name
     * @param validator    consumer validator converts to an actual type validator.
     *                     e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @param <T>          U is a derived class of type ViewModel.
     * @return returns itself following the builder pattern.
     */
    default <T extends ViewModel> T addValidator(Enum name, String friendlyName, FloatConsumerValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the integer property is passed in to be used.
     *
     * @param name         Property name
     * @param friendlyName Friendly name of property name
     * @param validator    consumer validator converts to an actual type validator.
     *                     e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @param <T>          U is a derived class of type ViewModel.
     * @return returns itself following the builder pattern.
     */
    default <T extends ViewModel> T addValidator(String name, String friendlyName, IntegerConsumerValidator validator) {
        IntegerValidator tValidator = (ReadOnlyIntegerProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
        getValidationManager().createFieldValidator(name, friendlyName, tValidator);
        return (T) this;
    }

    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the integer property is passed in to be used.
     *
     * @param name         Property name
     * @param friendlyName Friendly name of property name
     * @param validator    consumer validator converts to an actual type validator.
     *                     e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @param <T>          U is a derived class of type ViewModel.
     * @return returns itself following the builder pattern.
     */
    default <T extends ViewModel> T addValidator(Enum name, String friendlyName, IntegerConsumerValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the list as a read-only property is passed in to be used.
     *
     * @param name         Property name
     * @param friendlyName Friendly name of property name
     * @param validator    consumer validator converts to an actual type validator.
     *                     e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @param <T>          T is a derived class of type ViewModel.
     *
     * @return returns itself following the builder pattern.
     */
    default <T extends ViewModel> T addValidator(String name, String friendlyName, ListConsumerValidator validator) {
        ListValidator tValidator = (ObservableList<?> prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
        getValidationManager().createFieldValidator(name, friendlyName, tValidator);
        T t = (T) this;
        return t;
    }

    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the list as a read-only property is passed in to be used.
     *
     * @param name         Property name
     * @param friendlyName Friendly name of property name
     * @param validator    consumer validator converts to an actual type validator.
     *                     e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @param <T>          U is a derived class of type ViewModel.
     * @return returns itself following the builder pattern.
     */
    default <T extends ViewModel> T addValidator(Enum name, String friendlyName, ListConsumerValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }
    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the Long as a read-only property is passed in to be used.
     *
     * @param name         Property name
     * @param friendlyName Friendly name of property name
     * @param validator    consumer validator converts to an actual type validator.
     *                     e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @param <T>          U is a derived class of type ViewModel.
     * @return returns itself following the builder pattern.
     */
    default <T extends ViewModel> T addValidator(String name, String friendlyName, LongConsumerValidator validator) {
        LongValidator tValidator = (ReadOnlyLongProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
        getValidationManager().createFieldValidator(name, friendlyName, tValidator);
        return (T) this;
    }

    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the Long as a read-only property is passed in to be used.
     *
     * @param name         Property name
     * @param friendlyName Friendly name of property name
     * @param validator    consumer validator converts to an actual type validator.
     *                     e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @param <T>          U is a derived class of type ViewModel.
     * @return returns itself following the builder pattern.
     */
    default <T extends ViewModel> T addValidator(Enum name, String friendlyName, LongConsumerValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }
    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the object as a read-only property is passed in to be used.
     *
     * @param name         Property name
     * @param friendlyName Friendly name of property name
     * @param validator    consumer validator converts to an actual type validator.
     *                     e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @param <T>          U is a derived class of type ViewModel.
     * @return returns itself following the builder pattern.
     */
    default <T extends ViewModel> T addValidator(String name, String friendlyName, ObjectConsumerValidator validator) {
        ObjectValidator tValidator = (ReadOnlyObjectProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
        getValidationManager().createFieldValidator(name, friendlyName, tValidator);
        return (T) this;
    }

    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the object as a read-only property is passed in to be used.
     *
     * @param name         Property name
     * @param friendlyName Friendly name of property name
     * @param validator    consumer validator converts to an actual type validator.
     *                     e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @param <T>          U is a derived class of type ViewModel.
     * @return returns itself following the builder pattern.
     */
    default <T extends ViewModel> T addValidator(Enum name, String friendlyName, ObjectConsumerValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the set as a read-only property is passed in to be used.
     *
     * @param name         Property name
     * @param friendlyName Friendly name of property name
     * @param validator    consumer validator converts to an actual type validator.
     *                     e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @param <T>          U is a derived class of type ViewModel.
     * @return returns itself following the builder pattern.
     */
    default <T extends ViewModel> T addValidator(String name, String friendlyName, SetConsumerValidator validator) {
        SetValidator tValidator = (ObservableSet<?> prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
        getValidationManager().createFieldValidator(name, friendlyName, tValidator);
        return (T) this;
    }

    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the set as a read-only property is passed in to be used.
     *
     * @param name         Property name
     * @param friendlyName Friendly name of property name
     * @param validator    consumer validator converts to an actual type validator.
     *                     e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @param <T>          T is a derived class of type ViewModel.
     * @return returns itself following the builder pattern.
     */
    default <T extends ViewModel> T addValidator(Enum name, String friendlyName, SetConsumerValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }
    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the string as a read-only property is passed in to be used.
     *
     * @param name         Property name
     * @param friendlyName Friendly name of property name
     * @param validator    consumer validator converts to an actual type validator.
     *                     e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @param <T>          U is a derived class of type ViewModel.
     * @return returns itself following the builder pattern.
     */
    default <T extends ViewModel> T addValidator(String name, String friendlyName, StringConsumerValidator validator) {
        StringValidator stringValidator = (ReadOnlyStringProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
        getValidationManager().createFieldValidator(name, friendlyName, stringValidator);
        return (T) this;
    }

    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual typed Validator.
     * As a convenience the string as a read-only property is passed in to be used.
     *
     * @param name         Property name
     * @param friendlyName Friendly name of property name
     * @param validator    consumer validator converts to an actual type validator.
     *                     e.g. BooleanValidator (ReadOnlyBooleanProperty prop, ViewModel vm) -> accumulateMessagesFunction(name, validator, prop);
     * @param <T>          T is a derived class of type ViewModel.
     * @return returns itself following the builder pattern.
     */
    default <T extends ViewModel> T addValidator(Enum name, String friendlyName, StringConsumerValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }

    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual custom validator.
     *
     * @param name         Property name
     * @param friendlyName Friendly name of property name
     * @param validator    consumer validator converts to an actual type validator.
     *                     e.g. CustomValidator customValidator = (Void prop, ViewModel vm) -> accumulateMessagesFunction(name, validator);
     * @param <T>          T is a derived class of type ViewModel.
     * @return returns itself following the builder pattern.
     */
    default <T extends ViewModel> T addValidator(String name, String friendlyName, ConsumerValidator validator) {
        CustomValidator customValidator = (Void prop, ViewModel vm) -> accumulateMessagesFunction(name, validator);
        getValidationManager().createFieldValidator(name, friendlyName, customValidator);
        return (T) this;
    }

    /**
     * A consumer validator creates a validator that allows the code to eval validation logic and can generate multiple error messages.
     * This consumer validator is converted to an actual custom validator.
     *
     * @param name         Property name
     * @param friendlyName Friendly name of property name
     * @param validator    consumer validator converts to an actual type validator.
     *                     e.g. CustomValidator customValidator = (Void prop, ViewModel vm) -> accumulateMessagesFunction(name, validator);
     * @param <T>          T is a derived class of type ViewModel.
     * @return returns itself following the builder pattern.
     */
    default <T extends ViewModel> T addValidator(Enum name, String friendlyName, ConsumerValidator validator) {
        return addValidator(name.toString(), friendlyName, validator);
    }
    /**
     * Goes through all validators to execute creating error, warning, info
     *
     * @param <T> T is a view model of type ViewModel
     * @return Returns a ViewModel following the builder pattern.
     */
    <T extends ViewModel> T validate();

    /**
     * Returns all validators based on property name
     * @param name property name
     * @return List of validators
     */
    default List<Validator<?, ViewModel>> getValidators(String name) {
        return (List<Validator<?, ViewModel>>) getValidationManager().getValidators(name);
    }

    /**
     * Returns a list of validators referencing an Enum property name.
     * @param name An Enum property name.
     * @return Returns a list of validators referencing an Enum property name.
     */
    default List<Validator<?, ViewModel>> getValidators(Enum name) {
        return getValidators(name.toString());
    }
    /**
     * Returns all validation messages.
     *
     * @return A list of validation messages.
     */
    default List<ValidationMessage> getValidationMessages() {
        return getValidationManager().getValidationMessages();
    }

    /**
     * True is returned if there are error messages otherwise false.
     *
     * @return True is returned if there are error messages otherwise false.
     */
    default boolean hasErrorMsgs() {
        return getValidationManager().hasMsgType(MessageType.ERROR);
    }

    /**
     * True is returned if there are no error messages otherwise false.
     *
     * @return True is returned if there are no error messages otherwise false.
     */
    default boolean hasNoErrorMsgs() {
        return getValidationManager().hasNoErrorMsgs();
    }

    /**
     * True is returned if there are warning messages otherwise false.
     *
     * @return True is returned if there are warning messages otherwise false.
     */
    default boolean hasWarningMsgs() {
        return getValidationManager().hasWarningMsgs();
    }

    /**
     * True is returned if there are no warning messages otherwise false.
     *
     * @return True is returned if there are no warning messages otherwise false.
     */
    default boolean hasNoWarningMsgs() {
        return getValidationManager().hasNoWarningMsgs();
    }

    /**
     * True is returned if there are info messages otherwise false.
     *
     * @return True is returned if there are info messages otherwise false.
     */
    default boolean hasInfoMsgs() {
        return getValidationManager().hasInfoMsgs();
    }

    /**
     * True is returned if there are no info messages otherwise false.
     *
     * @return True is returned if there are no info messages otherwise false.
     */
    default boolean hasNoInfoMsgs() {
        return getValidationManager().hasNoInfoMsgs();
    }


    /**
     * Invalidate or clear error messages.
     *
     * @param <T> Derived ViewModel
     * @return itself
     */
    <T extends ViewModel> T invalidate();

    /**
     * Returns friendly name of property name.
     *
     * @param propertyName property name
     * @return friendly name
     */
    default String getFriendlyName(String propertyName) {
        return getValidationManager().getFriendlyName(propertyName);
    }

    /**
     * Returns friendly name of property name.
     *
     * @param propertyName An Enum property name
     * @return A friendly name representing the property. Used for error messages.
     */
    default String getFriendlyName(Enum propertyName) {
        return getFriendlyName(propertyName.name());
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
     * @param prop the observable set
     * @param <E> the type of object in set.
     * @return ValidationMessage will be null or VALID. Messages will be in validation result.
     */
    default <E> ValidationMessage accumulateMessagesFunction(String name, TypeConsumerValidator validator, ObservableSet<E> prop) {
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
     * @param prop the observable list
     * @param <E> the type of object in list.
     * @return ValidationMessage will be null or VALID. Messages will be in validation result.
     */
    default <E> ValidationMessage accumulateMessagesFunction(String name, TypeConsumerValidator validator, ObservableList<E> prop) {
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
     * @param prop the property as a read-only
     * @return ValidationMessage will be null or VALID. Messages will be in validation result.
     */
    default ValidationMessage accumulateMessagesFunction(Enum name, TypeConsumerValidator validator, ReadOnlyProperty prop) {
        return accumulateMessagesFunction(name.toString(), validator, prop);
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

    /**
     * Based on the property name, consumer validator will pass caller an empty validation result (no messages).
     * @param name Property name
     * @param validator Consumer validator allows caller to add messages to validation result object.
     * @return ValidationMessage will be null or VALID. Messages will be in validation result.
     */
    default ValidationMessage accumulateMessagesFunction(Enum name, ConsumerValidator validator) {
        return accumulateMessagesFunction(name.toString(), validator);
    }
    /**
     * True will force a save and returns the view model itself following the builder pattern.
     * The validation does not occur, property values copied to model values. false will call regular save().
     *
     * @param force True will copy property values over to model values regardless of validators.
     * @param <T> Any derived ViewModel class type.
     * @return Returns a ValidationViewModel (itself) following the builder pattern.
     */
    <T extends ViewModel> T save(boolean force);

    /**
     * Returns a read only boolean property of the valid property. True if there are 0 messages.
     * @return read only boolean property of the valid property.
     */
    default ReadOnlyBooleanProperty validProperty() {
        return getValidationManager().validProperty();
    }

    /**
     * Returns a read only boolean property of the invalid property. True if there are more than 0 messages.
     * @return read only boolean property of the invalid property.
     */
    default ReadOnlyBooleanProperty invalidProperty() {
        return getValidationManager().invalidProperty();
    }

}
