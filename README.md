# Android EditText Validations
Easily Validate EditTexts

[![](https://jitpack.io/v/thomhurst/Android-EditText-Validations.svg)](https://jitpack.io/#thomhurst/Android-EditText-Validations)

This library is best used with Kotlin, and is to help reduce boilerplate code when writing validation rules for EditText fields.

To install:

Add Jitpack to your repositories in your gradle.build file

    allprojects {
        repositories {
          ...
          maven { url 'https://jitpack.io' }
        }
      }

Add the below to your dependencies, again in your gradle.build file

     implementation 'com.github.thomhurst:Android-EditText-Validations:{version}'

Usage 

        val editText = EditText(applicationContext)
        
        editText.apply {
            failWithMessageIf("Text must not be blank") { it.text.toString().isBlank() }
            failIf { it.text.toString().length > 30 }
            failWithMessageIf("Text must be less than 30 characters") { !it.text.toString().isDigitsOnly() }
        }

        if(editText.validationPassed()) {
            // This will return either true or false
        }

        editText
                .failWithMessageIf("Text must not be blank") { it.text.toString().isBlank() }
                .failIf { it.text.toString().length > 30 }
                .failWithMessageIf("Text must be less than 30 characters") { !it.text.toString().isDigitsOnly() }
                .validate(
                        onValidationPassed = {
                            // Code to execute if all the validation has passed
                        },
                        onValidationFailed = { errorMessages ->
                            // Code to execute if execution has failed.
                            // Any failed validation messages are returned here so we could set error messages somewhere.
                            errorMessages.firstOrNull()?.let { firstErrorMessage ->
                                // Show Error Toast
                                Toast.makeText(applicationContext, firstErrorMessage, Toast.LENGTH_LONG).show()

                                // Show error snackbar
                                Snackbar.make(findViewById(android.R.id.content), firstErrorMessage, Snackbar.LENGTH_INDEFINITE).show()

                                // Show edit text error
                                editText.error = firstErrorMessage
                            }

                        }
                )
                // Using validateAndShowError instead will do the exact same as above 
                // and also show an error on the edit text like editText.error = firstErrorMessage

If you enjoy, please buy me a coffee :)

<a href="https://www.buymeacoffee.com/tomhurst" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png" alt="Buy Me A Coffee" style="height: auto !important;width: auto !important;" ></a>
