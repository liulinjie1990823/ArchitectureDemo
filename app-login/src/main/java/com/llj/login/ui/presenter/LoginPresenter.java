package com.llj.login.ui.presenter;

import com.llj.lib.mvp.annotation.IView;
import com.llj.lib.mvp.annotation.ShowLoading;
import com.llj.lib.mvp.annotation.ShowRefreshing;
import com.llj.adapter.refresh.IRefresh;
import com.llj.component.service.vo.UserInfoVo;
import com.llj.lib.base.mvp.BaseActivityPresenter;
import com.llj.lib.base.mvp.IBaseActivityView;
import com.llj.lib.net.RxApiManager;
import com.llj.lib.net.observer.BaseApiObserver;
import com.llj.lib.net.response.BaseResponse;
import com.llj.login.ui.repository.LoginRepository;
import com.llj.login.ui.view.ILoginView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-11
 */
public class LoginPresenter extends BaseActivityPresenter<LoginRepository, IBaseActivityView> {
    @Inject
    public LoginPresenter(@NotNull LoginRepository repository) {
        super(repository);
    }

    @ShowLoading(key = "showLoading")
    @IView(view = ILoginView.PhoneLogin.class)
    public void accountLogin(boolean showLoading, ILoginView.PhoneLogin view) {
        int requestId = view.hashCode();
        if (view.getLoadingDialog() != null) view.getLoadingDialog().setRequestId(requestId);

        HashMap<String, Object> param = view.getParams1(requestId);
        if (param == null) {
            return;
        }
        Single<Response<BaseResponse<UserInfoVo>>> single = getRepository().phoneLogin(param);

        if (showLoading) {
            single = single.doOnSubscribe(view).doFinally(view);
        }
        //Observer
        BaseApiObserver<UserInfoVo> baseApiObserver = new BaseApiObserver<UserInfoVo>(view) {

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
                view.onDataError(-1, t, getRequestId());
            }

        };

        //subscribe
        RxApiManager.get().subscribeApi(single, view.bindRequestLifecycle(), baseApiObserver);
    }

    @ShowRefreshing
    @IView(view = ILoginView.PhoneLogin.class)
    public void phoneLogin(boolean refresh, final IRefresh iRefresh, ILoginView.PhoneLogin view) {
    }
}
