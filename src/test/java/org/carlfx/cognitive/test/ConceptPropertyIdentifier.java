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
