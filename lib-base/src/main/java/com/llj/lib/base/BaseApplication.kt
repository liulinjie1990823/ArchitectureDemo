package com.llj.lib.base

import android.app.Activity
import android.app.Application
import android.app.Service
import android.os.StrictMode
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import com.llj.lib.base.help.CrashHelper
import com.llj.lib.base.help.DisplayHelper
import com.llj.lib.base.help.FilePathHelper
import com.llj.lib.utils.AActivityManagerUtils
import com.llj.lib.utils.AToastUtils
import com.llj.lib.utils.LogUtil
import com.llj.lib.utils.helper.Utils
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/4/25
 */
abstract class BaseApplication : Application(),
        HasActivityInjector,
        HasServiceInjector,
        HasSupportFragmentInjector {
    val mTagLog: String = this.javaClass.simpleName

    @Inject
    lateinit var mActivityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var mSupportFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var mServiceInjector: DispatchingAndroidInjector<Service>


    @CallSuper
    override fun onCreate() {
        super.onCreate()
        if (AActivityManagerUtils.isRunningProcess(this)) {
            Utils.init(this)
            initDisplay() // 初始化屏幕宽高信息
            initSavePath() // 初始化文件存储路径

            initImageLoader() //图片加载器
            initToast() //全局toast初始化

            initCrashHandler() //异常捕捉
            initStetho() //设置okhttp请求调试
            initLeakCanary() //监听内存溢出
            initStrictMode() //设置严格模式
        }
        injectApp()
    }

    private fun initDisplay() {
        DisplayHelper.init(this)
    }

    private fun initSavePath() {
        FilePathHelper.init(this)
    }

    protected open fun isDebug(): Boolean {
        return false
    }

    protected open fun initImageLoader() {
    }


    protected open fun initToast() {
        AToastUtils.init()
    }


    protected open fun initCrashHandler() {
        if (!isDebug()) {
            return
        }
        CrashHelper.getInstance().init(this) { LogUtil.LLJe(it) }
    }

    protected open fun initStetho() {
    }

    protected open fun initLeakCanary() {
    }

    protected open fun initStrictMode() {
        if (!isDebug()) {
            return
        }
        //设置线程策略
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build())
        //设置虚拟机策略
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build())
    }

    protected abstract fun injectApp()

    ///////////////////////////////////////////////////////////////////////////
    // Dependencies Injection by dagger.android
    ///////////////////////////////////////////////////////////////////////////

    override fun activityInjector(): AndroidInjector<Activity>? {
        return mActivityInjector
    }

    override fun serviceInjector(): AndroidInjector<Service> {
        return mServiceInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return mSupportFragmentInjector
    }
}
