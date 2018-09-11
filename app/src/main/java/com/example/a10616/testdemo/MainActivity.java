package com.example.a10616.testdemo;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.a10616.testdemo.bluetooth.BluetoothActivity;
import com.example.a10616.testdemo.usb.CommunicatePCActivity;
import com.example.a10616.testdemo.usb.PrinterCmdUtils;
import com.example.a10616.testdemo.video.FullScreenVideoPlayerActivity;
import com.example.a10616.testdemo.video.VideoPlayerActivity;
import com.example.a10616.testdemo.zxing.activity.ScanCodeActivity;
import com.example.ty.jitlibrary.JinUtil;


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

    public void fullScreenPlay(View view) {
        FullScreenVideoPlayerActivity.startActivity(this);
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

        JinUtil.helloWorld();
    }

    public void scan(View view) {
        ScanCodeActivity.startActivityForResult(this, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 0 && data != null) {
            String scanResult = data.getStringExtra(ScanCodeActivity.QR_CODE_RESULT);
            Log.e(TAG, "onActivityResult: " + scanResult);
        }
    }
}
