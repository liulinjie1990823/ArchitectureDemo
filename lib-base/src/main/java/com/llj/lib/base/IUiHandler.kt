package com.llj.lib.base

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.llj.lib.utils.ACollectionUtils
import com.llj.lib.utils.ADisplayUtils
import com.llj.lib.utils.ATextUtils
import com.llj.lib.utils.AToastUtils

/**
 * ArchitectureDemo
 * describe: 相关ui控制
 * @author llj
 * @date 2018/5/24
 */
interface IUiHandler {


    ///////////////////////////////////////////////////////////////////////////
    // ui相关操作
    ///////////////////////////////////////////////////////////////////////////

    fun getTextStr(textView: TextView?): String {
        return ATextUtils.getText(textView)
    }

    fun setText(textView: TextView?, destination: CharSequence?) {
        ATextUtils.setText(textView, destination)
    }

    fun setText(textView: TextView?, destination: CharSequence?, defaultStr: CharSequence) {
        ATextUtils.setText(textView, destination, defaultStr)
    }

    fun setTextWithVisibility(textView: TextView?, destination: CharSequence?) {
        ATextUtils.setTextWithVisibility(textView, destination)
    }

    fun showToast(content: CharSequence?) {
        AToastUtils.show(content)
    }

    fun showToast(@StringRes resId: Int) {
        AToastUtils.show(resId)
    }

    fun showLongToast(content: CharSequence?) {
        AToastUtils.showLong(content)
    }

    fun showLongToast(@StringRes resId: Int) {
        AToastUtils.showLong(resId)
    }

    fun isEmpty(text: CharSequence?): Boolean {
        return android.text.TextUtils.isEmpty(text)
    }

    fun isEmpty(textView: TextView): Boolean {
        return android.text.TextUtils.isEmpty(getTextStr(textView))
    }

    fun isEmpty(list: Collection<Any?>?): Boolean {
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


    fun getCustomDrawable(context: Context,
                          conner: Float, @ColorRes bgColor: Int): Drawable {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.setColor(getCompatColor(context, bgColor))
        drawable.cornerRadius = conner
        return drawable
    }

    fun getCustomDrawable(context: Context, shape: Int,
                          conner: Float, @ColorRes bgColor: Int): Drawable {
        val drawable = GradientDrawable()
        drawable.shape = shape
        drawable.setColor(getCompatColor(context, bgColor))
        drawable.cornerRadius = conner
        return drawable
    }


    fun getCustomDrawable(context: Context,
                          connerLAT: Float, connerRAT: Float, @ColorRes bgColor: Int): Drawable {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.setColor(getCompatColor(context, bgColor))
        val radios = floatArrayOf(connerLAT, connerLAT, connerRAT, connerRAT, 0f, 0f, 0f, 0f)
        drawable.cornerRadii = radios
        return drawable
    }

    fun getCustomDrawable(context: Context, shape: Int,
                          connerLAT: Float, connerRAT: Float, @ColorRes bgColor: Int): Drawable {
        val drawable = GradientDrawable()
        drawable.shape = shape
        drawable.setColor(getCompatColor(context, bgColor))
        val radios = floatArrayOf(connerLAT, connerLAT, connerRAT, connerRAT, 0f, 0f, 0f, 0f)
        drawable.cornerRadii = radios
        return drawable
    }

    fun getCustomDrawable(context: Context,
                          connerLAT: Float, connerRAT: Float, @ColorRes bgColor: Int,
                          strokeWidth: Int, @ColorRes strokeColor: Int): Drawable {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.setColor(getCompatColor(context, bgColor))
        val radios = floatArrayOf(connerLAT, connerLAT, connerRAT, connerRAT, 0f, 0f, 0f, 0f)
        drawable.cornerRadii = radios
        drawable.setStroke(strokeWidth, getCompatColor(context, strokeColor))
        return drawable
    }

    fun getCustomDrawable(context: Context, shape: Int,
                          connerLAT: Float, connerRAT: Float, @ColorRes bgColor: Int,
                          strokeWidth: Int, @ColorRes strokeColor: Int): Drawable {
        val drawable = GradientDrawable()
        drawable.shape = shape
        drawable.setColor(getCompatColor(context, bgColor))
        val radios = floatArrayOf(connerLAT, connerLAT, connerRAT, connerRAT, 0f, 0f, 0f, 0f)
        drawable.cornerRadii = radios
        drawable.setStroke(strokeWidth, getCompatColor(context, strokeColor))
        return drawable
    }

    fun getCustomDrawable(context: Context,
                          conner: Float, @ColorRes bgColor: Int,
                          strokeWidth: Int, @ColorRes strokeColor: Int): Drawable {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.setColor(getCompatColor(context, bgColor))
        drawable.cornerRadius = conner
        drawable.setStroke(strokeWidth, getCompatColor(context, strokeColor))
        return drawable
    }

    fun getCustomDrawable(context: Context, shape: Int,
                          conner: Float, @ColorRes bgColor: Int,
                          strokeWidth: Int, @ColorRes strokeColor: Int): Drawable {
        val drawable = GradientDrawable()
        drawable.shape = shape
        drawable.setColor(getCompatColor(context, bgColor))
        drawable.cornerRadius = conner
        drawable.setStroke(strokeWidth, getCompatColor(context, strokeColor))
        return drawable
    }


    fun getCustomDrawable(context: Context, shape: Int,
                          conner: FloatArray, @ColorRes bgColor: Int,
                          strokeWidth: Int, @ColorRes strokeColor: Int,
                          dashWidth: Float, dashGap: Float): Drawable {
        val drawable = GradientDrawable()
        drawable.shape = shape
        drawable.setColor(getCompatColor(context, bgColor))
        drawable.cornerRadii = conner
        drawable.setStroke(strokeWidth, getCompatColor(context, bgColor))
        return drawable
    }

    fun dip2px(context: Context, dpValue: Float): Int {
        return ADisplayUtils.dp2px(context, dpValue)
    }

    fun nullToEmpty(destination: CharSequence?): CharSequence {
        return destination ?: ""
    }

    fun getMiddle(value: Float, minValue: Float, maxValue: Float): Float {
        return Math.min(maxValue, Math.max(minValue, value))
    }

    fun getMiddle(value: Double, minValue: Double, maxValue: Double): Double {
        return Math.min(maxValue, Math.max(minValue, value))
    }

    fun getMiddle(value: Int, minValue: Int, maxValue: Int): Int {
        return Math.min(maxValue, Math.max(minValue, value))
    }


    fun isVisible(view: View): Boolean {
        return view.visibility == View.VISIBLE
    }

    fun isInVisible(view: View): Boolean {
        return view.visibility == View.INVISIBLE
    }

    fun isNotVisible(view: View): Boolean {
        return view.visibility == View.INVISIBLE || view.visibility == View.GONE
    }

    fun isGone(view: View): Boolean {
        return view.visibility == View.GONE
    }

}
