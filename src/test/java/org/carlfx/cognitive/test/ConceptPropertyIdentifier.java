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
package org.carlfx.cognitive.test;

import org.carlfx.cognitive.viewmodel.PropertyIdentifier;

import java.util.UUID;

public record ConceptPropertyIdentifier(/* Db db, */ConceptRecord concept) implements PropertyIdentifier<UUID, ConceptRecord> {
    @Override
    public UUID getPropertyId() {
        return concept.uuid();
    }

    @Override
    public ConceptRecord getUserData() {
        return concept;
    }


    /**
     * Additional methods to provide full names, short names, etc.
     * @return The full name of the concept
     */
    public String fullName() {
        // able to retrieve concepts full name from database.
        // return db.query(id: concept.uuid() ).fqn()
        return concept.fullName();
    }
    /**
     * A default readable property name for field often used for the UIs label.
     * @return property name to lookup field
     */
    @Override
    public String getPropertyName() {
        // able to retrieve concepts full name from database.
        // return db.query(id: concept.uuid() ).fqn()
        return concept.shortName();
    }

    /**
     * A default readable property name for field often used for the UIs label.
     * @return property name to lookup field
     */
    public String shortName() {
        // able to retrieve concepts full name from database.
        // return db.query(id: concept.uuid() ).fqn()
        return concept.shortName();
    }

}
