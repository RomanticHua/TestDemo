package com.example.a10616.testdemo;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AtomicTest {

    private UiDevice uiDevice;

    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        uiDevice = UiDevice.getInstance(instrumentation);
    }

    @Test
    public void test() throws Exception {
        uiDevice.findObject(By.text("增加")).click();
        Thread.sleep(3000);
    }
    @Test
    public void test2() throws Exception {
        uiDevice.findObject(By.text("增加2")).click();
        Thread.sleep(3000);
    }
}
