package com.llj.lib.socialization.wechat.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import bolts.Continuation;
import bolts.Task;
import com.llj.socialization.R;
import com.llj.socialization.init.SocialManager;
import com.llj.socialization.log.Logger;
import com.llj.socialization.share.ShareObject;
import com.llj.socialization.share.SharePlatformType;
import com.llj.socialization.share.ShareUtil;
import com.llj.socialization.share.ShareUtil.ImageEncodeToFileCallable;
import com.llj.socialization.share.callback.ShareListener;
import com.llj.socialization.share.interfaces.IShare;
import com.llj.socialization.share.model.ShareResult;
import com.llj.socialization.utils.Utils;
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
import java.io.File;
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

    private final WeakReference<Context> mWeakReference;
    private final ShareListener          mShareListener;

    public MyIWXAPIEventHandler(Context context, ShareListener shareListener) {
      mWeakReference = new WeakReference<>(context);
      mShareListener = shareListener;
    }

    @Override
    public void onReq(BaseReq baseReq) {

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
      Utils.finishActivity(mWeakReference.get());
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

  // 判断微信版本是否为7.0.13及以上
  public boolean checkVersionValid(Context context) {
    return mIWXAPI.getWXAppSupportAPI() >= 0x27000D00;
  }

  // 判断Android版本是否7.0及以上
  public boolean checkAndroidNotBelowN() {
    return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N;
  }

  public String getFileUri(Context context, File file) {
    if (file == null || !file.exists()) {
      return null;
    }
    // 要与`AndroidManifest.xml`里配置的`authorities`一致，假设你的应用包名为com.example.app
    String authority = context.getApplicationInfo().packageName + ".share.wechat.fileprovider";
    Uri contentUri = FileProvider.getUriForFile(context, authority, file);

    // 授权给微信访问路径
    context.grantUriPermission("com.tencent.mm",  // 这里填微信包名
        contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

    return contentUri.toString();   // contentUri.toString() 即是以"content://"开头的用于共享的路径
  }

  /**
   * 微信朋友圈:纯图片分享,使用本地file路径有效,url有些无效 微信:纯图片分享,可以使用url 所以这里统一转换为本地地址路劲。
   * <p/>
   * 7.0以上需要使用FileProvide进行分享
   *
   * @param activity
   * @param platform
   * @param shareObject
   */
  @Override
  public void shareImage(Activity activity, int platform, @NonNull ShareObject shareObject) {

    final WXMediaMessage message = new WXMediaMessage();

    Task.callInBackground(new ImageEncodeToFileCallable(activity, shareObject, mShareListener))
        .continueWith(task -> {
          if (task.getError() != null) {
            Logger.e(TAG, task.getError());
            sendFailure(activity, mShareListener, activity.getString(R.string.load_image_failure));
            return null;
          }
          //这个是单纯的本地地址，传给下一个Continuation获取缩略图用
          String imageLocalPath = task.getResult();

          String imageLocalContentPath = task.getResult();
          //7.0以上需要使用FileProvide进行分享
          if (checkVersionValid(activity) && checkAndroidNotBelowN()) {
            // 使用contentPath作为文件路径进行分享
            String filePath = task.getResult();
            File file = new File(filePath);
            imageLocalContentPath = getFileUri(activity, file);
          }

          if (ShareUtil.isGifPath(imageLocalPath) || shareObject.isShareEmoji()) {
            WXEmojiObject emoji = new WXEmojiObject();
            emoji.emojiPath = imageLocalContentPath;//图片路径
            message.mediaObject = emoji;
          } else {
            WXImageObject imageObject = new WXImageObject();
            imageObject.imagePath = imageLocalContentPath;//图片路径
            message.mediaObject = imageObject;
          }

          return imageLocalPath;
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

    Task.callInBackground(new ImageEncodeToFileCallable(activity, shareObject, mShareListener))
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
    Task.callInBackground(new ImageEncodeToFileCallable(activity, shareObject, mShareListener))
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
  public void handleResult(Activity activity, int requestCode, int resultCode, Intent data) {
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
