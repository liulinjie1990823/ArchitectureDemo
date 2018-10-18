package com.llj.architecturedemo

import android.app.Activity
import android.arch.lifecycle.DefaultLifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ProcessLifecycleOwner
import android.content.Context
import android.os.Bundle
import android.support.multidex.MultiDex
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.billy.cc.core.component.CC
import com.llj.component.service.ComponentApplication
import com.llj.component.service.statusbar.LightStatusBarCompat
import com.llj.component.service.statusbar.StatusBarCompat
import com.llj.lib.base.MvpBaseActivity
import com.llj.lib.base.MvpBaseFragment
import com.llj.lib.base.listeners.ActivityLifecycleCallbacksAdapter
import com.llj.socialization.SocialConstants
import com.llj.socialization.share.SocialConfig
import com.llj.socialization.share.SocialManager
import dagger.android.AndroidInjector


/**
 * ArchitectureDemo
 * describe:
 * author llj
 * date 2018/5/18
 */
class AppApplication : ComponentApplication() {
    private lateinit var mAppComponent: AppComponent

    override fun injectApp() {
        mAppComponent = DaggerAppComponent.builder()
                .component(mComponent)
                .build()

        //调用LoginComponent中的dagger组件
        CC.obtainBuilder("LoginModule")
                .setActionName("init")
                .build()
                .call()

        val config = SocialConfig.instance().qqId("1103566659")
                .wx("wx78b27fadc81b6df4", "022fa45d435d7845179b6ae8d1912690")
                .sign("1476913513", "http://www.jiehun.com.cn/api/weibo/_grant", SocialConstants.SCOPE)
        SocialManager.init(config)


        ProcessLifecycleOwner.get().lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
            }

            override fun onStart(owner: LifecycleOwner) {
            }

            override fun onResume(owner: LifecycleOwner) {
            }

            override fun onPause(owner: LifecycleOwner) {
            }

            override fun onStop(owner: LifecycleOwner) {
            }

            override fun onDestroy(owner: LifecycleOwner) {
            }
        })


        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacksAdapter() {
            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                if (activity == null) {
                    return
                }
                StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.white))
                LightStatusBarCompat.setLightStatusBar(activity.window, true)
            }

        })

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return AndroidInjector { activity ->
            val mvpBaseActivity = activity as MvpBaseActivity<*>

            if ("app" == mvpBaseActivity.moduleName()) {
                //主工程
                mAppComponent.activityInjector().inject(activity)
            } else if ("login" == mvpBaseActivity.moduleName()) {
                CC.obtainBuilder("LoginModule")
                        .setContext(activity)
                        .setActionName("injectActivity")
                        .build()
                        .call()
            }
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return AndroidInjector { fragment ->
            val mvpBaseFragment = fragment as MvpBaseFragment<*>

            if ("app" == mvpBaseFragment.moduleName()) {
                //主工程
                mAppComponent.supportFragmentInjector().inject(fragment)
            } else if ("login" == mvpBaseFragment.moduleName()) {
                CC.obtainBuilder("LoginModule")
                        .setContext(fragment.context)
                        .addParam("fragment",fragment.tag)
                        .setActionName("injectFragment")
                        .build()
                        .call()
            }
        }
    }

}
