package com.llj.lib.utils;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.llj.lib.utils.helper.Utils;

/**
 * 共通的土司，这里可以全局控制土司的开关
 *
 * @author liulj
 */
public class AToastUtils {
    private static final String TAG = "AToastUtils";

    private static Toast mToast;

    private static Handler sHandler = new Handler(Looper.getMainLooper());//用来toast在非主线程里面调用

    private AToastUtils() {
    }

    public static void init() {
        if (sHandler == null)
            sHandler = new Handler(Looper.getMainLooper());
    }

    public static void init(Toast toast) {
        mToast = toast;
        if (sHandler == null)
            sHandler = new Handler(Looper.getMainLooper());
    }

    public static void show(int resId) {
        if (Utils.getApp() != null && Utils.getApp().getResources() != null)
            show(Utils.getApp().getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(int resId, int duration) {
        show(Utils.getApp().getResources().getText(resId), duration);
    }

    public static void show(CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }


    public static void show(final CharSequence text, final int duration) {
        if (Utils.getApp() == null) {
            LogUtil.e(TAG, "mContext为空");
        }
        try {
            if (!TextUtils.isEmpty(text)) {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    if (mToast == null)
                        mToast = Toast.makeText(Utils.getApp(), text, duration);
                    else {
                        mToast.setText(text);
                        mToast.setDuration(duration);
                    }
                    mToast.show();
                } else {
                    sHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mToast == null)
                                mToast = Toast.makeText(Utils.getApp(), text, duration);
                            else {
                                mToast.setText(text);
                                mToast.setDuration(duration);
                            }
                            mToast.show();
                        }
                    });
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void show(int resId, Object... args) {
        show(String.format(Utils.getApp().getResources().getString(resId), args), Toast.LENGTH_SHORT);
    }

    public static void show(String format, Object... args) {
        show(String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(int resId, int duration, Object... args) {
        show(String.format(Utils.getApp().getResources().getString(resId), args), duration);
    }

    public static void show(String format, int duration, Object... args) {
        show(String.format(format, args), duration);
    }

    /**
     * 关闭toast
     */
    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    /**
     * 应用退出时候调用
     */
    public static void destroyToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        if (sHandler != null) {
            sHandler = null;
        }
    }
}
