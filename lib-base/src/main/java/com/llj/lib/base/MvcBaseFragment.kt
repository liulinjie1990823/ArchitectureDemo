package com.llj.lib.base

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import butterknife.ButterKnife
import butterknife.Unbinder
import com.llj.lib.base.event.BaseEvent
import com.llj.lib.base.widget.LoadingDialog
import com.llj.lib.utils.AInputMethodManagerUtils
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber
import java.lang.ref.WeakReference
import java.lang.reflect.ParameterizedType


/**
 * Fragment在 ViewPager中的表现
 *
 * 08-11 11:33:36.156    7162-7162/com.example.yinsgo.myui V/Fragment1﹕ setUserVisibleHint false
 * 08-11 11:33:36.156    7162-7162/com.example.yinsgo.myui V/Fragment2﹕ setUserVisibleHint false
 * 08-11 11:33:36.157    7162-7162/com.example.yinsgo.myui V/Fragment1﹕ setUserVisibleHint true
 * 08-11 11:33:36.158    7162-7162/com.example.yinsgo.myui V/Fragment1﹕ onAttach
 * 08-11 11:33:36.158    7162-7162/com.example.yinsgo.myui V/Fragment1﹕ onCreate
 * 08-11 11:33:36.159    7162-7162/com.example.yinsgo.myui V/Fragment1﹕ onCreateView
 * 08-11 11:33:36.160    7162-7162/com.example.yinsgo.myui V/Fragment1﹕ onActivityCreated
 * 08-11 11:33:36.160    7162-7162/com.example.yinsgo.myui V/Fragment1﹕ onResume()
 * 08-11 11:33:36.160    7162-7162/com.example.yinsgo.myui V/Fragment2﹕ onAttach
 * 08-11 11:33:36.160    7162-7162/com.example.yinsgo.myui V/Fragment2﹕ onCreate
 * 08-11 11:33:36.160    7162-7162/com.example.yinsgo.myui V/Fragment2﹕ onCreateView
 * 08-11 11:33:36.161    7162-7162/com.example.yinsgo.myui V/Fragment2﹕ onActivityCreated
 * 08-11 11:33:36.161    7162-7162/com.example.yinsgo.myui V/Fragment2﹕ onResume()
 *
 * 当切换到第二个fragment时打印日志：
 *
 * 08-11 11:33:54.084    7162-7162/com.example.yinsgo.myui V/Fragment3﹕ setUserVisibleHint false
 * 08-11 11:33:54.084    7162-7162/com.example.yinsgo.myui V/Fragment1﹕ setUserVisibleHint false
 * 08-11 11:33:54.084    7162-7162/com.example.yinsgo.myui V/Fragment2﹕ setUserVisibleHint true
 * 08-11 11:33:54.084    7162-7162/com.example.yinsgo.myui V/Fragment3﹕ onAttach
 * 08-11 11:33:54.085    7162-7162/com.example.yinsgo.myui V/Fragment3﹕ onCreate
 * 08-11 11:33:54.085    7162-7162/com.example.yinsgo.myui V/Fragment3﹕ onCreateView
 * 08-11 11:33:54.085    7162-7162/com.example.yinsgo.myui V/Fragment3﹕ onActivityCreated
 * 08-11 11:33:54.085    7162-7162/com.example.yinsgo.myui V/Fragment3﹕ onResume()
 *
 * ArchitectureDemo.
 *
 * describe:Fragment基类，兼容DialogFragment
 * @author llj
 * @date 2018/8/15
 */
abstract class MvcBaseFragment<V : ViewBinding> : androidx.fragment.app.WrapDialogFragment()
    , IBaseFragment, ICommon<V>, IUiHandler, IEvent, ITask, ILoadingDialogHandler<BaseDialog>, IFragmentHandle {
  @JvmField
  val mTagLog: String = this.javaClass.simpleName

  lateinit var mContext: Context

  private var mInit: Boolean = false //是否已经初始化

  @JvmField
  var mViewBinder: V? = null//ViewBinding
  private var mUnBinder: Unbinder? = null

  private var mRequestDialog: BaseDialog? = null

  private val mCancelableTasks: androidx.collection.ArrayMap<Int, Disposable> = androidx.collection.ArrayMap()
  private val mDelayMessages: androidx.collection.ArraySet<String> = androidx.collection.ArraySet()

  var mUseSoftInput: Boolean = false //是否使用软键盘，用来自动显示输入法
  var mUseTranslucent: Boolean = false //是否使用透明模式
  var mTextColorBlack: Boolean? = null //是否使用黑色字体，mUseTranslucent=true的前提下起作用

  private var mCurrentMill: Long? = null//记录初始化时间用

  init {
    showsDialog = false
  }

  //<editor-fold desc="对话框相关">
  private var mOnDismissListener: DialogInterface.OnDismissListener? = null
  private var mOnShowListener: DialogInterface.OnShowListener? = null

  fun setOnDismissListener(onDismissListener: DialogInterface.OnDismissListener) {
    mOnDismissListener = onDismissListener
  }

  fun setOnShowListener(onShowListener: DialogInterface.OnShowListener) {
    mOnShowListener = onShowListener
  }

  /**
   * 设置状态栏字体颜色
   */
  open fun statusBarTextColorBlack(): Boolean {
    return true
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    Timber.tag(mTagLog).i("Lifecycle %s onCreateDialog：%d", mTagLog, hashCode())

    val baseDialogImpl = BaseDialogImpl(requireActivity(), theme)
    baseDialogImpl.mUseTranslucent = mUseTranslucent

    if (mTextColorBlack == null) {
      baseDialogImpl.mTextColorBlack = statusBarTextColorBlack()
    } else {
      baseDialogImpl.mTextColorBlack = mTextColorBlack!!
    }

    return baseDialogImpl
  }

  //<editor-fold desc="onShow">
  //如果是DialogFragment，会在该方法中显示dialog
  private fun dispatchShowListener() {
    //设置回调方法
    if (dialog == null) {
      mOnShowListener?.onShow(null)
    } else {
      Timber.tag(mTagLog).i("Lifecycle %s BaseDialogImpl show：%d", mTagLog, dialog.hashCode())
      mOnShowListener?.onShow(dialog)
    }
  }

  private fun showSoftInput() {
    //是否显示输入法
    if (!mUseSoftInput) {
      return
    }
    if (dialog == null) {
      activity?.window?.decorView?.post(Runnable {
        AInputMethodManagerUtils.showOrHideInput(activity, true)
      })
    } else {
      dialog?.window?.decorView?.post(Runnable {
        AInputMethodManagerUtils.showOrHideInput(dialog, true)
      })
    }
  }
  //</editor-fold>

  //<editor-fold desc="onDismiss">
  //如果有dialog，dialog的dismiss会回调该方法
  //不要设置dialog的setOnDismissListener，因为DialogFragment中会覆盖
  //在fragment中提供了mOnDismissListener的回调
  override fun onDismiss(dialog: DialogInterface) {
    super.onDismiss(dialog)

    dispatchDismissListener()
  }

  private fun dispatchDismissListener() {
    //设置回调方法
    Timber.tag(mTagLog).i("Lifecycle %s BaseDialogImpl dismiss：%d", mTagLog, dialog.hashCode())
    mOnDismissListener?.onDismiss(dialog)
  }

  override fun dismissAllowingStateLoss() {
    hideSoftInput()
    super.dismissAllowingStateLoss()
  }

  private fun hideSoftInput() {
    if (!mUseSoftInput) {
      return
    }
    if (dialog == null) {
      AInputMethodManagerUtils.hideSoftInputFromWindow(activity)
    } else {
      AInputMethodManagerUtils.hideSoftInputFromWindow(dialog)
    }

  }
  //</editor-fold>

  //<editor-fold desc="smartDismiss">
  fun smartDismiss() {
    if (showsDialog) {
      //后续会调用hideSoftInput，onDismiss，走dispatchDismissListener
      dismissAllowingStateLoss()
    } else {
      hideSoftInput()
      removeFragment(parentFragmentManager, this)
      dispatchDismissListener()
    }
  }
  //</editor-fold>


  //<editor-fold desc="smartShow">
  fun smartShow(manager: FragmentManager, tag: String) {
    smartShow(manager, tag, Window.ID_ANDROID_CONTENT)
  }

  fun smartShow(manager: FragmentManager, tag: String, @IdRes containerViewId: Int) {
    if (showsDialog) {
      try {
        super.show(manager, tag)
      } catch (ignore: IllegalStateException) {
        //  容错处理,不做操作
        Timber.tag(mTagLog).i("Lifecycle %s smartShow：%d %s", mTagLog, hashCode(), ignore.message)
      }

    } else {
      //添加到ID_ANDROID_CONTENT里
      addFragment(manager.beginTransaction(), containerViewId, this, tag, true)
    }
  }
  //</editor-fold>

  //<editor-fold desc="smartShowNow">
  fun smartShowNow(manager: FragmentManager, tag: String) {
    smartShowNow(manager, tag, Window.ID_ANDROID_CONTENT)
  }

  //1.先添加fragment
  //2.在fragment的生命周期的onStart中显示dialog
  fun smartShowNow(manager: FragmentManager, tag: String, @IdRes containerViewId: Int) {
    if (showsDialog) {
      try {
        super.showNow(manager, tag)
      } catch (ignore: IllegalStateException) {
        //  容错处理,不做操作
        Timber.tag(mTagLog).i("Lifecycle %s onCreateView：%d %s", mTagLog, hashCode(), ignore.message)
      }

    } else {
      //添加到ID_ANDROID_CONTENT里
      addFragmentNow(manager.beginTransaction(), containerViewId, this, tag, true)
    }
  }
  //</editor-fold>
  //</editor-fold >

  override fun setUserVisibleHint(isVisibleToUser: Boolean) {
    super.setUserVisibleHint(isVisibleToUser)
    Timber.tag(mTagLog).i("Lifecycle %s setUserVisibleHint %s ：%d", mTagLog, isVisibleToUser, hashCode())
    //当fragment在viewPager中的时候需要实现懒加载的模式
    //当使用viewPager进行预加载fragment的时候,先调用setUserVisibleHint,后调用onViewCreated
    //所以刚开始是mIsInit=true,mIsVisible为false

    // 加载数据
    if (useLazyLoad() && hasInitAndVisible()) {
      initData()
    }
  }

  //<editor-fold desc="生命周期">
  override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
    Timber.tag(mTagLog).i("Lifecycle %s onCreateAnimation：%d", mTagLog, hashCode())
    return super.onCreateAnimation(transit, enter, nextAnim)
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    Timber.tag(mTagLog).i("Lifecycle %s onAttach：%d", mTagLog, hashCode())
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Timber.tag(mTagLog).i("Lifecycle %s onCreate：%d", mTagLog, hashCode())

    //设置dialog的style
    setStyle(STYLE_NO_TITLE, R.style.no_dim_dialog)

    mContext = requireContext()

    if (arguments !== null) {
      getArgumentsData(requireArguments())
    }

    try {
      AndroidSupportInjection.inject(this)
    } catch (e: Exception) {
      Timber.tag(mTagLog).i("AndroidSupportInjection.inject failed：%s", e.message)
    }
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    Timber.tag(mTagLog).i("Lifecycle %s onActivityCreated：%d", mTagLog, hashCode())
    if (dialog == null || dialog!!.window == null) {
      return
    }
    setWindowParams(dialog!!.window!!, -1, -1, Gravity.CENTER)
  }

  private var mRootView: View? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    mCurrentMill = System.currentTimeMillis()
    Timber.tag(mTagLog).i("Lifecycle %s onCreateView：%d", mTagLog, hashCode())

    //为避免view泄露，onDestroyView里面会注销，所以这里需要重新注册
    registerEventBus(this)

    if (mRootView != null) {
      return mRootView
    }

    mViewBinder = layoutViewBinding()

    if (mViewBinder == null) {
      //如果忘记设置则使用反射设置
      reflectViewBinder()
    }

    if (mViewBinder != null) {
      mRootView = mViewBinder?.root;
    } else {
      val layoutView = layoutView()
      mRootView = layoutView ?: inflater.inflate(layoutId(), null)
      mUnBinder = ButterKnife.bind(this, mRootView!!)
    }

    checkLoadingDialog()

    initViews(savedInstanceState)

    Timber.tag(mTagLog).i("Lifecycle %s onCreate：%d cost %d ms", mTagLog, hashCode(), (System.currentTimeMillis() - mCurrentMill!!))
    return mRootView
  }

  private fun reflectViewBinder() {
    val currentTimeMillis = System.currentTimeMillis()
    var classType: Class<*> = javaClass
    for (i in 0..4) {
      if (classType.genericSuperclass is ParameterizedType) {
        //父类是泛型类型就反射一次
        Timber.tag(mTagLog).i("Lifecycle %s layoutViewBinding reflect class %s：%d",
            mTagLog, classType.genericSuperclass!!.toString(), hashCode())
        mViewBinder = reflectOnce(classType.genericSuperclass as ParameterizedType)
        if (mViewBinder != null) {
          break
        }
      }
      classType = classType.superclass
    }
    val diffTimeMillis = System.currentTimeMillis() - currentTimeMillis
    Timber.tag(mTagLog).i("Lifecycle %s layoutViewBinding reflect %d ms：%d", mTagLog, diffTimeMillis, hashCode())
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
      Timber.tag(mTagLog).i("Lifecycle %s layoutViewBinding reflect failed：%s", mTagLog, e.message)
    }
    return viewBinder
  }


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    Timber.tag(mTagLog).i("Lifecycle %s onViewCreated：%d", mTagLog, hashCode())

    // 已经完成初始化
    mInit = true
    // 加载数据
    if (useLazyLoad()) {
      if (hasInitAndVisible()) {
        Timber.tag(mTagLog).i("Lifecycle %s hasInitAndVisible：%d", mTagLog, hashCode())
        initData()
      }
    } else {
      initData()
    }
  }

  override fun onStart() {
    super.onStart()
    Timber.tag(mTagLog).i("Lifecycle %s onStart：%d", mTagLog, hashCode())

    dispatchShowListener()

    showSoftInput()
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

  override fun onDestroyView() {
    //移除所有的任务,有可能任务里面引用了RefreshLayout，导致无法回收引起内存泄露
    removeAllDisposable()
    //取消事件监听,EventBus事件中有可能调用接口，会对RefreshLayout引用，导致无法回收引起内存泄露
    unregisterEventBus(this)
    super.onDestroyView()
    Timber.tag(mTagLog).i("Lifecycle %s onDestroyView：%d", mTagLog, hashCode())

    //将mRootView在parent中移除绑定，便于在重新attach的时候可以重用mRootView
    if (mRootView?.parent is ViewGroup) {
      (mRootView?.parent as ViewGroup).removeView(mRootView)
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    Timber.tag(mTagLog).i("Lifecycle %s onDestroy：%d", mTagLog, hashCode())

    //防止窗口泄漏，移除监听，并移除mRequestDialog
    val requestDialog = getLoadingDialog() as Dialog?
    if (requestDialog != null && requestDialog.isShowing) {
      requestDialog.cancel()
      requestDialog.setOnCancelListener(null)
      requestDialog.setOnDismissListener(null)
      dismissLoadingDialog()

      mRequestDialog = null
    }


    //移除view绑定
    mUnBinder?.unbind()

    mRootView = null
  }

  override fun onDetach() {
    super.onDetach()
    Timber.tag(mTagLog).i("Lifecycle %s onDetach：%d", mTagLog, hashCode())
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
  @Subscribe(threadMode = ThreadMode.MAIN)
  fun onEvent(event: BaseEvent) {
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

  override fun onReceiveEvent(event: BaseEvent) {
  }

  //一般用event.pageName和子Fragment中标记的pageName比对
  override fun inCurrentPage(event: BaseEvent): Boolean {
    return mTagLog == event.pageName
  }
  //</editor-fold >

  //<editor-fold desc="IBaseFragment">
  override fun initLifecycleObserver(lifecycle: Lifecycle) {
    //将mPresenter作为生命周期观察者添加到lifecycle中
  }
  //</editor-fold >

  //<editor-fold desc="IBaseFragmentLazy">
  override fun hasInitAndVisible(): Boolean {
    return mInit && userVisibleHint
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
        mRequestDialog = LoadingDialog(mContext)
        mRequestDialog!!.setOnCancelListener(MyOnCancelListener(this as ITask, getRequestId()))
        mRequestDialog!!.setOnDismissListener(MyOnDismissListener(this as ITask, getRequestId()))
      }
      setRequestId(hashCode())
      Timber.tag(mTagLog).i("Lifecycle %s initLoadingDialog %s ：%d", mTagLog, mRequestDialog!!.javaClass.simpleName, hashCode())
    }
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

  override fun setRequestId(taskId: Int) {
    getLoadingDialog()?.setRequestId(taskId)
  }

  override fun getRequestId(): Int {
    return getLoadingDialog()?.getRequestId() ?: -1
  }
  //</editor-fold >


  fun setWindowParams(window: Window, width: Int, height: Int, gravity: Int) {
    //        StatusBarCompat.translucentStatusBar(getWindow(), true);
    //         setCancelable(cancelable);
    //         setCanceledOnTouchOutside(cancel);
    dialog?.window?.setBackgroundDrawable(ColorDrawable(0x00000000));
    val params = window.attributes
    // setContentView设置布局的透明度，0为透明，1为实际颜色,该透明度会使layout里的所有空间都有透明度，不仅仅是布局最底层的view
    // params.alpha = 1f;
    // 窗口的背景，0为透明，1为全黑
    // params.dimAmount = 0f;
    params.width = width
    params.height = height
    params.gravity = gravity
    window.attributes = params
  }
}