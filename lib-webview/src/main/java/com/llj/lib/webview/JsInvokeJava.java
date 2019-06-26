package com.llj.lib.webview;

import android.webkit.JavascriptInterface;

import com.llj.lib.webview.event.JsInvokeJavaEvent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/3
 */
public class JsInvokeJava {

    /**
     * @param handler  事件类型
     * @param args     参数
     * @param callback 回调js方法
     */
    @JavascriptInterface
    public void android(String handler, String args, String callback) {
        if (handler == null) {
            return;
        }
        EventBus.getDefault().post(new JsInvokeJavaEvent(handler, getJSONObject(args), callback));
    }

    private JSONObject getJSONObject(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject == null) {
            return null;
        }
        return jsonObject;
    }
}
