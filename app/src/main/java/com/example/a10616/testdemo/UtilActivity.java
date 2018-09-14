package com.example.a10616.testdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.a10616.testdemo.net.BaseBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by TY on 2018/9/12.
 */

public class UtilActivity extends AppCompatActivity {
    public static final String TAG = UtilActivity.class.getSimpleName();

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UtilActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_util);
    }

    public void unModifyList(View view) {
        List<BaseBean> list = new ArrayList<>();
        list.add(new BaseBean(0));
        list.add(new BaseBean(1));
        list.add(new BaseBean(2));

        List<BaseBean> strings = Collections.unmodifiableList(list);
        // 可以对集合中元素的属性进行改变,但是不能对集合新增或者删除.
        strings.get(0).state = 2222;
        Log.e(TAG, "unModifyList: " + strings.get(0).state);
        //不可以新增或者删除,集合元素固定是初始的几个,下面会提示 UnsupportedOperationException 错误.
//        strings.add(new BaseBean(4));
//        strings.remove(0);
        for (BaseBean bean : strings) {
            Log.e(TAG, "unModifyList: "+bean.state);
        }
    }

}
