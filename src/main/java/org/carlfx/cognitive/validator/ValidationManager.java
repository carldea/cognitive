package org.carlfx.cognitive.validator;

import javafx.beans.property.Property;
import org.carlfx.cognitive.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ValidationManager {
    private final Map<String, String> friendlyNameMap = new LinkedHashMap<>();
    private Map<String, List<Validator<?, ViewModel>>> validatorsMap = new LinkedHashMap<>();

    private List<ValidationMessage> validationMessages = new ArrayList<>();

    /**
     * Allows derived classes to create field (property) validators.
     * @param name Property name
     * @param friendlyName Friendly name of field
     * @param validator Validator based on datatype
     * @return ValidationViewModel itself.
     */
    public Validator<?, ViewModel> createFieldValidator(String name, String friendlyName, Validator<?, ViewModel> validator) {
        String alreadyName = friendlyNameMap.get(name);
        if ((alreadyName != null && !alreadyName.isBlank() && (friendlyName == null || friendlyName.isBlank()))) {
            // ignore the caller's friendly name as null or empty
        } else {
            friendlyNameMap.putIfAbsent(name, friendlyName);
        }
        validatorsMap.putIfAbsent(name, new ArrayList());
        List<Validator<?, ViewModel>> validators = validatorsMap.get(name);
        validators.add(validator);
        return validator;
    }

    /**
     * Allows derived classes to create global type validators. e.g. saving to database a duplicate record.
     * @param errorCode an error code assigned to error message.
     * @param name Property name
     * @param errorMsg A string of the validation message
     * @param validator Validator based on datatype
     * @return ValidationViewModel itself.
     * @param <T> Any class derived from a Validator
     */
    public  <T extends Validator> Validator createGlobalValidator(int errorCode, String name, String errorMsg, T validator) {
        validatorsMap.putIfAbsent(name, new ArrayList());
        List validators = validatorsMap.get(name);
        validators.add(validator);
        return validator;
    }

    /**
     * Returns all validators based on property name
     * @param name property name
     * @return List of validators
     */
    public List<Validator<?, ViewModel>> getValidators(String name) {
        return validatorsMap.get(name);
    }
    /**
     * Invalidate or clear error messages.
     * @return itself
     */
    public void invalidate() {
        getValidationMessages().clear();
    }
    public List<ValidationMessage> validate(ViewModel viewModel) {
        validationMessages.clear();
        // with each property contain a validator eval and build validation messages.
        validatorsMap.forEach( (name, validators) -> {
            // each property evaluate validators
            validationMessages.addAll(validators.stream().map(validator -> {
                // call each validator with property and this vm
                // 1. property?
                // 2. list?
                // 3. set?
                // 4. custom global name
                ValidationMessage validationMessage = switch (validator) {
                    case BooleanValidator boolValidator -> boolValidator.apply(viewModel.getProperty(name), viewModel);
                    case CustomValidator customValidator -> customValidator.apply(viewModel.getProperty(name), viewModel);
                    case DoubleValidator doubleValidator -> doubleValidator.apply(viewModel.getProperty(name), viewModel);
                    case FloatValidator floatValidator -> floatValidator.apply(viewModel.getProperty(name), viewModel);
                    case IntegerValidator intValidator -> intValidator.apply(viewModel.getProperty(name), viewModel);
                    case ListValidator listValidator -> listValidator.apply(viewModel.getProperty(name), viewModel);
                    case LongValidator longValidator -> longValidator.apply(viewModel.getProperty(name), viewModel);
                    case ObjectValidator objValidator -> objValidator.apply(viewModel.getProperty(name), viewModel);
                    case SetValidator setValidator -> setValidator.apply(viewModel.getProperty(name), viewModel);
                    case StringValidator stringValidator -> stringValidator.apply(viewModel.getProperty(name), viewModel);
                    default -> throw new IllegalStateException("Unexpected value: " + validator);
                };
                return validationMessage;
            }).filter( vMsg -> vMsg != null).toList());
        });
        return validationMessages;
    }
    /**
     * Returns all validation messages.
     * @return A list of validation messages.
     */
    public List<ValidationMessage> getValidationMessages() {
        return validationMessages;
    }
    /**
     * True is returned if there are error messages otherwise false.
     * @return True is returned if there are error messages otherwise false.
     */
    public boolean hasErrorMsgs() {
        return hasMsgType(MessageType.ERROR);
    }

    /**
     * True is returned if there are no error messages otherwise false.
     * @return True is returned if there are no error messages otherwise false.
     */
    public boolean hasNoErrorMsgs() {
        return !hasErrorMsgs();
    }

    /**
     * True is returned if there are warning messages otherwise false.
     * @return True is returned if there are warning messages otherwise false.
     */
    public boolean hasWarningMsgs() {
        return hasMsgType(MessageType.WARN);
    }

    /**
     * True is returned if there are no warning messages otherwise false.
     * @return True is returned if there are no warning messages otherwise false.
     */
    public boolean hasNoWarningMsgs() {
        return !hasWarningMsgs();
    }

    /**
     * True is returned if there are info messages otherwise false.
     * @return True is returned if there are info messages otherwise false.
     */
    public boolean hasInfoMsgs() {
        return hasMsgType(MessageType.INFO);
    }

    /**
     * True is returned if there are no info messages otherwise false.
     * @return True is returned if there are no info messages otherwise false.
     */
    public boolean hasNoInfoMsgs() {
        return !hasInfoMsgs();
    }

    public boolean hasMsgType(MessageType type){
        if (getValidationMessages() != null) {
            return getValidationMessages().stream().filter(msg -> msg.messageType() == type).findAny().isPresent();
        }
        return false;
    }
    /**
     * Returns friendly name of property name.
     * @param propertName property name
     * @return friendly name
     */
    public String getFriendlyName(String propertName) {
        return friendlyNameMap.get(propertName);
    }

    public Map<String, String> getFriendlyNameMap() {
        return friendlyNameMap;
    }
}
