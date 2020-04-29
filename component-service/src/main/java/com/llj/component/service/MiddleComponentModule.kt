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
import okio.Buffer
import retrofit2.Retrofit
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.*

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
        val builder = OkHttpClientUtils.okHttpClientBuilder()

        //拦截器
        builder.addInterceptor(InterceptorFactory.UrlHandlerInterceptor()) //多url域名拦截
        builder.addInterceptor(HeaderInterceptor()) //自定义header
        //okHttpClientBuilder.addInterceptor(InterceptorFactory.AGENT_INTERCEPTOR)//自定义ua
        //设置http请求header的缓存字段，可以控制返回数据的缓存，但是是在一条线程内，会导致页面显示变慢，应该先显示页面，再将数据进行缓存，所以不推荐
        //okHttpClientBuilder.addInterceptor(InterceptorFactory.REQUEST_CACHE_CONTROL_INTERCEPTOR)
        //通过addInterceptor添加，这样即使是获取的缓存数据也能打印出来
        builder.addInterceptor(InterceptorFactory.HTTP_LOGGING_INTERCEPTOR) //日志拦截
        //设置http返回header的缓存字段，一般不用自己设置，由服务端设置
        //okHttpClientBuilder.addNetworkInterceptor(InterceptorFactory.RESPONSE_CACHE_CONTROL_INTERCEPTOR)

        //缓存文件夹
        val cacheFile = File(context.externalCacheDir, "http")
        //缓存大小为10M
        val cacheSize = 10 * 1024 * 1024
        //创建缓存对象
        val cache = Cache(cacheFile, cacheSize.toLong())
        builder.cache(cache)


        val x509TrustManager = object : X509TrustManager {
            //校验客户端的证书
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate?>?, authType: String?) {
            }

            //校验服务器返回的证书，主要是这个要实现
            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate?>?, authType: String?) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                return arrayOf<X509Certificate?>()
            }
        }
        //使用自己实现的TrustManager
        val trustAllCertsManager = arrayOf<TrustManager>(x509TrustManager)

        //SSLContext
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustAllCertsManager, SecureRandom())
        builder.sslSocketFactory(sslContext.socketFactory, x509TrustManager)
        //ssl
        builder.hostnameVerifier(object : HostnameVerifier {
            override fun verify(hostname: String?, session: SSLSession?): Boolean {
                return true
            }
        })


        return builder.build()
    }

    private fun getUnSafeSocketFactory(): SSLSocketFactory {
        //使用自己实现的TrustManager
        val trustAllCertsManager = arrayOf<TrustManager>(
                object : X509TrustManager {
                    //校验客户端的证书
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(chain: Array<X509Certificate?>?, authType: String?) {
                    }

                    //校验服务器返回的证书，主要是这个要实现
                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<X509Certificate?>?, authType: String?) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                        return arrayOf<X509Certificate?>()
                    }
                }
        )

        //SSLContext
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustAllCertsManager, SecureRandom())
        return sslContext.socketFactory
    }

    private fun getSocketFactory(inputStream: InputStream): SSLSocketFactory? {
        try {
            //生成Certificate
            val certificateFactory: CertificateFactory = CertificateFactory.getInstance("X.509")
            val certificate = certificateFactory.generateCertificate(inputStream)

            //生成KeyStore
            val keyStore: KeyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(null, null)
            try {
                keyStore.setCertificateEntry("hbh", certificate)
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            //生成TrustManager，使用系统的trustManagers
            val trustManagerFactory: TrustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(keyStore)
            val trustManagers = trustManagerFactory.trustManagers

            //SSLContext
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, trustManagers, SecureRandom())
            return sslContext.socketFactory
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


    private fun getCustomSocketFactory(inputStream: InputStream): SSLSocketFactory? {
        try {
            //生成Certificate
            val certificateFactory: CertificateFactory = CertificateFactory.getInstance("X.509")
            val certificate = certificateFactory.generateCertificate(inputStream)

            //生成KeyStore
            val keyStore: KeyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(null, null)
            try {
                keyStore.setCertificateEntry("hbh", certificate)
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            //使用自己实现的TrustManager
            val trustAllCertsManager = arrayOf<TrustManager>(
                    object : X509TrustManager {
                        //校验客户端的证书
                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(chain: Array<X509Certificate?>?, authType: String?) {
                        }

                        //校验服务器返回的证书，主要是这个要实现
                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(chain: Array<X509Certificate?>?, authType: String?) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                            return arrayOf<X509Certificate?>()
                        }
                    }
            )

            //SSLContext
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, trustAllCertsManager, SecureRandom())
            return sslContext.socketFactory
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun trustedCertificatesInputStream(): InputStream {
        val publicKey: String = ""
        return Buffer()
                .writeUtf8(publicKey)
                .inputStream()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val builder = RetrofitUtils.createRxJava2Retrofit(ComponentHttpUrl.BASE_URL)
        builder.client(okHttpClient)
        return builder.build()
    }
}