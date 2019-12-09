package com.llj.architecturedemo

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.multidex.MultiDex
import com.billy.cc.core.component.CC
import com.didichuxing.doraemonkit.DoraemonKit
import com.llj.architecturedemo.ui.activity.MainActivity
import com.llj.component.service.IModule
import com.llj.component.service.MiddleApplication
import com.llj.component.service.arouter.CJump
import com.llj.component.service.arouter.CRouter
import com.llj.lib.base.AppManager
import com.llj.lib.base.MvpBaseActivity
import com.llj.lib.base.MvpBaseFragment
import com.llj.lib.base.config.JumpConfig
import com.llj.lib.base.config.ToolbarConfig
import com.llj.lib.base.config.UserInfoConfig
import com.llj.lib.base.listeners.ActivityLifecycleCallbacksAdapter
import com.llj.lib.jump.api.JumpHelp
import com.llj.lib.statusbar.LightStatusBarCompat
import com.llj.lib.statusbar.StatusBarCompat
import com.llj.lib.tracker.Tracker
import com.llj.lib.tracker.TrackerConfig
import com.llj.lib.webview.manager.IWebViewClient
import com.llj.lib.webview.manager.WebViewConfig
import com.llj.lib.webview.manager.WebViewManager
import com.llj.socialization.SocialConstants
import com.llj.socialization.init.SocialConfig
import com.llj.socialization.init.SocialManager
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.smtt.sdk.WebView
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


    override fun onCreate() {
        //设置共用的toolbar
        val toolbarConfig = ToolbarConfig()
        toolbarConfig.leftImageRes = R.drawable.service_icon_back
        AppManager.getInstance().toolbarConfig = toolbarConfig
        //跳转配置
        val jumpConfig = JumpConfig()
        jumpConfig.nativeScheme = CJump.SCHEME
        jumpConfig.loadingPath = CRouter.APP_LOADING_ACTIVITY
        jumpConfig.loginPath = CRouter.LOGIN_LOGIN_ACTIVITY
        AppManager.getInstance().jumpConfig = jumpConfig
        //用户信息配置
        val userInfoConfig = UserInfoConfig()
        userInfoConfig.isLogin = false
        AppManager.getInstance().userInfoConfig = userInfoConfig

        //WebView配置
        val webViewConfig = WebViewConfig()
        webViewConfig.scheme = CJump.SCHEME
        webViewConfig.iWebViewClient = object : IWebViewClient {
            override fun shouldOverrideUrlLoading(webView: WebView?, s: String?): Boolean {
                if (s != null && s.startsWith(webViewConfig.scheme)) {

                }

                return false
            }
        }
        WebViewManager.getInstance().webViewConfig = webViewConfig
        super.onCreate()
    }

    override fun injectApp() {
        DoraemonKit.install(this)
        //设置日志
        Timber.plant(object : Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                if (BuildConfig.DEBUG) {
                    super.log(priority, tag, message, t)
                }
            }
        })
        //设置埋点
        Tracker.init(this, TrackerConfig.Builder().build())
        //页面跳转
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

        //Crash记录
        CrashReport.initCrashReport(applicationContext, "a0ed9c00ad", false)

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacksAdapter() {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                if (activity is MainActivity) {
                    //status和界面中的布局覆盖布局，界面中加了fitWindow,有padding效果，覆盖白字
                    StatusBarCompat.translucentStatusBar(activity.window, true)
                    LightStatusBarCompat.setLightStatusBar(activity.window, false)
                } else {
                    //status和界面中的布局线性布局，白低黑字
//                    StatusBarCompat.setStatusBarColor(activity.window, ContextCompat.getColor(activity, R.color.white))
                    StatusBarCompat.translucentStatusBar(activity.window, true)
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
