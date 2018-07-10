package com.llj.architecturedemo;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/10
 */
public class HttpUrl {
    public static String BASE_URL       = "https://www.iteblog.com/api/mobile.php";
    public static String UPLOAD_IMG_URL = "http://imgsrv.jiehun.com.cn";

    public static final String GITHUB_URL_KEY = "github";
    public static final String GITHUB_URL     = "https://www.github.com";

    static {
        if (BuildConfig.BUILD_TYPE.equals("debug")) {//测试环境
            BASE_URL = "https://www.iteblog.com";
            UPLOAD_IMG_URL = "http://oss.test.jiehun.com.cn";
        } else if (BuildConfig.BUILD_TYPE.equals("beta")) {//预发布环境
            BASE_URL = "http://open.jiehun.com.cn";
        } else if (BuildConfig.BUILD_TYPE.equals("release")) {//正式环境
            BASE_URL = "https://open.jiehun.com.cn";
            UPLOAD_IMG_URL = "http://imgsrv.jiehun.com.cn";
        }
    }
}
