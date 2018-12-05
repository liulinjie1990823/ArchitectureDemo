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

    void init(final Context context, final LoginListener listener, final boolean fetchUserInfo);

    void doLogin(Activity activity, boolean fetchUserInfo);

    void fetchUserInfo(Context context, BaseToken token);

}
