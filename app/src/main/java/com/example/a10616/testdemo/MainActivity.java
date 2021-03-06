package com.example.a10616.testdemo;

import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.a10616.testdemo.bluetooth.BluetoothActivity;
import com.example.a10616.testdemo.usb.CommunicatePCActivity;
import com.example.a10616.testdemo.video.FullScreenVideoPlayerActivity;
import com.example.a10616.testdemo.video.VideoPlayerActivity;
import com.example.a10616.testdemo.zxing.activity.ScanCodeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

//import com.example.ty.jitlibrary.JinUtil;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.btn_video)
    Button btnVideo;
    @BindView(R.id.btn_video_full_screen)
    Button btnVideoFullScreen;
    private Unbinder bind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
        btnVideo.setText("xxxxxxxx");
        btnVideoFullScreen.setText("aaaaaaaa");

    }

    public void notify(View view) {
//        NotificationsUtils.open(this);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        boolean isOpened = managerCompat.areNotificationsEnabled();
        if (!isOpened) {
            // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);

        }

    }
//    public void videoPlay(View view) {
//        VideoPlayerActivity.startActivity(this);
//    }

//    public void fullScreenPlay(View view) {
//        FullScreenVideoPlayerActivity.startActivity(this);
//    }

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

//        JinUtil.helloWorld();
    }

    public void util(View view) {
        UtilActivity.startActivity(this);
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

    public void testProxy() {
//        Proxy.newProxyInstance(TestApi.class.getClassLoader())
    }


    @OnClick({R.id.btn_video, R.id.btn_video_full_screen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_video:
                Toast.makeText(this, "video", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_video_full_screen:
                Toast.makeText(this, "btn_video_full_screen", Toast.LENGTH_SHORT).show();

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        Log.e(TAG, "onDestroy: 本地DEV分支修改");
        Log.e(TAG, "onDestroy: 主分支代码修改");
        Log.e(TAG, "onDestroy: 主分支又修改了代码");
        Log.e(TAG, "onDestroy: jwh分支修改...");

    }

    public void masterDestroy() {
        Log.e(TAG, "onDestroy: 主分支又修改了代码");

    }

    public void jWHMoify() {
        Log.e(TAG, "onDestroy: jwh分支修改...");
    }

}
