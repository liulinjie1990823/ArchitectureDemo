package com.llj.architecturedemo;

import android.arch.lifecycle.LiveData;

import com.llj.architecturedemo.db.model.MobileEntity;
import com.llj.architecturedemo.repository.MobileRepository;
import com.llj.lib.net.IRequestDialog;
import com.llj.lib.net.IResponse;
import com.uber.autodispose.AutoDisposeConverter;

import javax.inject.Inject;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/29
 */
public class MainContractViewModel extends MainContract.ViewModel {

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

    public LiveData<MobileEntity> getMobile(AutoDisposeConverter<IResponse<MobileEntity>> autoDisposeConverter, IRequestDialog iRequestDialog) {
        return mMobileRepository.getMobile(autoDisposeConverter,iRequestDialog);
    }

    public void setQuery( String originalInput){
        mMobileRepository.setQuery(  originalInput);
    }
}
