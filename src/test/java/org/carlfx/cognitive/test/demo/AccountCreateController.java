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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import org.carlfx.cognitive.loader.InjectViewModel;

import static org.carlfx.cognitive.test.demo.AccountViewModel.AccountField.*;

public class AccountCreateController {

    @FXML
    private Button emailOverlay;

    @FXML
    private TextField emailTF;

    @FXML
    private Tooltip emailTooltip;

    @FXML
    private Button firstNameErrorOverlay;

    @FXML
    private TextField firstNameTF;

    @FXML
    private Tooltip firstNameTooltip;

    @FXML
    private Button lastNameErrorOverlay;

    @FXML
    private TextField lastNameTF;

    @FXML
    private Tooltip lastNameTooltip;

    @FXML
    private Button submitButton;

    @FXML
    private Text transactionMessageText;

    @InjectViewModel
    AccountViewModel accountViewModel;

    @FXML
    public void initialize() {

        // fields change fire a validation
        accountViewModel.doOnChange(()->{
            accountViewModel.validate();
            System.out.println("invalid = " + accountViewModel.invalidProperty().getValue());
            System.out.println("FIRST_NAME = " + accountViewModel.getStringProperty(FIRST_NAME).getValueSafe());
            System.out.println("LAST_NAME = " + accountViewModel.getStringProperty(LAST_NAME).getValueSafe());
            System.out.println("EMAIL = " + accountViewModel.getStringProperty(EMAIL).getValueSafe());
        }, FIRST_NAME, LAST_NAME, EMAIL);

        // When valid property changes do something
        accountViewModel.validProperty().addListener(obs ->
                System.out.println(">>>> is valid: " + accountViewModel.validProperty().getValue()));

        // button disable property is bound to invalid property.
        submitButton.disableProperty().bind(accountViewModel.invalidProperty());

        // Does not decorate field initially. As user types validation occurs for field.
        accountViewModel.validateOnChange(FIRST_NAME, (validationMessages) -> {
            // clear decoration UI code.
            firstNameTooltip.setText("");
            firstNameErrorOverlay.setVisible(!validationMessages.isEmpty());

            // process each message owned by Field
            validationMessages.forEach(validationMessage -> {
                // show overlay and update tooltip
                accountViewModel.updateErrors(validationMessage);

                String message = validationMessage.interpolate(accountViewModel);

                // concatenate. Update UI
                firstNameTooltip.setText(firstNameTooltip.getText() + message + "\n");
            });
        });

        // Does not decorate field initially. As user types validation occurs for field.
        accountViewModel.validateOnChange(LAST_NAME, (validationMessages) -> {
            // clear decoration UI code.
            lastNameTooltip.setText("");
            lastNameErrorOverlay.setVisible(!validationMessages.isEmpty());

            // process each message owned by Field
            validationMessages.forEach(validationMessage -> {
                // show overlay and update tooltip
                accountViewModel.updateErrors(validationMessage);

                String message = validationMessage.interpolate(accountViewModel);

                // concatenate. Update UI
                lastNameTooltip.setText(lastNameTooltip.getText() + message + "\n");
            });
        });

        // Does not decorate field initially. As user types validation occurs for field.
        accountViewModel.validateOnChange(EMAIL, (validationMessages) -> {
            // clear decoration UI code.
            emailTooltip.setText("");
            emailOverlay.setVisible(!validationMessages.isEmpty());

            // process each message owned by Field
            validationMessages.forEach(validationMessage -> {
                // show overlay and update tooltip
                accountViewModel.updateErrors(validationMessage);

                String message = validationMessage.interpolate(accountViewModel);

                // concatenate. Update UI
                emailTooltip.setText(emailTooltip.getText() + message + "\n");
            });
        });

        // every keystroke will check if form is populated.
        firstNameTF.textProperty().bindBidirectional(accountViewModel.getStringProperty(FIRST_NAME));
        lastNameTF.textProperty().bindBidirectional(accountViewModel.getProperty(LAST_NAME));
        emailTF.textProperty().bindBidirectional(accountViewModel.getProperty(EMAIL));

        transactionMessageText.textProperty().bindBidirectional(accountViewModel.getProperty(TRANSACTION_TEXT));
        resetErrorOverlays();
    }
    private void clearForm() {
        // TODO accountViewModel.reset()
        accountViewModel.reset();
        resetErrorOverlays();
        submitButton.setDisable(true);

    }
    public void resetErrorOverlays() {
        firstNameTooltip.setText("");
        firstNameErrorOverlay.setVisible(false);
        lastNameTooltip.setText("");
        lastNameErrorOverlay.setVisible(false);
        emailTooltip.setText("");
        emailOverlay.setVisible(false);
    }

    @FXML
    private void submitAction(ActionEvent actionEvent) {
        accountViewModel.save();

        // Testing the newer invalid property
        if (accountViewModel.invalidProperty().get()) {
            accountViewModel.setPropertyValue(TRANSACTION_TEXT, "Error :-(");
        }

        // Testing the newer valid property
        if (accountViewModel.validProperty().get()){
            System.out.println("Account created successful! ");
            System.out.println("firstName = " + accountViewModel.getValue(FIRST_NAME));
            System.out.println(" lastName = " + accountViewModel.getValue(LAST_NAME));
            System.out.println("    email = " + accountViewModel.getValue(EMAIL));
            accountViewModel.setPropertyValue(TRANSACTION_TEXT, "Success!!!");
        }
    }

}
