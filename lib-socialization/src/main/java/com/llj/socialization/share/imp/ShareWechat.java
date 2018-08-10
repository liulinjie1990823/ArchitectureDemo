package com.llj.socialization.share.imp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.llj.socialization.InstallUtil;
import com.llj.socialization.Logger;
import com.llj.socialization.R;
import com.llj.socialization.share.SharePlatformType;
import com.llj.socialization.share.ShareUtil;
import com.llj.socialization.share.SocialManager;
import com.llj.socialization.share.callback.ShareListener;
import com.llj.socialization.share.interfaces.ShareInterface;
import com.llj.socialization.share.model.ShareImageObject;
import com.llj.socialization.share.model.ShareResult;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXEmojiObject;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import bolts.Continuation;
import bolts.Task;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/1/18.
 */

public class ShareWechat implements ShareInterface {
    public static final String TAG = ShareWechat.class.getSimpleName();

    private IWXAPI mIWXAPI;

    private static final int TARGET_SIZE = 200;
    private static final int THUMB_SIZE  = 32 * 1024;//32kb的字节（微信的限制）

    private ShareListener mShareListener;

    public ShareWechat(Context context, ShareListener shareListener) {
        mIWXAPI = WXAPIFactory.createWXAPI(context, SocialManager.CONFIG.getWxId(), true);
        mIWXAPI.registerApp(SocialManager.CONFIG.getWxId());
        mShareListener = shareListener;
    }

    @Override
    public void shareTitle(Activity activity, int platform, String title, String targetUrl) {
        WXTextObject textObject = new WXTextObject();
        textObject.text = title;

        WXMediaMessage message = new WXMediaMessage();
        message.mediaObject = textObject;
        message.title = title;

        sendMessage(platform, message, buildTransaction("text"));
    }

    @Override
    public void shareDescription(Activity activity, int platform, String description, String targetUrl) {
        WXTextObject textObject = new WXTextObject();
        textObject.text = description;

        WXMediaMessage message = new WXMediaMessage();
        message.mediaObject = textObject;
        message.description = description;

        sendMessage(platform, message, buildTransaction("text"));
    }

    /**
     * 微信分享title和targetUrl无用
     *
     * @param activity
     * @param platform
     * @param title
     * @param description
     * @param targetUrl
     */
    @Override
    public void shareText(Activity activity, int platform, String title, String description, String targetUrl) {
        WXTextObject textObject = new WXTextObject();
        textObject.text = description;

        WXMediaMessage message = new WXMediaMessage();//组装WXTextObject
        message.mediaObject = textObject;
        message.title = title;
        message.description = description;//设置描述

        sendMessage(platform, message, buildTransaction("text"));
    }

    /**
     * 微信朋友圈:纯图片分享,使用本地file路径有效,url有些无效
     * 微信:纯图片分享,可以使用url
     * <p>
     * 这里统一转换为本地地址
     *
     * @param activity
     * @param platform
     * @param shareImageObject
     * @param targetUrl
     */
    @Override
    public void shareImage(final Activity activity, final int platform, final ShareImageObject shareImageObject, String targetUrl) {
        if (shareImageObject == null
                || (shareImageObject.getPathOrUrl() == null && shareImageObject.getBitmap() == null)) {
            sendFailure(activity,mShareListener, activity.getString(R.string.incorrect_parameter_passed_in));
            return;
        }

        final WXMediaMessage message = new WXMediaMessage();

        Task.callInBackground(new ShareUtil.ImageDecoderCallable(activity, shareImageObject, mShareListener))
                .continueWith(new Continuation<String, String>() {
                    @Override
                    public String then(Task<String> task) throws Exception {
                        if (task.getError() != null) {
                            Logger.e(TAG, task.getError());
                            sendFailure(activity,mShareListener, activity.getString(R.string.load_image_failure));
                            return null;
                        }
                        if (ShareUtil.isGifPath(task.getResult()) || shareImageObject.isShareEmoji()) {
                            WXEmojiObject emoji = new WXEmojiObject();
                            emoji.emojiPath = task.getResult();//图片路径
                            message.mediaObject = emoji;
                        } else {
                            WXImageObject imageObject = new WXImageObject();
                            imageObject.imagePath = task.getResult();//图片路径
                            message.mediaObject = imageObject;
                        }
                        return task.getResult();
                    }
                })
                .continueWith(new ShareUtil.ThumbDataContinuation(TARGET_SIZE, THUMB_SIZE))
                .continueWith(new Continuation<byte[], Void>() {
                    @Override
                    public Void then(Task<byte[]> task) throws Exception {
                        if (task.getError() != null) {
                            Logger.e(TAG, task.getError());
                            sendFailure(activity,mShareListener, activity.getString(R.string.compress_image_failure));
                            return null;
                        }
                        if (task.getResult() == null) {
                            sendFailure(activity,mShareListener, activity.getString(R.string.compress_image_failure));
                            return null;
                        }
                        message.thumbData = task.getResult();
                        sendMessage(platform, message, buildTransaction("image"));
                        return null;
                    }
                }, Task.UI_THREAD_EXECUTOR);
    }


    @Override
    public void shareMedia(final Activity activity, final int platform, String title, String description, final ShareImageObject shareImageObject, String targetUrl) {
        shareWebpage(activity, platform, title, description, shareImageObject, targetUrl);
    }

    public void shareWebpage(final Activity activity, final int platform, String title, String description, final ShareImageObject shareImageObject, String targetUrl) {
        if (TextUtils.isEmpty(targetUrl)) {
            sendFailure(activity,mShareListener, activity.getString(R.string.incorrect_parameter_passed_in));
            return;
        }

        WXWebpageObject wxWebpageObject = new WXWebpageObject();
        wxWebpageObject.webpageUrl = targetUrl;//网页链接

        final WXMediaMessage message = new WXMediaMessage();//组装WXWebpageObject
        message.mediaObject = wxWebpageObject;
        message.title = title;//网页标题
        message.description = description;//网页描述

        if (shareImageObject == null || (shareImageObject.getBitmap() == null && TextUtils.isEmpty(shareImageObject.getPathOrUrl()))) {
            sendMessage(platform, message, buildTransaction("webPage"));
            return;
        }

        Task.callInBackground(new ShareUtil.ImageDecoderCallable(activity, shareImageObject, mShareListener))
                .continueWith(new ShareUtil.ThumbDataContinuation(TARGET_SIZE, THUMB_SIZE))
                .continueWith(new Continuation<byte[], Void>() {
                    @Override
                    public Void then(Task<byte[]> task) throws Exception {
                        if (task.getError() != null) {
                            Logger.e(TAG, task.getError());
                            sendFailure(activity,mShareListener, activity.getString(R.string.compress_image_failure));
                            return null;
                        }
                        if (task.getResult() == null) {
                            sendFailure(activity,mShareListener, activity.getString(R.string.compress_image_failure));
                            return null;
                        }
                        message.thumbData = task.getResult();
                        sendMessage(platform, message, buildTransaction("webPage"));
                        return null;
                    }
                }, Task.UI_THREAD_EXECUTOR);
    }

    @Override
    public void handleResult(Intent data) {
        if (ShareUtil.mShareListenerWrap != null)
            mIWXAPI.handleIntent(data, ShareUtil.mShareListenerWrap);
    }

    @Override
    public boolean isInstalled(Context context) {
        return InstallUtil.isWeChatInstalled(mIWXAPI);
    }

    @Override
    public void recycle() {
        mIWXAPI.detach();
    }


    private void sendMessage(int platform, WXMediaMessage message, String transaction) {
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = transaction;
        req.message = message;
        req.scene = platform == SharePlatformType.WECHAT_CIRCLE ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        mIWXAPI.sendReq(req);
    }

    private String buildTransaction(String type) {
        return System.currentTimeMillis() + type;
    }

    @Override
    public void sendFailure(Activity activity,ShareListener shareListener, String message) {
        activity.finish();
        shareListener.onShareResponse(new ShareResult(shareListener.getPlatform(), ShareResult.RESPONSE_SHARE_FAILURE, message));
    }
}
