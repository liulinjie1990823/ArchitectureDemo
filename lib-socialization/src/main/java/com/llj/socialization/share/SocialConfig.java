package com.llj.socialization.share;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by llj on 2016/12/7.
 */

public class SocialConfig {
    private Context context;
    private String  wxId;
    private String  wxSecret;
    private String  qqId;
    private String  signId;
    private String  signRedirectUrl;
    private String  signScope;
    private boolean debug;

    SocialConfig(Builder builder) {
        this.context = builder.context;
        this.wxId = builder.wxId;
        this.wxSecret = builder.wxSecret;
        this.qqId = builder.qqId;
        this.signId = builder.signId;
        this.signRedirectUrl = builder.signRedirectUrl;
        this.signScope = builder.signScope;
        this.debug = builder.debug;
        if (builder.init) {
            saveToPreferences();
        } else {
            initByPreferences();
        }

    }

    private void saveToPreferences() {
        SharedPreferences socialConfig = context.getSharedPreferences("SocialConfig", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = socialConfig.edit();
        edit.putString("wxId", wxId);
        edit.putString("wxSecret", wxSecret);
        edit.putString("qqId", qqId);
        edit.putString("signId", signId);
        edit.putString("signRedirectUrl", signRedirectUrl);
        edit.putString("signScope", signScope);
        edit.putBoolean("debug", debug);
        edit.apply();
    }

    private void initByPreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SocialConfig", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        this.wxId = sharedPreferences.getString("wxId", "");
        this.wxSecret = sharedPreferences.getString("wxSecret", "");
        this.qqId = sharedPreferences.getString("qqId", "");
        this.signId = sharedPreferences.getString("signId", "");
        this.signRedirectUrl = sharedPreferences.getString("signRedirectUrl", "");
        this.signScope = sharedPreferences.getString("signScope", "");
        this.debug = sharedPreferences.getBoolean("debug", false);
    }

    public static class Builder {
        private Context context;
        private boolean init;
        private String  wxId;
        private String  wxSecret;
        private String  qqId;
        private String  signId;
        private String  signRedirectUrl = "https://api.weibo.com/oauth2/default.html";
        private String  signScope       = "all";
        private boolean debug;

        public Builder(Context context, boolean init) {
            this.context = context.getApplicationContext();
            this.init = init;
        }

        public Builder wx(String wxId, String wxSecret) {
            this.wxId = wxId;
            this.wxSecret = wxSecret;
            return this;
        }


        public Builder qqId(String id) {
            qqId = id;
            return this;
        }

        public Builder sign(String id, String url, String scope) {
            this.signId = id;
            this.signRedirectUrl = url;
            this.signScope = scope;
            return this;
        }

        public Builder debug(boolean isDebug) {
            debug = isDebug;
            return this;
        }

        public SocialConfig build() {
            return new SocialConfig(this);
        }

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
