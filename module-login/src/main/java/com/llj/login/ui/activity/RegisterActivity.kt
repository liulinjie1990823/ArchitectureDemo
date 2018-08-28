package com.llj.login.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.component.service.arouter.CRouter
import com.llj.lib.base.MvcBaseActivity
import com.llj.login.R

/**
 * ArchitectureDemo.
 * describe:注册界面
 * author llj
 * date 2018/8/22
 */
@Route(path = CRouter.LOGIN_REGISTER_ACTIVITY)
class RegisterActivity : MvcBaseActivity() {
    override fun layoutId(): Int {
        return R.layout.login_activity_register
    }

    override fun initViews(savedInstanceState: Bundle?) {
    }

    override fun initData() {
    }
}