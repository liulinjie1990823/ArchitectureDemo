package com.llj.login.api

import android.content.Context
import com.llj.lib.net.BaseApi
import com.llj.lib.net.Interceptors.InterceptorFactory
import com.llj.lib.net.ssl.SSLFactory
import com.llj.lib.net.utils.OkHttpClientUtils
import com.llj.lib.net.utils.RetrofitUtils
import okhttp3.Cache
import okhttp3.Interceptor
import retrofit2.Retrofit
import java.io.File

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/18
 */
class LoginApi(context: Context) : BaseApi<LoginApiService>(context) {


    companion object {
        private var mLoginApi: LoginApi? = null

        fun getInstance(context: Context): LoginApi {
            synchronized(LoginApi::class.java) {
                if (mLoginApi == null) {
                    mLoginApi = LoginApi(context)
                }
            }
            return mLoginApi as LoginApi
        }
    }

    fun getService(): LoginApiService {
        mService = mLoginApi?.retrofit?.create(LoginApiService::class.java)!!
        return mService
    }


    override fun createRetrofit(context: Context, baseUrl: String, vararg interceptors: Interceptor?): Retrofit {
        val builder = RetrofitUtils.createRxJava2Retrofit(baseUrl)

        val okHttpClientBuilder = OkHttpClientUtils.okHttpClientBuilder()

        //拦截器
        val urlHandlerInterceptor = InterceptorFactory.UrlHandlerInterceptor()
        okHttpClientBuilder.addInterceptor(urlHandlerInterceptor)
        okHttpClientBuilder.addInterceptor(InterceptorFactory.AGENT_INTERCEPTOR)
        //        okHttpClientBuilder.addInterceptor(InterceptorFactory.REQUEST_CACHE_CONTROL_INTERCEPTOR);
        okHttpClientBuilder.addInterceptor(InterceptorFactory.HTTP_LOGGING_INTERCEPTOR)
        //        okHttpClientBuilder.addNetworkInterceptor(InterceptorFactory.RESPONSE_CACHE_CONTROL_INTERCEPTOR);

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

    override fun init(context: Context) {
        mLoginApi?.initRetrofit(context, null, null)
    }
}