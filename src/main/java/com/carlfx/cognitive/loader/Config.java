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
package com.carlfx.cognitive.loader;


import com.carlfx.cognitive.viewmodel.ViewModel;

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
    public URL fxml() {
        return fxml;
    }

    public Config() {

    }
    public Config(URL fxml) {
        this.fxml = fxml;
    }
    public Config(URL fxml, Class controllerClass) {
        this.fxml = fxml;
        this.controllerClass = controllerClass;
    }
    public Config(URL fxml, Object controllerClassInstance) {
        if (controllerClassInstance instanceof NamedVm) {
            throw new RuntimeException("Not a JavaFX Controller class");
        }
        this.fxml = fxml;
        this.controller = controllerClassInstance;
    }

    public Config fxml(URL fxml) {
        this.fxml = fxml;
        return this;
    }

    public Class controllerClass() {
        return controllerClass;
    }

    public Config controllerClass(Class controllerClass) {
        this.controllerClass = controllerClass;
        return this;
    }

    public Object controller() {
        return controller;
    }

    public Config controller(Object controllerObject) {
        this.controller = controllerObject;
        return this;
    }

    public NamedVm[] namedViewModels() {
        if (namedViewModels == null) {
            namedViewModels = new NamedVm[0];
        }
        return namedViewModels;
    }

    public Config namedViewModels(NamedVm ...namedViewModels) {
        this.namedViewModels = namedViewModels;
        return this;
    }
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
     * Since the ViewModel isn't created outside the FXMLMvvm.make(config) will allow the caller to create a config object to update the view model.
     * <pre>
     *     Config config = new Config();
     *     config.updateViewModel("myViewModel", (viewModel) -> {
     *         viewModel.setPropertyValue("XYZ", 12345)
     *                  .setPropertyValue("ABC", "Hello");
     *     });
     *
     * </pre>
     *
     * @return
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

    public Config updateViewModel(String variableName, Consumer<ViewModel> viewModelConsumer) {
        // if variable found grab it and give it to caller.
        getUpdaterConsumers(variableName).add(viewModelConsumer);
        return this;
    }
}
