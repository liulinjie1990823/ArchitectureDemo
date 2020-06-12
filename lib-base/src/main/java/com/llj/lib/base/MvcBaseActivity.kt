package com.llj.lib.base

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
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
import java.lang.reflect.ParameterizedType

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019/3/13
 */
abstract class MvcBaseActivity<V : ViewBinding> : AppCompatActivity()
    , IBaseActivity, ICommon<V>, IUiHandler, IEvent, IBaseActivityView, IFragmentHandle {

  @JvmField
  val mTagLog: String = this.javaClass.simpleName

  lateinit var mContext: Context

  private var mUnBinder: Unbinder? = null

  lateinit var mViewBinder: V//ViewBinding

  private var mRequestDialog: BaseDialog? = null

  private val mCancelableTasks: androidx.collection.ArrayMap<Int, Disposable> = androidx.collection.ArrayMap()
  private val mDelayMessages: androidx.collection.ArraySet<String> = androidx.collection.ArraySet()

  var mCurrentMill: Long? = null//记录初始化时间用

  override fun useEventBus(): Boolean {
    return true
  }

  //<editor-fold desc="生命周期">
  override fun onCreate(savedInstanceState: Bundle?) {
    mCurrentMill = System.currentTimeMillis()

    Timber.tag(mTagLog).i("Lifecycle %s onCreate：%d", mTagLog, hashCode())
    mContext = this

    try {
      AndroidInjection.inject(this)
    } catch (e: Exception) {
      Timber.tag(mTagLog).i("AndroidSupportInjection.inject failed：%s", e.message)
    }

    super.onCreate(savedInstanceState)

    addCurrentActivity(this)

    getIntentData(intent)

    var layoutViewBinding = layoutViewBinding()

    if (layoutViewBinding == null) {
      //如果忘记设置则使用反射设置
      layoutViewBinding = reflectViewBinder()
    }

    if (layoutViewBinding != null) {
      mViewBinder = layoutViewBinding
      setContentView(mViewBinder.root)
      Timber.tag(mTagLog).i("Lifecycle %s layoutViewBinding success：%d", mTagLog, hashCode())
    } else {
      val layoutView = layoutView()
      if (layoutView == null) {
        setContentView(layoutId())
        Timber.tag(mTagLog).i("Lifecycle %s layoutId：%d", mTagLog, hashCode())
      } else {
        setContentView(layoutView)
        Timber.tag(mTagLog).i("Lifecycle %s layoutView：%d", mTagLog, hashCode())
      }
      mUnBinder = ButterKnife.bind(this)
    }

    checkLoadingDialog()

    initLifecycleObserver(lifecycle)

    registerEventBus(this)

    initViews(savedInstanceState)

    initData()

    Timber.tag(mTagLog).i("Lifecycle %s onCreate：%d cost %d ms", mTagLog, hashCode(), (System.currentTimeMillis() - mCurrentMill!!))
  }

  private fun reflectViewBinder(): V? {
    val currentTimeMillis: Long = System.currentTimeMillis()
    var classType: Class<*> = javaClass
    for (i in 0..10) {
      if (classType.genericSuperclass is ParameterizedType) {
        //父类是泛型类型就反射一次
        Timber.tag(mTagLog).i("Lifecycle %s layoutViewBinding reflect class %s：%d",
            mTagLog, classType.genericSuperclass!!.toString(), hashCode())
        val reflectOnce = reflectOnce(classType.genericSuperclass as ParameterizedType)
        if (reflectOnce != null) {
          val diffTimeMillis = System.currentTimeMillis() - currentTimeMillis
          Timber.tag(mTagLog).i("Lifecycle %s layoutViewBinding reflect cost：%d ms %d", mTagLog, diffTimeMillis, hashCode())
          return reflectOnce
        }
      }
      classType = classType.superclass
    }
    val diffTimeMillis = System.currentTimeMillis() - currentTimeMillis
    Timber.tag(mTagLog).i("Lifecycle %s layoutViewBinding reflect %d cost：%d ms", mTagLog, diffTimeMillis, hashCode())
    return null
  }

  private fun reflectOnce(type: ParameterizedType): V? {
    var viewBinder: V? = null
    try {
      val clazz = type.actualTypeArguments[0] as Class<V>
      Timber.tag(mTagLog).i("Lifecycle %s layoutViewBinding reflect generic type %s：%d",
          mTagLog, clazz, hashCode())
      val method = clazz.getMethod("inflate", LayoutInflater::class.java)
      viewBinder = method.invoke(null, layoutInflater) as V
    } catch (e: Exception) {
      e.printStackTrace()
      Timber.tag(mTagLog).i("Lifecycle %s layoutViewBinding reflect failed：%s", mTagLog, e.message)
    }
    return viewBinder
  }


  override fun onStart() {
    super.onStart()
    Timber.tag(mTagLog).i("Lifecycle %s onStart：%d", mTagLog, hashCode())
  }

  override fun onResume() {
    super.onResume()
    Timber.tag(mTagLog).i("Lifecycle %s onResume：%d", mTagLog, hashCode())
  }

  override fun onPause() {
    super.onPause()
    Timber.tag(mTagLog).i("Lifecycle %s onPause：%d", mTagLog, hashCode())
  }

  override fun onStop() {
    super.onStop()
    Timber.tag(mTagLog).i("Lifecycle %s onStop：%d", mTagLog, hashCode())
  }

  @CallSuper
  override fun onDestroy() {
    super.onDestroy()
    Timber.tag(mTagLog).i("Lifecycle %s onDestroy：%d", mTagLog, hashCode())

    //防止窗口泄漏，关闭dialog同时结束相关请求
    val requestDialog = getLoadingDialog() as Dialog?
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
    Timber.tag(mTagLog).i("Lifecycle %s initLoadingDialog %s ：%d", mTagLog, mRequestDialog!!.javaClass.simpleName, hashCode())
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