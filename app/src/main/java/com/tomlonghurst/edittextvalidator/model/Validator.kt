package com.tomlonghurst.edittextvalidator.model

import android.text.Editable

data class Validator(val validationMessage: String? = null, val showErrorRealTime: Boolean = false, val condition: (Editable) -> Boolean)