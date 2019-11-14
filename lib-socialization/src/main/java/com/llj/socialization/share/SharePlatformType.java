package com.llj.socialization.share;

import androidx.annotation.IntDef;

import com.llj.socialization.Platform;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/1/18.
 */

public interface SharePlatformType extends Platform.PlatformType {
    @IntDef({QQ, QQ_ZONE, WECHAT, WECHAT_CIRCLE, SINA})
    @Retention(RetentionPolicy.SOURCE)
    @interface Platform {
    }

}
