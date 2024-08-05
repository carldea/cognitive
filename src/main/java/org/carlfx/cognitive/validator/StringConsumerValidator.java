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
package org.carlfx.cognitive.validator;

import javafx.beans.property.ReadOnlyStringProperty;
import org.carlfx.cognitive.viewmodel.ViewModel;

/**
 * A string type validator that allows a developer to add many validation messages.
 * This is a tri-(ple) consumer taking a read-only string property, validation result, and view model.
 * <pre>{@code
 * myViewModel.addProperty(FIRST_NAME, "FRED")
 *            .addValidator(FIRST_NAME, "First Name", (ReadOnlyStringProperty prop, ValidationResult vr, ViewModel vm) -> {
 *               if (prop.get().equals(prop.get().toLowerCase()) {
 *                 vr.error("${%s} must be lower case. Entered as %s ".formatted(FIRST_NAME, prop.get()));
 *                 vr.warn("${%s} must be lower case. Entered as %s ".formatted(FIRST_NAME, prop.get()));
 *               }
 *            });
 * }</pre>
 */
public interface StringConsumerValidator extends TypeConsumerValidator<ReadOnlyStringProperty, ValidationResult, ViewModel> {
}
