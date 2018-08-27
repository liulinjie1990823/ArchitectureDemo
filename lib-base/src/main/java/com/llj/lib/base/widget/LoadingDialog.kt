package com.llj.lib.base.widget

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import com.llj.lib.base.BaseDialog
import com.llj.lib.base.ITask
import com.llj.lib.base.MvcBaseActivity
import com.llj.lib.base.R
import com.llj.lib.net.observer.ITag
import com.llj.lib.utils.ADisplayUtils
import com.llj.lib.utils.LogUtil

/**
 * ArchitectureDemo
 * describe:
 * author llj
 * date 2018/5/24
 */
class LoadingDialog(context: Context)
    : BaseDialog(context, R.style.no_dim_dialog),
        ITag {

    private var mTag: Any = -1

    override fun layoutId(): Int {
        return R.layout.dialog_loading
    }

    override fun initViews() {
        setOnCancelListener {
            LogUtil.i(mTagLog, "cancelTask:" + getRequestTag())
            when (context) {
                is MvcBaseActivity -> (context as ITask).removeDisposable(getRequestTag())
            }
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


    override fun setRequestTag(tag: Any) {
        mTag = tag
    }

    override fun getRequestTag(): Any {
        return mTag
    }

    override fun showLoadingDialog() {
        show()
    }

    override fun dismissLoadingDialog() {
        dismiss()
    }
}
