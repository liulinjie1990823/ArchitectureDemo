package com.llj.socialization.share.imp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.llj.socialization.InstallUtil;
import com.llj.socialization.Logger;
import com.llj.socialization.R;
import com.llj.socialization.share.ShareUtil;
import com.llj.socialization.share.SocialManager;
import com.llj.socialization.share.callback.ShareListener;
import com.llj.socialization.share.interfaces.ShareInterface;
import com.llj.socialization.share.model.ShareImageObject;
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

public class ShareQq implements ShareInterface {
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

    public ShareQq(Context context, ShareListener shareListener) {
        mTencent = Tencent.createInstance(SocialManager.CONFIG.getQqId(), context.getApplicationContext());
        mShareListener = shareListener;
    }

    public String getAppName() {
        return mAppName;
    }

    public void setAppName(String appName) {
        mAppName = appName;
    }

    @Override
    public void shareTitle(Activity activity, int platform, String title, String targetUrl) {

        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);

        if (!TextUtils.isEmpty(mAppName)) {
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, mAppName);
        }
        if (!TextUtils.isEmpty(title)) {
            params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        }
        if (!TextUtils.isEmpty(targetUrl)) {
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
        }
        shareQQ(activity, params);
    }

    @Override
    public void shareDescription(Activity activity, int platform, String description, String targetUrl) {

        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);

        if (!TextUtils.isEmpty(mAppName)) {
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, mAppName);
        }
        if (!TextUtils.isEmpty(description)) {
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, description);
        }
        if (!TextUtils.isEmpty(targetUrl)) {
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
        }
        shareQQ(activity, params);
    }

    /**
     * 分享文字标题
     *
     * @param activity
     * @param platform
     * @param title
     * @param description
     * @param targetUrl
     */
    @Override
    public void shareText(Activity activity, int platform, String title, String description, String targetUrl) {

        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);

        if (!TextUtils.isEmpty(mAppName)) {
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, mAppName);
        }
        if (!TextUtils.isEmpty(title)) {
            params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        }
        if (!TextUtils.isEmpty(description)) {
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, description);
        }
        if (!TextUtils.isEmpty(targetUrl)) {
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
        }
        shareQQ(activity, params);
    }

    /**
     * 纯图分享只支持本地图片
     *
     * @param activity
     * @param platform
     * @param shareImageObject
     * @param targetUrl
     */
    @Override
    public void shareImage(final Activity activity, int platform, final ShareImageObject shareImageObject, final String targetUrl) {
        if (shareImageObject == null
                || (shareImageObject.getPathOrUrl() == null && shareImageObject.getBitmap() == null)) {
            sendFailure(activity,mShareListener, activity.getString(R.string.incorrect_parameter_passed_in));
            return;
        }

        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);

        if (!TextUtils.isEmpty(mAppName)) {
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, mAppName);
        }
        if (!TextUtils.isEmpty(targetUrl)) {
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
        }

        Task.callInBackground(new ShareUtil.ImageDecoderCallable(activity, shareImageObject, mShareListener))
                .continueWith(new Continuation<String, Void>() {
                    @Override
                    public Void then(Task<String> task) throws Exception {
                        if (task.getError() != null) {
                            Log.e(TAG, Log.getStackTraceString(task.getError()));
                            sendFailure(activity,mShareListener, activity.getString(R.string.share_image_failure));
                            return null;
                        }
                        if(TextUtils.isEmpty(task.getResult())){
                            sendFailure(activity,mShareListener,activity.getString(R.string.load_image_failure));
                            return null;
                        }
                        if (!new File(task.getResult()).exists()) {
                            Log.e(TAG, activity.getString(R.string.local_file_does_not_exist));
                            sendFailure(activity,mShareListener, activity.getString(R.string.local_file_does_not_exist));
                            return null;
                        }
                        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, task.getResult());
                        shareQQ(activity, params);
                        return null;
                    }
                }, Task.UI_THREAD_EXECUTOR);
    }

    /**
     * 分享图文
     *
     * @param activity
     * @param platform
     * @param title
     * @param description
     * @param shareImageObject
     * @param targetUrl
     */
    @Override
    public void shareMedia(final Activity activity, int platform, String title, String description, final ShareImageObject shareImageObject, final String targetUrl) {
        if (TextUtils.isEmpty(targetUrl)) {
            sendFailure(activity,mShareListener, activity.getString(R.string.incorrect_parameter_passed_in));
            return;
        }

        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);

        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, mAppName);
        if (!TextUtils.isEmpty(title)) {
            params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        }
        if (!TextUtils.isEmpty(description)) {
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, description);
        }
        if (!TextUtils.isEmpty(targetUrl)) {
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
        }

        if (shareImageObject == null || (shareImageObject.getBitmap() == null && TextUtils.isEmpty(shareImageObject.getPathOrUrl()))) {
            shareQQ(activity, params);
            return;
        }
        Task.callInBackground(new ShareUtil.ImageDecoderCallable(activity, shareImageObject, mShareListener))
                .continueWith(new Continuation<String, Void>() {
                    @Override
                    public Void then(Task<String> task) throws Exception {
                        if (task.getError() != null) {
                            Logger.e(TAG, task.getError());
                            sendFailure(activity,mShareListener, activity.getString(R.string.load_image_failure));
                            return null;
                        }
                        if(TextUtils.isEmpty(task.getResult())){
                            sendFailure(activity,mShareListener,activity.getString(R.string.load_image_failure));
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
                    }
                }, Task.UI_THREAD_EXECUTOR);
    }

    @Override
    public void handleResult(Intent data) {
        if (ShareUtil.mShareListenerWrap != null)
            Tencent.handleResultData(data, ShareUtil.mShareListenerWrap);
    }

    @Override
    public boolean isInstalled(Context context) {
        return InstallUtil.isQQInstalled(context);
    }

    private void shareQQ(final Activity activity, Bundle params) {
        mTencent.shareToQQ(activity, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                mShareListener.onComplete(o);
            }

            @Override
            public void onError(UiError uiError) {
                activity.finish();
                mShareListener.onError(uiError);
            }

            @Override
            public void onCancel() {
                mShareListener.onCancel();
            }
        });
    }

    @Override
    public void recycle() {
        if (mTencent != null) {
            mTencent.releaseResource();
            mTencent = null;
        }

    }

    @Override
    public void sendFailure(Activity activity,ShareListener shareListener, String message) {
        activity.finish();
        shareListener.onShareResponse(new ShareResult(shareListener.getPlatform(), ShareResult.RESPONSE_SHARE_FAILURE, message));
    }
}
