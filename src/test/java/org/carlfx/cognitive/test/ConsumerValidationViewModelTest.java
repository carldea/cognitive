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
package org.carlfx.cognitive.test;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.ReadOnlyFloatProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import org.carlfx.cognitive.validator.ValidationMessage;
import org.carlfx.cognitive.validator.ValidationResult;
import org.carlfx.cognitive.viewmodel.ValidationViewModel;
import org.carlfx.cognitive.viewmodel.ViewModel;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This tests methods of addValidator() taking consumer validators. Consumer type validators will allow developer to do validation to add multiple error, warn, and info messages.
 * This cuts code down and allows an adhoc approach to validating fields and custom validation.
 */
public class ConsumerValidationViewModelTest {
    public static void main(String[] args){
        final String FIRST_NAME = "firstName";
        final String AGE = "age";
        final String PHONE = "phone";
        final String HEIGHT = "height";
        final String COLORS = "colors";
        final String FOODS = "foods";
        final String THING = "thing";
        final String MPG = "mpg";
        final String CUSTOM_PROP = "customProp";

        ValidationViewModel personVm = new ValidationViewModel()
                .addProperty(FIRST_NAME, "")
                .addValidator(FIRST_NAME, "First Name", (ReadOnlyStringProperty prop, ValidationResult vr, ViewModel vm) -> {
                    if (prop.isEmpty().get()) {
                        vr.error("${%s} is required".formatted(FIRST_NAME));
                    }
                })
                .addValidator(FIRST_NAME, "First Name", (ReadOnlyStringProperty prop, ValidationResult vr, ViewModel vm) -> {
                    String value = prop.get();
                    if (value.length() < 3) {
                        vr.error("${%s} must be greater than 2 characters.".formatted(FIRST_NAME));
                        vr.warn("${%s} must be greater than 2 characters.".formatted(FIRST_NAME));
                    }
                    if (value.length() == 0) {
                        vr.info("${%s} must not be blank.".formatted(FIRST_NAME));
                    }
                })
                .addProperty(PHONE, "111-1111111")
                .addValidator(PHONE, "Phone Number", (ReadOnlyStringProperty prop, ValidationResult vr, ViewModel vm) -> {
                    String ph = prop.get();
                    Pattern pattern = Pattern.compile("([0-9]{3}\\-[0-9]{3}\\-[0-9]{4})");
                    Matcher matcher = pattern.matcher(ph);
                    if (!matcher.matches()) {
                        vr.error("${%s} must be formatted XXX-XXX-XXXX. Entered as %s".formatted(PHONE, ph));
                    }
                })
                .addProperty("age", 54l)
                .addProperty("height", 11)
                .addValidator(HEIGHT, "Height", (ReadOnlyIntegerProperty prop, ValidationResult vr, ViewModel vm) -> {
                    if (prop.get() < 1 || prop.get() > 10) {
                        vr.error("${%s} must be in range 1-10. Entered as %s ".formatted(HEIGHT, prop.get()));
                    }
                })
                .addProperty("colors", Set.of("red", "blue"))
                .setPropertyValues("foods", List.of("bbq", "chips", "bbq"), true)
                .addProperty("thing", new Object(){
                    @Override
                    public String toString() {
                        return "thing ";
                    }
                })
                .addProperty(MPG, 20.5f)
                .addValidator(MPG, "Miles Per Gallon", (ReadOnlyFloatProperty prop, ValidationResult vr, ViewModel vm) -> {
                    if (prop.get() < 1.0 || prop.get() > 20) {
                        vr.error("${%s} must be in range 1.0 - 20.0. Entered as %s ".formatted(MPG, prop.get()));
                    }
                })
                .addValidator(CUSTOM_PROP, "Custom Prop", (ValidationResult vr, ViewModel vm) -> {
                    FloatProperty mpg = vm.getProperty(MPG);

                    if (mpg.get() < 1.0 || mpg.get() > 20) {
                        vr.error("${%s} must be in range 1.0 - 20.0. Entered as %s ".formatted(CUSTOM_PROP, mpg.get()));
                    }
                });
//        personVm.onChange(true, FIRST_NAME, PHONE);
//        personVm.onChange(HEIGHT).dependsOn(FIRST_NAME, PHONE);
//        personVm.onChange(a, b)
//                .invoke(()-> {
//
//                });
        // list of properties attach invalidation listener above for each.
        /*
        a + b = c
        a -> firstname v1
        b -> lastname
        c -> validate

        // evaluate c any time a or b has changed.
        vm.validate(c).dependingOn(a, b);

        // invoke code any time a or b has changed.
        vm.invoke(()-> {}).dependingOn(a, b);
        vm.onChange(a, b)
          .invoke(()-> {});

        // any changes evaluate all validators
        vm.onChange(a, b).validateAll();

        // unbind any and all dependencies & change listeners
        vm.unbind();
        vm.unbind(b);

          .dependsOn(propA, propB)
        vm.onChange(IS_CANCELLED_PROP)
          .invoke( (oldVal, newVal)-> {
             if (newVal && task != null && !task.isCancelled()) {
                task.cancel();
            }
          });
         */

        log("--------------");
        log("Creation personVm \n" + personVm);
        log("--------------");
        personVm.validate();
        log("Validation personVm \n" + personVm);
        log("--------------");
        log(" Number of errors: " + personVm.getValidationMessages().size());
        log("--------------");
        displayErrorMsgs(personVm);
        log("--------------");

        personVm.invalidate();
        log("Invalidation personVm \n" + personVm);

        log("--------------");
        log(" Time to fix fields ");
        personVm.setPropertyValue(FIRST_NAME, "Mary");
        log("before save " + personVm.debugPropertyMessage(FIRST_NAME));
        personVm.save();
        log("after save " + personVm.debugPropertyMessage(FIRST_NAME));
        log("--------------");
        personVm.setPropertyValue(AGE, 20);
        log("before save " + personVm.debugPropertyMessage(AGE));
        personVm.save();
        log("after save " + personVm.debugPropertyMessage(AGE));
        log("--------------");
        personVm.setPropertyValue(PHONE, "222-888-4444");
        log("before save " + personVm.debugPropertyMessage(PHONE));
        personVm.save();
        log("after save " + personVm.debugPropertyMessage(PHONE));
        log("--------------");

        personVm.setPropertyValue(HEIGHT, 5);
        log("before save " + personVm.debugPropertyMessage(HEIGHT));
        personVm.save();
        log("after save " + personVm.debugPropertyMessage(HEIGHT));
        log("--------------");

        personVm.setPropertyValue(MPG, 16.2);
        log("before save " + personVm.debugPropertyMessage(MPG));
        personVm.save();
        log("after save " + personVm.debugPropertyMessage(MPG));
        log("--------------");
        log(" Number of errors: " + personVm.getValidationMessages().size());

        for (ValidationMessage vMsg : personVm.getValidationMessages()) {
            vMsg.interpolate(personVm);
            System.out.println("msg Type: %s errorcode: %s, msg: %s".formatted(vMsg.messageType(), vMsg.errorCode(), vMsg.interpolate(personVm)) );
        }

        log("--------------");
        log(" Assuming there are no errors, the save will commit changes from view props to model values");
        log(" After save -> \n" + personVm);
    }
    private static void displayErrorMsgs(ValidationViewModel personVm) {
        for (ValidationMessage vMsg : personVm.getValidationMessages()) {
            System.out.println("msg Type: %s errorcode: %s, msg: %s".formatted(vMsg.messageType(), vMsg.errorCode(), vMsg.interpolate(personVm)) );
        }
    }
    protected static void log(String message) {
        System.out.println(message);
    }
}
