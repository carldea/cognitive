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

import javafx.collections.ObservableList;
import org.carlfx.cognitive.viewmodel.ViewModel;


/**
 * An observable list type validator that allows a developer to add many validation messages.
 * This is a tri-(ple) consumer taking a read-only list property, validation result, and view model.
 * <pre>{@code
 * myViewModel.addProperty(GRADES, List.of("F", "B")
 *            .addValidator(GRADES, "GRADES", (ReadOnlyListProperty prop, ValidationResult vr, ViewModel vm) -> {
 *               if (prop.get().size() > 1 ) {
 *                 vr.error("${%s} must have at least two values. Entered as %s ".formatted(GRADES, prop.get()));
 *                 if (prop.get().get(0).equals("F")) {
 *                     vr.error("${%s} first grade is an F (failing). Entered as %s ".formatted(GRADES, "F"));
 *                 }
 *               }
 *            });
 * }</pre>
 */
public interface ListConsumerValidator extends TypeConsumerValidator<ObservableList<?>, ValidationResult, ViewModel> {
}
