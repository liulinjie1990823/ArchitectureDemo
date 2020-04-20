package com.llj.login

import androidx.viewbinding.ViewBinding
import com.llj.component.service.MiddleMvpBaseFragment
import com.llj.lib.base.mvp.IBasePresenter

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
abstract class LoginMvpBaseFragment<P : IBasePresenter> : MiddleMvpBaseFragment<ViewBinding, P>() {

    override fun getModuleName(): String {
        return "app-login"
    }
}