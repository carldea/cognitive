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

package org.carlfx.cognitive.validator;

import javafx.beans.property.ReadOnlyIntegerProperty;
import org.carlfx.cognitive.viewmodel.ViewModel;

/**
 * An integer type validator that allows a developer to add many validation messages.
 * This is a tri-(ple) consumer taking a read-only integer property, validation result, and view model.
 * <pre>{@code
 * myViewModel.addProperty(BRAIN_CELLS, 20)
 *            .addValidator(BRAIN_CELLS, "Brain Cells", (ReadOnlyIntegerProperty prop, ValidationResult vr, ViewModel vm) -> {
 *               if (prop.get() < 1 || prop.get() > 20) {
 *                 vr.error("${%s} must be in range 1 - 20. Entered as %s ".formatted(BRAIN_CELLS, prop.get()));
 *               }
 *               if (prop.get() % 2 != 0) {
 *                 vr.error("${%s} must be an even number. Entered as %s ".formatted(BRAIN_CELLS, prop.get()));
 *               }
 *            });
 * }</pre>
 */
public interface IntegerConsumerValidator extends TypeConsumerValidator<ReadOnlyIntegerProperty, ValidationResult, ViewModel> {
}
