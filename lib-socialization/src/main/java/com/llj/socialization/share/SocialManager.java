package com.llj.socialization.share;

import android.content.Context;

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
    }
}
