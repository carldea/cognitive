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

import java.util.ArrayList;
import java.util.List;

/**
 * Collects validation messages for one property name.
 */
public class ValidationResult {
    private List<ValidationMessage> messages = new ArrayList<>();

    private final String propertyName;

    /**
     * Constructor
     * @param propertyName Property name or field.
     */
    public ValidationResult(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * Adds an error validation message.
     * @param propertyName Property name
     * @param message Message when validation evaluates to invalid.
     * @param errorCode -1 for unknown otherwise a specific error number.
     * @param throwable A thrown exception.
     */
    public void error(String propertyName, String message, int errorCode, Throwable throwable) {
        messages.add(new ValidationMessage(propertyName, MessageType.ERROR, errorCode, message, throwable));
    }
    /**
     * Adds an error validation message.
     * @param message Message when validation evaluates to invalid.
     * @param errorCode -1 for unknown otherwise a specific error number.
     * @param throwable A thrown exception.
     */
    public void error(String message, int errorCode, Throwable throwable) {
        this.error(propertyName, message, errorCode, throwable);
    }
    /**
     * Adds an error validation message.
     * @param propertyName Property name
     * @param message Message when validation evaluates to invalid.
     * @param throwable A thrown exception.
     */
    public void error(String propertyName, String message, Throwable throwable) {
        messages.add(new ValidationMessage(propertyName, MessageType.ERROR, -1, message, throwable));
    }
    /**
     * Adds an error validation message.
     * @param message Message when validation evaluates to invalid.
     * @param throwable A thrown exception.
     */
    public void error(String message, Throwable throwable) {
        this.error(propertyName, message, throwable);
    }
    /**
     * Adds an error validation message.
     * @param propertyName Property name
     * @param message Message when validation evaluates to invalid.
     * @param errorCode -1 for unknown otherwise a specific error number.
     */
    public void error(String propertyName, String message, int errorCode) {
        messages.add(new ValidationMessage(propertyName, MessageType.ERROR, errorCode, message));
    }
    /**
     * Adds an error validation message.
     * @param message Message when validation evaluates to invalid.
     * @param errorCode -1 for unknown otherwise a specific error number.
     */
    public void error(String message, int errorCode) {
        this.error(propertyName, message, errorCode);
    }
    /**
     * Adds an error validation message.
     * @param propertyName Property name
     * @param message Message when validation evaluates to invalid.
     */
    public void error(String propertyName, String message) {
        messages.add(new ValidationMessage(propertyName, MessageType.ERROR, message));
    }
    /**
     * Adds an error validation message.
     * @param message Message when validation evaluates to invalid.
     */
    public void error(String message) {
        this.error(propertyName, message);
    }

    /**
     * Adds a warning validation message.
     * @param propertyName Property name
     * @param message Message when validation evaluates to invalid.
     * @param errorCode -1 for unknown otherwise a specific error number.
     * @param throwable A thrown exception.
     */
    public void warn(String propertyName, String message, int errorCode, Throwable throwable) {
        messages.add(new ValidationMessage(propertyName, MessageType.WARN, errorCode, message, throwable));
    }
    /**
     * Adds a warning validation message.
     * @param message Message when validation evaluates to invalid.
     * @param errorCode -1 for unknown otherwise a specific error number.
     * @param throwable A thrown exception.
     */
    public void warn(String message, int errorCode, Throwable throwable) {
        this.warn(propertyName, message, errorCode, throwable);
    }

    /**
     * Adds a warning validation message.
     * @param message Message when validation evaluates to invalid.
     * @param throwable A thrown exception.
     */
    public void warn(String propertyName, String message, Throwable throwable) {
        messages.add(new ValidationMessage(propertyName, MessageType.WARN, -1, message, throwable));
    }
    /**
     * Adds a warning validation message.
     * @param message Message when validation evaluates to invalid.
     * @param throwable A thrown exception.
     */
    public void warn(String message, Throwable throwable) {
        this.warn(propertyName, message, throwable);
    }
    /**
     * Adds a warning validation message.
     * @param message Message when validation evaluates to invalid.
     * @param errorCode -1 for unknown otherwise a specific error number.
     */
    public void warn(String propertyName, String message, int errorCode) {
        messages.add(new ValidationMessage(propertyName, MessageType.WARN, errorCode, message));
    }
    /**
     * Adds a warning validation message.
     * @param message Message when validation evaluates to invalid.
     * @param errorCode -1 for unknown otherwise a specific error number.
     */
    public void warn(String message, int errorCode) {
        this.warn(propertyName, message, errorCode);
    }
    /**
     * Adds a warning validation message.
     * @param message Message when validation evaluates to invalid.
     */
    public void warn(String propertyName, String message) {
        messages.add(new ValidationMessage(propertyName, MessageType.WARN, message));
    }
    /**
     * Adds a warning validation message.
     * @param message Message when validation evaluates to invalid.
     */
    public void warn(String message) {
        this.warn(propertyName, message);
    }

    /**
     * Adds an information validation message.
     * @param propertyName Property name
     * @param message Message when validation evaluates to invalid.
     * @param errorCode -1 for unknown otherwise a specific error number.
     * @param throwable A thrown exception.
     */
    public void info(String propertyName, String message, int errorCode, Throwable throwable) {
        messages.add(new ValidationMessage(propertyName, MessageType.INFO, errorCode, message, throwable));
    }
    /**
     * Adds an information validation message.
     * @param message Message when validation evaluates to invalid.
     * @param errorCode -1 for unknown otherwise a specific error number.
     * @param throwable A thrown exception.
     */
    public void info(String message, int errorCode, Throwable throwable) {
        this.info(propertyName, message, errorCode, throwable);
    }
    /**
     * Adds an information validation message.
     * @param propertyName Property name
     * @param message Message when validation evaluates to invalid.
     * @param throwable A thrown exception.
     */
    public void info(String propertyName, String message, Throwable throwable) {
        messages.add(new ValidationMessage(propertyName, MessageType.INFO, -1, message, throwable));
    }
    /**
     * Adds an information validation message.
     * @param message Message when validation evaluates to invalid.
     * @param throwable A thrown exception.
     */
    public void info(String message, Throwable throwable) {
        this.info(propertyName, message, throwable);
    }
    /**
     * Adds an information validation message.
     * @param propertyName Property name
     * @param message Message when validation evaluates to invalid.
     * @param errorCode -1 for unknown otherwise a specific error number.
     */
    public void info(String propertyName, String message, int errorCode) {
        messages.add(new ValidationMessage(propertyName, MessageType.INFO, errorCode, message));
    }
    /**
     * Adds an information validation message.
     * @param message Message when validation evaluates to invalid.
     * @param errorCode -1 for unknown otherwise a specific error number.
     */
    public void info(String message, int errorCode) {
        this.info(propertyName, message, errorCode);
    }
    /**
     * Adds an information validation message.
     * @param propertyName Property name
     * @param message Message when validation evaluates to invalid.
     */
    public void info(String propertyName, String message) {
        messages.add(new ValidationMessage(propertyName, MessageType.INFO, message));
    }
    /**
     * Adds an information validation message.
     * @param message Message when validation evaluates to invalid.
     */
    public void info(String message) {
        this.info(propertyName, message);
    }

    /**
     * Returns a list of ValidationMessage objects.
     * @return Returns a list of ValidationMessage objects.
     */
    public List<ValidationMessage> getMessages(){
        return messages;
    }
}
