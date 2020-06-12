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


  override fun layoutId(): Int {
    return R.layout.dialog_loading
  }

  override fun initViews() {
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

  override fun onLoadingDialogCancel(dialog: DialogInterface?) {
  }

  override fun onLoadingDialogDismiss(dialog: DialogInterface?) {
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
