package com.llj.login

import com.llj.component.service.MiddleMvcBaseActivity

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
abstract class LoginMvcBaseActivity : MiddleMvcBaseActivity() {

    override fun getModuleName(): String {
        return "app-login"
    }
}