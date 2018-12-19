package com.tomlonghurst.edittextvalidator.model

import android.widget.EditText

class Validations(private val editText: EditText) {

    private val validators = arrayListOf<Validator>()

    fun failIf(predicate: (EditText) -> Boolean) : Validations {
        validators.add(Validator(null, predicate))
        return this
    }

    fun failWithMessageIf(validationMessage: String, predicate: (EditText) -> Boolean) : Validations {
        validators.add(Validator(validationMessage, predicate))
        return this
    }

    fun removeAllValidators() {
        validators.clear()
    }

    fun validate(onValidationPassed: () -> Unit, onValidationFailed: (errorMessages: List<String>) -> Unit) {
        if(validationPassed()) {
            onValidationPassed.invoke()
        } else {
            onValidationFailed.invoke(validators.filter { it.predicate.invoke(editText) }.mapNotNull { it.validationMessage }.filter { it.isNotBlank() })
        }
    }

    fun validationPassed() : Boolean {
        val failed = validators.filter { it.predicate.invoke(editText) }

        if(failed.isNotEmpty()) {
            return false
        }

        return true
    }

    fun validateAndShowError(onValidationPassed: () -> Unit, onValidationFailed: (errorMessages: List<String>) -> Unit) {
        validate(onValidationPassed, onValidationFailed)

        val failed = validators.filter { it.predicate.invoke(editText) }.firstOrNull()?.validationMessage?.let {
            editText.error = it
        }
    }
}