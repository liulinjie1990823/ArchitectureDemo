package com.llj.architecturedemo.presenter;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.llj.architecturedemo.db.entity.MobileEntity;
import com.llj.architecturedemo.repository.MobileRepository;
import com.llj.architecturedemo.view.MainContractView;
import com.llj.lib.base.mvp.BasePresenter;
import com.llj.lib.net.response.IResponse;

import javax.inject.Inject;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/17
 */
public class MainPresenter extends BasePresenter<MobileRepository, MainContractView> {

    @Inject
    public MainPresenter(MobileRepository repository, MainContractView view) {
        super(repository, view);
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        super.onResume(owner);

        LiveData<IResponse<MobileEntity>> mobileLivData = mRepository.getMobile("13188888888", mView);
        mobileLivData.removeObservers(mView);
        mobileLivData.observe(mView, new Observer<IResponse<MobileEntity>>() {
            @Override
            public void onChanged(@Nullable IResponse<MobileEntity> baseResponse) {
                mView.toast(baseResponse.getData());
            }
        });
    }
}
