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
 * @author liulj
 * @date 2018/4/25
 */
abstract class BaseApplication : Application() {
  val mTagLog: String = this.javaClass.simpleName


  @CallSuper
  override fun onCreate() {
    super.onCreate()
  }

  protected open fun isDebug(): Boolean {
    return false
  }

  //debug下启用
  protected open fun initStrictMode() {
    if (isDebug()) {
      //设置线程策略
      StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build())
      //设置虚拟机策略
      StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build())
    }

  }

}
