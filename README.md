[Cognitive](https://github.com/carldea/cognitive/wiki)
# Cognitive
A light weight JavaFX (21) forms framework based on the MVVM UI architecture pattern.

View Models maintain the state of a view (Form). In principle view models should contain a controller's presentation logic.
This allows the developer to test the presentation logic without having to wire up a JavaFX controller during runtime.

# Introduction
Software developers creating form based applications will inevitably stumble across the single most important UI architectural design pattern [Model View Controller](http://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller) or **MVC** in short. This concept has paved the way for many frameworks which provide a good (acceptable) [separation of concerns.](http://en.wikipedia.org/wiki/Separation_of_concerns)

However, there are drawbacks to the MVC pattern (especially in JavaFX). One main drawback is that the controller layer can be difficult if not impossible to test. It is especially concerning when you are JavaFX developer who has worked with FXML and controller classes.

The bottom-line issue are UI components, model (data) and presentation logic are coupled inside a controller class. Because UI components are available during runtime it difficult to test interactions (presentation logic) between the model layer and UI.

**So, what is a solution?**

You guessed it! The **MVVM** architecture pattern.

## What is MVVM?

MVVM is an architectural pattern that isolates the business logic/back-end data(Presentation Logic) from the view (UI code). It was designed to simplify user interfaces. According to Wikipedia the [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) is a variation of [Martin Fowler](https://en.wikipedia.org/wiki/Martin_Fowler_(software_engineer))'s [Presentation Model design pattern](https://martinfowler.com/eaaDev/PresentationModel.html).

Next, let's see how to apply the MVVM UI pattern to an existing JavaFX Application

# Converting JavaFX MVC to the MVVM UI pattern

JavaFX MVC (Model View Controller) has some drawbacks in terms of interactions between the UI and Model (domain model). One of the drawbacks are UI elements such as `Button`s and `TextField`s are bound to the View (FXML) and the state of a form.

![Converting_JavaFX_MVC-to-MVVM](https://github.com/carldea/cognitive/assets/1594624/127d1ce4-026f-42d8-b129-8b0f904feacb)


As shown above you can treat the JavaFX FXML & Controller class as the **View** and the Model remains the same. The only difference is the ViewModels will contain much of the state and presentation logic.

Let's look at a comparison between a regular controller code to mimic a save operation.

### This is a typical controller without the use of a view model.
Below is an example of how a UI form is about to save data.
```java
/**
 * User clicks on save button to save contact information.
 * @param ae event 
 */
@FXML
private void saveAction(ActionEvent ae) {
   // copy user's input
   String firstName = firstNameTextField.getText();
   String lastName = lastNameTextField.getText();
   
   // validate user's input
   // if valid write to database and reset ui.

}
```
Now, let's look at how to use a view model in a controller class.

### Using ValidationViewModels in a controller
To use view models you can have presentation logic or even database calls inside so the view model can be tested without assembling a Controller. A test could populate a view model with the correct values. Remember a view model does not contain any UI controls. The code below is rewritten to use view models.

```java
@FXML
private void saveAction(ActionEvent ae) {
    personViewModel.save(); // validates
    if (personViewModel.hasErrorMsgs()) {
       // apply messages to badges or decorate control for fields or global messages.  
    } else {
       // view model get model values and has logic to persist data.
       // personViewModel.write() or personViewModel.save( () -> db.write(...)); 
    }
}
```

Now that you see lots of the work is using the view models instead of methods or ui code inside the controller class. The goal is to remove state and presentation logic from the controller class.

Let's look at the two main implementations of the ViewModel interface SimpleViewModel and ValidationViewModel.

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