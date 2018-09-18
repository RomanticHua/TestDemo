package com.example.a10616.testdemo;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.util.Log;
import android.view.KeyEvent;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * 使用AndroidJUnit4来进行测试
 */
@RunWith(AndroidJUnit4.class)
public class CalculateTest {
    public static final String TAG = CalculateTest.class.getSimpleName();
    private UiDevice uiDevice;

    /**
     * 用例测试之前
     */
    @Before
    public void setUp() throws Exception {
        Log.e(TAG, "setUp: ");
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        uiDevice = UiDevice.getInstance(instrumentation);
        //按下物理按键-电源键
        uiDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        //滑动一段距离解锁
        uiDevice.swipe(159, 963, 583, 365, 10);
        Thread.sleep(500);
        //按一下home键,回到主界面
        uiDevice.pressHome();
        //点击计算器
        uiDevice.findObject(By.desc("计算器")).click();
        Thread.sleep(500);

    }

    @Test
    public void add() throws Exception {
        while (true) {
            //计算4+5=9
            uiDevice.findObject(By.res("com.sec.android.app.popupcalculator:id/bt_04")).click();
            uiDevice.findObject(By.res("com.sec.android.app.popupcalculator:id/bt_add")).click();
            uiDevice.findObject(By.res("com.sec.android.app.popupcalculator:id/bt_05")).click();
            uiDevice.findObject(By.res("com.sec.android.app.popupcalculator:id/bt_equal")).click();

            //得到结果,
            uiDevice.findObject(By.res("com.sec.android.app.popupcalculator:id/bt_backspace")).click();
            //断言
            UiObject2 etResult = uiDevice.findObject(By.res("com.sec.android.app.popupcalculator:id/txtCalc"));

            String result = etResult.getText().replace(". 正在编辑。", "");
            Log.e(TAG, "calculatorTest: " + result);
            Assert.assertEquals(result, "9");

            etResult = uiDevice.findObject(By.res("com.sec.android.app.popupcalculator:id/txtCalc"));
            etResult.setText("");
        }
    }

    @Test
    public void reduce() throws Exception {
        //计算5-4=1
        uiDevice.findObject(By.res("com.sec.android.app.popupcalculator:id/bt_05")).click();
        uiDevice.findObject(By.res("com.sec.android.app.popupcalculator:id/bt_sub")).click();
        uiDevice.findObject(By.res("com.sec.android.app.popupcalculator:id/bt_04")).click();
        uiDevice.findObject(By.res("com.sec.android.app.popupcalculator:id/bt_equal")).click();

        //得到结果,
        uiDevice.findObject(By.res("com.sec.android.app.popupcalculator:id/bt_backspace")).click();
        //断言
        UiObject2 etResult = uiDevice.findObject(By.res("com.sec.android.app.popupcalculator:id/txtCalc"));

        String result = etResult.getText().replace(". 正在编辑。", "");
        Log.e(TAG, "calculatorTest: " + result);
        Assert.assertEquals(result, "1");

    }

    @Test
    public void multiply() throws Exception {
        //计算2*5=10
        uiDevice.findObject(By.res("com.sec.android.app.popupcalculator:id/bt_02")).click();
        uiDevice.findObject(By.res("com.sec.android.app.popupcalculator:id/bt_mul")).click();
        uiDevice.findObject(By.res("com.sec.android.app.popupcalculator:id/bt_05")).click();
        uiDevice.findObject(By.res("com.sec.android.app.popupcalculator:id/bt_equal")).click();

        //得到结果,
        uiDevice.findObject(By.res("com.sec.android.app.popupcalculator:id/bt_backspace")).click();
        //断言
        UiObject2 etResult = uiDevice.findObject(By.res("com.sec.android.app.popupcalculator:id/txtCalc"));
        String result = etResult.getText().replace(". 正在编辑。", "");
        Log.e(TAG, "calculatorTest: " + result);
        Assert.assertEquals(result, "10");

    }

    @Test
    public void divide() throws Exception {
        //计算6/3=2
        uiDevice.findObject(By.res("com.sec.android.app.popupcalculator:id/bt_06")).click();
        uiDevice.findObject(By.res("com.sec.android.app.popupcalculator:id/bt_div")).click();
        uiDevice.findObject(By.res("com.sec.android.app.popupcalculator:id/bt_03")).click();
        uiDevice.findObject(By.res("com.sec.android.app.popupcalculator:id/bt_equal")).click();

        //得到结果,
        uiDevice.findObject(By.res("com.sec.android.app.popupcalculator:id/bt_backspace")).click();
        //断言
        UiObject2 etResult = uiDevice.findObject(By.res("com.sec.android.app.popupcalculator:id/txtCalc"));
        String result = etResult.getText().replace(". 正在编辑。", "");
        Log.e(TAG, "calculatorTest: " + result);
        Assert.assertEquals(result, "2");

    }

    @After
    public void finish() throws Exception {
        Log.e(TAG, "finish: ");

        UiObject2 etResult = uiDevice.findObject(By.res("com.sec.android.app.popupcalculator:id/txtCalc"));
        etResult.setText("");
        Thread.sleep(500);

    }


}
