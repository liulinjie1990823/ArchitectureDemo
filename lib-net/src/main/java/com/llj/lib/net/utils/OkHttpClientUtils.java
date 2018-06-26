package com.llj.lib.net.utils;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.llj.lib.utils.helper.Utils;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/7
 */
public class OkHttpClientUtils {

    public static final int OK_CONNECT_TIMEOUT = 15_000;
    public static final int OK_WRITE_TIMEOUT   = 15_000;
    public static final int OK_READ_TIMEOUT    = 15_000;

    public static OkHttpClient.Builder okHttpClientBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder
                .cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(Utils.getApp())))
                .connectTimeout(OK_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(OK_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(OK_READ_TIMEOUT, TimeUnit.MILLISECONDS);
        return builder;
    }

    public static OkHttpClient.Builder okHttpClientBuilder(Interceptor... interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (interceptors != null && interceptors.length != 0) {
            for (Interceptor interceptor : interceptors) {
                if (interceptor == null) {
                    continue;
                }
                builder.addInterceptor(interceptor);
            }
        }
        builder
                .cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(Utils.getApp())))
                .connectTimeout(OK_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(OK_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(OK_READ_TIMEOUT, TimeUnit.MILLISECONDS);
        return builder;
    }
}
