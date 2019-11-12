package com.llj.login.interceptor;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.PretreatmentService;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.lib.jump.annotation.callback.JumpCallback;
import com.llj.component.service.arouter.CJump;
import com.llj.component.service.arouter.CRouter;
import com.llj.lib.base.AppManager;
import com.llj.lib.jump.api.core.Warehouse;
import com.llj.lib.utils.AParseUtils;
import com.llj.lib.webview.manager.WebViewManager;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

/**
 * ArchitectureDemo. 对所有跳转链接进行预处理，包括带有group的传统内页路径和外链（http,自定义scheme）
 *
 * @author llj
 * @date 2019-10-22
 */
@Route(path = "/login/LoginPretreatmentServiceImpl")
public class LoginPretreatmentServiceImpl implements PretreatmentService {

  @Override
  public boolean onPretreatment(Context context, Postcard postcard) {

    return start(context, postcard.getPath(), postcard);
  }

  @Override
  public void init(Context context) {

  }

  /**
   * @param context
   * @param link
   * @param postcard
   * @return false 拦截成功
   */
  public static boolean start(Context context, String link, Postcard postcard) {
    if (TextUtils.isEmpty(link)) {
      return false;
    }
    try {
      link = URLDecoder.decode(link, "utf-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    if (link.startsWith("/" + AppManager.getInstance().getJumpConfig().getNativeScheme())) {
      link = link.replaceFirst("/", "");
    }

    //已经登录了就把标记位去掉
    if (AppManager.getInstance().getUserInfoConfig().isLogin()) {
      link = link.replace("needLogin=1", "");
    }

    //直接跳转到内页:/setting/InjectActivity
    if (link.startsWith("/")) {
      return true;
    }

    //如果是链接参数的形式先解析字段
    HashMap<String, String> params = analysisParams(link);
    //判断是否登录
    //通过ciw映射跳转到内页:outPath://InjectActivity?needLogin=1&type=1&value=2&Id=3
    //通过WebView跳转:https://activity.jiehun.com.cn/app-update/index.html?hbh_app_version=6.10.0&hbh_app_link=ciw://mv/home?tab=0&needLogin=1
    if (params != null && params.size() != 0) {
      String needLoginStr = params.get(CRouter.AROUTER_NEED_LOGIN);
      //需要登录且没有登录
      if (AParseUtils.parseInt(needLoginStr) == 1 && !isLogin()) {
        //跳登录页面
        ARouter.getInstance().build(AppManager.getInstance().getJumpConfig().getLoginPath())
            .withString(CRouter.AROUTER_FORWARD_PATH, link)
            .withInt(CRouter.AROUTER_NEED_LOGIN, 0)
            .navigation();
        return false;
      }
    }
    //通过ciw映射跳转到内页:outPath://InjectActivity?needLogin=1&type=1&value=2&Id=3
    if (link.startsWith(AppManager.getInstance().getJumpConfig().getNativeScheme())) {
      goToNativePage(link, params, postcard);

    } else {
      //通过WebView跳转:https://activity.jiehun.com.cn/app-update/index.html?hbh_app_version=6.10.0&hbh_app_link=ciw://mv/home?tab=0&needLogin=1
      if (params != null && params.containsKey(CJump.HBH_APP_VERSION) && params
          .containsKey(CJump.HBH_APP_LINK)) {
        String version =
            params.get(CJump.HBH_APP_VERSION) == null ? "0.0.0" : params.get(CJump.HBH_APP_VERSION);
        String appLink =
            params.get(CJump.HBH_APP_LINK) == null ? "" : params.get(CJump.HBH_APP_LINK);
        String decodeAppLink = null;
        try {
          decodeAppLink = URLDecoder.decode(appLink, "utf-8");
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }
        int target = calculation(version);
        int current = getAppVersion(context);

        if (current >= target && decodeAppLink != null && decodeAppLink
            .startsWith(WebViewManager.getInstance().getWebViewConfig().getScheme())) {
          //app版本比url中的版本大的
          goToNativePage(decodeAppLink, params, postcard);
        } else {
          //web
          goToWebPage(link, params, postcard);
        }
      } else {
        //web
        goToWebPage(link, params, postcard);
      }
    }

    return false;
  }

  private static boolean isLogin() {
    return AppManager.getInstance().getUserInfoConfig() != null && AppManager.getInstance()
        .getUserInfoConfig().isLogin();
  }


  private static void goToNativePage(String link, HashMap<String, String> params,
      Postcard postcard) {
    JumpCallback jumpCallback = Warehouse.sMap.get(UrlPage(link));
    if (jumpCallback != null) {
      jumpCallback.process(link, params);
    }
  }

  private static void goToWebPage(String link, HashMap<String, String> params, Postcard postcard) {
  }

  private static HashMap<String, String> analysisParams(String link) {

    return URLRequest(link);

  }

  public static int getAppVersion(Context context) {
    String version = "0";
    try {
      version = context.getApplicationContext().getPackageManager()
          .getPackageInfo(context.getPackageName(), 0).versionName;
    } catch (PackageManager.NameNotFoundException e) {
      throw new RuntimeException("the application not found");
    }
    return calculation(version);
  }

  /**
   * 6.10.0 三段式，每段100进制判断版本大小
   *
   * @param version
   * @return
   */
  public static int calculation(String version) {
    int num = 0;
    String[] split = version.split("\\.");
    if (split.length == 1) {
      num = AParseUtils.parseInt(split[0]) * 100 * 100;
    } else if (split.length == 2) {
      num = AParseUtils.parseInt(split[0]) * 100 * 100 + AParseUtils.parseInt(split[1]) * 100;
    } else if (split.length == 3) {
      num = AParseUtils.parseInt(split[0]) * 100 * 100 + AParseUtils.parseInt(split[1]) * 100
          + AParseUtils.parseInt(split[2]);
    }
    return num;

  }

  public static String UrlPage(String strURL) {
    String strPage = null;
    String[] arrSplit = null;

    strURL = strURL.trim();

    arrSplit = strURL.split("[?]");
    if (strURL.length() > 0) {
      if (arrSplit.length > 1) {
        if (arrSplit[0] != null) {
          strPage = arrSplit[0];
        }
      }
    }

    return strPage;
  }

  /**
   * 解析出url参数中的键值对 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
   *
   * @param URL url地址
   * @return url请求参数部分
   */
  public static HashMap<String, String> URLRequest(String URL) {
    HashMap<String, String> mapRequest = new HashMap<>();

    String[] arrSplit = null;

    String strUrlParam = truncateUrlPage(URL);
    if (strUrlParam == null) {
      return mapRequest;
    }
    //每个键值为一组 www.2cto.com
    arrSplit = strUrlParam.split("[&]");
    for (String strSplit : arrSplit) {
      String[] arrSplitEqual = null;
      arrSplitEqual = strSplit.split("[=]");

      //解析出键值
      if (arrSplitEqual.length > 1) {
        //正确解析
        mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

      } else {
        if (arrSplitEqual[0] != "") {
          //只有参数没有值，不加入
          mapRequest.put(arrSplitEqual[0], "");
        }
      }
    }
    return mapRequest;
  }

  /**
   * 去掉url中的路径，留下请求参数部分
   *
   * @param strURL url地址
   * @return url请求参数部分
   */
  private static String truncateUrlPage(String strURL) {
    String strAllParam = null;
    String[] arrSplit = null;

    strURL = strURL.trim();

    arrSplit = strURL.split("[?]");
    if (strURL.length() > 1) {
      if (arrSplit.length > 1) {
        if (arrSplit[1] != null) {
          strAllParam = arrSplit[1];
        }
      }
    }

    return strAllParam;
  }
}
