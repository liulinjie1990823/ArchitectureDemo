package com.llj.lib.image.loader;

import android.util.Log;

import com.llj.lib.utils.ASpUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/3
 */
public class AddCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        Set<String> stringSet = ASpUtils.getDefaultSharedPreferences().getStringSet(Constants.SP_KEY_COOKIE, new HashSet<String>());
        HashSet<String> preferences = (HashSet<String>) stringSet;

        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
            Log.v("OkHttp", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
        }
        return chain.proceed(builder.build());
    }
}
