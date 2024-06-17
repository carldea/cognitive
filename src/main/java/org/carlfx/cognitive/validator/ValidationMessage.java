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

package org.carlfx.cognitive.validator;



import org.carlfx.cognitive.viewmodel.Validatable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A Java Record representing a validation message can evaluate rules related to a property (field) of a view model.
 * Each message can be an info, error, or warning (messageType). It can optionally have an error code. If it is a
 * global or runtime error the exception can be expressed in the message.
 * <pre>
 *     Messages can have variable substitutions such as
 *     Error occurred in ${myPropertyName}. Should be greater than 20.
 * </pre>
 * @param propertyName Property or field name
 * @param messageType message type is error, warn, or info
 * @param errorCode Any integer for an error code can be used.
 * @param message A message of the validation condition when not valid.
 * @param th Any runtime exception
 */
public record ValidationMessage(String propertyName, MessageType messageType, int errorCode, String message, Throwable th){
    /**
     * Runtime exception and global type error
     * @param messageType the message type such as ERROR, INFO or WARN.
     * @param message the message of the error.
     * @param th An exception during the validation.
     */
    public ValidationMessage(MessageType messageType, String message, Throwable th) {
        this(null, messageType, -1, message, th);
    }

    /**
     * A form field type error message.
     * @param propertyName field property name
     * @param messageType info, error, warning message.
     * @param errorCode an error code. -1 if unknown
     * @param message a message to let the user know what happened.
     */
    public ValidationMessage(String propertyName, MessageType messageType, int errorCode, String message) {
        this(propertyName, messageType, errorCode, message, null);
    }

    /**
     * A form field type error message (no error code).
     * @param propertyName field property name
     * @param messageType info, error, warning message.
     * @param message a message to let the user know what happened.
     */
    public ValidationMessage(String propertyName, MessageType messageType, String message) {
        this(propertyName, messageType, -1, message, null);
    }

    /**
     * Used in templates named substitution. Regex to find properties used in message such as ${myProp1}
     */
    static Pattern PROPERTY_PATTERN = Pattern.compile("\\$\\{(.+)\\}");

    /**
     * Interpolates a string containing property values to substitute.
     * @param vvmodel A validation view model
     * @return A interpolated string
     */
    public String interpolate(Validatable vvmodel) {
        Matcher matcher = PROPERTY_PATTERN.matcher(this.message);
        List<String> props = new ArrayList<>();
        String newMessage = this.message;
        while (matcher.find()) {
            String subPropName = matcher.group();
            String propName = matcher.group(1);
            props.add(matcher.group());
            String friendlyName =vvmodel.getFriendlyName(propName);
            if (friendlyName != null) {
                newMessage = newMessage.replace(subPropName, friendlyName);
            }
        }
        return newMessage!= null ? newMessage : "";
    }
}