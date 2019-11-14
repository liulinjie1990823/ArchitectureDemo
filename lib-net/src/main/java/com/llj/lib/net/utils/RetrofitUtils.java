package com.llj.lib.net.utils;

import androidx.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/6
 */
public class RetrofitUtils {

    public static Retrofit.Builder createRetrofit(@NonNull String baseUrl) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl);
        return builder;
    }

    public static Retrofit.Builder createGsonConverterRetrofit(@NonNull String baseUrl) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl);
        builder.addConverterFactory(GsonConverterFactory.create()); //解析方法
        return builder;
    }


    public static Retrofit.Builder createRxJava2Retrofit(@NonNull String baseUrl) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl);
        builder.addConverterFactory(GsonConverterFactory.create()); //解析方法
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        return builder;
    }

}
