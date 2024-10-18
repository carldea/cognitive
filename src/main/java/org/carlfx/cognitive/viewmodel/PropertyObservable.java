/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright © 2024. Carl Dea.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.carlfx.cognitive.viewmodel;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

/**
 * For common JavaFX datatypes associated with Property objects. Such as the following:
 * <pre>
 *     - IntegerProperty
 *     - FloatProperty
 *     - StringProperty
 *     - LongProperty
 *     - ObjectProperty
 *     - BooleanProperty
 * </pre>
 */
public interface PropertyObservable {
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
     * Returns a JavaFX Property to be used in a UI form.
     * <pre>{@code
     *    StringProperty myName = viewModel.getProperty("firstName");
     *    System.out.println("My name is %s".formatted(myName.get()); // My name is Fred
     * }
     * </pre>
     *
     * @param name The property name as an enum value. e.g. PersonEnum { firstName, lastName }.
     * @return Returns a Property. The property contains the actual value.
     * @param <T> T is the Property such as IntegerProperty or StringProperty.
     */
    <T extends Property> T getProperty(Enum name);

    /**
     * Returns a JavaFX BooleanProperty.
     * <pre>{@code
     *    BooleanProperty flagProp = viewModel.getBooleanProperty("happy");
     *    System.out.println("Happy? %s".formatted(flagProp.isTrue()); // Happy? true
     * }
     * </pre>
     *
     * @param name The property name. e.g. firstName, lastName.
     * @return Returns a Property. The property contains the actual value.
     */
    default BooleanProperty getBooleanProperty(String name) {
        return getProperty(name);
    }

    /**
     * Returns a JavaFX BooleanProperty.
     * <pre>{@code
     *    BooleanProperty flagProp = viewModel.getBooleanProperty(HAPPY);
     *    System.out.println("Happy? %s".formatted(flagProp.isTrue()); // Happy? true
     * }
     * </pre>
     *
     * @param name The property name. e.g. firstName, lastName.
     * @return Returns a Property. The property contains the actual value.
     */
    default BooleanProperty getBooleanProperty(Enum name) {
        return getProperty(name);
    }

    /**
     * Returns a JavaFX FloatProperty.
     * <pre>{@code
     *    FloatProperty floatProp = viewModel.getFloatProperty("meters");
     *    System.out.println("meters? %s".formatted(floatProp.getValue()); // Happy? true
     * }
     * </pre>
     *
     * @param name The property name. e.g. firstName, lastName.
     * @return Returns a Property. The property contains the actual value.
     */
    default FloatProperty getFloatProperty(String name) {
        return getProperty(name);
    }

    /**
     * Returns a JavaFX FloatProperty.
     * <pre>{@code
     *    FloatProperty myProp = viewModel.getFloatProperty("meters");
     *    System.out.println("meters? %s".formatted(myProp.getValue());
     * }
     * </pre>
     *
     * @param name The property name. e.g. firstName, lastName.
     * @return Returns a Property. The property contains the actual value.
     */
    default FloatProperty getFloatProperty(Enum name) {
        return getProperty(name);
    }

    /**
     * Returns a JavaFX DoubleProperty.
     * <pre>{@code
     *    DoubleProperty myProp = viewModel.getDoubleProperty("meters");
     *    System.out.println("meters? %s".formatted(myProp.getValue());
     * }
     * </pre>
     *
     * @param name The property name. e.g. firstName, lastName.
     * @return Returns a Property. The property contains the actual value.
     */
    default DoubleProperty getDoubleProperty(String name) {
        return getProperty(name);
    }

    /**
     * Returns a JavaFX DoubleProperty.
     * <pre>{@code
     *    DoubleProperty myProp = viewModel.getDoubleProperty("meters");
     *    System.out.println("meters? %s".formatted(myProp.getValue());
     * }
     * </pre>
     *
     * @param name The property name. e.g. firstName, lastName.
     * @return Returns a Property. The property contains the actual value.
     */
    default DoubleProperty getDoubleProperty(Enum name) {
        return getProperty(name);
    }
    /**
     * Returns a JavaFX IntegerProperty.
     * <pre>{@code
     *    IntegerProperty myProp = viewModel.getIntegerProperty("meters");
     *    System.out.println("meters? %s".formatted(myProp.getValue());
     * }
     * </pre>
     *
     * @param name The property name. e.g. firstName, lastName.
     * @return Returns a Property. The property contains the actual value.
     */
    default IntegerProperty getIntegerProperty(String name) {
        return getProperty(name);
    }

    /**
     * Returns a JavaFX IntegerProperty.
     * <pre>{@code
     *    IntegerProperty myProp = viewModel.getIntegerProperty("meters");
     *    System.out.println("meters? %s".formatted(myProp.getValue());
     * }
     * </pre>
     *
     * @param name The property name. e.g. firstName, lastName.
     * @return Returns a Property. The property contains the actual value.
     */
    default IntegerProperty getIntegerProperty(Enum name) {
        return getProperty(name);
    }
    /**
     * Returns a JavaFX LongProperty.
     * <pre>{@code
     *    LongProperty myProp = viewModel.getLongProperty("meters");
     *    System.out.println("meters? %s".formatted(myProp.getValue());
     * }
     * </pre>
     *
     * @param name The property name. e.g. firstName, lastName.
     * @return Returns a Property. The property contains the actual value.
     */
    default LongProperty getLongProperty(String name) {
        return getProperty(name);
    }

    /**
     * Returns a JavaFX LongProperty.
     * <pre>{@code
     *    LongProperty myProp = viewModel.getLongProperty("meters");
     *    System.out.println("meters? %s".formatted(myProp.getValue());
     * }
     * </pre>
     *
     * @param name The property name. e.g. firstName, lastName.
     * @return Returns a Property. The property contains the actual value.
     */
    default LongProperty getLongProperty(Enum name) {
        return getProperty(name);
    }

    /**
     * Returns a JavaFX ObjectProperty.
     * <pre>{@code
     *    ObjectProperty<Person> myProp = viewModel.getObjectProperty("person");
     *    System.out.println("person? %s".formatted(myProp.getValue().getFirstName());
     * }
     * </pre>
     *
     * @param name The property name. e.g. firstName, lastName.
     * @param <T> Type of object inside ObjectProperty.
     * @return Returns a Property. The property contains the actual value.
     */
    default <T> ObjectProperty<T> getObjectProperty(String name) {
        return getProperty(name);
    }

    /**
     * Returns a JavaFX ObjectProperty.
     * <pre>{@code
     *    ObjectProperty myProp = viewModel.getObjectProperty("person");
     *    System.out.println("person? %s".formatted(myProp.getValue());
     * }
     * </pre>
     *
     * @param name The property name. e.g. firstName, lastName.
     * @param <T> Type of object inside ObjectProperty.
     * @return Returns a Property. The property contains the actual value.
     */
    default <T> ObjectProperty<T> getObjectProperty(Enum name) {
        return getProperty(name);
    }

    /**
     * Returns a JavaFX StringProperty.
     * <pre>{@code
     *    StringProperty myProp = viewModel.getStringProperty("firstName");
     *    System.out.println("firstName? %s".formatted(myProp.getValue());
     * }
     * </pre>
     *
     * @param name The property name. e.g. firstName, lastName.
     * @return Returns a Property. The property contains the actual value.
     */
    default StringProperty getStringProperty(String name) {
        return getProperty(name);
    }

    /**
     * Returns a JavaFX StringProperty.
     * <pre>{@code
     *    StringProperty myProp = viewModel.getStringProperty("firstName");
     *    System.out.println("firstName? %s".formatted(myProp.getValue());
     * }
     * </pre>
     *
     * @param name The property name. e.g. firstName, lastName.
     * @return Returns a Property. The property contains the actual value.
     */
    default StringProperty getStringProperty(Enum name) {
        return getProperty(name);
    }

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
     * Returns an ObservableList based on property name.
     * @param name property name as an Enum.
     * @return an observable list. Used for the UI or testing.
     * @param <T> T is type of object in an ObservableList.
     */
    <T> ObservableList<T> getObservableList(Enum name);

    /**
     * Returns an ObservableSet based on property name.
     * @param name property name.
     * @return an observable set. Used for the UI.
     * @param <T> T is type of object in an ObservableSet.
     */
    <T> ObservableSet<T> getObservableSet(String name);

    /**
     * Returns an ObservableSet based on property name (as an Enum). The set is from the property value layer.
     * @param name property name (as an Enum).
     * @return an observable set. Used for the UI.
     * @param <T> T is type of object in an ObservableSet.
     */
    <T> ObservableSet<T> getObservableSet(Enum name);

}
