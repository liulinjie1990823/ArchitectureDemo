package com.llj.architecturedemo

import android.app.Activity
import android.arch.lifecycle.DefaultLifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ProcessLifecycleOwner
import android.content.Context
import android.os.Bundle
import android.support.multidex.MultiDex
import android.support.v4.content.ContextCompat
import com.billy.cc.core.component.CC
import com.llj.architecturedemo.ui.activity.MainActivity
import com.llj.component.service.IModule
import com.llj.component.service.MiddleApplication
import com.llj.lib.base.MvpBaseActivity
import com.llj.lib.base.MvpBaseFragment
import com.llj.lib.base.listeners.ActivityLifecycleCallbacksAdapter
import com.llj.lib.jump.api.JumpHelp
import com.llj.lib.statusbar.LightStatusBarCompat
import com.llj.lib.statusbar.StatusBarCompat
import com.llj.socialization.SocialConstants
import com.llj.socialization.init.SocialConfig
import com.llj.socialization.init.SocialManager
import com.tencent.bugly.crashreport.CrashReport
import dagger.android.AndroidInjector
import timber.log.Timber


/**
 * ArchitectureDemo
 * describe:
 * author llj
 * date 2018/5/18
 */
class AppApplication : MiddleApplication() {
    private lateinit var mAppComponent: AppComponent

    override fun injectApp() {
        Timber.plant(object : Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                if (BuildConfig.DEBUG) {
                    super.log(priority, tag, message, t)
                }
            }
        })

        JumpHelp.init(this)

        mAppComponent = DaggerAppComponent.builder()
                .middleComponent(mMiddleComponent)
                .build()

        //调用LoginComponent中的dagger组件
        CC.obtainBuilder("app").setActionName(IModule.INIT).build().call()
        CC.obtainBuilder("app-login").setActionName(IModule.INIT).build().call()
        CC.obtainBuilder("app-setting").setActionName(IModule.INIT).build().call()

        //分享
        val config = SocialConfig.Builder(this, true).qqId("1103566659")
                .wx("wx78b27fadc81b6df4", "022fa45d435d7845179b6ae8d1912690")
                .sign("1476913513", "http://www.jiehun.com.cn/api/weibo/_grant", SocialConstants.SCOPE)
                .build()
        SocialManager.init(config)

        CrashReport.initCrashReport(applicationContext, "a0ed9c00ad", false)


        ProcessLifecycleOwner.get().lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                Timber.i("ProcessLifecycleOwner onCreate")
            }

            override fun onStart(owner: LifecycleOwner) {
                Timber.i("ProcessLifecycleOwner onStart")
            }

            override fun onResume(owner: LifecycleOwner) {
                Timber.i("ProcessLifecycleOwner onResume")
            }

            override fun onPause(owner: LifecycleOwner) {
                Timber.i("ProcessLifecycleOwner onPause")
            }

            override fun onStop(owner: LifecycleOwner) {
                Timber.i("ProcessLifecycleOwner onStop")
            }

            override fun onDestroy(owner: LifecycleOwner) {
                Timber.i("ProcessLifecycleOwner onDestroy")
            }
        })


        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacksAdapter() {
            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                if (activity == null) {
                    return
                }
                if (activity is MainActivity) {
                    //status和界面中的布局覆盖布局，界面中加了fitWindow,有padding效果，覆盖白字
                    StatusBarCompat.translucentStatusBar(activity.window, true)
                    LightStatusBarCompat.setLightStatusBar(activity.window, false)
                } else {
                    //status和界面中的布局线性布局，白低黑字
                    StatusBarCompat.setStatusBarColor(activity.window, ContextCompat.getColor(activity, R.color.white))
                    LightStatusBarCompat.setLightStatusBar(activity.window, true)
                }
            }
        })


    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return object : AndroidInjector<Any> {
            override fun inject(data: Any?) {
                if (data is MvpBaseActivity<*>) {
                    val mvpBaseActivity = data

                    //调用IModule中的对应action
                    CC.obtainBuilder(mvpBaseActivity.getModuleName())
                            .setContext(mvpBaseActivity)
                            .setActionName(IModule.INJECT_ACTIVITY)
                            .build()
                            .call()
                } else {
                    val mvpBaseFragment = data as MvpBaseFragment<*>

                    //调用IModule中的对应action
                    CC.obtainBuilder(mvpBaseFragment.getModuleName())
                            .setContext(mvpBaseFragment.context)
                            .addParam("fragment", mvpBaseFragment.tag)
                            .setActionName(IModule.INJECT_FRAGMENT)
                            .build()
                            .call()
                }
            }
        }
    }
}
