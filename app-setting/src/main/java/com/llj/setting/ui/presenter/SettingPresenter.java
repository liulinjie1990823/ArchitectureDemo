package com.llj.setting.ui.presenter;

import com.llj.component.service.vo.UserInfoVo;
import com.llj.lib.base.mvp.BaseActivityPresenter;
import com.llj.lib.net.RxApiManager;
import com.llj.lib.net.observer.BaseApiObserver;
import com.llj.lib.net.response.BaseResponse;
import com.llj.setting.ui.model.MobileInfoVo;
import com.llj.setting.ui.repository.SettingRepository;
import com.llj.setting.ui.view.SettingView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/25
 */
public class SettingPresenter extends BaseActivityPresenter<SettingRepository, SettingView> {

    @Inject
    SettingPresenter(@NotNull SettingRepository repository, @NotNull SettingView view) {
        super(repository, view);
    }

    //手机登录
    public void phoneLogin(HashMap<String, Object> map, Boolean showLoading) {
        if (getMRepository() == null) {
            return;
        }
        //Single
        Single<Response<BaseResponse<UserInfoVo>>> phoneLogin = getMRepository().phoneLogin(map);
        if (showLoading) {
            phoneLogin = phoneLogin.doOnSubscribe(getMView()).doFinally(getMView());
        }
        if (phoneLogin != null) {
            subscribeLogin(phoneLogin);
        }
    }

    //账号登录
    public void accountLogin(HashMap<String, Object> map, Boolean showLoading) {
        if (getMRepository() == null) {
            return;
        }
        //Single
        Single<Response<BaseResponse<UserInfoVo>>> phoneLogin = getMRepository().accountLogin(map);
        if (showLoading) {
            phoneLogin = phoneLogin.doOnSubscribe(getMView()).doFinally(getMView());
        }
        if (phoneLogin != null) {
            subscribeLogin(phoneLogin);
        }
    }

    //获取手机号信息
    public void getMobileInfo(String mobile, Boolean showLoading) {
        if (getMRepository() == null) {
            return;
        }
        //Single
        Single<Response<BaseResponse<MobileInfoVo>>> getMobileInfo = getMRepository().getMobileInfo(mobile);
        if (showLoading) {
            getMobileInfo = getMobileInfo.doOnSubscribe(getMView()).doFinally(getMView());
        }
        //Observer
        BaseApiObserver<MobileInfoVo> baseApiObserver = new BaseApiObserver<MobileInfoVo>(getMView().getRequestTag()) {

            @Override
            public void onSubscribe(@NotNull Disposable d) {
                super.onSubscribe(d);
                getMView().addDisposable(getRequestTag(), d);
            }


            @Override
            public void onSuccess(@NotNull BaseResponse<MobileInfoVo> response) {
                super.onSuccess(response);
                getMView().onDataSuccess2(response.getData());
            }


            @Override
            public void onError(@NotNull Throwable t) {
                super.onError(t);
            }

        };

        //subscribe
        RxApiManager.get().subscribeApi(getMobileInfo, getMView().bindRequestLifecycle(), baseApiObserver);
    }

    private void subscribeLogin(Single<Response<BaseResponse<UserInfoVo>>> single) {
        //Observer
        BaseApiObserver<UserInfoVo> baseApiObserver = new BaseApiObserver<UserInfoVo>(getMView().getRequestTag()) {

            @Override
            public void onSubscribe(@NotNull Disposable d) {
                super.onSubscribe(d);
                getMView().addDisposable(getRequestTag(), d);
            }


            @Override
            public void onSuccess(@NotNull BaseResponse<UserInfoVo> response) {
                super.onSuccess(response);
                getMView().onDataSuccess1(response.getData());
            }


            @Override
            public void onError(@NotNull Throwable t) {
                super.onError(t);
            }

        };

        //subscribe
        RxApiManager.get().subscribeApi(single, getMView().bindRequestLifecycle(), baseApiObserver);
    }
}
