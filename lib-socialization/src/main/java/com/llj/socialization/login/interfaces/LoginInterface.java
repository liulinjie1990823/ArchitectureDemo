package com.llj.socialization.login.interfaces;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.llj.socialization.login.model.BaseToken;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/2/16.
 */

public interface LoginInterface {

    void doLogin(Activity activity, boolean fetchUserInfo);

    void fetchUserInfo(Context context, BaseToken token);

    void handleResult(int requestCode, int resultCode, Intent data);

    boolean isInstalled(Context context);

    void recycle();
}
