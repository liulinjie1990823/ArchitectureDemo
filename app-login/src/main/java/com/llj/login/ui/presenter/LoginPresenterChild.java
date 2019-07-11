package com.llj.login.ui.presenter;

import com.llj.login.ui.repository.LoginRepository;

import org.jetbrains.annotations.NotNull;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-07-11
 */
public class LoginPresenterChild extends LoginPresenter {

    public LoginPresenterChild(@NotNull LoginRepository repository) {
        super(repository);
    }
}
