package com.llj.socialization.share;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import bolts.Continuation;
import bolts.Task;
import com.llj.socialization.R;
import com.llj.socialization.ResponseActivity;
import com.llj.socialization.share.callback.ShareListener;
import com.llj.socialization.share.interfaces.IShare;
import com.llj.socialization.share.model.ShareResult;
import com.llj.socialization.share.process.ImageEncode;
import java.util.concurrent.Callable;

/**
 * PROJECT: DESCRIBE: Created by llj on 2017/1/18.
 */

public class ShareUtil {

  public static final String  TAG  = ShareUtil.class.getSimpleName();
  public static final int     TYPE = 10086;
  public static       boolean sIsInProcess;//避免华为手机多次调用WXEntryActivity，通过变量判断是否已经启动过ResponseActivity

  private static IShare        sIShare;
  public static  ShareListener mShareListenerWrap;

  private static       int mType;
  private final static int TYPE_TITLE       = 1;
  private final static int TYPE_DESCRIPTION = 2;
  private final static int TYPE_TEXT        = 3;
  private final static int TYPE_IMAGE       = 4;
  private final static int TYPE_WEB         = 5;
  private final static int TYPE_MINIPROGRAM = 6;

  private static int mPlatform;

  private static ShareObject mShareObject;


  //在未打开回调页之前就判断参数是否合法
  private static void sendFailure(Context context, ShareListener shareListener, String message) {
    if (context instanceof Activity) {
      Activity activity = (Activity) context;
      if (activity.getClass().getSimpleName().equals("ResponseActivity") && !activity
          .isDestroyed()) {
        activity.finish();
      }
      shareListener.onShareResponse(
          new ShareResult(shareListener.getPlatform(), ShareResult.RESPONSE_SHARE_FAILURE,
              message));
    }
  }

  private static void commonShare(Context context,
      @SharePlatformType.Platform int platform,
      ShareObject shareObject,
      ShareListener listener) {
    mPlatform = platform;
    mShareObject = shareObject;

    listener.setPlatform(platform);
    mShareListenerWrap = buildWrapListener(listener);

    ResponseActivity.start(context, TYPE, platform);
  }

  //分享title
  public static void shareTitle(Context context,
      @SharePlatformType.Platform int platform,
      ShareObject shareObject,
      ShareListener listener) {
    if (shareObject == null || TextUtils.isEmpty(shareObject.getTitle())) {
      sendFailure(context, listener, context.getString(R.string.incorrect_parameter_passed_in));
      return;
    }

    mType = TYPE_TITLE;

    commonShare(context, platform, shareObject, listener);
  }


  //分享内容
  public static void shareDescription(Context context,
      @SharePlatformType.Platform int platform,
      ShareObject shareObject,
      ShareListener listener) {
    if (shareObject == null || TextUtils.isEmpty(shareObject.getDescription())) {
      sendFailure(context, listener, context.getString(R.string.incorrect_parameter_passed_in));
      return;
    }
    mType = TYPE_DESCRIPTION;
    commonShare(context, platform, shareObject, listener);
  }

  //分享标题内容
  public static void shareText(Context context,
      @SharePlatformType.Platform int platform,
      ShareObject shareObject,
      ShareListener listener) {
    if (shareObject == null || (TextUtils.isEmpty(shareObject.getTitle()) && TextUtils
        .isEmpty(shareObject.getDescription()))) {
      sendFailure(context, listener, context.getString(R.string.incorrect_parameter_passed_in));
      return;
    }
    mType = TYPE_TEXT;
    commonShare(context, platform, shareObject, listener);
  }

  //纯图分享
  public static void shareImage(Context context,
      @SharePlatformType.Platform final int platform,
      ShareObject shareObject,
      ShareListener listener) {
    if (shareObject == null || (TextUtils.isEmpty(shareObject.getImageUrlOrPath())
        && shareObject.getImageBitmap() == null)) {
      sendFailure(context, listener, context.getString(R.string.incorrect_parameter_passed_in));
      return;
    }
    mType = TYPE_IMAGE;
    commonShare(context, platform, shareObject, listener);
  }


  //图文分享
  public static void shareWeb(Context context,
      @SharePlatformType.Platform int platform,
      ShareObject shareObject,
      ShareListener listener) {
    mType = TYPE_WEB;
    commonShare(context, platform, shareObject, listener);
  }

  public static void shareMiniProgram(Context context,
      @SharePlatformType.Platform int platform,
      ShareObject shareObject,
      ShareListener listener) {
    mType = TYPE_MINIPROGRAM;
    commonShare(context, platform, shareObject, listener);
  }


  /**
   * 执行分享操作
   *
   * @param activity
   */
  public static void perform(Activity activity) {
    //获取对应平台
    sIShare = getPlatform(mPlatform, activity);

    if (mShareListenerWrap == null || sIShare == null) {
      activity.finish();
      return;
    }

    if (!sIShare.isInstalled(activity)) {
      mShareListenerWrap
          .onShareResponse(new ShareResult(mPlatform, ShareResult.RESPONSE_SHARE_NOT_INSTALL));
      activity.finish();
      return;
    }
    mShareListenerWrap.onStart();

    switch (mType) {
      case TYPE_TITLE:
        sIShare.shareTitle(activity, mPlatform, mShareObject);
        break;
      case TYPE_DESCRIPTION:
        sIShare.shareDescription(activity, mPlatform, mShareObject);
        break;
      case TYPE_TEXT:
        sIShare.shareText(activity, mPlatform, mShareObject);
        break;
      case TYPE_IMAGE:
        sIShare.shareImage(activity, mPlatform, mShareObject);
        break;
      case TYPE_WEB:
        sIShare.shareWeb(activity, mPlatform, mShareObject);
        break;
      case TYPE_MINIPROGRAM:
        sIShare.shareMiniProgram(activity, mPlatform, mShareObject);
        break;
      default:
        break;
    }
  }

  //通过反射获取类
  private static IShare getPlatform(@SharePlatformType.Platform int platform, Context context) {

    Class clazz;
    IShare share = null;
    try {
      switch (platform) {
        case SharePlatformType.WECHAT:
        case SharePlatformType.WECHAT_CIRCLE:
          clazz = Class.forName("com.llj.lib.socialization.wechat.share.ShareWechat");
          break;
        case SharePlatformType.QQ:
          clazz = Class.forName("com.llj.lib.socialization.qq.share.ShareQq");
          break;
        case SharePlatformType.QQ_ZONE:
          clazz = Class.forName("com.llj.lib.socialization.qq.share.ShareQzone");
          break;
        case SharePlatformType.SINA:
          clazz = Class.forName("com.llj.lib.socialization.sina.share.ShareSina");
          break;
        default:
          clazz = Class.forName("com.llj.lib.socialization.qq.share.ShareQq");
          break;
      }
      share = (IShare) clazz.newInstance();
      share.init(context, mShareListenerWrap);
    } catch (Exception e) {
    }
    return share;
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
      return ImageEncode.compress2Byte(task.getResult(), mSize, mLength);
    }
  }

  public static class ImageEncodeToFileCallable implements Callable<String> {

    private Context       mContext;
    private ShareObject   mShareObject;
    private ShareListener mShareListener;

    public ImageEncodeToFileCallable(Activity activity, ShareObject shareObject,
        ShareListener shareListener) {
      mContext = activity.getApplicationContext();
      mShareObject = shareObject;
      mShareListener = shareListener;
    }


    @Override
    public String call() throws Exception {
      if (mShareObject == null) {
        return null;
      }
      //解析用户设置的url或者bitmap
      String imageLocalPath = ImageEncode.encode(mContext, mShareObject);

      //尝试解析bitmap，看看是否可用
      BitmapFactory.Options options = null;
      try {
        options = ImageEncode.tryCompress2Byte(imageLocalPath);
      } catch (Exception e) {
        e.printStackTrace();
      }

      //使用备用的bitmap
      if (TextUtils.isEmpty(imageLocalPath) || (options == null) || (options.outHeight == 0) || (
          options.outWidth == 0)) {
        mShareObject.setImageUrlOrPath("");
        mShareObject.setImageBitmap(mShareListener.getExceptionImage());
        imageLocalPath = ImageEncode.encode(mContext, mShareObject);
      }
      //抛出
      if (TextUtils.isEmpty(imageLocalPath)) {
        mShareListener.onShareResponse(
            new ShareResult(mShareListener.getPlatform(), ShareResult.RESPONSE_SHARE_FAILURE,
                "图片加载失败"));
        return null;
      }
      //如果对路劲进行了处理，则返回处理后的
      String imageLocalPathWrap = mShareListener.imageLocalPathWrap(imageLocalPath);
      if (!TextUtils.isEmpty(imageLocalPathWrap)) {
        return imageLocalPathWrap;
      }

      return imageLocalPath;
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


  public static void onNewIntent(Activity activity, Intent data) {
    if (sIShare != null) {
      sIShare.onNewIntent(data);
    }
  }

  public static void handleResult(Activity activity, int requestCode, int resultCode, Intent data) {
    Log.e(TAG, "requestCode:" + requestCode + " resultCode:" + resultCode);

    if (resultCode == Activity.RESULT_CANCELED) {
      if (activity.getClass().getSimpleName().equals("ResponseActivity") && !activity
          .isDestroyed()) {
        activity.finish();
      }
      return;
    }

    if (sIShare != null) {
      sIShare.handleResult(requestCode, resultCode, data);
    }
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

  public static void recycle() {

    // bitmap recycle
    if (mShareObject != null
        && mShareObject.getImageBitmap() != null
        && !mShareObject.getImageBitmap().isRecycled()) {
      mShareObject.getImageBitmap().recycle();
    }
    mShareObject = null;

    if (sIShare != null) {
      sIShare.recycle();
    }
    sIShare = null;

    mShareListenerWrap = null;
  }

  /**
   * 判断是否是gif
   *
   * @param path
   * @return
   */
  public static boolean isGifPath(String path) {
    return path != null && (path.contains(".gif") || path.contains(".GIF"));
  }

  /**
   * 发送短信分享
   *
   * @param context ctx
   * @param phone 手机号
   * @param msg 内容
   */
  public static void sendSms(Context context, String phone, String msg) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    if (TextUtils.isEmpty(phone)) {
      phone = "";
    }
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
   * @param mailto email
   * @param subject 主题
   * @param msg 内容
   */
  public static void sendEmail(Context context, String mailto, String subject, String msg) {
    Intent intent = new Intent(Intent.ACTION_SENDTO);
    if (TextUtils.isEmpty(mailto)) {
      mailto = "";
    }
    intent.setData(Uri.parse("mailto:" + mailto));
    intent.putExtra(Intent.EXTRA_SUBJECT, subject);
    intent.putExtra(Intent.EXTRA_TEXT, msg);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }

  public static void copyText(Context context, String text) {
    ClipboardManager clipboardManager = (ClipboardManager) context.getApplicationContext()
        .getSystemService(Context.CLIPBOARD_SERVICE);
    // 将文本内容放到系统剪贴板里。
    ClipData clipData = ClipData.newPlainText("Label", text);
    clipboardManager.setPrimaryClip(clipData);
  }
}
