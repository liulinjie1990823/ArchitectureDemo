package com.llj.socialization;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.sina.weibo.sdk.WbSdk;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.util.List;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/2/16.
 */

public class InstallUtil {
    /**
     * @param context
     * @return
     */
    public static boolean isQQInstalled(Context context) {
        PackageManager pm = context.getPackageManager();
        if (pm == null) {
            return false;
        }

        List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
        for (PackageInfo info : packageInfos) {
            if (TextUtils.equals(info.packageName.toLowerCase(), "com.tencent.mobileqq")) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param mIWXAPI
     * @return
     */
    public static boolean isWeChatInstalled(IWXAPI mIWXAPI) {
        return mIWXAPI.isWXAppInstalled();
    }

    /**
     * @param context
     * @return
     */
    public static boolean isSinaInstalled(Context context) {
        return WbSdk.isWbInstall(context);
    }
}
