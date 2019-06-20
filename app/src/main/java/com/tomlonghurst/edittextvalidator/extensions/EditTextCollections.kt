package com.tomlonghurst.edittextvalidator.extensions

import android.widget.EditText

/**
 * Returns true if all EditTexts pass validation, otherwise false
 * @sample listOf(EditText1, EditText2, EditText3).validationPassed()
 */
fun <T> Collection<T>.validationPassed(): Boolean where T : EditText{
    return none { !it.validationPassed() }
}

/**
 * Executes onValidationPassed if all EditTexts pass validation, otherwise executes onValidationFailed and returns the failed EditText's in the unit
 * @sample listOf(EditText1, EditText2, EditText3).validate(onValidationPassed { println("Pass") }, onValidationFailed { println("Failed") })
 */
fun <T> Collection<T>.validate(onValidationPassed: () -> Unit, onValidationFailed: (failedEditTexts: List<EditText>) -> Unit) where T : EditText {
    if(validationPassed()) {
        onValidationPassed.invoke()
    } else {
        val failedEditTexts = filter { !it.validationPassed() }
        onValidationFailed.invoke(failedEditTexts)
    }
}