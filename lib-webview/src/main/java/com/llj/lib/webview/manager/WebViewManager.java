package com.llj.lib.webview.manager;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-10-21
 */
public class WebViewManager {

    private WebViewConfig mWebViewConfig;

    private static WebViewManager sInstance;


    public static WebViewManager getInstance() {
        if (sInstance == null) {
            synchronized (WebViewManager.class) {
                if (sInstance == null) {
                    sInstance = new WebViewManager();
                }
            }
        }
        return sInstance;
    }

    public WebViewConfig getWebViewConfig() {
        return mWebViewConfig;
    }

    public void setWebViewConfig(WebViewConfig webViewConfig) {
        mWebViewConfig = webViewConfig;
    }
}
