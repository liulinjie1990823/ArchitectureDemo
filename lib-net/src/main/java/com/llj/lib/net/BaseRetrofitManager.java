package com.llj.lib.net;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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
        builder.client(OkHttpClientManager.getApiOkHttpClient(interceptors));
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


    protected <T> Observable wrapObservable(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())//指定io
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new ExceptionFunction<>());
//                .map((Function<? super T, ? extends R>) new ResultFunction<T>());

    }
}
