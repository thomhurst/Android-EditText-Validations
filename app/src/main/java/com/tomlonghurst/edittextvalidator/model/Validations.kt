package com.tomlonghurst.edittextvalidator.model

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class Validations(private val editText: EditText) {

    private val validators = ListenableArrayList<Validator>(onChange = {
        updateRealTimeValidators()
    })

    private val textWatchers = arrayListOf<TextWatcher>()

    private fun updateRealTimeValidators() {
        textWatchers.apply {
            forEach { editText.removeTextChangedListener(it) }
            clear()
        }

            val textWatcher = object : TextWatcher {
                override fun afterTextChanged(editable: Editable?) {
                    editable?.let { nonNullEditable ->
                        editText.error = null

                        validators.filter { it.showErrorRealTime }.forEach { validator ->
                            if (validator.condition.invoke(nonNullEditable)) {
                                editText.error = validator.validationMessage
                                return@let
                            }
                        }
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

            }
            textWatchers.add(textWatcher)
            editText.addTextChangedListener(textWatcher)
    }

    fun failIf(condition: (Editable) -> Boolean) : Validations {
        validators.add(Validator(null, condition = condition))
        return this
    }

    fun failWithMessageIf(validationMessage: String, condition: (Editable) -> Boolean) : Validations {
        validators.add(Validator(validationMessage, condition = condition))
        return this
    }

    fun failWithMessageRealTime(validationMessage: String, condition: (Editable) -> Boolean) : Validations {
        validators.add(Validator(validationMessage, showErrorRealTime = true, condition = condition))
        return this
    }

    fun removeAllValidators() {
        validators.clear()
    }

    fun validate(onValidationPassed: () -> Unit, onValidationFailed: (errorMessages: List<String>) -> Unit) {
        if(validationPassed()) {
            onValidationPassed.invoke()
        } else {
            onValidationFailed.invoke(validators.filter { it.condition.invoke(editText.text) }.mapNotNull { it.validationMessage }.filter { it.isNotBlank() })
        }
    }

    fun validationPassed() : Boolean {
         return validators.none { it.condition.invoke(editText.text) }
    }

    fun validateAndShowError(onValidationPassed: () -> Unit, onValidationFailed: (errorMessages: List<String>) -> Unit) {
        validators.firstOrNull { it.condition.invoke(editText.text) }?.validationMessage?.let {
            editText.error = it
        }

        validate(onValidationPassed, onValidationFailed)
    }
}