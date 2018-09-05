package com.example.a10616.testdemo.usb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.a10616.testdemo.R;
import com.example.a10616.testdemo.video.VideoPlayerActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by TY on 2018/9/4.
 */

public class CommunicatePCActivity extends AppCompatActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CommunicatePCActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communicate_pc);
        serverThread = new ServerThread();
        serverThread.start();
    }

    private static final String TAG = "ServerThread";

    ServerThread serverThread;

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), msg.getData().getString("MSG", "Toast"), Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        serverThread.setIsLoop(false);
    }

    class ServerThread extends Thread {

        boolean isLoop = true;

        public void setIsLoop(boolean isLoop) {
            this.isLoop = isLoop;
        }

        @Override
        public void run() {
            Log.d(TAG, "running");

            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(9000);

                while (isLoop) {
                    Socket socket = serverSocket.accept();

                    Log.d(TAG, "accept");

                   /* BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String msg =bufferedReader.readLine();*/


                    byte[] bytes = new byte[socket.getInputStream().available()];
                    socket.getInputStream().read(bytes);
                    String msg = new String(bytes);

                    Log.e(TAG, "run: " + msg);

                    Message message = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putString("MSG", msg);
                    message.setData(bundle);
                    handler.sendMessage(message);

                    socket.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Log.d(TAG, "destory");

                if (serverSocket != null) {
                    try {
                        serverSocket.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
