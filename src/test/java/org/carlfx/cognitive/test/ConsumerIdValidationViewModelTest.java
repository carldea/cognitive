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

import javafx.beans.property.*;
import org.carlfx.cognitive.validator.ValidationMessage;
import org.carlfx.cognitive.validator.ValidationResult;
import org.carlfx.cognitive.viewmodel.IdValidationViewModel;
import org.carlfx.cognitive.viewmodel.PropertyIdentifier;
import org.carlfx.cognitive.viewmodel.SimplePropertyIdentifier;
import org.carlfx.cognitive.viewmodel.ViewModel;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsumerIdValidationViewModelTest {
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
        final String BRAIN_POWER_PROP = "brainPower";
        UUID uuid = UUID.randomUUID();
        PropertyIdentifier<UUID, String> spId = new SimplePropertyIdentifier<>("otherName", UUID::fromString, uuid.toString()); // object is a string
//        PropertyIdentifier<PublicId, EntityFacade> spId2 = new SimplePropertyIdentifier<>("otherName", cf -> cf.getPublicId()Function.identity(), uuid); // object is a UUID

        ConceptRecord caseSigConceptRecord = new ConceptRecord(UUID.randomUUID(), "Case Significance", "Case");
        ConceptRecord caseInsenstiveConcept = new ConceptRecord(UUID.randomUUID(), "Case Insensitive", "Insensitive");
        ConceptRecord caseCapInitialConcept = new ConceptRecord(UUID.randomUUID(), "Capitalize initial character", "Cap 1st Character");

        IdValidationViewModel personVm =  new IdValidationViewModel()
                .addProperty(FIRST_NAME, "")
                .addValidator(FIRST_NAME, "First Name", (ReadOnlyStringProperty prop, ValidationResult vr, ViewModel vm) -> {
                    if (prop.isEmpty().get()) {
                        vr.error("${%s} is required".formatted(FIRST_NAME));
                    }
                })
                .addValidator(FIRST_NAME, "First Name", (ReadOnlyStringProperty prop, ValidationResult vr, ViewModel vm) -> {
                    if (prop.isEmpty().get() || prop.isNotEmpty().get() && prop.get().length() < 3) {
                        vr.error("${%s} must be greater than 3 characters.".formatted(FIRST_NAME));
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
                })
                .addProperty(spId, "hello") // otherName SimplePropertyIdentifier with text as value
                .addValidator(spId.getPropertyName(), "Other Name", (ReadOnlyStringProperty prop, ValidationResult vr, ViewModel vm) -> {
                    String firstChar = prop.get().substring(0,1);
                    if (!firstChar.toUpperCase().equals(firstChar)) {
                        vr.error("${%s} first character must be capitalized. Entered as %s ".formatted(spId.idToString(), prop.get()));
                    }
                })
                .addProperty(new SimplePropertyIdentifier(BRAIN_POWER_PROP, (id) -> id.toString().hashCode(), "brain power 1" ), 6l) // otherName SimplePropertyIdentifier with text as value
                .addValidator(BRAIN_POWER_PROP, "Brain power", (ReadOnlyLongProperty prop, ValidationResult vr, ViewModel vm) -> {
                    if (prop.get() >= 5) {
                        vr.error("${%s} must be less than 5".formatted(BRAIN_POWER_PROP), 401);
                    }
                })
                .addProperty(new ConceptPropertyIdentifier(caseSigConceptRecord), caseInsenstiveConcept) // Custom PropertyIdentifier Concept oriented value. propertyId (case sig) -> Property Value (Case insensitive).
                //.addProperty(new ConceptPropertyIdentifier(caseSigConceptRecord), (Object) null) // Custom PropertyIdentifier Concept oriented value. propertyId (case sig) -> Property Value (Case insensitive).
                .addValidator(caseSigConceptRecord.uuid(), "Case significance", (ReadOnlyObjectProperty prop, ValidationResult vr, ViewModel vm) -> {
                    if (prop.isNull().get()) {
                        vr.error(caseSigConceptRecord.uuid().toString(), "Case Significance must be populated. Set to null ");
                    }
                    if (prop.isNotNull().get()) {
                        ConceptRecord conceptRecord = (ConceptRecord) prop.get();
                        if (!conceptRecord.uuid().equals(caseCapInitialConcept.uuid())) {
                            vr.error(caseSigConceptRecord.uuid().toString(), "Case Significance must be %s. Entered as %s ".formatted(caseCapInitialConcept, prop.get()));
                        }
                    }
                });
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
        displayErrorMsgs(personVm);

        log("--------------");
        personVm.setPropertyValue(AGE, 20);
        log("before save " + personVm.debugPropertyMessage(AGE));
        personVm.save();
        log("after save " + personVm.debugPropertyMessage(AGE));
        displayErrorMsgs(personVm);

        log("--------------");
        personVm.setPropertyValue(PHONE, "222-888-4444");
        log("before save " + personVm.debugPropertyMessage(PHONE));
        personVm.save();
        log("after save " + personVm.debugPropertyMessage(PHONE));
        displayErrorMsgs(personVm);

        log("--------------");

        personVm.setPropertyValue(HEIGHT, 5);
        log("before save " + personVm.debugPropertyMessage(HEIGHT));
        personVm.save();
        log("after save " + personVm.debugPropertyMessage(HEIGHT));
        displayErrorMsgs(personVm);

        log("--------------");

        personVm.setPropertyValue(MPG, 16.2);
        log("before save " + personVm.debugPropertyMessage(MPG));
        personVm.save();
        log("after save " + personVm.debugPropertyMessage(MPG));
        displayErrorMsgs(personVm);

        log("--------------");
        personVm.setPropertyValue(spId, "Hello");
        log("before save " + personVm.debugPropertyMessage(spId.idToString()));
        personVm.save();
        log("after save " + personVm.debugPropertyMessage(spId.idToString()));
        displayErrorMsgs(personVm);

        log("--------------");
        personVm.setPropertyValue(caseSigConceptRecord.uuid(), caseCapInitialConcept);
        log("before save " + personVm.debugPropertyMessage(caseSigConceptRecord.uuid().toString()));
        personVm.save();
        log("after save " + personVm.debugPropertyMessage(caseSigConceptRecord.uuid().toString()));
        displayErrorMsgs(personVm);

        log("--------------");


        log(" Number of errors: " + personVm.getValidationMessages().size());

        displayErrorMsgs(personVm);

        log("--------------");
        log(" Assuming there are no errors, the save will commit changes from view props to model values");
        log(" After save -> \n" + personVm);
    }

    private static void displayErrorMsgs(IdValidationViewModel personVm) {
        for (ValidationMessage vMsg : personVm.getValidationMessages()) {
            System.out.println("msg Type: %s errorcode: %s, msg: %s".formatted(vMsg.messageType(), vMsg.errorCode(), vMsg.interpolate(personVm)) );
        }
    }

    protected static void log(String message) {
        System.out.println(message);
    }
}
