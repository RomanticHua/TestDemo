package com.example.a10616.testdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.a10616.testdemo.usb.CommunicatePCActivity;
import com.example.a10616.testdemo.video.VideoPlayerActivity;

public class MainActivity extends AppCompatActivity {

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
}
