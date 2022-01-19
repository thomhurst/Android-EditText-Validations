package com.tomlonghurst.edittextvalidator

import android.text.Editable
import android.widget.EditText
import com.tomlonghurst.edittextvalidator.enum.EditTextCondition

internal object ConditionConverter {
    internal fun conditionToPredicate(editTextCondition: EditTextCondition): (Editable) -> Boolean {
        return when(editTextCondition) {
            EditTextCondition.NOT_VALID_EMAIL -> { it: Editable -> !it.toString().lowercase().matches("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])".toRegex()) }
            EditTextCondition.NOT_LETTERS_ONLY -> { it: Editable -> it.toString().toCharArray().filterNot { it.isLetter() }.isNotEmpty() }
            EditTextCondition.NOT_NUMBERS_ONLY -> { it: Editable -> it.toString().toCharArray().filterNot { it.isDigit() }.isNotEmpty() }
            EditTextCondition.NOT_LETTERS_OR_NUMBERS_ONLY -> { it: Editable -> it.toString().toCharArray().filterNot { it.isLetterOrDigit() }.isNotEmpty() }
            EditTextCondition.CONTAINS_SPECIAL_CHARACTERS -> { it: Editable -> it.toString().matches("[`~!@#$%^&*()_+\\[\\]\\\\;\',./{}|:\"<>?]".toRegex()) }
            EditTextCondition.IS_EMPTY -> { it: Editable -> it.toString().isEmpty() }
            EditTextCondition.IS_BLANK_OR_EMPTY -> { it: Editable -> it.toString().isBlank() }
        }
    }
}