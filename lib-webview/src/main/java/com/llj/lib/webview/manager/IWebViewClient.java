package com.llj.lib.webview.manager;

import com.tencent.smtt.sdk.WebView;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-10-21
 */
public interface IWebViewClient {

    boolean shouldOverrideUrlLoading(WebView webView, String s);
}
