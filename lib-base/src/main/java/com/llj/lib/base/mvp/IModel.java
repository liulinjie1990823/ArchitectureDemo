package com.llj.lib.base.mvp;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/16
 */
public interface IModel {

    /**
     * 在框架中 {@link BasePresenter#onDestroy()} 时会默认调用 {@link IModel#onDestroy()}
     */
    void onDestroy();
}
