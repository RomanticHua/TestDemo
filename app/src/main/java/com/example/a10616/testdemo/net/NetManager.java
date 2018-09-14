package com.example.a10616.testdemo.net;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.AsyncSubject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by TY on 2018/9/11.
 */

public class NetManager {

    public static void getInstance() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .get()
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/") // baseUrl 应该以"/" 结尾,否则会提示异常
                .addConverterFactory(GsonConverterFactory.create()) // 实体bean由什么转化
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 线程调度
                .client(okHttpClient) // 不设置,默认也是okHttpClient
                .build();
        ApiService apiService = retrofit.create(ApiService.class); // 动态代理创建一个接口的实例.

        apiService.getTime("123", "jin")
                .subscribe(new Consumer<BaseBean>() {
                    @Override
                    public void accept(BaseBean baseBean) throws Exception {

                    }
                });


        retrofit2.Call<BaseBean> numCall = apiService.getNum("123", "jin");

        numCall.enqueue(new retrofit2.Callback<BaseBean>() {
            @Override
            public void onResponse(retrofit2.Call<BaseBean> call, retrofit2.Response<BaseBean> response) {

            }

            @Override
            public void onFailure(retrofit2.Call<BaseBean> call, Throwable t) {

            }
        });


        try {
            retrofit2.Response<BaseBean> response = apiService.getNum("123", "jin").execute();
            BaseBean body = response.body();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
