package com.tomlonghurst.edittextvalidator

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.widget.EditText
import com.tomlonghurst.edittextvalidator.extensions.failIf
import com.tomlonghurst.edittextvalidator.extensions.failWithMessageIf
import com.tomlonghurst.edittextvalidator.extensions.validate
import com.tomlonghurst.edittextvalidator.extensions.validationPassed
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
            .failWithMessageIf("Text is empty") { it.text.isEmpty() }

        Assert.assertFalse(editText.validationPassed())
    }

    @Test
    fun editText_OneValidationWithMessageFailLambda() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val editText = EditText(appContext)

        editText
            .failWithMessageIf("Text is empty") { it.text.isEmpty() }

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
            .failIf { it.text.isEmpty() }

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
            .failWithMessageIf("Text is empty") { it.text.isEmpty() }
            .failWithMessageIf("Text is empty1") { it.text.isEmpty() }
            .failWithMessageIf("Text is empty2") { it.text.isEmpty() }
            .failWithMessageIf("Text is empty3") { it.text.isEmpty() }

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
            .failWithMessageIf("Text is empty") { it.text.isEmpty() }
            .failIf { it.text.isEmpty() }
            .failWithMessageIf("Text is empty2") { it.text.isEmpty() }

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
            .failWithMessageIf("Text is empty") { it.text.isEmpty() }
            .failIf { it.text.toString() == "Hello, World" }

        editText.validate(
            onValidationPassed = {
                throw Exception("Should Fail")
            },
            onValidationFailed = {
                Assert.assertEquals(1, it.size)
                Assert.assertEquals(it.first(), "Text is empty")
            })
    }


}
