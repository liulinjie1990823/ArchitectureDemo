package com.llj.architecturedemo

import android.util.Log
import com.llj.application.AppApplication
import timber.log.Timber

/**
 * ArchitectureDemo. describe:
 *
 * @author llj
 * @date 2020/4/29
 */
class MainApp : AppApplication() {

  companion object {
    var TAG = "MainApp"
  }


  private var mStartTime: Long = 0

  override fun onCreate() {
    Log.e("AppInit", "MainApp start")
    mStartTime = System.currentTimeMillis()
    super.onCreate()

    val diff = System.currentTimeMillis() - mStartTime
    Timber.tag("AppInit").e("MainApp start up %d ms", diff)
  }


}