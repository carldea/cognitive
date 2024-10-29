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

import javafx.scene.Node;
import org.carlfx.cognitive.viewmodel.ViewModel;

import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/**
 * A record that represents a JavaFX Node, Controller, and a collection of view models inside the controller class.
 * A JFXNode is returned after using the FXMLMvvmLoader.make() method.
 * @param node - JavaFX Node to be used on the Scene graph represented from an FXML file.
 * @param controller - An instance of the controller identified inside.
 * @param namedViewModels - A set of named variables referencing View Models inside controller.
 * @param <N> JavaFX Node
 * @param <T> Controller's Class.
 *
 */
public record JFXNode<N extends Node, T>(N node, T controller, Set<NamedVm> namedViewModels) {

    /**
     * Returns a view model after updating based on the variable name.
     * @param variableName variable name injected into the controller.
     * @param updater A update consumer allowing the caller to update the view model
     * @param <S> A derived class of type ViewModel
     */
    public <S extends ViewModel> void update(String variableName, Consumer<S> updater ) {
        Optional<S> viewModelOption = getViewModel(variableName);
        viewModelOption.ifPresent(updater);
    }

    /**
     * Returns a specified view model based on the variable name, otherwise empty.
     * @param variableName The variable name of the view model to fetch.
     * @return An optional containing a ViewModel or empty.
     * @param <S> S is a derived ViewModel class type.
     */
    public <S extends ViewModel> Optional<S> getViewModel(String variableName) {
        Optional<S> found = Optional.empty();
        for (NamedVm namedVm : this.namedViewModels) {
            if (namedVm.variableName().equals(variableName)) {
                ViewModel viewModel = namedVm.viewModel();
                //noinspection unchecked
                found = (Optional<S>) Optional.of(viewModel);
                break;
            }
        }
        return found;
    }
}
