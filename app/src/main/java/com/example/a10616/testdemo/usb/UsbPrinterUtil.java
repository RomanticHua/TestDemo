package com.example.a10616.testdemo.usb;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.text.TextUtils;
import android.widget.Toast;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



/**
 *安卓设备和USB设备连接模式:
 USB Host : 安卓设备作为 USB 主机，所有活动均由安卓设备发起,由安卓设备供电.
 USB Accessory : 由连接的 USB 硬件做 USB 主机，由 USB硬件供电.
 https://blog.csdn.net/JAZZSOLDIER/article/details/73739974


 一个USBDevice 含有多个interface ,一个接口代表一个基本功能,一个interface含有多个 endpoint,
 利用endpoint来进行通信.

 USB 通信最基本的形式是通过USB 设备的 端点(endpoint)，endpoint是通信的发送或者接收点，
 发送数据，只要把数据发送到正确的端点那里就可以了。
 端点也是有方向的，从usb 主机到设备称为 out 端点，从设备到主机称为in 端点。
 USB 设备必须具有端点0，它可以作为in 端点，也可以作为out 端点.

 USB endpoint 有四种类型，也就分别对应了四种不同的数据传输方式。它们是控制传输（Control Transfers），
 中断传输（Interrupt Data Transfers），批量传输(Bulk DataTransfers)，等时传输(Isochronous Data Transfers)。

 打印机和扫描仪属于需要利用批量传输.

 */

public class UsbPrinterUtil {
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private Context activity;
    private UsbEndpoint outEndpoint;
    private UsbDeviceConnection connection;
    private UsbManager usbManager;
    private static UsbPrinterUtil mUsbPrinterUtils;
    private PendingIntent mPermissionIntent;


    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private UsbPrinterUtil(Context activity) {

        this.activity = activity;
        mPermissionIntent = PendingIntent.getBroadcast(activity, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_USB_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        activity.registerReceiver(mUsbReceiver, filter);
        usbPrint();
    }

    private BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            //call method to set up device communication
                            // 设备接口, 注意设备不同接口不同, 这里取第一个
                            UsbInterface usbInterface = device.getInterface(0);
                            // 分配端点, 注意设备不同端点不同, 这里取第一个
                            outEndpoint = usbInterface.getEndpoint(0);
                            // 打开设备建立连接
                            connection = usbManager.openDevice(device);
                            connection.claimInterface(usbInterface, true);
                        }
                    }
                }
            }
            //当打印机连接上时自动申请权限
            else if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                if (device != null) {
                    Toast.makeText(activity, "连接USB", Toast.LENGTH_LONG).show();
                    getInstance(activity);
                }
            } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                if (device != null) {
                    Toast.makeText(activity, "断开USB", Toast.LENGTH_LONG).show();
                    close();
                }
            }
        }
    };

    public static UsbPrinterUtil getInstance(Context context) {
        if (mUsbPrinterUtils == null) {
            mUsbPrinterUtils = new UsbPrinterUtil(context);
        }
        return mUsbPrinterUtils;
    }

    /**
     * XP-D610L USB打印机连接
     */
    private void usbPrint() {
        // 取连接到设备上的USB设备集合
        usbManager = (UsbManager) activity.getSystemService(Context.USB_SERVICE);
        //得到所有usb连接的设备
        HashMap<String, UsbDevice> map = usbManager.getDeviceList();

        // 没有连接设备
        if (map.isEmpty()) {
//            ToastUtil.showToast(activity, activity.getString(R.string.connect_usb));
            return;
        }

        // 遍历集合取指定的USB设备
        UsbDevice usbDevice = null;
        for (UsbDevice device : map.values()) {
            // 芯烨  XP-D610L 的 VendorID = 8137 , ProductID = 8214
            int VendorID = device.getVendorId();//生产商id
            int ProductID = device.getProductId();//产品id
            if (VendorID == 8137 && ProductID == 8214) {
                usbDevice = device;
                break;
            }
        }
        // 没有找到设备
        if (usbDevice == null) {
//            ToastUtil.showToast(activity, activity.getString(R.string.no_connect_usb));
            return;
        }
        // 程序是否有操作设备的权限
        if (!usbManager.hasPermission(usbDevice)) {
            usbManager.requestPermission(usbDevice, mPermissionIntent);
            return;
        }

        // 其他地方抄过来的方法,用来获取正确的interface和endpoint进行通信.
        UsbEndpoint mUsbEndpointOut;
        for (int interfaceIndex = 0; interfaceIndex < usbDevice.getInterfaceCount(); interfaceIndex++) {
            UsbInterface usbInterface = usbDevice.getInterface(interfaceIndex);
            //todo 这里应该是判断接口能够通信
            if ((UsbConstants.USB_CLASS_CDC_DATA == usbInterface.getInterfaceClass())
                    || (UsbConstants.USB_CLASS_COMM == usbInterface.getInterfaceClass())) {

                for (int i = 0; i < usbInterface.getEndpointCount(); i++) {
                    UsbEndpoint ep = usbInterface.getEndpoint(i);
                    // 打印采用bulk来批量通信,同时判断端口是输出端口
                    if (ep.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK && ep.getDirection() == UsbConstants.USB_DIR_OUT) {
                        mUsbEndpointOut = ep;
                        break;
                    }
                }
            }
        }

        // 设备接口, 注意设备不同接口不同, 这里取第一个
        UsbInterface usbInterface = usbDevice.getInterface(0);
        // 分配端点, 注意设备不同端点不同, 这里取第一个
        outEndpoint = usbInterface.getEndpoint(0);
        // 打开设备建立连接
        connection = usbManager.openDevice(usbDevice);
        // 申明占用这个接口
        connection.claimInterface(usbInterface, true);
    }


    public void close() {

        if (connection != null) {
            connection.close();
        }
        mUsbPrinterUtils = null;
    }

    /**
     * 关闭连接,释放资源
     */
    public void destroy() {
        activity.unregisterReceiver(mUsbReceiver);
        close();
    }

    /**
     * 打印字符串
     *
     * @param data
     * @return
     */
    public boolean printerString(String data) {
        if (!TextUtils.isEmpty(data)) {
            try {
                byte[] printData = data.getBytes("gbk");
                return printerByte(printData);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 打印字节数组
     *
     * @param printData
     * @return
     */
    public boolean printerByte(byte[] printData) {
        if (connection != null && outEndpoint != null) {
            // 通过给定的endpoint来进行大量的数据传输，传输的方向取决于该节点的方向，buffer是要发送或接收的字节数组，
            // length是该字节数组的长度。传输成功则返回所传输的字节数组的长度，失败则返回负数。
            int out = connection.bulkTransfer(outEndpoint, printData, printData.length, 5000);
            if (out != 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 初始化打印机
     *
     * @return
     */
    public boolean init_printer() {
        byte[] init_printer = PrinterCmdUtils.init_printer();
        return printerByte(init_printer);
    }

    /**
     * 切纸
     *
     * @return
     */
    public boolean feedPaperCutPartial() {
        byte[] feedPaperCutPartial = PrinterCmdUtils.feedPaperCutPartial();
        return printerByte(feedPaperCutPartial);
    }

    /**
     * 打开钱箱
     *
     * @return
     */
    public boolean openCashbox() {
        byte[] openCashbox = PrinterCmdUtils.openCashbox();
        return printerByte(openCashbox);
    }

    /**
     * 初始化打印机
     *
     * @return
     */
    public boolean fontSizeSetBig(int num) {
        byte[] fontSizeSetBig = PrinterCmdUtils.fontSizeSetBig(num);
        return printerByte(fontSizeSetBig);
    }

    /**
     * 换行
     *
     * @param lineNum 要换几行
     * @return
     */
    public boolean nextLine(int lineNum) {
        byte[] nextLine = PrinterCmdUtils.nextLine(lineNum);
        return printerByte(nextLine);
    }


    public void test() {
        // todo 发生错误时,是否会有异常.
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                openCashbox();
                //字体放大两倍打印标题
                fontSizeSetBig(2);
                printerString("    RTTMALL");
                nextLine(1);
                //取消字体放大 初始化打印机
                init_printer();
                printerString("收银：1001 牌号：0002");
                nextLine(1);
                printerString("单号：201712111171554555");
                nextLine(1);
                printerString("时间：2017-12-14 12:12:12");
                nextLine(1);
                printerString("----------------------------------------");
                nextLine(1);
                printerString("商品名称            单价      数量 小计");
                nextLine(1);
                printerString("商品名称            单价(TT$) 数量 小计(TT$)");
                nextLine(1);
                printerString("百事可乐330ml        3         1     3");
                nextLine(1);
                printerString("加多宝310ml          5        2       10");
                nextLine(1);
                printerString("----------------------------------------");
                nextLine(1);
                printerString("原价：13            总数：3");
                nextLine(1);
                printerString("现价：13            支付：现金：13");
                nextLine(1);
                printerString("实收：15            找零：2");
                nextLine(1);
                printerString("----------------------------------------");
                nextLine(2);
                printerString("福建省厦门市思明区观音山商务区*****");
                nextLine(1);
                printerString("        欢迎光临");
                nextLine(2);
                feedPaperCutPartial();
            }
        });

    }
}
