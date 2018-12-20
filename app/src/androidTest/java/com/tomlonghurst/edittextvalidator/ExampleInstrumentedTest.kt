package com.tomlonghurst.edittextvalidator

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.widget.EditText
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
            .failWithMessageRealTimeIf("Error Message") { it.toString() == "ERROR" }
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

}
