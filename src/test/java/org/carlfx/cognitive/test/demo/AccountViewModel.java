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

import static org.carlfx.cognitive.test.demo.AccountViewModel.AccountField.*;

public class AccountViewModel extends ValidationViewModel {
    public enum AccountField {
        FIRST_NAME("First Name"),
        LAST_NAME("Last Name"),
        EMAIL("Email"),
        TRANSACTION_TEXT("Transaction Text"),
        FIRST_NAME_ERROR,
        LAST_NAME_ERROR,
        EMAIL_ERROR,
        ;

        public final String name;
        AccountField(String name){
            this.name = name;
        }
        AccountField(){
            this.name = this.name();
        }
    }

    public AccountViewModel () {
        addProperty(FIRST_NAME, "")
                .addValidator(FIRST_NAME, FIRST_NAME.name, (ReadOnlyStringProperty prop, ValidationResult validationResult, ViewModel vm) -> {
                    if (prop.isEmpty().get()) {
                        validationResult.error("${%s} is required".formatted(FIRST_NAME));
                    }
                })
                .addValidator(FIRST_NAME, FIRST_NAME.name, (ReadOnlyStringProperty prop, ValidationResult validationResult, ViewModel viewModel) -> {
                    if (prop.isNotEmpty().get()) {
                        // check first character
                        String firstChar = String.valueOf(prop.get().charAt(0));
                        if (firstChar.equals(firstChar.toLowerCase())) {
                            validationResult.error("${%s} first character must be upper case.".formatted(FIRST_NAME));
                        }
                        // check minimum number of characters
                        if (prop.get().length() < 4){
                            validationResult.error("${%s} must be greater than 3 characters.".formatted(FIRST_NAME));
                        }
                    }
                });

        addProperty(LAST_NAME, "")
                .addValidator(LAST_NAME, LAST_NAME.name, (ReadOnlyStringProperty prop, ValidationResult validationResult, ViewModel vm) -> {
                    if (prop.isEmpty().get()) {
                        validationResult.error("${%s} is required".formatted(LAST_NAME));
                    }
                    if (prop.isNotEmpty().get()) {
                        // check first character
                        String firstChar = String.valueOf(prop.get().charAt(0));
                        if (firstChar.equals(firstChar.toLowerCase())) {
                            validationResult.error("${%s} first character must be upper case.".formatted(LAST_NAME));
                        }
                        // check minimum number of characters
                        if (prop.get().length() ==1){
                            validationResult.error("${%s} must be greater than 1 character.".formatted(LAST_NAME));
                        }
                    }
                });

        addProperty(EMAIL, "")
                .addValidator(EMAIL, EMAIL.name, (ReadOnlyStringProperty prop, ViewModel vm) -> {
                    String email = prop.get();
                    Pattern pattern = Pattern.compile("([0-9 | a-z | A-Z | - | \\.]*\\@.*\\.[0-9 | - | a-z]{2,})");
                    Matcher matcher = pattern.matcher(email);
                    if (!matcher.matches()) {
                        return new ValidationMessage(EMAIL, MessageType.ERROR, "${%s} must be formatted abc@efg.com. Entered as %s".formatted(EMAIL, email));
                    }
                    return VALID;
                });

//        onValidProperty();
//        onErrorProperty();
//        onWarningProperty();
//        onInfoProperty();
//        ifValidOrElse(() -> {
//            //
//        }, () -> {
//
//        });
//        ifValid(() -> {
//
//        });


        addProperty(TRANSACTION_TEXT, "");
        addProperty(FIRST_NAME_ERROR, "");
        addProperty(LAST_NAME_ERROR, "");
        addProperty(EMAIL_ERROR, "");
    }


    public void updateErrors(ValidationMessage validationMessage) {
        setPropertyValue(validationMessage.propertyName() + "_ERROR", validationMessage.interpolate(this));
    }
}
