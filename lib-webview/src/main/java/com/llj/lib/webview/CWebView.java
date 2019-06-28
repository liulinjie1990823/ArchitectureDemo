package com.llj.lib.webview;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

/**
 * https://blog.csdn.net/carson_ho/article/details/52693322
 * ArchitectureDemo
 * describe:
 * author llj
 * date 2018/5/8
 */
public class CWebView extends WebView {
    public static final String TAG = CWebView.class.getSimpleName();

    private MyWebChromeClient.OpenFileChooserCallBack mOpenFileChooserCallBack;


    //设置打开文件监听
    public void setOpenFileChooserCallBack(MyWebChromeClient.OpenFileChooserCallBack openFileChooserCallBack) {
        mOpenFileChooserCallBack = openFileChooserCallBack;
    }

    public CWebView(Context context) {
        super(context);
        init();
    }

    public CWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public CWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public CWebView(Context context, AttributeSet attributeSet, int i, boolean b) {
        super(context, attributeSet, i, b);
        init();
    }

    public CWebView(Context context, AttributeSet attributeSet, int i, Map<String, Object> map, boolean b) {
        super(context, attributeSet, i, map, b);
        init();
    }

    private void init() {
        initSetting();

        initWebViewClient();

        initWebChromeClient();

        initDownloadListener();

        initLongClickListener();

        requestFocus();
    }

    private String domain;

    /**
     * 最好获取顶级域名的Domain，更好的适配cookie
     *
     * @param url
     */
    private void getDomain(String url) {
        try {
            HashMap<String, Boolean> suffixMap = new HashMap<>();
            suffixMap.put("com", true);
            suffixMap.put("cn", true);
            suffixMap.put("net", true);
            suffixMap.put("org", true);
            suffixMap.put("cc", true);
            String host = Uri.parse(url).getHost();
            domain = host;
            if (host == null) {
                return;
            }
            String[] urlArray = host.split("\\.");
            for (int i = urlArray.length - 1; i >= 0; i--) {
                if (suffixMap.get(urlArray[i]) == null) {
                    domain = "";
                    for (int j = i; j < urlArray.length; j++) {
                        domain += "." + urlArray[j];
                    }
                    break;
                }
            }
        } catch (Exception e) {

        }
    }

    private void setCookie(String url) {
        if (url == null) {
            return;
        }
        CookieSyncManager.createInstance(getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);


        HashMap<String, Object> map = new HashMap<>();

        map.put("app-key", "");
        map.put("client-id", "");
        map.put("device-id", "");

        for (String key : map.keySet()) {
            String sbCookie = String.format(key + "=%s", map.get(key)) +
                    String.format(";domain=%s", domain) +
                    String.format(";path=%s", "/");
            cookieManager.setCookie(url, sbCookie);
            Timber.i("setCookie -- %s", sbCookie);
        }
        CookieSyncManager.getInstance().sync();

        getCookie(url);
    }

    public void loadUrlWrap(String url) {
        Timber.i("loadUrlWrap -- %s", url);
        getDomain(url);
        setCookie(url);
        loadUrl(url);
    }

    private void getCookie(String url) {
        String cookie = CookieManager.getInstance().getCookie(url);
        Timber.i("SyncedCookie -- %s", cookie);
    }


    private void initSetting() {
        WebSettings webSettings = getSettings();
        //设置编码格式
        webSettings.setDefaultTextEncodingName("utf-8");
        //网页内容的宽度是否可大于WebView控件的宽度
        webSettings.setLoadWithOverviewMode(true);
        // 设置此属性，可任意比例缩放。
        webSettings.setUseWideViewPort(true);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        //隐藏原生的缩放控件
        webSettings.setDisplayZoomControls(false);
        // 允许访问文件
        webSettings.setAllowFileAccess(true);
        // WebView是否支持多个窗口。
        webSettings.setSupportMultipleWindows(false);
        // 排版适应屏幕,内容将自动缩放
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 设置缓存模式
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);


        //缓存https://www.jianshu.com/p/95d4d73be3d1
        // 使用localStorage则必须打开
        webSettings.setDomStorageEnabled(true);
        //数据库缓存,有默认路径
        webSettings.setDatabaseEnabled(true);
        //Application Cache 存储机制,必须设置路径
        webSettings.setAppCacheEnabled(true);
        final String cachePath = getContext().getDir("appCache", Context.MODE_PRIVATE).getPath();
        webSettings.setAppCachePath(cachePath);
        webSettings.setAppCacheMaxSize(5 * 1024 * 1024);

        //启用JavaScript执行,同时打开Indexed Database 存储机制
        webSettings.setJavaScriptEnabled(true);

        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        if (isNetworkConnected()) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
        }

        //WebView先不要自动加载图片，等页面finish后再发起图片加载
        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setLoadsImagesAutomatically(true);
        } else {
            webSettings.setLoadsImagesAutomatically(false);
        }


    }

    private void initWebViewClient() {
        setWebViewClient(new WebViewClient() {
            //对url进行拦截
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                Timber.i("shouldOverrideUrlLoading -- %s", s);
                return super.shouldOverrideUrlLoading(webView, s);
            }

            //在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
            @Override
            public void onLoadResource(WebView webView, String s) {
                super.onLoadResource(webView, s);
            }

            @Override
            public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
                super.onReceivedError(webView, webResourceRequest, webResourceError);
                //出现错误先清除原来的界面，再显示我们要显示的页面
                // loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
                // mErrorFrame.setVisibility(View.VISIBLE);
                switch (webResourceError.getErrorCode()) {
                    case 404:
                        break;
                }
            }

            //处理https请求错误
            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                super.onReceivedSslError(webView, sslErrorHandler, sslError);
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                Timber.i("onPageStarted -- %s", s);
            }

            //在Android4.4的手机上onPageFinished()回调会多调用一次,所以用onProgressChanged来代替
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                Timber.i("onPageFinished -- %s", s);
                if (!webView.getSettings().getLoadsImagesAutomatically()) {
                    webView.getSettings().setLoadsImagesAutomatically(true);
                }
            }

            //可以拦截到所有的网页中资源请求，比如加载JS，图片以及Ajax请求等等
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
                return super.shouldInterceptRequest(webView, s);
            }
        });
    }

    private void initWebChromeClient() {
        MyWebChromeClient myWebChromeClient = new MyWebChromeClient();
        myWebChromeClient.setOpenFileChooserCallBack(new MyWebChromeClient.OpenFileChooserCallBack() {
            @Override
            public void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType) {
                if (mOpenFileChooserCallBack != null) {
                    mOpenFileChooserCallBack.openFileChooserCallBack(uploadMsg, acceptType);
                }
            }

            @Override
            public boolean showFileChooserCallBack(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                return mOpenFileChooserCallBack != null && mOpenFileChooserCallBack.showFileChooserCallBack(webView, filePathCallback, fileChooserParams);
            }
        });
        setWebChromeClient(myWebChromeClient);
    }

    private void initDownloadListener() {
        setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                goToUpdate(getContext(), url);
            }
        });
    }

    private void initLongClickListener() {
        setOnLongClickListener(v -> true);
    }


    public void performResume() {
        resumeTimers();
        super.onResume();
    }

    public void performPause() {
        pauseTimers();
        super.onPause();

    }

    public void performDestroy() {
        loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        //只会webview访问历史记录里的所有记录除了当前访问记录
        clearHistory();
        //这个api仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据
        clearFormData();
        //
        clearCache(true);
        //移除cookie
        CookiesUtil.removeAllCookie(getContext().getApplicationContext());

        ((ViewGroup) getParent()).removeView(this);
        super.destroy();
    }


    //返回处理
    public void onBackPressed() {
        if (canGoBack()) {
            goBack();
        } else {
            Context context = getContext();
            if (context instanceof Activity) {
                ((Activity) getContext()).onBackPressed();
            } else {
                throw new RuntimeException("context not instanceof Activity");
            }
        }
    }


    private void goToUpdate(Context context, String url) {
        //去下载
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String downLoadUrl = url;
        if (!downLoadUrl.contains("http://")) {
            downLoadUrl = "http://" + downLoadUrl;
        }
        intent.setData(Uri.parse(downLoadUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @SuppressLint("MissingPermission")
    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Network[] networks = connectivityManager.getAllNetworks();
            if (networks == null || networks.length == 0) {
                return false;
            }
            for (Network mNetwork : networks) {
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo != null && networkInfo.isConnected()) {
                    return true;
                }
            }

        } else {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info == null || info.length == 0) {
                return false;
            }
            for (NetworkInfo networkInfo : info) {
                if (networkInfo != null && networkInfo.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static class MyWebChromeClient extends WebChromeClient {
        private OpenFileChooserCallBack mOpenFileChooserCallBack;

        public interface OpenFileChooserCallBack {
            void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType);

            boolean showFileChooserCallBack(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams);
        }


        ///////////////////////////////////////////////////////////////////////////
        // 文件操作
        ///////////////////////////////////////////////////////////////////////////
        public void setOpenFileChooserCallBack(OpenFileChooserCallBack openFileChooserCallBack) {
            mOpenFileChooserCallBack = openFileChooserCallBack;
        }


        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            Timber.i("openFileChooser < 3.0");
            mOpenFileChooserCallBack.openFileChooserCallBack(uploadMsg, "image/*");
        }

        // For Android >= 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            Timber.i("openFileChooser >= 3.0");
            mOpenFileChooserCallBack.openFileChooserCallBack(uploadMsg, acceptType);
        }

        // For Android >= 4.1
        @Override
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            Timber.i("openFileChooser >= 4.1");
            mOpenFileChooserCallBack.openFileChooserCallBack(uploadMsg, acceptType);
        }

        // For Android >= 5.0
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            Timber.i("onShowFileChooser >= 5.0");
            return mOpenFileChooserCallBack.showFileChooserCallBack(mWebView, filePathCallback, fileChooserParams);
        }

        ///////////////////////////////////////////////////////////////////////////
        // 相关方法
        ///////////////////////////////////////////////////////////////////////////
        //设置页面进度
        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
        }

        //设置页面title
        @Override
        public void onReceivedTitle(WebView webView, String s) {
            super.onReceivedTitle(webView, s);
        }

        @Override
        public void onRequestFocus(WebView webView) {
            super.onRequestFocus(webView);
        }

        @Override
        public boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult) {
            return super.onJsAlert(webView, s, s1, jsResult);
        }

        @Override
        public boolean onJsConfirm(WebView webView, String s, String s1, JsResult jsResult) {
            return super.onJsConfirm(webView, s, s1, jsResult);
        }

        @Override
        public boolean onJsPrompt(WebView webView, String s, String s1, String s2, JsPromptResult jsPromptResult) {
            return super.onJsPrompt(webView, s, s1, s2, jsPromptResult);
        }

        @Override
        public boolean onJsBeforeUnload(WebView webView, String s, String s1, JsResult jsResult) {
            return super.onJsBeforeUnload(webView, s, s1, jsResult);
        }

    }


}
