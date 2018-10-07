package com.example.a10616.testdemo.genericity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.a10616.testdemo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class GenericityActivity extends AppCompatActivity {
    private String TAG = GenericityActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genericity);
        View viewById = findViewById(R.id.full_id);

        Flowable<String> stringFlowable = Flowable.fromArray("1");

        Flowable<Integer> map = stringFlowable.map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                return 1;
            }
        });


        Cat<String> stringCat = new Cat<>("123");

        Dog dog = stringCat.getString();


        Log.e(TAG, "onCreate: " + dog.name);


        Object number = stringCat.getNumber();
        Log.e(TAG, "onCreate: " + number);
        String sex = Cat.getSex("1");

        ArrayList<String> strings = new ArrayList<>();

        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();


        View view = findView(R.id.full_id);
    }

    // 在修饰符和返回类型之间加一个泛型类型参数的声明，
    // 表明在这个方法作用域中谁才是泛型类型参数

    public static <T> void copy(List<? super T> dest, List<? extends T> src) {

    }

    // 声明泛型的下限.
    public <T extends View> T findView(@IdRes int id){
        return null;
    }

    public <T extends View> T findViewById(@IdRes int id) {
        return getDelegate().findViewById(id);
    }
}
