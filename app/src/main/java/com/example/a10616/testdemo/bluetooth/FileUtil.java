package com.example.a10616.testdemo.bluetooth;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by TY on 2018/9/7.
 */

public class FileUtil {
    /**
     * 关流
     */
    public static void close(Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
