package com.llj.architecturedemo;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;

import com.llj.architecturedemo.api.TestApiService;
import com.llj.architecturedemo.db.AppDb;
import com.llj.architecturedemo.db.dao.MobileDao;
import com.llj.lib.net.Interceptors.InterceptorFactory;
import com.llj.lib.net.ssl.SSLFactory;
import com.llj.lib.net.utils.OkHttpClientUtils;
import com.llj.lib.net.utils.RetrofitUtils;

import java.io.File;

import javax.inject.Singleton;

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

        OkHttpClient.Builder okHttpClientBuilder = OkHttpClientUtils.okHttpClientBuilder();

        //拦截器
        InterceptorFactory.UrlHandlerInterceptor urlHandlerInterceptor = new InterceptorFactory.UrlHandlerInterceptor();
        okHttpClientBuilder.addInterceptor(urlHandlerInterceptor);
        okHttpClientBuilder.addInterceptor(InterceptorFactory.AGENT_INTERCEPTOR);
//        okHttpClientBuilder.addInterceptor(InterceptorFactory.REQUEST_CACHE_CONTROL_INTERCEPTOR);
        okHttpClientBuilder.addInterceptor(InterceptorFactory.HTTP_LOGGING_INTERCEPTOR);
//        okHttpClientBuilder.addNetworkInterceptor(InterceptorFactory.RESPONSE_CACHE_CONTROL_INTERCEPTOR);

        //缓存文件夹
        File cacheFile = new File(context.getExternalCacheDir(), "http");
        //缓存大小为10M
        int cacheSize = 10 * 1024 * 1024;
        //创建缓存对象
        final Cache cache = new Cache(cacheFile, cacheSize);
        okHttpClientBuilder.cache(cache);

        //ssl
        okHttpClientBuilder.sslSocketFactory(SSLFactory.getUnsafeSocketFactory());
        okHttpClientBuilder.hostnameVerifier((hostname, session) -> true);


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
