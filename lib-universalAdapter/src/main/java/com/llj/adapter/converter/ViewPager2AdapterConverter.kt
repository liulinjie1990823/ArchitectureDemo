package com.llj.adapter.converter

import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import androidx.viewpager2.widget.ViewPager2
import com.llj.adapter.UniversalAdapter
import com.llj.adapter.UniversalConverter
import com.llj.adapter.XViewHolder
import com.llj.adapter.listener.*
import com.llj.adapter.observable.ListObserver
import com.llj.adapter.observable.ListObserverListener
import com.llj.adapter.util.ThreadingUtils

/**
 * describe ViewPager2AdapterConverter
 *
 * @author liulinjie
 * @date 2020/5/17 11:28 AM
 */
class ViewPager2AdapterConverter<Item, Holder : XViewHolder> : RecyclerView.Adapter<XViewHolder>,
    HeaderListenerAdapter<Item, Holder>,
    FooterListenerAdapter<Item, Holder>,
    ItemListenerAdapter<Item, Holder>,
    UniversalConverter<Item, Holder> {

  constructor(universalAdapter: UniversalAdapter<Item, Holder>, viewPager2: ViewPager2) : super() {

    mObserverListener = RecyclerViewListObserverListener(this)
    universalAdapter.checkIfBoundAndSet()
    setAdapter(universalAdapter)
    viewPager2.adapter = this
    val recycleView = viewPager2.getChildAt(0) as RecyclerView
    recycleView.addOnItemTouchListener(object : RecyclerViewItemClickListener() {
      override fun onItemClick(holder: Holder, parent: RecyclerView?, position: Int, x: Float,
                               y: Float) {
        if (getAdapter().internalIsEnabled(position)) {
          if (mRecyclerItemClickListener != null) {
            mRecyclerItemClickListener!!.onItemClick(holder, parent, position, x, y)
          }
          getAdapter().onItemClicked(position, holder)
        }
      }

      override fun onItemDoubleClick(holder: Holder, parent: RecyclerView?, position: Int, x: Float,
                                     y: Float) {
        getAdapter().onItemDoubleClicked(position, holder)
      }

      override fun onItemLongClick(holder: Holder, parent: RecyclerView?, position: Int, x: Float,
                                   y: Float) {
        getAdapter().onItemLongClicked(position, holder)
      }
    })
    universalAdapter.notifyDataSetChanged()
  }

  interface RecyclerItemClickListener<Holder : XViewHolder> {
    /**
     * Called when an item in the [RecyclerView] is clicked.
     *
     * @param viewHolder The view holder of the clicked item.
     * @param parent The recycler view which contained the clicked item.
     * @param position The position in the adapter of the clicked item.
     * @param x x position
     * @param y y position
     */
    fun onItemClick(viewHolder: Holder, parent: RecyclerView?, position: Int, x: Float, y: Float)
  }

  private var mUniversalAdapter: UniversalAdapter<Item, Holder>? = null
  private var mRecyclerItemClickListener: RecyclerItemClickListener<Holder>? = null
  private val mObserverListener: RecyclerViewListObserverListener<Item>

  fun setRecyclerItemClickListener(
      recyclerItemClickListener: RecyclerItemClickListener<Holder>?) {
    mRecyclerItemClickListener = recyclerItemClickListener
  }

  ///////////////////////////////////////////////////////////////////////////
  //
  ///////////////////////////////////////////////////////////////////////////
  override fun setAdapter(listAdapter: UniversalAdapter<Item, Holder>) {
    if (mUniversalAdapter != null) {
      mUniversalAdapter!!.mListObserver.removeListener(mObserverListener)
    }

    mUniversalAdapter = listAdapter
    getAdapter().mListObserver.addListener(mObserverListener)
    setHasStableIds(listAdapter.hasStableIds())
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
  override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): XViewHolder {
    return getAdapter().createViewHolder(viewGroup, viewType)
  }

  override fun onBindViewHolder(holder: XViewHolder, position: Int) {
    getAdapter().bindViewHolder(holder, position)
  }

  override fun onBindViewHolder(holder: XViewHolder, position: Int,
                                payloads: List<Any>) {
    if (payloads.isEmpty()) {
      getAdapter().bindViewHolder(holder, position)
    } else {
      getAdapter().bindViewHolder(holder, position, payloads)
    }
  }

  override fun getItemId(position: Int): Long {
    return getAdapter().getItemId(position)
  }

  override fun getItemViewType(position: Int): Int {
    return getAdapter().getInternalItemViewType(position)
  }

  override fun getItemCount(): Int {
    return getAdapter().internalCount
  }

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
  ///////////////////////////////////////////////////////////////////////////
  //
  ///////////////////////////////////////////////////////////////////////////
  /**
   * adapter更新封装
   *
   * @param <Item>
  </Item> */
  internal class RecyclerViewListObserverListener<Item> : ListObserverListener<Item> {
    private val adapter: RecyclerView.Adapter<XViewHolder>

    constructor(adapter: RecyclerView.Adapter<XViewHolder>) {
      this.adapter = adapter
    }

    override fun onItemRangeChanged(observer: ListObserver<Item>, startPosition: Int, count: Int, payload: Any?) {
      ThreadingUtils.runOnUIThread(Runnable { adapter.notifyItemRangeChanged(startPosition, count, payload) })
    }

    override fun onItemRangeChanged(observer: ListObserver<Item>, startPosition: Int, count: Int) {
      ThreadingUtils.runOnUIThread(Runnable { adapter.notifyItemRangeChanged(startPosition, count) })
    }

    override fun onItemRangeInserted(observer: ListObserver<Item>, startPosition: Int, count: Int) {
      ThreadingUtils.runOnUIThread(Runnable { adapter.notifyItemRangeInserted(startPosition, count) })
    }

    override fun onItemRangeRemoved(observer: ListObserver<Item>, startPosition: Int, count: Int) {
      ThreadingUtils.runOnUIThread(Runnable { adapter.notifyItemRangeRemoved(startPosition, count) })
    }

    override fun onGenericChange(observer: ListObserver<Item>) {
      ThreadingUtils.runOnUIThread(Runnable { adapter.notifyDataSetChanged() })
    }

  }

  //点击事件实现
  abstract inner class RecyclerViewItemClickListener : OnItemTouchListener {
    private var gestureDetector: GestureDetector? = null
    override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
      if (gestureDetector == null) {
        gestureDetector = GestureDetector(view.context,
            object : SimpleOnGestureListener() {
              override fun onSingleTapUp(e: MotionEvent): Boolean {
                //1.如果支持双击，返回false,则gestureDetector.onTouchEvent(e)返回false，不会触发后面的单击代码
                //单击事件在onSingleTapConfirmed中触发
                //2.如果不支持双击，返回true,则gestureDetector.onTouchEvent(e)返回true，直接触发后面的单击代码，
                //这样在onSingleTapUp后就返回true,就触发了单击，单击只需100ms左右，如果放在onSingleTapConfirmed里面执行单击则需要至少300ms的延时判断
                return !getAdapter().isSupportDoubleClick
              }

              override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                //300ms外没有迎来第二次点击
                if (getAdapter().isSupportDoubleClick) {
                  val childView = view.findChildViewUnder(e.x, e.y)
                  if (childView != null) {
                    val position = view.getChildAdapterPosition(childView)
                    val holder = view.getChildViewHolder(childView) as Holder
                    onItemClick(holder, view, position, e.x, e.y)
                  }
                }
                return super.onSingleTapConfirmed(e)
              }

              override fun onDoubleTap(e: MotionEvent): Boolean {
                //必须300ms内触发
                val childView = view.findChildViewUnder(e.x, e.y)
                if (childView != null) {
                  val position = view.getChildAdapterPosition(childView)
                  val holder = view.getChildViewHolder(childView) as Holder
                  onItemDoubleClick(holder, view, position, e.x, e.y)
                }
                return super.onDoubleTap(e)
              }

              override fun onLongPress(e: MotionEvent) {
                //长按，超过600ms
                val childView = view.findChildViewUnder(e.x, e.y)
                if (childView != null) {
                  val position = view.getChildAdapterPosition(childView)
                  val holder = view.getChildViewHolder(childView) as Holder
                  onItemLongClick(holder, view, position, e.x, e.y)
                }
              }
            })
        gestureDetector!!.setIsLongpressEnabled(getAdapter().isSupportLongClick)
      }
      if (gestureDetector!!.onTouchEvent(e)) {
        val childView = view.findChildViewUnder(e.x, e.y)
        if (childView != null) {
          val holder = view.getChildViewHolder(childView) as Holder
          val position = view.getChildAdapterPosition(childView)
          onItemClick(holder, view, position, e.x, e.y)
        }
      }
      return false
    }

    override fun onTouchEvent(recyclerView: RecyclerView, motionEvent: MotionEvent) {}
    override fun onRequestDisallowInterceptTouchEvent(b: Boolean) {}
    abstract fun onItemClick(holder: Holder, parent: RecyclerView?, position: Int,
                             x: Float, y: Float)

    abstract fun onItemDoubleClick(holder: Holder, parent: RecyclerView?,
                                   position: Int,
                                   x: Float, y: Float)

    abstract fun onItemLongClick(holder: Holder, parent: RecyclerView?, position: Int,
                                 x: Float, y: Float)
  }

}