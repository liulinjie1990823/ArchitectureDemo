package com.llj.login

import androidx.viewbinding.ViewBinding
import com.llj.component.service.MiddleMvcBaseActivity

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
abstract class LoginMvcBaseActivity<V : ViewBinding> : MiddleMvcBaseActivity<V>() {

    override fun getModuleName(): String {
        return "app-login"
    }
}