package com.llj.socialization;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.annotation.Nullable;
import com.llj.socialization.login.LoginUtil;
import com.llj.socialization.pay.PayUtil;
import com.llj.socialization.share.ShareUtil;

/**
 * PROJECT:babyphoto_app DESCRIBE: Created by llj on 2017/1/18.
 */

public class ResponseActivity extends Activity {

  private int mType;//登录，分享，支付
  private int mPlatform; //三方平台

  private static final String TYPE     = "share_activity_type";
  private static final String PLATFORM = "share_activity_platform";

  public static void start(Context context, int type, int mPlatform) {
    Intent intent = new Intent(context, ResponseActivity.class);
    if (context instanceof Application) {
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
    intent.putExtra(TYPE, type);
    intent.putExtra(PLATFORM, mPlatform);
    context.startActivity(intent);
  }

  public boolean isLogin() {
    return mType == LoginUtil.TYPE;
  }

  public boolean isShare() {
    return mType == ShareUtil.TYPE;
  }

  public boolean isPay() {
    return mType == PayUtil.TYPE;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.e("ResponseActivity", "onCreate:" + hashCode());
    // init data
    mType = getIntent().getIntExtra(TYPE, 0);
    mPlatform = getIntent().getIntExtra(PLATFORM, 0);

    if (ShareUtil.sIsInProcess || LoginUtil.sIsInProcess || PayUtil.sIsInProcess) {
      finish();
      return;
    } else {
      if (isShare()) {
        ShareUtil.sIsInProcess = true;
      } else if (isLogin()) {
        LoginUtil.sIsInProcess = true;
      } else if (isPay()) {
        PayUtil.sIsInProcess = true;
      }
    }

    mIsFirst = true;

    if (isShare()) {
      ShareUtil.perform(this);
    } else if (isLogin()) {
      LoginUtil.perform(this);
    } else if (isPay()) {
      PayUtil.perform(this);
    }
  }

  //是否是第一次进入还是从其他页面返回
  private boolean mIsFirst;
  //如果分享出去，选择了留在微信，返回时不会进onNewIntent和onActivityResult，仅仅进了onResume，导致ResponseActivity
  // 页面无法关闭；用户选择返回则会走相关逻辑，页面也会关闭
  private boolean mHasProcess;


  @Override
  protected void onRestart() {
    super.onRestart();
    Log.e("ResponseActivity", "onRestart:" + hashCode());

    //如果直接通过最近列表返回app，则需要通过该方法关闭页面
    //因为微信的页面是通过NEW_TASK启动的，会启动一个新的任务栈，所以可以通过最近列表返回
    //qq和sina是通过标准模式启动的页面，页面是嵌入到我们app的任务栈中，taskId相同，taskAffinity不同
    if (!mIsFirst) {
      mHandler.postDelayed(mRunnable, 500);
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    Log.e("ResponseActivity", "onResume:" + hashCode());
    if (mIsFirst) {
      mIsFirst = false;
    }
  }

  private Runnable mRunnable = new Runnable() {
    @Override
    public void run() {
      if (!mHasProcess) {
        Log.e("ResponseActivity", "onRestart:" + "finish");
        finish();
      }
    }
  };
  private Handler  mHandler  = new Handler();


  @Override
  protected void onPause() {
    super.onPause();
    Log.e("ResponseActivity", "onPause:" + hashCode());
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    Log.e("ResponseActivity", "onNewIntent:" + hashCode());
    setIntent(intent);

    mHasProcess = true;
    // 处理回调
    //微信只走onNewIntent
    if (Platform.isWechat(mPlatform) || Platform.isWechatCircle(mPlatform)) {
      if (isLogin()) {
        LoginUtil.onNewIntent(this, intent);
      } else if (isShare()) {
        ShareUtil.onNewIntent(this, intent);
      } else if (isPay()) {
        PayUtil.onNewIntent(this, intent);
      }
    }
    //finish();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.e("ResponseActivity", "onDestroy:" + hashCode());

    mHandler.removeCallbacks(mRunnable);
    mRunnable = null;
    mHandler = null;

    ShareUtil.sIsInProcess = false;
    LoginUtil.sIsInProcess = false;
    PayUtil.sIsInProcess = false;
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    Log.e("ResponseActivity", "onActivityResult:" + hashCode());

    mHasProcess = true;
    // 处理回调
    //qq,sina只走onActivityResult
    if (Platform.isQQ(mPlatform) || Platform.isQQZone(mPlatform) || Platform.isSina(mPlatform)) {
      if (isLogin()) {
        LoginUtil.handleResult(this, requestCode, resultCode, data);
      } else if (isShare()) {
        ShareUtil.handleResult(this, requestCode, resultCode, data);
      } else if (isPay()) {
        PayUtil.handleResult(this, requestCode, resultCode, data);
      }
    }
    //finish();
  }
}
