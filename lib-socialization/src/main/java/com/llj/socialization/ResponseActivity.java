package com.llj.socialization;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import android.util.Log;

import com.llj.socialization.login.LoginUtil;
import com.llj.socialization.pay.PayUtil;
import com.llj.socialization.share.ShareUtil;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/1/18.
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
    //如果分享出去，选择了留在微信，返回时不会进onNewIntent和onActivityResult，仅仅进了onResume，导致ResponseActivity页面无法关闭；用户选择返回则会走相关逻辑，页面也会关闭
    private boolean mHasProcess;

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("ResponseActivity", "onResume:" + hashCode());
        if (mIsFirst) {
            mIsFirst = false;
        } else {
            mHandler.postDelayed(mRunnable, 500);
        }
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (!mHasProcess) {
                Log.e("ResponseActivity", "onResume:" + "finish");
                finish();
            }
        }
    };
    private Handler  mHandler  = new Handler();


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("ResponseActivity", "onNewIntent:" + hashCode());
        setIntent(intent);

        mHasProcess = true;
        // 处理回调
        if (Platform.isWechat(mPlatform) || Platform.isWechatCircle(mPlatform)) {
            if (isLogin()) {
                LoginUtil.handleResult(0, 0, intent);
            } else if (isShare()) {
                ShareUtil.handleResult(0, 0, intent);
            } else if (isPay()) {
                PayUtil.handleResult(0, 0, intent);
            }
        } else if (Platform.isSina(mPlatform) && isShare()) {
            ShareUtil.handleResult(0, 0, intent);
        }
        finish();
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
        if (Platform.isQQ(mPlatform) || Platform.isQQZone(mPlatform)) {
            if (isLogin()) {
                LoginUtil.handleResult(requestCode, resultCode, data);
            } else if (isShare()) {
                ShareUtil.handleResult(requestCode, resultCode, data);
            } else if (isPay()) {
                PayUtil.handleResult(requestCode, resultCode, data);
            }
        } else if (Platform.isSina(mPlatform) && isLogin()) {
            LoginUtil.handleResult(requestCode, resultCode, data);
        }
        finish();
    }
}
