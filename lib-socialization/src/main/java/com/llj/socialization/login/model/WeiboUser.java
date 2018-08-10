package com.llj.socialization.login.model;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by shaohui on 2016/12/1.
 */

public class WeiboUser extends BaseUser {
    private String headimgurl_hd;
    private String headImageUrlLarge;
    private String userName;
    private String iconURL;


    public static WeiboUser parse(JSONObject jsonObject) throws JSONException {
        WeiboUser user = new WeiboUser();
        user.setOpenId(String.valueOf(jsonObject.getInt("id")));
        user.setNickname(jsonObject.getString("screen_name"));
        user.setUserName(user.getNickname());

        // 性别
        String gender = jsonObject.getString("gender");
        if (TextUtils.equals(gender, "m")) {
            user.setSex(1);
        } else if (TextUtils.equals(gender, "f")) {
            user.setSex(2);
        } else {
            user.setSex(0);
        }

        user.setHeadImageUrl(jsonObject.getString("profile_image_url"));
        user.setIconURL(user.getHeadImageUrl());
        user.setHeadImageUrlLarge(jsonObject.getString("avatar_large"));
        user.setHeadimgurl_hd(jsonObject.getString("avatar_hd"));

        user.setCity(jsonObject.getString("city"));
        user.setProvince(jsonObject.getString("province"));

        return user;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadImageUrlLarge() {
        return headImageUrlLarge;
    }

    public void setHeadImageUrlLarge(String headImageUrlLarge) {
        this.headImageUrlLarge = headImageUrlLarge;
    }

    public String getHeadimgurl_hd() {
        return headimgurl_hd;
    }

    public void setHeadimgurl_hd(String headimgurl_hd) {
        this.headimgurl_hd = headimgurl_hd;
    }
}
