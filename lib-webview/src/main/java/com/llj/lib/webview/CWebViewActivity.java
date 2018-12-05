package com.llj.lib.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/3
 */
public class CWebViewActivity extends AppCompatActivity {
    public static final String KEY_URL = "url";

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, CWebViewActivity.class);
        intent.putExtra(KEY_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_webview);

        String url = getIntent().getStringExtra(KEY_URL);

        CWebView CWebView = new CWebView(getApplicationContext());
        addContentView(CWebView, new ViewGroup.LayoutParams(-1, -1));

        getLifecycle().addObserver(new CWebViewLifecycleObserver(CWebView));

        CWebView.loadUrl(url);
    }
}
