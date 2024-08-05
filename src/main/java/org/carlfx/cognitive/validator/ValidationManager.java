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
package org.carlfx.cognitive.validator;

import org.carlfx.cognitive.viewmodel.ViewModel;

import java.util.*;

/**
 *  A validation manager is responsible for evaluating validators and producing validation messages.
 *  Each validation messages can be warnings, errors, and info type messages. Validation messages are considered global or field based.
 *  When used in a field in a form-based UI messages can be applied beside UI control to provide the user feedback.
 *
 *  When creating validators validation messages can be more human-readable by using and substituting the property name with a friendly name.
 *
 * @see org.carlfx.cognitive.viewmodel.IdValidationViewModel
 * @see org.carlfx.cognitive.viewmodel.ValidationViewModel
 */
public class ValidationManager {
    private final Map<String, String> friendlyNameMap = new LinkedHashMap<>();
    private Map<String, List<Validator<?, ViewModel>>> validatorsMap = new LinkedHashMap<>();
    private Set<String> triggerValidatorSet = new HashSet<>();

    private List<ValidationMessage> validationMessages = new ArrayList<>();

    /**
     * Default constructor.
     */
    public ValidationManager() {
        super();
    }
    /**
     * Allows derived classes to create field (property) validators.
     * @param name Property name
     * @param friendlyName Friendly name of field
     * @param validator Validator based on datatype
     * @return ValidationViewModel itself.
     */
    public Validator<?, ViewModel> createFieldValidator(String name, String friendlyName, Validator<?, ViewModel> validator) {
        String alreadyName = friendlyNameMap.get(name);
        if ((alreadyName != null && !alreadyName.isBlank() && (friendlyName == null || friendlyName.isBlank()))) {
            // ignore the caller's friendly name as null or empty
        } else {
            friendlyNameMap.putIfAbsent(name, friendlyName);
        }
        validatorsMap.putIfAbsent(name, new ArrayList());
        List<Validator<?, ViewModel>> validators = validatorsMap.get(name);
        validators.add(validator);
        return validator;
    }

    /**
     * Allows derived classes to create global type validators. e.g. saving to database a duplicate record.
     * @param errorCode an error code assigned to error message.
     * @param name Property name
     * @param errorMsg A string of the validation message
     * @param validator Validator based on datatype
     * @return ValidationViewModel itself.
     * @param <T> Any class derived from a Validator
     */
    public  <T extends Validator> Validator createGlobalValidator(int errorCode, String name, String errorMsg, T validator) {
        validatorsMap.putIfAbsent(name, new ArrayList());
        List validators = validatorsMap.get(name);
        validators.add(validator);
        return validator;
    }

    public void validateOnChange(boolean triggerValidator, String ...propertyNames) {
        if (triggerValidator) {
            if (propertyNames != null) {
                triggerValidatorSet.addAll(Arrays.stream(propertyNames).toList());
            }
        } else {
            triggerValidatorSet.removeAll(Arrays.stream(propertyNames).toList());
        }
    }
    public boolean shouldValidateOnChange(String propertyName) {
        return triggerValidatorSet.contains(propertyName);
    }

    /**
     * Returns all validators based on property name
     * @param name property name
     * @return List of validators
     */
    public List<Validator<?, ViewModel>> getValidators(String name) {
        return validatorsMap.get(name);
    }
    /**
     * Invalidate or clear all validation messages.
     *
     */
    public void invalidate() {
        getValidationMessages().clear();
    }

    /**
     * Returns a list of ValidationMessage objects after validation occurs on validators.
     * @param viewModel A view model to validate against.
     * @return Returns a list of ValidationMessage objects after validation occurs on validators.
     */
    public List<ValidationMessage> validate(ViewModel viewModel) {
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
                    case BooleanValidator boolValidator -> boolValidator.apply(viewModel.getProperty(name), viewModel);
                    case CustomValidator customValidator -> customValidator.apply(null, viewModel);
                    case DoubleValidator doubleValidator -> doubleValidator.apply(viewModel.getProperty(name), viewModel);
                    case FloatValidator floatValidator -> floatValidator.apply(viewModel.getProperty(name), viewModel);
                    case IntegerValidator intValidator -> intValidator.apply(viewModel.getProperty(name), viewModel);
                    case ListValidator listValidator -> listValidator.apply(viewModel.getProperty(name), viewModel);
                    case LongValidator longValidator -> longValidator.apply(viewModel.getProperty(name), viewModel);
                    case ObjectValidator objValidator -> objValidator.apply(viewModel.getProperty(name), viewModel);
                    case SetValidator setValidator -> setValidator.apply(viewModel.getProperty(name), viewModel);
                    case StringValidator stringValidator -> stringValidator.apply(viewModel.getProperty(name), viewModel);
                    default -> throw new IllegalStateException("Unexpected value: " + validator);
                };

                return validationMessage;
            }).filter( vMsg -> vMsg != null).toList());
        });
        return validationMessages;
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
        return hasMsgType(MessageType.ERROR);
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
        return hasMsgType(MessageType.WARN);
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
        return hasMsgType(MessageType.INFO);
    }

    /**
     * True is returned if there are no info messages otherwise false.
     * @return True is returned if there are no info messages otherwise false.
     */
    public boolean hasNoInfoMsgs() {
        return !hasInfoMsgs();
    }


    /**
     * Returns true when validation messages exist of type <code>MessageType</code> (error, warn, info), otherwise false.
     * @param type The type can be an error, warning or information message type.
     * @return Returns true when validation messages exist of type <code>MessageType</code> (error, warn, info), otherwise false.
     */
    public boolean hasMsgType(MessageType type){
        if (getValidationMessages() != null) {
            return getValidationMessages().stream().filter(msg -> msg.messageType() == type).findAny().isPresent();
        }
        return false;
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
     * Returns a map of property names to friendly names.
     * @return a map of property names to friendly names.
     */
    public Map<String, String> getFriendlyNameMap() {
        return friendlyNameMap;
    }
}
