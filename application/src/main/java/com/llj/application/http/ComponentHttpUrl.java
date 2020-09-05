package com.llj.application.http;

import com.llj.component.service.BuildConfig;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/10
 */
public class ComponentHttpUrl {
    public static String BASE_URL = "http://open.test.jiehun.com.cn";

    static {
        if (BuildConfig.BUILD_TYPE.equals("debug")) {//测试环境
            BASE_URL = "http://open.test.jiehun.com.cn";
        } else if (BuildConfig.BUILD_TYPE.equals("beta")) {//预发布环境
        } else if (BuildConfig.BUILD_TYPE.equals("release")) {//正式环境
        }
    }
}
