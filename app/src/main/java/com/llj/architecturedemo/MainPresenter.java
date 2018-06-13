package com.llj.architecturedemo;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

//        mViewModel.getTest().observe(owner, new Observer<MobileEntity>() {
//            @Override
//            public void onChanged(@Nullable MobileEntity mobileEntity) {
//                LogUtil.e(TAG, mobileEntity.toString());
//            }
//        });
//
//        mViewModel.getResult().observe(owner, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                LogUtil.e(TAG, s);
//            }
//        });
//
//        mViewModel.setQuery("test");
//
        LiveData<MobileEntity> mobileLivData = mViewModel.getMobile(bindRequestLifecycle(), mView);
        mobileLivData.removeObservers(mView);
        mobileLivData.observe(mView, new Observer<MobileEntity>() {
            @Override
            public void onChanged(@Nullable MobileEntity mobileEntity) {
                mView.toast(mobileEntity);
            }
        });
    }
}
