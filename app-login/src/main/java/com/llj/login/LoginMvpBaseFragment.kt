package com.llj.login

import com.llj.lib.base.MvpBaseFragment
import com.llj.lib.base.mvp.IBasePresenter

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
abstract class LoginMvpBaseFragment<P : IBasePresenter> : MvpBaseFragment<P>() {

    override fun getModuleName(): String {
        return "app-login"
    }
}