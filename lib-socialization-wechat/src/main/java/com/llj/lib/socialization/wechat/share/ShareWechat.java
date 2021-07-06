package com.llj.lib.socialization.wechat.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;
import bolts.Continuation;
import bolts.Task;
import com.llj.socialization.R;
import com.llj.socialization.init.SocialManager;
import com.llj.socialization.log.Logger;
import com.llj.socialization.share.ShareObject;
import com.llj.socialization.share.SharePlatformType;
import com.llj.socialization.share.ShareUtil;
import com.llj.socialization.share.callback.ShareListener;
import com.llj.socialization.share.interfaces.IShare;
import com.llj.socialization.share.model.ShareResult;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXEmojiObject;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import java.lang.ref.WeakReference;

/**
 * PROJECT:babyphoto_app DESCRIBE: Created by llj on 2017/1/18.
 */

public class ShareWechat implements IShare {

  public static final String TAG = ShareWechat.class.getSimpleName();

  private IWXAPI mIWXAPI;

  private static final int TARGET_SIZE    = 200;
  private static final int THUMB_SIZE     = 32 * 1024;//32kb的字节（微信的限制）
  private static final int THUMB_SIZE_128 = 128 * 1024;//32kb的字节（微信的限制）

  private ShareListener      mShareListener;
  private IWXAPIEventHandler mIWXAPIEventHandler;

  @Override
  public void init(Context context, ShareListener listener) {
    mIWXAPI = WXAPIFactory.createWXAPI(context.getApplicationContext(),
        SocialManager.getConfig(context.getApplicationContext()).getWxId(), true);
    mIWXAPI.registerApp(SocialManager.getConfig(context.getApplicationContext()).getWxId());
    mShareListener = listener;

    mIWXAPIEventHandler = new MyIWXAPIEventHandler(context, listener);
  }

  private static class MyIWXAPIEventHandler implements IWXAPIEventHandler {

    private WeakReference<Context> mWeakReference;
    private ShareListener          mShareListener;

    public MyIWXAPIEventHandler(Context context, ShareListener shareListener) {
      mWeakReference = new WeakReference<>(context);
      mShareListener = shareListener;
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    void finishActivity(Context context) {
      if (context instanceof Activity) {
        Activity activity = (Activity) context;
        if (activity.getClass().getSimpleName().equals("ResponseActivity") && !activity
            .isDestroyed()) {
          activity.finish();
        }
      }
    }

    @Override
    public void onResp(BaseResp baseResp) {
      Log.e("ShareWeChat",
          "onResp:" + "resp.getType()=" + baseResp.getType() + " resp.errCode=" + baseResp.errCode
              + " resp.errStr=" + baseResp.errStr);

      if (ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX == baseResp.getType()) {
        //分享
        switch (baseResp.errCode) {
          case BaseResp.ErrCode.ERR_OK:
            mShareListener.onShareResponse(
                new ShareResult(mShareListener.getPlatform(), ShareResult.RESPONSE_SHARE_SUCCESS));
            break;
          case BaseResp.ErrCode.ERR_USER_CANCEL:
            mShareListener.onShareResponse(new ShareResult(mShareListener.getPlatform(),
                ShareResult.RESPONSE_SHARE_HAS_CANCEL));
            break;
          case BaseResp.ErrCode.ERR_SENT_FAILED:
            mShareListener.onShareResponse(
                new ShareResult(mShareListener.getPlatform(), ShareResult.RESPONSE_SHARE_FAILURE,
                    baseResp.errStr));
            break;
          case BaseResp.ErrCode.ERR_AUTH_DENIED:
            mShareListener.onShareResponse(new ShareResult(mShareListener.getPlatform(),
                ShareResult.RESPONSE_SHARE_AUTH_DENIED));
            break;
          default:
            mShareListener.onShareResponse(
                new ShareResult(mShareListener.getPlatform(), ShareResult.RESPONSE_SHARE_FAILURE,
                    baseResp.errStr));
            break;
        }
      }
      finishActivity(mWeakReference.get());
    }
  }

  @Override
  public void shareTitle(Activity activity, int platform, @NonNull ShareObject shareObject) {

    WXTextObject textObject = new WXTextObject();
    textObject.text = shareObject.getTitle();

    WXMediaMessage message = new WXMediaMessage();
    message.mediaObject = textObject;
    message.title = shareObject.getTitle();

    sendMessage(platform, message, buildTransaction("text"));
  }

  @Override
  public void shareDescription(Activity activity, int platform, @NonNull ShareObject shareObject) {
    WXTextObject textObject = new WXTextObject();
    textObject.text = shareObject.getDescription();

    WXMediaMessage message = new WXMediaMessage();
    message.mediaObject = textObject;
    message.description = shareObject.getDescription();

    sendMessage(platform, message, buildTransaction("text"));
  }


  /**
   * 微信分享title和targetUrl无用
   *
   * @param activity
   * @param platform
   */
  @Override
  public void shareText(Activity activity, int platform, @NonNull ShareObject shareObject) {
    WXTextObject textObject = new WXTextObject();
    textObject.text = shareObject.getDescription();

    WXMediaMessage message = new WXMediaMessage();//组装WXTextObject
    message.mediaObject = textObject;
    message.title = shareObject.getTitle();
    message.description = shareObject.getDescription();//设置描述

    sendMessage(platform, message, buildTransaction("text"));
  }


  /**
   * 微信朋友圈:纯图片分享,使用本地file路径有效,url有些无效 微信:纯图片分享,可以使用url
   * <p>
   * 这里统一转换为本地地址
   *
   * @param activity
   * @param platform
   * @param shareObject
   */
  @Override
  public void shareImage(Activity activity, int platform, @NonNull ShareObject shareObject) {

    final WXMediaMessage message = new WXMediaMessage();

    Task.callInBackground(new ShareUtil.ImageDecoderCallable(activity, shareObject, mShareListener))
        .continueWith(task -> {
          if (task.getError() != null) {
            Logger.e(TAG, task.getError());
            sendFailure(activity, mShareListener, activity.getString(R.string.load_image_failure));
            return null;
          }
          if (ShareUtil.isGifPath(task.getResult()) || shareObject.isShareEmoji()) {
            WXEmojiObject emoji = new WXEmojiObject();
            emoji.emojiPath = task.getResult();//图片路径
            message.mediaObject = emoji;
          } else {
            WXImageObject imageObject = new WXImageObject();
            imageObject.imagePath = task.getResult();//图片路径
            message.mediaObject = imageObject;
          }
          return task.getResult();
        })
        .continueWith(new ShareUtil.ThumbDataContinuation(TARGET_SIZE, THUMB_SIZE))
        .continueWith((Continuation<byte[], Void>) task -> {
          if (task.getError() != null) {
            Logger.e(TAG, task.getError());
            sendFailure(activity, mShareListener,
                activity.getString(R.string.compress_image_failure));
            return null;
          }
          if (task.getResult() == null) {
            sendFailure(activity, mShareListener,
                activity.getString(R.string.compress_image_failure));
            return null;
          }
          message.thumbData = task.getResult();
          sendMessage(platform, message, buildTransaction("image"));
          return null;
        }, Task.UI_THREAD_EXECUTOR);
  }

  @Override
  public void shareWeb(Activity activity, int platform, @NonNull ShareObject shareObject) {
    shareWebpage(activity, platform, shareObject);
  }

  public void shareWebpage(final Activity activity, final int platform,
      @NonNull ShareObject shareObject) {

    WXWebpageObject wxWebpageObject = new WXWebpageObject();
    wxWebpageObject.webpageUrl = shareObject.getTargetUrl();//网页链接

    final WXMediaMessage message = new WXMediaMessage();//组装WXWebpageObject
    message.mediaObject = wxWebpageObject;
    message.title = shareObject.getTitle();//网页标题
    message.description = shareObject.getDescription();//网页描述

    Task.callInBackground(new ShareUtil.ImageDecoderCallable(activity, shareObject, mShareListener))
        .continueWith(new ShareUtil.ThumbDataContinuation(TARGET_SIZE, THUMB_SIZE))
        .continueWith((Continuation<byte[], Void>) task -> {
          if (task.getError() != null) {
            Logger.e(TAG, task.getError());
            sendFailure(activity, mShareListener,
                activity.getString(R.string.compress_image_failure));
            return null;
          }
          if (task.getResult() == null) {
            sendFailure(activity, mShareListener,
                activity.getString(R.string.compress_image_failure));
            return null;
          }
          message.thumbData = task.getResult();
          sendMessage(platform, message, buildTransaction("webPage"));
          return null;
        }, Task.UI_THREAD_EXECUTOR);
  }

  //小程序类型分享
  @Override
  public void shareMiniProgram(Activity activity, int platform, @NonNull ShareObject shareObject) {
    WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
    miniProgramObj.webpageUrl = shareObject.getTargetUrl(); // 兼容低版本的网页链接
    miniProgramObj.miniprogramType = shareObject.getMiniprogramType();// 正式版:0，测试版:1，体验版:2
    miniProgramObj.userName = shareObject.getUserName();     // 小程序原始id
    miniProgramObj.path = shareObject
        .getPath();            //小程序页面路径；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"

    WXMediaMessage message = new WXMediaMessage(miniProgramObj);
    message.title = shareObject.getTitle();                    // 小程序消息title
    message.description = shareObject.getDescription();               // 小程序消息desc
    Task.callInBackground(new ShareUtil.ImageDecoderCallable(activity, shareObject, mShareListener))
        .continueWith(new ShareUtil.ThumbDataContinuation(500, THUMB_SIZE_128))
        .continueWith((Continuation<byte[], Void>) task -> {
          if (task.getError() != null) {
            Logger.e(TAG, task.getError());
            sendFailure(activity, mShareListener,
                activity.getString(R.string.compress_image_failure));
            return null;
          }
          if (task.getResult() == null) {
            sendFailure(activity, mShareListener,
                activity.getString(R.string.compress_image_failure));
            return null;
          }
          message.thumbData = task.getResult();
          sendMessage(platform, message, buildTransaction("miniProgram"));
          return null;
        }, Task.UI_THREAD_EXECUTOR);
  }

  @Override
  public void onNewIntent(Intent data) {
    mIWXAPI.handleIntent(data, mIWXAPIEventHandler);
  }

  @Override
  public void handleResult(int requestCode, int resultCode, Intent data) {
  }


  @Override
  public boolean isInstalled(Context context) {
    return mIWXAPI.isWXAppInstalled();
  }

  @Override
  public void recycle() {
    mIWXAPI.detach();
  }


  private void sendMessage(int platform, WXMediaMessage message, String transaction) {
    SendMessageToWX.Req req = new SendMessageToWX.Req();
    req.transaction = transaction;
    req.message = message;
    req.scene = platform == SharePlatformType.WECHAT_CIRCLE ? SendMessageToWX.Req.WXSceneTimeline
        : SendMessageToWX.Req.WXSceneSession;
    mIWXAPI.sendReq(req);
  }

  private String buildTransaction(String type) {
    return System.currentTimeMillis() + type;
  }

}
