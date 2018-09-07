package com.example.a10616.testdemo;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.a10616.testdemo.bluetooth.BluetoothActivity;
import com.example.a10616.testdemo.usb.CommunicatePCActivity;
import com.example.a10616.testdemo.video.VideoPlayerActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void videoPlay(View view) {
        VideoPlayerActivity.startActivity(this);
    }

    public void usb_pc(View view) {
        CommunicatePCActivity.startActivity(this);
    }

    public void bluetooth(View view) {
        BluetoothActivity.startActivity(this);
    }

    public void threadPool(View view) {
        ThreadPoolActivity.startActivity(this);
    }

    public void block(View view) {
        Log.e(TAG, "block: start....");
        SystemClock.sleep(2000);
        Log.e(TAG, "block: end....");
    }
    public void threadBlock(View view) {
        Log.e(TAG, "threadBlock: start....");
        SystemClock.sleep(2000);
        Log.e(TAG, "threadBlock: end....");
    }
}
