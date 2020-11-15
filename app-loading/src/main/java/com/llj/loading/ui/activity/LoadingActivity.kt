package com.llj.loading.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.llj.application.router.CJump
import com.llj.application.router.CRouter
import com.llj.component.service.permission.PermissionManager
import com.llj.lib.base.AppManager
import com.llj.lib.base.help.DisplayHelper
import com.llj.lib.image.loader.ImageLoader
import com.llj.lib.jump.annotation.Jump
import com.llj.loading.LoadingMvcBaseActivity
import com.llj.loading.R
import com.llj.loading.databinding.ActivityLoadingBinding
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit

/**
 * ArchitectureDemo.
 * describe:
 * 1. 直接打开页面，使用主题中设置的动画即可
 * 2. 使用finish关闭有很大概率动画不衔接，不推荐使用
 * 3. （推荐）如果通过设置Flags来关闭页面，仅仅在主题中设置的动画会不衔接，也需要设置.withTransition(R.anim.fade_in, R.anim.no_fade)
 * @author llj
 * @date 2018/9/20
 */
@Jump(outPath = CJump.JUMP_LOADING_ACTIVITY, inPath = CRouter.LOADING_LOADING_ACTIVITY, needLogin = true, desc = "LoadingActivity")
@Route(path = CRouter.LOADING_LOADING_ACTIVITY)
class LoadingActivity : LoadingMvcBaseActivity<ActivityLoadingBinding>() {

  private lateinit var mImageLoader: ImageLoader
  private var mDisposable: Disposable? = null

  override fun initViews(savedInstanceState: Bundle?) {

    mUseAnim = false
    mImageLoader = ImageLoader.getInstance()

    val dip2px = dip2px(this, 50f)
    mImageLoader.loadImage(mViewBinder.sdvIcon, R.mipmap.ic_launcher, dip2px, dip2px)

    mViewBinder.tvLeapfrog.setOnClickListener {

      mDisposable?.dispose()

      ARouter.getInstance().build(AppManager.getInstance().jumpConfig.mainPath)
          .withTransition(R.anim.fade_in, R.anim.no_fade)
//          .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
          .navigation(mContext)

      finish()
    }

    PermissionManager.checkPhoneStateAndStorage(this) {

      mViewBinder.sdvAdd.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver
      .OnGlobalLayoutListener {
        override fun onGlobalLayout() {
          mViewBinder.sdvAdd.viewTreeObserver.removeOnGlobalLayoutListener(this)
          val url = "http://pic34.photophoto.cn/20150112/0034034439579927_b.jpg"
          mImageLoader.loadImage(mViewBinder.sdvAdd, url, DisplayHelper.SCREEN_WIDTH, mViewBinder.sdvAdd.height)
        }
      })

      countDown()
    }
    //        countDown()
  }

  override fun initData() {
  }

  fun countDown() {
    val count: Long = 4 // 倒计时10秒
    Observable.interval(0, 1, TimeUnit.SECONDS)
        .take(count + 1)
        .map(object : Function<Long, Long> {
          override fun apply(t: Long): Long {
            return count - t
          }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : Observer<Long> {

          override fun onSubscribe(d: Disposable) {
            mDisposable = d
          }

          override fun onNext(s: Long) {
            mViewBinder.tvLeapfrog.text = "跳过${s}s"
          }

          override fun onError(e: Throwable) {
            Log.e(mTagLog, "onError:" + e.message)
          }

          override fun onComplete() {
            ARouter.getInstance().build(AppManager.getInstance().jumpConfig.mainPath)
                .withTransition(R.anim.fade_in, R.anim.no_fade)
                .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .navigation(mContext)
//            finish()
          }
        })
  }
}
