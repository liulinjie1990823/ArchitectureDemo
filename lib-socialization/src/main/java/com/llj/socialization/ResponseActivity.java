package com.llj.socialization;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.llj.socialization.login.LoginUtil;
import com.llj.socialization.pay.PayUtil;
import com.llj.socialization.share.ShareUtil;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/1/18.
 */

public class ResponseActivity extends Activity {
    private int     mType;
    private int     mPlatform;
    private boolean isNew;
    private static final String TYPE     = "share_activity_type";
    private static final String PLATFORM = "share_activity_platform";

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
        isNew = true;

        // init data
        mType = getIntent().getIntExtra(TYPE, 0);
        mPlatform = getIntent().getIntExtra(PLATFORM, 0);
        if (isShare()) {
            // 分享
            ShareUtil.perform(this);
        } else if (isLogin()) {
            // 登录
            LoginUtil.perform(this);
        } else if (isPay()) {
            PayUtil.perform(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNew) {
            isNew = false;
        } else {
            if (Platform.isWechat(mPlatform)) {
                LoginUtil.handleResult(-1, -1, getIntent());
                ShareUtil.handleResult(getIntent());
                PayUtil.handleResult(-1, -1, getIntent());
            }
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        // 处理回调
        if (isLogin()) {
            LoginUtil.handleResult(0, 0, intent);
        } else if (isShare()) {
            ShareUtil.handleResult(intent);
        } else if (isPay()) {
            PayUtil.handleResult(0, 0, intent);
        }
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 处理回调
        if (isLogin()) {
            LoginUtil.handleResult(requestCode, resultCode, data);
        } else if (isShare()) {
            ShareUtil.handleResult(data);
        } else if (isPay()) {
            PayUtil.handleResult(requestCode, resultCode, data);
        }
        finish();
    }
}
