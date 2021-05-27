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
import androidx.lifecycle.LifecycleOwner
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
 * 08-11 11:33:36.160    7162-7162/com.example.yinsgo.myui V/Fragment1﹕ onStart
 * 08-11 11:33:36.160    7162-7162/com.example.yinsgo.myui V/Fragment1﹕ onResume()
 * 08-11 11:33:36.160    7162-7162/com.example.yinsgo.myui V/Fragment2﹕ onAttach
 * 08-11 11:33:36.160    7162-7162/com.example.yinsgo.myui V/Fragment2﹕ onCreate
 * 08-11 11:33:36.160    7162-7162/com.example.yinsgo.myui V/Fragment2﹕ onCreateView
 * 08-11 11:33:36.161    7162-7162/com.example.yinsgo.myui V/Fragment2﹕ onActivityCreated
 * 08-11 11:33:36.160    7162-7162/com.example.yinsgo.myui V/Fragment2﹕ onStart
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
 * 08-11 11:33:36.160    7162-7162/com.example.yinsgo.myui V/Fragment3﹕ onStart
 * 08-11 11:33:54.085    7162-7162/com.example.yinsgo.myui V/Fragment3﹕ onResume()
 *
 *
 * FragmentPagerAdapter 通过attach和detach，会保存Fragment实例
 *
 * FragmentStatePagerAdapter 通过add和remove，完整生命周期添加移除，但是状态会保存在SavedState以备恢复
 *
 * ArchitectureDemo.
 *
 * describe:Fragment基类，兼容DialogFragment
 * @author llj
 * @date 2018/8/15
 */
abstract class MvcBaseFragment<V : ViewBinding> : androidx.fragment.app.WrapDialogFragment(), IBaseFragment, ICommon<V>, IUiHandler, IEvent, ITask,
  ILoadingDialogHandler<BaseDialog>, IFragmentHandle {
  @JvmField val mTagLog: String = this.javaClass.simpleName

  lateinit var mContext: Context

  private var mInit: Boolean = false //是否已经初始化
  private var mPreLoadData: Boolean = false //是否预加载数据，比如使用了ViewModel预加载

  private var mRootView: View? = null

  lateinit var mViewBinder: V//ViewBinding

  private var mUnBinder: Unbinder? = null

  private var mRequestDialog: BaseDialog? = null

  private val mCancelableTasks: androidx.collection.ArrayMap<Int, Disposable> = androidx.collection.ArrayMap()
  private val mResumedMessages: androidx.collection.ArraySet<String> = androidx.collection.ArraySet()

  //是否需要保存view，可能有些界面不需要重新加载，显示后需要保存在内存中；需要注意内存量
  //配合FragmentPagerAdapter时候使用，如果是dialog模式则不支持
  var mShouldSaveView = false
  var mUseSoftInput: Boolean = false //是否使用软键盘，用来自动显示输入法
  var mUseTranslucent: Boolean = false //是否使用透明模式
  var mDialogUseDismiss: Boolean = false //是否使用Dismiss模式，Dismiss模式关闭的时候不会移除fragment，这样不会重新onCreateView
  var mTextColorBlack: Boolean? = null //是否使用黑色字体，mUseTranslucent=true的前提下起作用

  private var mCurrentMill: Long? = null//记录初始化时间用

  init {
    showsDialog = false
  }

  //region 对话框相关
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

  //region onShow
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
      activity?.window?.decorView?.post { performToggleSoftInput(true) }
    } else {
      dialog?.window?.decorView?.post { performToggleSoftInput(true) }
    }
  }

  private fun performToggleSoftInput(show: Boolean) {
    if (show) {
      if (dialog == null) {
        AInputMethodManagerUtils.showOrHideInput(activity, true)
      } else {
        AInputMethodManagerUtils.showOrHideInput(dialog, true)
      }
    } else {
      if (dialog == null) {
        AInputMethodManagerUtils.hideSoftInputFromWindow(activity)
      } else {
        AInputMethodManagerUtils.hideSoftInputFromWindow(dialog)
      }
    }

  }

  //endregion

  //region onDismiss
  //如果有dialog，dialog的dismiss会回调该方法
  //不要设置dialog的setOnDismissListener，因为DialogFragment中会覆盖
  //在fragment中提供了mOnDismissListener的回调
  override fun onDismiss(dialog: DialogInterface) {
    if (!mDialogUseDismiss) {
      super.onDismiss(dialog)
    }
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
    performToggleSoftInput(false)

  }
  //endregion

  //region smartDismiss
  fun smartDismiss() {
    if (showsDialog) {
      if (mDialogUseDismiss) {
        dialog?.dismiss()
      } else {
        //后续会调用hideSoftInput，onDismiss，走dispatchDismissListener
        dismissAllowingStateLoss()
      }
    } else {
      hideSoftInput()
      removeFragment(parentFragmentManager, this)
      dispatchDismissListener()
    }
  }
  //endregion


  //region smartShow
  fun smartShow(manager: FragmentManager, tag: String = mTagLog, @IdRes containerViewId: Int = Window.ID_ANDROID_CONTENT) {
    if (showsDialog) {
      try {
        if (mDialogUseDismiss && dialog != null) {
          dialog!!.show()
        } else {
          super.show(manager, tag)
        }

      } catch (ignore: IllegalStateException) {
        //  容错处理,不做操作
        Timber.tag(mTagLog).i("Lifecycle %s smartShow：%d %s", mTagLog, hashCode(), ignore.message)
      }

    } else {
      //添加到ID_ANDROID_CONTENT里
      addFragment(manager.beginTransaction(), this, containerViewId, tag)
    }
  }
  //endregion

  //region smartShowNow
  //1.先添加fragment
  //2.在fragment的生命周期的onStart中显示dialog
  fun smartShowNow(manager: FragmentManager, tag: String = mTagLog, @IdRes containerViewId: Int = Window.ID_ANDROID_CONTENT) {
    if (showsDialog) {
      try {
        super.showNow(manager, tag)
      } catch (ignore: IllegalStateException) {
        //  容错处理,不做操作
        Timber.tag(mTagLog).i("Lifecycle %s smartShowNow：%d %s", mTagLog, hashCode(), ignore.message)
      }

    } else {
      //添加到ID_ANDROID_CONTENT里
      addFragmentNow(manager.beginTransaction(), this, containerViewId, tag)
    }
  }
  //endregion
  //endregion


  //region生命周期
  override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
    Timber.tag(mTagLog).i("Lifecycle %s onCreateAnimation：%d", mTagLog, hashCode())
    return super.onCreateAnimation(transit, enter, nextAnim)
  }

  override fun setUserVisibleHint(isVisibleToUser: Boolean) {
    super.setUserVisibleHint(isVisibleToUser)
    Timber.tag(mTagLog).i("Lifecycle %s setUserVisibleHint %s ：%d", mTagLog, isVisibleToUser, hashCode())
    //当fragment在viewPager中的时候需要实现懒加载的模式
    //当使用viewPager进行预加载fragment的时候,先调用setUserVisibleHint,后调用onViewCreated
    //所以切换到第二个fragment的时候mIsInit=true，而后面调用到setUserVisibleHint后再刷新
    //最新和viewPager搭配的时候最好设置BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT，在onResume中来控制懒加载

    // 加载数据
    if (useLazyLoad() && hasInitAndVisible()) {
      initData()
    }
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    Timber.tag(mTagLog).i("Lifecycle %s onAttach：%d", mTagLog, hashCode())
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Timber.tag(mTagLog).i("Lifecycle %s onCreate：%d", mTagLog, hashCode())

    if (showsDialog && mShouldSaveView) {
      throw RuntimeException("使用dialog模式时不支持 mShouldSaveView=true")
    }

    //设置dialog的style
    setStyle(STYLE_NO_TITLE, R.style.no_dim_dialog)

    mContext = requireContext()

    checkLoadingDialog()

    getArgumentsData(arguments)

    //如果有预加载数据的可以实现这个方法
    mPreLoadData = preLoadData()

    try {
      AndroidSupportInjection.inject(this)
    } catch (e: Exception) {
      Timber.tag(mTagLog).i("AndroidSupportInjection.inject failed：%s", e.message)
    }
  }


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    mCurrentMill = System.currentTimeMillis()
    Timber.tag(mTagLog).i("Lifecycle %s onCreateView：%d", mTagLog, hashCode())

    //为避免view泄露，onDestroyView里面会注销，所以这里需要重新注册
    registerEventBus(this)

    if (mShouldSaveView && mRootView != null) {
      return mRootView
    }

    var layoutViewBinding = layoutViewBinding()
    if (layoutViewBinding == null) {
      //如果忘记设置则使用反射设置
      layoutViewBinding = reflectViewBinder(layoutInflater, mTagLog)
    }

    if (layoutViewBinding != null) {
      mViewBinder = layoutViewBinding
      mRootView = mViewBinder.root
    } else {
      val layoutView = layoutView()
      mRootView = layoutView ?: inflater.inflate(layoutId(), container, false)
      mUnBinder = ButterKnife.bind(this, mRootView!!)
    }

    initViews(savedInstanceState)

    Timber.tag(mTagLog).i("Lifecycle %s onCreate：%d cost %d ms", mTagLog, hashCode(), (System.currentTimeMillis() - mCurrentMill!!))
    return mRootView
  }

  //当fragment被添加到activity中会调用
  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    Timber.tag(mTagLog).i("Lifecycle %s onActivityCreated：%d", mTagLog, hashCode())
    dialog?.window?.let {
      setWindowParams(it, -1, -1, Gravity.CENTER)
    }
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
    if (mDialogUseDismiss) {
      val temp = dialog
      if ((temp is BaseDialog) && !temp.mCreate) {
        //第一次还是使用默认的方式， mDialog.show()
        super.onStart()
        dispatchShowListener()
        showSoftInput()
      } else {
        //后面就不主动显示dialog
        superOnStart()
      }
    } else {
      super.onStart()
      dispatchShowListener()
      showSoftInput()
    }
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


  //如果需要在内存中持有view，不需要在onDestroyView中移除observers，使用自身LifecycleOwner即可
  open fun getCorrectLifecycleOwner(): LifecycleOwner? {
    return if (mShouldSaveView) {
      //使用自身LifecycleOwner，则
      this
    } else {
      //特制的LifecycleOwner，onDestroyView中会分发onDestroy来移除observers
      viewLifecycleOwner
    }
  }

  override fun onDestroyView() {
    Timber.tag(mTagLog).i("Lifecycle %s onDestroyView：%d", mTagLog, hashCode())

    //移除所有的任务,因为有可能任务里面引用了RefreshLayout，导致onDestroyView无法回收view引起内存泄露
    removeAllDisposable()
    //取消事件监听,EventBus事件中有可能调用接口，会对RefreshLayout引用，导致无法回收引起内存泄露
    unregisterEventBus(this)


    //将mRootView在parent中移除绑定，便于在重新attach的时候可以重用mRootView
    if (mRootView?.parent is ViewGroup) {
      (mRootView?.parent as ViewGroup).removeView(mRootView)
    }
    if (!mShouldSaveView) {
      //移除view绑定
      mUnBinder?.unbind()
      mRootView = null
    }

    super.onDestroyView()
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
  }

  override fun onDetach() {
    super.onDetach()
    Timber.tag(mTagLog).i("Lifecycle %s onDetach：%d", mTagLog, hashCode())
  }
  //endregion


  //region任务处理
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
  //endregion

  //region事件总线
  @Subscribe(threadMode = ThreadMode.MAIN)
  fun onEvent(event: BaseEvent) {
    if (!isEmpty(event.resumedMessage)) {
      //延迟消息
      mResumedMessages.add(event.resumedMessage)
    } else {
      if ("refresh" == event.message && inCurrentPage(event)) {
        //限定界面
        if (mPreLoadData) {
          preLoadData()
        } else {
          initData()
        }
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
  //endregion

  //region IBaseFragment
  override fun initLifecycleObserver(lifecycle: Lifecycle) {
    //将mPresenter作为生命周期观察者添加到lifecycle中
  }
  //endregion

  //region IBaseFragmentLazy
  override fun hasInitAndVisible(): Boolean {
    return mInit && userVisibleHint
  }
  //endregion

  //region ILoadingDialogHandler加载框
  override fun getLoadingDialog(): BaseDialog? {
    checkLoadingDialog()
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

  private class MyOnCancelListener(fragment: ITask, requestId: Int) : DialogInterface.OnCancelListener {
    val mFragment: WeakReference<ITask> = WeakReference<ITask>(fragment)
    val mRequestId: Int = requestId

    override fun onCancel(dialog: DialogInterface?) {
      mFragment.get() ?: return
      val task: ITask = mFragment.get()!!
      //移除任务
      task.removeDisposable(mRequestId)
      //回调监听
      if (task is ILoadingDialogHandler<*>) {
        task.onLoadingDialogCancel(dialog)
      }
    }
  }

  private class MyOnDismissListener(fragment: ITask, requestId: Int) : DialogInterface.OnDismissListener {
    val mFragment: WeakReference<ITask> = WeakReference<ITask>(fragment)
    val mRequestId: Int = requestId

    override fun onDismiss(dialog: DialogInterface?) {
      mFragment.get() ?: return

      val task: ITask = mFragment.get()!!
      //移除任务
      task.removeDisposable(mRequestId)
      //回调监听
      if (task is ILoadingDialogHandler<*>) {
        task.onLoadingDialogDismiss(dialog)
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
  //endregion


  fun setWindowParams(window: Window, width: Int, height: Int, gravity: Int) {
    //        StatusBarCompat.translucentStatusBar(getWindow(), true);
    //         setCancelable(cancelable);
    //         setCanceledOnTouchOutside(cancel);
    window.setBackgroundDrawable(ColorDrawable(0x00000000))
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