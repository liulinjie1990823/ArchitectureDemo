package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.llj.application.router.CRouterClassName
import com.llj.architecturedemo.MainMvcBaseActivity
import com.llj.architecturedemo.databinding.ActivityActivityAnimBinding
import timber.log.Timber


@Route(path = CRouterClassName.APP_ACTIVITY_ANIM_ACTIVITY)
class ActivityAnimActivity : MainMvcBaseActivity<ActivityActivityAnimBinding>() {

  override fun initViews(savedInstanceState: Bundle?) {

    mViewBinder.tvFade.setOnClickListener {
      ARouter.getInstance().build(CRouterClassName.APP_ACTIVITY_ANIM_FADE_ACTIVITY)
          .withParcelable("Parcelable", Data())
          .navigation(this)
    }
    mViewBinder.tvSlide.setOnClickListener {
      ARouter.getInstance().build(CRouterClassName.APP_ACTIVITY_ANIM_SLIDE_ACTIVITY)
          .navigation(this)
    }

//    val packages: List<ReactPackage> = PackageList(application).getPackages()

//    ReactInstanceManager.builder()
//        .setApplication(application)
//        .setCurrentActivity(this)
//        .setBundleAssetName("index.android.bundle")
//        .setJSMainModulePath("index")
//        .addPackages(packages)
//        .setUseDeveloperSupport(BuildConfig.DEBUG)
//        .setInitialLifecycleState(LifecycleState.RESUMED)
//        .build();
  }

  override fun initData() {
  }


  override fun onPause() {
    super.onPause()
  }

  class Data() : Parcelable {

    constructor(parcel: Parcel) : this() {

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
      Timber.tag("").d("")
    }

    override fun describeContents(): Int {
      return 0
    }

    companion object CREATOR : Parcelable.Creator<Data> {
      override fun createFromParcel(parcel: Parcel): Data {
        return Data(parcel)
      }

      override fun newArray(size: Int): Array<Data?> {
        return arrayOfNulls(size)
      }
    }

  }
}