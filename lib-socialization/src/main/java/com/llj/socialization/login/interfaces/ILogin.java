package com.llj.socialization.login.interfaces;

import android.app.Activity;
import android.content.Context;

import com.llj.socialization.IControl;
import com.llj.socialization.login.callback.LoginListener;
import com.llj.socialization.login.model.BaseToken;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/2/16.
 */

public interface ILogin extends IControl {

    void init( Context context,  LoginListener listener,  boolean fetchUserInfo, boolean fetchWxToken);

    void doLogin(Activity activity);

    void fetchUserInfo(Context context, BaseToken token);

}
