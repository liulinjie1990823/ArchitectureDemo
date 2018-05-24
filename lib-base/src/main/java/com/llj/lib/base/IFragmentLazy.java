package com.llj.lib.base;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/24
 */
public interface IFragmentLazy {

    default boolean useLazyLoad() {
        return false;
    }

    boolean hasInitAndVisible();

    default void onLazyLoad() {

    }

}
