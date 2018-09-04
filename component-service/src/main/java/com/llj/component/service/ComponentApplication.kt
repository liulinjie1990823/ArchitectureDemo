package com.llj.component.service

import android.support.annotation.CallSuper
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.llj.lib.base.BaseApplication
import com.tencent.smtt.sdk.QbSdk

/**
 * ArchitectureDemo
 * describe:
 * author llj
 * date 2018/7/3
 */
abstract class ComponentApplication : BaseApplication() {

    @CallSuper
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {   // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog()     // Print log
            ARouter.openDebug()   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this)

        //x5内核初始化接口
        QbSdk.initX5Environment(applicationContext, object : QbSdk.PreInitCallback {
            override fun onViewInitFinished(arg0: Boolean) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.e("ComponentApplication", " onViewInitFinished is $arg0")
            }

            override fun onCoreInitFinished() {
            }
        })
    }
}
