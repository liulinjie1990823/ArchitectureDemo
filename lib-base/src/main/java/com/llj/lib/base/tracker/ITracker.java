package com.llj.lib.base.tracker;

import android.support.annotation.Nullable;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/14
 */
public interface ITracker {


    @Nullable
    String getChildPageName();

    void setChildPageName(String name);


    //如果是一个页面公用的情况使用该方法传递，否则用PageName中的name
    default String getPageName() {
        return "";
    }

}
