package com.llj.lib.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * InputMethodManager.SHOW_IMPLICIT:表示隐式显示输入窗口，非用户直接要求。窗口可能不显示。 InputMethodManager.SHOW_FORCED:表示用户强制打开输入法（如长按菜单键），一直保持打开直至只有显式关闭
 * InputMethodManager.HIDE_IMPLICIT_ONLY:表示如果用户未显式地显示软键盘窗口，则隐藏窗口 InputMethodManager.HIDE_NOT_ALWAYS:表示软键盘窗口总是会隐藏，除非开始是以SHOW_FORCED显示
 * <p>
 * <p>
 * imm.hideSoftInputFromInputMethod(passwdEdit.getWindowToken(), 0); android4.2会无效 Created by llj on
 * 16/1/25.
 */
public class AInputMethodManagerUtils {

    /**
     * （推荐）
     *
     * @param context
     * @param show
     */
    public static void showOrHideInput(Activity context, boolean show) {
        if (show) {
            //显示
            //showSoftInput这个方法也可以
            toggleSoftInput(context);
        } else {
            //隐藏
            if (context.getWindow() != null) {
                context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }
        }
    }

    public static void showOrHideInput(Dialog dialog, boolean show) {
        if (show) {
            //显示
            //showSoftInput这个方法也可以
            toggleSoftInput(dialog.getContext());
        } else {
            //隐藏
            if (dialog.getWindow() != null) {
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }
        }
    }

    /**
     * 1.必须在onResume和onStart之后调用，可以在postDelayed或者post之后调用，dialog可以在show方法中通过post延时调用
     * 2.EditText需要显示，且获得焦点
     *
     * @param editText 必须先获得焦点的EditText
     */
    public static boolean showSoftInput(EditText editText) {
        editText.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) editText.getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        return inputMethodManager.showSoftInput(editText, 0);
    }

    /**
     * 切换输入法的显示,如果输入法在窗口上已经显示，则隐藏(有时候隐藏可能失效，使用hideSoftInputFromWindow)，如果隐藏，则显示输入法到窗口上
     * 默认使用该方法显示 （推荐）
     *
     * @param context context
     */
    public static void toggleSoftInput(Context context) {
        InputMethodManager im = ((InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE));
        im.toggleSoftInput(0, 0);
    }

    /**
     * 如果页面刚进来可能获取不到焦点的view,因为焦点的view是在某些方法后才可以获取到
     * (Activity) context).getCurrentFocus()
     *
     * @param activity activity
     */
    public static void hideSoftInputFromWindow(Activity activity) {
        if (activity == null || activity.getCurrentFocus() == null) {
            return;
        }
        InputMethodManager inputMethodManager = ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE));
        if (inputMethodManager == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


    public static void hideSoftInputFromWindow(Dialog dialog) {
        if (dialog == null || dialog.getCurrentFocus() == null) {
            return;
        }
        InputMethodManager inputMethodManager = ((InputMethodManager) dialog.getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
        if (inputMethodManager == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(dialog.getCurrentFocus().getWindowToken(), 0);
    }


    /**
     * 隐藏输入法,默认使用该方法,view必须要获得焦点后
     *
     * @param view
     */
    public static boolean hideSoftInputFromWindow(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) {
            return false;
        }
        return inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
