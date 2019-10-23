package com.llj.login.ui.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.lib.jump.annotation.Jump;
import com.example.lib.jump.annotation.JumpKey;
import com.llj.component.service.arouter.CJump;
import com.llj.component.service.arouter.CRouter;
import com.llj.component.service.vo.UserInfoVo;
import com.llj.login.LoginMvpBaseActivity;
import com.llj.login.ui.presenter.LoginPresenter;
import com.llj.login.ui.view.ILoginView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-11
 */
@Jump(ciw = CJump.CIW_LOGIN, route = CRouter.LOGIN_PHONE_LOGIN_ACTIVITY)
@Route(path = CRouter.LOGIN_PHONE_LOGIN_ACTIVITY)
public class PhoneLoginActivity extends LoginMvpBaseActivity<LoginPresenter> implements ILoginView.PhoneLogin {
    @JumpKey(ciw = "nickName", name = CRouter.KEY_NICKNAME)
    @Autowired(name = CRouter.KEY_NICKNAME) String mName;

    @Override
    public int layoutId() {
        return 0;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }

    @Nullable
    @Override
    public HashMap<String, Object> getParams1(int taskId) {
        return null;
    }

    @Override
    public void onDataSuccess1(UserInfoVo result, int taskId) {

    }

    @Override
    public void onDataError(int tag, @NotNull Throwable e, int taskId) {

    }
}
