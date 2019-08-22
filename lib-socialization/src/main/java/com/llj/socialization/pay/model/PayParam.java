package com.llj.socialization.pay.model;

/**
 * project:android
 * describe:
 * Created by llj on 2017/8/14.
 */

public class PayParam {
    private String appId;//微信开放平台审核通过的应用APPID
    private String partnerId;//微信支付分配的商户号
    private String prepayId;//微信返回的支付交易会话ID
    private String packageValue;//暂填写固定值Sign=WXPay
    private String nonceStr;//随机字符串，不长于32位。推荐随机数生成算法
    private long   timeStamp;//时间戳
    private String sign;

    private String signType;
    private String mch_key;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getMch_key() {
        return mch_key;
    }

    public void setMch_key(String mch_key) {
        this.mch_key = mch_key;
    }
}
