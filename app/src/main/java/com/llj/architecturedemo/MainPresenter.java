package com.llj.architecturedemo;

import com.llj.lib.base.mvp.BasePresenter;

import javax.inject.Inject;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/17
 */
public class MainPresenter extends BasePresenter<MainContract.View,MainContract.Model> {

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
        super(model, rootView);
    }

}
