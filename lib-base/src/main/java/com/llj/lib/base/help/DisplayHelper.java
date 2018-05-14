package com.llj.lib.base.help;

import android.content.Context;

import com.llj.lib.utils.ADisplayUtils;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/4/25
 */
public class DisplayHelper {
    public static int CONTENT_HEIGHT;

    public static int   SCREEN_WIDTH_PIXELS;
    public static int   SCREEN_HEIGHT_PIXELS;

    public static int   STATUS_BAR_HEIGHT;
    public static int   NAVIGATION_BAR_HEIGHT;

    public static float SCREEN_DENSITY;
    public static float SCREEN_DENSITY_DPI;


    private static boolean sInitialed;

    public static void init(Context context) {
        if (sInitialed || context == null) {
            return;
        }
        sInitialed = true;

        SCREEN_WIDTH_PIXELS = context.getResources().getDisplayMetrics().widthPixels;
        SCREEN_HEIGHT_PIXELS = context.getResources().getDisplayMetrics().heightPixels;

        STATUS_BAR_HEIGHT= ADisplayUtils.getStatusBarHeight(context.getApplicationContext());
        STATUS_BAR_HEIGHT= ADisplayUtils.getNavigationBarOffset(context.getApplicationContext());

        SCREEN_DENSITY = context.getResources().getDisplayMetrics().density;
        SCREEN_DENSITY_DPI = context.getResources().getDisplayMetrics().densityDpi;
    }
}
