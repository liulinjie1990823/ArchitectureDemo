package com.llj.lib.webview;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;

import java.util.Vector;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/3
 */
public class CookiesUtil {

    private static void clearCookieByUrl(String url, CookieManager pCookieManager, CookieSyncManager pCookieSyncManager) {
        Uri uri = Uri.parse(url);
        String host = uri.getHost();
        clearCookieByUrlInternal(url, pCookieManager, pCookieSyncManager);
        clearCookieByUrlInternal("http://." + host, pCookieManager, pCookieSyncManager);
        clearCookieByUrlInternal("https://." + host, pCookieManager, pCookieSyncManager);
    }

    private static void clearCookieByUrlInternal(String url, CookieManager pCookieManager, CookieSyncManager pCookieSyncManager) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        String cookieString = pCookieManager.getCookie(url);
        Vector<String> cookie = getCookieNamesByUrl(cookieString);
        if (cookie == null || cookie.isEmpty()) {
            return;
        }
        int len = cookie.size();
        for (int i = 0; i < len; i++) {
            pCookieManager.setCookie(url, cookie.get(i) + "=-1");
        }
        pCookieSyncManager.sync();
    }

    private static Vector<String> getCookieNamesByUrl(String cookie) {
        if (TextUtils.isEmpty(cookie)) {
            return null;
        }
        String[] cookieField = cookie.split(";");
        int len = cookieField.length;
        for (int i = 0; i < len; i++) {
            cookieField[i] = cookieField[i].trim();
        }
        Vector<String> allCookieField = new Vector<>();
        for (int i = 0; i < len; i++) {
            if (TextUtils.isEmpty(cookieField[i])) {
                continue;
            }
            if (!cookieField[i].contains("=")) {
                continue;
            }
            String[] singleCookieField = cookieField[i].split("=");
            allCookieField.add(singleCookieField[0]);
        }
        if (allCookieField.isEmpty()) {
            return null;
        }
        return allCookieField;
    }


    //移除所有的cookie
    public static void removeAllCookie(Context context) {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();

        CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(context.getApplicationContext());
        cookieSyncManager.sync();

    }

    //根据url移除cookie
    public static void removeCookie(Context context, String url) {
        CookieManager mCookieManager = CookieManager.getInstance();
        CookieSyncManager mCookieSyncManager = CookieSyncManager.createInstance(context.getApplicationContext());

        CookiesUtil.clearCookieByUrl(url, mCookieManager, mCookieSyncManager);
    }
}
