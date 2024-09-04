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
package org.carlfx.cognitive.test.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.carlfx.cognitive.loader.Config;
import org.carlfx.cognitive.loader.FXMLMvvmLoader;
import org.carlfx.cognitive.loader.JFXNode;
import org.carlfx.cognitive.viewmodel.ValidationViewModel;

import static org.carlfx.cognitive.test.demo.AccountViewModel.EMAIL;

public class DemoApp extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Demo AccountViewModel");
        Config config = new Config(this.getClass().getResource("account-create.fxml"))
                .updateViewModel("accountViewModel", (ValidationViewModel acctViewModel) -> {
                    acctViewModel
                            .setPropertyValue(EMAIL, "test")
                            .save(true);
                    System.out.println("Debug: " + acctViewModel);
                });
        JFXNode<Pane, Void> jfxNode = FXMLMvvmLoader.make(config);
        Scene scene = new Scene(jfxNode.node());
        stage.setScene(scene);
        stage.show();
    }
}
