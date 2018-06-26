package com.llj.architecturedemo;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.llj.architecturedemo.db.model.MobileEntity;
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
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    public MainPresenter(MainContractViewModel model, MainContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        super.onResume(owner);

        LiveData<MobileEntity> mobileLivData = mViewModel.getMobile(mView);
        mobileLivData.removeObservers(mView);
        mobileLivData.observe(mView, mobileEntity -> mView.toast(mobileEntity));
    }
}
