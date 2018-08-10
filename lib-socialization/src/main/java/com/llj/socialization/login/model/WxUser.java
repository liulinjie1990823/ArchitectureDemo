package com.llj.socialization.login.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by shaohui on 2016/12/1.
 */

public class WxUser extends BaseUser {
    private String country;
    private String headimgurl;//注意我们服务器那边是解析这个字段的

    private String unionid;//针对用户唯一,微信用

    public static WxUser parse(JSONObject jsonObject) throws JSONException {
        WxUser user = new WxUser();
        user.setOpenId(jsonObject.getString("openid"));
        user.setNickname(jsonObject.getString("nickname"));
        user.setSex(jsonObject.getInt("sex"));
        user.setHeadImageUrl(jsonObject.getString("headimgurl"));
        user.setHeadimgurl(user.getHeadImageUrl());
        user.setProvince(jsonObject.getString("province"));
        user.setCity(jsonObject.getString("city"));
        user.setCountry(jsonObject.getString("country"));
        user.setUnionid(jsonObject.getString("unionid"));

        return user;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
