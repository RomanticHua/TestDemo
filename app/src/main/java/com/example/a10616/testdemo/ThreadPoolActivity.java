package com.example.a10616.testdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by TY on 2018/9/7.
 */

public class ThreadPoolActivity extends AppCompatActivity {


    private static final String TAG = ThreadPoolActivity.class.getSimpleName();
    private ExecutorService executorService;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ThreadPoolActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);
        executorService = Executors.newSingleThreadExecutor();
    }

    private Runnable task1 = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                Log.e(TAG, "run:  task1");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private Runnable task333 = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(10000);
                Log.e(TAG, "run: task333");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private Runnable task2 = new Runnable() {
        @Override
        public void run() {
            try {
                Log.e(TAG, "run: task2 start...");
                Thread.sleep(2000);

                int i = 1 / 0;
                Log.e(TAG, "run: task2 end... ");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    /**
     * 单一线程池执行任务,任务会依次执行,等待第一个任务执行完毕,才会执行第二个任务.
     *
     * 如果某一个任务出现异常,这个异常没有被try catch,那么这个异常任务会终止,不会继续执行,但是后续的任务仍然会执行.
     */
    public void submit(View v) {

        executorService.submit(task1);
        executorService.submit(task2);
        executorService.submit(task333);
    }

    /**
     * 单一线程池执行任务,任务会依次执行,等待第一个任务执行完毕,才会执行第二个任务.
     *
     * execute 方法, 如果任务执行过程中出现异常,这个异常没有被捕获,那么程序会崩溃.
     */
    public void execute(View v) {

        //
        executorService.execute(task1);
        executorService.execute(task2);
        executorService.execute(task333);
    }
}
