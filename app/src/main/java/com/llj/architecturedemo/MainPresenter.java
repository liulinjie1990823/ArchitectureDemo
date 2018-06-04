package com.llj.architecturedemo;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import com.llj.lib.base.mvp.BasePresenter;

import javax.inject.Inject;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/17
 */
public class MainPresenter extends BasePresenter<MainContractViewModel, MainContract.View> {

    @Inject
    public MainPresenter(MainContractViewModel model, MainContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        super.onResume(owner);
        mViewModel.getMobile(bindLifecycle(), mView).observe(mView, mobile -> {
            mView.toast(mobile);
        });
    }
}
