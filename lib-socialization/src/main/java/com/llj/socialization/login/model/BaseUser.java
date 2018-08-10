package com.llj.socialization.login.model;

/**
 * Created by shaohui on 2016/12/1.
 */

public class BaseUser {
    /**
     * sex
     * 0. 未知
     * 1. 男
     * 2. 女
     */
    private String openId;
    private String nickname;//用户名
    private int    sex;//性别

    private String headImageUrl;//头像小图

    private String province;//省份
    private String city;//城市


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
