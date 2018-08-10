package com.llj.socialization.share;

/**
 * Created by shaohui on 2016/12/7.
 */

public class SocialConfig {

    private String wxId;

    private String wxSecret;

    private String qqId;

    private String signId;

    private String signRedirectUrl = "https://api.weibo.com/oauth2/default.html";

    private String signScope = "email";

    private boolean debug;

    public static SocialConfig instance() {
        return new SocialConfig();
    }

    public SocialConfig wx(String wxId, String wxSecret) {
        this.wxId = wxId;
        this.wxSecret = wxSecret;
        return this;
    }


    public SocialConfig qqId(String id) {
        qqId = id;
        return this;
    }

    public SocialConfig sign(String id, String url, String scope) {
        this.signId = id;
        this.signRedirectUrl = url;
        this.signScope = scope;
        return this;
    }

    public SocialConfig debug(boolean isDebug) {
        debug = isDebug;
        return this;
    }

    public String getWxId() {
        return wxId;
    }

    public String getWxSecret() {
        return wxSecret;
    }

    public String getQqId() {
        return qqId;
    }

    public String getSignId() {
        return signId;
    }

    public String getSignRedirectUrl() {
        return signRedirectUrl;
    }

    public String getSignScope() {
        return signScope;
    }

    public boolean isDebug() {
        return debug;
    }
}
