package com.tomlonghurst.edittextvalidator

import android.widget.EditText
import com.tomlonghurst.edittextvalidator.extensions.validate
import com.tomlonghurst.edittextvalidator.extensions.validationPassed

object EditTextValidation {

    fun <T> validationPassed(vararg editTexts: T): Boolean where T : EditText {
        return editTexts.toList().validationPassed()
    }

    fun <T> validationPassed(editTexts: Collection<T>): Boolean where T : EditText {
        return editTexts.validationPassed()
    }

    fun <T> validate(vararg editTexts: T, onValidationPassed: () -> Unit, onValidationFailed: (failedEditTexts: List<EditText>) -> Unit) where T : EditText {
        editTexts.toList().validate(onValidationPassed, onValidationFailed)
    }

    fun <T> validate(editTexts: Collection<T>, onValidationPassed: () -> Unit, onValidationFailed: (failedEditTexts: List<EditText>) -> Unit) where T : EditText {
        editTexts.validate(onValidationPassed, onValidationFailed)
    }

}