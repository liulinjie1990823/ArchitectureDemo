package com.llj.architecturedemo

import android.app.Application
import android.arch.persistence.room.Room
import com.llj.architecturedemo.api.TestApiService
import com.llj.architecturedemo.db.AppDb
import com.llj.architecturedemo.db.dao.MobileDao
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/6
 */
@Module
internal class AppComponentModule {

//    @Singleton
//    @Provides
//    fun provideRetrofit(context: Application): Retrofit {
//        val builder = RetrofitUtils.createRxJava2Retrofit(ComponentHttpUrl.BASE_URL)
//
//        val okHttpClientBuilder = OkHttpClientUtils.okHttpClientBuilder()
//
//        //拦截器
//        val urlHandlerInterceptor = InterceptorFactory.UrlHandlerInterceptor()
//        okHttpClientBuilder.addInterceptor(urlHandlerInterceptor)
//        okHttpClientBuilder.addInterceptor(InterceptorFactory.AGENT_INTERCEPTOR)
//        //        okHttpClientBuilder.addInterceptor(InterceptorFactory.REQUEST_CACHE_CONTROL_INTERCEPTOR);
//        okHttpClientBuilder.addInterceptor(InterceptorFactory.HTTP_LOGGING_INTERCEPTOR)
//        //        okHttpClientBuilder.addNetworkInterceptor(InterceptorFactory.RESPONSE_CACHE_CONTROL_INTERCEPTOR);
//
//        //缓存文件夹
//        val cacheFile = File(context.externalCacheDir, "http")
//        //缓存大小为10M
//        val cacheSize = 10 * 1024 * 1024
//        //创建缓存对象
//        val cache = Cache(cacheFile, cacheSize.toLong())
//        okHttpClientBuilder.cache(cache)
//
//        //ssl
//        okHttpClientBuilder.sslSocketFactory(SSLFactory.getUnsafeSocketFactory())
//        okHttpClientBuilder.hostnameVerifier { hostname, session -> true }
//
//
//        builder.client(okHttpClientBuilder.build())
//
//        return builder.build()
//    }

    @Singleton
    @Provides
    fun provideTestApiService(retrofit: Retrofit): TestApiService {
        return retrofit.create(TestApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideAppDb(app: Application): AppDb {
        return Room.databaseBuilder(app, AppDb::class.java, "app.db").build()
    }

    @Singleton
    @Provides
    fun provideMobileDao(appDb: AppDb): MobileDao {
        return appDb.mobileDao()
    }
}
