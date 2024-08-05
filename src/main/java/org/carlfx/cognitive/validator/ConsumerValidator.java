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

import org.carlfx.cognitive.viewmodel.ViewModel;

import java.util.function.BiConsumer;

/** All consumer type validators don't return a validation message like simple validators, but allows
 * the developer to add any number of validation messages.
 *
 * A ConsumerValidator is a bi consumer that provides the caller with an instance of a validation result
 * and view model. A validation result allows the caller to add any number of validation messages to the
 * validation result.
 */
public interface ConsumerValidator extends BiConsumer<ValidationResult, ViewModel> {
}
