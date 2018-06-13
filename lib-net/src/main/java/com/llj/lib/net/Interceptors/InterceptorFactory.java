package com.llj.lib.net.Interceptors;

import android.support.annotation.RequiresPermission;

import com.llj.lib.utils.ANetWorkUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/12
 */
public class InterceptorFactory {

    public static Interceptor AGENT_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request newRequest = chain.request().newBuilder()
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
                    .build();
            return chain.proceed(newRequest);
        }
    };

    public static Interceptor REQUEST_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
            cacheBuilder.maxAge(10, TimeUnit.SECONDS);
            CacheControl cacheControl = cacheBuilder.build();
            Request request = chain.request();
            request = request.newBuilder()
                    .cacheControl(cacheBuilder.build())
                    .build();

            return chain.proceed(request);
        }
    };
    public static Interceptor HTTP_LOGGING_INTERCEPTOR = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    public static Interceptor RESPONSE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @RequiresPermission(ACCESS_NETWORK_STATE)
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            if (ANetWorkUtils.isNetworkConnected()) {
                int maxAge = 60; // read from cache
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };
}
