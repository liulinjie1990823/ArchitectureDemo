package com.llj.socialization.login.model;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by shaohui on 2016/12/1.
 */

public class QQUser extends BaseUser {

    private String headImageUrlLarge;//头像大图
    private String mQzoneHeadImage;//qq空间小头像
    private String mQzoneHeadImageLarge;//qq空间大头像
    private String figureurl_qq_2;//qq空间大头像
    private String userName;
    private String iconURL;

    public static QQUser parse(String openId, JSONObject jsonObject) throws JSONException {
        QQUser user = new QQUser();
        user.setOpenId(openId);

        user.setProvince(jsonObject.getString("province"));
        user.setCity(jsonObject.getString("city"));

        user.setNickname(jsonObject.getString("nickname"));
        user.setUserName(jsonObject.getString("nickname"));
        user.setSex(TextUtils.equals("男", jsonObject.getString("gender")) ? 1 : 2);

        user.setHeadImageUrl(jsonObject.getString("figureurl_qq_1"));
        user.setHeadImageUrlLarge(jsonObject.getString("figureurl_qq_2"));
        user.setFigureurl_qq_2(user.getHeadImageUrlLarge());
        user.setIconURL(user.getHeadImageUrlLarge());

        user.setQzoneHeadImage(jsonObject.getString("figureurl_1"));
        user.setQzoneHeadImageLarge(jsonObject.getString("figureurl_2"));

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

    public String getFigureurl_qq_2() {
        return figureurl_qq_2;
    }

    public void setFigureurl_qq_2(String figureurl_qq_2) {
        this.figureurl_qq_2 = figureurl_qq_2;
    }

    public void setHeadImageUrlLarge(String headImageUrlLarge) {
        this.headImageUrlLarge = headImageUrlLarge;
    }

    public String getQzoneHeadImage() {
        return mQzoneHeadImage;
    }

    public void setQzoneHeadImage(String qzoneHeadImage) {
        mQzoneHeadImage = qzoneHeadImage;
    }

    public String getQzoneHeadImageLarge() {
        return mQzoneHeadImageLarge;
    }

    public void setQzoneHeadImageLarge(String qzoneHeadImageLarge) {
        mQzoneHeadImageLarge = qzoneHeadImageLarge;
    }
}
