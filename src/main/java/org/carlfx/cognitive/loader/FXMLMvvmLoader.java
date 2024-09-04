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
package org.carlfx.cognitive.loader;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.carlfx.cognitive.viewmodel.ViewModel;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;
import java.util.function.Consumer;

/**
 * An FXML loader capable of injecting ViewModels into Controller classes.
 * Returns a JFXNode object containing the Node, Controller and set of NamedVm objects.
 * NamedVm objects represent a variable name and view model. This allows the caller to inject many view models into a
 * controller. Since they are created inside a controller the loader will return the known set of variables and their view models.
 * The following are the various use cases.
 * <pre>
 *     1. Controller has a field annotated with @InjectViewModel instantiate the view model to be assigned.
 *     2. Controller receives a NamedVm to be referenced instead of instantiating a view model.
 *     3. Caller supplies a newly instantiated instance of a Controller to be passed into make().
 *     4. Caller supplies a controller class to be passed into make().
 *     5. FXML specifies a controller class. Caller will supply no controller into make().
 * </pre>
 */
public class FXMLMvvmLoader {

    private FXMLMvvmLoader(){}

    /**
     * Creates a JFXNode representing the JavaFX Node (FXML), Controller instance and a list of Named
     * @param fxml URL of the FXML file in resources.
     * @param namedViewModels The named view models. A controller class can have any number of view models (annotated with InjectViewModel).
     * @return A JFXNode instance representing the JavaFX Node (FXML), Controller instance and a list of Named.
     */
    public static JFXNode make(URL fxml, NamedVm...namedViewModels) {
        return make(new Config(fxml), namedViewModels);
    }

    /**
     * Creates a JFXNode representing the JavaFX Node (FXML), Controller instance and a list of Named
     * @param fxml URL of the FXML file in resources.
     * @param controllerClass A JavaFX controller class.
     * @param namedViewModels The named view models. A controller class can have any number of view models (annotated with InjectViewModel).
     * @return A JFXNode instance representing the JavaFX Node (FXML), Controller instance and a list of Named.
     */
    public static JFXNode make(URL fxml, Class controllerClass, NamedVm...namedViewModels) {
        return make(new Config(fxml, controllerClass), namedViewModels);
    }

    /**
     * Creates a JFXNode representing the JavaFX Node (FXML), Controller instance and a list of Named
     * @param fxml URL of the FXML file in resources.
     * @param controller A JavaFX controller instance. A caller may construct their own instance to be used.
     * @param namedViewModels The named view models. A controller class can have any number of view models (annotated with InjectViewModel).
     * @return A JFXNode instance representing the JavaFX Node (FXML), Controller instance and a list of Named.
     */
    public static JFXNode make(URL fxml, Object controller, NamedVm...namedViewModels) {
        return make(new Config(fxml, controller), namedViewModels);
    }

    /**
     * Creates a JFXNode representing the JavaFX Node (FXML), Controller instance and a list of Named
     * @param config A Config object containing URL(FXML), and other configurations to assemble a JFXNode.
     * @param namedViewModels A list of named view models. A controller class can have any number of view models (annotated with InjectViewModel).
     * @return A JFXNode instance representing the JavaFX Node (FXML), Controller instance and a list of Named.
     */
    public static JFXNode make(Config config, List<NamedVm> namedViewModels) {
        NamedVm[] namedVms = new NamedVm[namedViewModels.size()];
        namedViewModels.toArray(namedVms); // fill the array
        return make(config, namedVms);
    }

    /**
     * Creates a JFXNode representing the JavaFX Node (FXML), Controller instance and a list of Named
     * @param config A Config object containing URL(FXML), and other configurations to assemble a JFXNode.
     * @param namedViewModels variable argument parameter of named view models. A controller class can have any number of view models (annotated with InjectViewModel).
     * @return A JFXNode instance representing the JavaFX Node (FXML), Controller instance and a list of Named.
     */
    public static JFXNode make(Config config, NamedVm...namedViewModels) {
        Map<String, NamedVm> namedViewModelMap = new TreeMap<>();
        // scan from config
        if (config.namedViewModels() != null && config.namedViewModels().length > 0) {
            for(NamedVm namedVm: config.namedViewModels()) {
                namedViewModelMap.putIfAbsent(namedVm.variableName(), namedVm);
            }
        }

        // scan from namedViewModels array
        if (namedViewModels != null && namedViewModels.length > 0) {
            for(NamedVm namedVm: namedViewModels) {
                namedViewModelMap.putIfAbsent(namedVm.variableName(), namedVm);
            }
        }

        Object controller = null;
        if (config.controllerClass() != null && config.controller() == null) {
            // When caller specifies an instance of the javafx controller.
            try {
                controller = config.controllerClass().getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        } else if (config.controller() != null) {
            // When caller specifies the javafx controller class
            controller = config.controller();
        } else {
            // TODO: verify the code below is able to grab the controller from the FXML file
        }

        Node node = null;
        try {
            FXMLLoader loader = new FXMLLoader(config.fxml());
            final Set<NamedVm> namedVms = new LinkedHashSet<>(); // add order
            // When a controller is set inside an FXML file. This creates a controller factory.
            // Here the code will inject the view models.
            if (controller == null) {
                loader.setControllerFactory(aClass -> {
                    try {
                        if (aClass != null) {
                            Object controllerInFxml = aClass.getDeclaredConstructor().newInstance();
                            namedVms.addAll(injectViewModels(controllerInFxml, namedViewModelMap, config.getViewModelUpdaterMap()));
                            return controllerInFxml;
                        }
                        return null;
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                });
            } else {
                // When caller is supplying a controller class or controller instance.
                if (controller != null) {
                    // also get a list of ViewModels
                    namedVms.addAll(injectViewModels(controller, namedViewModelMap, config.getViewModelUpdaterMap()));
                    //LOG.debug("Injecting ViewModels into controller class %s with the following fields: %s".formatted(controller.getClass().getName(), namedVms));
                    loader.setController(controller);
                }
            }

            node = loader.load();
            controller = loader.getController();

            return new JFXNode(node, controller, namedVms);

        } catch (IOException e) {
            throw new RuntimeException("Error unable to load %s.".formatted(config.fxml().toString()),e);
        }

    }

    /**
     * A controller will contain annotated view models that will need instances injected or instantiated.
     * Method allows caller to create a map to be passed in to collect all view models inside the controller.
     * Also, the parameter updateConsumerMap represents a way for the caller to update properties prior to injection.
     * Returns a list of NamedVm objects each representing the variable names to
     * @param controller The JavaFX controller class possibly containing view models
     * @param namedViewModelMap A map consisting of variable name as the key and view model as the value.
     * @param updateConsumerMap An update consumer map, allowing caller to update view model properties prior to injection.
     * @param <U> A typed ViewModel provided for the consumer (code block). This allows the user of the API to declare a
     *           typed ViewModel to downcast if needed.
     *
     * @return A list of NamedVm objects from inside the JavaFX controller class.
     */
    protected static <U extends ViewModel> List<NamedVm> injectViewModels(Object controller, Map<String, NamedVm> namedViewModelMap, Map<String, List<Consumer<U>>> updateConsumerMap) {
        List<NamedVm> names = new ArrayList<>();
        for (Field field : controller.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectViewModel.class)) {

                field.setAccessible(true);
                try {
                    Object viewModel = field.get(controller);
                    String fieldName = field.getName();
                    if (viewModel == null) {
                        if (namedViewModelMap.containsKey(fieldName)) {
                            // replace from map to be injected
                            NamedVm namedVm = namedViewModelMap.get(fieldName);
                            names.add(namedVm);
                            field.set(controller, namedVm.viewModel());
                        } else {
                            // create a new empty constructor of the ViewModel's type.
                            viewModel = field.getType().getDeclaredConstructor().newInstance();
                            names.add(new NamedVm(fieldName, (ViewModel) viewModel));
                            field.set(controller, viewModel);
                        }
                    } else {
                        // The user has chosen to instantiate field.
                    }
                } catch (IllegalAccessException | NoSuchMethodException | InstantiationException |
                         InvocationTargetException e) {
                    throw new RuntimeException("%s class field %s".formatted(controller.getClass().getName(), field),e);
                }
            }
        }
        // Iterate through the available injected view models to run the consumer updaters (callers will update the view model)
        for(NamedVm namedVm:names) {
            if (updateConsumerMap.containsKey(namedVm.variableName())) {
                updateConsumerMap
                        .get(namedVm.variableName()) /* get updaters */
                        .forEach(viewModelConsumer ->
                                viewModelConsumer.accept((U) namedVm.viewModel())); /* view model set ViewModel properties */
            }
        }
        return names;
    }
}
