package com.llj.lib.webview.manager;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-10-21
 */
public class WebViewConfig {
    private String mScheme;
    private IWebViewClient mIWebViewClient;

    public String getScheme() {
        return mScheme;
    }

    public void setScheme(String scheme) {
        mScheme = scheme;
    }

    public IWebViewClient getIWebViewClient() {
        return mIWebViewClient;
    }

    public void setIWebViewClient(IWebViewClient IWebViewClient) {
        mIWebViewClient = IWebViewClient;
    }
}
