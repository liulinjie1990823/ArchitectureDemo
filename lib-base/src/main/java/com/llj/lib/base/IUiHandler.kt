package com.llj.lib.base

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView

import com.llj.lib.base.listeners.OnMyClickListener
import com.llj.lib.utils.ACollectionUtils
import com.llj.lib.utils.ADisplayUtils
import com.llj.lib.utils.ATextUtils
import com.llj.lib.utils.AToastUtils

/**
 * ArchitectureDemo
 * describe:
 * author llj
 * date 2018/5/24
 */
interface IUiHandler {


    ///////////////////////////////////////////////////////////////////////////
    // ui相关操作
    ///////////////////////////////////////////////////////////////////////////

    fun setOnClickListener(view: View, onClickListener: View.OnClickListener?) {
        if (onClickListener == null) {
            view.setOnClickListener(null)
        } else {
            view.setOnClickListener(object : OnMyClickListener() {
                override fun onCanClick(v: View) {
                    onClickListener.onClick(v)
                }
            })
        }
    }

    fun getTextStr(textView: TextView): CharSequence {
        return ATextUtils.getText(textView)
    }

    fun setText(textView: TextView, destination: CharSequence) {
        ATextUtils.setText(textView, destination)
    }

    fun setText(textView: TextView, destination: CharSequence, defaultStr: CharSequence) {
        ATextUtils.setText(textView, destination, defaultStr)
    }

    fun showToast(content: String) {
        AToastUtils.show(content)
    }

    fun showToast(@StringRes resId: Int) {
        AToastUtils.show(resId)
    }

    fun showLongToast(content: String) {
        AToastUtils.showLong(content)
    }

    fun showLongToast(@StringRes resId: Int) {
        AToastUtils.showLong(resId)
    }

    fun isEmpty(text: CharSequence): Boolean {
        return android.text.TextUtils.isEmpty(text)
    }

    fun isEmpty(textView: TextView): Boolean {
        return android.text.TextUtils.isEmpty(getTextStr(textView))
    }

    fun isEmpty(list: Collection<*>): Boolean {
        return ACollectionUtils.isEmpty<Any>(list)
    }

    fun setTextColor(textView: TextView, @ColorRes id: Int) {
        textView.setTextColor(ContextCompat.getColor(textView.context, id))
    }

    fun getCompatColor(context: Context, @ColorRes id: Int): Int {
        return ContextCompat.getColor(context, id)
    }

    fun getCompatDrawable(context: Context, @DrawableRes id: Int): Drawable? {
        return ContextCompat.getDrawable(context, id)
    }

    fun dip2px(context: Context, dpValue: Int): Int {
        return ADisplayUtils.dp2px(context, dpValue.toFloat())
    }

    fun nullToEmpty(destination: CharSequence?): CharSequence {
        return destination ?: ""
    }
}
