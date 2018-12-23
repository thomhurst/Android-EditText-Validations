package com.tomlonghurst.edittextvalidator.model

import android.text.Editable
import com.tomlonghurst.edittextvalidator.ConditionConverter
import com.tomlonghurst.edittextvalidator.enum.EditTextCondition

internal data class Validator(val validationMessage: String? = null, val showErrorRealTime: Boolean = false, val condition: (Editable) -> Boolean) {

    constructor(validationMessage: String? = null, showErrorRealTime: Boolean = false, editTextCondition: EditTextCondition)
            : this(validationMessage, showErrorRealTime,  ConditionConverter.conditionToPredicate(editTextCondition))

}