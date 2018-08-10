package com.llj.socialization.share.imp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.llj.socialization.InstallUtil;
import com.llj.socialization.share.ShareUtil;
import com.llj.socialization.share.SocialManager;
import com.llj.socialization.share.callback.ShareListener;
import com.llj.socialization.share.interfaces.ShareSinaCustomInterface;
import com.llj.socialization.share.model.ShareImageObject;
import com.llj.socialization.share.model.ShareResult;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareHandler;

import bolts.Continuation;
import bolts.Task;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/1/18.
 */

public class ShareSina implements ShareSinaCustomInterface {
    public static final String TAG = ShareSina.class.getSimpleName();
    /**
     * 微博分享限制thumb image必须小于2097152，否则点击分享会没有反应
     */

    private WbShareHandler mWbShareHandler;

    private static final int TARGET_SIZE   = 1024;
    private static final int TARGET_LENGTH = 256 * 1024;

    private ShareListener mShareListener;

    public ShareSina(Context context, ShareListener shareListener) {
        AuthInfo authInfo = new AuthInfo(context, SocialManager.CONFIG.getSignId(),
                SocialManager.CONFIG.getSignRedirectUrl(),
                SocialManager.CONFIG.getSignScope());
        WbSdk.install(context, authInfo);

        mWbShareHandler = new WbShareHandler((Activity) context);
        mWbShareHandler.registerApp();

        mShareListener = shareListener;

    }

    @Override
    public void shareTitle(Activity activity, int platform, String title, String targetUrl) {
        TextObject textObject = new TextObject();
        textObject.title = title;
        WeiboMultiMessage message = new WeiboMultiMessage();
        message.textObject = textObject;

        sendRequest(activity, message);
    }

    @Override
    public void shareDescription(Activity activity, int platform, String description, String targetUrl) {
        final String text = String.format("%s %s", description, targetUrl);

        TextObject textObject = new TextObject();
        textObject.description = text;
        WeiboMultiMessage message = new WeiboMultiMessage();
        message.textObject = textObject;

        sendRequest(activity, message);
    }

    @Override
    public void shareText(Activity activity, int platform, String title, String description, String targetUrl) {
        final String text = String.format("%s %s %s", title, description, targetUrl);

        TextObject textObject = new TextObject();
        textObject.text = text;
        WeiboMultiMessage message = new WeiboMultiMessage();
        message.textObject = textObject;

        sendRequest(activity, message);
    }

    @Override
    public void shareImage(Activity activity, int platform, ShareImageObject shareImageObject, String targetUrl) {
        shareMedia(activity, platform, "", "", shareImageObject, targetUrl);
    }

    @Override
    public void shareMedia(final Activity activity, final int platform, String title, String description, ShareImageObject shareImageObject, String targetUrl) {
        final ImageObject imageObject = new ImageObject();

        final String text = String.format("%s %s", description, targetUrl);

        Task.callInBackground(new ShareUtil.ImageDecoderCallable(activity, shareImageObject, mShareListener))
                .continueWith(new Continuation<String, String>() {
                    @Override
                    public String then(Task<String> task) throws Exception {
                        if (task.getError() != null) {
                            mShareListener.onShareResponse(new ShareResult(mShareListener.getPlatform(), ShareResult.RESPONSE_SHARE_FAILURE, Log.getStackTraceString(task.getError())));
                            return null;
                        }
                        imageObject.imagePath = task.getResult();
                        return task.getResult();
                    }
                })
                .continueWith(new ShareUtil.ThumbDataContinuation(TARGET_SIZE, TARGET_LENGTH))
                .continueWith(new Continuation<byte[], Void>() {
                    @Override
                    public Void then(Task<byte[]> task) throws Exception {
                        if (task.getError() != null) {
                            mShareListener.onShareResponse(new ShareResult(mShareListener.getPlatform(), ShareResult.RESPONSE_SHARE_FAILURE, Log.getStackTraceString(task.getError())));
                            return null;
                        }
                        imageObject.imageData = task.getResult();

                        WeiboMultiMessage message = new WeiboMultiMessage();
                        message.imageObject = imageObject;

                        if (!TextUtils.isEmpty(text)) {
                            TextObject textObject = new TextObject();
                            textObject.text = text;
                            message.textObject = textObject;
                        }

                        sendRequest(activity, message);

                        return null;
                    }
                }, Task.UI_THREAD_EXECUTOR);
    }

    @Override
    public void shareCustom(Activity activity, int platform, String description, ShareImageObject shareImageObject, ShareListener listener) {

    }

    @Override
    public void handleResult(Intent data) {
        if (ShareUtil.mShareListenerWrap != null) {
            mWbShareHandler.doResultIntent(data, ShareUtil.mShareListenerWrap);
        }
    }

    @Override
    public boolean isInstalled(Context context) {
        return InstallUtil.isSinaInstalled(context);
    }

    @Override
    public void recycle() {
        mWbShareHandler = null;
    }

    private void sendRequest(Activity activity, WeiboMultiMessage message) {
        mWbShareHandler.shareMessage(message, false);
    }

    @Override
    public void sendFailure(Activity activity,ShareListener shareListener, String message) {
        activity.finish();
        shareListener.onShareResponse(new ShareResult(shareListener.getPlatform(), ShareResult.RESPONSE_SHARE_FAILURE, message));
    }
}
