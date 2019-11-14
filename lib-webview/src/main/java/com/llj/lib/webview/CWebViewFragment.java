package com.llj.lib.webview;

import android.app.Activity;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.collection.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.llj.lib.webview.event.JsInvokeJavaEvent;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-28
 */
public class CWebViewFragment extends Fragment {
    private CWebView mCWebView;

    private ArrayMap<String, String> mCallback = new ArrayMap<>();//js

    private String mUrl;
    private int    mHeight;

    private CWebViewLifecycleObserver mCWebViewLifecycleObserver;
    private DefaultLifecycleObserver  mAppLifecycleObserver;
    private DefaultLifecycleObserver  mCustomLifecycleObserver;

    private             ValueCallback<Uri>   mUploadMessage;
    private             ValueCallback<Uri[]> mUploadMessage5;
    private             String               mFileType;
    public static final int                  FILECHOOSER_RESULTCODE               = 5173;
    public static final int                  FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 5174;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        ViewGroup inflate = (ViewGroup) inflater.inflate(R.layout.activity_c_webview, container);

        if (getArguments() == null) {
            return null;
        }
        mUrl = getArguments().getString("url");

        mCWebView = new CWebView(getContext().getApplicationContext());
        mCWebView.setOpenFileChooserCallBack(new CWebView.MyWebChromeClient.OpenFileChooserCallBack() {
            @Override
            public void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;

                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
            }

            @Override
            public boolean showFileChooserCallBack(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mUploadMessage5 = filePathCallback;
                    if (fileChooserParams.getAcceptTypes() != null && fileChooserParams.getAcceptTypes().length > 0) {
                        mFileType = fileChooserParams.getAcceptTypes()[0];
                    }
                    if (mFileType.equals("image/*")) {


                    } else {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType(mFileType);
                        startActivityForResult(intent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
                    }
                }
                return true;
            }
        });

        inflate.addView(mCWebView, new FrameLayout.LayoutParams(-1, -1));

        //当前页面生命周期回调
        mCWebViewLifecycleObserver = new CWebViewLifecycleObserver(mCWebView);
        getLifecycle().addObserver(mCWebViewLifecycleObserver);

        //应用前后台监听
        mAppLifecycleObserver = new DefaultLifecycleObserver() {

            @Override
            public void onStart(@NonNull LifecycleOwner owner) {
                String callback = mCallback.get(JsInvokeJavaEvent.APP_ACTIVATION);
                if (callback != null) {
                    mCWebView.loadUrlWrap("javascript:" + callback + "()");
                }
            }

            @Override
            public void onStop(@NonNull LifecycleOwner owner) {
                String callback = mCallback.get(JsInvokeJavaEvent.APP_BACKGROUND);
                if (callback != null) {
                    mCWebView.loadUrlWrap("javascript:" + callback + "()");
                }
            }
        };
        ProcessLifecycleOwner.get().getLifecycle().addObserver(mAppLifecycleObserver);

        //页面事件
        mCustomLifecycleObserver = new DefaultLifecycleObserver() {
            @Override
            public void onResume(@NonNull LifecycleOwner owner) {
                String callback = mCallback.get(JsInvokeJavaEvent.UI_VIEW_APPEAR);
                if (callback != null) {
                    mCWebView.loadUrlWrap("javascript:" + callback + "()");
                }
            }

            @Override
            public void onPause(@NonNull LifecycleOwner owner) {
                String callback = mCallback.get(JsInvokeJavaEvent.UI_VIEW_DISAPPEAR);
                if (callback != null) {
                    mCWebView.loadUrlWrap("javascript:" + callback + "()");
                }
            }
        };
        getLifecycle().addObserver(mCustomLifecycleObserver);

        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCWebView.loadUrlWrap(mUrl);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(JsInvokeJavaEvent baseResponse) {
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
            case JsInvokeJavaEvent.UI_SHOW_CLOSE:
                break;
            case JsInvokeJavaEvent.UI_SHOW_SHARE:
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
            if (getActivity() != null)
                getActivity().finish();
        } else if ("2".equals(closeType)) {
            // TODO: 2019-06-26 跳转页面
            if (getActivity() != null)
                getActivity().finish();
        }
    }

    @Override
    public void onDestroyView() {
        String callback = mCallback.get(JsInvokeJavaEvent.UI_VIEW_DESTROY);
        if (callback != null) {
            mCWebView.loadUrlWrap("javascript:" + callback + "()");
        }

        super.onDestroyView();

        EventBus.getDefault().unregister(this);
        //注销回调
        ProcessLifecycleOwner.get().getLifecycle().removeObserver(mAppLifecycleObserver);
        getLifecycle().removeObserver(mCWebViewLifecycleObserver);
        getLifecycle().removeObserver(mCustomLifecycleObserver);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5 && resultCode == Activity.RESULT_OK) {
            //5.0以上
            if (null == mUploadMessage5) {
                return;
            }
            if (mFileType.equals("image/*")) {
                //自己的选择照片
//                Uri[] results = null;
//                List<String> urlList = (List<String>) data.getSerializableExtra(PhotoPickerConfig.KEY_SELECTED_PHOTOS);
//                if (AbPreconditions.checkNotEmptyList(urlList)) {
//                    results = new Uri[urlList.size()];
//                    for (int i = 0; i < urlList.size(); i++) {
//                        results[i] = Uri.parse(urlList.get(i));
//                        //                        results[i] =  ImageUtils.getImageContentUri(this,new File(urlList.get(i)));
//                    }
//                }
//                    mUploadMessage5.onReceiveValue(results);
            } else {
                mUploadMessage5.onReceiveValue(new Uri[]{intent.getData()});
            }
            mUploadMessage5 = null;
        } else if (requestCode == FILECHOOSER_RESULTCODE) {
            //5.0以下
            if (null == mUploadMessage) {
                return;
            }
            Uri result = intent == null || resultCode != Activity.RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
    }
}
