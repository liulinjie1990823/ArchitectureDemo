package com.llj.login

import com.llj.lib.base.MvpBaseActivity
import com.llj.lib.base.mvp.IPresenter

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
abstract class LoginMvpBaseActivity<P : IPresenter> : MvpBaseActivity<P>() {

    override fun moduleName(): String {
        return "login"
    }
}