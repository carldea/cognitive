package org.carlfx.cognitive.viewmodel;

import org.carlfx.cognitive.validator.*;

import java.util.List;

/**
 * The Validatable interface provides the validation behavior for Validation Model. These methods primarily are a pass through or a delegate to invoke similar methods on the validation manager.
 */
public interface Validatable {
    /**
     * When validators are successful (no errors, warnings, infos) Valid is returned.
     */
    ValidationMessage VALID = null;

    /**
     * Returns the validation manager.
     * @return Returns the validation manager.
     */
    ValidationManager getValidationManager();
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ViewModel itself.
     * @param <U> Any class derived from a ViewModel
     *
     */
    <U extends ViewModel> U addValidator(String name, String friendlyName, StringValidator validator);
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ViewModel itself.
     * @param <U> Any class derived from a ViewModel
     *
     */
    <U extends ViewModel> U addValidator(String name, String friendlyName, BooleanValidator validator);
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ViewModel itself.
     * @param <U> Any class derived from a ViewModel
     *
     */
    <U extends ViewModel> U addValidator(String name, String friendlyName, IntegerValidator validator);
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ViewModel itself.
     * @param <U> Any class derived from a ViewModel
     *
     */
    <U extends ViewModel> U addValidator(String name, String friendlyName, LongValidator validator);
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ViewModel itself.
     * @param <U> Any class derived from a ViewModel
     *
     */
    <U extends ViewModel> U addValidator(String name, String friendlyName, FloatValidator validator);

    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ViewModel itself.
     * @param <U> Any class derived from a ViewModel
     *
     */
    <U extends ViewModel> U addValidator(String name, String friendlyName, DoubleValidator validator);
    
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ViewModel itself.
     * @param <U> Any class derived from a ViewModel
     *
     */
    <U extends ViewModel> U addValidator(String name, String friendlyName, ObjectValidator validator);
    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ViewModel itself.
     * @param <U> Any class derived from a ViewModel
     *
     */
    <U extends ViewModel> U addValidator(String name, String friendlyName, ListValidator validator);

    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ViewModel itself.
     * @param <U> Any class derived from a ViewModel
     *
     */
    <U extends ViewModel> U addValidator(String name, String friendlyName, SetValidator validator);

    /**
     * Allows derived classes to create global type validators.
     * @param name Property name
     * @param friendlyName A string of the validation message
     * @param validator Validator based on datatype
     * @return ViewModel itself.
     * @param <U> Any class derived from a ViewModel
     *
     */
    <U extends ViewModel> U addValidator(String name, String friendlyName, CustomValidator validator);

    /**
     * Goes through all validators to execute creating error, warning, info
     * @return Returns a ViewModel following the builder pattern.
     * @param <U> U is a view model of type ViewModel
     */
    <U extends ViewModel> U validate();

    /**
     * Returns all validators based on property name
     * @param name property name
     * @return List of validators
     */
    default List<Validator<?, ViewModel>> getValidators(String name) {
        return (List<Validator<?, ViewModel>>) getValidationManager().getValidators(name);
    }

    /**
     * Returns all validation messages.
     * @return A list of validation messages.
     */
    default List<ValidationMessage> getValidationMessages() {
        return getValidationManager().getValidationMessages();
    }

    /**
     * True is returned if there are error messages otherwise false.
     * @return True is returned if there are error messages otherwise false.
     */
    default boolean hasErrorMsgs(){
        return getValidationManager().hasMsgType(MessageType.ERROR);
    }

    /**
     * True is returned if there are no error messages otherwise false.
     * @return True is returned if there are no error messages otherwise false.
     */
    default boolean hasNoErrorMsgs() {
        return getValidationManager().hasNoErrorMsgs();
    }

    /**
     * True is returned if there are warning messages otherwise false.
     * @return True is returned if there are warning messages otherwise false.
     */
    default boolean hasWarningMsgs(){
        return getValidationManager().hasWarningMsgs();
    }

    /**
     * True is returned if there are no warning messages otherwise false.
     * @return True is returned if there are no warning messages otherwise false.
     */
    default boolean hasNoWarningMsgs(){
        return getValidationManager().hasNoWarningMsgs();
    }

    /**
     * True is returned if there are info messages otherwise false.
     * @return True is returned if there are info messages otherwise false.
     */
    default boolean hasInfoMsgs(){
        return getValidationManager().hasInfoMsgs();
    }

    /**
     * True is returned if there are no info messages otherwise false.
     * @return True is returned if there are no info messages otherwise false.
     */
    default boolean hasNoInfoMsgs() {
        return getValidationManager().hasNoInfoMsgs();
    }


    /**
     * Invalidate or clear error messages.
     * @return itself
     * @param <U> Derived ViewModel
     */
    <U extends ViewModel> U invalidate();

    /**
     * Returns friendly name of property name.
     * @param propertName property name
     * @return friendly name
     */
    default String getFriendlyName(String propertName) {
        return getValidationManager().getFriendlyName(propertName);
    }

}
