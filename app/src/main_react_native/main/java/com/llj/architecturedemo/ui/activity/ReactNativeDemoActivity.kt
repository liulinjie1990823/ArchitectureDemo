package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.facebook.react.PackageList
import com.facebook.react.ReactInstanceManager
import com.facebook.react.ReactPackage
import com.facebook.react.ReactRootView
import com.facebook.react.common.LifecycleState
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import com.facebook.soloader.SoLoader
import com.llj.application.router.CRouter
import com.llj.architecturedemo.BuildConfig
import com.llj.architecturedemo.MainMvcBaseActivity
import com.llj.architecturedemo.databinding.ActivityReactNativeDemoBinding


/**
 * describe
 *
 * @author
 * @date
 */
@Route(path = CRouter.APP_REACT_NATIVE_DEMO_ACTIVITY)
class ReactNativeDemoActivity : MainMvcBaseActivity<ActivityReactNativeDemoBinding>(), DefaultHardwareBackBtnHandler {

  private lateinit var mReactRootView: ReactRootView
  private var mReactInstanceManager: ReactInstanceManager? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    SoLoader.init(this, false)
    mReactRootView = ReactRootView(this)
    val packages: List<ReactPackage> = PackageList(application).packages
    // 有一些第三方可能不能自动链接，对于这些包我们可以用下面的方式手动添加进来：
    // packages.add(new MyReactNativePackage());
    // 同时需要手动把他们添加到`settings.gradle`和 `app/build.gradle`配置文件中。
    mReactInstanceManager = ReactInstanceManager.builder()
        .setApplication(application)
        .setCurrentActivity(this)
        .setBundleAssetName("index.android.bundle")
        .setJSMainModulePath("index")
        .addPackages(packages)
        .setUseDeveloperSupport(BuildConfig.DEBUG)
        .setInitialLifecycleState(LifecycleState.RESUMED)
        .build()
    // 注意这里的MyReactNativeApp 必须对应"index.js"中的
    // "AppRegistry.registerComponent()"的第一个参数
    mReactRootView.startReactApplication(mReactInstanceManager, "MyReactNativeApp", null)
//    setContentView(mReactRootView)

    mViewBinder.root.addView(mReactRootView)
  }


  override fun initViews(savedInstanceState: Bundle?) {
  }

  override fun initData() {
  }

  override fun invokeDefaultOnBackPressed() {
    super.onBackPressed()
  }

  override fun onPause() {
    super.onPause()
    if (mReactInstanceManager != null) {
      mReactInstanceManager!!.onHostPause(this)
    }
  }

  override fun onResume() {
    super.onResume()
    if (mReactInstanceManager != null) {
      mReactInstanceManager!!.onHostResume(this, this)
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    if (mReactInstanceManager != null) {
      mReactInstanceManager!!.onHostDestroy(this)
    }
    if (mReactRootView != null) {
      mReactRootView.unmountReactApplication()
    }
  }

  override fun onBackPressed() {
    if (mReactInstanceManager != null) {
      mReactInstanceManager!!.onBackPressed()
    } else {
      super.onBackPressed()
    }
  }
}