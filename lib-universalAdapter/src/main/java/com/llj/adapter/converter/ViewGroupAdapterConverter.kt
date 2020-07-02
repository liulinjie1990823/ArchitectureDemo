package com.llj.adapter.converter

import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.llj.adapter.UniversalAdapter
import com.llj.adapter.UniversalConverter
import com.llj.adapter.XViewHolder
import com.llj.adapter.listener.*
import com.llj.adapter.observable.ListObserver
import com.llj.adapter.observable.ListObserverListener
import com.llj.adapter.observable.SimpleListObserverListener
import com.llj.adapter.util.UniversalAdapterUtils
import java.lang.reflect.Field

/**
 * PROJECT:CommonAdapter DESCRIBE: Created by llj on 2017/2/11.
 */
class ViewGroupAdapterConverter<Item, Holder : XViewHolder> :
    HeaderListenerAdapter<Item, Holder>,
    FooterListenerAdapter<Item, Holder>,
    ItemListenerAdapter<Item, Holder>,
    UniversalConverter<Item, Holder> {


  val mViewGroup: ViewGroup

  internal constructor(adapter: UniversalAdapter<Item, Holder>, viewGroup: ViewGroup) {
    this.mViewGroup = viewGroup
    this.mItemClickWrapper = ItemClickWrapper(this)

    this.mObserverListener = object : SimpleListObserverListener<Item>() {
      override fun onGenericChange(observer: ListObserver<Item>) {
        populateAll()
      }

      override fun onItemRangeChanged(observer: ListObserver<Item>, startPosition: Int, count: Int) {
        for (i in startPosition until startPosition + count) {
          val childAt = viewGroup.getChildAt(i) as ViewGroup
          getAdapter().bindViewHolder(UniversalAdapterUtils.getViewHolder(childAt), i)
        }
      }
    }

    adapter.checkIfBoundAndSet()
    setAdapter(adapter)
    populateAll()
  }


  private var mUniversalAdapter: UniversalAdapter<Item, Holder>? = null
  private val mItemClickWrapper: ItemClickWrapper<Item, Holder>
  private val mObserverListener: ListObserverListener<Item>

  ///////////////////////////////////////////////////////////////////////////
  //
  ///////////////////////////////////////////////////////////////////////////
  override fun setFooterClickListener(footerClickListener: FooterClickListener<Item, Holder>) {
    getAdapter().setFooterClickListener(footerClickListener)
  }

  override fun setHeaderClickListener(headerClickListener: HeaderClickListener<Item, Holder>) {
    getAdapter().setHeaderClickListener(headerClickListener)
  }

  override fun setItemClickedListener(listener: ItemClickListener<Item, Holder>) {
    getAdapter().setItemClickedListener(listener)
  }

  override fun setAdapter(listAdapter: UniversalAdapter<Item, Holder>) {
    if (mUniversalAdapter != null) {
      mUniversalAdapter!!.mListObserver.removeListener(mObserverListener)
    }
    mUniversalAdapter = listAdapter
    getAdapter().mListObserver.addListener(mObserverListener)
    populateAll()
  }

  override fun getAdapter(): UniversalAdapter<Item, Holder> {
    return mUniversalAdapter!!
  }

  override fun cleanup() {
    getAdapter().mListObserver.removeListener(mObserverListener)
  }

  private fun clear() {
    mViewGroup.removeAllViews()
  }

  private fun populateAll() {
    clear()
    val count = getAdapter().internalCount
    for (i in 0 until count) {
      addItem(i)
    }
  }

  private fun addItem(position: Int) {
    val holder = getAdapter()
        .createViewHolder(mViewGroup, mUniversalAdapter!!.getInternalItemViewType(position))
    getAdapter().bindViewHolder(holder, position)
    val view = holder.itemView
    UniversalAdapterUtils.setViewHolder(view, holder)
    if (!hasOnClickListeners(view)) {
      mItemClickWrapper.register(view)
    }
    mViewGroup.addView(view, position)
  }

  private fun hasOnClickListeners(view: View): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
      getOnClickListenerV14(view) != null
    } else {
      getOnClickListenerV(view) != null
    }
  }

  //Used for APIs lower than ICS (API 14)
  private fun getOnClickListenerV(view: View): View.OnClickListener? {
    var retrievedListener: View.OnClickListener? = null
    val viewStr = "android.view.View"
    val field: Field
    try {
      field = Class.forName(viewStr).getDeclaredField("mOnClickListener")
      retrievedListener = field[view] as View.OnClickListener
    } catch (ex: NoSuchFieldException) {
      Log.e("Reflection", "No Such Field.")
    } catch (ex: IllegalAccessException) {
      Log.e("Reflection", "Illegal Access.")
    } catch (ex: ClassNotFoundException) {
      Log.e("Reflection", "Class Not Found.")
    }
    return retrievedListener
  }

  //Used for new ListenerInfo class structure used beginning with API 14 (ICS)
  private fun getOnClickListenerV14(view: View): View.OnClickListener? {
    var retrievedListener: View.OnClickListener? = null
    val viewStr = "android.view.View"
    val lInfoStr = "android.view.View\$ListenerInfo"
    try {
      var listenerInfo: Any? = null
      val listenerField = Class.forName(viewStr).getDeclaredField("mListenerInfo")
      if (listenerField != null) {
        listenerField.isAccessible = true
        listenerInfo = listenerField[view]
      }
      val clickListenerField = Class.forName(lInfoStr).getDeclaredField("mOnClickListener")
      if (clickListenerField != null && listenerInfo != null) {
        retrievedListener = clickListenerField[listenerInfo] as View.OnClickListener?
      }
    } catch (ex: NoSuchFieldException) {
      Log.e("Reflection", "No Such Field.")
    } catch (ex: IllegalAccessException) {
      Log.e("Reflection", "Illegal Access.")
    } catch (ex: ClassNotFoundException) {
      Log.e("Reflection", "Class Not Found.")
    }
    return retrievedListener
  }


}