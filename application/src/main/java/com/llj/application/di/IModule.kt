package com.llj.application.di

import android.app.Activity
import android.content.Context
import android.util.ArrayMap
import com.billy.cc.core.component.CC
import com.llj.application.AppApplication
import com.llj.component.service.IInject

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

    fun initComponent(application: AppApplication)

    fun getComponent(): IInject

    fun checkComponent(component: IInject?): IInject {
        if (component == null) {
            throw RuntimeException("current component should be initialization")
        }
        return component
    }

    //cc实现
    fun innerCall(cc: CC): Boolean {
        if (INIT == cc.actionName) {
            if (cc.context is AppApplication) {
                initComponent(cc.context as AppApplication)
            }
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

    //ARouter实现
    fun innerCall(context: Context, action: String, map: ArrayMap<String, String>): Boolean {
        if (INIT == action) {
            if (context is AppApplication) {
                initComponent(context)
            }
        } else if (INJECT_ACTIVITY == action) {
            val activity = context as Activity
            getComponent().activityInjector().inject(activity)
        } else if (INJECT_FRAGMENT == action) {
            val activity = context as androidx.fragment.app.FragmentActivity
            val tag = map["fragment"]
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