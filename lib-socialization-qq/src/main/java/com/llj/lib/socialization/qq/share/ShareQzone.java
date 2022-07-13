package com.llj.lib.socialization.qq.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import bolts.Continuation;
import bolts.Task;
import com.llj.socialization.R;
import com.llj.socialization.init.SocialManager;
import com.llj.socialization.log.Logger;
import com.llj.socialization.share.ShareObject;
import com.llj.socialization.share.ShareUtil.ImageEncodeToFileCallable;
import com.llj.socialization.share.callback.ShareListener;
import com.llj.socialization.share.interfaces.IShareQzone;
import com.llj.socialization.share.model.ShareResult;
import com.llj.socialization.share.process.ImageEncode;
import com.llj.socialization.utils.InstallUtil;
import com.llj.socialization.utils.Utils;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzonePublish;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * PROJECT:babyphoto_app DESCRIBE: Created by llj on 2017/1/18.
 */

public class ShareQzone implements IShareQzone {

  /**
   * public static final int SHARE_TO_QZONE_TYPE_NO_TYPE = 0; public static final int
   * SHARE_TO_QZONE_TYPE_IMAGE_TEXT = 1; public static final int SHARE_TO_QZONE_TYPE_IMAGE = 5;
   * public static final int SHARE_TO_QZONE_TYPE_APP = 6;
   */
  public static final String TAG = "ShareQzone";


  private Tencent mTencent;
  private String  mAppName;

  private ShareListener mShareListener;
  private MyIUiListener mIUiListener;

  @Override
  public void init(Context context, ShareListener listener) {
    mTencent = Tencent
        .createInstance(SocialManager.getConfig(context.getApplicationContext()).getQqId(),
            context.getApplicationContext(), context.getApplicationContext().getPackageName() +
                ".shareFileProvider");
    Tencent.setIsPermissionGranted(true);
    mShareListener = listener;

    mIUiListener = new MyIUiListener(context, listener);
  }

  private static class MyIUiListener implements IUiListener {

    private WeakReference<Context> mWeakReference;
    private ShareListener          mShareListener;

    public MyIUiListener(Context context, ShareListener shareListener) {
      mWeakReference = new WeakReference<>(context);
      mShareListener = shareListener;
    }


    @Override
    public void onComplete(Object o) {
      mShareListener.onShareResponse(
          new ShareResult(mShareListener.getPlatform(), ShareResult.RESPONSE_SHARE_SUCCESS));
      Utils.finishActivity(mWeakReference.get());
    }

    @Override
    public void onError(UiError uiError) {
      mShareListener.onShareResponse(
          new ShareResult(mShareListener.getPlatform(), ShareResult.RESPONSE_SHARE_FAILURE,
              uiError.errorMessage));
      Utils.finishActivity(mWeakReference.get());
    }

    @Override
    public void onCancel() {
      mShareListener.onShareResponse(
          new ShareResult(mShareListener.getPlatform(), ShareResult.RESPONSE_SHARE_HAS_CANCEL));
      Utils.finishActivity(mWeakReference.get());
    }

    @Override
    public void onWarning(int i) {

    }
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
    params
        .putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD);

    if (!TextUtils.isEmpty(mAppName)) {
      params.putString(QzoneShare.SHARE_TO_QQ_APP_NAME, mAppName);
    }
    params.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareObject.getTitle());
    if (!TextUtils.isEmpty(shareObject.getTargetUrl())) {
      params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareObject.getTargetUrl());
    }
    mTencent.publishToQzone(activity, params, mIUiListener);
  }

  @Override
  public void shareDescription(Activity activity, int platform, @NonNull ShareObject shareObject) {

    final Bundle params = new Bundle();
    params
        .putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD);

    if (!TextUtils.isEmpty(mAppName)) {
      params.putString(QzoneShare.SHARE_TO_QQ_APP_NAME, mAppName);
    }
    params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareObject.getDescription());
    if (!TextUtils.isEmpty(shareObject.getTargetUrl())) {
      params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareObject.getTargetUrl());
    }
    mTencent.publishToQzone(activity, params, mIUiListener);
  }

  /**
   * 分享的title和targetUrl无用
   *
   * @param activity
   * @param platform
   */

  @Override

  public void shareText(Activity activity, int platform, @NonNull ShareObject shareObject) {

    final Bundle params = new Bundle();
    params
        .putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD);

    params.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareObject.getTitle());
    params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareObject.getDescription());
    if (!TextUtils.isEmpty(shareObject.getTargetUrl())) {
      params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareObject.getTargetUrl());
    }
    mTencent.publishToQzone(activity, params, mIUiListener);
  }

  /**
   * public static final int PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD = 3; public static final int
   * PUBLISH_TO_QZONE_TYPE_PUBLISHVIDEO = 4; qq空间说说分享，支持纯图
   * <p>
   *
   * @param activity
   * @param platform
   * @param shareObject
   */
  @Override
  public void shareTalkAbout(final Activity activity, int platform, final ShareObject shareObject) {
    final Bundle params = new Bundle();
    params
        .putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD);

    if (!TextUtils.isEmpty(shareObject.getTargetUrl())) {
      params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareObject.getTargetUrl());
    }

    Task.callInBackground(() -> ImageEncode.encode(activity, shareObject))
        .continueWith((Continuation<String, Void>) task -> {
          if (task.getError() != null) {
            Log.e(TAG, Log.getStackTraceString(task.getError()));
            return null;
          }
          ArrayList<String> imageUrls = new ArrayList<>();
          imageUrls.add(task.getResult());

          params.putStringArrayList(QzonePublish.PUBLISH_TO_QZONE_IMAGE_URL, imageUrls);
          mTencent.publishToQzone(activity, params, mIUiListener);

          return null;
        });
  }


  /**
   * qq空间目前不支持纯图片
   *
   * @param activity
   * @param platform
   */
  @Override
  public void shareImage(Activity activity, int platform, @NonNull ShareObject shareObject) {
    shareImageCustomer(activity, platform, shareObject);
  }

  //本身sdk并不支持分享纯图到qq空间
  public void shareImageOffical(Activity activity, int platform, @NonNull ShareObject shareObject) {
    final Bundle params = new Bundle();
    params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE);

    if (!TextUtils.isEmpty(mAppName)) {
      params.putString(QQShare.SHARE_TO_QQ_APP_NAME, mAppName);
    }
    if (!TextUtils.isEmpty(shareObject.getTargetUrl())) {
      params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareObject.getTargetUrl());
    }

    Task.callInBackground(new ImageEncodeToFileCallable(activity, shareObject, mShareListener))
        .continueWith(new Continuation<String, Void>() {
          @Override
          public Void then(Task<String> task) throws Exception {
            if (task.getError() != null) {
              Log.e(TAG, Log.getStackTraceString(task.getError()));
              sendFailure(activity, mShareListener,
                  activity.getString(R.string.share_image_failure));
              return null;
            }
            if (TextUtils.isEmpty(task.getResult())) {
              sendFailure(activity, mShareListener,
                  activity.getString(R.string.load_image_failure));
              return null;
            }
            if (!new File(task.getResult()).exists()) {
              Log.e(TAG, activity.getString(R.string.local_file_does_not_exist));
              sendFailure(activity, mShareListener,
                  activity.getString(R.string.local_file_does_not_exist));
              return null;
            }
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, task.getResult());
            shareToQzone(activity, params);
            return null;
          }
        }, Task.UI_THREAD_EXECUTOR);
  }

  //特殊的方法分享纯图到qq空间
  private void shareImageCustomer(Activity activity, int platform,
      @NonNull ShareObject shareObject) {
    Bundle params = new Bundle();

    params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
        QQShare.SHARE_TO_QQ_TYPE_IMAGE);  //注意，要向qq空间分享纯图片，只能传这三个参数，不能传其他的
    params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);

    Task.callInBackground(new ImageEncodeToFileCallable(activity, shareObject, mShareListener))
        .continueWith(new Continuation<String, Void>() {
          @Override
          public Void then(Task<String> task) throws Exception {
            if (task.getError() != null) {
              Log.e(TAG, Log.getStackTraceString(task.getError()));
              sendFailure(activity, mShareListener,
                  activity.getString(R.string.share_image_failure));
              return null;
            }
            if (TextUtils.isEmpty(task.getResult())) {
              sendFailure(activity, mShareListener,
                  activity.getString(R.string.load_image_failure));
              return null;
            }
            if (!new File(task.getResult()).exists()) {
              Log.e(TAG, activity.getString(R.string.local_file_does_not_exist));
              sendFailure(activity, mShareListener,
                  activity.getString(R.string.local_file_does_not_exist));
              return null;
            }
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,
                task.getResult()); //localImgUrl必须是本地手机图片地址

            mTencent.shareToQQ(activity, params, mIUiListener);
            return null;
          }
        }, Task.UI_THREAD_EXECUTOR);
  }


  /**
   * qq空间 SHARE_TO_QZONE_TYPE_IMAGE_TEXT:必须要有title,图片,targetUrl
   * 腾讯bug：即使是本地照片，传的时候字段设置的也必需是SHARE_TO_QQ_IMAGE_URL，
   *
   * @param activity
   * @param platform
   */

  @Override
  public void shareWeb(Activity activity, int platform, @NonNull ShareObject shareObject) {

    final ArrayList<String> imageUrls = new ArrayList<>();

    final Bundle params = new Bundle();
    params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
    if (!TextUtils.isEmpty(mAppName)) {
      params.putString(QzoneShare.SHARE_TO_QQ_APP_NAME, mAppName);
    }
    params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareObject.getDescription());
    params.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareObject.getTitle());
    if (!TextUtils.isEmpty(shareObject.getTargetUrl())) {
      params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareObject.getTargetUrl());
    }

    Task.callInBackground(new ImageEncodeToFileCallable(activity, shareObject, mShareListener))
        .continueWith((Continuation<String, Void>) task -> {
          if (task.getError() != null) {
            Logger.e(task.getError());
            sendFailure(activity, mShareListener, activity.getString(R.string.load_image_failure));
            return null;
          }
          if (TextUtils.isEmpty(task.getResult())) {
            sendFailure(activity, mShareListener, activity.getString(R.string.load_image_failure));
            return null;
          }
          imageUrls.add(task.getResult());
          if (new File(task.getResult()).exists()) {
            //本地文件
            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
          } else {
            //网络文件
            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
          }
          shareToQzone(activity, params);

          return null;
        }, Task.UI_THREAD_EXECUTOR);
  }

  private void shareToQzone(final Activity activity, Bundle params) {
    mTencent.shareToQzone(activity, params, mIUiListener);
  }

  @Override
  public void onNewIntent(Intent data) {

  }

  @Override
  public void handleResult(Activity activity, int requestCode, int resultCode, Intent data) {
    Tencent.handleResultData(data, mIUiListener);
  }

  @Override
  public boolean isInstalled(Context context) {
    return InstallUtil.isQQInstalled(context);
  }


  @Override
  public void recycle() {
    if (mTencent != null) {
      //mTencent.releaseResource();
      mTencent = null;
    }
  }

}
