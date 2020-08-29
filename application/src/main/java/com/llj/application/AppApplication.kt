package com.llj.application

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.multidex.MultiDex
import com.billy.cc.core.component.CC
import com.didichuxing.doraemonkit.DoraemonKit
import com.facebook.imagepipeline.backends.okhttp3.OkHttpNetworkFetcher
import com.llj.application.di.AppComponent
import com.llj.application.di.DaggerAppComponent
import com.llj.application.di.IModule
import com.llj.component.service.MiddleApplication
import com.llj.component.service.arouter.CJump
import com.llj.component.service.arouter.CRouter
import com.llj.lib.base.AppManager
import com.llj.lib.base.MvcBaseActivity
import com.llj.lib.base.MvpBaseActivity
import com.llj.lib.base.MvpBaseFragment
import com.llj.lib.base.config.JumpConfig
import com.llj.lib.base.config.ToolbarConfig
import com.llj.lib.base.config.UserInfoConfig
import com.llj.lib.base.listeners.ActivityLifecycleCallbacksAdapter
import com.llj.lib.image.loader.ImageLoader
import com.llj.lib.image.loader.engine.fresco.FrescoEngine
import com.llj.lib.image.loader.engine.fresco.FrescoUtils
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
 * @author llj
 * @date 2018/5/18
 */
open class AppApplication : MiddleApplication() {

  lateinit var mAppComponent: AppComponent

  override fun initStrictMode() {
  }

  override fun attachBaseContext(base: Context?) {
    super.attachBaseContext(base)
    MultiDex.install(this)
  }

  override fun onCreate() {
    mAppComponent = DaggerAppComponent.builder()
        .application(this)
        .build()

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
    //设置日志
    Timber.plant(object : Timber.DebugTree() {
      override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (isDebug()) {
          super.log(priority, tag, message, t)
        }
      }
    })
    //设置埋点
    Tracker.init(this, TrackerConfig.Builder().build())
    //页面跳转
    JumpHelp.init(this)

    //调用LoginComponent中的dagger组件
    CC.obtainBuilder("app").setActionName(IModule.INIT).build().callAsync()
    CC.obtainBuilder("app-login").setActionName(IModule.INIT).build().callAsync()
    CC.obtainBuilder("app-setting").setActionName(IModule.INIT).build().callAsync()

    //分享
    val config = SocialConfig.Builder(this, true).qqId(getString(R.string.qq_id))
        .wx(getString(R.string.wx_id), getString(R.string.wx_secret))
        .sign(getString(R.string.sina_id), getString(R.string.sina_url), SocialConstants.SCOPE)
        .build()
    SocialManager.init(config)

    //bugly的Crash记录
    CrashReport.initCrashReport(applicationContext, getString(R.string.bugly_id), false)


    //设置状态栏监听
    registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacksAdapter() {
      override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        val simpleName = activity.javaClass.simpleName
        if ("MainActivity" == simpleName) {
          //status和界面中的布局覆盖布局，界面中加了fitWindow,有padding效果，覆盖白字
          StatusBarCompat.translucentStatusBar(activity.window, true)
          LightStatusBarCompat.setLightStatusBar(activity.window, false)
        } else if (simpleName != "KeyboardStateActivity") {
          //status和界面中的布局线性布局，白低黑字
          StatusBarCompat.translucentStatusBar(activity.window, true)
          LightStatusBarCompat.setLightStatusBar(activity.window, true)

        }
      }
    })

    DoraemonKit.install(this)
  }

  override fun initImageLoader() {
    super.initImageLoader()
    FrescoUtils.initFresco(this.applicationContext, OkHttpNetworkFetcher(mAppComponent.okHttpClient()));
    //图片加载引擎
    ImageLoader.addImageLoadEngine(0, FrescoEngine())
  }

  override fun androidInjector(): AndroidInjector<Any> {
    return object : AndroidInjector<Any> {
      override fun inject(data: Any?) {
        when (data) {
          is MvpBaseActivity<*, *> -> {
            //调用IModule中的对应action
            CC.obtainBuilder(data.getModuleName())
                .setContext(data)
                .setActionName(IModule.INJECT_ACTIVITY)
                .build()
                .call()
          }
          is MvcBaseActivity<*> -> {
            //调用IModule中的对应action
            CC.obtainBuilder(data.getModuleName())
                .setContext(data)
                .setActionName(IModule.INJECT_ACTIVITY)
                .build()
                .call()
          }
          is MvpBaseFragment<*, *> -> {
            //调用IModule中的对应action
            CC.obtainBuilder(data.getModuleName())
                .setContext(data.context)
                .setActionName(IModule.INJECT_FRAGMENT)
                .build()
                .call()

            CC.obtainBuilder(data.getModuleName())
                .setContext(data.context)
                .addParam("fragment", data.tag)
                .setActionName(IModule.INJECT_FRAGMENT)
                .build()
                .call()
          }
        }
      }
    }
  }
}
