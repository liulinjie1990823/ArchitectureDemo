package com.llj.lib.base.mvp;

import android.arch.lifecycle.ViewModel;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/29
 */
public class BaseViewModel extends ViewModel implements IModel{
    /**
     * 在框架中 {@link BasePresenter#destroy()} 时会默认调用 {@link IModel#destroy()}
     */
    @Override
    public void destroy() {

    }
}
