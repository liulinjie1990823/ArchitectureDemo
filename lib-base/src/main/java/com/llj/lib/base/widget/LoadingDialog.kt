package com.llj.lib.base.widget

import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import com.llj.lib.base.BaseDialog
import com.llj.lib.base.ITask
import com.llj.lib.base.R
import com.llj.lib.utils.ADisplayUtils
import java.lang.ref.WeakReference

/**
 * ArchitectureDemo
 *
 * describe:加载框
 * @author llj
 * @date 2018/5/24
 */
class LoadingDialog : BaseDialog {

  constructor(context: Context) : super(context, R.style.no_dim_dialog) {
    this.mTaskId = -1
  }

  private var mTaskId: Int
  private var mOnCustomerCancelListener: OnCustomerCancelListener? = null

  interface OnCustomerCancelListener {
    fun onCancel(dialog: DialogInterface?)
  }

  fun setOnCustomerCancelListener(onCustomerCancelListener: OnCustomerCancelListener) {
    mOnCustomerCancelListener = onCustomerCancelListener
  }

  private class MyOnCancelListener : DialogInterface.OnCancelListener {
    val mActivity: WeakReference<ITask>
    val mRequestId: Int
    val mOnCustomerCancelListener: OnCustomerCancelListener?

    constructor(activity: ITask, requestId: Int, onCustomerCancelListener: OnCustomerCancelListener?) {
      this.mActivity = WeakReference<ITask>(activity)
      this.mRequestId = requestId
      this.mOnCustomerCancelListener = onCustomerCancelListener
    }

    override fun onCancel(dialog: DialogInterface?) {
      mActivity.get()?.removeDisposable(mRequestId)
      //回调监听
      mOnCustomerCancelListener?.onCancel(dialog)
    }
  }


  override fun layoutId(): Int {
    return R.layout.dialog_loading
  }

  override fun initViews() {
    if (mContext is ITask) {
      setOnCancelListener(MyOnCancelListener(mContext as ITask, getRequestId(), mOnCustomerCancelListener))
    }

//    setOnCancelListener { dialogInterface ->
//      LogUtil.i(mTagLog, "cancelTask:" + getRequestId())
//
//      //移除任务
//      mContext.let {
//        if (it is ITask) {
//          it.removeDisposable(getRequestId())
//        }
//      }
//
//      //回调监听
//      mOnCustomerCancelListener?.onCancel(dialogInterface)
//    }
  }


  override fun setWindowParam() {
    val width = ADisplayUtils.dp2px(context, 120f)
    setWindowParams(width, width, Gravity.CENTER)
    setCanceledOnTouchOutside(false)
  }


  override fun needLoadingDialog(): Boolean {
    return false
  }


  override fun setRequestId(taskId: Int) {
    mTaskId = taskId
  }

  override fun getRequestId(): Int {
    return mTaskId
  }

  //需要重写
  override fun showLoadingDialog() {
    show()
  }

  //需要重写
  override fun dismissLoadingDialog() {
    dismiss()
  }
}
