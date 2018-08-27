package com.llj.lib.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.ViewGroup
import butterknife.ButterKnife
import com.llj.lib.base.widget.LoadingDialog
import com.llj.lib.net.observer.ITag

/**
 * ArchitectureDemo
 * describe:
 * author llj
 * date 2018/5/24
 */
abstract class BaseDialog : Dialog, ILoadingDialogHandler {

    companion object {
        const val MATCH = ViewGroup.LayoutParams.MATCH_PARENT
        const val WRAP = ViewGroup.LayoutParams.WRAP_CONTENT
    }

    constructor(context: Context?) : super(context, R.style.dim_dialog)
    constructor(context: Context?, themeResId: Int) : super(context, themeResId)

    val mTagLog: String = this.javaClass.simpleName

    private var mRequestDialog: ITag? = null

    init {
        bindViews()
        initViews()
    }

    private fun bindViews() {
        setContentView(layoutId())
        ButterKnife.bind(this)
    }

    @LayoutRes
    abstract fun layoutId(): Int

    abstract fun initViews()


    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        // 第一次show的时候会调用该方法
        // bindViews();
        // initViews();
        setWindowParam()
        if (needLoadingDialog()) {
            checkRequestDialog()
        }
        performCreate(savedInstanceState)
    }

    protected open fun performCreate(savedInstanceState: Bundle) {}
    protected abstract fun setWindowParam()


    protected fun setWindowParams(gravity: Int) {
        setWindowParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, gravity)
    }

    protected fun setWindowParams(height: Int, gravity: Int) {
        setWindowParams(ViewGroup.LayoutParams.MATCH_PARENT, height, gravity)
    }

    /**
     * 在设置 设置dialog的一些属性
     *
     * @param width   一般布局和代码这里都设置match,要设置边距的直接布局里调好
     * @param height  一般布局height设置为wrap，这样可以调整dialog的上中下位置，要固定(非上中下)位置的直接在布局中调整， 设置match后，软键盘不会挤压布局
     * @param gravity 设置match后，此属性无用
     */
    fun setWindowParams(width: Int, height: Int, gravity: Int) {
        //         setCancelable(cancelable);
        //         setCanceledOnTouchOutside(cancel);
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


    open fun needLoadingDialog(): Boolean {
        return true
    }

    private fun checkRequestDialog() {
        if (mRequestDialog == null) {
            mRequestDialog = initLoadingDialog()
            if (mRequestDialog == null) {
                mRequestDialog = LoadingDialog(context)
            }
        }
        setRequestTag(hashCode())
    }

    override fun initLoadingDialog(): ITag? {
        return null
    }

    override fun getLoadingDialog(): ITag? {
        return mRequestDialog
    }

    //如果该RequestDialog和请求关联就设置tag
    override fun setRequestTag(tag: Any) {
        getLoadingDialog()?.setRequestTag(tag)
    }

    override fun getRequestTag(): Any {
        return getLoadingDialog()?.getRequestTag() ?: -1
    }


}
