package com.example.a10616.testdemo;

import android.util.Log;

public class TagUtil {

    public static final String TAG = TagUtil.class.getSimpleName();

    public void tag() {
        Log.i(TAG, "tag: 正式发版1.0.0");
    }

    public void normalDev() {
        Log.i(TAG, "normalDev: 正常开发111111...");
        Log.i(TAG, "normalDev: 正常开发22222222...");
        Log.i(TAG, "normalDev: 正常开发3333333333...");

    }
}
