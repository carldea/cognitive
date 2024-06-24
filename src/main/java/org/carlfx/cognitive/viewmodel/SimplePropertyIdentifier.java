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

import java.util.function.Function;

/**
 * A simple property identifier has a unique id and a user data type object (domain or dto).
 * @param <T> The type as the unique id's type.
 * @param <U> The user data type object's type.
 */
public class SimplePropertyIdentifier<T, U> implements PropertyIdentifier {
    private final String propertyName;
    private final U userData;
    private final Function<U, T> propertyIdFunctor;

    /**
     * A constructor of a SimplePropertyIdentifier.
     * @param propertyName The property name.
     * @param propertyIdFunctor A Function or functional interface by passing in user data and return unique id.
     * @param userData The user data or domain object.
     */
    public SimplePropertyIdentifier (String propertyName, Function<U, T> propertyIdFunctor, U userData) {
        this.propertyName = propertyName;
        this.userData = userData;
        this.propertyIdFunctor = propertyIdFunctor;
    }

    /**
     * Constructor receiving a Function responsible for returning the property id based on the user data object.
     * An example is the following:
     * <pre>
     *     // Property Id is of type UUID, user data object is a String.
     *     SimplePropertyIdentifier&lt;UUID, String&gt;> spId = new SimplePropertyIdentifier&lt;&gt;(UUID::fromString, UUID.randomUUID().toString());
     *
     * </pre>
     * @param propertyIdFunctor a function to return the unique id (property id) from the user data object.
     * @param userData A user data type object containing a unique identifier value.
     */
    public SimplePropertyIdentifier (Function<U, T> propertyIdFunctor, U userData) {
        this.userData = userData;
        this.propertyIdFunctor = propertyIdFunctor;
        this.propertyName = idToString();

    }
    @Override
    public T getPropertyId() {
        return propertyIdFunctor.apply(getUserData());
    }

    /**
     * A readable property name associated with the property id.
     * @return Returns the property name.
     */
    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public U getUserData() {
        return userData;
    }

    @Override
    public String toString() {
        return getPropertyId().toString() + "(" + getPropertyName() + ")" ;
    }
}
