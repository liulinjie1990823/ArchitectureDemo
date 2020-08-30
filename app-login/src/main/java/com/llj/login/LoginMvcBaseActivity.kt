package com.llj.login

import androidx.viewbinding.ViewBinding
import com.llj.component.service.MiddleMvcBaseActivity
import com.llj.component.service.arouter.CRouter

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
abstract class LoginMvcBaseActivity<V : ViewBinding> : MiddleMvcBaseActivity<V>() {

    override fun getModuleName(): String {
        return CRouter.MODULE_LOGIN
    }
}