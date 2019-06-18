package com.llj.socialization.login.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by shaohui on 2016/12/3.
 */

public class WxToken extends BaseToken {
    private String unionid;//同一个微信开放平台帐号下绑定的移动应用、网站应用和公众帐号，用户的UinonID是唯一的。
    private String scope;//用户授权的作用域，使用逗号（,）分隔
    private String refresh_token;

    public static WxToken parse(JSONObject jsonObject) throws JSONException {
        WxToken wxToken = new WxToken();
        wxToken.setOpenid(jsonObject.getString("openid"));
        wxToken.setAccessToken(jsonObject.getString("access_token"));
        wxToken.setExpiresIn(jsonObject.getLong("expires_in"));

        wxToken.setUnionId(jsonObject.getString("unionid"));
        wxToken.setScope(jsonObject.getString("scope"));
        wxToken.setRefreshToken(jsonObject.getString("refresh_token"));
        return wxToken;
    }

    public String getUnionId() {
        return unionid;
    }

    public void setUnionId(String unionid) {
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
