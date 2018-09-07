package com.example.a10616.testdemo.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10616.testdemo.R;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 蓝牙分为传统蓝牙和低功耗蓝牙,这两者的api完全不一样,这个类说明的是传统蓝牙
 */

public class BluetoothActivity extends AppCompatActivity {

    private static final String TAG = BluetoothActivity.class.getSimpleName();
    private BluetoothAdapter bluetoothAdapter;

    /**
     * ssp 蓝牙串口服务 ,必须使用这个值
     */
    private final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothSocket bluetoothSocket;
    private ExecutorService singleThreadExecutor;
    private OutputStream outputStream;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, BluetoothActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);// 搜索到设备
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);//蓝牙状态发生改变
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);// 搜索开始
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);// 搜索结束
        registerReceiver(broadcastReceiver, filter);

        singleThreadExecutor = Executors.newSingleThreadExecutor();
    }

    private StringBuilder sbUse = new StringBuilder();
    private StringBuilder sbPair = new StringBuilder();
    private BluetoothDevice device = null;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.isEmpty(action)) {
                return;
            }
            switch (action) {
                case BluetoothDevice.ACTION_FOUND:
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if (!TextUtils.isEmpty(device.getName())) {
                        if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                            sbUse.append(device.getName() + "   " + device.getType()
                                    + "   " + device.getBluetoothClass().getMajorDeviceClass() + "  " + device.getBluetoothClass().getDeviceClass() + "  >>>");
                        } else {
                            sbPair.append(device.getName() + "   " + device.getType()
                                    + "   " + device.getBluetoothClass().getMajorDeviceClass() + "  " + device.getBluetoothClass().getDeviceClass() + "  >>>");
                        }
                    }
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                    Log.e(TAG, "onReceive:搜索开始 ");
                    sbUse.append("已配对设备");
                    sbPair.append("可用设备");
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    Log.e(TAG, "onReceive: 搜索结束");
                    Log.e(TAG, "onReceive: " + sbUse.toString());
                    Log.e(TAG, "onReceive: " + sbPair.toString());
                    sbUse.delete(0, sbUse.length());
                    sbPair.delete(0, sbPair.length());
                    break;

                case BluetoothAdapter.ACTION_STATE_CHANGED:

                    int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.STATE_OFF);
                    Log.e(TAG, "onReceive:   " + state);

                    break;
            }
        }
    };

    public void open(View v) {


        //得到蓝牙适配器
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

         /*打开蓝牙有三种情况,
         1）没有任何提示，直接打开了蓝牙。如Nexus 5 Android 4.4.4 手机。
        （2）会弹出提示框，提示安全警告 “ ***应用尝试开启蓝牙”，可以选择“拒绝”或“允许”。大多数手机都是这样的。
        （3）强制打开蓝牙失败，并且没有任何提示。*/
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }

        //开启被其它蓝牙设备发现的功能
//        if (bluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
//            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//            //设置为一直开启
//            // value是设置设备可见多少秒,默认是120秒,0是始终可见
////            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
//            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
//            startActivity(intent);
//        }

        // 开始搜索的时候需要访问位置权限,这是一个危险权限,需要动态地申请.
        // <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

        //成功打开蓝牙后开始搜索附近的蓝牙设备,大概搜索12秒左右
        bluetoothAdapter.startDiscovery();
        // 获得本机配对的设备
        // 这个配对设备并不表示两个安卓设备都配对了,每个手机都可以选择取消配对.
        // 这仅仅只能表示本机仍然是和那个设备配对的.但另一个设备是否仍然保持配对,在这里并不能确定.

        //如果蓝牙没有打开,那么得到的是一个空的set,
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();

        Log.e(TAG, "bondedDevices  isEmpty:   " + bondedDevices.isEmpty());


        for (BluetoothDevice d : bondedDevices) {
            Log.e(TAG, "名称: " + d.getName() + "   type: " + d.getType()
                    + "   " + d.getBluetoothClass().getMajorDeviceClass());

            // 不同设备类型该值不同，比如computer蓝牙为256、phone 蓝牙为512、打印机蓝牙为1536等等。
            int deviceType = device.getBluetoothClass().getMajorDeviceClass();
            if (deviceType == BluetoothClass.Device.Major.IMAGING) {
                Log.e(TAG, "open: 这是蓝牙打印机:  " + d.getName());
            }

            if (d.getName().equals("vivo X20A")) {
                device = d;
            }
        }

        if (device == null) {
            return;
        }
        /*//服务端代码
        try {
            BluetoothServerSocket bluetoothServerSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord("jin", uuid);
            bluetoothServerSocket.accept();
            bluetoothServerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        singleThreadExecutor.execute(new Runnable() {


            @Override
            public void run() {
                try {
                    byte[] bytes = new byte[]{31, 32, 22, 54, 11};
                    bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid);
                    bluetoothAdapter.cancelDiscovery();
                    //连接到一个远程设备,会阻塞线程,在连接之前最好取消搜索,即使没有搜索,这样也是为了确保没有搜索,
                    //因为搜索是一个重量级的任务,会让连接明显变慢
                    bluetoothSocket.connect();
                    outputStream = bluetoothSocket.getOutputStream();


                    outputStream.write(bytes);
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    FileUtil.close(outputStream, bluetoothSocket);
                }
            }
        });
*/

    }

    public void close() {
        FileUtil.close(outputStream, bluetoothSocket);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(broadcastReceiver);
    }
}
