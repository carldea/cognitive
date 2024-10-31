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
import javafx.beans.property.Property;

import java.util.*;
import java.util.function.Function;

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
public interface ViewModel extends PropertyObservable {
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
     * Return Model values.
     * @param name An Enum representing the property name instead of a String.
     * @return Return model value.
     * @param <T> The type of the stored value.
     */
    <T> T getValue(Enum name);

    /**
     * Add (create) a new property referenced by name.
     * @param name The property's name as a String.
     * @param property A JavaFX property.
     * @return Returns a ViewModel following the builder pattern.
     * @param <T> Type T is any derived ViewModel object.
     */
    <T extends ViewModel> T addProperty(String name, Property property);

    /**
     * Add (create) a new property referenced by name.
     * @param name The property's name as an Enum.
     * @param property A JavaFX property.
     * @return Returns a ViewModel following the builder pattern.
     * @param <T> Type T is any derived ViewModel object.
     */
    <T extends ViewModel> T addProperty(Enum name, Property property);

    /**
     * Add (create) a new property referenced by name.
     * @param name The property's name as an Enum.
     * @param value A String value. A JavaFX StringProperty will get created.
     * @return Returns a ViewModel following the builder pattern.
     * @param <T> Type T is any derived ViewModel object.
     */
    <T extends ViewModel> T addProperty(Enum name, String value);

    /**
     * Add (create) a new property referenced by name.
     * @param name The property's name as a String.
     * @param value A String value. A JavaFX StringProperty will get created.
     * @return Returns a ViewModel following the builder pattern.
     * @param <T> Type T is any derived ViewModel object.
     */
    <T extends ViewModel> T addProperty(String name, String value);

    /**
     * Add (create) a new property referenced by name.
     * @param name The property's name as an Enum.
     * @param value An int value. A JavaFX IntegerProperty will get created.
     * @return Returns a ViewModel following the builder pattern.
     * @param <T> Type T is any derived ViewModel object.
     */
    <T extends ViewModel> T addProperty(Enum name, int value);

    /**
     * Add (create) a new property referenced by name.
     * @param name The property's name as an Enum.
     * @param value A long value. A JavaFX LongProperty will get created.
     * @return Returns a ViewModel following the builder pattern.
     * @param <T> Type T is any derived ViewModel object.
     */
    <T extends ViewModel> T addProperty(Enum name, long value);

    /**
     * Add (create) a new property referenced by name.
     * @param name The property's name as an Enum.
     * @param value A float value. A JavaFX FloatProperty will get created.
     * @return Returns a ViewModel following the builder pattern.
     * @param <T> Type T is any derived ViewModel object.
     */
    <T extends ViewModel> T addProperty(Enum name, float value);

    /**
     * Add (create) a new property referenced by name.
     * @param name The property's name as an Enum.
     * @param value A double value. A JavaFX DoubleProperty will get created.
     * @return Returns a ViewModel following the builder pattern.
     * @param <T> Type T is any derived ViewModel object.
     */
    <T extends ViewModel> T addProperty(Enum name, double value);

    /**
     * Add (create) a new property referenced by name.
     * @param name The property's name as an Enum.
     * @param value A boolean value. A JavaFX BooleanProperty will get created.
     * @return Returns a ViewModel following the builder pattern.
     * @param <T> Type T is any derived ViewModel object.
     */
    <T extends ViewModel> T addProperty(Enum name, boolean value);

    /**
     * Add (create) a new property referenced by name.
     * @param name The property's name as an Enum.
     * @param value A Collection value. A JavaFX ObservableCollection will get created.
     * @return Returns a ViewModel following the builder pattern.
     * @param <T> Type T is any derived ViewModel object.
     */
    <T extends ViewModel> T addProperty(Enum name, Collection value);

    /**
     * Add (create) a new property referenced by name.
     * @param name The property's name as an Enum.
     * @param value A Collection value. A JavaFX ObservableCollection will get created.
     * @param skip skip being true means during a save() operation to not copy (skip) property values from the property value layer into the model value layer. This is typically for scenarios to populate a UIs dropdown options.
     * @return Returns a ViewModel following the builder pattern.
     * @param <T> Type T is any derived ViewModel object.
     */
    <T extends ViewModel> T addProperty(Enum name, Collection value, boolean skip);

    /**
     * Add (create) a new property referenced by name.
     * @param name The property's name as a String.
     * @param value A Collection value. A JavaFX ObservableCollection will get created.
     * @param skip skip being true means during a save() operation to not copy (skip) property values
     *             from the property value layer into the model value layer. This is typically for scenarios
     *             to populate a UIs dropdown options.
     * @return Returns a ViewModel following the builder pattern.
     * @param <T> Type T is any derived ViewModel object.
     */
    <T extends ViewModel> T addProperty(String name, Collection value, boolean skip);

    /**
     * Add (create) a new property referenced by name.
     * @param name The property's name as a String.
     * @param value An Object value. A JavaFX ObjectProperty will get created.
     * @return Returns a ViewModel following the builder pattern.
     * @param <T> Type T is any derived ViewModel object.
     */
    <T extends ViewModel> T addProperty(String name, Object value);

    /**
     * Add (create) a new property referenced by name.
     * @param name The property's name as an Enum.
     * @param value An Object value. A JavaFX ObjectProperty will get created.
     * @return Returns a ViewModel following the builder pattern.
     * @param <T> Type T is any derived ViewModel object.
     */
    <T extends ViewModel> T addProperty(Enum name, Object value);

    /**
     * Add (create) a new property referenced by name.
     * @param name The property's name as an Enum.
     * @param value A Function that returns a Collection value. A JavaFX ObservableCollection will get created.
     * @return Returns a ViewModel following the builder pattern.
     * @param <T> Type T is any derived ViewModel object.
     */
    <T extends ViewModel> T addProperty(Enum name, Function<T, Collection> value);

    /**
     * Add (create) a new property referenced by name.
     * @param name The property's name as an Enum.
     * @param value A Function that returns a Collection value. A JavaFX ObservableCollection will get created.
     * @param skip skip being true means during a save() operation to not copy (skip) property values from the
     *             property value layer into the model value layer. This is typically for scenarios to populate
     *             a UIs dropdown options.
     * @return Returns a ViewModel following the builder pattern.
     * @param <T> Type T is any derived ViewModel object.
     */
    <T extends ViewModel> T addProperty(Enum name, Function<T, Collection> value, boolean skip);

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
     * Returns a value to be used in a UI form.
     * <pre>{@code
     *    String myName = viewModel.getPropertyValue("firstName");
     *    System.out.println("My name is %s".formatted(myName); // My name is Fred
     * }
     * </pre>
     *
     * @param name The property name as an enum value. e.g. PersonForm { firstName, lastName}.
     * @return Returns a Property's value.
     * @param <T> T is the Property such as IntegerProperty or StringProperty.
     */
    default <T> T getPropertyValue(Enum name) {
        return getPropertyValue(name.toString());
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
     * @param name The property name as an enum. e.g. firstName, lastName.
     * @return Returns a Property's value.
     * @param <T> T is the Property such as IntegerProperty or StringProperty .
     */
    default <T> T getPropertyValues(Enum name) {
        return getPropertyValues(name.toString());
    }

    /**
     * Sets the Property to contain the new value. It does not set the model value.
     * @param name Enum used instead of a String property name.
     * @param values a collection values to be set.
     * @param skip skip being true means during a save() operation to not copy (skip) property values from the
     *             property value layer into the model value layer. This is typically for scenarios to populate
     *             a UIs dropdown options.
     * @return returns itself of type ViewModel.
     * @param <T> T is an instance of a ViewModel.
     */
    <T extends ViewModel> T setPropertyValues(Enum name, Collection values, boolean skip);

    /**
     * Sets the Property to contain the new value. It does not set the model value.
     * @param name property name
     * @param value raw value
     * @return returns itself of type ViewModel.
     * @param <T> T is an instance of a ViewModel.
     */
    <T extends ViewModel> T setPropertyValue(String name, Object value);

    /**
     * Sets the Property to contain the new value. It does not set the model value.
     * @param name property name as an Enum value.
     * @param value raw value
     * @return returns itself of type ViewModel.
     * @param <T> T is an instance of a ViewModel.
     */
    <T extends ViewModel> T setPropertyValue(Enum name, Object value);

    /**
     * Sets the observable collection's with values.
     * @param name The name of the property
     * @param values The list of values to be put into an observable collection.
     * @return Returns the view model itself following the builder pattern.
     * @param <T> T is an instance of a ViewModel.
     */
    <T extends ViewModel> T setPropertyValues(String name, Collection values);

    /**
     * Sets the observable collection's with values.
     * @param name The name of the property as an enum value.
     * @param values The list of values to be put into an observable collection.
     * @return Returns the view model itself following the builder pattern.
     * @param <T> T is an instance of a ViewModel.
     */
    <T extends ViewModel> T setPropertyValues(Enum name, Collection values);

    /**
     * Sets the model layer's value based on the property name. Please see the save() method.
     * @param name The property name as an Enum
     * @param value The value to be set.
     * @return Returns this variable following the builder pattern.
     * @param <T> Type T is a derived class of a ViewModel.
     */
    <T extends ViewModel> T setValue(Enum name, Object value);

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
     * Sets the Property to contain the new value. It does not set the model value.
     * @param name Enum to be used to reference a property lookup
     * @param value raw value
     * @param skip True to skip the process of copying the property value into the model value.
     * @return returns itself of type ViewModel following the builder pattern.
     * @param <T> T is an instance of a ViewModel.
     */
    <T extends ViewModel> T setPropertyValue(Enum name, Object value, boolean skip);

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
     * Removes a property and model values from the view model.
     * @param name property name as an Enum
     * @return The property associated with the name. Here you can unbind things or clean up.
     */
    Property removeProperty(Enum name);

    /**
     * Sets the model data (valueMap values). TODO: check type before putting it into valueMap. e.g. if StringProperty value should not allow an Integer.
     * @param name property name
     * @param value Raw value to be set as the committed data.
     * @return returns itself of type SimpleViewModel.
     * @param <T> T is an instance of a ViewModel.
     */
    <T extends ViewModel> T setValue(String name, Object value);

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
     * Removes a property and values based on the property name.
     * @param name property name as an Enum.
     * @return Returns Observable after it is removed.
     */
    Observable removeObservableCollection(Enum name);

    /**
     * Returns the model value as a List. e.g. using a SimpleViewModel save() method the property value is copied into the model value.
     * @param name Property name
     * @return Returns the raw value (List) from the model values (multi value map).
     * @param <T> T is the values return type. A class cast exception can occur.
     */
    <T> List<T> getList(String name);

    /**
     * Returns a List from the model value layer. See save() method for more details.
     * @param name The name of the property to lookup.
     * @return Returns a List from the model value layer. See save() method for more details.
     * @param <T> Type T is the type inside the collection.
     */
    <T> List<T> getList(Enum name);

    /**
     * Returns the model value as a Set. e.g. using a SimpleViewModel save() method the property value is copied into the model value.
     * @param name Property name
     * @return Returns the raw value (Set) from the model values (multi value map).
     * @param <T> T is the values return type. A class cast exception can occur.
     */
    <T> Set<T> getSet(String name);

    /**
     * Returns a Set from the model value layer. See save() method for more details.
     * @param name The name of the property to lookup.
     * @return Returns a List from the model value layer. See save() method for more details.
     * @param <T> Type T is the type inside the collection.
     */
    <T> Set<T> getSet(Enum name);

    /**
     * Returns the model value as a Collection. e.g. using a SimpleViewModel save() method the property value is copied into the model value.
     * @param name Property name
     * @return Returns the raw value (Collection) from the model values (multi value map).
     * @param <T> T is the values return type. A class cast exception can occur.
     */
    <T> Collection<T> getCollection(String name);

    /**
     * Returns a Set from the model value layer. See save() method for more details.
     * @param name The name of the property to lookup.
     * @return Returns a List from the model value layer. See save() method for more details.
     * @param <T> Type T is the type inside the collection.
     */
    <T> Collection<T> getCollection(Enum name);

    /**
     * TODO output to console or logger
     * Debug and output property values.
     * @param name the property value
     */
    default void debugProperty(String name) {

    }

    /**
     * TODO output to console or logger
     * Debug and output property values.
     * @param name the property value
     */
    void debugProperty(Enum name);

    /**
     * Returns a debug string of property values and model values.
     * @param name property name to debug
     * @return Returns a debug string of property values and model values.
     */
    default String debugPropertyMessage(String name) {
        if (getProperty(name) != null) {
            return "propertyValue:%s = %s | modelValue:%s = %s".formatted(name, getProperty(name).getValue(), name, getValue(name));
        } else if(getObservableCollection(name) != null) {
            return "propertyValue:%s = %s | modelValue:%s = %s".formatted(name, getObservableCollection(name), name, getCollection(name));
        }
        return "Unknown property:%s    ".formatted(name);
    }

    /**
     * Returns a debug string of property values and model values.
     * @param name property name to debug
     * @return Returns a debug string of property values and model values.
     */
    String debugPropertyMessage(Enum name);

    /**
     * Perform work on either a non or JavaFX application thread when a property value changes.
     * @param doIt A runnable code block
     * @param propertyName at least one property to monitor change.
     * @param propertyNames Additional properties to monitor change.
     * @param <T> A derived ViewModel type
     * @return Returns itself ViewModel
     */
    default <T extends ViewModel> T doOnChange(Runnable doIt, Object propertyName, Object ...propertyNames) {
        assert propertyName != null;
        List list = new ArrayList<>();
        list.add(propertyName);
        if (propertyNames.length > 0) {
            list.addAll(Arrays.stream(propertyNames).toList());
        }
        list.forEach(propertyName2 -> {
            Property property = null;
            if (propertyName2 instanceof String pStr) {
                property = getProperty(pStr);
            } else if (propertyName2 instanceof Enum pEnum) {
                property = getProperty(pEnum);
            }
            if (property != null) {
                property.addListener((observable, oldValue, newValue) -> {
                    doIt.run();
                });
            }
        });
        return (T) this;
    }
}
