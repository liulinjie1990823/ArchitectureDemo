package com.llj.architecturedemo;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;

import com.llj.architecturedemo.api.TestApiService;
import com.llj.architecturedemo.db.AppDb;
import com.llj.architecturedemo.db.dao.MobileDao;
import com.llj.lib.net.Interceptors.InterceptorFactory;
import com.llj.lib.net.OkHttpClientManager;
import com.llj.lib.net.RetrofitUtils;

import java.io.File;
import java.security.SecureRandom;

import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/6
 */
@Module
class AppModule {

    @Singleton
    @Provides
    Retrofit provideRetrofit(Application context) {
        Retrofit.Builder builder = RetrofitUtils.createRxJava2Retrofit(HttpUrl.BASE_URL);

        //缓存文件夹
        File cacheFile = new File(context.getExternalCacheDir(), "http");
        //缓存大小为10M
        int cacheSize = 10 * 1024 * 1024;
        //创建缓存对象
        final Cache cache = new Cache(cacheFile, cacheSize);

        OkHttpClient.Builder okHttpClientBuilder = OkHttpClientManager.okHttpClientBuilder();
        okHttpClientBuilder.addInterceptor(InterceptorFactory.AGENT_INTERCEPTOR);
        okHttpClientBuilder.addInterceptor(InterceptorFactory.REQUEST_CACHE_CONTROL_INTERCEPTOR);
        okHttpClientBuilder.addInterceptor(InterceptorFactory.HTTP_LOGGING_INTERCEPTOR);
        okHttpClientBuilder.addNetworkInterceptor(InterceptorFactory.RESPONSE_CACHE_CONTROL_INTERCEPTOR);

        okHttpClientBuilder.cache(cache);

        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            }}, new SecureRandom());

            okHttpClientBuilder.sslSocketFactory(sslContext.getSocketFactory());
            okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        builder.client(okHttpClientBuilder.build());

        return builder.build();
    }

    @Singleton
    @Provides
    TestApiService provideGithubService(@NonNull Retrofit retrofit) {
        return retrofit.create(TestApiService.class);
    }

    @Singleton
    @Provides
    AppDb provideAppDb(Application app) {
        return Room.databaseBuilder(app, AppDb.class, "app.db").build();
    }

    @Singleton
    @Provides
    MobileDao provideMobileDao(AppDb appDb) {
        return appDb.mobileDao();
    }
}
