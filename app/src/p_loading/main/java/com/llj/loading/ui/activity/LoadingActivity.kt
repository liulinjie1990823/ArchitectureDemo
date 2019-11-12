package com.llj.loading.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.TextView
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.llj.lib.jump.annotation.Jump
import com.facebook.drawee.view.GenericDraweeView
import com.facebook.drawee.view.SimpleDraweeView
import com.llj.architecturedemo.R
import com.llj.component.service.MiddleMvcBaseActivity
import com.llj.component.service.arouter.CJump
import com.llj.component.service.arouter.CRouter
import com.llj.component.service.permission.PermissionManager
import com.llj.lib.base.help.DisplayHelper
import com.llj.lib.image.loader.FrescoImageLoader
import com.llj.lib.image.loader.ICustomImageLoader
import com.llj.lib.statusbar.StatusBarCompat
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
@Jump(outPath = CJump.JUMP_LOADING_ACTIVITY, inPath = CRouter.APP_LOADING_ACTIVITY, needLogin = true, desc = "LoadingActivity")
@Route(path = CRouter.APP_LOADING_ACTIVITY)
class LoadingActivity : MiddleMvcBaseActivity() {
    @BindView(R.id.sdv_add) lateinit var mSdvAdd: SimpleDraweeView
    @BindView(R.id.sdv_icon) lateinit var mSdvIcon: SimpleDraweeView
    @BindView(R.id.tv_leapfrog) lateinit var mTvLeapfrog: TextView
    @BindView(R.id.tv_app_name) lateinit var mTvAppName: TextView
    @BindView(R.id.tv_sub_title) lateinit var mTvSubTitle: TextView


    private lateinit var mImageLoader: ICustomImageLoader<GenericDraweeView>

    private lateinit var mDisposable: Disposable

    override fun layoutId(): Int {
        return R.layout.activity_loading
    }

    override fun initViews(savedInstanceState: Bundle?) {
        StatusBarCompat.translucentStatusBar(window, true)

        mUseAnim = false
        mImageLoader = FrescoImageLoader.getInstance(this.applicationContext)


        val dip2px = dip2px(this, 50f)
        mImageLoader.loadImage(R.mipmap.ic_launcher, dip2px, dip2px, mSdvIcon)

        mTvLeapfrog.setOnClickListener {
            mDisposable.dispose()

            ARouter.getInstance().build(CRouter.APP_MAIN_ACTIVITY)
                    .navigation(mContext)

            finish()
        }

        PermissionManager.checkPhoneStateAndStorage(this, object : PermissionManager.PermissionListener {
            override fun onGranted(permissions: MutableList<String>?) {

                mSdvAdd.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        mSdvAdd.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        val url = "http://pic34.photophoto.cn/20150112/0034034439579927_b.jpg"
                        mImageLoader.loadImage(url, DisplayHelper.SCREEN_WIDTH, mSdvAdd.height, mSdvAdd)
                    }
                })

                countDown()
            }
        })
        //        countDown()
    }

    override fun initData() {
    }

    fun countDown() {
        val count: Long = 3 //倒计时10秒
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
                        mTvLeapfrog.text = "跳过${s}s"
                    }

                    override fun onError(e: Throwable) {
                        Log.e(mTagLog, "onError:" + e.message)
                    }

                    override fun onComplete() {
                        ARouter.getInstance().build(CRouter.APP_MAIN_ACTIVITY)
                                .withTransition(R.anim.fade_in, R.anim.no_fade)
                                .navigation(mContext)
                        finish()
                    }
                })
    }
}