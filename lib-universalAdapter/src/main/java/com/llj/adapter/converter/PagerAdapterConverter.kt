package com.llj.adapter.converter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.llj.adapter.UniversalAdapter
import com.llj.adapter.UniversalConverter
import com.llj.adapter.XViewHolder
import com.llj.adapter.listener.ItemClickListener
import com.llj.adapter.listener.ItemClickWrapper
import com.llj.adapter.listener.ItemListenerAdapter
import com.llj.adapter.observable.ListObserver
import com.llj.adapter.observable.ListObserverListener
import com.llj.adapter.observable.SimpleListObserverListener
import com.llj.adapter.util.ThreadingUtils
import com.llj.adapter.util.UniversalAdapterUtils.setViewHolder

/**
 * PagerAdapterConverter
 *
 * @author liulinjie
 * @date 2017/2/11
 */
class PagerAdapterConverter<Item, Holder : XViewHolder>
internal constructor(universalAdapter: UniversalAdapter<Item, Holder>, viewPager: ViewPager) : PagerAdapter(),
  ItemListenerAdapter<Item, Holder>,
  UniversalConverter<Item, Holder> {

  private var mUniversalAdapter: UniversalAdapter<Item, Holder>? = null
  private val mItemClickedWrapper: ItemClickWrapper<Item, Holder>
  private val mObserverListener: ListObserverListener<Item>
  private val mSuperDataSetChangedRunnable: Runnable = Runnable { superNotifyDataSetChanged() }

  init {
    this.mObserverListener = object : SimpleListObserverListener<Item>() {
      override fun onGenericChange(observer: ListObserver<Item>) {
        superNotifyDataSetChangedOnUIThread()
      }
    }
    universalAdapter.checkIfBoundAndSet()
    mItemClickedWrapper = ItemClickWrapper(this)
    setAdapter(universalAdapter)
    viewPager.adapter = this
    superNotifyDataSetChanged()
  }


  ///////////////////////////////////////////////////////////////////////////
  //
  ///////////////////////////////////////////////////////////////////////////
  override fun setItemClickedListener(listener: ItemClickListener<Item, Holder>) {
    getAdapter().setItemClickedListener(listener)
  }


  ///////////////////////////////////////////////////////////////////////////
  //
  ///////////////////////////////////////////////////////////////////////////
  override fun getAdapter(): UniversalAdapter<Item, Holder> {
    return mUniversalAdapter!!
  }

  override fun setAdapter(listAdapter: UniversalAdapter<Item, Holder>) {
    if (mUniversalAdapter != null) {
      mUniversalAdapter!!.mListObserver.removeListener(mObserverListener)
    }
    mUniversalAdapter = listAdapter
    getAdapter().mListObserver.addListener(mObserverListener)
  }

  override fun cleanup() {
    getAdapter().mListObserver.removeListener(mObserverListener)
  }

  ///////////////////////////////////////////////////////////////////////////
  //
  ///////////////////////////////////////////////////////////////////////////
  override fun getItemPosition(`object`: Any): Int {
    return getAdapter().getItemPosition(`object`)
  }

  override fun instantiateItem(container: ViewGroup, position: Int): Any {
    val holder = getAdapter().createViewHolder(container, getAdapter().getInternalItemViewType(position))
    getAdapter().bindViewHolder(holder, position)
    val view = holder.itemView
    setViewHolder(view, holder)
    mItemClickedWrapper.register(view)
    container.addView(view)
    return holder.itemView
  }

  override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    container.removeView(`object` as View)
  }

  override fun getCount(): Int {
    return getAdapter().internalCount
  }

  override fun isViewFromObject(view: View, `object`: Any): Boolean {
    return view === `object`
  }

  ///////////////////////////////////////////////////////////////////////////
  // 刷新方法
  ///////////////////////////////////////////////////////////////////////////
  override fun notifyDataSetChanged() {
    mUniversalAdapter!!.notifyDataSetChanged()
  }


  protected fun superNotifyDataSetChangedOnUIThread() {
    ThreadingUtils.runOnUIThread(mSuperDataSetChangedRunnable)
  }


  protected fun superNotifyDataSetChanged() {
    super.notifyDataSetChanged()
  }

}