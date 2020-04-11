package com.llj.lib.base

import android.app.Application
import android.os.StrictMode
import androidx.annotation.CallSuper
import com.llj.lib.base.help.CrashHelper
import com.llj.lib.base.help.DisplayHelper
import com.llj.lib.base.help.FilePathHelper
import com.llj.lib.utils.AActivityManagerUtils
import com.llj.lib.utils.AToastUtils
import com.llj.lib.utils.LogUtil
import com.llj.lib.utils.helper.Utils
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/4/25
 */
abstract class BaseApplication : Application(),
        HasAndroidInjector {
    val mTagLog: String = this.javaClass.simpleName

    @Inject
    lateinit var mActivityInjector: DispatchingAndroidInjector<Any>


    @CallSuper
    override fun onCreate() {
        super.onCreate()
        Utils.init(this)

        initDisplay() // 初始化屏幕宽高信息
        initSavePath() // 初始化文件存储路径

        initImageLoader() //图片加载器
        initToast() //全局toast初始化

        initCrashHandler() //异常捕捉

        if (AActivityManagerUtils.isRunningProcess(this)) {

            initLeakCanary() //监听内存溢出
            initFlipper() //设置okhttp请求调试
            initStrictMode() //设置严格模式

        }
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
        if (isDebug()) {
            CrashHelper.getInstance().init(this) { LogUtil.LLJe(it) }
        }
    }

    protected open fun initFlipper() {
    }

    protected open fun initLeakCanary() {
    }

    protected open fun initStrictMode() {
        if (isDebug()) {
            //设置线程策略
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build())
            //设置虚拟机策略
            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build())
        }

    }


    override fun androidInjector(): AndroidInjector<Any> {
        return mActivityInjector
    }
}
