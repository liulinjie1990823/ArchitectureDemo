package com.llj.component.service

import android.app.Application
import com.llj.component.service.http.ComponentHttpUrl
import com.llj.component.service.http.HeaderInterceptor
import com.llj.lib.net.Interceptors.InterceptorFactory
import com.llj.lib.net.ssl.SSLFactory
import com.llj.lib.net.utils.OkHttpClientUtils
import com.llj.lib.net.utils.RetrofitUtils
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import retrofit2.Retrofit
import java.io.File

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
@Module
class ComponentModule {

    @Provides
    fun provideRetrofit(context: Application): Retrofit {

        val builder = RetrofitUtils.createRxJava2Retrofit(ComponentHttpUrl.BASE_URL)

        val okHttpClientBuilder = OkHttpClientUtils.okHttpClientBuilder()

        //拦截器
        val urlHandlerInterceptor = InterceptorFactory.UrlHandlerInterceptor()
        okHttpClientBuilder.addInterceptor(urlHandlerInterceptor) //多url域名拦截
        okHttpClientBuilder.addInterceptor(HeaderInterceptor()) //自定义header
        //okHttpClientBuilder.addInterceptor(InterceptorFactory.AGENT_INTERCEPTOR)//自定义ua
        okHttpClientBuilder.addInterceptor(InterceptorFactory.REQUEST_CACHE_CONTROL_INTERCEPTOR)
        okHttpClientBuilder.addInterceptor(InterceptorFactory.HTTP_LOGGING_INTERCEPTOR) //日志拦截
        okHttpClientBuilder.addNetworkInterceptor(InterceptorFactory.RESPONSE_CACHE_CONTROL_INTERCEPTOR)

        //缓存文件夹
        val cacheFile = File(context.externalCacheDir, "http")
        //缓存大小为10M
        val cacheSize = 10 * 1024 * 1024
        //创建缓存对象
        val cache = Cache(cacheFile, cacheSize.toLong())
        okHttpClientBuilder.cache(cache)

        //ssl
        okHttpClientBuilder.sslSocketFactory(SSLFactory.getUnsafeSocketFactory())
        okHttpClientBuilder.hostnameVerifier { hostname, session -> true }


        builder.client(okHttpClientBuilder.build())

        return builder.build()
    }
}