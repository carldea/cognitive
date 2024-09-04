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


import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import org.carlfx.cognitive.viewmodel.SimpleViewModel;
import org.carlfx.cognitive.viewmodel.ViewModel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SimpleViewModel Test")
public class SimpleViewModelTest implements TestLifecycleLogger {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleViewModelTest.class);
    protected enum PersonForm {
        FIRST_NAME,
        LAST_NAME,
        AGE,
    }
    public static String FIRST_NAME = "firstName";
    public static String LAST_NAME = "lastName";
    public static String AGE = "age";
    @ParameterizedTest
    @DisplayName("Parameterized Enum as property name getProperty() Test - SimpleViewModel.getProperty(Enum) Test")
    @MethodSource("provideEnumPropertyNames")
    public void parameterizedEnumPropertyNameGetPropertyTest(SimpleViewModel simpleViewModel) {
        StringProperty firstName = simpleViewModel.getProperty(PersonForm.FIRST_NAME);
        assertNotNull(firstName);
        assertTrue(firstName.isNotEmpty().get());

        StringProperty lastName = simpleViewModel.getProperty(PersonForm.LAST_NAME);
        assertNotNull(lastName);
        assertTrue(lastName.isNotEmpty().get());

        IntegerProperty age = simpleViewModel.getProperty(PersonForm.AGE);
        assertNotNull(age);
        assertTrue(age.greaterThan(0).get());
    }

    private static Stream<Arguments> provideEnumPropertyNames() {
        return Stream.of(
                Arguments.of(new SimpleViewModel()
                        .addProperty(PersonForm.FIRST_NAME, "Barney")
                        .addProperty(PersonForm.LAST_NAME,  "Rubble")
                        .addProperty(PersonForm.AGE, 990)),
                Arguments.of(new SimpleViewModel()
                        .addProperty(PersonForm.FIRST_NAME, "Fred")
                        .addProperty(PersonForm.LAST_NAME,  "Flintstone")
                        .addProperty(PersonForm.AGE, 1000))
        );
    }

    @ParameterizedTest
    @DisplayName("Parameterized String property name getProperty() Test - SimpleViewModel.getProperty(Enum) Test")
    @MethodSource("provideStringPropertyNames")
    public void parameterizedStringPropertyNameGetPropertyTest(SimpleViewModel simpleViewModel) {
        StringProperty firstName = simpleViewModel.getProperty(FIRST_NAME);
        assertNotNull(firstName);
        assertTrue(firstName.isNotEmpty().get());

        StringProperty lastName = simpleViewModel.getProperty(LAST_NAME);
        assertNotNull(lastName);
        assertTrue(lastName.isNotEmpty().get());

        IntegerProperty age = simpleViewModel.getProperty(AGE);
        assertNotNull(age);
        assertTrue(age.greaterThan(0).get());
    }
    private static Stream<Arguments> provideStringPropertyNames() {
        return Stream.of(
                Arguments.of(new SimpleViewModel()
                        .addProperty(FIRST_NAME, "Barney")
                        .addProperty(LAST_NAME,  "Rubble")
                        .addProperty(AGE, 990)),
                Arguments.of(new SimpleViewModel()
                        .addProperty(FIRST_NAME, "Fred")
                        .addProperty(LAST_NAME,  "Flintstone")
                        .addProperty(AGE, 1000))
        );
    }

    @Test
    @DisplayName("SimpleViewModel.getPropertyValue(Enum and String) Test")
    void getPropertyValue(TestInfo testInfo) {
        // Expected
        String expected = "Fred";
        SimpleViewModel simpleViewModel = new SimpleViewModel();
        simpleViewModel.addProperty(PersonForm.FIRST_NAME, expected);

        // Actual
        String actual = simpleViewModel.getPropertyValue(PersonForm.FIRST_NAME);

        // test method.
        assertEquals(expected, actual, "getProperty(Enum) did not return expected value " + expected);

        SimpleViewModel simpleViewModel2 = new SimpleViewModel();
        simpleViewModel2.addProperty(FIRST_NAME, expected);
        String actual2 = simpleViewModel2.getPropertyValue(FIRST_NAME);

        // test method.
        assertEquals(expected, actual2, "getProperty(String) did not return expected value " + expected);
    }

    @Test
    @DisplayName("SimpleViewModel.save() Test")
    void save() {
        String expectedFirstName = "Barney";
        String expectedLastName = "Rubble";
        int expectedAge = 990;

        SimpleViewModel simpleViewModel = new SimpleViewModel()
                .addProperty(PersonForm.FIRST_NAME, expectedFirstName)
                .addProperty(PersonForm.LAST_NAME,  expectedLastName)
                .addProperty(PersonForm.AGE, expectedAge);

        // testing the save method.
        simpleViewModel.save();

        // internally the save method will copy property values into the model values layer.
        assertEquals(simpleViewModel.getValue(PersonForm.FIRST_NAME), expectedFirstName);
        assertEquals(simpleViewModel.getValue(PersonForm.LAST_NAME), expectedLastName);
        assertEquals((int)simpleViewModel.getValue(PersonForm.AGE), expectedAge);

        SimpleViewModel simpleViewModel2 = new SimpleViewModel()
                .addProperty(FIRST_NAME, expectedFirstName)
                .addProperty(LAST_NAME,  expectedLastName)
                .addProperty(AGE, expectedAge);

        // testing the save method.
        simpleViewModel2.save();

        // internally the save method will copy property values into the model values layer.
        assertEquals(simpleViewModel2.getValue(FIRST_NAME), expectedFirstName);
        assertEquals(simpleViewModel2.getValue(LAST_NAME), expectedLastName);
        assertEquals((int)simpleViewModel2.getValue(AGE), expectedAge);
    }
    @Test
    @DisplayName("Creating a custom string binding Test. ")
    @Disabled
    void myTest() {
        ViewModel personVm = new SimpleViewModel()
                .addProperty(PersonForm.FIRST_NAME, new Object());
        ObjectProperty<Object> objectProperty = personVm.getProperty(PersonForm.FIRST_NAME);

        // custom binding if not null display date otherwise empty string.
        StringBinding dateStrProp = Bindings
                .when(objectProperty.isNotNull())
                .then(LocalDate.now().format(DateTimeFormatter.ofPattern("MMM d, yyyy")))
                .otherwise("");

        LOG.info("Not Null =====> the dateStrProp.get() = %s".formatted(dateStrProp.get()));
        assertNotEquals("", dateStrProp.get(), "Should not be an empty string Should be a date formatted MMM d, yyyy");
        personVm.setPropertyValue(PersonForm.FIRST_NAME, null);
        LOG.info("Is Null  =====> the dateStrProp.get() = %s".formatted(dateStrProp.get()));
        assertEquals("", dateStrProp.get(), "Should be an empty string not a date format string.");

    }
    @Test
    void testVariousDataTypesAddProperty() {
        //TextField firstName
        ViewModel personVm = new SimpleViewModel()
                .addProperty(FIRST_NAME, "Fred")
                .addProperty(LAST_NAME, "Flintstone")
                .addProperty(PersonForm.AGE, 54l)
                .addProperty("height", 123)
                .addProperty("colors", Set.of("red", "blue"))
                .addProperty("foods", List.of("bbq", "chips", "bbq"))
                .addProperty("thing", new Object(){
                    @Override
                    public String toString() {
                        return "thing ";
                    }
                })
                .addProperty("mpg", 20.5f);

        LOG.info("--------------");
        LOG.info("Creation personVm \n" + personVm);

        LOG.info("--------------");
        personVm.setPropertyValue(FIRST_NAME, "Wilma");
        LOG.info("before save " + personVm.debugPropertyMessage(FIRST_NAME));
        personVm.save();
        LOG.info("after save " + personVm.debugPropertyMessage(FIRST_NAME));
        LOG.info("--------------");
        personVm.setPropertyValue(AGE, 20);
        LOG.info("before save " + personVm.debugPropertyMessage(AGE));
        personVm.save();
        LOG.info("after save " + personVm.debugPropertyMessage(AGE));
        LOG.info("--------------");
        personVm.setPropertyValue("height", 555);
        LOG.info("before save " + personVm.debugPropertyMessage("height"));
        personVm.save();
        LOG.info("after save " + personVm.debugPropertyMessage("height"));
        LOG.info("--------------");
        personVm.setPropertyValue("colors", Set.of("green"));
        LOG.info("before save " + personVm.debugPropertyMessage("colors"));
        personVm.save();
        LOG.info("after save " + personVm.debugPropertyMessage("colors"));

        // changing ("bbq", "chips", "bbq") TO ("corn", "crabs")
        personVm.setPropertyValues("foods", Set.of("corn", "crabs"));
        LOG.info("before save " + personVm.debugPropertyMessage("foods"));
        personVm.save(); // commit data from
        LOG.info("after save  " + personVm.debugPropertyMessage("foods"));

        LOG.info("--------------");

        ViewModel personVm2 = new SimpleViewModel()
                .addProperty(FIRST_NAME, "Fred")
                .addProperty(AGE, 54l)
                .addProperty("height", 123)
                .addProperty("colors", Set.of("red", "blue"))
                .addProperty("foods", List.of("bbq", "chips", "bbq"))
                .addProperty("thing", new Object(){
                    @Override
                    public String toString() {
                        return "thing ";
                    }
                })
                .addProperty("mpg", 20.5f);


        personVm2.setPropertyValue(FIRST_NAME, "Mary");
        personVm2.setPropertyValue(AGE, 20);
        personVm2.setPropertyValue("height", 555);
        personVm2.setPropertyValue("colors", Set.of("green"));
        personVm2.setPropertyValues("foods", Set.of("corn", "crabs"));
        personVm2.setPropertyValue("thing", new Object(){
            @Override
            public String toString() {
                return "thing 2";
            }
        });
        LOG.info("before reset personVm2 \n" + personVm2);
        personVm2.reset();
        LOG.info("after reset  personVm2 \n" + personVm2);
    }
    public static void main(String[] args){
        //TextField firstName
        ViewModel personVm = new SimpleViewModel()
                .addProperty(FIRST_NAME, "Fred")
                .addProperty(LAST_NAME, "Flintstone")
                .addProperty(PersonForm.AGE, 54l)
                .addProperty("height", 123)
                .addProperty("colors", Set.of("red", "blue"))
                .addProperty("foods", List.of("bbq", "chips", "bbq"))
                .addProperty("thing", new Object(){
                    @Override
                    public String toString() {
                        return "thing ";
                    }
                })
                .addProperty("mpg", 20.5f);

        LOG.info("--------------");
        LOG.info("Creation personVm \n" + personVm);

        LOG.info("--------------");
        personVm.setPropertyValue(FIRST_NAME, "Wilma");
        LOG.info("before save " + personVm.debugPropertyMessage(FIRST_NAME));
        personVm.save();
        LOG.info("after save " + personVm.debugPropertyMessage(FIRST_NAME));
        LOG.info("--------------");
        personVm.setPropertyValue(AGE, 20);
        LOG.info("before save " + personVm.debugPropertyMessage(AGE));
        personVm.save();
        LOG.info("after save " + personVm.debugPropertyMessage(AGE));
        LOG.info("--------------");
        personVm.setPropertyValue("height", 555);
        LOG.info("before save " + personVm.debugPropertyMessage("height"));
        personVm.save();
        LOG.info("after save " + personVm.debugPropertyMessage("height"));
        LOG.info("--------------");
        personVm.setPropertyValue("colors", Set.of("green"));
        LOG.info("before save " + personVm.debugPropertyMessage("colors"));
        personVm.save();
        LOG.info("after save " + personVm.debugPropertyMessage("colors"));

        // changing ("bbq", "chips", "bbq") TO ("corn", "crabs")
        personVm.setPropertyValues("foods", Set.of("corn", "crabs"));
        LOG.info("before save " + personVm.debugPropertyMessage("foods"));
        personVm.save(); // commit data from
        LOG.info("after save  " + personVm.debugPropertyMessage("foods"));

        LOG.info("--------------");

        ViewModel personVm2 = new SimpleViewModel()
                .addProperty(FIRST_NAME, "Fred")
                .addProperty(AGE, 54l)
                .addProperty("height", 123)
                .addProperty("colors", Set.of("red", "blue"))
                .addProperty("foods", List.of("bbq", "chips", "bbq"))
                .addProperty("thing", new Object(){
                    @Override
                    public String toString() {
                        return "thing ";
                    }
                })
                .addProperty("mpg", 20.5f);


        personVm2.setPropertyValue(FIRST_NAME, "Mary");
        personVm2.setPropertyValue(AGE, 20);
        personVm2.setPropertyValue("height", 555);
        personVm2.setPropertyValue("colors", Set.of("green"));
        personVm2.setPropertyValues("foods", Set.of("corn", "crabs"));
        personVm2.setPropertyValue("thing", new Object(){
            @Override
            public String toString() {
                return "thing 2";
            }
        });
        LOG.info("before reset personVm2 \n" + personVm2);
        personVm2.reset();
        LOG.info("after reset  personVm2 \n" + personVm2);

    }

}
