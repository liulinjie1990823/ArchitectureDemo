package com.llj.component.service

import android.app.Application
import com.llj.component.service.http.ComponentHttpUrl
import com.llj.component.service.http.HeaderInterceptor
import com.llj.lib.net.Interceptors.InterceptorFactory
import com.llj.lib.net.utils.OkHttpClientUtils
import com.llj.lib.net.utils.RetrofitUtils
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.io.File
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
@Module
class MiddleComponentModule {

  @Singleton
  @Provides
  fun provideOkHttpClient(context: Application): OkHttpClient {
    val okHttpClientBuilder = OkHttpClientUtils.okHttpClientBuilder()

    //拦截器
    okHttpClientBuilder.addInterceptor(InterceptorFactory.UrlHandlerInterceptor()) //多url域名拦截
    okHttpClientBuilder.addInterceptor(HeaderInterceptor()) //自定义header
    //okHttpClientBuilder.addInterceptor(InterceptorFactory.AGENT_INTERCEPTOR)//自定义ua
    //设置http请求header的缓存字段，可以控制返回数据的缓存，但是是在一条线程内，会导致页面显示变慢，应该先显示页面，再将数据进行缓存，所以不推荐
    //okHttpClientBuilder.addInterceptor(InterceptorFactory.REQUEST_CACHE_CONTROL_INTERCEPTOR)
    //通过addInterceptor添加，这样即使是获取的缓存数据也能打印出来
    okHttpClientBuilder.addInterceptor(InterceptorFactory.HTTP_LOGGING_INTERCEPTOR) //日志拦截
    //设置http返回header的缓存字段，一般不用自己设置，由服务端设置
    //okHttpClientBuilder.addNetworkInterceptor(InterceptorFactory.RESPONSE_CACHE_CONTROL_INTERCEPTOR)

    //缓存文件夹
    val cacheFile = File(context.externalCacheDir, "http")
    //缓存大小为10M
    val cacheSize = 10 * 1024 * 1024
    //创建缓存对象
    val cache = Cache(cacheFile, cacheSize.toLong())
    okHttpClientBuilder.cache(cache)

    //ssl
    okHttpClientBuilder.hostnameVerifier(object : HostnameVerifier {
      override fun verify(hostname: String?, session: SSLSession?): Boolean {
        return true
      }
    })


    return okHttpClientBuilder.build()
  }

  @Singleton
  @Provides
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    val builder = RetrofitUtils.createRxJava2Retrofit(ComponentHttpUrl.BASE_URL)
    builder.client(okHttpClient)
    return builder.build()
  }
}