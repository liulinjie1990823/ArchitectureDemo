package com.llj.lib.net;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.llj.lib.net.utils.OkHttpClientUtils;

import okhttp3.Interceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/7
 */
public abstract class BaseRetrofitManager<T> {

    private Retrofit mRetrofit;

    protected T mService;


    public BaseRetrofitManager() {
    }

    public BaseRetrofitManager(@NonNull Context context) {
        init(context);
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public Retrofit createRetrofit(@NonNull Context context, @NonNull String baseUrl) {
        return createRetrofit(context, baseUrl, new Interceptor[]{});
    }

    public Retrofit createRetrofit(Context context, @NonNull String baseUrl, @Nullable Interceptor... interceptors) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl);
        builder.addConverterFactory(GsonConverterFactory.create()); //解析方法
        builder.client(OkHttpClientUtils.okHttpClientBuilder(interceptors).build());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        return builder.build();
    }


    protected abstract void init(@NonNull Context context);


    protected void initRetrofit(Context context, String baseUrl) {
        initRetrofit(context, baseUrl, new Interceptor[]{});
    }

    protected void initRetrofit(Context context, String baseUrl, @Nullable Interceptor... interceptors) {
        if (mRetrofit == null) {
            //锁定代码块
            synchronized (BaseRetrofitManager.class) {
                if (mRetrofit == null) {
                    mRetrofit = createRetrofit(context, baseUrl, interceptors);
                }
            }
        }
    }

}
