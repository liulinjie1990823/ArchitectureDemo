package com.llj.socialization.share;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.llj.socialization.INFO;
import com.llj.socialization.ResponseActivity;
import com.llj.socialization.share.callback.ShareListener;
import com.llj.socialization.share.imp.ShareQq;
import com.llj.socialization.share.imp.ShareQzone;
import com.llj.socialization.share.imp.ShareSina;
import com.llj.socialization.share.imp.ShareWechat;
import com.llj.socialization.share.interfaces.ShareInterface;
import com.llj.socialization.share.model.ShareImageObject;
import com.llj.socialization.share.model.ShareResult;
import com.llj.socialization.share.process.ImageDecoder;

import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/1/18.
 */

public class ShareUtil {
    public static final String TAG  = ShareUtil.class.getSimpleName();
    public static final int    TYPE = 10086;

    private static ShareInterface mShareInterface;
    public static  ShareListener  mShareListenerWrap;

    private static int mType;
    private final static int TYPE_TITLE       = 1;
    private final static int TYPE_DESCRIPTION = 2;
    private final static int TYPE_TEXT        = 3;
    private final static int TYPE_IMAGE       = 4;
    private final static int TYPE_MEDIA       = 5;

    private static int mPlatform;

    private static String mTitle;
    private static String mDescription;
    private static String mTargetUrl;

    private static ShareImageObject mShareImageObject;

    private static void reset() {
        mTitle = null;
        mDescription = null;
        mTargetUrl = null;
        mShareImageObject = null;
    }


    //分享title
    public static void shareTitle(Context context,
                                  @SharePlatformType.Platform int platform,
                                  String title,
                                  ShareListener listener) {
        mType = TYPE_TITLE;

        mPlatform = platform;
        mTitle = title;

        listener.setPlatform(platform);
        mShareListenerWrap = buildWrapListener(listener);

        ResponseActivity.start(context, TYPE);
    }


    //分享内容
    public static void shareDescription(Context context,
                                        @SharePlatformType.Platform int platform,
                                        String description,
                                        ShareListener listener) {
        mType = TYPE_DESCRIPTION;

        mPlatform = platform;
        mDescription = description;

        listener.setPlatform(platform);
        mShareListenerWrap = buildWrapListener(listener);

        ResponseActivity.start(context, TYPE);
    }

    //分享标题内容
    public static void shareText(Context context,
                                 @SharePlatformType.Platform int platform,
                                 String title,
                                 String description,
                                 String targetUrl,
                                 ShareListener listener) {
        mType = TYPE_TEXT;

        mPlatform = platform;
        mTitle = title;
        mDescription = description;
        mTargetUrl = targetUrl;


        listener.setPlatform(platform);
        mShareListenerWrap = buildWrapListener(listener);

        ResponseActivity.start(context, TYPE);
    }

    //纯图分享
    public static void shareImage(Context context,
                                  @SharePlatformType.Platform final int platform,
                                  final String urlOrPath,
                                  ShareListener listener) {
        mType = TYPE_IMAGE;

        mPlatform = platform;
        mShareImageObject = new ShareImageObject(urlOrPath);

        listener.setPlatform(platform);
        mShareListenerWrap = buildWrapListener(listener);

        ResponseActivity.start(context, TYPE);
    }

    //纯图分享
    public static void shareImage(Context context,
                                  @SharePlatformType.Platform final int platform,
                                  ShareImageObject shareImageObject,
                                  ShareListener listener) {
        mType = TYPE_IMAGE;

        mPlatform = platform;
        mShareImageObject = shareImageObject;

        listener.setPlatform(platform);
        mShareListenerWrap = buildWrapListener(listener);

        ResponseActivity.start(context, TYPE);
    }

    //纯图分享
    public static void shareImage(Context context,
                                  @SharePlatformType.Platform final int platform,
                                  final Bitmap bitmap,
                                  ShareListener listener) {
        mType = TYPE_IMAGE;

        mPlatform = platform;
        mShareImageObject = new ShareImageObject(bitmap);

        listener.setPlatform(platform);
        mShareListenerWrap = buildWrapListener(listener);

        ResponseActivity.start(context, TYPE);
    }

    //图文分享
    public static void shareMedia(Context context,
                                  @SharePlatformType.Platform int platform,
                                  String title,
                                  String description,
                                  Bitmap thumb,
                                  String targetUrl,
                                  ShareListener listener) {
        mType = TYPE_MEDIA;

        mPlatform = platform;
        mTitle = title;
        mDescription = description;
        mTargetUrl = targetUrl;
        mShareImageObject = new ShareImageObject(thumb);

        listener.setPlatform(platform);
        mShareListenerWrap = buildWrapListener(listener);

        ResponseActivity.start(context, TYPE);
    }

    //图文分享
    public static void shareMedia(Context context,
                                  @SharePlatformType.Platform int platform,
                                  String title,
                                  String description,
                                  String thumbUrlOrPath,
                                  String targetUrl,
                                  ShareListener listener) {
        mType = TYPE_MEDIA;

        mPlatform = platform;
        mTitle = title;
        mDescription = description;
        mTargetUrl = targetUrl;
        mShareImageObject = new ShareImageObject(thumbUrlOrPath);

        listener.setPlatform(platform);
        mShareListenerWrap = buildWrapListener(listener);

        ResponseActivity.start(context, TYPE);
    }

    /**
     * 执行分享操作
     *
     * @param activity
     */
    public static void perform(Activity activity) {
        //获取对应平台
        mShareInterface = getPlatform(mPlatform, activity);

        if (mShareListenerWrap == null || mShareInterface == null) {
            activity.finish();
            return;
        }

        if (!mShareInterface.isInstalled(activity)) {
            mShareListenerWrap.onShareResponse(new ShareResult(mPlatform, ShareResult.RESPONSE_SHARE_NOT_INSTALL));
            activity.finish();
            return;
        }
        mShareListenerWrap.onStart();

        switch (mType) {
            case TYPE_TITLE:
                mShareInterface.shareTitle(activity, mPlatform, mTitle, mTargetUrl);
                break;
            case TYPE_DESCRIPTION:
                mShareInterface.shareDescription(activity, mPlatform, mDescription, mTargetUrl);
                break;
            case TYPE_TEXT:
                mShareInterface.shareText(activity, mPlatform, mTitle, mDescription, mTargetUrl);
                break;
            case TYPE_IMAGE:
                mShareInterface.shareImage(activity, mPlatform, mShareImageObject, mTargetUrl);
                break;
            case TYPE_MEDIA:
                mShareInterface.shareMedia(activity, mPlatform, mTitle, mDescription, mShareImageObject, mTargetUrl);
                break;
        }
    }

    private static ShareInterface getPlatform(@SharePlatformType.Platform int platform, Context context) {
        switch (platform) {
            case SharePlatformType.WECHAT:
            case SharePlatformType.WECHAT_CIRCLE:
                return new ShareWechat(context, mShareListenerWrap);
            case SharePlatformType.QQ:
                return new ShareQq(context, mShareListenerWrap);
            case SharePlatformType.QQ_ZONE:
                return new ShareQzone(context, mShareListenerWrap);
            case SharePlatformType.SINA:
                return new ShareSina(context, mShareListenerWrap);
        }
        return null;
    }

    private static ShareListenerWrap buildWrapListener(ShareListener listener) {
        return new ShareListenerWrap(listener);
    }

    //微信分享获取缩略图
    public static class ThumbDataContinuation implements Continuation<String, byte[]> {
        private int mSize;
        private int mLength;

        public ThumbDataContinuation(int size, int length) {
            mSize = size;
            mLength = length;
        }

        @Override
        public byte[] then(Task<String> task) throws Exception {
            if (task.getError() != null) {
                Log.e(TAG, Log.getStackTraceString(task.getError()));
                return null;
            }
            return ImageDecoder.compress2Byte(task.getResult(), mSize, mLength);
        }
    }

    public static class ImageDecoderCallable implements Callable<String> {
        private Activity         mActivity;
        private ShareImageObject mShareImageObject;
        private ShareListener    mShareListener;

        public ImageDecoderCallable(Activity activity, ShareImageObject shareImageObject, ShareListener shareListener) {
            mActivity = activity;
            mShareImageObject = shareImageObject;
            mShareListener = shareListener;
        }

        @Override
        public String call() throws Exception {
            if (mShareImageObject == null) {
                return null;
            }
            if (ShareUtil.isGifPath(mShareImageObject.getPathOrUrl())) {
                //gif
                return ImageDecoder.decode(mActivity, mShareImageObject);
            } else {
                //非gif
                String imageLocalPath = ImageDecoder.decode(mActivity, mShareImageObject);

                //使用备用的bitmap
                if (TextUtils.isEmpty(imageLocalPath)) {
                    mShareImageObject.setPathOrUrl("");
                    mShareImageObject.setBitmap(mShareListener.getExceptionImage());
                    imageLocalPath = ImageDecoder.decode(mActivity, mShareImageObject);
                }
                //抛出
                if (TextUtils.isEmpty(imageLocalPath)) {
                    mShareListener.onShareResponse(new ShareResult(mShareListener.getPlatform(), ShareResult.RESPONSE_SHARE_FAILURE, "图片加载失败"));
                    return null;
                }
                String imageLocalPathWrap = mShareListener.imageLocalPathWrap(imageLocalPath);
                if (TextUtils.isEmpty(imageLocalPathWrap))
                    return imageLocalPath;
                else
                    return imageLocalPathWrap;
            }
        }
    }


    private static class ShareListenerWrap extends ShareListener {

        private final ShareListener mShareListener;

        ShareListenerWrap(ShareListener listener) {
            mShareListener = listener;
            setPlatform(mShareListener.getPlatform());
        }

        @Override
        public void onStart() {
            mShareListener.onStart();
        }

        @Override
        public void onShareResponse(ShareResult shareResult) {
            mShareListener.onShareResponse(shareResult);
            recycle();
        }

        @Override
        public String imageLocalPathWrap(String imageLocalPath) {
            return mShareListener.imageLocalPathWrap(imageLocalPath);
        }

        @Override
        public Bitmap getExceptionImage() {
            return mShareListener.getExceptionImage();
        }
    }

    public static void handleResult(Intent data) {
        // 微博分享会同时回调onActivityResult和onNewIntent， 而且前者返回的intent为null
        if (mShareInterface != null && data != null) {
            mShareInterface.handleResult(data);
        } else if (data == null) {
            if (mPlatform != SharePlatformType.SINA) {
                Log.e(TAG, INFO.HANDLE_DATA_NULL);
            }
        } else {
            Log.e(TAG, INFO.UNKNOWN_ERROR);
        }
    }

    public static void recycle() {
        mTitle = null;
        mDescription = null;
        mTargetUrl = null;

        // bitmap recycle
        if (mShareImageObject != null
                && mShareImageObject.getBitmap() != null
                && !mShareImageObject.getBitmap().isRecycled()) {
            mShareImageObject.getBitmap().recycle();
        }
        mShareImageObject = null;

        if (mShareInterface != null) {
            mShareInterface.recycle();
        }
        mShareInterface = null;

        mShareListenerWrap = null;
    }

    /**
     * 判断是否是gif
     *
     * @param path
     *
     * @return
     */
    public static boolean isGifPath(String path) {
        return path != null && (path.contains(".gif") || path.contains(".GIF"));
    }

    /**
     * 发送短信分享
     *
     * @param context ctx
     * @param phone   手机号
     * @param msg     内容
     */
    public static void sendSms(Context context, String phone, String msg) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (TextUtils.isEmpty(phone))
            phone = "";
        intent.setData(Uri.parse("smsto:" + phone));
        intent.putExtra("sms_body", msg);
        intent.setType("vnd.android-dir/mms-sms");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 发送邮件分享
     *
     * @param context ctx
     * @param mailto  email
     * @param subject 主题
     * @param msg     内容
     */
    public static void sendEmail(Context context, String mailto, String subject, String msg) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        if (TextUtils.isEmpty(mailto))
            mailto = "";
        intent.setData(Uri.parse("mailto:" + mailto));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void copyText(Context context, String text) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        ClipData clipData = ClipData.newPlainText("Label", text);
        clipboardManager.setPrimaryClip(clipData);
    }
}
