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

    @Override
    public ValidationViewModel addProperty(String name, Property property) {
        return super.addProperty(name, property);
    }

    @Override
    public ValidationViewModel addProperty(String name, String value) {
        return super.addProperty(name, value);
    }

    @Override
    public ValidationViewModel addProperty(String name, int value) {
        return super.addProperty(name, value);
    }

    @Override
    public ValidationViewModel addProperty(String name, long value) {
        return super.addProperty(name, value);
    }

    @Override
    public ValidationViewModel addProperty(String name, float value) {
        return super.addProperty(name, value);
    }

    @Override
    public ValidationViewModel addProperty(String name, double value) {
        return super.addProperty(name, value);
    }

    @Override
    public ValidationViewModel addProperty(String name, boolean value) {
        return super.addProperty(name, value);
    }

    @Override
    public ValidationViewModel addProperty(String name, Collection value) {
        return super.addProperty(name, value);
    }

    @Override
    public ValidationViewModel addProperty(String name, Object value) {
        return super.addProperty(name, value);
    }
    public ValidationViewModel setPropertyValues(String name, Collection values, boolean skip){
        return (ValidationViewModel) super.setPropertyValues(name, values, skip);
    }
    @Override
    public ValidationViewModel setPropertyValue(String name, Object value, boolean skip) {
        return (ValidationViewModel) super.setPropertyValue(name, value, skip);
    }

    @Override
    public ValidationViewModel reset() {
        return (ValidationViewModel) super.reset();
    }

    @Override
    public ValidationViewModel setValue(String name, Object value) {
        return (ValidationViewModel) super.setValue(name, value);
    }

    @Override
    public ValidationViewModel setPropertyValue(String name, Object value) {
        return (ValidationViewModel) super.setPropertyValue(name, value);
    }

    @Override
    public ValidationViewModel setPropertyValues(String name, Collection values) {
        return (ValidationViewModel)super.setPropertyValues(name, values);
    }

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
    protected  <U extends ValidationViewModel, T extends Validator> U createGlobalValidator(int errorCode, String name, String errorMsg, T validator) {
        validatorsMap.putIfAbsent(name, new ArrayList());
        List validators = validatorsMap.get(name);
        validators.add(validator);
        return (U) this;
    }
    public <U extends ValidationViewModel> U addValidator(String name, String friendlyName, StringValidator validator) {
        return createFieldValidator(name, friendlyName, validator);
    }
    public <U extends ValidationViewModel> U addValidator(String name, String friendlyName, BooleanValidator validator) {
        return createFieldValidator(name, friendlyName, validator);
    }
    public <U extends ValidationViewModel> U addValidator(String name, String friendlyName, IntegerValidator validator) {
        return createFieldValidator(name, friendlyName, validator);
    }
    public <U extends ValidationViewModel> U addValidator(String name, String friendlyName, LongValidator validator) {
        return createFieldValidator(name, friendlyName, validator);
    }
    public <U extends ValidationViewModel> U addValidator(String name, String friendlyName, FloatValidator validator) {
        return createFieldValidator(name, friendlyName, validator);
    }
    public <U extends ValidationViewModel> U addValidator(String name, String friendlyName, DoubleValidator validator) {
        return createFieldValidator(name, friendlyName, validator);
    }
    public <U extends ValidationViewModel> U addValidator(String name, String friendlyName, ObjectValidator validator) {
        return createFieldValidator(name, friendlyName, validator);
    }

    public <U extends ValidationViewModel> U addValidator(String name, String friendlyName, ListValidator validator) {
        return createFieldValidator(name, friendlyName, validator);
    }

    public <U extends ValidationViewModel> U addValidator(String name, String friendlyName, SetValidator validator) {
        return createFieldValidator(name, friendlyName, validator);
    }

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

    public <T extends Validator<U>, U> List<T> getValidators(String name) {
        return (List<T>)validatorsMap.get(name);
    }
    public List<ValidationMessage> getValidationMessages() {
        return validationMessages;
    }

    public boolean hasErrorMsgs() {
        return hasMsgType(ERROR);
    }
    public boolean hasNoErrorMsgs() {
        return !hasErrorMsgs();
    }
    public boolean hasWarningMsgs() {
        return hasMsgType(WARN);
    }
    public boolean hasNoWarningMsgs() {
        return !hasWarningMsgs();
    }
    public boolean hasInfoMsgs() {
        return hasMsgType(INFO);
    }
    public boolean hasNoInfoMsgs() {
        return !hasInfoMsgs();
    }

    private boolean hasMsgType(MessageType type){
        if (getValidationMessages() != null) {
            return getValidationMessages().stream().filter(msg -> msg.messageType() == type).findAny().isPresent();
        }
        return false;
    }
    public <U extends ValidationViewModel> U invalidate() {
        getValidationMessages().clear();
        return (U) this;
    }

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
    @Override
    public ValidationViewModel save() {
        // validate if any are errors, don't save.
        validate();
        if (getValidationMessages().stream().filter(validationMessage -> validationMessage.messageType() == ERROR).findAny().isPresent()) {
            return this;
        }

        return (ValidationViewModel) super.save();
    }

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
