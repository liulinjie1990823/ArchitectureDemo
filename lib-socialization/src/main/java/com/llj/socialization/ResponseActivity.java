package com.llj.socialization;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
    private              int     mType;//登录，分享，支付
    private              int     mPlatform; //平台
    private              boolean isNew;
    private static final String  TYPE     = "share_activity_type";
    private static final String  PLATFORM = "share_activity_platform";

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, ResponseActivity.class);
        if (context instanceof Application) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }

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
        if (ShareUtil.sIsInProcess) {
            finish();
            return;
        } else {
            ShareUtil.sIsInProcess = true;
        }

        isNew = true;

        // init data
        mType = getIntent().getIntExtra(TYPE, 0);
        mPlatform = getIntent().getIntExtra(PLATFORM, 0);
        if (isShare()) {
            ShareUtil.perform(this);
        } else if (isLogin()) {
            LoginUtil.perform(this);
        } else if (isPay()) {
            PayUtil.perform(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("ResponseActivity", "onResume:" + hashCode());
        if (isNew) {
            isNew = false;
        } else {
            if (Platform.isWechat(mPlatform)) {
                if (isLogin()) {
                    LoginUtil.handleResult(-1, -1, getIntent());
                } else if (isShare()) {
                    ShareUtil.handleResult(-1, -1, getIntent());
                } else if (isPay()) {
                    PayUtil.handleResult(-1, -1, getIntent());
                }
            }
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("ResponseActivity", "onNewIntent:" + hashCode());
        setIntent(intent);
        // 处理回调
        if (isLogin()) {
            LoginUtil.handleResult(0, 0, intent);
        } else if (isShare()) {
            ShareUtil.handleResult(0, 0, intent);
        } else if (isPay()) {
            PayUtil.handleResult(0, 0, intent);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("ResponseActivity", "onDestroy:" + hashCode());

        ShareUtil.sIsInProcess = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("ResponseActivity", "onActivityResult:" + hashCode());
        // 处理回调
        if (isLogin()) {
            LoginUtil.handleResult(requestCode, resultCode, data);
        } else if (isShare()) {
            ShareUtil.handleResult(requestCode, resultCode, data);
        } else if (isPay()) {
            PayUtil.handleResult(requestCode, resultCode, data);
        }
        finish();
    }
}
