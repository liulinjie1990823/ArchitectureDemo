package com.llj.login.ui.presenter;

import com.llj.adapter.refresh.IRefresh;
import com.llj.application.vo.UserInfoVo;
import com.llj.lib.base.mvp.BaseActivityPresenter;
import com.llj.lib.base.mvp.IBaseActivityView;
import com.llj.lib.mvp.annotation.Presenter;
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
 * ArchitectureDemo. describe:
 *
 * @author llj
 * @date 2019-06-11
 */
public class LoginPresenter extends BaseActivityPresenter<LoginRepository, IBaseActivityView> {

  //private LoginPresenter_Helper mLoginPresenter;

  @Inject
  public LoginPresenter(@NotNull LoginRepository repository) {
    super(repository);
    //mLoginPresenter = new LoginPresenter_Helper(repository);
  }

  //@Presenter(object = UserInfoVo.class, showLoading = true, exceptionId = 1)
  public void accountLogin(boolean showLoading, int requestId, ILoginView.PhoneLogin view) {
    if (view == null) {
      return;
    }

    int taskId = 0;
    if (view.getLoadingDialog() != null) {
      view.getLoadingDialog().setRequestId(requestId);
    }

    HashMap<String, Object> param = view.getParams1(requestId);
    if (param == null) {
      return;
    }
    Single<Response<BaseResponse<UserInfoVo>>> single = getRepository().accountLogin(param);

    if (showLoading) {
      single = single.doOnSubscribe(view).doFinally(view);
    }
    //Observer
    BaseApiObserver<UserInfoVo> baseApiObserver = new BaseApiObserver<UserInfoVo>(view.hashCode()) {

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

  @Presenter(object = UserInfoVo.class, showLoading = true, exceptionId = 2)
  public void accountLogin(boolean showLoading, ILoginView.PhoneLogin view) {

  }

  @Presenter(object = UserInfoVo.class, showLoading = false, exceptionId = 3)
  public void phoneLogin(boolean refresh, final IRefresh iRefresh, int requestId,
      ILoginView.PhoneLogin view) {
  }

  @Presenter(object = UserInfoVo.class, showLoading = false, exceptionId = 4)
  public void phoneLogin(boolean refresh, final IRefresh iRefresh, ILoginView.PhoneLogin view) {
    if (iRefresh == null || view == null) {
      return;
    }

    int requestId = view.hashCode();
    if (view.getLoadingDialog() != null) {
      view.getLoadingDialog().setRequestId(requestId);
    }

    HashMap<String, Object> param = view.getParams1(requestId);
    if (param == null) {
      return;
    }
    if (refresh) {
      iRefresh.resetPageNum();
    }

    param.put("pageNum", iRefresh.getCurrentPageNum());
    param.put("pageSize", iRefresh.getPageSize());

    Single<Response<BaseResponse<UserInfoVo>>> single = getRepository().phoneLogin(param);

    //Observer
    BaseApiObserver<UserInfoVo> baseApiObserver = new BaseApiObserver<UserInfoVo>(view.hashCode()) {

      @Override
      public void onSubscribe(@NotNull Disposable d) {
        super.onSubscribe(d);
        view.addDisposable(getRequestId(), d);
      }


      @Override
      public void onSuccess(@NotNull BaseResponse<UserInfoVo> response) {
        super.onSuccess(response);
        iRefresh.finishRefreshOrLoadMore(true);
        view.onDataSuccess1(response.getData(), getRequestId());
      }


      @Override
      public void onError(@NotNull Throwable t) {
        super.onError(t);
        iRefresh.finishRefreshOrLoadMore(false);
        view.onDataError(-1, t, getRequestId());
      }

    };

    //subscribe
    RxApiManager.get().subscribeApi(single, view.bindRequestLifecycle(), baseApiObserver);

  }

}
