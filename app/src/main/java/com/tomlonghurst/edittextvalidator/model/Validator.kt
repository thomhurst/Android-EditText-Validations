package com.tomlonghurst.edittextvalidator.model

import android.widget.EditText

data class Validator(val validationMessage: String? = null, val predicate: (EditText) -> Boolean)