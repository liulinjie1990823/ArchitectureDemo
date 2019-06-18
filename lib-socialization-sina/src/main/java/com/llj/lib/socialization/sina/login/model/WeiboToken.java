package com.llj.lib.socialization.sina.login.model;


import com.llj.socialization.login.model.BaseToken;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/**
 * Created by shaohui on 2016/12/3.
 */

public class WeiboToken extends BaseToken {

    private String refreshToken;
    private String phoneNum;
    private String uid;

    public static WeiboToken parse(Oauth2AccessToken token) {
        WeiboToken target = new WeiboToken();
        target.setUid(token.getUid());
        target.setAccessToken(token.getToken());
        target.setRefreshToken(token.getRefreshToken());
        target.setPhoneNum(token.getPhoneNum());
        return target;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
