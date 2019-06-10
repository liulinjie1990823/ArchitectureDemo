package com.llj.lib.base.widget

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import com.llj.lib.base.BaseDialog
import com.llj.lib.base.ITask
import com.llj.lib.base.MvcBaseActivity
import com.llj.lib.base.R
import com.llj.lib.utils.ADisplayUtils
import com.llj.lib.utils.LogUtil

/**
 * ArchitectureDemo
 * describe:
 * author llj
 * date 2018/5/24
 */
class LoadingDialog(context: Context) : BaseDialog(context, R.style.no_dim_dialog) {

    private var mTaskId: Int = -1

    private var mOnCustomerCancelListener: OnCustomerCancelListener? = null

    interface OnCustomerCancelListener {
        fun onCancel(dialog: DialogInterface)
    }

    fun setOnCustomerCancelListener(onCustomerCancelListener: OnCustomerCancelListener) {
        mOnCustomerCancelListener = onCustomerCancelListener
    }


    override fun layoutId(): Int {
        return R.layout.dialog_loading
    }

    override fun initViews() {
        setOnCancelListener {
            LogUtil.i(mTagLog, "cancelTask:" + getRequestId())
            when (context) {
                is MvcBaseActivity -> (context as ITask).removeDisposable(getRequestId())
            }

            mOnCustomerCancelListener?.onCancel(it)
        }
    }


    override fun setWindowParam() {
        val width = ADisplayUtils.dp2px(context, 120f)
        setWindowParams(width, width, Gravity.CENTER)
        setCanceledOnTouchOutside(false)
    }

    override fun performCreate(savedInstanceState: Bundle) {
        super.performCreate(savedInstanceState)

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

    override fun showLoadingDialog() {
        show()
    }

    override fun dismissLoadingDialog() {
        dismiss()
    }
}
