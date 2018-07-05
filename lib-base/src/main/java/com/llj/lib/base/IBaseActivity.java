package com.llj.lib.base;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.support.annotation.NonNull;
import android.view.MotionEvent;

import com.llj.lib.utils.AInputMethodManagerUtils;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/24
 */
public interface IBaseActivity extends IActivityStack {

    void initLifecycleObserver(@NonNull Lifecycle lifecycle);

    void superOnBackPressed();

    void backToLauncher(boolean nonRoot);

    default boolean onTouchEvent(Activity activity, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            AInputMethodManagerUtils.hideSoftInputFromWindow(activity);
        }
        return activity.onTouchEvent(event);
    }
}
