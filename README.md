# Cognitive
A light weight JavaFX (21) forms framework based on the MVVM UI architecture pattern.

View Models maintain the state of a view (Form). In principle view models should contain a controller's presentation logic. 
This allows the developer to test the presentation logic without having to wire up a JavaFX controller during runtime.

## `SimpleViewModel`

Let's start by creating a `SimpleViewModel` with one property that holds a String such as a first name. The objective is
to be able to create a JavaFX text field and bind the value with a view model's property.

To bind properties do the following:
```java
final String FIRST_NAME = "firstName";

// A text field
TextField firstNameTextField = new TextField();

// Create a 
SimpleViewModel personVm = new SimpleViewModel()
        .addProperty(FIRST_NAME, "");

// Bidirectional bind of the first name property and text field's text property.
firstNameTextField.textProperty().bidirectional(personVm.getProperty(FIRST_NAME));

// Set view model property value.
personVm.setPropertyValue(FIRST_NAME, "Fred");

// Output Text field's text property
System.out.println("First name = " + firstNameTextField.getText());
```

Output:

```text
First name = Fred
```
As you can see when ever a user enters text into the text field the view model's property (first name) gets populated and visa-versa.
Usually if you have a UI that is read only or no validation needed a `SimpleViewModel` can be used. A form controls bound to properties on a view 
model you can call the `reset()` method to copy initial model values back into the property values, thus clearing the screen. The `save()` method 
will copy the property values into the model values layer. For simple UIs you can validate fields manually. 

## `ValidationViewModel`

Next, let's look at ValidationViewModel(s). These allow the developer to add validation to properties. The following example 
shows you how to create properties and add validators. These use cases are typically when a user is about to save information. Here they would need to validate before obtaining model values.
```java
final String FIRST_NAME = "firstName";
final String AGE = "age";
final String PHONE = "phone";
final String HEIGHT = "height";
final String COLORS = "colors";
final String FOODS = "foods";
final String THING = "thing";
final String MPG = "mpg";
final String CUSTOM_PROP = "customProp";

ValidationViewModel personVm = new ValidationViewModel()
    .addProperty(FIRST_NAME, "")
    .addValidator(FIRST_NAME, "First Name", (ReadOnlyStringProperty prop, ViewModel vm) -> {
        if (prop.isEmpty().get()) {
            return new ValidationMessage(FIRST_NAME, MessageType.ERROR, "${%s} is required".formatted(FIRST_NAME));
        }
        return VALID;
    })
    .addValidator(FIRST_NAME, "First Name", (ReadOnlyStringProperty prop, ViewModel vm) -> {
        if (prop.isEmpty().get() || prop.isNotEmpty().get() && prop.get().length() < 3) {
            return new ValidationMessage(FIRST_NAME, MessageType.ERROR, "${%s} must be greater than 3 characters.".formatted(FIRST_NAME));
        }
        return VALID;
    })
    .addProperty(PHONE, "111-1111111")
    .addValidator(PHONE, "Phone Number", (ReadOnlyStringProperty prop, ViewModel vm) -> {
        String ph = prop.get();
        Pattern pattern = Pattern.compile("([0-9]{3}\\-[0-9]{3}\\-[0-9]{4})");
        Matcher matcher = pattern.matcher(ph);
        if (!matcher.matches()) {
            return new ValidationMessage(PHONE, MessageType.ERROR, "${%s} must be formatted XXX-XXX-XXXX. Entered as %s".formatted(PHONE, ph));
        }
        return VALID;
    });

// validate view model
personVm.validate();

if (personVm.hasErrors()) {
    for (ValidationMessage vMsg : personVm.getValidationMessages()) {
        vMsg.interpolate(personVm);
        System.out.println("msg Type: %s errorcode: %s, msg: %s".formatted(vMsg.messageType(), vMsg.errorCode(), vMsg.interpolate(personVm)) );
    }
}
    
```
Output:

```text
msg Type: ERROR errorcode: -1, msg: First Name is required
msg Type: ERROR errorcode: -1, msg: First Name must be greater than 3 characters.
msg Type: ERROR errorcode: -1, msg: Phone Number must be formatted XXX-XXX-XXXX. Entered as 111-1111111

```
Above you'll notice the first name field is required and must be more than 3 characters. The phone number is formatted incorrectly.

As each validation message contains a property of the field in question the code can create decorators or badges affixed on a UI control to allow the user to see the error or warning.
```java
 ValidationMessage vMsg = ...;
 Label firstNameError = ...;
 if (FIRST_NAME.equals(vMsg.propertyName())) {
    firstNameError.setText(vMsg.message());    
 }
 
```
Now let's fix the validation issues but instead of calling `validate()` you should call `save()`. A `ValidatationViewModel` overrides the `SimpleViewModel`'s `save()` method.
In essence the save() means property values get copied over to the model value layer. Since a call to validate() happens before save(), when errors occur property values will not be copied.
```java
personVm.setPropertyValue(FIRST_NAME, "Fred");
personVm.setPropertyValue(PHONE, "123-867-5309");
personVm.save();

```

The correct thing to do is obtain the view model's model values by calling the following:

```java
if (personVm.hasErrorMsgs()) {
    return;
}
// Valid!
// Obtain valid values from the view model.
String validFirstName = personVm.getValue(FIRST_NAME); // You should not use personVm.getPropertyValue(FIRST_NAME);

// Write to database 
db.write(...);

```
You can think of the property values of a view model used for the form ui and the model values used on the backend.

## How to inject view models into JavaFX controllers?

When creating JavaFX controller classes you can add view models by using the annotation as follows:

```java
// ... A controllers instance variables

@InjectViewModel
SimpleViewModel personViewModel;

```
When you've created a FXML (view) and a controller you must use the `FXMLMvvmLoader.make()` facility.

```java
Config config = ...;
JFXNode<Pane, PersonController> personJFXNode = FXMLMvvmLoader.make(config);
Pane personPane = personJFXNode.node();
PersonController personController = personJFXNode.controller();
// perform work

```
