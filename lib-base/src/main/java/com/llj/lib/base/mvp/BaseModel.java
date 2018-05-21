package com.llj.lib.base.mvp;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/16
 */
public class BaseModel implements IModel {
    /**
     * 在框架中 {@link BasePresenter#destroy()} 时会默认调用 {@link IModel#destroy()}
     */
    @Override
    public void destroy() {

    }
}