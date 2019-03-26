package com.llj.architecturedemo.component

import android.app.Activity
import android.support.v4.app.FragmentActivity
import com.billy.cc.core.component.CC
import com.billy.cc.core.component.IComponent
import com.llj.architecturedemo.AppComponent
import com.llj.architecturedemo.DaggerAppComponent
import com.llj.component.service.ComponentApplication

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/23
 */
class AppModule : IComponent {
    private lateinit var mComponent: AppComponent

    override fun getName(): String {
        return "app"
    }

    override fun onCall(cc: CC?): Boolean {
        if (cc == null) {
            return false
        }
        if ("init" == cc.actionName) {
            val componentApplication = cc.context as ComponentApplication
            mComponent = DaggerAppComponent.builder()
                    .component(componentApplication.mComponent)
                    .build()
        } else if ("injectActivity" == cc.actionName) {
            val activity = cc.context as Activity
            mComponent.activityInjector().inject(activity)
        } else if ("injectFragment" == cc.actionName) {
            val activity = cc.context as FragmentActivity
            val tag = cc.getParamItem<String>("fragment")

            if (tag != null) {
                val findFragmentByTag = activity.supportFragmentManager.findFragmentByTag(tag)
                if (findFragmentByTag != null) {
                    mComponent.supportFragmentInjector().inject(findFragmentByTag)
                }
            }
        }
        return false
    }
}