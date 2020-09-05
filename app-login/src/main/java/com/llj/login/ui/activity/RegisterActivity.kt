package com.llj.login.ui.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.component.service.MiddleMvcBaseActivity
import com.llj.application.router.CRouter
import com.llj.login.LoginMvcBaseActivity
import com.llj.login.R

/**
 * ArchitectureDemo.
 * describe:注册界面
 * author llj
 * date 2018/8/22
 */
@Route(path = CRouter.LOGIN_REGISTER_ACTIVITY)
class RegisterActivity : LoginMvcBaseActivity<ViewBinding>() {
    override fun layoutId(): Int {
        return R.layout.login_activity_register
    }

    override fun initViews(savedInstanceState: Bundle?) {
    }

    override fun initData() {
    }
}