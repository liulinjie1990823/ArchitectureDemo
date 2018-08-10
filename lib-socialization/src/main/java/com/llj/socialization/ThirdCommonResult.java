package com.llj.socialization;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/2/16.
 */

public class ThirdCommonResult {

    /**
     * @param platform
     * @return
     */
    public String getPlatformString(int platform) {
        String type = "";
        switch (platform) {
            case Platform.PlatformType.QQ:
                type = "qq";
                break;
            case Platform.PlatformType.QQ_ZONE:
                type = "qq空间";
                break;
            case Platform.PlatformType.WECHAT:
                type = "微信";
                break;
            case Platform.PlatformType.WECHAT_CIRCLE:
                type = "微信朋友圈";
                break;
            case Platform.PlatformType.SINA:
                type = "微博";
                break;
        }
        return type;
    }

    public static String getUninstallString(int platform) {
        String type = "";
        switch (platform) {
            case Platform.PlatformType.QQ:
                type = "请先安装qq客户端";
                break;
            case Platform.PlatformType.QQ_ZONE:
                type = "请先安装qq客户端";
                break;
            case Platform.PlatformType.WECHAT:
                type = "请先安装微信客户端";
                break;
            case Platform.PlatformType.WECHAT_CIRCLE:
                type = "请先安装微信客户端";
                break;
            case Platform.PlatformType.SINA:
                type = "请先安装新浪微博客户端";
                break;
        }
        return type;
    }
}
