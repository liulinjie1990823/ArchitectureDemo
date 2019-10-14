package com.llj.login

import com.llj.component.service.MiddleMvpBaseFragment
import com.llj.lib.base.mvp.IBasePresenter

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
abstract class LoginMvpBaseFragment<P : IBasePresenter> : MiddleMvpBaseFragment<P>() {

    override fun getModuleName(): String {
        return "app-login"
    }
}