# Android EditText Validations
Easily Validate EditTexts

[![](https://jitpack.io/v/thomhurst/Android-EditText-Validations.svg)](https://jitpack.io/#thomhurst/Android-EditText-Validations)

This library is best used with Kotlin, and is to help reduce boilerplate code when writing validation rules for EditText fields.

To install:

Add Jitpack to your repositories in your `build.gradle` file

```groovy
allprojects {
    repositories {
      // ...
      maven { url 'https://jitpack.io' }
    }
}
```

Add the below to your dependencies, again in your gradle.build file

```groovy
implementation 'com.github.thomhurst:Android-EditText-Validations:{version}'
```

# Usage 
Using a reference to your edit text:

```kotlin
val editText = EditText(applicationContext)
```
        
You can define failures in an apply block:
        
```kotlin
editText.apply {
    failWithMessageIf(errorMessage = "Text must not be blank", condition = { it.toString().isBlank() })
    failIf { it.toString().length > 30 }
    failWithMessageIf(errorMessage = "Text must be less than 30 characters", condition = { !it.toString().isDigitsOnly() })
}
```
        
Or you can chain failures together:

```kotlin
editText
    .failWithMessageIf(errorMessage = "Text must not be blank", condition = { it.toString().isBlank() })
    .failIf { !it.toString().isDigitsOnly() }
    .failWithMessageIf(errorMessage = "Text must be less than 30 characters", condition = { it.toString().length > 30 })
```
                
As you can see, you can specify your own rules as above, or you can use some of the preset rules by using an enum:

```kotlin
editText
    .failWithMessageIf(errorMessage = "Must not be blank", editTextCondition = EditTextCondition.IS_BLANK_OR_EMPTY)
```
                
The enums available are:

```kotlin
    IS_EMPTY,
    IS_BLANK_OR_EMPTY,
    NOT_VALID_EMAIL,
    NOT_LETTERS_ONLY,
    NOT_NUMBERS_ONLY,
    NOT_LETTERS_OR_NUMBERS_ONLY,
    CONTAINS_SPECIAL_CHARACTERS
```    

Calling `EditText.validationPassed` will return you a boolean `true` or `false`

```kotlin
if (editText.validationPassed()) {
    // This will return either true or false based on the failures defined
}
```

Or you can call `EditText.validate` and define code to be executed in a callback; `onValidationPassed` or `onValidationFailed`

```kotlin
editText
    .validate(
        onValidationPassed = {
            // Code to execute if all the validation has passed
        },
        onValidationFailed = {
            // Code to execute if any validation has failed.
        }
    )
```
                
You can also call `EditText.validateAndShowError` which will execute the same as `validate`, however it will also apply an error with a message (if you've provided one) to your EditText.

`onValidationFailed` will return a list of error messages that failed. You can then display these however you want. Toast, Snackbar or EditText error, etc.

```kotlin
onValidationFailed = { errorMessages ->
    // Any failed validation messages are returned here so we can set error messages however we like
    errorMessages.firstOrNull()?.let { firstErrorMessage ->
        // Show Error Toast
        Toast.makeText(applicationContext, firstErrorMessage, Toast.LENGTH_LONG).show()

        // Show error snackbar
        Snackbar.make(findViewById(android.R.id.content), firstErrorMessage, Snackbar.LENGTH_INDEFINITE).show()

        // Show edit text error
        editText.error = firstErrorMessage
    }
}
```
                        
Using `EditText.failWithMessageRealTimeIf` will cause an EditText error to be displayed in real time. So, if while they're typing, they enter data that breaks your validation, this will be flagged instantly.

If you enjoy, please buy me a coffee :)

<a href="https://www.buymeacoffee.com/tomhurst" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png" alt="Buy Me A Coffee" style="height: auto !important;width: auto !important;" ></a>
