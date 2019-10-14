package com.llj.lib.base

import android.app.Dialog
import android.arch.lifecycle.Lifecycle
import android.content.Context
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.util.ArrayMap
import android.support.v4.util.ArraySet
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import butterknife.ButterKnife
import butterknife.Unbinder
import com.llj.lib.base.event.BaseEvent
import com.llj.lib.base.mvp.IBaseActivityView
import com.llj.lib.base.widget.LoadingDialog
import com.llj.lib.net.observer.ITaskId
import dagger.android.AndroidInjection
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/13
 */
abstract class MvcBaseActivity : AppCompatActivity()
        , IBaseActivity, ICommon, IUiHandler, IEvent, IBaseActivityView {

    val mTagLog: String = this.javaClass.simpleName

    lateinit var mContext: Context

    private lateinit var mUnBinder: Unbinder
    private var mRequestDialog: ITaskId? = null

    private val mCancelableTasks: ArrayMap<Int, Disposable> = ArrayMap()
    private val mDelayMessages: ArraySet<String> = ArraySet()


    override fun useEventBus(): Boolean {
        return true
    }

    //<editor-fold desc="生命周期">
    override fun onCreate(savedInstanceState: Bundle?) {
        mContext = this

        try {
            AndroidInjection.inject(this)
        } catch (e: Exception) {
        }

        super.onCreate(savedInstanceState)
        Timber.tag(mTagLog).i("onCreate：%s", mTagLog)

        addCurrentActivity(this)

        getIntentData(intent)

        val layoutView = layoutView()
        if (layoutView == null) setContentView(layoutId()) else setContentView(layoutView)

        mUnBinder = ButterKnife.bind(this)

        checkLoadingDialog()

        initLifecycleObserver(lifecycle)

        register(this)

        initViews(savedInstanceState)

        initData()
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(mTagLog).i("onStart：%s", mTagLog)
    }

    override fun onResume() {
        super.onResume()
        Timber.tag(mTagLog).i("onResume：%s", mTagLog)
        Timber.tag(mTagLog).i("mPageName：%s", mTagLog)
    }

    override fun onPause() {
        super.onPause()
        Timber.tag(mTagLog).i("onPause：%s", mTagLog)
    }

    override fun onStop() {
        super.onStop()
        Timber.tag(mTagLog).i("onStop：%s", mTagLog)
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        Timber.tag(mTagLog).i("onDestroy：%s", mTagLog)

        //防止窗口泄漏，关闭dialog同时结束相关请求
        val requestDialog = getLoadingDialog() as Dialog?
        if (requestDialog != null && requestDialog.isShowing) {
            requestDialog.cancel()
        }
        mRequestDialog = null

        //注销事件总线
        unregister(this)

        //移除所有的任务
        removeAllDisposable()

        mUnBinder.unbind()

        //移除列表中的activity
        removeCurrentActivity(this)
    }
    //</editor-fold >

    //<editor-fold desc="任务处理">
    override fun addDisposable(taskId: Int, disposable: Disposable) {
        mCancelableTasks[taskId] = disposable
    }

    override fun removeDisposable(taskId: Int?) {
        val disposable = mCancelableTasks[taskId] ?: return

        if (!disposable.isDisposed) {
            disposable.dispose()
            mCancelableTasks.remove(taskId)
        }
    }

    override fun removeAllDisposable() {
        if (mCancelableTasks.isEmpty) {
            return
        }
        val keys = mCancelableTasks.keys
        for (apiKey in keys) {
            removeDisposable(apiKey)
        }
    }
    //</editor-fold >

    //<editor-fold desc="事件总线">

    //限定界面
    protected fun <T> inCurrentPage(event: BaseEvent<T>): Boolean {
        return mTagLog == event.data
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun <T> onEvent(event: BaseEvent<T>) {
        if (!isEmpty(event.delayMessage)) {
            //延迟消息
            mDelayMessages.add(event.delayMessage)
        } else {
            if ("refresh" == event.message && inCurrentPage(event)) {
                //限定界面
                initData()
            } else {
                onReceiveEvent(event)
            }
        }
    }

    override fun <T : Any?> onReceiveEvent(event: BaseEvent<T>) {
    }
    //</editor-fold >

    //<editor-fold desc="IBaseActivity">
    override fun initLifecycleObserver(lifecycle: Lifecycle) {
        //将mPresenter作为生命周期观察者添加到lifecycle中
    }

    override fun superOnBackPressed() {
        super.onBackPressed()
    }

    override fun backToLauncher(nonRoot: Boolean) {
        moveTaskToBack(nonRoot)
    }
    //</editor-fold >

    //<editor-fold desc="ILoadingDialogHandler">
    override fun getLoadingDialog(): ITaskId? {
        return mRequestDialog
    }

    //自定义实现
    override fun initLoadingDialog(): ITaskId? {
        return null
    }

    private fun checkLoadingDialog() {
        if (mRequestDialog == null) {
            mRequestDialog = initLoadingDialog()

            if (mRequestDialog == null) {
                mRequestDialog = LoadingDialog(this)
            }
        }
        setRequestId(hashCode())
    }

    //如果该RequestDialog和请求关联就设置tag
    override fun setRequestId(taskId: Int) {
        getLoadingDialog()?.setRequestId(taskId)
    }

    override fun getRequestId(): Int {
        return getLoadingDialog()?.getRequestId() ?: -1
    }
    //</editor-fold >

    //<editor-fold desc="处理点击外部影藏输入法">
    override fun onTouchEvent(event: MotionEvent): Boolean {
        onTouchEvent(this, event)
        return super<AppCompatActivity>.onTouchEvent(event)
    }
    //</editor-fold >

}