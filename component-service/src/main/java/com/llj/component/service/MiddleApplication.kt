package com.llj.component.service

import android.content.Context
import android.content.pm.ApplicationInfo
import androidx.annotation.CallSuper
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.FileUtils
import com.facebook.common.memory.manager.NoOpDebugMemoryManager
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.perflogger.NoOpFlipperPerfLogger
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.databases.impl.SqliteDatabaseDriver
import com.facebook.flipper.plugins.databases.impl.SqliteDatabaseProvider
import com.facebook.flipper.plugins.fresco.FrescoFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.sandbox.SandboxFlipperPlugin
import com.facebook.flipper.plugins.sandbox.SandboxFlipperPluginStrategy
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.imagepipeline.backends.okhttp3.OkHttpNetworkFetcher
import com.facebook.imagepipeline.debug.FlipperImageTracker
import com.llj.component.service.imageLoader.FrescoUtils
import com.llj.component.service.preference.UserInfoPreference
import com.llj.component.service.vo.UserInfoVo
import com.llj.lib.base.BaseApplication
import skin.support.SkinCompatManager
import skin.support.app.SkinCardViewInflater
import skin.support.constraint.app.SkinConstraintViewInflater
import skin.support.design.app.SkinMaterialViewInflater
import java.io.File


/**
 * ArchitectureDemo
 * describe:
 * author llj
 * date 2018/7/3
 */
abstract class MiddleApplication : BaseApplication() {

    lateinit var mMiddleComponent: MiddleComponent

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

        mMiddleComponent = DaggerMiddleComponent.builder()
                .application(this)
                .build()

        if (BuildConfig.DEBUG) {   // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog()     // Print log
            ARouter.openDebug()   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this)

        super.onCreate()

        //x5内核初始化接口
        //        QbSdk.initX5Environment(applicationContext, object : QbSdk.PreInitCallback {
        //            override fun onViewInitFinished(arg0: Boolean) {
        //                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
        //                Log.e("MiddleApplication", " onViewInitFinished is $arg0")
        //            }
        //
        //            override fun onCoreInitFinished() {
        //            }
        //        })


        initUserInfo(null)
    }


    override fun isDebug(): Boolean {
        return applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
    }

    override fun initImageLoader() {
        FrescoUtils.initFresco(this.applicationContext, OkHttpNetworkFetcher(mMiddleComponent.okHttpClient()));
    }

    override fun initFlipper() {
        if (!isDebug()) {
            return
        }

        if (FlipperUtils.shouldEnableFlipper(this)) {
            val client = AndroidFlipperClient.getInstance(this)
            //布局查看
            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
            //图片加载
            client.addPlugin(FrescoFlipperPlugin(
                    FlipperImageTracker(),
                    Fresco.getImagePipelineFactory().platformBitmapFactory,
                    null,
                    NoOpDebugMemoryManager(),
                    NoOpFlipperPerfLogger(),
                    null,
                    null))
            //沙盒
            client.addPlugin(SandboxFlipperPlugin(object : SandboxFlipperPluginStrategy {
                override fun getKnownSandboxes(): MutableMap<String, String> {
                    return HashMap()
                }

                override fun setSandbox(sandbox: String?) {
                }

            }))
            //数据库
            client.addPlugin(DatabasesFlipperPlugin(SqliteDatabaseDriver(this, object : SqliteDatabaseProvider {
                override fun getDatabaseFiles(): MutableList<File> {
                    val databaseFiles = ArrayList<File>()
                    for (item in applicationContext.databaseList()) {
                        databaseFiles.add(applicationContext.getDatabasePath(item))
                    }
                    return databaseFiles
                }
            })))
            //文件操作
            val descriptors = ArrayList<SharedPreferencesFlipperPlugin.SharedPreferencesDescriptor>()
            val listFilesInDir = FileUtils.listFilesInDir(cacheDir.parentFile.absolutePath + File.separator + "shared_prefs")
            for (file in listFilesInDir) {
                descriptors.add(SharedPreferencesFlipperPlugin.SharedPreferencesDescriptor(file.name.replace(".xml",""), Context.MODE_PRIVATE))
            }
            client.addPlugin(SharedPreferencesFlipperPlugin(this, descriptors))
            //崩溃统计
            client.addPlugin(CrashReporterPlugin.getInstance())
            client.start()
        }
    }

    override fun initLeakCanary() {
    }

    override fun initStrictMode() {
    }
}
