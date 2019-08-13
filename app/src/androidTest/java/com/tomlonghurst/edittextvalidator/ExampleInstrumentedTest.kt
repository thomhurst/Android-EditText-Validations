package com.tomlonghurst.edittextvalidator

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import android.widget.EditText
import com.tomlonghurst.edittextvalidator.enum.EditTextCondition
import com.tomlonghurst.edittextvalidator.extensions.*
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun editText_OneValidationFail() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val editText = EditText(appContext)

        editText
            .failWithMessageIf("Text is empty") { it.toString().isEmpty() }

        Assert.assertFalse(editText.validationPassed())
    }

    @Test
    fun editText_OneValidationWithMessageFailLambda() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val editText = EditText(appContext)

        editText
            .failWithMessageIf("Text is empty") { it.toString().isEmpty() }

        editText.validate(
            onValidationPassed = {
                throw Exception("Should Fail")
            },
            onValidationFailed = {
                Assert.assertEquals(1, it.size)
            })
    }

    @Test
    fun editText_OneValidationWithoutMessageFailLambda() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val editText = EditText(appContext)

        editText
            .failIf { it.isEmpty() }

        editText.validate(
            onValidationPassed = {
                throw Exception("Should Fail")
            },
            onValidationFailed = {
                Assert.assertEquals(0, it.size)
            })
    }

    @Test
    fun editText_MultipleValidationWithMessageFailLambda() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val editText = EditText(appContext)

        editText
            .failWithMessageIf("Text is empty") { it.toString().isEmpty() }
            .failWithMessageIf("Text is empty1") { it.toString().isEmpty() }
            .failWithMessageIf("Text is empty2") { it.toString().isEmpty() }
            .failWithMessageIf("Text is empty3") { it.toString().isEmpty() }

        editText.validate(
            onValidationPassed = {
                throw Exception("Should Fail")
            },
            onValidationFailed = {
                Assert.assertEquals(4, it.size)
            })
    }

    @Test
    fun editText_MultipleValidationWithAndWithoutMessageFailLambda() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val editText = EditText(appContext)

        editText
            .failWithMessageIf("Text is empty") { it.toString().isEmpty() }
            .failIf { it.isEmpty() }
            .failWithMessageIf("Text is empty2") { it.toString().isEmpty() }

        editText.validate(
            onValidationPassed = {
                throw Exception("Should Fail")
            },
            onValidationFailed = {
                Assert.assertEquals(2, it.size)
                Assert.assertEquals(it.first(), "Text is empty")
                Assert.assertEquals(it[1], "Text is empty2")
            })
    }

    @Test
    fun editText_MultipleConditions() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val editText = EditText(appContext)

        editText
            .failWithMessageIf("Text is empty") { it.toString().isEmpty() }
            .failIf { it.toString() == "Hello, World" }

        editText.validate(
            onValidationPassed = {
                throw Exception("Should Fail")
            },
            onValidationFailed = {
                Assert.assertEquals(1, it.size)
                Assert.assertEquals(it.first(), "Text is empty")
            })
    }

    @Test
    fun editText_ValidateAndShowError() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val editText = EditText(appContext)

        editText
            .failWithMessageIf("Error Message") { it.toString() == "ERROR" }

        editText.setText("ERROR")

        editText.validateAndShowError(
            onValidationPassed = {
                throw Exception("Should Fail")
            },
            onValidationFailed = {
                Assert.assertEquals(1, it.size)
                Assert.assertEquals(it.first(), "Error Message")
                Assert.assertEquals("Error Message", editText.error)
            })
    }

    @Test
    fun editText_ValidateAndShowError_Negative() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val editText = EditText(appContext)

        editText
            .failWithMessageIf("Error Message") { it.toString() == "ERROR" }

        editText.setText("eRROR")

        editText.validateAndShowError(
            onValidationPassed = {

            },
            onValidationFailed = {
                throw Exception("Pass")
            })
    }

    @Test
    fun editText_ShowErrorRealtime_One() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val editText = EditText(appContext)

        editText
            .failWithMessageRealTimeIf("Error Message") { it.toString() == "ERROR" }

        editText.setText("ERROR")

        Assert.assertEquals("Error Message", editText.error)
    }

    @Test
    fun editText_ShowErrorRealtime_Two() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val editText = EditText(appContext)

        editText
            .failWithMessageRealTimeIf("Error Message") { it.toString() == "ERROR" }
            .failWithMessageRealTimeIf("Error Message2") { it.toString() == "ERROR2" }

        editText.setText("ERROR")
        Assert.assertEquals("Error Message", editText.error)

        editText.setText("ERROR2")
        Assert.assertEquals("Error Message2", editText.error)
    }

    @Test
    fun editText_ShowErrorRealtime_Three() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val editText = EditText(appContext)

        editText
            .failWithMessageRealTimeIf("Error Message") { it.toString() == "ERROR" }
            .failWithMessageRealTimeIf("Error Message2") { it.toString() == "ERROR2" }
            .failWithMessageRealTimeIf("Error Message3") { it.toString() == "ERROR3" }

        editText.setText("ERROR")
        Assert.assertEquals("Error Message", editText.error)

        editText.setText("ERROR2")
        Assert.assertEquals("Error Message2", editText.error)

        editText.setText("ERROR3")
        Assert.assertEquals("Error Message3", editText.error)
    }

    @Test
    fun editText_ShowErrorRealtime_DismissError() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val editText = EditText(appContext)

        editText
            .failWithMessageRealTimeIf("Error Message") { it.toString() == "ERROR" }
            .failWithMessageRealTimeIf("Error Message2") { it.toString() == "ERROR2" }
            .failWithMessageRealTimeIf("Error Message3") { it.toString() == "ERROR3" }

        editText.setText("ERROR")
        Assert.assertEquals("Error Message", editText.error)

        editText.setText("ERROR2")
        Assert.assertEquals("Error Message2", editText.error)

        editText.setText("ERROR3")
        Assert.assertEquals("Error Message3", editText.error)

        editText.setText("Good")
        Assert.assertNull(editText.error)
    }

    @Test
    fun editText_RemoveValidators() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val editText = EditText(appContext)

        editText
            .failWithMessageRealTimeIf("Error Message", condition = { it.toString() == "ERROR" })
            .failWithMessageRealTimeIf("Error Message2") { it.toString() == "ERROR2" }
            .failWithMessageRealTimeIf("Error Message3") { it.toString() == "ERROR3" }

        editText.setText("ERROR")
        Assert.assertEquals("Error Message", editText.error)

        editText.setText("ERROR2")
        Assert.assertEquals("Error Message2", editText.error)

        editText.setText("ERROR3")
        Assert.assertEquals("Error Message3", editText.error)

        editText.removeAllValidators()

        editText.setText("ERROR")
        Assert.assertNull(editText.error)
    }

    @Test
    fun email_Test_Failures() {
        val emails = arrayListOf("tom@tom", "eaiotei", "", "a@a@a", "a.a.a", "@.com", "a@.com", "@a.com")
        for(email in emails) {
            val appContext = InstrumentationRegistry.getTargetContext()
            val editText = EditText(appContext).apply {
                setText(email)
            }

            editText
                .failWithMessageIf("Not Valid Email", EditTextCondition.NOT_VALID_EMAIL)

            editText.validate(
                onValidationPassed = {
                    throw Exception("Should Fail: $email")
                },
                onValidationFailed = {
                    Assert.assertEquals(1, it.size)
                    Assert.assertEquals(it.first(), "Not Valid Email")
                })
        }
    }

    @Test
    fun email_Test_Passes() {
        val emails = arrayListOf("hidarima@grangmi.ga", "itjij@v58tk1r6kp2ft01.cf", "Husbad1993@teleworm.us", "oreppuzuss-1458@yopmail.com", "sanasrowa123y@zn4chyguz9rz2gvjcq.tk", "56_4dim6@fakemailgenerator.net", "sanasrowa123y@lawrence1121.multiple.domain.club", "test@a.b.c.d.e.com")
        for(email in emails) {
            val appContext = InstrumentationRegistry.getTargetContext()
            val editText = EditText(appContext).apply {
                setText(email)
            }

            editText
                .failWithMessageIf("Not Valid Email", EditTextCondition.NOT_VALID_EMAIL)

            editText.validate(
                onValidationPassed = {

                },
                onValidationFailed = {
                    throw Exception("Should Pass: $email")
                })
        }
    }

    @Test
    fun letters_Only_Fail() {
        val texts = arrayListOf("123", "abc123", "a!", "Hello-World", "Hello_World","Hi?")
        for(text in texts) {
            val appContext = InstrumentationRegistry.getTargetContext()
            val editText = EditText(appContext).apply {
                setText(text)
            }

            editText
                .failWithMessageIf("Letters Only", EditTextCondition.NOT_LETTERS_ONLY)

            editText.validate(
                onValidationPassed = {
                    throw Exception("Should Fail: $text")
                },
                onValidationFailed = {
                    Assert.assertEquals(1, it.size)
                    Assert.assertEquals(it.first(), "Letters Only")
                })
        }
    }

    @Test
    fun letters_Only_Pass() {
        val texts = arrayListOf("h", "hello", "HELLO", "Hello")
        for(text in texts) {
            val appContext = InstrumentationRegistry.getTargetContext()
            val editText = EditText(appContext).apply {
                setText(text)
            }

            editText
                .failWithMessageIf("Letters Only", EditTextCondition.NOT_LETTERS_ONLY)

            editText.validate(
                onValidationPassed = {

                },
                onValidationFailed = {
                    throw Exception("Should Pass: $text")
                })
        }
    }

    @Test
    fun numbers_Only_Fail() {
        val texts = arrayListOf("abc", "abc123", "1!", "1-2", "1_2","1?")
        for(text in texts) {
            val appContext = InstrumentationRegistry.getTargetContext()
            val editText = EditText(appContext).apply {
                setText(text)
            }

            editText
                .failWithMessageIf("Numbers Only", EditTextCondition.NOT_NUMBERS_ONLY)

            editText.validate(
                onValidationPassed = {
                    throw Exception("Should Fail: $text")
                },
                onValidationFailed = {
                    Assert.assertEquals(1, it.size)
                    Assert.assertEquals(it.first(), "Numbers Only")
                })
        }
    }

    @Test
    fun numbers_Only_Pass() {
        val texts = arrayListOf("1", "123", "12345", "12345678910")
        for(text in texts) {
            val appContext = InstrumentationRegistry.getTargetContext()
            val editText = EditText(appContext).apply {
                setText(text)
            }

            editText
                .failWithMessageIf("Numbers Only", EditTextCondition.NOT_NUMBERS_ONLY)

            editText.validate(
                onValidationPassed = {

                },
                onValidationFailed = {
                    throw Exception("Should Pass: $text")
                })
        }
    }

    @Test
    fun empty_Fail() {
        val text = ""
        val appContext = InstrumentationRegistry.getTargetContext()
        val editText = EditText(appContext).apply {
            setText(text)
        }

        editText
            .failWithMessageIf("Must not be empty", EditTextCondition.IS_EMPTY)

        editText.validate(
            onValidationPassed = {
                throw Exception("Should Fail: $text")
            },
            onValidationFailed = {
                Assert.assertEquals(1, it.size)
                Assert.assertEquals(it.first(), "Must not be empty")
            })
    }

    @Test
    fun empty_Pass() {
        val text = " "
        val appContext = InstrumentationRegistry.getTargetContext()
        val editText = EditText(appContext).apply {
            setText(text)
        }

        editText
            .failWithMessageIf("Must not be empty", EditTextCondition.IS_EMPTY)

        editText.validate(
            onValidationPassed = {

            },
            onValidationFailed = {
                throw Exception("Should Pass: $text")
            })
    }

    @Test
    fun blank_Fail() {
        val text = " "
        val appContext = InstrumentationRegistry.getTargetContext()
        val editText = EditText(appContext).apply {
            setText(text)
        }

        editText
            .failWithMessageIf(errorMessage = "Must not be blank", condition = EditTextCondition.IS_BLANK_OR_EMPTY)

        editText.validate(
            onValidationPassed = {
                throw Exception("Should Fail: $text")
            },
            onValidationFailed = {
                Assert.assertEquals(1, it.size)
                Assert.assertEquals(it.first(), "Must not be blank")
            })
    }

    @Test
    fun blank_Pass() {
        val text = " ."
        val appContext = InstrumentationRegistry.getTargetContext()
        val editText = EditText(appContext).apply {
            setText(text)
        }

        editText
            .failWithMessageIf("Must not be blank", EditTextCondition.IS_BLANK_OR_EMPTY)

        editText.validate(
            onValidationPassed = {

            },
            onValidationFailed = {
                throw Exception("Should Pass: $text")
            })
    }

    @Test
    fun collectionTest1_3Failures() {
        val text = ""
        val appContext = InstrumentationRegistry.getTargetContext()


        val editText1 = EditText(appContext).apply {
            setText(text)
            failWithMessageIf("Must not be blank", EditTextCondition.IS_BLANK_OR_EMPTY)
        }

        val editText2 = EditText(appContext).apply {
            setText(text)
            failWithMessageIf("Must not be blank", EditTextCondition.IS_BLANK_OR_EMPTY)
        }

        val editText3 = EditText(appContext).apply {
            setText(text)
            failWithMessageIf("Must not be blank", EditTextCondition.IS_BLANK_OR_EMPTY)
        }

        EditTextValidation.validate(editText1, editText2, editText3,
            onValidationPassed = {
                throw Exception("Should Fail")
            },
            onValidationFailed = { failedEditTexts ->
                Assert.assertEquals(3, failedEditTexts.size)
            })
    }

    @Test
    fun collectionTest2_3Failures() {
        val text = ""
        val appContext = InstrumentationRegistry.getTargetContext()


        val editText1 = EditText(appContext).apply {
            setText(text)
            failWithMessageIf("Must not be blank", EditTextCondition.IS_BLANK_OR_EMPTY)
        }

        val editText2 = EditText(appContext).apply {
            setText(text)
            failWithMessageIf("Must not be blank", EditTextCondition.IS_BLANK_OR_EMPTY)
        }

        val editText3 = EditText(appContext).apply {
            setText(text)
            failWithMessageIf("Must not be blank", EditTextCondition.IS_BLANK_OR_EMPTY)
        }

        setOf(editText1, editText2, editText3).validate(
            onValidationPassed = {
                throw Exception("Should Fail")
            },
            onValidationFailed = { failedEditTexts ->
                Assert.assertEquals(3, failedEditTexts.size)
            })
    }

    @Test
    fun collectionTest3_3Failures() {
        val text = ""
        val appContext = InstrumentationRegistry.getTargetContext()


        val editText1 = EditText(appContext).apply {
            setText(text)
            failWithMessageIf("Must not be blank", EditTextCondition.IS_BLANK_OR_EMPTY)
        }

        val editText2 = EditText(appContext).apply {
            setText(text)
            failWithMessageIf("Must not be blank", EditTextCondition.IS_BLANK_OR_EMPTY)
        }

        val editText3 = EditText(appContext).apply {
            setText(text)
            failWithMessageIf("Must not be blank", EditTextCondition.IS_BLANK_OR_EMPTY)
        }

        listOf(editText1, editText2, editText3).validate(
            onValidationPassed = {
                throw Exception("Should Fail")
            },
            onValidationFailed = { failedEditTexts ->
                Assert.assertEquals(3, failedEditTexts.size)
            })
    }

    @Test
    fun collectionTest1_2Failures() {
        val appContext = InstrumentationRegistry.getTargetContext()

        val editText1 = EditText(appContext).apply {
            setText("")
            failWithMessageIf("Must not be blank", EditTextCondition.IS_BLANK_OR_EMPTY)
        }

        val editText2 = EditText(appContext).apply {
            setText("")
            failWithMessageIf("Must not be blank", EditTextCondition.IS_BLANK_OR_EMPTY)
        }

        val editText3 = EditText(appContext).apply {
            setText(".")
            failWithMessageIf("Must not be blank", EditTextCondition.IS_BLANK_OR_EMPTY)
        }

        EditTextValidation.validate(editText1, editText2, editText3,
            onValidationPassed = {
                throw Exception("Should Fail")
            },
            onValidationFailed = { failedEditTexts ->
                Assert.assertEquals(2, failedEditTexts.size)
            })
    }

    @Test
    fun getErrorMessages() {
        val appContext = InstrumentationRegistry.getTargetContext()

        val editText1 = EditText(appContext).apply {
            setText("")
            failWithMessageIf("Must not be blank", EditTextCondition.IS_BLANK_OR_EMPTY)
            failWithMessageIf("Error Message") { it.toString() == "ERROR" }
        }

        editText1.failedValidationMessages.let {
            Assert.assertEquals(1, it.size)
            Assert.assertEquals("Must not be blank", it.first())
        }
    }



}
