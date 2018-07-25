package com.llj.lib.base

import android.app.Dialog
import android.arch.lifecycle.Lifecycle
import android.content.Context
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.util.ArrayMap
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import butterknife.ButterKnife
import butterknife.Unbinder
import com.facebook.stetho.common.LogUtil
import com.llj.lib.base.mvp.IPresenter
import com.llj.lib.base.widget.LoadingDialog
import com.llj.lib.net.observer.ITag
import dagger.android.AndroidInjection
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/15
 */
abstract class MvpBaseActivity<P : IPresenter> : AppCompatActivity(),
        IBaseActivity, ICommon, IUiHandler, IEvent, ILoadingDialogHandler, ITask {
    lateinit var mTag: String
    lateinit var mContext: Context

    @Inject lateinit var mPresenter: P
    private lateinit var mUnbinder: Unbinder
    private var mRequestDialog: ITag? = null

    private val mCancelableTask: ArrayMap<Any, Disposable> = ArrayMap()

    //<editor-fold desc="生命周期">
    override fun onCreate(savedInstanceState: Bundle?) {
        mContext = this
        mTag = this.javaClass.simpleName

        try {
            AndroidInjection.inject(this)
        } catch (e: Exception) {
        }

        super.onCreate(savedInstanceState)

        addCurrentActivity(this)

        getIntentData(intent)

        val layoutView = layoutView()
        if (layoutView == null) setContentView(layoutId()) else setContentView(layoutView)

        mUnbinder = ButterKnife.bind(this)

        checkRequestDialog()

        initLifecycleObserver(lifecycle)

        register(this)

        initViews(savedInstanceState)

        initData()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()

        //防止窗口泄漏，关闭dialog同时结束相关请求

        val requestDialog = getLoadingDialog() as Dialog?
        if (requestDialog != null && requestDialog.isShowing) {
            requestDialog.cancel()
        }

        //注销事件总线
        unregister(this)

        //移除所有的任务
        removeAllDisposable()

        mUnbinder.unbind()

        mPresenter.destroy()

        //移除列表中的activity
        removeCurrentActivity(this)
    }
    //</editor-fold >

    //<editor-fold desc="任务处理">
    override fun addDisposable(tag: Any, disposable: Disposable) {
        mCancelableTask.put(tag, disposable)
    }

    override fun removeDisposable(tag: Any?) {
        val disposable = mCancelableTask[tag] ?: return

        if (!disposable.isDisposed) {
            disposable.dispose()
            mCancelableTask.remove(tag)
        }
    }

    override fun removeAllDisposable() {
        if (mCancelableTask.isEmpty) {
            return
        }
        val keys = mCancelableTask.keys
        for (apiKey in keys) {
            removeDisposable(apiKey)
        }
    }
    //</editor-fold >

    //<editor-fold desc="事件总线">
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: BaseEvent) {
    }
    //</editor-fold >

    //<editor-fold desc="IBaseActivity">
    override fun initLifecycleObserver(lifecycle: Lifecycle) {
        //将mPresenter作为生命周期观察者添加到lifecycle中
        if (mPresenter != null) {
            lifecycle.addObserver(mPresenter!!)
        }
    }

    override fun superOnBackPressed() {
        super.onBackPressed()
    }

    override fun backToLauncher(nonRoot: Boolean) {
        moveTaskToBack(nonRoot)
    }
    //</editor-fold >

    //<editor-fold desc="ILoadingDialogHandler">
    override fun getLoadingDialog(): ITag? {
        return mRequestDialog
    }

    override fun initLoadingDialog(): ITag? {
        return null
    }

    private fun checkRequestDialog() {
        if (mRequestDialog == null) {
            mRequestDialog = initLoadingDialog()

            if (mRequestDialog == null) {
                mRequestDialog = LoadingDialog(this)
            }
            (mRequestDialog as Dialog).setOnCancelListener { dialog ->
                LogUtil.i(mTag, "cancelTask:" + mRequestDialog?.getRequestTag())
                removeDisposable(mRequestDialog?.getRequestTag())
            }
        }
        setRequestTag(hashCode())
    }

    //如果该RequestDialog和请求关联就设置tag
    override fun setRequestTag(tag: Any) {
        getLoadingDialog()?.setRequestTag(tag)
    }

    override fun getRequestTag(): Any {
        return getLoadingDialog()?.getRequestTag() ?: -1
    }
    //</editor-fold >

    //<editor-fold desc="处理点击外部影藏输入法">
    override fun onTouchEvent(event: MotionEvent): Boolean {
        onTouchEvent(this, event)
        return super<AppCompatActivity>.onTouchEvent(event)
    }
    //</editor-fold >

}
