package com.llj.login.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.component.service.arouter.CRouter
import com.llj.lib.base.MvcBaseActivity
import com.llj.login.R

/**
 * ArchitectureDemo.
 * describe:登录界面
 * author llj
 * date 2018/8/22
 */
@Route(path = CRouter.LOGIN_LOGIN_ACTIVITY)
class LoginActivity : MvcBaseActivity() {
    override fun layoutId(): Int {
        return R.layout.login_activity_login
    }

    override fun initViews(savedInstanceState: Bundle?) {
    }

    override fun initData() {
    }
}