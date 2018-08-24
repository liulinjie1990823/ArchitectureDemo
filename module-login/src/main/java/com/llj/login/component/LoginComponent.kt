package com.llj.login.component

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.IComponent
import com.llj.component.service.arouter.CRouter

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/23
 */
class LoginComponent : IComponent {
    override fun getName(): String {
        return "LoginComponent"
    }

    override fun onCall(cc: CC?): Boolean {
        if(cc==null){
            return false
        }
        when(cc.actionName){
            "login" -> startLoginActivity()
        }

        return false
    }


    private fun startLoginActivity(){
        CRouter.start(CRouter.LOGIN_LOGIN_ACTIVITY)
    }


}