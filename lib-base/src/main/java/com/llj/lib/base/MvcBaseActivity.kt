package com.llj.lib.base

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Trace
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
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
import java.lang.ref.WeakReference

/**
 * ArchitectureDemo.
 * describe:基础类库
 *
 * @author llj
 * @date 2019/3/13
 */
abstract class MvcBaseActivity<V : ViewBinding> : AppCompatActivity(), IBaseActivity, ICommon<V>, IUiHandler, IEvent, IBaseActivityView, IFragmentHandle {

  @JvmField
  val mTagLog: String = this.javaClass.simpleName

  lateinit var mContext: Context

  private var mUnBinder: Unbinder? = null

  lateinit var mViewBinder: V//ViewBinding

  private var mRequestDialog: BaseDialog? = null

  private val mCancelableTasks: androidx.collection.ArrayMap<Int, Disposable> = androidx.collection.ArrayMap()
  private val mDelayMessages: androidx.collection.ArraySet<String> = androidx.collection.ArraySet()

  private var mCurrentMill: Long = 0//记录初始化时间用

  override fun useEventBus(): Boolean {
    return true
  }


  //<editor-fold desc="生命周期">
  override fun onCreate(savedInstanceState: Bundle?) {
    mContext = this
    mCurrentMill = System.currentTimeMillis()
    Trace.beginSection("MvcBaseActivity.onCreate")

    Timber.tag(mTagLog).i("Lifecycle %s（%d）onCreate start", mTagLog, hashCode())
    try {
      AndroidInjection.inject(this)
    } catch (e: Exception) {
      Timber.tag(mTagLog).i("Lifecycle %s（%d）inject failed：%s", mTagLog, hashCode(), e.message)
    }
    val inject = (System.currentTimeMillis() - mCurrentMill)
    Timber.tag(mTagLog).i("Lifecycle %s（%d）inject：cost %d ms", mTagLog, hashCode(), inject)



    val currentMill = System.currentTimeMillis()
    super.onCreate(savedInstanceState)
    val temp3 = (System.currentTimeMillis() - currentMill)
    Timber.tag(mTagLog).i("Lifecycle %s（%d）onCreate super：cost %d ms", mTagLog, hashCode(), temp3)

    addCurrentActivity(this)

    getIntentData(intent)

    val inflaterStart = System.currentTimeMillis()

    val layoutId = layoutId()
    val layoutView = layoutView()

    if (layoutId != 0) {

      val currentMill1 = System.currentTimeMillis()
      val inflateView = LayoutInflater.from(mContext).inflate(layoutId, null)
      val temp4 = (System.currentTimeMillis() - currentMill1)
      Timber.tag(mTagLog).i("Lifecycle %s（%d）inflate：cost %d ms", mTagLog, hashCode(), temp4)

      setContentView(inflateView)
      mUnBinder = ButterKnife.bind(this)
      Timber.tag(mTagLog).i("Lifecycle %s（%d）layoutId success", mTagLog, hashCode())

    } else if (layoutView != null) {

      setContentView(layoutView)
      mUnBinder = ButterKnife.bind(this)
      Timber.tag(mTagLog).i("Lifecycle %s（%d）layoutView success", mTagLog, hashCode())

    } else {

      var layoutViewBinding = layoutViewBinding()
      if (layoutViewBinding == null) {
        //如果忘记设置则使用反射设置
        layoutViewBinding = reflectViewBinder(layoutInflater, mTagLog)
      }
      if (layoutViewBinding != null) {
        Timber.tag(mTagLog).i("Lifecycle %s（%d）layoutViewBinding success", mTagLog, hashCode())
        mViewBinder = layoutViewBinding
        setContentView(mViewBinder.root)
      }

    }

    val temp = (System.currentTimeMillis() - inflaterStart)
    Timber.tag(mTagLog).i("Lifecycle %s（%d）inflate and setContentView：cost %d ms", mTagLog, hashCode(), temp)

    initLifecycleObserver(lifecycle)

    registerEventBus(this)

    initViews(savedInstanceState)

    initData()

    Trace.endSection()

    val temp2 = (System.currentTimeMillis() - mCurrentMill)
    Timber.tag(mTagLog).i("Lifecycle %s（%d）onCreate end：cost %d ms", mTagLog, hashCode(), temp2)
  }


  override fun onStart() {
    Timber.tag(mTagLog).i("Lifecycle %s（%d）onStart start", mTagLog, hashCode())
    val currentMill = System.currentTimeMillis()
    super.onStart()
    val temp2 = (System.currentTimeMillis() - currentMill)
    Timber.tag(mTagLog).i("Lifecycle %s（%d）onStart end：cost %d ms", mTagLog, hashCode(), temp2)
  }

  override fun onResume() {
    Timber.tag(mTagLog).i("Lifecycle %s（%d）onResume start", mTagLog, hashCode())
    val currentMill = System.currentTimeMillis()
    super.onResume()
    val temp2 = (System.currentTimeMillis() - currentMill)
    Timber.tag(mTagLog).i("Lifecycle %s（%d）onResume end：cost %d ms", mTagLog, hashCode(), temp2)
  }

  override fun onPause() {
    Timber.tag(mTagLog).i("Lifecycle %s（%d）onPause start", mTagLog, hashCode())
    val currentMill = System.currentTimeMillis()
    super.onPause()
    val temp2 = (System.currentTimeMillis() - currentMill)
    Timber.tag(mTagLog).i("Lifecycle %s（%d）onPause end：cost %d ms", mTagLog, hashCode(), temp2)
  }

  override fun onStop() {
    super.onStop()
    Timber.tag(mTagLog).i("Lifecycle %s（%d）onStop", mTagLog, hashCode())
  }

  @CallSuper
  override fun onDestroy() {
    super.onDestroy()
    Timber.tag(mTagLog).i("Lifecycle %s（%d）onDestroy", mTagLog, hashCode())

    //防止窗口泄漏，关闭dialog同时结束相关请求
    val requestDialog = mRequestDialog as Dialog?
    if (requestDialog != null && requestDialog.isShowing) {
      requestDialog.cancel()
      requestDialog.setOnCancelListener(null)
      requestDialog.setOnDismissListener(null)
      dismissLoadingDialog()

      mRequestDialog = null
    }


    //注销事件总线
    unregisterEventBus(this)

    //移除所有的任务
    removeAllDisposable()

    mUnBinder?.unbind()

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
  fun onEvent(event: BaseEvent) {
    if (!isEmpty(event.delayMessage)) {
      //延迟消息
      mDelayMessages.add(event.delayMessage)
    } else {
      //即时消息
      onReceiveEvent(event)
    }
  }

  @CallSuper
  override fun onReceiveEvent(event: BaseEvent) {
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

  override fun inCurrentPage(event: BaseEvent): Boolean {
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
    if (mRequestDialog == null) {
      checkLoadingDialog()
    }
    return mRequestDialog
  }

  private fun checkLoadingDialog() {
    if (mRequestDialog == null) {
      mRequestDialog = initLoadingDialog()

      if (mRequestDialog == null) {
        mRequestDialog = LoadingDialog(this)
        mRequestDialog!!.setOnCancelListener(MyOnCancelListener(this as ITask, getRequestId()))
        mRequestDialog!!.setOnDismissListener(MyOnDismissListener(this as ITask, getRequestId()))
      }
    }
    setRequestId(hashCode())
    Timber.tag(mTagLog).i("Lifecycle %s（%d）initLoadingDialog %s（%d）", mTagLog, hashCode(),
        mRequestDialog!!.javaClass.simpleName, mRequestDialog!!.hashCode())
  }

  private class MyOnCancelListener : DialogInterface.OnCancelListener {
    val mFragment: WeakReference<ITask>
    val mRequestId: Int

    constructor(fragment: ITask, requestId: Int) {
      this.mFragment = WeakReference<ITask>(fragment)
      this.mRequestId = requestId
    }

    override fun onCancel(dialog: DialogInterface?) {
      if (mFragment.get() == null) {
        return
      }
      //移除任务
      mFragment.get()!!.removeDisposable(mRequestId)

      //回调监听
      if (mFragment.get() is ILoadingDialogHandler<*>) {
        val iLoadingDialogHandler = mFragment.get() as ILoadingDialogHandler<*>
        iLoadingDialogHandler.onLoadingDialogCancel(dialog)
      }
    }
  }

  private class MyOnDismissListener : DialogInterface.OnDismissListener {
    val mFragment: WeakReference<ITask>
    val mRequestId: Int

    constructor(fragment: ITask, requestId: Int) {
      this.mFragment = WeakReference<ITask>(fragment)
      this.mRequestId = requestId
    }

    override fun onDismiss(dialog: DialogInterface?) {
      if (mFragment.get() == null) {
        return
      }
      //移除任务
      mFragment.get()!!.removeDisposable(mRequestId)

      //回调监听
      if (mFragment.get() is ILoadingDialogHandler<*>) {
        val iLoadingDialogHandler = mFragment.get() as ILoadingDialogHandler<*>
        iLoadingDialogHandler.onLoadingDialogDismiss(dialog)
      }
    }
  }

  override fun onLoadingDialogCancel(dialog: DialogInterface?) {
  }

  override fun onLoadingDialogDismiss(dialog: DialogInterface?) {
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