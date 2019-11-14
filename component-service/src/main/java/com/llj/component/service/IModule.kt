package com.llj.component.service

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import com.billy.cc.core.component.CC

/**
 * ArchitectureDemo.
 * 各个子module需要实现该类，可以通过cc的IComponent，或者ARouter来实现。
 * author llj
 * date 2019/3/26
 */
interface IModule {

    companion object {
        const val INIT = "init"
        const val INJECT_ACTIVITY = "injectActivity"
        const val INJECT_FRAGMENT = "injectFragment"
    }

    fun initComponent(application: MiddleApplication)

    fun getComponent(): IInject

    fun checkComponent(component: IInject?): IInject {
        if (component == null) {
            throw RuntimeException("current component should be initialization")
        }
        return component
    }

    fun innerCall(cc: CC): Boolean {
        if (INIT == cc.actionName) {
            val componentApplication = cc.context as MiddleApplication
            initComponent(componentApplication)
        } else if (INJECT_ACTIVITY == cc.actionName) {
            val activity = cc.context as Activity
            getComponent().activityInjector().inject(activity)
        } else if (INJECT_FRAGMENT == cc.actionName) {
            val activity = cc.context as androidx.fragment.app.FragmentActivity
            val tag = cc.getParamItem<String>("fragment")

            if (tag != null) {
                val findFragmentByTag = activity.supportFragmentManager.findFragmentByTag(tag)
                if (findFragmentByTag != null) {
                    getComponent().supportFragmentInjector().inject(findFragmentByTag)
                }
            }
        }
        return false
    }
}