package com.llj.login.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.component.service.arouter.CRouter
import com.llj.login.LoginMvpBaseActivity
import com.llj.login.R
import com.llj.login.ui.presenter.PhoneLoginPresenter
import com.llj.login.ui.view.PhoneLoginView

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/18
 */
@Route(path = CRouter.LOGIN_PHONE_LOGIN_ACTIVITY)
class PhoneLoginActivity : LoginMvpBaseActivity<PhoneLoginPresenter>(), PhoneLoginView {
    override fun initData() {
    }

    override fun initViews(savedInstanceState: Bundle?) {
    }

    override fun layoutId(): Int {
        return R.layout.login_activity_phone_login
    }
}