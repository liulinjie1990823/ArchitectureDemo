package com.llj.lib.base.mvp;

import android.arch.lifecycle.DefaultLifecycleObserver;

import com.uber.autodispose.AutoDisposeConverter;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/16
 */
public interface IPresenter extends DefaultLifecycleObserver {

    <T> AutoDisposeConverter<T> bindLifecycle();

    void init();
    void destroy();

}
