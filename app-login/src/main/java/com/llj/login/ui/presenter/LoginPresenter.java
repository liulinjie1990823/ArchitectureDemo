package com.llj.login.ui.presenter;

import com.example.lib.base.annotation.IView;
import com.example.lib.base.annotation.ShowLoading;
import com.example.lib.base.annotation.ShowRefreshing;
import com.llj.lib.base.mvp.BaseActivityPresenter;
import com.llj.lib.base.mvp.IBaseActivityView;
import com.llj.login.ui.repository.LoginRepository;
import com.llj.login.ui.view.ILoginView;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

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

    @ShowLoading
    @ShowRefreshing
    @IView(view = ILoginView.PhoneLogin.class)
    public void accountLogin() {
    }

    @ShowLoading
    @ShowRefreshing
    @IView(view = ILoginView.PhoneLogin.class)
    public void phoneLogin() {
    }
}
