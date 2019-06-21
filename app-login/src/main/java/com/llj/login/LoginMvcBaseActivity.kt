package com.llj.login

import com.llj.component.service.ComponentMvcBaseActivity

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
abstract class LoginMvcBaseActivity : ComponentMvcBaseActivity() {

    override fun moduleName(): String {
        return "app-login"
    }
}