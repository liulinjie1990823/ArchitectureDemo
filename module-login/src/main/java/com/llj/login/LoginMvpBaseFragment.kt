package com.llj.login

import com.llj.lib.base.MvpBaseFragment
import com.llj.lib.base.mvp.IPresenter

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
abstract class LoginMvpBaseFragment<P : IPresenter> : MvpBaseFragment<P>() {

    override fun moduleName(): String {
        return "login"
    }
}