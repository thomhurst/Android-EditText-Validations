package com.tomlonghurst.edittextvalidator

import android.widget.EditText
import com.tomlonghurst.edittextvalidator.extensions.validate
import com.tomlonghurst.edittextvalidator.extensions.validationPassed

/**
 * Helper for validating EditText's
 */
object EditTextValidation {

    /**
     * Returns true if all EditTexts pass validation, otherwise false
     * @sample EditTextValidation.validationPassed(EditText1, EditText2, EditText3).validationPassed()
     */
    fun <T> validationPassed(vararg editTexts: T): Boolean where T : EditText {
        return editTexts.toList().validationPassed()
    }

    /**
     * Returns true if all EditTexts pass validation, otherwise false
     * @sample EditTextValidation.validationPassed(listOf(EditText1, EditText2, EditText3)).validationPassed()
     */
    fun <T> validationPassed(editTexts: Collection<T>): Boolean where T : EditText {
        return editTexts.validationPassed()
    }

    /**
     * Executes onValidationPassed if all EditTexts pass validation, otherwise executes onValidationFailed and returns the failed EditText's in the unit
     * @sample EditTextValidation.validationPassed(EditText1, EditText2, EditText3).validate(onValidationPassed { println("Pass") }, onValidationFailed { println("Failed") })
     */
    fun <T> validate(vararg editTexts: T, onValidationPassed: () -> Unit, onValidationFailed: (failedEditTexts: List<EditText>) -> Unit) where T : EditText {
        editTexts.toList().validate(onValidationPassed, onValidationFailed)
    }

    /**
     * Executes onValidationPassed if all EditTexts pass validation, otherwise executes onValidationFailed and returns the failed EditText's in the unit
     * @sample EditTextValidation.validationPassed(listOf(EditText1, EditText2, EditText3)).validate(onValidationPassed { println("Pass") }, onValidationFailed { println("Failed") })
     */
    fun <T> validate(editTexts: Collection<T>, onValidationPassed: () -> Unit, onValidationFailed: (failedEditTexts: List<EditText>) -> Unit) where T : EditText {
        editTexts.validate(onValidationPassed, onValidationFailed)
    }

}