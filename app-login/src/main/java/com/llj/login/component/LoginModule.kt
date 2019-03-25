package com.llj.login.component

import android.app.Activity
import android.support.v4.app.FragmentActivity
import com.billy.cc.core.component.CC
import com.billy.cc.core.component.IComponent
import com.llj.component.service.ComponentApplication
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
        return "app-login"
    }

    override fun onCall(cc: CC?): Boolean {
        if (cc == null) {
            return false
        }
        if ("init" == cc.actionName) {
            val componentApplication = cc.context as ComponentApplication
            mLoginComponent = DaggerLoginComponent.builder()
                    .component(componentApplication.mComponent)
                    .build()
        } else if ("injectActivity" == cc.actionName) {
            val activity = cc.context as Activity
            mLoginComponent.activityInjector().inject(activity)
        } else if ("injectFragment" == cc.actionName) {
            val activity = cc.context as FragmentActivity
            val tag = cc.getParamItem<String>("fragment")

            if (tag != null) {
                val findFragmentByTag = activity.supportFragmentManager.findFragmentByTag(tag)
                if (findFragmentByTag != null) {
                    mLoginComponent.supportFragmentInjector().inject(findFragmentByTag)
                }
            }
        }
        return false
    }
}