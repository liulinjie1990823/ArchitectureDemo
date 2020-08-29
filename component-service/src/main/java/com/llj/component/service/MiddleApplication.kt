package com.llj.component.service

import android.content.pm.ApplicationInfo
import androidx.annotation.CallSuper
import com.alibaba.android.arouter.launcher.ARouter
import com.facebook.imagepipeline.backends.okhttp3.OkHttpNetworkFetcher
import com.llj.component.service.preference.UserInfoPreference
import com.llj.component.service.utils.BuildTypeUtil
import com.llj.component.service.vo.UserInfoVo
import com.llj.lib.base.BaseApplication
import com.llj.lib.image.loader.engine.fresco.FrescoUtils
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
abstract class MiddleApplication : BaseApplication() {

//  lateinit var mMiddleComponent: MiddleComponent

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

//    mMiddleComponent = DaggerMiddleComponent.builder()
//        .application(this)
//        .build()

    //页面路由
    if (isDebug()) {   // These two lines must be written before init, otherwise these configurations will be invalid in the init process
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

  }

  override fun initCrashHandler() {
  }

  override fun initFlipper() {
    BuildTypeUtil.initFlipper(this)
  }

}
