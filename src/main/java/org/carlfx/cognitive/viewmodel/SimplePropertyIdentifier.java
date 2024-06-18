package org.carlfx.cognitive.viewmodel;

import java.util.function.Function;

/**
 * A simple property identifier has a unique id and a user data type object (domain or dto).
 * @param <T> The type as the unique id's type.
 * @param <U> The user data type object's type.
 */
public class SimplePropertyIdentifier<T, U> implements PropertyIdentifier {
    private final String propertyName;
    private final U userData;
    private final Function<U, T> propertyIdFunctor;

    /**
     * A constructor of a SimplePropertyIdentifier.
     * @param propertyName The property name.
     * @param propertyIdFunctor A Function or functional interface by passing in user data and return unique id.
     * @param userData The user data or domain object.
     */
    public SimplePropertyIdentifier (String propertyName, Function<U, T> propertyIdFunctor, U userData) {
        this.propertyName = propertyName;
        this.userData = userData;
        this.propertyIdFunctor = propertyIdFunctor;
    }
    public SimplePropertyIdentifier (Function<U, T> propertyIdFunctor, U userData) {
        this.userData = userData;
        this.propertyIdFunctor = propertyIdFunctor;
        this.propertyName = idToString();

    }
    @Override
    public T getPropertyId() {
        return propertyIdFunctor.apply(getUserData());
    }

    /**
     * A readable property name associated with the property id.
     * @return Returns the property name.
     */
    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public U getUserData() {
        return userData;
    }

    @Override
    public String toString() {
        return getPropertyId().toString() + "(" + getPropertyName() + ")" ;
    }
}
