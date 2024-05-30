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

import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

import java.util.*;

/**
 * As part of the MVVM pattern this view model interface provide common functions to store model values and create
 * named JavaFX properties to allow Controllers to bind or simply copy values.
 * <pre>
 *     save() - Copies property values into the model values.
 *     reset() - Copies model values into property values.
 * </pre>
 * <pre>{@code
 *   ViewModel personVm = new SimpleViewModel()
 *                 .addProperty("firstName", "Fred")
 *                 .addProperty("age", 54l)
 *                 .addProperty("height", 123)
 *                 .addProperty("colors", Set.of("red", "blue"))
 *                 .addProperty("foods", List.of("bbq", "chips", "bbq"))
 *                 .addProperty("thing", new Object(){
 *                     @Override
 *                     public String toString() {
 *                         return "thing ";
 *                     }
 *                 })
 *                 .addProperty("mpg", 20.5f);
 *
 *   // UI and ViewModel
 *   TextField firstNameTF = new TextField();
 *   firstNameTF.textProperty().bidirectionalBind(personVm.getProperty("firstName"));
 *
 *   // user types new name
 *   firstNameTF.setText("Wilma");
 *
 *   // if a reset button is called
 *   personVm.reset(); // Property value is Fred (copy model value into property value)
 *
 *   // if a save button is called
 *   personVm.save();  // Property value is Wilma and Model value is Wilma (copy property value into model value)
 *
 *   // after a successful save you can obtain the values. Since the UI controls might be bound to properties.
 *   String firstName = personVm.getValue("firstName");
 *   //... write to database
 *
 *
 * }
 * </pre>
 */
public interface ViewModel {
    /**
     * Copies the model values back into the property values.
     * @return Returns the ViewModel itself following the builder pattern.
     * @param <T> T is an instance of a ViewModel.
     */
    <T> T reset();

    /**
     * Copies the property values into the model values.
     * @return Returns the ViewModel itself following the builder pattern.
     * @param <T> T is an instance of a ViewModel.
     */
    <T> T save();

    /**
     * Returns a JavaFX Property to be used in a UI form.
     * <pre>{@code
     *    StringProperty myName = viewModel.getProperty("firstName");
     *    System.out.println("My name is %s".formatted(myName.get()); // My name is Fred
     * }
     * </pre>
     *
     * @param name The property name. e.g. firstName, lastName.
     * @return Returns a Property. The property contains the actual value.
     * @param <T> T is the Property such as IntegerProperty or StringProperty.
     */
    <T extends Property> T getProperty(String name);

    /**
     * Returns a value to be used in a UI form.
     * <pre>{@code
     *    String myName = viewModel.getPropertyValue("firstName");
     *    System.out.println("My name is %s".formatted(myName); // My name is Fred
     * }
     * </pre>
     *
     * @param name The property name. e.g. firstName, lastName.
     * @return Returns a Property's value.
     * @param <T> T is the Property such as IntegerProperty or StringProperty.
     */
    default <T> T getPropertyValue(String name) {
        return (T) getProperty(name).getValue();
    }

    /**
     * Returns a ObservableCollection to be used in a UI form.
     * <pre>{@code
     *    ObservableCollection<String> phones = viewModel.getPropertyValues("phones");
     *    phones.forEach(System.out::println);
     *    phones.addListener( new ListChangeListener() {
     *         @Override
     *         public void onChanged(ListChangeListener.Change change) {
     *              if (change.wasAdded()) {
     *                  System.out.println("a phone added);
     *              }
     *         }
     *    }
     *    phones.add("123-222-5555");
     * }
     * </pre>
     *
     * @param name The property name. e.g. firstName, lastName.
     * @return Returns a Property's value.
     * @param <T> T is the Property such as IntegerProperty or StringProperty .
     */
    default <T> T getPropertyValues(String name) {
        return (T) getObservableCollection(name);
    }

    /**
     * Sets the Property to contain the new value. It does not set the model value.
     * @param name property name
     * @param value raw value
     * @return returns itself of type ViewModel.
     * @param <T> T is an instance of a ViewModel.
     */
    <T extends ViewModel> T setPropertyValue(String name, Object value);

    /**
     * Sets the observable collection's with values.
     * @param name The name of the property
     * @param values The list of values to be put into an observable collection.
     * @return Returns the view model itself following the builder pattern.
     * @param <T> T is an instance of a ViewModel.
     */
    <T extends ViewModel> T setPropertyValues(String name, Collection values);

    /**
     * Sets the Property to contain the new value. It does not set the model value.
     * @param name property name
     * @param value raw value
     * @param skip True to skip the process of copying the property value into the model value.
     * @return returns itself of type ViewModel following the builder pattern.
     * @param <T> T is an instance of a ViewModel.
     */
    <T extends ViewModel> T setPropertyValue(String name, Object value, boolean skip);

    /**
     * Sets the observable collection's with values.
     * @param name The name of the property
     * @param values The collection of values to be put into an observable collection.
     * @param skip True to skip the process of copying the property values into the model values. Saves on memory typically used to populate ComboBoxes and ListViews.
     * @return Returns the view model itself following the builder pattern.
     * @param <T> T is an instance of a ViewModel.
     */
    <T extends ViewModel> T setPropertyValues(String name, Collection values, boolean skip);

    /**
     * Removes a property and model values from the view model.
     * @param name property name
     * @return The property associated with the name. Here you can unbind things or clean up.
     */
    Property removeProperty(String name);
    /**
     * Sets the model data (valueMap values). TODO: check type before putting it into valueMap. e.g. if StringProperty value should not allow an Integer.
     * @param name property name
     * @param value Raw value to be set as the committed data.
     * @return returns itself of type SimpleViewModel.
     * @param <T> T is an instance of a ViewModel.
     */
    <T extends ViewModel> T setValue(String name, Object value);

    /**
     * Returns an ObservableCollection based on property name.
     * @param name property name.
     * @return an observable collection. Used for the UI or testing.
     */
    public Observable getObservableCollection(String name);

    /**
     * Returns an ObservableList based on property name.
     * @param name property name.
     * @return an observable list. Used for the UI or testing.
     * @param <T> T is type of object in an ObservableList.
     */
    <T> ObservableList<T> getObservableList(String name);

    /**
     * Returns an ObservableSet based on property name.
     * @param name property name.
     * @return an observable set. Used for the UI.
     * @param <T> T is type of object in an ObservableSet.
     */
    <T> ObservableSet<T> getObservableSet(String name);

    /**
     * Removes the ObservableCollection from the view model.
     * @param name property name.
     * @return Returns the removed observable collection. Allows caller to clean up or unbind any listeners.
     */
    Observable removeObservableCollection(String name);

    /**
     * Returns the model value. e.g. using a SimpleViewModel save() method the property value is copied to the model value.
     * @param name Property name
     * @return Returns the raw value from the model values map.
     * @param <T> T is the values return type. A class cast exception can occur.
     */
    <T> T getValue(String name);

    /**
     * Returns the model value as a List. e.g. using a SimpleViewModel save() method the property value is copied into the model value.
     * @param name Property name
     * @return Returns the raw value (List) from the model values (multi value map).
     * @param <T> T is the values return type. A class cast exception can occur.
     */
    <T> List<T> getList(String name);

    /**
     * Returns the model value as a Set. e.g. using a SimpleViewModel save() method the property value is copied into the model value.
     * @param name Property name
     * @return Returns the raw value (Set) from the model values (multi value map).
     * @param <T> T is the values return type. A class cast exception can occur.
     */
    <T> Set<T> getSet(String name);

    /**
     * Returns the model value as a Collection. e.g. using a SimpleViewModel save() method the property value is copied into the model value.
     * @param name Property name
     * @return Returns the raw value (Collection) from the model values (multi value map).
     * @param <T> T is the values return type. A class cast exception can occur.
     */
    <T> Collection<T> getCollection(String name);

    /**
     * TODO output to console or logger
     * Debug and output property values.
     * @param name the property value
     */
    default void debugProperty(String name) {

    }

    /**
     * Returns a debug string of property values and model values.
     * @param name property name to debug
     * @return Returns a debug string of property values and model values.
     */
    default String debugPropertyMessage(String name) {
        if (getProperty(name) != null) {
            return "viewProperty:%s = %s | modelValue:%s = %s".formatted(name, getProperty(name).getValue(), name, getValue(name));
        } else if(getObservableCollection(name) != null) {
            return "viewProperty:%s = %s | modelValue:%s = %s".formatted(name, getObservableCollection(name), name, getCollection(name));
        }
        return "Unknown viewProperty:%s    ".formatted(name);
    }
}
