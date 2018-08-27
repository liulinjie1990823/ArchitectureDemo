package com.llj.lib.base

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow

import butterknife.ButterKnife

/**
 * dmp_hunbohui
 * describe:
 * author liulj
 * date 2017/12/14
 */

abstract class BasePopupWindow(width: Int, height: Int, var mContext: Context)
    : PopupWindow(width, height) {
    private var mShowAlpha = 1.0f

    abstract val layoutId: Int

    private var mOnDismissListener: PopupWindow.OnDismissListener? = null

    init {
        initParams(mContext)
        bindViews()
        initViews()
    }

    /**
     * @param context
     */
    private fun initParams(context: Context) {
        // 在PopupWindow里面就加上下面代码，让键盘弹出时，不会挡住pop窗口。
        inputMethodMode = PopupWindow.INPUT_METHOD_NEEDED
        softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            elevation = 20f
        }
        // 这个设置了可以按返回键dismiss
        isFocusable = true
        // 可以点击外面dismiss需要一下两个条件
        setBackgroundDrawable(ColorDrawable())
        isOutsideTouchable = true
        // 添加pop窗口关闭事件
        setOnDismissListener {
            backgroundAlpha(context, 1f)
            if (mOnDismissListener != null) {
                mOnDismissListener!!.onDismiss()
            }
        }
    }

    private fun bindViews() {
        val view = View.inflate(mContext, layoutId, null)
        contentView = view
        ButterKnife.bind(this, view)
    }

    fun setOnMyDismissListener(onDismissListener: PopupWindow.OnDismissListener) {
        mOnDismissListener = onDismissListener
    }


    fun setShowAlpha(bgAlpha: Float) {
        this.mShowAlpha = bgAlpha
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun showAsDropDown(anchor: View, xoff: Int, yoff: Int, gravity: Int, showAlpha: Float) {
        backgroundAlpha(mContext, showAlpha)
        this.showAsDropDown(anchor, xoff, yoff, gravity)
    }

    fun showAsDropDown(anchor: View, showAlpha: Float) {
        backgroundAlpha(mContext, showAlpha)
        this.showAsDropDown(anchor)
    }

    /**
     * 根据view来设置位置
     *
     * @param anchor
     * @param xoff
     * @param yoff
     * @param showAlpha
     */
    fun showAsDropDown(anchor: View, xoff: Int, yoff: Int, showAlpha: Float) {
        backgroundAlpha(mContext, showAlpha)
        this.showAsDropDown(anchor, xoff, yoff)
    }

    /**
     * 根据全屏来设置位置
     *
     * @param parent    可以是屏幕内的任何的view,为的是获得token
     * @param gravity
     * @param x
     * @param y
     * @param showAlpha
     */
    fun showAtLocation(parent: View, gravity: Int, x: Int, y: Int, showAlpha: Float) {
        backgroundAlpha(mContext, showAlpha)
        this.showAtLocation(parent, gravity, x, y)
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    private fun backgroundAlpha(context: Context, bgAlpha: Float) {
        mShowAlpha = bgAlpha
        val lp = (context as Activity).window.attributes
        if (mShowAlpha != lp.alpha) {
            lp.alpha = bgAlpha // 0.0-1.0
            context.window.attributes = lp
        }
    }


    override fun showAsDropDown(anchor: View) {
        onShow()
        showAsDropDownCompat(anchor)
        super.showAsDropDown(anchor)
    }

    override fun showAsDropDown(anchor: View, xoff: Int, yoff: Int, gravity: Int) {
        onShow()
        showAsDropDownCompat(anchor)
        super.showAsDropDown(anchor, xoff, yoff, gravity)
    }

    override fun showAsDropDown(anchor: View, xoff: Int, yoff: Int) {
        onShow()
        showAsDropDownCompat(anchor)
        super.showAsDropDown(anchor, xoff, yoff)
    }

    override fun showAtLocation(parent: View, gravity: Int, x: Int, y: Int) {
        onShow()
        super.showAtLocation(parent, gravity, x, y)
    }

    private fun showAsDropDownCompat(anchor: View) {
        if (Build.VERSION.SDK_INT == 24) {
            val rect = Rect()
            anchor.getGlobalVisibleRect(rect)
            val h = anchor.resources.displayMetrics.heightPixels - rect.bottom
            height = h
        }
    }


    private fun onShow() {}

    private fun initViews() {}

}
