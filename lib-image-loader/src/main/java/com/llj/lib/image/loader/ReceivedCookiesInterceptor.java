package com.llj.lib.image.loader;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/3
 */
public class ReceivedCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        List<String> headers = originalResponse.headers("Set-Cookie");

        if (!headers.isEmpty()) {
            HashSet<String> cookies = new HashSet<>(headers);

//            ASpUtils.getDefaultSharedPreferences().edit()
//                    .putStringSet(Constants.SP_KEY_COOKIE, cookies)
//                    .apply();
        }

        return originalResponse;
    }
}
