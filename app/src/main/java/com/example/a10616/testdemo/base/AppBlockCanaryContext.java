package com.example.a10616.testdemo.base;

import android.content.Context;

import com.github.moduth.blockcanary.BlockCanaryContext;
import com.github.moduth.blockcanary.internal.BlockInfo;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by TY on 2018/9/7.
 */

public class AppBlockCanaryContext extends BlockCanaryContext {

    /**
     * 毫秒值,间隔多长时间可以认为是一个卡顿,默认是1000
     */
    public int provideBlockThreshold() {
        return 1500;
    }


}
