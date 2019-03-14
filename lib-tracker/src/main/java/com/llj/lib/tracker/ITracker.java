package com.llj.lib.tracker;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/14
 */
public interface ITracker {

    @NonNull
    String getPageKey();

    //如果是一个页面公用的情况使用该方法传递，否则用PageName中的name
    default String getPageName() {
        return "";
    }

    default void setPageName(Activity activity) {
        if (TextUtils.isEmpty(getPageKey())) {
            throw new RuntimeException("you should set PageKey in activity");
        }
        if (!TextUtils.isEmpty(getPageName())) {
            activity.getIntent().putExtra(getPageKey(), getPageName());
        } else {
            PageName pageName = activity.getClass().getAnnotation(PageName.class);
            if (pageName != null) {
                activity.getIntent().putExtra(getPageKey(), pageName.value());
            }
        }
    }
}
