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

import javafx.beans.Observable;
import javafx.beans.property.Property;
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
 * }
 * </pre>
 */
public interface ViewModel {
    <T> T reset();
    <T> T save();

    <T extends Property> T getProperty(String name);
    default <T> T getPropertyValue(String name) {
        return (T) getProperty(name).getValue();
    }
    default <T> T getPropertyValues(String name) {
        return (T) getObservableCollection(name);
    }
    /**
     * Sets the Property to contain the new value. It does not set the model value.
     * @param name property name
     * @param value raw value
     * @return returns itself of type SimpleViewModel.
     */
    <T extends ViewModel> T setPropertyValue(String name, Object value);
    <T extends ViewModel> T setPropertyValues(String name, Collection values);
    <T extends ViewModel> T setPropertyValue(String name, Object value, boolean skip);
    <T extends ViewModel> T setPropertyValues(String name, Collection values, boolean skip);
    Property removeProperty(String name);
    /**
     * Sets the model data (valueMap values). TODO: check type before putting it into valueMap. e.g. if StringProperty value should not allow an Integer.
     * @param name property name
     * @param value Raw value to be set as the committed data.
     * @return returns itself of type SimpleViewModel.
     */
    <T extends ViewModel> T setValue(String name, Object value);
    public Observable getObservableCollection(String name);
    <T> ObservableList<T> getObservableList(String name);
    <T> ObservableSet<T> getObservableSet(String name);
    Observable removeObservableCollection(String name);
    <T> T getValue(String name);
    <T> List<T> getList(String name);
    <T> Set<T> getSet(String name);
    <T> Collection<T> getCollection(String name);

    default void debugProperty(String name) {

    }
    default String debugPropertyMessage(String name) {
        if (getProperty(name) != null) {
            return "viewProperty:%s = %s | modelValue:%s = %s".formatted(name, getProperty(name).getValue(), name, getValue(name));
        } else if(getObservableCollection(name) != null) {
            return "viewProperty:%s = %s | modelValue:%s = %s".formatted(name, getObservableCollection(name), name, getCollection(name));
        }
        return "Unknown viewProperty:%s    ".formatted(name);
    }
}
