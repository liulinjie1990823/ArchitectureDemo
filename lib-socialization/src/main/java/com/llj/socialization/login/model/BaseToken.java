package com.llj.socialization.login.model;

/**
 * Created by shaohui on 2016/12/3.
 */

public class BaseToken {
    private long   expires_in;
    private String access_token;
    private String openid;//每个用户在子体（公众号，小程序，移动应用等等）返回的openid都不同；而这些子体只要通过微信开放平台绑定了，就会返回唯一的unionId，用户唯一

    public long getExpiresIn() {
        return expires_in;
    }

    public void setExpiresIn(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
