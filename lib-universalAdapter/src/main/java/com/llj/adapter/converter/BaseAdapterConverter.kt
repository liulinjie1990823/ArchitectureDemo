package com.llj.adapter.converter

import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.BaseAdapter
import com.llj.adapter.UniversalAdapter
import com.llj.adapter.UniversalConverter
import com.llj.adapter.XViewHolder
import com.llj.adapter.listener.*
import com.llj.adapter.observable.ListObserver
import com.llj.adapter.observable.ListObserverListener
import com.llj.adapter.observable.SimpleListObserverListener
import com.llj.adapter.util.ThreadingUtils
import com.llj.adapter.util.UniversalAdapterUtils

/**
 * PROJECT:CommonAdapter DESCRIBE: Created by llj on 2017/2/11.
 */
class BaseAdapterConverter<Item, Holder : XViewHolder> : BaseAdapter,
        HeaderListenerAdapter<Item, Holder>,
        FooterListenerAdapter<Item, Holder>,
        ItemListenerAdapter<Item, Holder>,
        UniversalConverter<Item, Holder> {

    internal constructor(universalAdapter: UniversalAdapter<Item, Holder>, adapterView: AbsListView) : super() {
        this.superDataSetChangedRunnable = Runnable { superNotifyDataSetChanged() }
        this.mObserverListener = object : SimpleListObserverListener<Item>() {
            override fun onGenericChange(observer: ListObserver<Item>) {
                superNotifyDataSetChangedOnUIThread()
            }
        }
        this.mItemClickListener = AdapterView.OnItemClickListener { _, view, position, _ -> getAdapter().onItemClicked(position, view) }
        this.mLongClickListener = OnItemLongClickListener { _, view, position, _ -> getAdapter().onItemLongClicked(position, view) }

        universalAdapter.checkIfBoundAndSet()
        setAdapter(universalAdapter)
        adapterView.adapter = this

        adapterView.onItemClickListener = mItemClickListener
        adapterView.onItemLongClickListener = mLongClickListener

        notifyDataSetChanged()
    }

    private var mUniversalAdapter: UniversalAdapter<Item, Holder>? = null
    private val mItemClickListener: AdapterView.OnItemClickListener
    private val mLongClickListener: AdapterView.OnItemLongClickListener
    private val mObserverListener: ListObserverListener<Item>

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
    override fun setFooterClickListener(
            footerClickListener: FooterClickListener<Item, Holder>) {
        getAdapter().setFooterClickListener(footerClickListener)
    }

    override fun setHeaderClickListener(
            headerClickListener: HeaderClickListener<Item, Holder>) {
        getAdapter().setHeaderClickListener(headerClickListener)
    }

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

    override fun getItem(position: Int): Item {
        return getAdapter()[position]!!
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

    private val superDataSetChangedRunnable: Runnable


}