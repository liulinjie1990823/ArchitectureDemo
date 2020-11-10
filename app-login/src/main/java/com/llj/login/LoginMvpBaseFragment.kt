package com.llj.login

import androidx.viewbinding.ViewBinding
import com.llj.component.service.PlatformMvpBaseFragment
import com.llj.application.router.CRouter
import com.llj.lib.base.mvp.IBasePresenter

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
abstract class LoginMvpBaseFragment<P : IBasePresenter> : PlatformMvpBaseFragment<ViewBinding, P>() {

    override fun getModuleName(): String {
        return CRouter.MODULE_LOGIN
    }
}