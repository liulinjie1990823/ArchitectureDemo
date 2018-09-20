package com.llj.login.component

import android.app.Activity
import com.billy.cc.core.component.CC
import com.billy.cc.core.component.IComponent
import com.llj.lib.base.BaseApplication
import com.llj.login.DaggerLoginComponent
import com.llj.login.LoginComponent

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/23
 */
class LoginModule : IComponent {
    private lateinit var mLoginComponent: LoginComponent

    override fun getName(): String {
        return "LoginModule"
    }

    override fun onCall(cc: CC?): Boolean {
        if (cc == null) {
            return false
        }
        if ("init" == cc.actionName) {
            mLoginComponent = DaggerLoginComponent.builder()
                    .application(cc.context.applicationContext as BaseApplication)
                    .build()
        } else if ("inject" == cc.actionName) {
            val activity = cc.context as Activity
            mLoginComponent.activityInjector().inject(activity)
        }
        return false
    }


}