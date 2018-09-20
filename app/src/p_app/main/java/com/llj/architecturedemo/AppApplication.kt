package com.llj.architecturedemo

import android.app.Activity
import android.content.Context
import android.support.multidex.MultiDex
import com.billy.cc.core.component.CC
import com.llj.component.service.ComponentApplication
import com.llj.lib.base.MvpBaseActivity
import com.llj.socialization.SocialConstants
import com.llj.socialization.share.SocialConfig
import com.llj.socialization.share.SocialManager
import dagger.android.AndroidInjector


/**
 * ArchitectureDemo
 * describe:
 * author liulj
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
                        .setActionName("inject")
                        .build()
                        .call()
            }
        }
    }
}
