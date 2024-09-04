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
module org.carlfx.cognitive.test {

    requires java.base;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires org.slf4j;
    requires org.carlfx.cognitive;
    requires org.junit.jupiter.engine;
    requires org.junit.jupiter.params;
    requires org.junit.jupiter.api;
    opens org.carlfx.cognitive.test to org.junit.platform.commons;
    opens org.carlfx.cognitive.test.demo to javafx.fxml, org.carlfx.cognitive;
    exports org.carlfx.cognitive.test;
    exports org.carlfx.cognitive.test.demo;
}
