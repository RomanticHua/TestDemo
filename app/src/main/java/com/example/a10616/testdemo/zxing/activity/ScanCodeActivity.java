package com.example.a10616.testdemo.zxing.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;




public class ScanCodeActivity extends CaptureActivity {
    public static final String QR_CODE_RESULT="qr_code_result";
    public static final String TAG = ScanCodeActivity.class.getName();

    public static void startActivityForResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, ScanCodeActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //todo 如果兼容6.0以上需要做权限处理

    }


    //处理返回结果
    @Override
    public void handleDecodeCallback(String result) {
        Intent intent = new Intent();
        intent.putExtra(QR_CODE_RESULT, result);
        setResult(0, intent);
        finish();
    }


}
