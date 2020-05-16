package com.llj.lib.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;
import com.llj.lib.webview.event.JsInvokeJavaEvent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

/**
 * ArchitectureDemo. describe: author llj date 2018/9/3
 */
public class CWebViewActivity extends AppCompatActivity {

  public static final String KEY_URL = "url";

  private ArrayMap<String, String> mCallback = new ArrayMap<>();

  public static void start(Context context, String url) {
    Intent intent = new Intent(context, CWebViewActivity.class);
    intent.putExtra(KEY_URL, url);
    context.startActivity(intent);
  }

  private CWebView mCWebView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_c_webview);

    EventBus.getDefault().register(this);

    String url = getIntent().getStringExtra(KEY_URL);

    mCWebView = new CWebView(getApplicationContext());
    addContentView(mCWebView, new ViewGroup.LayoutParams(-1, -1));

    ProcessLifecycleOwner.get().getLifecycle().addObserver(new DefaultLifecycleObserver() {
      @Override
      public void onCreate(@NonNull LifecycleOwner owner) {

      }

      @Override
      public void onStart(@NonNull LifecycleOwner owner) {

      }

      @Override
      public void onResume(@NonNull LifecycleOwner owner) {

      }

      @Override
      public void onPause(@NonNull LifecycleOwner owner) {

      }

      @Override
      public void onStop(@NonNull LifecycleOwner owner) {

      }

      @Override
      public void onDestroy(@NonNull LifecycleOwner owner) {

      }
    });
    getLifecycle().addObserver(new CWebViewLifecycleObserver(mCWebView));

    mCWebView.loadUrl(url);
  }

  @Override
  protected void onResume() {
    super.onResume();
    String callback = mCallback.get(JsInvokeJavaEvent.UI_VIEW_APPEAR);
    if (callback != null) {
      mCWebView.loadUrl("javascript:" + callback + "()");
    }
  }

  @Override
  protected void onStop() {
    super.onStop();
    String callback = mCallback.get(JsInvokeJavaEvent.UI_VIEW_DISAPPEAR);
    if (callback != null) {
      mCWebView.loadUrl("javascript:" + callback + "()");
    }
  }


  @Subscribe(threadMode = ThreadMode.MAIN)
  public void EventBus(JsInvokeJavaEvent baseResponse) {
    switch (baseResponse.handler) {
      case JsInvokeJavaEvent.APP_ACTIVATION:
        mCallback.put(JsInvokeJavaEvent.UI_VIEW_APPEAR, baseResponse.callback);
        break;
      case JsInvokeJavaEvent.APP_BACKGROUND:
        mCallback.put(JsInvokeJavaEvent.APP_BACKGROUND, baseResponse.callback);
        break;
      case JsInvokeJavaEvent.UI_VIEW_APPEAR:
        mCallback.put(JsInvokeJavaEvent.UI_VIEW_APPEAR, baseResponse.callback);
        break;
      case JsInvokeJavaEvent.UI_VIEW_DISAPPEAR:
        mCallback.put(JsInvokeJavaEvent.UI_VIEW_DISAPPEAR, baseResponse.callback);
        break;
      case JsInvokeJavaEvent.UI_VIEW_REFRESH:
        if (baseResponse.args == null) {
          return;
        }
        String pageName = baseResponse.args.optString("pageName");

        break;
      case JsInvokeJavaEvent.CLOSE_WAP:
        closeWap(baseResponse.args);
        break;
      case JsInvokeJavaEvent.APP_JUMP:
        if (baseResponse.args == null) {
          return;
        }
        String routePath = baseResponse.args.optString("routePath");
        break;
      case JsInvokeJavaEvent.UI_SHARE:
        break;
      case JsInvokeJavaEvent.DIRECT_SHARE:
        break;
      default:
        break;
    }
  }

  private void closeWap(JSONObject jsonObject) {
    if (jsonObject == null) {
      return;
    }
    String closeType = jsonObject.optString("closeType");
    String routePath = jsonObject.optString("routePath");
    if ("1".equals(closeType)) {
      finish();
    } else if ("2".equals(closeType)) {
      // TODO: 2019-06-26 跳转页面
      finish();
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);

    String callback = mCallback.get(JsInvokeJavaEvent.UI_VIEW_DESTROY);
    if (callback != null) {
      mCWebView.loadUrl("javascript:" + callback + "()");
    }
  }
}
