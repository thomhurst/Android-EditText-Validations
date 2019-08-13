# Android EditText Validations
Easily Validate EditTexts

[![](https://jitpack.io/v/thomhurst/Android-EditText-Validations.svg)](https://jitpack.io/#thomhurst/Android-EditText-Validations)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d013eeed324c4ff8a581dfbad88ea779)](https://www.codacy.com/app/thomhurst/Android-EditText-Validations?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=thomhurst/Android-EditText-Validations&amp;utm_campaign=Badge_Grade)
[![Android Weekly](https://img.shields.io/badge/Android%20Weekly-341-blue.svg)](http://androidweekly.net/issues/issue-341)

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

## Usage 
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

You can dynamically get the failed error messages at any time using `EditText.failedValidationMessages` which will return a list of error messages.

## Collections

To validate multiple text fields at once, you have a few ways:

```kotlin
if(editText1.validationPassed() && editText2.validationPassed() && editText3.validationPassed()) {
            ...
        }
```

```kotlin
// Varargs of EditTexts using EditTextValidation helper class
EditTextValidation.validationPassed(editText1, editText2, editText3) // Boolean - True or False

// Collection of EditTexts using EditTextValidation helper class
EditTextValidation.validationPassed(listOf(editText1, editText2, editText3)) // Boolean - True or False
```

```kotlin
// Varargs of EditTexts using EditTextValidation helper class
EditTextValidation.validate(editText1, editText2, editText3,
            onValidationPassed = {
                ...
            },
            onValidationFailed = { failedEditTexts ->
                ...
            })

// Collection of EditTexts using EditTextValidation helper class
EditTextValidation.validate(listOf(editText1, editText2, editText3),
            onValidationPassed = {
                ...
            },
            onValidationFailed = { failedEditTexts ->
                ...
            })
```

```kotlin
// Set of EditTexts using Collection Extension Method
setOf(editText1, editText2, editText3).validationPassed() // Boolean - True or False

// List of EditTexts using Collection Extension Method
listOf(editText1, editText2, editText3).validationPassed() // Boolean - True or False
```

```kotlin
// Set of EditTexts using Collection Extension Method
setOf(editText1, editText2, editText3).validate(
            onValidationPassed = {
                ...
            },
            onValidationFailed = { failedEditTexts ->
                ...
            })
            
// List of EditTexts using Collection Extension Method
listOf(editText1, editText2, editText3).validate(
            onValidationPassed = {
                ...
            },
            onValidationFailed = { failedEditTexts ->
                ...
            })
```

And to easily grab error messages within these collection callbacks:

```kotlin
onValidationFailed = { failedEditTexts ->
                failedEditTexts.forEach { failedEditText ->
                    failedEditText.failedValidationMessages.forEach { failedValidationMessage ->
                        failedEditText.error = failedValidationMessage
                    }
                }
            }
```

If you enjoy, please buy me a coffee :)

<a href="https://www.buymeacoffee.com/tomhurst" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png" alt="Buy Me A Coffee" style="height: auto !important;width: auto !important;" ></a>
