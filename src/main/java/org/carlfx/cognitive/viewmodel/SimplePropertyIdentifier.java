package org.carlfx.cognitive.viewmodel;

import java.util.function.Function;

public class SimplePropertyIdentifier<T, U> implements PropertyIdentifier {
    private final String propertyName;
    private final U userData;
    private final Function<U, T> propertyIdFunctor;
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
