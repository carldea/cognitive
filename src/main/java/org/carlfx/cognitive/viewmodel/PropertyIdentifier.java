package org.carlfx.cognitive.viewmodel;

/**
 * A property id and name to uniquely represent a field. See setUserData() methods to allow the field to reference any object.
 * @param <T> type of unique identifier. e.g. UUID, Integer, String
 * @param <U> The domain object entity type. e.g. Concept record, or String.
 */
public interface PropertyIdentifier<T, U>{
    /**
     * Returns the unique id of this object.
     * @return Returns the unique id of this object.
     */
    T getPropertyId();

    /**
     * Returns the user data set into this object.
     * @return Returns the user data set into this object.
     */
    U getUserData();
    String getPropertyName();
    /**
     * Compares other PropertyIdentifier instances for equality.
     * @param other Other property identifier to compare.
     * @return Returns true if equals otherwise false.
     */
    default boolean equals(PropertyIdentifier other) {
        return getPropertyId().equals(other.getPropertyId());
    }

    /**
     * A unique integer hash code based on the property id.
     * @return A unique integer hash code based on the property id.
     */
    default int hashcode() {
        return getPropertyId().hashCode();
    }

    default String idToString() {
        return getPropertyId().toString();
    }
}