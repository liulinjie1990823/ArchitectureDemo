package com.llj.lib.webview;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.annotation.NonNull;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/3
 */
public class CWebViewLifecycleObserver implements DefaultLifecycleObserver {

    private JavaInvokeJs mJavaInvokeJs;
    private CWebView     mCWebView;

    public CWebViewLifecycleObserver(CWebView webView) {
        mCWebView = webView;
        mJavaInvokeJs = new JavaInvokeJs(webView);
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        mJavaInvokeJs.onCreate();
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        mJavaInvokeJs.onStart();
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        mJavaInvokeJs.onResume();
        mCWebView.performResume();
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        mJavaInvokeJs.onPause();
        mCWebView.performPause();
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        mJavaInvokeJs.onStop();
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        mJavaInvokeJs.onDestroy();
        mCWebView.performDestroy();
    }
}
