package com.llj.lib.tracker;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/14
 */
public interface ITracker {


    //如果是一个页面公用的情况使用该方法传递，否则用PageName中的name
    default String getPageName() {
        return "";
    }

    default String getPageId() {
        return "";
    }

    default boolean ignore() {
        return false;
    }

}
