package com.llj.login

import com.llj.component.service.ComponentMvpBaseActivity
import com.llj.lib.base.mvp.IBasePresenter

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
abstract class LoginMvpBaseActivity<P : IBasePresenter> : ComponentMvpBaseActivity<P>() {

    override fun moduleName(): String {
        return "app-login"
    }
}