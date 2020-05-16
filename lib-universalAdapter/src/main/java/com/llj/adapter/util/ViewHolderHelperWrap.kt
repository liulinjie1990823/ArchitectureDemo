package com.llj.adapter.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.OnLongClickListener
import androidx.annotation.*
import androidx.viewbinding.ViewBinding
import com.llj.adapter.XViewHolder


open class ViewHolderHelperWrap<T : ViewBinding> : XViewHolder, IHolder {

  private var mHolderHelper: HolderHelper
  lateinit var mViewBinder: T


  constructor(viewBinder: T) : super(viewBinder.root) {
    this.mHolderHelper = HolderHelper(viewBinder.root)
    this.mViewBinder = viewBinder
  }

  constructor(itemView: View) : super(itemView) {
    this.mHolderHelper = HolderHelper(itemView)
  }


  override val context: Context
    get() = mHolderHelper.context


  override fun removeFromCache(@IdRes id: Int) {
    mHolderHelper.removeFromCache(id)
  }

  override fun removeAll() {
    mHolderHelper.removeAll()
  }


  override fun <T : View> getView(@IdRes id: Int): T {
    return mHolderHelper.getView(id)
  }

  override fun getText(@IdRes id: Int): CharSequence {
    return mHolderHelper.getText(id)
  }

  override fun setText(@IdRes id: Int, text: CharSequence?): IHolder {
    return mHolderHelper.setText(id, text)
  }

  override fun setText(@IdRes id: Int, @StringRes stringId: Int): IHolder {
    return mHolderHelper.setText(id, stringId)
  }

  override fun setTextTrim(@IdRes id: Int, text: CharSequence?): IHolder {
    return mHolderHelper.setTextTrim(id, text)
  }

  override fun setTextTrim(@IdRes id: Int, @StringRes stringId: Int): IHolder {
    return mHolderHelper.setTextTrim(id, stringId)
  }

  override fun setTextWithVisibility(@IdRes id: Int, text: CharSequence?): IHolder {
    return mHolderHelper.setTextWithVisibility(id, text)
  }

  override fun setTextWithVisibility(@IdRes id: Int, @StringRes stringId: Int): IHolder {
    return mHolderHelper.setTextWithVisibility(id, stringId)
  }

  override fun setTextColor(@IdRes id: Int, @ColorInt textColor: Int): IHolder {
    return mHolderHelper.setTextColor(id, textColor)
  }

  override fun setTextColorRes(@IdRes id: Int, @ColorRes textColor: Int): IHolder {
    return mHolderHelper.setTextColorRes(id, textColor)
  }

  override fun setSelected(@IdRes id: Int, selected: Boolean): IHolder {
    return mHolderHelper.setSelected(id, selected)
  }

  override fun setEnabled(@IdRes id: Int, enabled: Boolean): IHolder {
    return mHolderHelper.setEnabled(id, enabled)
  }

  override fun setVisibility(@IdRes id: Int, visibility: Int): IHolder {
    return mHolderHelper.setVisibility(id, visibility)
  }

  override fun setVisible(@IdRes id: Int): IHolder {
    return mHolderHelper.setVisible(id)
  }

  override fun setInvisible(@IdRes id: Int): IHolder {
    return mHolderHelper.setInvisible(id)
  }

  override fun setGone(@IdRes id: Int): IHolder {
    return mHolderHelper.setGone(id)
  }

  override fun setImageResource(@IdRes id: Int, @DrawableRes res: Int): IHolder {
    return mHolderHelper.setImageResource(id, res)
  }

  override fun setImageBitmap(@IdRes id: Int, bitmap: Bitmap?): IHolder {
    return mHolderHelper.setImageBitmap(id, bitmap)
  }

  override fun setImageDrawable(@IdRes id: Int, drawable: Drawable?): IHolder {
    return mHolderHelper.setImageDrawable(id, drawable)
  }

  override fun setBackgroundResource(@IdRes id: Int, @DrawableRes res: Int): IHolder {
    return mHolderHelper.setBackgroundResource(id, res)
  }

  override fun setBackgroundBitmap(@IdRes id: Int, bitmap: Bitmap?): IHolder {
    return mHolderHelper.setBackgroundBitmap(id, bitmap)
  }

  override fun setBackgroundDrawable(@IdRes id: Int, drawable: Drawable?): IHolder {
    return mHolderHelper.setBackgroundDrawable(id, drawable)
  }

  override fun setOnItemClickListener(listener: View.OnClickListener?): IHolder {
    return mHolderHelper.setOnItemClickListener(listener)
  }

  override fun setOnClickListener(@IdRes id: Int, onClickListener: View.OnClickListener?): IHolder {
    return mHolderHelper.setOnClickListener(id, onClickListener)
  }

  override fun setOnLongClickListener(
      @IdRes id: Int, onLongClickListener: OnLongClickListener?): IHolder {
    return mHolderHelper.setOnLongClickListener(id, onLongClickListener)
  }

  override fun setTag(@IdRes viewId: Int, tag: Any?): IHolder {
    return mHolderHelper.setTag(viewId, tag)
  }

  override fun setTag(@IdRes viewId: Int, key: Int, tag: Any?): IHolder {
    return mHolderHelper.setTag(viewId, key, tag)
  }

  override fun setTypeface(typeface: Typeface?, vararg viewIds: Int): IHolder {
    return mHolderHelper.setTypeface(typeface, *viewIds)
  }

  override fun setProgress(@IdRes viewId: Int, progress: Int): IHolder {
    return mHolderHelper.setProgress(viewId, progress)
  }

  override fun setProgress(@IdRes viewId: Int, progress: Int, max: Int): IHolder {
    return mHolderHelper.setProgress(viewId, progress, max)
  }

  override fun setMax(@IdRes viewId: Int, max: Int): IHolder {
    return mHolderHelper.setMax(viewId, max)
  }

  override fun setRating(@IdRes viewId: Int, rating: Float): IHolder {
    return mHolderHelper.setRating(viewId, rating)
  }

  override fun setRating(@IdRes viewId: Int, rating: Float, max: Int): IHolder {
    return mHolderHelper.setRating(viewId, rating, max)
  }

  override fun setChecked(@IdRes viewId: Int, checked: Boolean): IHolder {
    return mHolderHelper.setChecked(viewId, checked)
  }

  companion object {

    @JvmStatic
    fun <T : ViewBinding> createViewHolder(viewBinder: T): ViewHolderHelperWrap<T> {
      return ViewHolderHelperWrap(viewBinder)
    }
  }

}