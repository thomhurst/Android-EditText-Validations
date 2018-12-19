package com.tomlonghurst.edittextvalidator;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;
import com.tomlonghurst.edittextvalidator.extensions.EditTextKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class JavaInstrumentedTest {

    @Test
    public void runFromJava() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        EditText editText = new EditText(appContext);

        EditTextKt.failIf(editText, new Function1<EditText, Boolean>() {
            @Override
            public Boolean invoke(EditText editText) {
                return editText.getText().toString().isEmpty();
            }
        });

        Assert.assertTrue(!EditTextKt.validationPassed(editText));
    }

    @Test
    public void runFromJavaLambda() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        EditText editText = new EditText(appContext);

        EditTextKt.failWithMessageIf(editText, "Error Message", new Function1<EditText, Boolean>() {
            @Override
            public Boolean invoke(EditText editText) {
                return editText.getText().toString().isEmpty();
            }
        });

        EditTextKt.validate(editText, new Function0<Unit>() {
                    @Override
                    public Unit invoke() {
                        throw new RuntimeException("Should not pass");
                    }
                },
                new Function1<List<String>, Unit>() {
                    @Override
                    public Unit invoke(List<String> strings) {
                        Assert.assertEquals(1, strings.size());
                        return Unit.INSTANCE;
                    }
                });
    }

}
