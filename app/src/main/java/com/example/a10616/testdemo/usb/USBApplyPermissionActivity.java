package com.example.a10616.testdemo.usb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.a10616.testdemo.R;

/**
 * Created by TY on 2018/9/5.
 */

public class USBApplyPermissionActivity extends AppCompatActivity {
    private static final String TAG = USBApplyPermissionActivity.class.getSimpleName();
    private UsbPrinterUtil usbPrinterUtil;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, USBApplyPermissionActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usb_permission);
    }

    public void getDeviceList(View view) {
        usbPrinterUtil = UsbPrinterUtil.getInstance(this);
        usbPrinterUtil.test();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        usbPrinterUtil.destroy();
    }
}
