package com.tomlonghurst.edittextvalidator.extensions

import android.widget.EditText
import com.tomlonghurst.edittextvalidator.LazyWithReceiver
import com.tomlonghurst.edittextvalidator.model.Validations

val EditText.validations: Validations
     by LazyWithReceiver<EditText, Validations> { Validations(this) }

fun EditText.validationPassed() : Boolean {
    return validations.validationPassed()
}

fun EditText.validate(onValidationPassed: () -> Unit, onValidationFailed: (errorMessages: List<String>) -> Unit) {
    validations.validate(onValidationPassed, onValidationFailed)
}

fun EditText.validateAndShowError(onValidationPassed: () -> Unit, onValidationFailed: (errorMessages: List<String>) -> Unit) {
    validations.validateAndShowError(onValidationPassed, onValidationFailed)
}

fun EditText.failIf(predicate: (EditText) -> Boolean) : EditText {
    this.validations.failIf(predicate)
    return this
}

fun EditText.failWithMessageIf(errorMessage: String, predicate: (EditText) -> Boolean) : EditText {
    this.validations.failWithMessageIf(errorMessage, predicate)
    return this
}

fun EditText.removeAllValidators() {
    validations.removeAllValidators()
}