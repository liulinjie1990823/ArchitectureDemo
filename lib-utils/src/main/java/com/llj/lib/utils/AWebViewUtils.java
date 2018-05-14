package com.llj.lib.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by liulj on 2016/11/22.
 * WEBVIEW utils
 */

public class AWebViewUtils {
    /**
     * @param webView
     */
    public static void initWebViewSetting(final WebView webView) {
        WebSettings webSettings = webView.getSettings();
//        try {
//            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //允许js交互,允许中文编码
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(false);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);

        //Android webview 从Lollipop(5.0)开始webview默认不允许混合模式，https当中不能加载http资源，需要设置开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setHapticFeedbackEnabled(false);
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                AIntentUtils.goToUpdate(webView.getContext(), url);
            }
        });
    }


    public static final String ALIPAY_SCHEME = "alipays";
    public static final String TAOBAO_SCHEME = "taobao";
    public static final String TMALL_SCHEME  = "tmall";

    public static boolean shouldOverrideIntentUrl(Context context, String url) {
        Uri uri = Uri.parse(url);
        // 不是 http 协议，不是内部协议
        if (uri != null && uri.getScheme() != null && !uri.getScheme().startsWith("http") && !uri.getScheme().startsWith("https")) {
            try {
                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setComponent(null);
                intent.setSelector(null);
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof ActivityNotFoundException) {
                    // 找不到客户端
                    switch (uri.getScheme()) {
                        case ALIPAY_SCHEME:
                            AToastUtils.show("未检测到支付宝客户端");
                            break;
                        case TAOBAO_SCHEME:
                            AToastUtils.show("未检测到淘宝客户端");
                            break;
                        case TMALL_SCHEME:
                            AToastUtils.show("未检测到天猫客户端");
                            break;
                        default:
                            AToastUtils.show("未检测到客户端");
                            break;
                    }
                }
                return true;
            }
            return true;
        }
        return false;
    }


}
