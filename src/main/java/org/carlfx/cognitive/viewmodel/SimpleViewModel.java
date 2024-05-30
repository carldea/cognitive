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
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A lightweight implementation of a ViewModel of the MVVM pattern.
 * <pre>
 *     reset - Will copy model values into property.
 *     save  - Will copy property values into the model value (value map).
 * </pre>
 */
public class SimpleViewModel implements ViewModel {
    private ObservableMap<String, Property> singleValueMap = FXCollections.observableMap(new LinkedHashMap<>());
    private ObservableMap<String, Observable> multiValueMap = FXCollections.observableMap(new LinkedHashMap<>());
    private Map<String, Object> valueMap = FXCollections.observableMap(new LinkedHashMap<>());
    private final List<String> skipPropertiesToSave = new ArrayList<>();

    /**
     * Default constructor.
     */
    public SimpleViewModel(){}
    /**
     * Copies value from valueMap to property. Updating view from model.
     * @param name property name
     * @param property The property to be reloaded from model value.
     */
    public void reloadProperty(String name, Property property) {
        Object value = valueMap.get(name);
        if (property instanceof IntegerProperty p) {
            p.set((int)valueMap.get(name));
        } else if(property instanceof LongProperty p) {
            p.set((long) value);
        } else if(property instanceof FloatProperty p) {
            p.set((float) value);
        } else if(property instanceof DoubleProperty p) {
            p.set((double) value);
        } else if(property instanceof BooleanProperty p) {
            p.set((boolean) value);
        } else if(property instanceof StringProperty p) {
            p.set((String) value);
        } else if(property instanceof ObjectProperty p) {
            p.set(value);
        }
    }

    /**
     * Returns true if a property is skipped meaning model values are ignored. e.g. a list of items for display purposes should not be copied to model values.
     * @param name property name to skip.
     * @return Returns true if a property is skipped meaning model values are ignored. e.g. a list of items for display purposes should not be copied to model values.
     */
    protected boolean shouldSkip(String name) {
        return skipPropertiesToSave.contains(name);
    }
    /**
     * Copies value model to properties. ValueMap to Properties.
     * @return SimpleViewModel instance.
     */
    public SimpleViewModel reset() {
        getSingleValueMap().forEach((name, propVal) -> {
            if (!shouldSkip(name)) {
                // copy values into properties
                reloadProperty(name, propVal);
            }
        });
        getMultiValueMap().forEach((k, observableVal) -> {
            if (!shouldSkip(k)) {
                // copy values into properties
                if (observableVal instanceof Collection<?> collection) {
                    Collection c2 = (Collection) valueMap.get(k);
                    if (c2 != null) {
                        collection.clear();
                        collection.addAll(c2);
                    } else {
                        collection.clear();
                    }
                } else {
                    throw new RuntimeException("Each value should be a Collection not a " + observableVal);
                }
            }
        });
        return this;
    }

    /**
     * Copies Properties to ValueMap.
     * @return returns itself of type SimpleViewModel.
     */
    public SimpleViewModel save() {

        // single values
        getSingleValueMap()
                .forEach((key, value) -> {
                    if (!shouldSkip(key)) {
                        // copy values into properties
                        valueMap.put(key, value.getValue());
                    }
                });
        // multiple values (lists and sets)
        getMultiValueMap()
                .forEach((key, value) -> {
                    if (!shouldSkip(key)) {
                        Observable c = value;
                        if (c instanceof List collection) {
                            valueMap.put(key, new ArrayList<>(collection));
                        } else if (c instanceof Set collection) {
                            valueMap.put(key, new TreeSet<>(collection));
                        }
                    }
                });
        return this;
    }

    /**
     * Sets the model data (valueMap values). TODO: check type before putting it into valueMap. e.g. if StringProperty value should not allow an Integer.
     * @param name property name
     * @param value Raw value to be set as the committed data.
     * @return returns itself of type SimpleViewModel.
     */
    public SimpleViewModel setValue(String name, Object value) {
        if (singleValueMap.containsKey(name) || multiValueMap.containsKey(name)) {
            valueMap.put(name, value);
        }
        return this;
    }

    /**
     * Sets the Property to contain the new value. It does not set the model value.
     * TODO: Skip value is not supported yet. Collections are supported.
     * @param name property name
     * @param value raw value
     * @param skip true to avoid (skip) the copy step when copying property values to model values.
     * @return returns itself of type SimpleViewModel.
     */
    public SimpleViewModel setPropertyValue(String name, Object value, boolean skip) {
        if (value instanceof Collection<?> collection) {
            setPropertyValues(name, collection, skip);
            //throw new RuntimeException("Setting property:%s value cannot be a Collection. Try calling setPropertyValues().".formatted(name));
            return this;
        }
        // If a Property doesn't exist Just create it. TODO: Let's verify whether this should be so strict.
        Property property = getProperty(name);
        if (property == null) {
            property = addProperty(name, value).getProperty(name);
        }
        property.setValue(value);
        return this;
    }
    public SimpleViewModel setPropertyValues(String name, Collection values, boolean skip) {
        Observable observable = multiValueMap.get(name);
        if (observable == null) {
            // create
            addProperty(name, values, skip);
            observable = getObservableCollection(name);
        }
        if (observable instanceof ObservableSet<?> observableSet) {
            observableSet.clear();
            if (values != null) {
                observableSet.addAll(new HashSet<>(values));
            }
        } else if (observable instanceof ObservableList<?> observableList) {
            observableList.clear();
            if (values != null) {
                observableList.addAll(new ArrayList<>(values));
            }
        } else {
            throw new RuntimeException("Not supported Observable Collection for property " + name);
        }
        return this;
    }

    @Override
    public SimpleViewModel setPropertyValue(String name, Object value) {
        return this.setPropertyValue(name, value, false);
    }

    @Override
    public SimpleViewModel setPropertyValues(String name, Collection values) {
        return this.setPropertyValues(name, values, false);
    }

    /**
     * Gets the raw value from the model data.
     * @param name Name of the property
     * @return Raw value from the model data.
     */
    public <T> T getValue(String name) {
        return (T) valueMap.get(name);
    }

    /**
     * Add a new property.
     * @param name property name.
     * @param property The JavaFX Property instance referencing a property name.
     * @return Returns a SimpleViewModel itself following the builder pattern.
     * @param <U> U is derived instance of SimpleViewModel.
     */
    public <U extends SimpleViewModel> U addProperty(String name, Property property) {
        if (singleValueMap.containsKey(name) || multiValueMap.containsKey(name)) {
            Object sVal = singleValueMap.get(name);
            Object mVal = multiValueMap.get(name);
            throw new RuntimeException("The property %s already exists. Property: %s".formatted(name, sVal !=null ? sVal : mVal));
        }
        singleValueMap.put(name, property);
        valueMap.put(name, property.getValue());
        return (U) this;
    }

    /**
     * Add a new property.
     * @param name property name.
     * @param value The raw value type referencing a property name. This will create a JavaFX property to hold the raw value.
     * @return Returns a SimpleViewModel itself following the builder pattern.
     * @param <U> U is derived instance of SimpleViewModel.
     */
    public <U extends SimpleViewModel> U addProperty(String name, String value) {
        return addProperty(name, new SimpleStringProperty(value));
    }

    /**
     * Add a new property as an int value.
     * @param name property name.
     * @param value The raw value type referencing a property name. This will create a JavaFX property to hold the raw value.
     * @return Returns a SimpleViewModel itself following the builder pattern.
     * @param <U> U is derived instance of SimpleViewModel.
     */
    public <U extends SimpleViewModel> U addProperty(String name, int value) {
        return addProperty(name, new SimpleIntegerProperty(value));
    }
    /**
     * Add a new property as a long value.
     * @param name property name.
     * @param value The raw value type referencing a property name. This will create a JavaFX property to hold the raw value.
     * @return Returns a SimpleViewModel itself following the builder pattern.
     * @param <U> U is derived instance of SimpleViewModel.
     */
    public <U extends SimpleViewModel> U addProperty(String name, long value) {
        return addProperty(name, new SimpleLongProperty(value));
    }
    /**
     * Add a new property as a float value.
     * @param name property name.
     * @param value The raw value type referencing a property name. This will create a JavaFX property to hold the raw value.
     * @return Returns a SimpleViewModel itself following the builder pattern.
     * @param <U> U is derived instance of SimpleViewModel.
     */
    public <U extends SimpleViewModel> U addProperty(String name, float value) {
        return addProperty(name, new SimpleFloatProperty(value));
    }
    /**
     * Add a new property as a double value.
     * @param name property name.
     * @param value The raw value type referencing a property name. This will create a JavaFX property to hold the raw value.
     * @return Returns a SimpleViewModel itself following the builder pattern.
     * @param <U> U is derived instance of SimpleViewModel.
     */
    public <U extends SimpleViewModel> U addProperty(String name, double value) {
        return addProperty(name, new SimpleDoubleProperty(value));
    }
    /**
     * Add a new property as a boolean value.
     * @param name property name.
     * @param value The raw value type referencing a property name. This will create a JavaFX property to hold the raw value.
     * @return Returns a SimpleViewModel itself following the builder pattern.
     * @param <U> U is derived instance of SimpleViewModel.
     */
    public <U extends SimpleViewModel> U addProperty(String name, boolean value) {
        return addProperty(name, new SimpleBooleanProperty(value));
    }
    /**
     * Add a new property as a Collection value.
     * @param name property name.
     * @param value The raw value type referencing a property name. This will create a JavaFX property to hold the raw value.
     * @return Returns a SimpleViewModel itself following the builder pattern.
     * @param <U> U is derived instance of SimpleViewModel.
     */
    public <U extends SimpleViewModel> U addProperty(String name, Collection value) {
        return this.addProperty(name, value, false);
    }

    /**
     * Add a new property as a Collection value with a way to skip any copying to model values layer.
     * @param name property name.
     * @param value The raw value type referencing a property name. This will create a JavaFX property to hold the raw value.
     * @param skip True to avoid copying property value to model value layer.
     * @return Returns a SimpleViewModel itself following the builder pattern.
     * @param <U> U is derived instance of SimpleViewModel.
     */
    public <U extends SimpleViewModel> U addProperty(String name, Collection value, boolean skip) {
        // if it's already an observable list or set just add it.
        if (value instanceof ObservableList<?> observableList) {
            multiValueMap.put(name, observableList);
            if (!skip) {
                valueMap.put(name, new ArrayList<>(value));
            }
        } else if (value instanceof ObservableSet observableSet) {
            multiValueMap.put(name, observableSet);
            if (!skip) {
                valueMap.put(name, new TreeSet<>(value));
            }
        } else {
            if (value instanceof Set set) {
                multiValueMap.put(name, FXCollections.observableSet(new LinkedHashSet<>(set)));
                if (!skip) {
                    valueMap.put(name, new TreeSet<>(value));
                }
            } else if (value instanceof List list) {
                multiValueMap.put(name, FXCollections.observableList(new ArrayList<>(value)));
                if (!skip) {
                    valueMap.put(name, new ArrayList<>(value));
                }
            }
        }

        return (U) this;
    }

    /**
     * Add a new property as an Object value with a way to skip any copying to model values layer.
     * @param name property name.
     * @param value The raw value type referencing a property name. This will create a JavaFX property to hold the raw value.
     * @return Returns a SimpleViewModel itself following the builder pattern.
     * @param <U> U is derived instance of SimpleViewModel.
     */
    public <U extends SimpleViewModel> U addProperty(String name, Object value) {
        singleValueMap.put(name, new SimpleObjectProperty(value));
        valueMap.put(name, value);
        return (U) this;
    }

    /**
     * Add a new property as a function to return a collection. A convenient way to pass in a query or fetcher in a lambda.
     * @param name property name.
     * @param value The raw value type referencing a property name. This will create a JavaFX property to hold the raw value.
     * @return Returns a SimpleViewModel itself following the builder pattern.
     * @param <U> U is derived instance of SimpleViewModel.
     */
    public <U extends SimpleViewModel> U addProperty(String name, Function<U, Collection> value) {
        return addProperty(name, value.apply((U)this), false);
    }
    /**
     * Add a new property as a function to return a collection. A convenient way to pass in a query or fetcher in a lambda. Also, skip avoids any copying after a save() or reset().
     * @param name property name.
     * @param value The raw value type referencing a property name. This will create a JavaFX property to hold the raw value.
     * @param skip true to avoid (skip) the copy step when copying property values to model values.
     * @return Returns a SimpleViewModel itself following the builder pattern.
     * @param <U> U is derived instance of SimpleViewModel.
     */
    public <U extends SimpleViewModel> U addProperty(String name, Function<U, Collection> value, boolean skip) {
        return addProperty(name, value.apply((U)this), skip);
    }

    /**
     * Returns a Property instance to be used for the form UI.
     * @param name The property name. e.g. firstName, lastName.
     * @return Returns a Property instance to be used for the form UI.
     * @param <T> T is a JavaFX Property object such as IntegerProperty, StringProperty, etc.
     */
    public <T extends Property> T getProperty(String name) {
        return (T) singleValueMap.get(name);
    }
    public Property removeProperty(String name) {
        valueMap.remove(name);
        return singleValueMap.remove(name);
    }

    /**
     * Returns an observable collection from the property layer. These collections are used in the UI. e.g. ListView containing user's selected items.
     * @param name property name.
     * @return Returns an observable collection from the property layer. These collections are used in the UI. e.g. ListView containing user's selected items.
     */
    public Observable getObservableCollection(String name) {
        return multiValueMap.get(name);
    }

    /**
     * Returns an observable list from the property layer. These collections are used in the UI. e.g. ListView containing user's selected items.
     * @param name property name.
     * @return Returns an observable list from the property layer. These lists are used in the UI. e.g. ListView containing user's selected items.
     */
    public <T> ObservableList<T> getObservableList(String name) {
        return (ObservableList) multiValueMap.get(name);
    }

    /**
     * Returns an observable set from the property layer. These sets are used in the UI. e.g. ListView containing user's selected items.
     * @param name property name.
     * @return Returns an observable set from the property layer. These sets are used in the UI. e.g. ListView containing user's selected items.
     */
    public <T> ObservableSet<T> getObservableSet(String name) {
        return (ObservableSet) multiValueMap.get(name);
    }

    /**
     * Returns and removes a collection from the view model.
     * @param name property name.
     * @return Returns and removes a collection from the view model.
     */
    public Observable removeObservableCollection(String name) {
        return multiValueMap.remove(name);
    }

    /**
     * Returns the raw model values as a List.
     * @param name Property name
     * @return A list of objects from the model values.
     * @param <T> T is the object type in the list.
     */
    public <T> List<T> getList(String name) {
        return (List) valueMap.get(name);
    }

    /**
     * Returns the raw model values as a Set.
     * @param name Property name
     * @return A list of objects from the model values.
     * @param <T> T is the object type in the Set.
     */
    public <T> Set<T> getSet(String name) {
        return (Set) valueMap.get(name);
    }

    /**
     * Returns the raw model values as a Collection.
     * @param name Property name
     * @return A list of objects from the model values.
     * @param <T> T is the object type in the collection.
     */
    public <T> Collection<T> getCollection(String name) {
        return (Collection<T>) valueMap.get(name);
    }

    /**
     * Outputs the view model property and model values
     * @param name the property value
     */
    public void debugProperty(String name) {
        System.out.println(debugPropertyMessage(name));
    }

    /**
     * Returns a string of debug info of the view model property and model values
     * @param name property name to debug
     * @return Returns a string of debug info of the view model property and model values
     */
    public String debugPropertyMessage(String name) {
        if (getProperty(name) != null) {
            return "viewProperty:%s = %s | modelValue:%s = %s".formatted(name, getProperty(name).getValue(), name, getValue(name));
        } else if(getObservableCollection(name) != null) {
            return "viewProperty:%s = %s | modelValue:%s = %s".formatted(name, getObservableCollection(name), name, getCollection(name));
        }

        return "Unknown viewProperty:%s    ".formatted(name);

    }

    /**
     * Returns a Map of property name to Property objects. When ever these are single values such as int, float, string.
     * @return Returns a Map of property name to Property objects.
     */
    protected ObservableMap<String, Property> getSingleValueMap() {
        return singleValueMap;
    }

    /**
     * Returns a Map of property name to Observable type objects such as lists,sets,collections.
     * @return Returns a Map of property name to Observable type objects such as lists,sets,collections.
     */
    protected ObservableMap<String, Observable> getMultiValueMap() {
        return multiValueMap;
    }

    /**
     * Returns a map of property name to raw model values. This can be anything from Lists to Integers.
     * @return Returns a map of property name to raw model values. This can be anything from Lists to Integers.
     */
    protected Map<String, Object> getValueMap() {
        return valueMap;
    }

    /**
     * Returns a set of strings of all property names.
     * @return Returns a set of strings of all property names.
     */
    public Set<String> getPropertyNames() {
        return getValueMap().keySet();
    }

    /**
     * Returns a string of the view model contents.
     * @return Returns a string of the view model contents.
     */
    @Override
    public String toString() {
        return "SimpleViewModel {\n" +
                singleValueMap
                        .keySet()
                        .stream()
                        .map(name -> " " + debugPropertyMessage(name) + "\n")
                        .collect(Collectors.joining()) +
                multiValueMap
                        .keySet()
                        .stream()
                        .map(name -> " " + debugPropertyMessage(name) + "\n")
                        .collect(Collectors.joining()) +
                '}';
    }
}
