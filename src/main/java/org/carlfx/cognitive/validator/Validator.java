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



import org.carlfx.cognitive.viewmodel.ViewModel;

import java.util.function.BiFunction;

/**
 * A validator will valid a view model based on property values or model values. When an error occurs messages are
 * created to allow a UI developer to display as feedback to the User.
 * @param <T> Type T is a JavaFX Read-only Property class. A custom validator T will be of type Void.
 * @param <U> Type U is any derived class from <code>ViewModel</code>
 */
public interface Validator<T, U extends ViewModel> extends BiFunction<T, U, ValidationMessage> {
}
