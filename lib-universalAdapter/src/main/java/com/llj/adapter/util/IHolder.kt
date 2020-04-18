package com.llj.adapter.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.OnLongClickListener
import androidx.annotation.*


interface IHolder {
    val context: Context
    fun removeFromCache(@IdRes id: Int)
    fun removeAll()

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    fun <T : View> getView(@IdRes id: Int): T
    fun getText(@IdRes id: Int): CharSequence?
    fun setText(@IdRes id: Int, text: CharSequence?): IHolder
    fun setText(@IdRes id: Int, @StringRes stringId: Int): IHolder
    fun setTextTrim(@IdRes id: Int, text: CharSequence?): IHolder
    fun setTextTrim(@IdRes id: Int, @StringRes stringId: Int): IHolder
    fun setTextWithVisibility(@IdRes id: Int, text: CharSequence?): IHolder
    fun setTextWithVisibility(@IdRes id: Int, @StringRes stringId: Int): IHolder
    fun setTextColor(@IdRes id: Int, @ColorInt textColor: Int): IHolder
    fun setTextColorRes(@IdRes id: Int, @ColorRes textColor: Int): IHolder

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    fun setSelected(@IdRes id: Int, selected: Boolean): IHolder
    fun setEnabled(@IdRes id: Int, enabled: Boolean): IHolder
    fun setVisibility(@IdRes id: Int, visibility: Int): IHolder
    fun setVisible(@IdRes id: Int): IHolder
    fun setInvisible(@IdRes id: Int): IHolder
    fun setGone(@IdRes id: Int): IHolder

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    fun setImageResource(@IdRes id: Int, @DrawableRes res: Int): IHolder
    fun setImageBitmap(@IdRes id: Int, bitmap: Bitmap?): IHolder
    fun setImageDrawable(@IdRes id: Int, drawable: Drawable?): IHolder

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    fun setBackgroundResource(@IdRes id: Int, @DrawableRes res: Int): IHolder
    fun setBackgroundBitmap(@IdRes id: Int, bitmap: Bitmap?): IHolder
    fun setBackgroundDrawable(@IdRes id: Int, drawable: Drawable?): IHolder

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    fun setOnItemClickListener(listener: View.OnClickListener?): IHolder
    fun setOnClickListener(@IdRes id: Int, onClickListener: View.OnClickListener?): IHolder
    fun setOnLongClickListener(@IdRes id: Int, onLongClickListener: OnLongClickListener?): IHolder

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    fun setTag(@IdRes viewId: Int, tag: Any?): IHolder
    fun setTag(@IdRes viewId: Int, key: Int, tag: Any?): IHolder

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    fun setTypeface(typeface: Typeface?, vararg viewIds: Int): IHolder
    fun setProgress(@IdRes viewId: Int, progress: Int): IHolder
    fun setProgress(@IdRes viewId: Int, progress: Int, max: Int): IHolder
    fun setMax(@IdRes viewId: Int, max: Int): IHolder
    fun setRating(@IdRes viewId: Int, rating: Float): IHolder
    fun setRating(@IdRes viewId: Int, rating: Float, max: Int): IHolder
    fun setChecked(@IdRes viewId: Int, checked: Boolean): IHolder
}