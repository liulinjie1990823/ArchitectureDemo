package com.llj.lib.socialization.sina.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import bolts.Continuation;
import bolts.Task;
import com.llj.socialization.init.SocialManager;
import com.llj.socialization.share.ShareObject;
import com.llj.socialization.share.ShareUtil;
import com.llj.socialization.share.ShareUtil.ImageEncodeToFileCallable;
import com.llj.socialization.share.callback.ShareListener;
import com.llj.socialization.share.interfaces.IShareSinaCustom;
import com.llj.socialization.share.model.ShareResult;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.common.UiError;
import com.sina.weibo.sdk.openapi.IWBAPI;
import com.sina.weibo.sdk.openapi.SdkListener;
import com.sina.weibo.sdk.openapi.WBAPIFactory;
import com.sina.weibo.sdk.share.WbShareCallback;

/**
 * PROJECT:babyphoto_app DESCRIBE: Created by llj on 2017/1/18.
 */

public class ShareSina implements IShareSinaCustom {

  public static final String TAG = ShareSina.class.getSimpleName();
  /**
   * 微博分享限制thumb image必须小于2097152，否则点击分享会没有反应
   */

  private             IWBAPI mWbApi;

  private static final int TARGET_SIZE   = 1024;
  private static final int TARGET_LENGTH = 256 * 1024;

  private ShareListener   mShareListener;
  private WbShareCallback mWbShareCallback;


  @Override
  public void init(Context context, ShareListener listener) {
    AuthInfo authInfo = new AuthInfo(context.getApplicationContext(),
        SocialManager.getConfig(context.getApplicationContext()).getSignId(),
        SocialManager.getConfig(context.getApplicationContext()).getSignRedirectUrl(),
        SocialManager.getConfig(context.getApplicationContext()).getSignScope());
    mWbApi = WBAPIFactory.createWBAPI(context.getApplicationContext());
    mWbApi.registerApp(context.getApplicationContext(), authInfo, new SdkListener() {
      @Override
      public void onInitSuccess() {

      }

      @Override
      public void onInitFailure(Exception e) {

      }
    });

    mShareListener = listener;

    mWbShareCallback = new WbShareCallback() {
      @Override
      public void onComplete() {
        finishActivity(context);
        mShareListener.onShareResponse(
            new ShareResult(mShareListener.getPlatform(), ShareResult.RESPONSE_SHARE_SUCCESS));
      }

      @Override
      public void onError(UiError uiError) {
        finishActivity(context);
        mShareListener.onShareResponse(
            new ShareResult(mShareListener.getPlatform(), ShareResult.RESPONSE_SHARE_FAILURE,
                uiError.errorMessage));
      }


      @Override
      public void onCancel() {
        finishActivity(context);
        mShareListener.onShareResponse(
            new ShareResult(mShareListener.getPlatform(), ShareResult.RESPONSE_SHARE_HAS_CANCEL));
      }

    };
  }

  @Override
  public void shareTitle(Activity activity, int platform, @NonNull ShareObject shareObject) {

    TextObject textObject = new TextObject();
    textObject.title = shareObject.getTitle();
    WeiboMultiMessage message = new WeiboMultiMessage();
    message.textObject = textObject;

    sendRequest(activity, message);
  }

  @Override
  public void shareDescription(Activity activity, int platform, @NonNull ShareObject shareObject) {
    final String text = String
        .format("%s %s", shareObject.getDescription(), shareObject.getTargetUrl());

    TextObject textObject = new TextObject();
    textObject.description = text;
    WeiboMultiMessage message = new WeiboMultiMessage();
    message.textObject = textObject;

    sendRequest(activity, message);
  }

  @Override
  public void shareText(Activity activity, int platform, @NonNull ShareObject shareObject) {
    final String text = String
        .format("%s %s %s", shareObject.getTitle(), shareObject.getDescription(),
            shareObject.getTargetUrl());

    TextObject textObject = new TextObject();
    textObject.text = text;
    WeiboMultiMessage message = new WeiboMultiMessage();
    message.textObject = textObject;

    sendRequest(activity, message);
  }

  @Override
  public void shareImage(Activity activity, int platform, @NonNull ShareObject shareObject) {
    final ImageObject imageObject = new ImageObject();
    Task.callInBackground(new ImageEncodeToFileCallable(activity, shareObject, mShareListener))
        .continueWith(task -> {
          if (task.getError() != null) {
            mShareListener.onShareResponse(
                new ShareResult(mShareListener.getPlatform(), ShareResult.RESPONSE_SHARE_FAILURE,
                    Log.getStackTraceString(task.getError())));
            return null;
          }
          //imageObject.imagePath = task.getResult();
          return task.getResult();
        })
        .continueWith(new ShareUtil.ThumbDataContinuation(TARGET_SIZE, TARGET_LENGTH))
        .continueWith((Continuation<byte[], Void>) task -> {
          if (task.getError() != null) {
            mShareListener.onShareResponse(
                new ShareResult(mShareListener.getPlatform(), ShareResult.RESPONSE_SHARE_FAILURE,
                    Log.getStackTraceString(task.getError())));
            return null;
          }
          imageObject.imageData = task.getResult();

          WeiboMultiMessage message = new WeiboMultiMessage();
          message.imageObject = imageObject;

          sendRequest(activity, message);

          return null;
        }, Task.UI_THREAD_EXECUTOR);
  }

  @Override
  public void shareWeb(Activity activity, int platform, @NonNull ShareObject shareObject) {

    final ImageObject imageObject = new ImageObject();

    final String text = String
        .format("%s %s", shareObject.getDescription(), shareObject.getTargetUrl());

    Task.callInBackground(new ImageEncodeToFileCallable(activity, shareObject, mShareListener))
        .continueWith(task -> {
          if (task.getError() != null) {
            mShareListener.onShareResponse(
                new ShareResult(mShareListener.getPlatform(), ShareResult.RESPONSE_SHARE_FAILURE,
                    Log.getStackTraceString(task.getError())));
            return null;
          }
          imageObject.imagePath = task.getResult();
          return task.getResult();
        })
        .continueWith(new ShareUtil.ThumbDataContinuation(TARGET_SIZE, TARGET_LENGTH))
        .continueWith((Continuation<byte[], Void>) task -> {
          if (task.getError() != null) {
            mShareListener.onShareResponse(
                new ShareResult(mShareListener.getPlatform(), ShareResult.RESPONSE_SHARE_FAILURE,
                    Log.getStackTraceString(task.getError())));
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
        }, Task.UI_THREAD_EXECUTOR);
  }

  @Override
  public void shareCustom(Activity activity, int platform, ShareObject shareObject,
      ShareListener listener) {

  }

  @Override
  public void onNewIntent(Intent data) {
  }

  @Override
  public void handleResult(Activity activity, int requestCode, int resultCode, Intent data) {
    mWbApi.doResultIntent(data, mWbShareCallback);
  }

  @Override
  public boolean isInstalled(Context context) {
    return mWbApi.isWBAppInstalled();
  }

  @Override
  public void recycle() {
    mWbApi = null;
  }

  private void sendRequest(Activity activity, WeiboMultiMessage message) {
    mWbApi.shareMessage(activity, message, false);
  }
}
