package com.llj.lib.net;

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
public abstract class BaseRetrofitManager {

    private Retrofit mRetrofit;

    public BaseRetrofitManager() {
        init();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    private Retrofit createRetrofit() {
        return createRetrofit(null, new Interceptor[]{});
    }

    private Retrofit createRetrofit(Interceptor... interceptors) {
        return createRetrofit(null, interceptors);
    }

    private Retrofit createRetrofit(String baseUrl) {
        return createRetrofit(baseUrl, new Interceptor[]{});
    }

    private Retrofit createRetrofit(String baseUrl, Interceptor... interceptors) {
        Retrofit.Builder builder = new Retrofit.Builder();
        if (baseUrl == null) {
            builder.baseUrl(BaseHttpUrl.BASE_URL);
        } else {
            builder.baseUrl(baseUrl);
        }
        builder.addConverterFactory(GsonConverterFactory.create()); //解析方法
        builder.client(OkHttpClientManager.okHttpClientBuilder(interceptors).build());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        return builder.build();
    }


    protected abstract void init();


    protected void initRetrofit(String baseUrl) {
        if (mRetrofit == null) {
            //锁定代码块
            synchronized (BaseRetrofitManager.class) {
                if (mRetrofit == null) {
                    mRetrofit = createRetrofit(baseUrl);
                }
            }
        }
    }

    protected void initRetrofit(String baseUrl, Interceptor... interceptors) {
        if (mRetrofit == null) {
            //锁定代码块
            synchronized (BaseRetrofitManager.class) {
                if (mRetrofit == null) {
                    mRetrofit = createRetrofit(baseUrl, interceptors);
                }
            }
        }
    }



}
