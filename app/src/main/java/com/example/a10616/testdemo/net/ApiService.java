package com.example.a10616.testdemo.net;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by TY on 2018/9/12.
 */

public interface ApiService {

    @POST("a/bcd")
    @FormUrlEncoded
    Observable<BaseBean> getTime(@Field("id") String id, @Field("name") String name);


    @POST("a/bcd")
    @FormUrlEncoded
    Call<BaseBean> getNum(@Field("id") String id, @Field("name") String name);

}
