package com.example.a10616.testdemo.thread;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.a10616.testdemo.R;
import com.example.a10616.testdemo.custom_annotation.Description;

import java.util.concurrent.Executors;

public class MultiThreadActivity extends AppCompatActivity {
    private static final String TAG = MultiThreadActivity.class.getSimpleName();
    private Thread thread;
    private StopThread stopThread;
    private StopThread2 stopThread2;
    private StopThread3 stopThread3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_thread);
    }

    public void first(View view) {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();

        new Thread(new MyRunnable("A", c, a)).start();
        SystemClock.sleep(100);
        new Thread(new MyRunnable("B", a, b)).start();
        SystemClock.sleep(100);
        new Thread(new MyRunnable("C", b, c)).start();
    }

    public void second(View view) {
        Log.e(TAG, Thread.currentThread().getName() + "主线程运行开始!");

        Thread1 mTh1 = new Thread1("A");
        Thread1 mTh2 = new Thread1("B");
        mTh1.start();
        mTh2.start();
        Log.e(TAG, Thread.currentThread().getName() + "主线程运行结束!");

    }


    public void three(View view) {
        Log.e(TAG, Thread.currentThread().getName() + "主线程运行开始!");
        Thread1 mTh1 = new Thread1("A");
        Thread1 mTh2 = new Thread1("B");
        mTh1.start();
        mTh2.start();

        try {
            mTh1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            mTh2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e(TAG, Thread.currentThread().getName() + "主线程运行结束!");
    }

    public void yield(View view) {
        new YieldThread("A").start();
        new YieldThread("B").start();
    }

    public void interrupt1(View view) {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run: 开始");
               /* try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.e(TAG, "run: 补货异常" );
                }*/
               // SystemClock.sleep 休眠的过程中，即使调用interrupt方法打断，也不会被打断
                SystemClock.sleep(4000);
                Log.e(TAG, "run: 结束");
            }
        });
        thread.start();
    }

    public void interrupt2(View view) {
        thread.interrupt();

    }
    public void interrupt3(View view) {
//        stopThread = new StopThread();
//        stopThread.start();

       /* stopThread2 = new StopThread2();
        stopThread2.start();
*/
        stopThread3 = new StopThread3();
        stopThread3.start();

    }

    public void interrupt4(View view) {
//        stopThread.interrupt();
//        stopThread2.interrupt();
        stopThread3.interrupt();
    }

    public void interrupt5(View view) {
//        stopThread.isStop = true;
//        stopThread2.isStop = true;
        stopThread3.isStop = true;

    }


    private class StopThread extends Thread {
        public volatile boolean isStop = false;

        @Override
        public void run() {
            super.run();
            Log.e(TAG, "run: 开始");
            // 在while里捕获是可以的，捕获到异常同时需要将标志位设置为true。
            while (!isStop) {
                Log.e(TAG, "run:  is running");
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.e(TAG, "run: 补货异常");
                    isStop = true;
                }
            }
            Log.e(TAG, "run: run 结束 ");
        }
    }

    private class StopThread2 extends Thread {
        public volatile boolean isStop = false;

        @Override
        public void run() {
            super.run();
            Log.e(TAG, "run: 开始");
            // 对整个循环进行捕获也是可以的，如果又中断操作，那么整个循环推出，同时被最外层捕获。
            try {
                while (!isStop) {
                    Log.e(TAG, "run:  is running");
                    sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.e(TAG, "run: 补货异常");
            }
            Log.e(TAG, "run: run 结束 ");

        }
    }

    private class StopThread3 extends Thread {
        public volatile boolean isStop = false;

        @Override
        public void run() {
            super.run();
            while (!isStop) {
                Log.e(TAG, "run:  is running");
            }
            Log.e(TAG, "run: run 结束 ");

        }
    }

    private class YieldThread extends Thread {
        public YieldThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            super.run();
            for (int i = 0; i < 5; i++) {
                Log.e(TAG, "run: " + getName() + "..." + i);
                if (i == 2) {
                    yield();
                }
            }
        }
    }

    private class MyRunnable implements Runnable {
        private String name;
        private Object prev;
        private Object self;

        public MyRunnable(String name, Object prev, Object self) {
            this.name = name;
            this.prev = prev;
            this.self = self;
        }

        @Override
        public void run() {
            int count = 10;
            while (count > 0) {
                synchronized (prev) {
                    synchronized (self) {
                        Log.e(TAG, name);
                        count--;
                        self.notify();
                    }
                    try {
                        prev.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }
                }
            }

        }
    }


    class Thread1 extends Thread {
        private String name;

        public Thread1(String name) {
            super(name);
            this.name = name;
        }

        public void run() {
            System.out.println();
            Log.e(TAG, Thread.currentThread().getName() + " 线程运行开始!");
            try {
                sleep((int) Math.random() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.e(TAG, Thread.currentThread().getName() + " 线程运行结束!");
        }
    }
}
