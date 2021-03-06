package com.example.a10616.testdemo.zxing.decoding;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;

import com.example.a10616.testdemo.zxing.view.ViewfinderView;
import com.google.zxing.Result;


/***
 *
 * @author Ryan.Tang & PengJian.Wu
 *
 */
public interface DecodeHandlerInterface {

    public static final int RESULT_STATE_OK = 0;

    public void drawViewfinder();

    public ViewfinderView getViewfinderView();

    public Handler getHandler();

    public void handleDecode(Result result, Bitmap barcode);

    public void returnScanResult(int resultCode, Intent data);

    public void launchProductQuary(String url);
}
