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

import java.util.Optional;

import static org.carlfx.cognitive.test.demo.AccountViewModel.AccountField.EMAIL;

public class DemoApp extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Demo AccountViewModel");
        Config config = new Config(this.getClass().getResource("account-create.fxml"));

        // (OPTIONAL 1) Allows you to update a view model before FXML load.
        config.updateViewModel("accountViewModel", (viewModel -> {
            // acctViewModel.setPropertyValue(...)
        }));

        // FXML Load.
        JFXNode<Pane, AccountCreateController> jfxNode = FXMLMvvmLoader.make(config);

        //  (OPTIONAL 2) First way to modify a view model AFTER form was loaded.
        jfxNode.updateViewModel("accountViewModel", (ValidationViewModel acctViewModel) ->
            acctViewModel.setPropertyValue(EMAIL, "test123"));

        //  (OPTIONAL 3) Second way to modify a view model AFTER from was loaded.
        Optional<AccountViewModel> accountViewModel = jfxNode.getViewModel("accountViewModel");
        accountViewModel.ifPresent(acctViewModel -> acctViewModel.setPropertyValue(EMAIL, "test123ABC"));

        jfxNode.controller().resetErrorOverlays();

        Scene scene = new Scene(jfxNode.node());
        stage.setScene(scene);
        stage.show();
    }
}
