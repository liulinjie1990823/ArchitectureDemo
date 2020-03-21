package com.llj.lib.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import com.llj.lib.base.mvvm.BaseViewModel
import com.llj.lib.base.widget.LoadingDialog
import dagger.android.AndroidInjection
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * ArchitectureDemo
 * describe:
 *
 * @author llj
 *
 * @date 2018/6/30
 */
abstract class MVVMBaseActivity<VM : BaseViewModel, B : ViewDataBinding> : AppCompatActivity(),
        IBaseActivity, ICommon, IUiHandler, IEventK, ILoadingDialogHandler<BaseDialog>, ITask {
    val mTagLog: String = this.javaClass.simpleName
    lateinit var mContext: Context

    @Inject
    lateinit var mViewModel: VM
    private lateinit var mDataBinding: B
    private var mRequestDialog: BaseDialog? = null

    private val mCancelableTask: androidx.collection.ArrayMap<Int, Disposable> = androidx.collection.ArrayMap()

    //<editor-fold desc="生命周期">
    override fun onCreate(savedInstanceState: Bundle?) {
        mContext = this

        try {
            AndroidInjection.inject(this)
        } catch (e: Exception) {
        }

        super.onCreate(savedInstanceState)

        addCurrentActivity(this)


        getIntentData(intent)

        val layoutView = layoutView()
        if (layoutView == null) {
            mDataBinding = DataBindingUtil.setContentView(this, layoutId())
        } else {
            setContentView(layoutView)
            mDataBinding = DataBindingUtil.inflate(layoutInflater, layoutId(), null, false)
        }

        checkRequestDialog()

        initLifecycleObserver(lifecycle)

        registerEventBus(this)

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

    override fun onDestroy() {
        super.onDestroy()

        //防止窗口泄漏，关闭dialog同时结束相关请求
        val requestDialog = getLoadingDialog() as Dialog?
        if (requestDialog != null && requestDialog.isShowing) {
            requestDialog.cancel()
        }

        //注销事件总线
        unregisterEventBus(this)

        //移除所有的任务
        removeAllDisposable()

        //移除列表中的activity
        removeCurrentActivity(this)
    }
    //</editor-fold >

    //<editor-fold desc="任务处理">
    override fun addDisposable(taskId: Int, disposable: Disposable) {
        mCancelableTask[taskId] = disposable
    }

    override fun removeDisposable(taskId: Int?) {
        val disposable = mCancelableTask[taskId] ?: return

        if (!disposable.isDisposed) {
            disposable.dispose()
            mCancelableTask.remove(taskId)
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
    //    @Subscribe(threadMode = ThreadMode.MAIN)
    //    fun onEvent(event: BaseEvent) {
    //    }
    //</editor-fold >

    //<editor-fold desc="IBaseActivity">
    override fun initLifecycleObserver(lifecycle: Lifecycle) {}

    override fun superOnBackPressed() {
        super.onBackPressed()
    }

    override fun backToLauncher(nonRoot: Boolean) {
        moveTaskToBack(nonRoot)
    }
    //</editor-fold >

    //<editor-fold desc="ILoadingDialogHandler">
    override fun getLoadingDialog(): BaseDialog? {
        return mRequestDialog
    }

    private fun checkRequestDialog() {
        if (mRequestDialog == null) {
            mRequestDialog = initLoadingDialog()

            if (mRequestDialog == null) {
                mRequestDialog = LoadingDialog(this)
            }
        }
        setRequestId(hashCode())
    }

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
