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


import org.carlfx.cognitive.viewmodel.ViewModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * A Config represents an FXML URL, controller class, controller object, zero to many NamedVm objects.
 * This convenience object help define configurations before calling the make() methond on the FXMLMvvmLoader.
 */
public class Config {
    private URL fxml;
    private Class controllerClass;
    private Object controller;

    private NamedVm[] namedViewModels;

    // New support to update view models that already exist.
    private Map<String, List<Consumer<ViewModel>>> viewModelUpdaterMap;

    /**
     * Returns the URL set as the location of the FXML file in the resources area.
     * @return Returns the URL set as the location of the FXML file in the resources area.
     */
    public URL fxml() {
        return fxml;
    }

    /**
     * Default constructor.
     */
    public Config() {
    }

    /**
     * Constructor accepting the FXML URL as a parameter.
     * @param fxml the URL set as the location of the FXML file in the resources area.
     */
    public Config(URL fxml) {
        this.fxml = fxml;
    }

    /**
     * Constructor accepting the FXML URL as a parameter.
     * @param fxml the URL set as the location of the FXML file in the resources area.
     * @param controllerClass a JavaFX controller class. Assuming this has an empty constructor.
     */
    public Config(URL fxml, Class controllerClass) {
        this.fxml = fxml;
        this.controllerClass = controllerClass;
    }

    /**
     * Constructor accepting the FXML URL as a parameter.
     * @param fxml the URL set as the location of the FXML file in the resources area.
     * @param controllerClassInstance a JavaFX controller instance. Caller has instantiated their own JavaFX controller instance.
     */
    public Config(URL fxml, Object controllerClassInstance) {
        if (controllerClassInstance instanceof NamedVm) {
            throw new RuntimeException("Not a JavaFX Controller class");
        }
        this.fxml = fxml;
        this.controller = controllerClassInstance;
    }

    /**
     * Setting FXML URL location and returning the Config following the builder pattern.
     * @param fxml The URL set as the location of the FXML file in the resources area.
     * @return Returns itself (Config).
     */
    public Config fxml(URL fxml) {
        this.fxml = fxml;
        return this;
    }

    /**
     * Returns the JavaFX controller class configuration.
     * @return Returns the JavaFX controller class configuration.
     */
    public Class controllerClass() {
        return controllerClass;
    }

    /**
     * Setting JavaFX controller class and returning the Config following the builder pattern.
     * @param controllerClass The JavaFX controller class to set.
     * @return Returns itself (Config).
     */
    public Config controllerClass(Class controllerClass) {
        this.controllerClass = controllerClass;
        return this;
    }

    /**
     * Returns the JavaFX controller instance from the configuration.
     * @return Returns the JavaFX controller instance from the configuration.
     */
    public Object controller() {
        return controller;
    }

    /**
     * Setting JavaFX controller instance and returning the Config following the builder pattern.
     * @param controllerObject The JavaFX controller instance to set.
     * @return Returns itself (Config).
     */
    public Config controller(Object controllerObject) {
        this.controller = controllerObject;
        return this;
    }

    /**
     * Returns an array of named view models (NamedVm). A named view model is a record holding the variable name and the ViewModel instance.
     *
     * @return Returns an array of named view models (NamedVm). A named view model is a record holding the variable name and the ViewModel instance.
     */
    public NamedVm[] namedViewModels() {
        if (namedViewModels == null) {
            namedViewModels = new NamedVm[0];
        }
        return namedViewModels;
    }

    /**
     * Sets n number of NamedVm instances and returns the Config object. This follows the builder pattern.
     * @param namedViewModels n number of NamedVm instances to be set. Each variable is bound before the initialization phase when using FXMLMvvmLoader.
     * @return Returns the Config object. This follows the builder pattern.
     */
    public Config namedViewModels(NamedVm ...namedViewModels) {
        this.namedViewModels = namedViewModels;
        return this;
    }

    /**
     * Adds a NamedVm instance and returns the Config object. This follows the builder pattern.
     * @param namedVm A NamedVm instance to add to configuration object. Each variable (ViewModel instance) is bound before the initialization phase when using FXMLMvvmLoader.
     * @return Returns the Config object. This follows the builder pattern.
     */
    public Config addNamedViewModel(NamedVm namedVm) {
        int len = 0;
        if (this.namedViewModels != null && this.namedViewModels.length > 0) {
            len = this.namedViewModels.length + 1;
            NamedVm[] viewModels2 = new NamedVm[len];
            System.arraycopy(namedViewModels, 0, this.namedViewModels, 0, len);
            viewModels2[len -1] = namedVm;
            this.namedViewModels = viewModels2;
        } else {
            this.namedViewModels = new NamedVm[1];
            this.namedViewModels[0] = namedVm;
        }
        return this;
    }

    /**
     * Returns a map of variable names to a list of updaters (Consumer objects). The caller will be able to update view models inside of controllers.
     * e.g. A developer wants to update a view model that is injected into a controller class.
     * The controller class contains the following:
     *
     *    {@literal @}InjectViewModel
     *    private SimpleViewModel myViewModel;
     *
     * Since the ViewModel isn't created outside the FXMLMvvm.make(config) it will allow the caller to create a config object to update the view model based on the variable name located in the controller class.
     * <pre>
     * {@code
     *     Config config = new Config();
     *     config.updateViewModel("myViewModel", (viewModel) -> {
     *         viewModel.setPropertyValue("XYZ", 12345)
     *                  .setPropertyValue("ABC", "Hello");
     *     });
     * }
     * </pre>
     *
     * @return Returns a map of variable names (String) as the key and a list of Consumer objects as the value. Each consumer allows you to update the view model properties.
     * This assumes the view model already exists.
     */
    public Map<String, List<Consumer<ViewModel>>> getViewModelUpdaterMap() {
        if (viewModelUpdaterMap == null) {
            viewModelUpdaterMap = new HashMap<>();
        }
        return viewModelUpdaterMap;
    }
    private List<Consumer<ViewModel>> getUpdaterConsumers(String variableName) {
        if (!getViewModelUpdaterMap().containsKey(variableName)) {
            getViewModelUpdaterMap().put(variableName, new ArrayList<>());
        }
        return getViewModelUpdaterMap().get(variableName);
    }

    /**
     * The caller will be able to update view models inside of controllers.
     * e.g. A developer wants to update a view model that is injected into a controller class.
     * The controller class contains the following:
     *
     *    {@literal @}InjectViewModel
     *    private SimpleViewModel myViewModel;
     *
     * Since the ViewModel isn't created outside the FXMLMvvm.make(config) it will allow the caller to create a config object to update the view model based on the variable name located in the controller class.
     * <pre>
     * {@code
     *     Config config = new Config();
     *     config.updateViewModel("myViewModel", (viewModel) -> {
     *         viewModel.setPropertyValue("XYZ", 12345)
     *                  .setPropertyValue("ABC", "Hello");
     *     });
     * }
     * </pre>
     *
     * Another example is specifying a derived view model to be updated.
     * <pre>{@code
     *     Config config = new Config();
     *     config.updateViewModel("myViewModel", (MyViewModel viewModel) -> {
     *         viewModel.setPropertyValue("XYZ", 12345)
     *                  .setPropertyValue("ABC", "Hello");
     *         viewModel.save(true);
     *         viewModel.doSomethingFun();
     *     });
     * }
     * </pre>
     * @param variableName A variable name as an instance variable (of type ViewModel) inside a controller class (annotated with InjectViewModel). These variables are injected or instantiated using FXMLMvvmLoader.
     * @param viewModelConsumer A consumer allows the caller to update property values on the view model.
     * @param <U> A typed ViewModel provided for the consumer (code block). This allows the user of the API to declare a typed ViewModel to downcast if needed.
     * @return Returns itself to follow a builder pattern for easy Configuration creation.
     */
    public <U extends ViewModel> Config updateViewModel(String variableName, Consumer<U> viewModelConsumer) {
        // if variable found grab it and give it to caller.
        getUpdaterConsumers(variableName).add((Consumer<ViewModel>)viewModelConsumer);
        return this;
    }
}
