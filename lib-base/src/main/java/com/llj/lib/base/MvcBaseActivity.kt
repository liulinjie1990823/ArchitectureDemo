package com.llj.lib.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import butterknife.ButterKnife
import butterknife.Unbinder
import com.llj.lib.base.event.BaseEvent
import com.llj.lib.base.mvp.IBaseActivityView
import com.llj.lib.base.widget.LoadingDialog
import dagger.android.AndroidInjection
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019/3/13
 */
abstract class MvcBaseActivity : AppCompatActivity()
        , IBaseActivity, ICommon, IUiHandler, IEvent, IBaseActivityView {

    val mTagLog: String = this.javaClass.simpleName

    lateinit var mContext: Context

    private lateinit var mUnBinder: Unbinder
    private var mRequestDialog: BaseDialog? = null

    private val mCancelableTasks: androidx.collection.ArrayMap<Int, Disposable> = androidx.collection.ArrayMap()
    private val mDelayMessages: androidx.collection.ArraySet<String> = androidx.collection.ArraySet()


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

        registerEventBus(this)

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
        unregisterEventBus(this)

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

    //<editor-fold desc="IEvent事件总线">
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun <T> onEvent(event: BaseEvent<T>) {
        if (!isEmpty(event.delayMessage)) {
            //延迟消息
            mDelayMessages.add(event.delayMessage)
        } else {
            //即时消息
            onReceiveEvent(event)
        }
    }

    @CallSuper
    override fun <T : Any?> onReceiveEvent(event: BaseEvent<T>) {
        val inCurrentPage = inCurrentPage(event)
        if (inCurrentPage) {
            if ("refresh" == event.message) {
                //刷新页面
                initData()
            } else if ("close" == event.message) {
                //关闭页面
                onBackPressed()
            }
        }
    }

    override fun <T> inCurrentPage(event: BaseEvent<T>): Boolean {
        return mTagLog == event.pageName
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

    //<editor-fold desc="ILoadingDialogHandler加载框">
    override fun getLoadingDialog(): BaseDialog? {
        return mRequestDialog
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

    //自定义实现
    override fun initLoadingDialog(): BaseDialog? {
        return null
    }

    override fun showLoadingDialog() {
        getLoadingDialog()?.show()
    }

    override fun dismissLoadingDialog() {
        getLoadingDialog()?.dismiss()
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