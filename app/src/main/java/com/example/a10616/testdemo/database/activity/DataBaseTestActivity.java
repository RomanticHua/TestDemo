package com.example.a10616.testdemo.database.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.a10616.testdemo.R;
import com.example.a10616.testdemo.database.bean.Teacher;
import com.example.a10616.testdemo.database.bean.Type;
import com.example.a10616.testdemo.database.dao.DaoSession;
import com.example.a10616.testdemo.database.dao.TeacherDao;
import com.example.a10616.testdemo.database.manager.DatabaseManager;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class DataBaseTestActivity extends AppCompatActivity {

    private DaoSession session;
    private String TAG = DataBaseTestActivity.class.getSimpleName();
    private Runnable target = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        }
    };
    private Runnable command = new Runnable() {
        @Override
        public void run() {
            SystemClock.sleep(10);
            value++;
        }
    };
    private Runnable command1 = new Runnable() {
        @Override
        public void run() {
            SystemClock.sleep(10);
            atomicInteger.getAndIncrement();
        }
    };
    ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        session = DatabaseManager.getInstance().getSession();
    }

    private int i;

    public void insert(View view) {

        for (int i = 0; i < 100; i++) {
            new Thread(command1).start();
        }
        SystemClock.sleep(1000);
        System.out.println("AtomicInteger 最终结果是" + atomicInteger.get());
        atomicInteger.set(0);
    }

    public static int value = 0;

    ExecutorService executorService = Executors.newFixedThreadPool(100);

    public void insert2(View view) {
        for (int i = 0; i < 100; i++) {
            new Thread(command).start();
        }
        SystemClock.sleep(1000);
        System.out.println("最终结果是" + value);
        value = 0;


    }

    int count = 0;

    public void test() throws Exception {
        for (int i = 0; i < 1000; i++) {
            new Thread(target).start();
        }
        Thread.sleep(2000);
        System.out.println(count);
        count = 0;
    }

    AtomicInteger atomicInteger = new AtomicInteger(0);

    public void test2() throws Exception {
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    atomicInteger.getAndIncrement();
                }
            }).start();
        }
        Thread.sleep(2000);
        System.out.println(atomicInteger);
    }


    private class RunnableTest implements Runnable {

        private int tick1 = 0;

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                tick1++;
            }
        }
    }

    private class RunnableTest2 implements Runnable {

        private volatile AtomicInteger tick = new AtomicInteger(60);

        @Override
        public void run() {
            while (true) {
                    if (tick.get() == 0) {
                        break;
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "=========" + tick.getAndDecrement());
                }

        }
    }

    public void delete(View view) {
        RunnableTest runnableTest = new RunnableTest();
        new Thread(runnableTest).start();
        new Thread(runnableTest).start();

        SystemClock.sleep(1000);
        System.out.println(runnableTest.tick1);
    }

    public void update(View view) {
        RunnableTest2 runnableTest2 = new RunnableTest2();
        new Thread(runnableTest2).start();
        new Thread(runnableTest2).start();
        SystemClock.sleep(1000);
        System.out.println(runnableTest2.tick.get());
    }


    private class RunnableTest3 implements Runnable {

        private int tick = 60;

        @Override
        public void run() {
            while (true) {
                synchronized (RunnableTest3.class) {
                    if (tick == 0) {
                        break;
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "=========" + tick--);
                }
            }
        }
    }

    public void query(View view) {
        RunnableTest3 runnableTest3 = new RunnableTest3();
        new Thread(runnableTest3).start();
        new Thread(runnableTest3).start();
    }
}
