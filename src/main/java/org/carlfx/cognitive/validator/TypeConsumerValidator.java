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

import java.util.Objects;

/**
 * A generic type validator that allows a developer to add many validation messages.
 * Derived functional interfaces will extend and target a property's data value type.
 *
 * E.g. To validate a field of type integer the consumer validator allows the developer to add validation messages such as:
 *       two validation messages: error message of value must be less than 100 and must be an even number.
 *
 * @see org.carlfx.cognitive.validator.IntegerConsumerValidator
 *
 * All consumer type validators don't return a validation message like simple validators. This allows
 * the developer to add any number of validation messages. The generic type T is the JavaFX read-only property's
 * data type.
 *
 *
 * @param <T> JavaFX ReadOnly Property based on a data type such as ReadOnlyBooleanProperty, ReadOnlyIntegerProperty
 * @param <ValidationResult> A validation result contains a list of validation messages.
 * @param <ViewModel> The view model validating against.
 */
public interface TypeConsumerValidator<T, ValidationResult, ViewModel> {

    /**
     * Performs this operation on the given arguments.
     * @param t
     * @param validationResult
     * @param viewModel
     */
    void accept(T t, ValidationResult validationResult, ViewModel viewModel);

    default TypeConsumerValidator<T, ValidationResult, ViewModel> andThen(TypeConsumerValidator<? super T, ValidationResult, ViewModel> after) {
        Objects.requireNonNull(after);
        return (l, r, x) -> {
            accept(l, r, x);
            after.accept(l, r, x);
        };
    }
}
