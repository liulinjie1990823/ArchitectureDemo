package com.llj.setting.ui.presenter;

import com.llj.component.service.vo.UserInfoVo;
import com.llj.lib.base.mvp.BaseActivityPresenter;
import com.llj.lib.base.mvp.IBaseActivityView;
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
public class SettingPresenter extends BaseActivityPresenter<SettingRepository, IBaseActivityView> {

    @Inject
    SettingPresenter(@NotNull SettingRepository repository) {
        super(repository);
    }

    //手机登录
    public void phoneLogin(boolean showLoading, SettingView.UserInfo view) {
        int taskId = view.hashCode();
        if (view.getLoadingDialog() != null) {
            view.getLoadingDialog().setRequestId(taskId);
        }
        HashMap<String, Object> param = view.getParams1(taskId);
        if (param == null) {
            return;
        }
        Single<Response<BaseResponse<UserInfoVo>>> phoneLogin = getRepository().phoneLogin(param);

        if (showLoading) {
            phoneLogin = phoneLogin.doOnSubscribe(view).doFinally(view);
        }
        subscribeLogin(phoneLogin, view);
    }

    //账号登录
    public void accountLogin(boolean showLoading, SettingView.UserInfo view) {
        int taskId = view.hashCode();
        if (view.getLoadingDialog() != null) {
            view.getLoadingDialog().setRequestId(taskId);
        }
        HashMap<String, Object> param = view.getParams1(taskId);
        if (param == null) {
            return;
        }
        Single<Response<BaseResponse<UserInfoVo>>> phoneLogin = getRepository().accountLogin(param);

        if (showLoading) {
            phoneLogin = phoneLogin.doOnSubscribe(view).doFinally(view);
        }
        subscribeLogin(phoneLogin, view);
    }

    //获取手机号信息
    public void getMobileInfo(boolean showLoading, SettingView.MobileInfo view) {
        int taskId = view.hashCode();
        if (view.getLoadingDialog() != null) {
            view.getLoadingDialog().setRequestId(taskId);
        }

        HashMap<String, Object> param = view.getParams2(taskId);
        if (param == null) {
            return;
        }
        String mobile = (String) param.get("mobile");

        Single<Response<BaseResponse<MobileInfoVo>>> getMobileInfo = getRepository().getMobileInfo(mobile);
        if (showLoading) {
            getMobileInfo = getMobileInfo.doOnSubscribe(view).doFinally(view);
        }
        //Observer
        BaseApiObserver<MobileInfoVo> baseApiObserver = new BaseApiObserver<MobileInfoVo>(getView()) {

            @Override
            public void onSubscribe(@NotNull Disposable d) {
                super.onSubscribe(d);
                view.addDisposable(getRequestId(), d);
            }


            @Override
            public void onSuccess(@NotNull BaseResponse<MobileInfoVo> response) {
                super.onSuccess(response);
                view.onDataSuccess2(response.getData(), getRequestId());
            }


            @Override
            public void onError(@NotNull Throwable t) {
                super.onError(t);
            }

        };

        //subscribe
        RxApiManager.get().subscribeApi(getMobileInfo, getView().bindRequestLifecycle(), baseApiObserver);
    }

    private void subscribeLogin(Single<Response<BaseResponse<UserInfoVo>>> single, SettingView.UserInfo view) {
        //Observer
        BaseApiObserver<UserInfoVo> baseApiObserver = new BaseApiObserver<UserInfoVo>(getView().getRequestId()) {

            @Override
            public void onSubscribe(@NotNull Disposable d) {
                super.onSubscribe(d);
                view.addDisposable(getRequestId(), d);
            }


            @Override
            public void onSuccess(@NotNull BaseResponse<UserInfoVo> response) {
                super.onSuccess(response);
                view.onDataSuccess1(response.getData(), getRequestId());
            }


            @Override
            public void onError(@NotNull Throwable t) {
                super.onError(t);
            }

        };

        //subscribe
        RxApiManager.get().subscribeApi(single, getView().bindRequestLifecycle(), baseApiObserver);
    }
}
