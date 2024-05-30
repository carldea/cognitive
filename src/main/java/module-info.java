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
/**
 * Module Cognitive is a JavaFX based MVVM framework capability to decouple presentation logic from controller code.
 */
module org.carlfx.cognitive {
    requires java.base;
    requires javafx.fxml;
    requires javafx.graphics;

    opens org.carlfx.cognitive.loader to javafx.fxml;
    opens org.carlfx.cognitive.validator to javafx.fxml;
    opens org.carlfx.cognitive.viewmodel to javafx.fxml;

    exports org.carlfx.cognitive.loader;
    exports org.carlfx.cognitive.validator;
    exports org.carlfx.cognitive.viewmodel;
}
