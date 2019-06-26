package com.llj.lib.webview.event;

import org.json.JSONObject;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-26
 */
public class JsInvokeJavaEvent {
    public static final String UI_VIEW_APPEAR    = "ui_view_appear";
    public static final String UI_VIEW_DISAPPEAR = "ui_view_disappear";
    public static final String UI_VIEW_DESTROY   = "ui_view_destroy";
    public static final String APP_ACTIVATION    = "app_activation";
    public static final String APP_BACKGROUND    = "app_background";
    public static final String APP_JUMP          = "app_jump";//页面跳转
    public static final String UI_VIEW_REFRESH   = "ui_view_refresh";//刷新其他页面
    public static final String CLOSE_WAP         = "close_wap";//关闭页面
    public static final String UI_SHARE          = "ui_share";//使用ui界面分享
    public static final String DIRECT_SHARE      = "direct_share";//直接分享

    public String     handler;
    public JSONObject args;
    public String     callback;

    public JsInvokeJavaEvent(String handler, JSONObject args, String callback) {
        this.handler = handler;
        this.args = args;
        this.callback = callback;
    }
}
