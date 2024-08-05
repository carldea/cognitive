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
package org.carlfx.cognitive.test.demo;

import javafx.beans.property.ReadOnlyStringProperty;
import org.carlfx.cognitive.validator.MessageType;
import org.carlfx.cognitive.validator.ValidationMessage;
import org.carlfx.cognitive.validator.ValidationResult;
import org.carlfx.cognitive.viewmodel.ValidationViewModel;
import org.carlfx.cognitive.viewmodel.ViewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountViewModel extends ValidationViewModel {
    public final static String FIRST_NAME = "firstName";
    public final static String LAST_NAME = "lastName";
    public final static String EMAIL = "email";
    public final static String TRANSACTION_TEXT = "transactionText";

    public static String IS_NOT_POPULATED = "isNotPopulated";
    public static String SUBMIT_BUTTON_STATE = "buttonState";


    public final static String ERROR = "error";
    public final static String FIRST_NAME_ERROR = FIRST_NAME + ERROR;
    public final static String LAST_NAME_ERROR = LAST_NAME + ERROR;
    public final static String EMAIL_ERROR = EMAIL + ERROR;

    public AccountViewModel () {
        addProperty(FIRST_NAME, "")
                .addValidator(FIRST_NAME, "First Name", (ReadOnlyStringProperty prop, ViewModel vm) -> {
                    if (prop.isEmpty().get()) {
                        return new ValidationMessage(FIRST_NAME, MessageType.ERROR, "${%s} is required".formatted(FIRST_NAME));
                    }
                    return VALID;
                })
                .addValidator(FIRST_NAME, "First Name", (ReadOnlyStringProperty prop, ValidationResult validationResult, ViewModel viewModel) -> {
                    if (prop.isEmpty().get() || prop.isNotEmpty().get() && prop.get().length() < 3) {
                        validationResult.error(FIRST_NAME, "${%s} must be greater than 3 characters.".formatted(FIRST_NAME));
                    }
                    String firstChar = String.valueOf(prop.get().charAt(0));
                    if (firstChar.equals(firstChar.toLowerCase())) {
                        validationResult.error(FIRST_NAME, "${%s} first character must be upper case.".formatted(FIRST_NAME));
                    }
                });

        addProperty(LAST_NAME, "")
                .addValidator(LAST_NAME, "Last Name", (ReadOnlyStringProperty prop, ViewModel vm) -> {
                    if (prop.isEmpty().get()) {
                        return new ValidationMessage(LAST_NAME, MessageType.ERROR, "${%s} is required".formatted(LAST_NAME));
                    }
                    return VALID;
                });

        addProperty(EMAIL, "")
                .addValidator(EMAIL, "Email", (ReadOnlyStringProperty prop, ViewModel vm) -> {
                    String email = prop.get();
                    Pattern pattern = Pattern.compile("([0-9 | a-z | - | \\.]*\\@.*\\.[0-9 | - | a-z]{2,})");
                    Matcher matcher = pattern.matcher(email);
                    if (!matcher.matches()) {
                        return new ValidationMessage(EMAIL, MessageType.ERROR, "${%s} must be formatted abc@efg.com. Entered as %s".formatted(EMAIL, email));
                    }
                    return VALID;
                });

        addProperty(SUBMIT_BUTTON_STATE, true);        // disable property (true) by default
        addValidator(IS_NOT_POPULATED, "Is Not Populated", (Void prop, ViewModel vm) -> {
            // if any fields are empty then it is not populated (invalid)
            if (vm.getPropertyValue(FIRST_NAME).toString().isBlank()
                    || vm.getPropertyValue(LAST_NAME).toString().isBlank()
                    || vm.getPropertyValue(EMAIL).toString().isBlank()) {

                // update is populated
                vm.setPropertyValue(SUBMIT_BUTTON_STATE, true); // disable is true
                // let caller know why it is not valid
                return new ValidationMessage(SUBMIT_BUTTON_STATE, MessageType.ERROR, "Account form is not populated");
            }
            // update is populated
            vm.setPropertyValue(SUBMIT_BUTTON_STATE, false); // disable is false (enabled)
            return VALID;
        });
        addProperty(TRANSACTION_TEXT, "");

        addProperty(FIRST_NAME_ERROR, "");
        addProperty(LAST_NAME_ERROR, "");
        addProperty(EMAIL_ERROR, "");

    }
    public void updateErrors(ValidationMessage validationMessage) {
        setPropertyValue(validationMessage.propertyName() + ERROR, validationMessage.interpolate(this));
    }
}
