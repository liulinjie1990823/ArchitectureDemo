package com.llj.adapter.converter

import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.BaseAdapter
import com.llj.adapter.UniversalAdapter
import com.llj.adapter.UniversalConverter
import com.llj.adapter.XViewHolder
import com.llj.adapter.listener.ItemClickListener
import com.llj.adapter.listener.ItemListenerAdapter
import com.llj.adapter.observable.ListObserver
import com.llj.adapter.observable.ListObserverListener
import com.llj.adapter.observable.SimpleListObserverListener
import com.llj.adapter.util.ThreadingUtils
import com.llj.adapter.util.UniversalAdapterUtils

/**
 * BaseAdapterConverter
 *
 * @author liulinjie
 * @date 2017/2/11
 */
class BaseAdapterConverter<Item, Holder : XViewHolder>
internal constructor(universalAdapter: UniversalAdapter<Item, Holder>, adapterView: AbsListView) : BaseAdapter(),
  ItemListenerAdapter<Item, Holder>,
  UniversalConverter<Item, Holder> {

  private var mUniversalAdapter: UniversalAdapter<Item, Holder>? = null
  private val mObserverListener: ListObserverListener<Item>
  private val superDataSetChangedRunnable: Runnable = Runnable { superNotifyDataSetChanged() }

  init {
    this.mObserverListener = object : SimpleListObserverListener<Item>() {
      override fun onGenericChange(observer: ListObserver<Item>) {
        superNotifyDataSetChangedOnUIThread()
      }
    }
    val itemClickListener = AdapterView.OnItemClickListener { _, view, position, _ -> getAdapter().onItemClicked(position, view) }
    val longClickListener = AdapterView.OnItemLongClickListener { _, view, position, _ -> getAdapter().onItemLongClicked(position, view) }
    universalAdapter.checkIfBoundAndSet()
    setAdapter(universalAdapter)
    adapterView.adapter = this
    adapterView.onItemClickListener = itemClickListener
    adapterView.onItemLongClickListener = longClickListener
    notifyDataSetChanged()
  }

  override fun setAdapter(listAdapter: UniversalAdapter<Item, Holder>) {
    if (mUniversalAdapter != null) {
      mUniversalAdapter!!.mListObserver.removeListener(mObserverListener)
    }
    this.mUniversalAdapter = listAdapter
    getAdapter().mListObserver.addListener(mObserverListener)
  }

  override fun getAdapter(): UniversalAdapter<Item, Holder> {
    return mUniversalAdapter!!
  }

  override fun cleanup() {
    getAdapter().mListObserver.removeListener(mObserverListener)
  }

  ///////////////////////////////////////////////////////////////////////////
  //
  ///////////////////////////////////////////////////////////////////////////
  override fun setItemClickedListener(listener: ItemClickListener<Item, Holder>) {
    getAdapter().setItemClickedListener(listener)
  }

  override fun notifyDataSetChanged() {
    getAdapter().notifyDataSetChanged()
  }

  ///////////////////////////////////////////////////////////////////////////
  //
  ///////////////////////////////////////////////////////////////////////////
  override fun getViewTypeCount(): Int {
    return getAdapter().internalItemViewTypeCount
  }

  override fun getItemViewType(position: Int): Int {
    return getAdapter().getInternalItemViewType(position)
  }

  override fun getCount(): Int {
    return getAdapter().internalCount
  }

  override fun getItem(position: Int): Item? {
    return getAdapter().get(position)
  }

  override fun getItemId(position: Int): Long {
    return getAdapter().getItemId(position)
  }

  override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
    var viewHolder: XViewHolder? = getViewHolder(convertView)
    if (viewHolder == null) {
      val viewType = getItemViewType(position)
      viewHolder = getAdapter().createViewHolder(parent, viewType)
      setViewHolder(viewHolder.itemView, viewHolder)
    }
    getAdapter().bindViewHolder(viewHolder, position)
    return viewHolder.itemView
  }

  override fun getDropDownView(position: Int, convertView: View, parent: ViewGroup): View {
    var viewHolder: XViewHolder? = getViewHolder(convertView)
    if (viewHolder == null) {
      val viewType = getItemViewType(position)
      viewHolder = getAdapter().createDropDownViewHolder(parent, viewType)
      setViewHolder(viewHolder.itemView, viewHolder)
    }
    getAdapter().bindDropDownViewHolder(viewHolder, position)
    return viewHolder.itemView
  }

  override fun isEnabled(position: Int): Boolean {
    return getAdapter().internalIsEnabled(position)
  }

  override fun areAllItemsEnabled(): Boolean {
    return getAdapter().areAllItemsEnabled()
  }

  protected fun superNotifyDataSetChanged() {
    super.notifyDataSetChanged()
  }

  protected fun getViewHolder(view: View): Holder {
    return UniversalAdapterUtils.getViewHolder(view)
  }

  protected fun setViewHolder(view: View?, holder: XViewHolder?) {
    UniversalAdapterUtils.setViewHolder(view, holder)
  }

  /**
   * Calls [.superNotifyDataSetChanged] on the UI thread.
   */
  protected fun superNotifyDataSetChangedOnUIThread() {
    ThreadingUtils.runOnUIThread(superDataSetChangedRunnable)
  }


}