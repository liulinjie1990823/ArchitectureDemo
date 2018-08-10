package com.llj.socialization.pay;

import android.support.annotation.IntDef;

import com.llj.socialization.Platform;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * project:android
 * describe:
 * Created by llj on 2017/8/13.
 */

public interface PayPlatformType extends Platform.PlatformType {
    @IntDef({WECHAT})
    @Retention(RetentionPolicy.SOURCE)
    @interface Platform {
    }

}
