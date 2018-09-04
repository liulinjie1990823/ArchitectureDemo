package com.llj.lib.socialization.qq.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.llj.socialization.InstallUtil;
import com.llj.socialization.Logger;
import com.llj.socialization.R;
import com.llj.socialization.share.ShareObject;
import com.llj.socialization.share.ShareUtil;
import com.llj.socialization.share.SocialManager;
import com.llj.socialization.share.callback.ShareListener;
import com.llj.socialization.share.interfaces.IShare;
import com.llj.socialization.share.model.ShareResult;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.File;

import bolts.Continuation;
import bolts.Task;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/1/18.
 */

public class ShareQq implements IShare {
    //1.标题+链接
    //2.标题+内容+链接
    //PARAM_TITLE、PARAM_IMAGE_URL、PARAM_SUMMARY不能全为空，最少必须有一个是有值的。
    /**
     * qq分享
     * public static final int SHARE_TO_QQ_TYPE_DEFAULT = 1;图文
     * public static final int SHARE_TO_QQ_TYPE_AUDIO = 2;音频
     * public static final int SHARE_TO_QQ_TYPE_IMAGE = 5;//纯图
     * public static final int SHARE_TO_QQ_TYPE_APP = 6;//
     */
    public static final String TAG = "ShareQq";

    private Tencent mTencent;
    private String  mAppName;

    private ShareListener mShareListener;

    private IUiListener mIUiListener ;

    @Override
    public void init(Context context, ShareListener listener) {
        mTencent = Tencent.createInstance(SocialManager.CONFIG.getQqId(), context.getApplicationContext());
        mShareListener = listener;

        mIUiListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                mShareListener.onShareResponse(new ShareResult(mShareListener.getPlatform(), ShareResult.RESPONSE_SHARE_SUCCESS));
            }

            @Override
            public void onError(UiError uiError) {
                mShareListener.onShareResponse(new ShareResult(mShareListener.getPlatform(), ShareResult.RESPONSE_SHARE_FAILURE, uiError.errorMessage));
            }

            @Override
            public void onCancel() {
                mShareListener.onShareResponse(new ShareResult(mShareListener.getPlatform(), ShareResult.RESPONSE_SHARE_HAS_CANCEL));
            }
        };
    }

    public String getAppName() {
        return mAppName;
    }

    public void setAppName(String appName) {
        mAppName = appName;
    }


    @Override
    public void shareTitle(Activity activity, int platform, @NonNull ShareObject shareObject) {

        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);

        if (!TextUtils.isEmpty(mAppName)) {
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, mAppName);
        }
        params.putString(QQShare.SHARE_TO_QQ_TITLE, shareObject.getTitle());

        if (!TextUtils.isEmpty(shareObject.getTargetUrl())) {
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareObject.getTargetUrl());
        }
        shareQQ(activity, params);
    }


    @Override
    public void shareDescription(Activity activity, int platform, @NonNull ShareObject shareObject) {

        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);

        if (!TextUtils.isEmpty(mAppName)) {
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, mAppName);
        }
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareObject.getDescription());
        if (!TextUtils.isEmpty(shareObject.getTargetUrl())) {
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareObject.getTargetUrl());
        }
        shareQQ(activity, params);
    }

    @Override
    public void shareText(Activity activity, int platform, @NonNull ShareObject shareObject) {

        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);

        if (!TextUtils.isEmpty(mAppName)) {
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, mAppName);
        }
        params.putString(QQShare.SHARE_TO_QQ_TITLE, shareObject.getTitle());
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareObject.getDescription());
        if (!TextUtils.isEmpty(shareObject.getTargetUrl())) {
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareObject.getTargetUrl());
        }
        shareQQ(activity, params);
    }

    /**
     * 纯图分享只支持本地图片
     *
     * @param activity
     * @param platform
     * @param shareObject
     */
    @Override
    public void shareImage(Activity activity, int platform, @NonNull ShareObject shareObject) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);

        if (!TextUtils.isEmpty(mAppName)) {
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, mAppName);
        }
        if (!TextUtils.isEmpty(shareObject.getTargetUrl())) {
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareObject.getTargetUrl());
        }

        Task.callInBackground(new ShareUtil.ImageDecoderCallable(activity, shareObject, mShareListener))
                .continueWith(new Continuation<String, Void>() {
                    @Override
                    public Void then(Task<String> task) throws Exception {
                        if (task.getError() != null) {
                            Log.e(TAG, Log.getStackTraceString(task.getError()));
                            ShareUtil.sendFailure(activity, mShareListener, activity.getString(R.string.share_image_failure));
                            return null;
                        }
                        if (TextUtils.isEmpty(task.getResult())) {
                            ShareUtil.sendFailure(activity, mShareListener, activity.getString(R.string.load_image_failure));
                            return null;
                        }
                        if (!new File(task.getResult()).exists()) {
                            Log.e(TAG, activity.getString(R.string.local_file_does_not_exist));
                            ShareUtil.sendFailure(activity, mShareListener, activity.getString(R.string.local_file_does_not_exist));
                            return null;
                        }
                        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, task.getResult());
                        shareQQ(activity, params);
                        return null;
                    }
                }, Task.UI_THREAD_EXECUTOR);
    }

    /**
     * 分享web
     *
     * @param activity
     * @param platform
     * @param shareObject
     */
    @Override
    public void shareWeb(Activity activity, int platform, @NonNull ShareObject shareObject) {

        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);

        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, mAppName);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, shareObject.getTitle());
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareObject.getDescription());
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareObject.getTargetUrl());

        Task.callInBackground(new ShareUtil.ImageDecoderCallable(activity, shareObject, mShareListener))
                .continueWith((Continuation<String, Void>) task -> {
                    if (task.getError() != null) {
                        Logger.e(TAG, task.getError());
                        ShareUtil.sendFailure(activity, mShareListener, activity.getString(R.string.load_image_failure));
                        return null;
                    }
                    if (TextUtils.isEmpty(task.getResult())) {
                        ShareUtil.sendFailure(activity, mShareListener, activity.getString(R.string.load_image_failure));
                        return null;
                    }
                    if (new File(task.getResult()).exists()) {
                        //本地文件
                        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, task.getResult());
                    } else {
                        //网络文件
                        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, task.getResult());
                    }
                    shareQQ(activity, params);
                    return null;
                }, Task.UI_THREAD_EXECUTOR);
    }


    private void shareQQ(final Activity activity, Bundle params) {
        mTencent.shareToQQ(activity, params, mIUiListener);
    }

    @Override
    public void handleResult(int requestCode, int resultCode, Intent data) {
        Tencent.handleResultData(data, mIUiListener);
    }

    @Override
    public boolean isInstalled(Context context) {
        return InstallUtil.isQQInstalled(context);
    }

    @Override
    public void recycle() {
        if (mTencent != null) {
            mTencent.releaseResource();
            mTencent = null;
        }

    }
}
