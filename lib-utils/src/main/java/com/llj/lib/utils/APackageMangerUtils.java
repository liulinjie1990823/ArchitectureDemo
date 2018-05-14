package com.llj.lib.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * 版本操作类
 *
 * @author liulj
 */
public class APackageMangerUtils {
    /**
     * 1.这个用来判别版本新旧，安装的时候会验证，新版本必须比旧的大
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        int versionCode = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode;
            return versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionCode;
    }

    /**
     * 2.其实这个才是版本号
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        String versionCode = null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionName;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionCode;
    }

    /**
     * 获取我们自己用以记录用的版本号
     *
     * @param context
     * @return
     */
    public static int getOurVersionCode(Context context) {
        int versionCode = -1;

        try {
            String versionName = getAppVersionName(context);
            String[] codes = versionName.split("\\.");
            versionCode = AParseUtils.parseInt(codes[0]) * 10000 + AParseUtils.parseInt(codes[1]) * 1000 + Integer.valueOf(codes[2]);
        } catch (Exception ignore) {
        }

        return versionCode;
    }

    //获取渠道名
    public static String getChannel(Context context) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getApplicationContext().getPackageManager().getApplicationInfo(context.getApplicationContext().getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (appInfo == null) {
            return "";
        }
        return appInfo.metaData.getString("UMENG_CHANNEL");
    }
}
