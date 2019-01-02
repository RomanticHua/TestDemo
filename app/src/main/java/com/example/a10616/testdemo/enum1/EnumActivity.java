package com.example.a10616.testdemo.enum1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.a10616.testdemo.R;
import com.example.a10616.testdemo.genericity.Dog;

import java.util.Calendar;

public class EnumActivity extends AppCompatActivity {
    private static final String TAG = EnumActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enum);


    }

    // 枚举不能继承
    public enum EnumTest {
        // 枚举常量本质是当前枚举类的对象,会调用该枚举的构造方法.
        ONE, TOW, THREE, Four();

        EnumTest() {
        }

        public void getTime() {

        }
    }

    public interface ColorInterface {
        String getSex();
    }

    public enum Color implements ColorInterface {
        // 如果打算自定义自己的方法，那么必须在enum实例序列的最后添加一个分号。而且 Java 要求必须先定义 enum 实例。
        RED("123", 1), BLACK("black", 2);
        private String name;
        private int index;

        Color(String name, int index) {
            this.name = name;
            this.index = index;
        }

        @Override
        public String getSex() {
            return name;
        }
    }

    private enum Color1 {
        RED() {
            @Override
            public String getName() {
                return "red";
            }
        },
        BLACK() {
            @Override
            public String getName() {
                return "black";
            }
        };

        // 定义抽象方法
        public abstract String getName();
    }

}
