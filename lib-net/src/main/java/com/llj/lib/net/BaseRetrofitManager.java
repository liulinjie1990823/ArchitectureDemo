package com.llj.lib.net;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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

    private  Retrofit mRetrofit;

    public BaseRetrofitManager() {
       init();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }


    private Retrofit createRetrofit(String baseUrl) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl);
        builder.addConverterFactory(GsonConverterFactory.create()); //解析方法
        builder.client(OkHttpClientManager.getApiOkHttpClient());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        return builder.build();
    }

    private Retrofit createRetrofit() {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BaseHttpUrl.BASE_URL);
        builder.addConverterFactory(GsonConverterFactory.create()); //解析方法
        builder.client(OkHttpClientManager.getApiOkHttpClient());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        return builder.build();
    }

    protected abstract void init();


    protected void initRetrofit(String baseUrl) {
        if (mRetrofit == null) {
            //锁定代码块
            synchronized (BaseRetrofitManager.class) {
                if (mRetrofit == null) {
                    if (baseUrl == null) {
                        mRetrofit = createRetrofit();
                    } else {
                        mRetrofit = createRetrofit(baseUrl); //创建mRetrofit对象
                    }
                }
            }
        }
    }


    protected  <T> Observable wrapObservable(Observable<IResponse<T>> observable) {
        return observable
                .subscribeOn(Schedulers.io())//指定io
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new ExceptionFunction<>());
//                .map((Function<? super T, ? extends R>) new ResultFunction<T>());

    }
}
