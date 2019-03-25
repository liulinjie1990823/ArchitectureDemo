package com.llj.login.api;

import com.llj.component.service.BuildConfig;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/10/18
 */
public class LoginHttpUrl {
    public static String BASE_URL = "https://www.iteblog.com";

    static {
        if (BuildConfig.BUILD_TYPE.equals("debug")) {//测试环境
            BASE_URL = "https://www.iteblog.com";
        } else if (BuildConfig.BUILD_TYPE.equals("beta")) {//预发布环境
        } else if (BuildConfig.BUILD_TYPE.equals("release")) {//正式环境
        }
    }
}
