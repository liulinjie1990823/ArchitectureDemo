package com.llj.socialization.login.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by shaohui on 2016/12/3.
 */

public class WxToken extends BaseToken {
    private String unionid;
    private String scope;//用户授权的作用域，使用逗号（,）分隔

    private String refresh_token;

    public static WxToken parse(JSONObject jsonObject) throws JSONException {
        WxToken wxToken = new WxToken();
        wxToken.setOpenid(jsonObject.getString("openid"));
        wxToken.setUnionid(jsonObject.getString("unionid"));
        wxToken.setScope(jsonObject.getString("scope"));
        wxToken.setAccessToken(jsonObject.getString("access_token"));
        wxToken.setRefreshToken(jsonObject.getString("refresh_token"));
        wxToken.setExpires_in(jsonObject.getLong("expires_in"));
        return wxToken;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRefreshToken() {
        return refresh_token;
    }

    public void setRefreshToken(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
