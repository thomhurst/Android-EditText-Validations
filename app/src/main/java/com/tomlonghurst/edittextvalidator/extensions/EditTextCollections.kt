package com.tomlonghurst.edittextvalidator.extensions

import android.widget.EditText

fun <T> Collection<T>.validationPassed(): Boolean where T : EditText{
    return none { !it.validationPassed() }
}

fun <T> Collection<T>.validate(onValidationPassed: () -> Unit, onValidationFailed: (failedEditTexts: List<EditText>) -> Unit) where T : EditText {
    if(validationPassed()) {
        onValidationPassed.invoke()
    } else {
        val failedEditTexts = filter { !it.validationPassed() }
        onValidationFailed.invoke(failedEditTexts)
    }
}