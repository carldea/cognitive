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

package com.carlfx.cognitive.validator;


/**
 * This indicates an error global to a screen or field level error on an input form.
 * A validator type is whether the error occured for a field property or a global error.
 * For example: if a person view model contains a field called firstName
 * a validator is set up to validate that the field is required (not null and greater than 0 characters).
 * If the UI is wants to indicate messages beside the field they can.
 * For global error message there may be another way to display a coarse grain type error message such as duplicate
 * entity after saving a record.
 */
public enum ValidatorType {
    GLOBAL,
    FIELD
}
