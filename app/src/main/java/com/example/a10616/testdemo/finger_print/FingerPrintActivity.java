package com.example.a10616.testdemo.finger_print;

import android.app.KeyguardManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.example.a10616.testdemo.R;



public class FingerPrintActivity extends AppCompatActivity {
    public static final String TAG = FingerPrintActivity.class.getSimpleName();
    private FingerprintManagerCompat fingerprintManager;
    private CancellationSignal cancellationSignal;
    private KeyguardManager keyguardManager;
    private FingerPrintDialog printDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_figner_print);
        keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

        fingerprintManager = FingerprintManagerCompat.from(this);
        cancellationSignal = new CancellationSignal();

    }

    public void fingerPrint(View view) {
        printDialog = new FingerPrintDialog();
        printDialog.setListener(new FingerPrintDialog.OnFingerPrintDialogListener() {
            @Override
            public void onDismiss() {
                // 取消之后就不能再次验证了.
//                if (cancellationSignal != null && !cancellationSignal.isCanceled()) {
//                    cancellationSignal.cancel();
//                    Log.d(TAG, "onDismiss: Cancel");
//                }
            }
        });
        printDialog.show(getSupportFragmentManager(), "FingerPrint");
        verifyFingerPrint();

    }

    public void verifyFingerPrint() {
        // android 6.0 以上,设置过锁屏(密码解锁等) , 可以使用指纹, 设置过指纹
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && keyguardManager.isKeyguardSecure()
                && fingerprintManager.isHardwareDetected()
                && fingerprintManager.hasEnrolledFingerprints()) {
            fingerprintManager.authenticate(new CryptoObjectHelper().buildCryptoObject(), 0, cancellationSignal, new FingerprintManagerCompat.AuthenticationCallback() {

                /**
                 * 当出现不可恢复的错误时调用,例如连续输错5次,达到输错上限
                 * @param errMsgId
                 * @param errString
                 */
                @Override
                public void onAuthenticationError(int errMsgId, CharSequence errString) {
                    super.onAuthenticationError(errMsgId, errString);
                    Log.d(TAG, "onAuthenticationError: " + errMsgId + "  " + errString);

                }

                /**
                 * 当出现可以恢复的错误时回调.例如传感器脏了
                 * @param helpMsgId
                 * @param helpString
                 */
                @Override
                public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                    super.onAuthenticationHelp(helpMsgId, helpString);
                    Log.d(TAG, "onAuthenticationHelp: " + helpMsgId + "  " + helpString);
                }

                /**
                 * 验证成功时回调
                 * @param result
                 */
                @Override
                public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
//                    try {
//                        FingerprintManagerCompat.CryptoObject cryptoObject = result.getCryptoObject();
//                        if (cryptoObject != null) {
//                            Cipher cipher = cryptoObject.getCipher();
//                            if (cipher != null) {
//                                byte[] bytes = cipher.doFinal();
//                                Log.d(TAG, "onAuthenticationSucceeded: " + new String(bytes));
//                            }
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

                    Log.d(TAG, "onAuthenticationSucceeded: ");
                    printDialog.dismissAllowingStateLoss();
                }

                /**
                 * 指纹输入错误时回调
                 */
                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    Log.d(TAG, "onAuthenticationFailed: ");
                }
            }, null);
        }

    }
}
