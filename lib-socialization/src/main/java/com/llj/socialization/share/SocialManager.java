package com.llj.socialization.share;

/**
 * Created by shaohui on 2016/12/5.
 */

public class SocialManager {

    private static boolean isInit = false;

    public static SocialConfig CONFIG;

    public static void init(SocialConfig config) {
        isInit = true;
        CONFIG = config;
    }
}
