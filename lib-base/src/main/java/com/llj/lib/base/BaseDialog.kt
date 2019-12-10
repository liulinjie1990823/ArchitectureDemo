package com.llj.lib.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import butterknife.ButterKnife
import com.llj.lib.base.widget.LoadingDialog
import com.llj.lib.net.observer.ITaskId

/**
 * ArchitectureDemo
 * describe:
 * @author llj
 * @date 2018/5/24
 */
abstract class BaseDialog : Dialog, ILoadingDialogHandler {

    companion object {
        const val MATCH = ViewGroup.LayoutParams.MATCH_PARENT
        const val WRAP = ViewGroup.LayoutParams.WRAP_CONTENT
    }


    var mContext: Context


    constructor(context: Context, themeResId: Int) : super(context, themeResId) {
        mContext = context
    }

    constructor(context: Context) : super(context, R.style.dim_dialog) {
        mContext = context
    }

    val mTagLog: String = this.javaClass.simpleName

    private var mRequestDialog: ITaskId? = null

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
        setRequestId(hashCode())
    }

    override fun initLoadingDialog(): ITaskId? {
        return null
    }

    override fun getLoadingDialog(): ITaskId? {
        return mRequestDialog
    }

    //如果该RequestDialog和请求关联就设置tag
    override fun setRequestId(taskId: Int) {
        getLoadingDialog()?.setRequestId(taskId)
    }

    override fun getRequestId(): Int {
        return getLoadingDialog()?.getRequestId() ?: -1
    }

    //设置dialog透明模式
    override fun show() {
        //Here's the magic..
        //Set the dialog to not focusable (makes navigation ignore us adding the window)
        window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)

        //Show the dialog!
        super.show()

        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window?.statusBarColor = Color.TRANSPARENT
        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        //Clear the not focusable flag from the window
        window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)

    }
}
