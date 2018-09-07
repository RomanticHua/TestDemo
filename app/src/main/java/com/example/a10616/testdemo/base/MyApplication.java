package com.example.a10616.testdemo.base;

import android.app.Application;

import com.github.moduth.blockcanary.BlockCanary;

/**
 * Created by TY on 2018/9/7.
 */

public class MyApplication extends Application {
    public static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }
}
