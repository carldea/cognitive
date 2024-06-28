🚧 Work in progress 🚧
Please view the Wiki [here](https://github.com/carldea/cognitive/wiki)

# Cognitive
A lightweight JavaFX (21) forms a framework based on the MVVM UI architecture pattern.

View Models maintain the state of a view (Form) and, in principle, should contain a controller's presentation logic.
This
MVVM allows the developer to test the presentation logic without having to wire up a JavaFX controller during runtime.

## Quick Start
To use Cognitive in your project, download and install Java 21 JDK. The library depends on JavaFX 21+
To see the demo's code see [Form demo](https://github.com/carldea/cognitive/tree/main/src/test/java/org/carlfx/cognitive/test/demo)

*Gradle:*
```gradle
implementation 'org.carlfx:cognitive:1.0.0'
```

*Maven:*
```xml
<dependency>
    <groupId>org.carlfx</groupId>
    <artifactId>cognitive</artifactId>
    <version>1.0.0</version>
</dependency>
```

Project using Java Modules (JPMS) will want to do the following in the consuming module:
```java

requires org.carlfx.cognitive;

// open for FXML loaders
opens com.mycompany.myproject.controller to javafx.fxml, org.carlfx.cognitive;

```
As you can see, the opens allow FXML and Controller code to inject view models and UI controls using reflection.

# Introduction
Software developers creating form based applications will inevitably stumble across the single most important UI architectural design pattern [Model View Controller](http://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller) or **MVC** in short. This concept has paved the way for many frameworks which provide a good (acceptable) [separation of concerns.](http://en.wikipedia.org/wiki/Separation_of_concerns)

However, there are drawbacks to the MVC pattern (especially in JavaFX). One main drawback is that the controller layer can be difficult (if not impossible) to test. It is especially concerning when you are JavaFX developer who has worked with FXML and controller classes.

The pattern will avoid coupling the UI components, model (data) and presentation logic within a controller class. Because UI components are available during runtime it difficult to test interactions (presentation logic) between the model layer and UI.

**So, what is a solution?**

You guessed it! The **MVVM** UI architecture pattern.

## What is MVVM?

MVVM is an architectural pattern that isolates the business logic/back-end data(Presentation Logic) from the view (UI code). Its goal is to simplify user interface development. According to Wikipedia, the [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) is a variation of [Martin Fowler](https://en.wikipedia.org/wiki/Martin_Fowler_(software_engineer))'s perspective of the [Presentation Model design pattern](https://martinfowler.com/eaDev/PresentationModel.html).

Next, let's see how to apply the MVVM UI pattern to an existing JavaFX Application

# Converting JavaFX MVC to the MVVM UI pattern

Before I show you how to convert a JavaFX MVC structure, let's compare the differences between MVC and the MVVM UI architecture patterns.

## MVC (Model View Controller)
Typically MVC is used in Java Swing or JavaFX UI form type apps.

JavaFX is MVC-based, where developers often follow the [Supervising Controller Pattern](https://martinfowler.com/eaaDev/SupervisingPresenter.html). An excerpt describing the controller's ability to manipulate the view with more complex view logic:

> "Supervising Controller uses a controller both to handle input response but also to manipulate the view to handle more complex view logic. It leaves simple view behavior to the declarative system, intervening only when effects are needed that are beyond what can be achieved declaratively."

In the case of JavaFX, the declarative part would be FXML & CSS (XML representing the View).

JavaFX can also bind ([Properties API](https://dev.java/learn/javafx/properties/)) UI controls and listeners to synchronize the model data. While it is convenient to do this inside a controller(Presenter) class, the code is tightly coupled regarding presentation logic, (complex) view logic, and (possibly) business logic, making code difficult to maintain, debug, and test.

It is difficult to test especially when many UI controls are realized (dependency injected) during runtime, such as `@FXML` annotated UI controls inside controller classes.

![mvc-ui-pattern](https://github.com/carldea/cognitive/assets/1594624/b8d811a2-cab6-4ac4-aa0e-5778a03e0d0b)

Now, let's look at the MVVM pattern and how it differs.

## MVVM (Model View ViewModel)
The MVVM UI pattern is a variation of the [Presentation Model Pattern](https://martinfowler.com/eaaDev/PresentationModel.html). Both MVVM and Presentation Model pattern both achieve the separation of the **View** and the **Model**. An excerpt from Martin Fowler's section on **When to use it?**
> "Presentation Model allows you to write logic completely independent of the views used for display. You also do not need to rely on the view to store state."

What's important is that the `ViewModel` manages the state. Later, we'll look at how to bind data between the View and ViewModel to synchronize model data.

The following is how MVVM is normally depected:

![mvvm-ui-pattern](https://github.com/carldea/cognitive/assets/1594624/6e290bc4-2b5e-475f-991f-291b196e207f)

As you will notice, the **model** does not update the view layer, which he main difference between MVVM and MVC.

The main advantage is testing **presentation logic** separately from the UI form and its associated JavaFX Controller class.

Now that you've seen the pros and cons between MVC and MVVM how do we conceptually convert a JavaFX MVC UI form into the MVVM pattern?

**Hint:**  Refactor (pull out) presentation logic and business logic away from JavaFX controllers into ViewModels or Model (services).

## Converting a JavaFX MVC Form UI using the MVVM pattern
Below is a conceptual way to think of JavaFX using the MVVM UI pattern.

Cognitive is an un-opinionated library that let's you to refactor things at your own pace. As you get comfortable, you'll notice that JavaFX controller classes contain less code where presentation logic is move off to the ViewModels.

![javafx-mvvm-ui-pattern](https://github.com/carldea/cognitive/assets/1594624/aa922411-e4fb-456a-93bc-6f8c7a333ad7)


As shown above, you can treat the JavaFX FXML & Controller class as the **View**, and the Model will remain the same. The only difference is that the ViewModels will contain much of the state of the UI form and presentation logic. The objective is to make the view very stupid.

Now that you know conceptually, let's look at a code example of an MVC-style controller with a save operation.

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
Using view models, you can have presentation logic or business logic. When testing presentation logic, you can populate a view model with the correct values without modifying the UI. Remember, a view model does not contain any UI controls. Shown below is an example of using a view model.

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

Now that you see, much of the work uses view models instead of methods or UI code inside the controller class. The developer aims to remove state and presentation logic from the controller class.

Let's look at the two main implementations of the ViewModel interface SimpleViewModel and ValidationViewModel.

## `SimpleViewModel`
Let's start by creating a `SimpleViewModel` with one property with a String such as a first name. The objective is
To create a JavaFX text field and bind the value with a view model's property.

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
As you can see whenever a user enters text into the text field (`TextField`) the view model's property (first name) gets populated and visa-versa.

Usually if you have a UI Form that has read-only or no validation needed a `SimpleViewModel` can be used. A form controls bound to properties on a view model you can call the `reset()` method to copy initial model values back into the property values, thus clearing the screen. The `save()` method
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

The `save()` method essentially copies property values into the model value layer. Since a call to the `validate()` method happens before the `save()` method, property values will not be copied when errors occur.

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
## Demo - Account Creation Form

Here's a demo UI form without values. As a user types into fields, the **validator** for populating the form will update the submit button state. If any fields are not populated, the save button will be disabled.

When pressing the submit button the validation behavior occurs afterwards. To see the demo's code see [Form demo](https://github.com/carldea/cognitive/tree/main/src/test/java/org/carlfx/cognitive/test/demo)

![demo1](https://github.com/carldea/cognitive/assets/1594624/320c19f2-6545-4f43-8762-522ec0100b93)

Input in error (after save to validate)

<img width="432" alt="demo2" src="https://github.com/carldea/cognitive/assets/1594624/069d2af3-fd24-469b-88a2-2cc28d08d2ac">

When the submit is pressed show the overlay icons for each field with an error.

<img width="595" alt="demo3" src="https://github.com/carldea/cognitive/assets/1594624/ced72d75-6681-4d42-a5c8-974ea70cab6f">

A user with valid input.

<img width="488" alt="demo4" src="https://github.com/carldea/cognitive/assets/1594624/66c147a1-abc6-4ca7-b018-a4b6ba8b545c">

# References
The following are links on the topic of UI/UX and Patterns:
* [GUI Architectural Patterns by Martin Fowler](https://martinfowler.com/eaaDev/uiArchs.html) - Model View Controller, Model-View-Presenter
* [Flow Synchronization Pattern](https://martinfowler.com/eaaDev/FlowSynchronization.html) - How to synch data between UI form and domain objects.
* [Separated Presentation](https://martinfowler.com/eaaDev/SeparatedPresentation.html) - Separating presentation and business logic code in separate layers.
* [JavaFX Forms Framework](https://carlfx.wordpress.com/2009/07/29/javafx-forms-framework-part-1/)