package com.llj.adapter.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.SparseArray
import android.view.View
import android.widget.*
import androidx.annotation.*
import androidx.core.content.ContextCompat

/**
 * PROJECT:babyphoto_app DESCRIBE: Created by llj on 2017/3/15.
 */
class HolderHelper : IHolder {

  private val itemView: View
  private val views: SparseArray<View>

  constructor(itemView: View) {
    this.itemView = itemView
    this.views = SparseArray<View>()
  }

  override val context: Context
    get() = itemView.context


  override fun removeFromCache(@IdRes id: Int) {
    views.remove(id)
  }

  override fun removeAll() {
    views.clear()
  }

  override fun <T : View> getView(@IdRes id: Int): T {
    var result: View? = views[id]
    if (result != null) {
      return result as T
    } else {
      result = itemView.findViewById<View>(id) as T?
      if (result == null) {
        throw RuntimeException()
      }
      views.put(id, result)
    }
    return result
  }

  override fun getText(@IdRes id: Int): CharSequence {
    val targetTxt = getView<TextView>(id)
    return targetTxt.text.toString().trim() ?: ""
  }

  override fun setText(@IdRes id: Int, text: CharSequence?): IHolder {
    val targetTxt = getView<TextView>(id)
    targetTxt.text = emptyIfNull(text)
    return this
  }

  override fun setText(@IdRes id: Int, @StringRes stringId: Int): IHolder {
    val targetTxt = getView<TextView>(id)
    targetTxt.text = emptyIfNull(context.getString(stringId))
    return this
  }

  override fun setTextTrim(@IdRes id: Int, @StringRes stringId: Int): IHolder {
    val targetTxt = getView<TextView>(id)
    targetTxt.text = emptyIfNull(context.getString(stringId).trim())
    return this
  }

  override fun setTextTrim(@IdRes id: Int, text: CharSequence?): IHolder {
    val targetTxt = getView<TextView>(id)
    targetTxt.text = emptyIfNull(text).toString().trim()
    return this
  }

  private fun emptyIfNull(str: CharSequence?): CharSequence {
    return str ?: ""
  }

  override fun setTextWithVisibility(@IdRes id: Int, text: CharSequence?): IHolder {
    val targetTxt = getView<TextView>(id)
    if (!TextUtils.isEmpty(text)) {
      targetTxt.visibility = View.VISIBLE
      targetTxt.text = text
    } else {
      targetTxt.visibility = View.GONE
    }
    return this
  }

  @SuppressLint("ResourceType")
  override fun setTextWithVisibility(@IdRes id: Int, @StringRes stringId: Int): IHolder {
    val targetTxt = getView<TextView>(id)
    if (stringId > 0) {
      targetTxt.visibility = View.VISIBLE
      targetTxt.text = context.getString(stringId)
    } else {
      targetTxt.visibility = View.GONE
    }
    return this
  }

  override fun setTextColor(@IdRes id: Int, @ColorInt textColor: Int): IHolder {
    val targetTxt = getView<TextView>(id)
    targetTxt.setTextColor(textColor)
    return this
  }

  override fun setTextColorRes(@IdRes id: Int, @ColorRes textColor: Int): IHolder {
    val targetTxt = getView<TextView>(id)
    targetTxt.setTextColor(ContextCompat.getColor(context, textColor))
    return this
  }

  override fun setSelected(@IdRes id: Int, selected: Boolean): IHolder {
    val targetTxt = getView<View>(id)
    targetTxt.isSelected = selected
    return this
  }

  override fun setEnabled(@IdRes id: Int, enabled: Boolean): IHolder {
    val targetTxt = getView<View>(id)
    targetTxt.isEnabled = enabled
    return this
  }

  override fun setVisibility(@IdRes id: Int, visibility: Int): IHolder {
    val targetTxt = getView<View>(id)
    targetTxt.visibility = visibility
    return this
  }

  override fun setVisible(@IdRes id: Int): IHolder {
    val targetTxt = getView<View>(id)
    targetTxt.visibility = View.VISIBLE
    return this
  }

  override fun setInvisible(@IdRes id: Int): IHolder {
    val targetTxt = getView<View>(id)
    targetTxt.visibility = View.INVISIBLE
    return this
  }

  override fun setGone(@IdRes id: Int): IHolder {
    val targetTxt = getView<View>(id)
    targetTxt.visibility = View.GONE
    return this
  }

  override fun setImageResource(@IdRes id: Int, @DrawableRes res: Int): IHolder {
    val view = getView<ImageView>(id)
    view.setImageResource(res)
    return this
  }

  override fun setImageBitmap(@IdRes id: Int, bitmap: Bitmap?): IHolder {
    val view = getView<ImageView>(id)
    view.setImageBitmap(bitmap)
    return this
  }

  override fun setImageDrawable(@IdRes id: Int, drawable: Drawable?): IHolder {
    val view = getView<ImageView>(id)
    view.setImageDrawable(drawable)
    return this
  }

  override fun setBackgroundResource(@IdRes id: Int, @DrawableRes res: Int): IHolder {
    val view = getView<View>(id)
    view.setBackgroundResource(res)
    return this
  }

  override fun setBackgroundBitmap(@IdRes id: Int, bitmap: Bitmap?): IHolder {
    val view = getView<View>(id)
    view.setBackgroundDrawable(BitmapDrawable(context.resources, bitmap))
    return this
  }

  override fun setBackgroundDrawable(@IdRes id: Int, drawable: Drawable?): IHolder {
    val view = getView<View>(id)
    view.setBackgroundDrawable(drawable)
    return this
  }

  override fun setOnItemClickListener(listener: View.OnClickListener?): IHolder {
    itemView.setOnClickListener(listener)
    return this
  }

  override fun setOnClickListener(@IdRes id: Int, onClickListener: View.OnClickListener?): IHolder {
    getView<View>(id).setOnClickListener(onClickListener)
    return this
  }

  override fun setOnLongClickListener(
      @IdRes id: Int, onLongClickListener: View.OnLongClickListener?): IHolder {
    getView<View>(id).setOnLongClickListener(onLongClickListener)
    return this
  }

  override fun setTag(@IdRes viewId: Int, tag: Any?): IHolder {
    val view = getView<View>(viewId)
    view.tag = tag
    return this
  }

  override fun setTag(@IdRes viewId: Int, key: Int, tag: Any?): IHolder {
    val view = getView<View>(viewId)
    view.setTag(key, tag)
    return this
  }

  override fun setTypeface(typeface: Typeface?, vararg viewIds: Int): IHolder {
    for (viewId in viewIds) {
      val view = getView<TextView>(viewId)
      if (view != null) {
        view.typeface = typeface
        view.paintFlags = view.paintFlags or Paint.SUBPIXEL_TEXT_FLAG
      }
    }
    return this
  }

  override fun setProgress(@IdRes viewId: Int, progress: Int): IHolder {
    val view = getView<ProgressBar>(viewId)
    view.progress = progress
    return this
  }

  override fun setProgress(@IdRes viewId: Int, progress: Int, max: Int): IHolder {
    val view = getView<ProgressBar>(viewId)
    view.max = max
    view.progress = progress
    return this
  }

  override fun setMax(@IdRes viewId: Int, max: Int): IHolder {
    val view = getView<ProgressBar>(viewId)
    view.max = max
    return this
  }

  override fun setRating(@IdRes viewId: Int, rating: Float): IHolder {
    val view = getView<RatingBar>(viewId)
    view.rating = rating
    return this
  }

  override fun setRating(@IdRes viewId: Int, rating: Float, max: Int): IHolder {
    val view = getView<RatingBar>(viewId)
    view.max = max
    view.rating = rating
    return this
  }

  override fun setChecked(@IdRes viewId: Int, checked: Boolean): IHolder {
    val view1 = getView<View>(viewId)
    val view: Checkable = view1 as Checkable
    view.isChecked = checked
    return this
  }

}