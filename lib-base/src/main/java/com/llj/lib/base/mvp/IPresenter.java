package com.llj.lib.base.mvp;

import android.arch.lifecycle.DefaultLifecycleObserver;

import com.llj.lib.base.IEvent;
import com.uber.autodispose.AutoDisposeConverter;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/16
 */
public interface IPresenter extends DefaultLifecycleObserver,IEvent {

    <T> AutoDisposeConverter<T> bindLifecycle();

}
