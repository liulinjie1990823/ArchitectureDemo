package com.llj.socialization;

import static com.llj.socialization.Platform.PlatformType.ALI;
import static com.llj.socialization.Platform.PlatformType.QQ;
import static com.llj.socialization.Platform.PlatformType.QQ_ZONE;
import static com.llj.socialization.Platform.PlatformType.SINA;
import static com.llj.socialization.Platform.PlatformType.WECHAT;
import static com.llj.socialization.Platform.PlatformType.WECHAT_CIRCLE;

/**
 * project:android
 * describe:
 * Created by llj on 2017/8/13.
 */

public class Platform {

    public static boolean isQQ(int platform) {
        return platform == QQ;
    }

    public static boolean isQQZone(int platform) {
        return platform == QQ_ZONE;
    }

    public static boolean isWechat(int platform) {
        return platform == WECHAT;
    }

    public static boolean isWechatCircle(int platform) {
        return platform == WECHAT_CIRCLE;
    }

    public static boolean isSina(int platform) {
        return platform == SINA;
    }

    public static boolean isAli(int platform) {
        return platform == ALI;
    }

    public interface PlatformType {
        int QQ            = 1;//qq
        int QQ_ZONE       = 2;//qq空间
        int WECHAT        = 3;//微信
        int WECHAT_CIRCLE = 4;//微信朋友圈
        int SINA          = 5;//新浪
        int ALI           = 6;//支付宝
    }
}
