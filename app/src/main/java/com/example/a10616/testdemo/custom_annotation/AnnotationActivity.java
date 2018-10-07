package com.example.a10616.testdemo.custom_annotation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.a10616.testdemo.R;
import com.example.myannoatationlib.MyAnnotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AnnotationActivity extends AppCompatActivity {
    private static final String TAG = AnnotationActivity.class.getSimpleName();
    @MyAnnotation
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
    }

    public void get(View view) {
        try {
            Class<?> c = Class.forName("com.example.a10616.testdemo.custom_annotation.Student");
            // 获得类上面的注解
            if ( c.isAnnotationPresent(Description.class)) {
                Description annotation = c.getAnnotation(Description.class);
                Log.e(TAG, "get: " + annotation.desc() + "..." + annotation.age());
            }
            // 获得方法上面的注解
            for (Method method : c.getMethods()) {
                if (method.isAnnotationPresent(Description.class)) {
                    Description description = method.getAnnotation(Description.class);
                    Log.e(TAG, "get: " + description.desc() + "..." + description.age());
                }
            }

            // 获得成员变量上的注解
            for (Field field : c.getFields()) {
                if (field.isAnnotationPresent(Description.class)) {
                    Description annotation = field.getAnnotation(Description.class);
                    Log.e(TAG, "get: " + annotation.desc() + "..." + annotation.age());
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
