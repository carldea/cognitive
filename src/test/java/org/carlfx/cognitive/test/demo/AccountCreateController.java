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

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import org.carlfx.cognitive.loader.InjectViewModel;
import org.carlfx.cognitive.validator.ValidationMessage;

import static org.carlfx.cognitive.test.demo.AccountViewModel.*;

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

        // check if populated.
        ChangeListener isNotPopulatedChecker = (obs, oldValue, newValue) -> {
            resetErrorOverlays();
            // TODO the following is ugly.
            // invoke validator and submit button state will be set.
            ValidationMessage validationMessage = accountViewModel.getValidators(IS_NOT_POPULATED).stream().findAny().get().apply(null, accountViewModel);
            if (validationMessage != null) {
                System.out.println(validationMessage.message());
            }
        };

        // every keystroke will check if form is populated.
        firstNameTF.textProperty().addListener(isNotPopulatedChecker);
        lastNameTF.textProperty().addListener(isNotPopulatedChecker);
        emailTF.textProperty().addListener(isNotPopulatedChecker);

        firstNameTF.textProperty().bindBidirectional(accountViewModel.getProperty(FIRST_NAME));
        lastNameTF.textProperty().bindBidirectional(accountViewModel.getProperty(LAST_NAME));
        emailTF.textProperty().bindBidirectional(accountViewModel.getProperty(EMAIL));

        submitButton.disableProperty().bind(accountViewModel.getProperty(SUBMIT_BUTTON_STATE));
        transactionMessageText.textProperty().bindBidirectional(accountViewModel.getProperty(TRANSACTION_TEXT));
    }
    private void clearForm() {
        // TODO accountViewModel.reset()
        accountViewModel.setPropertyValue(FIRST_NAME, "");
        accountViewModel.setPropertyValue(LAST_NAME, "");
        accountViewModel.setPropertyValue(EMAIL, "");
        accountViewModel.setPropertyValue(TRANSACTION_TEXT, "");

        // TODO refactor to use view model instead
        emailOverlay.setVisible(false);
        emailTooltip.setText("");
        firstNameErrorOverlay.setVisible(false);
        firstNameTooltip.setText("");
        lastNameErrorOverlay.setVisible(false);
        lastNameTooltip.setText("");
        submitButton.setDisable(true);


    }
    private void resetErrorOverlays() {
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
        if (accountViewModel.hasErrorMsgs()) {
            resetErrorOverlays();
            accountViewModel.getValidationMessages().forEach(validationMessage -> {
                // show overlay and update tooltip
                accountViewModel.updateErrors(validationMessage);

                System.out.println( validationMessage );

                String propName = validationMessage.propertyName();
                String message = validationMessage.interpolate(accountViewModel);

                if (FIRST_NAME.equals(propName)) {
                    // concatenate.
                    firstNameTooltip.setText(firstNameTooltip.getText() + message + "\n");
                    firstNameErrorOverlay.setVisible(true);
                } else if (LAST_NAME.equals(propName)) {
                    lastNameTooltip.setText(message);
                    lastNameErrorOverlay.setVisible(true);
                } else if (EMAIL.equals(propName)) {
                    emailTooltip.setText(message);
                    emailOverlay.setVisible(true);
                }
            });
            accountViewModel.setPropertyValue(TRANSACTION_TEXT, "Error :-(");
        } else {
            System.out.println("Account created successful! ");
            System.out.println("firstName = " + accountViewModel.getValue(FIRST_NAME));
            System.out.println(" lastName = " + accountViewModel.getValue(LAST_NAME));
            System.out.println("    email = " + accountViewModel.getValue(EMAIL));

            accountViewModel.setPropertyValue(TRANSACTION_TEXT, "Success!!!");
        }
    }

}
