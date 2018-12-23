package com.tomlonghurst.edittextvalidator.extensions

import android.text.Editable
import android.widget.EditText
import com.tomlonghurst.edittextvalidator.R
import com.tomlonghurst.edittextvalidator.enum.EditTextCondition
import com.tomlonghurst.edittextvalidator.model.Validations

internal val EditText.validations: Validations
     get() {
         val existingValidations = getTag(R.id.validations) as? Validations

         return if(existingValidations == null) {
             val newValidations = Validations(this)
             this.setTag(R.id.validations, newValidations)
             newValidations
         } else {
             existingValidations
         }
     }

/**
 * Returns true if all validation succeeds
 * Returns false if any validation fails
 */
fun EditText.validationPassed() : Boolean {
    return validations.validationPassed()
}

/**
 * onValidationPassed is called if all validation succeeds
 * onValidationFailed is called if any validation fails with a list of the failed validator error messages
 */
fun EditText.validate(onValidationPassed: () -> Unit, onValidationFailed: (errorMessages: List<String>) -> Unit) {
    validations.validate(onValidationPassed, onValidationFailed)
}

/**
 * onValidationPassed is called if all validation succeeds
 * onValidationFailed is called if any validation fails with a list of the failed validator error messages
 */
fun EditText.validateAndShowError(onValidationPassed: () -> Unit, onValidationFailed: (errorMessages: List<String>) -> Unit) {
    validations.validateAndShowError(onValidationPassed, onValidationFailed)
}

/**
 * Fail the validation call if the condition executed returns true
 */
fun EditText.failIf(condition: (Editable) -> Boolean) : EditText {
    this.validations.failIf(condition)
    return this
}


fun EditText.failIf(editTextCondition: EditTextCondition) : EditText {
    this.validations.failIf(editTextCondition)
    return this
}

/**
 * Fail the validation call if the condition executed returns true
 * Will return the error message if failed in the onValidationFail callback
 */
fun EditText.failWithMessageIf(errorMessage: String, condition: (Editable) -> Boolean) : EditText {
    this.validations.failWithMessageIf(errorMessage, condition)
    return this
}

fun EditText.failWithMessageIf(errorMessage: String, editTextCondition: EditTextCondition) : EditText {
    this.validations.failWithMessageIf(errorMessage, editTextCondition)
    return this
}

/**
 * This will also show a validation error in real-time while the user is typing
 */
fun EditText.failWithMessageRealTimeIf(errorMessage: String, condition: (Editable) -> Boolean) : EditText {
    this.validations.failWithMessageRealTime(errorMessage, condition)
    return this
}

fun EditText.failWithMessageRealTimeIf(errorMessage: String, editTextCondition: EditTextCondition) : EditText {
    this.validations.failWithMessageRealTime(errorMessage, editTextCondition)
    return this
}

/**
 * Remove all validators from the edit text
 */
fun EditText.removeAllValidators() {
    validations.removeAllValidators()
}