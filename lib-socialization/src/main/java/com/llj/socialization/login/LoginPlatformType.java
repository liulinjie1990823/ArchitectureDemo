package com.llj.socialization.login;

import androidx.annotation.IntDef;

import com.llj.socialization.Platform;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/1/18.
 */

public interface LoginPlatformType extends Platform.PlatformType {
    @IntDef({QQ, WECHAT, SINA})
    @Retention(RetentionPolicy.SOURCE)
    @interface Platform {
    }
}

