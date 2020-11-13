package com.llj.lib.base

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDialog
import butterknife.ButterKnife
import com.llj.lib.base.widget.LoadingDialog
import com.llj.lib.statusbar.LightStatusBarCompat
import timber.log.Timber


/**
 * ArchitectureDemo
 *
 * describe:对话框基类
 * @author llj
 * @date 2018/5/24
 */
abstract class BaseDialog : AppCompatDialog, ILoadingDialogHandler<BaseDialog> {

  companion object {
    const val MATCH = ViewGroup.LayoutParams.MATCH_PARENT
    const val WRAP = ViewGroup.LayoutParams.WRAP_CONTENT
  }


  val mContext: Context
  val mTagLog: String = this.javaClass.simpleName

  private var mRequestDialog: BaseDialog? = null
  var mUseTranslucent: Boolean = false //是否使用透明全屏模式，如果窗口使用输入法使用的是resize,则在透明全屏模式下会失效，需要自己手动resize布局
  var mTextColorBlack: Boolean = true //是否黑色字体

  private var mLazyInit: Boolean = false //是否延迟加载，在show的时候[onCreate]中加载

  constructor(context: Context) : this(context, R.style.dim_dialog, false)

  constructor(context: Context, lazyInit: Boolean) : this(context, R.style.dim_dialog, lazyInit)

  constructor(context: Context, themeResId: Int) : this(context, themeResId, false)

  constructor(context: Context, themeResId: Int, lazyInit: Boolean) : super(context, themeResId) {
    mContext = context
    mLazyInit = lazyInit;
    allInit()//如果用init方法，会在赋值前调用，所以直接手动调用
  }


  private fun allInit() {
    Timber.tag(mTagLog).i("Lifecycle %s init with %s ：%d", mTagLog, mContext.javaClass.simpleName, hashCode())

    //28中增加了layoutInDisplayCutoutMode，dialog需要设置LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES模式并配置相关decorView的systemUiVisibility参数才能全屏
    //但是activity在LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT模式下并配置相关decorView的systemUiVisibility参数就能全屏了
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      window?.attributes?.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
    }
    //22中增加了Z轴的高度，会使全屏的dialog在底部留出空隙，需要设置为0
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
      window?.setElevation(0f)
    }

    if (needLoadingDialog()) {
      checkRequestDialog()
    }

    if (!mLazyInit) {
      bindViews()
      initViews()
    }
  }

  private fun bindViews() {
    val layoutId = layoutId()
    if (layoutId != 0) {
      setContentView(layoutId())
      ButterKnife.bind(this)
      Timber.tag(mTagLog).i("Lifecycle %s bindViews with %s ：%d", mTagLog, mContext.javaClass.simpleName, hashCode())
    }
  }

  @LayoutRes
  abstract fun layoutId(): Int

  abstract fun initViews()


  override fun onCreate(savedInstanceState: Bundle?) {
    Timber.tag(mTagLog).i("Lifecycle %s onCreate with %s ：%d", mTagLog, mContext.javaClass.simpleName, hashCode())
    super.onCreate(savedInstanceState)

    if (mLazyInit) {
      bindViews()
      initViews()
    }

    // 第一次show的时候会调用该方法
    setWindowParam()
    performCreate(savedInstanceState)
  }

  protected open fun performCreate(savedInstanceState: Bundle?) {}


  //<editor-fold desc="设置dialog属性">
  protected abstract fun setWindowParam()



  protected fun setWindowParams(gravity: Int) {
    setWindowParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, gravity)
  }

  protected fun setWindowParams(height: Int, gravity: Int) {
    setWindowParams(ViewGroup.LayoutParams.MATCH_PARENT, height, gravity)
  }

  /**
   * 在设置 设置dialog的一些属性
   * dialog中尽量不要设置固定高度，使用MATCH_PARENT，然后在子布局中设置高度，否则adjustResize会失效
   *
   * @param width   一般布局和代码这里都设置match,要设置边距的直接布局里调好
   * @param height  一般布局height设置为wrap，这样可以调整dialog的上中下位置，要固定(非上中下)位置的直接在布局中调整， 设置match后，软键盘不会挤压布局
   * @param gravity 设置match后，此属性无用
   */
  fun setWindowParams(width: Int, height: Int, gravity: Int) {
//             setCancelable(cancelable);
//             setCanceledOnTouchOutside(cancel);
    val window = window
    val params = window!!.attributes
    // setContentView设置布局的透明度，0为透明，1为实际颜色,该透明度会使layout里的所有空间都有透明度，不仅仅是布局最底层的view
    // params.alpha = 1f;
    // 窗口的背景，0为透明，1为全黑
    // params.dimAmount = 0f;
    params.width = width
    params.height = height
    params.gravity = gravity

    window.attributes = params
  }
  //</editor-fold>

  //<editor-fold desc="ILoadingDialogHandler">
  //大部分的dialog是需要加载框的，LoadingDialog自身不需要
  open fun needLoadingDialog(): Boolean {
    return true
  }

  override fun getLoadingDialog(): BaseDialog? {
    return mRequestDialog
  }

  private fun checkRequestDialog() {
    if (mRequestDialog == null) {
      mRequestDialog = initLoadingDialog()
      if (mRequestDialog == null) {
        mRequestDialog = LoadingDialog(mContext)
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
  //</editor-fold>

  //  设置dialog透明模式
  override fun show() {

    if (mUseTranslucent) {
      Timber.tag(mTagLog).i("%s show with Translucent", mTagLog)
      //Immersive Mode
      //如果没有输入框，不需要弹出软键盘的可以使用TRANSPARENT模式，fitSystemWindow记得在布局中设置
      //因为如果需要使用软键盘，且需要adjustResize，TRANSPARENT模式会使adjustResize失效，布局还是原来的大小
      //需要自己根据输入法的是否显示来控制view的高度，所以一般情况下不弹软键盘的可以使用透明覆盖模式，如果使用
      //软键盘就使用非透明模式。不用自己计算布局的高度
      //Here's the magic..
      //Set the dialog to not focusable (makes navigation ignore us adding the window)
      window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
          WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)

      //Show the dialog!
      super.show()

      window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
      window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
      window?.statusBarColor = Color.TRANSPARENT

      val visibility: Int = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
          View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
          View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
      window?.decorView?.systemUiVisibility = visibility

      //设置状态栏字体颜色
      LightStatusBarCompat.setLightStatusBar(window, mTextColorBlack)

      //Clear the not focusable flag from the window
      window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
    } else {
      super.show()
    }
  }
}
