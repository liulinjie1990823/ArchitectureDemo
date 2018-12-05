package com.llj.lib.webview;

import com.tencent.smtt.sdk.WebView;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/3
 */
public class JavaInvokeJs {
    private WebView mWebView;

    public JavaInvokeJs(WebView webView) {
        mWebView = webView;
    }

    public void onCreate() {
        mWebView.loadUrl("javascript:onCreate()");
    }

    public void onStart() {
        mWebView.loadUrl("javascript:onStart()");
    }

    public void onResume() {
        mWebView.loadUrl("javascript:onResume()");
    }

    public void onPause() {
        mWebView.loadUrl("javascript:onPause()");
    }

    public void onStop() {
        mWebView.loadUrl("javascript:onStop()");
    }

    public void onDestroy() {
        mWebView.loadUrl("javascript:onDestroy()");
    }
}
