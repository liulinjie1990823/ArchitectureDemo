package com.llj.login

import androidx.viewbinding.ViewBinding
import com.llj.component.service.PlatformMvcBaseActivity
import com.llj.application.router.CRouter

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
abstract class LoginMvcBaseActivity<V : ViewBinding> : PlatformMvcBaseActivity<V>() {

    override fun getModuleName(): String {
        return CRouter.MODULE_LOGIN
    }
}