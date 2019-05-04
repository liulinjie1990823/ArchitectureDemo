package com.llj.socialization.init;

import android.content.Context;

import com.llj.socialization.login.hook.HookHelper;

/**
 * Created by shaohui on 2016/12/5.
 */

public class SocialManager {

    private static SocialConfig CONFIG;


    public static SocialConfig getConfig(Context context) {
        if (CONFIG == null) {
            CONFIG = new SocialConfig.Builder(context, false).build();
        }
        return CONFIG;
    }

    public static void init(SocialConfig config) {
        CONFIG = config;
        try {
            HookHelper.attachContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
