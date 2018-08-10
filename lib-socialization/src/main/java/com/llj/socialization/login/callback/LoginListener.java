package com.llj.socialization.login.callback;

import com.llj.socialization.login.model.BaseToken;
import com.llj.socialization.login.model.LoginResult;
import com.llj.socialization.share.SharePlatformType;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/2/16.
 */

public abstract class LoginListener {
    @SharePlatformType.Platform int platform;

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public void onStart() {
    }

    public void beforeFetchUserInfo(BaseToken token) {
    }

    public abstract void onLoginResponse(LoginResult result);


}
