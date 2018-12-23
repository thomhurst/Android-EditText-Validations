package com.tomlonghurst.edittextvalidator.enum

enum class EditTextCondition {
    IS_EMPTY,
    IS_BLANK_OR_EMPTY,
    NOT_VALID_EMAIL,
    NOT_LETTERS_ONLY,
    NOT_NUMBERS_ONLY,
    NOT_LETTERS_OR_NUMBERS_ONLY,
    CONTAINS_SPECIAL_CHARACTERS
}