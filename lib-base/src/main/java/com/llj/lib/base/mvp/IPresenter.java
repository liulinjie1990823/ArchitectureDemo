package com.llj.lib.base.mvp;

import android.arch.lifecycle.DefaultLifecycleObserver;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/16
 */
public interface IPresenter extends DefaultLifecycleObserver {

    void init();

    void destroy();

}
