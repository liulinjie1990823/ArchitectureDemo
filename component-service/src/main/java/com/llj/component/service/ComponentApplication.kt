package com.llj.component.service

import android.support.annotation.CallSuper
import com.alibaba.android.arouter.launcher.ARouter
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.leakcanary.LeakCanaryFlipperPlugin
import com.facebook.flipper.plugins.leakcanary.RecordLeakService
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.llj.component.service.preference.UserInfoPreference
import com.llj.component.service.vo.UserInfoVo
import com.llj.lib.base.BaseApplication
import com.llj.lib.image.loader.FrescoImageLoader
import com.squareup.leakcanary.LeakCanary
import skin.support.SkinCompatManager
import skin.support.app.SkinCardViewInflater
import skin.support.constraint.app.SkinConstraintViewInflater
import skin.support.design.app.SkinMaterialViewInflater


/**
 * ArchitectureDemo
 * describe:
 * author llj
 * date 2018/7/3
 */
abstract class ComponentApplication : BaseApplication() {

    lateinit var mComponent: Component

    companion object {
        lateinit var mUserInfoVo: UserInfoVo //用户信息

        fun initUserInfo(userInfo: UserInfoVo?) {
            UserInfoPreference.getInstance().saveUserInfo(userInfo)
            mUserInfoVo = UserInfoPreference.getInstance().getUserInfo()
        }
    }

    @CallSuper
    override fun onCreate() {
        SkinCompatManager.withoutActivity(this)                         // 基础控件换肤初始化
                .addInflater(SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
                .addInflater(SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
                .addInflater(SkinCardViewInflater())                // CardView v7 控件换肤初始化[可选]
                .setSkinStatusBarColorEnable(false)                     // 关闭状态栏换肤，默认打开[可选]
                .setSkinWindowBackgroundEnable(false)                   // 关闭windowBackground换肤，默认打开[可选]
                .loadSkin()

        mComponent = DaggerComponent.builder()
                .application(this)
                .build()

        super.onCreate()

        if (BuildConfig.DEBUG) {   // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog()     // Print log
            ARouter.openDebug()   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this)

        //x5内核初始化接口
        //        QbSdk.initX5Environment(applicationContext, object : QbSdk.PreInitCallback {
        //            override fun onViewInitFinished(arg0: Boolean) {
        //                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
        //                Log.e("ComponentApplication", " onViewInitFinished is $arg0")
        //            }
        //
        //            override fun onCoreInitFinished() {
        //            }
        //        })


        initUserInfo(null)
    }


    override fun isDebug(): Boolean {
        return BuildConfig.DEBUG
    }

    override fun initImageLoader() {
        super.initImageLoader()
        FrescoImageLoader.getInstance(this.applicationContext, mComponent.okHttpClient())
    }

    override fun initStetho() {
        super.initStetho()
        if (!isDebug()) {
            return
        }
        //        val build = Stetho.newInitializerBuilder(this)
        //                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
        //                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
        //                .build()
        //        Stetho.initialize(build)

        if (FlipperUtils.shouldEnableFlipper(this)) {
            val client = AndroidFlipperClient.getInstance(this)
            //布局查看
            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
            //网络
            client.addPlugin(NetworkFlipperPlugin())
            //内存管理
            client.addPlugin(LeakCanaryFlipperPlugin())
            //文件操作
            client.addPlugin(SharedPreferencesFlipperPlugin(this, UserInfoPreference.FILE_NAME))
            //崩溃统计
            client.addPlugin(CrashReporterPlugin.getInstance())
            client.start()
        }
    }

    override fun initLeakCanary() {
        if (!isDebug()) {
            return
        }
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.refWatcher(this)
                .listenerServiceClass(RecordLeakService::class.java)
                .buildAndInstall()
//        LeakCanary.install(this)
    }

    override fun initStrictMode() {
    }
}
