package com.llj.lib.net.Interceptors;

import android.support.annotation.RequiresPermission;
import android.text.TextUtils;

import com.llj.lib.net.url.DomainUrlParser;
import com.llj.lib.net.url.UrlParser;
import com.llj.lib.utils.ANetWorkUtils;
import com.llj.lib.utils.helper.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;

/**
 * ArchitectureDemo
 * describe:
 * author llj
 * date 2018/6/12
 */
public class InterceptorFactory {

    public static Interceptor AGENT_INTERCEPTOR = chain -> {
        Request newRequest = chain.request().newBuilder()
                .removeHeader("User-Agent")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
                .build();
        return chain.proceed(newRequest);
    };

    public static Interceptor REQUEST_CACHE_CONTROL_INTERCEPTOR = chain -> {
        CacheControl.Builder cacheBuilder = new CacheControl.Builder();
        cacheBuilder.maxAge(60 * 5, TimeUnit.SECONDS);
        CacheControl cacheControl = cacheBuilder.build();
        Request request = chain.request();
        request = request.newBuilder()
                .cacheControl(cacheControl)
                .build();

        return chain.proceed(request);
    };

    //设置日志的Interceptor
    public static Interceptor HTTP_LOGGING_INTERCEPTOR          = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    //设置缓存的header
    public static Interceptor RESPONSE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @RequiresPermission(ACCESS_NETWORK_STATE)
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            if (ANetWorkUtils.isNetworkConnected(Utils.getApp())) {
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

    public static class UrlHandlerInterceptor implements Interceptor {
        private static final String DOMAIN_HEADER_KEY       = "Domain-Name";
        public static final  String DOMAIN_HEADER = DOMAIN_HEADER_KEY + ": ";

        private final Map<String, HttpUrl> mDomainNameHub = new HashMap<>();

        private UrlParser mUrlParser;

        public UrlHandlerInterceptor() {
            mUrlParser=new DomainUrlParser();
        }

        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();
            Request.Builder newBuilder = request.newBuilder();

            String domainName = obtainDomainNameFromHeaders(request);

            HttpUrl httpUrl = null;
            if (!TextUtils.isEmpty(domainName)) {
                httpUrl = checkUrl(domainName);
                newBuilder.removeHeader(DOMAIN_HEADER_KEY);
            }

            if (null != httpUrl) {
                HttpUrl newUrl = mUrlParser.parseUrl(httpUrl, request.url());

                request = newBuilder
                        .url(newUrl)
                        .build();
            }

            return chain.proceed(request);
        }

        /**
         * 存放 Domain(BaseUrl) 的映射关系
         *
         * @param domainName
         * @param domainUrl
         */
        public void putDomain(String domainName, String domainUrl) {
            checkNotNull(domainName, "domainName cannot be null");
            checkNotNull(domainUrl, "domainUrl cannot be null");
            synchronized (mDomainNameHub) {
                mDomainNameHub.put(domainName, checkUrl(domainUrl));
            }
        }

        /**
         * 取出对应 {@code domainName} 的 Url(BaseUrl)
         *
         * @param domainName
         * @return
         */
        public synchronized HttpUrl fetchDomain(String domainName) {
            checkNotNull(domainName, "domainName cannot be null");
            return mDomainNameHub.get(domainName);
        }

        /**
         * 移除某个 {@code domainName}
         *
         * @param domainName {@code domainName}
         */
        public void removeDomain(String domainName) {
            checkNotNull(domainName, "domainName cannot be null");
            synchronized (mDomainNameHub) {
                mDomainNameHub.remove(domainName);
            }
        }

        /**
         * 清理所有 Domain(BaseUrl)
         */
        public void clearAllDomain() {
            mDomainNameHub.clear();
        }

        /**
         * 存放 Domain(BaseUrl) 的容器中是否存在这个 {@code domainName}
         *
         * @param domainName {@code domainName}
         * @return {@code true} 为存在, {@code false} 为不存在
         */
        public synchronized boolean haveDomain(String domainName) {
            return mDomainNameHub.containsKey(domainName);
        }

        private String obtainDomainNameFromHeaders(Request request) {
            List<String> headers = request.headers(DOMAIN_HEADER_KEY);
            if (headers == null || headers.size() == 0)
                return null;
            if (headers.size() > 1)
                throw new IllegalArgumentException("Only one Domain-Name in the headers");
            return request.header(DOMAIN_HEADER_KEY);
        }

        private HttpUrl checkUrl(String url) {
            HttpUrl parseUrl = HttpUrl.parse(url);
            if (null == parseUrl) {
                throw new RuntimeException("You've configured an invalid url : " + (TextUtils.isEmpty(url) ? "EMPTY_OR_NULL_URL" : url));
            } else {
                return parseUrl;
            }
        }

        private <T> T checkNotNull(T object, String message) {
            if (object == null) {
                throw new NullPointerException(message);
            }
            return object;
        }
    }
}
