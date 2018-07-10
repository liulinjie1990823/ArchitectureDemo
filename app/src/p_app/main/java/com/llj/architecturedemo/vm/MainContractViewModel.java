package com.llj.architecturedemo.vm;

import android.arch.lifecycle.LiveData;

import com.llj.architecturedemo.db.entity.MobileEntity;
import com.llj.architecturedemo.repository.MobileRepository;
import com.llj.lib.base.mvp.IView;
import com.llj.lib.base.mvvm.BaseViewModel;
import com.llj.lib.net.response.IResponse;

import javax.inject.Inject;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/29
 */
public class MainContractViewModel extends BaseViewModel {

    private LiveData<MobileEntity> test;

    private MobileRepository mMobileRepository;

    @Inject
    public MainContractViewModel(MobileRepository mobileRepository) {
        mMobileRepository = mobileRepository;
    }

    public LiveData<MobileEntity> getTest() {
        return mMobileRepository.getTest();
    }


    public LiveData<String> getResult() {
        return mMobileRepository.getResult();
    }

    public LiveData<IResponse<MobileEntity>> getMobile(String phone, IView view) {
        return mMobileRepository.getMobile(phone, view);
    }

    public void setQuery(String originalInput) {
        mMobileRepository.setQuery(originalInput);
    }
}
