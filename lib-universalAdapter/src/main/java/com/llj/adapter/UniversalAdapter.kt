package com.llj.adapter

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import androidx.viewpager.widget.PagerAdapter
import com.llj.adapter.listener.*
import com.llj.adapter.observable.ListObserver
import com.llj.adapter.observable.ListObserverListener
import com.llj.adapter.observable.SimpleListObserver
import com.llj.adapter.util.ViewHolderHelper
import com.llj.adapter.util.ViewHolderHelperWrap
import timber.log.Timber
import java.util.*

/**
 * PROJECT:UniversalAdapter DESCRIBE: Created by llj on 2017/1/14.
 */
abstract class UniversalAdapter<Item, Holder : XViewHolder> : ListObserver<Any?>,
    HeaderListenerAdapter<Item, Holder>,
    FooterListenerAdapter<Item, Holder>,
    ItemListenerAdapter<Item, Holder> {
  private val mHeaderHolders = SparseArray<Holder>()
  private val mFooterHolders = SparseArray<Holder>()
  private val mItemLayouts = SparseArray<LayoutConfig>()
  private val mViewBindings = SparseArray<ViewBindingConfig>()

  private var TAG: String = this.javaClass.simpleName

  companion object {
    //基本item类型
    var DEBUG = false
    const val COMMON_ITEM_TYPE = 0
  }

  class LayoutConfig {
    @LayoutRes
    var layoutId: Int
    var type = COMMON_ITEM_TYPE

    constructor(layoutId: Int, type: Int) {
      this.layoutId = layoutId
      this.type = type
    }

    constructor(layoutId: Int) {
      this.layoutId = layoutId
    }
  }

  class ViewBindingConfig {
    private val viewBinding: ViewBinding
    var type = COMMON_ITEM_TYPE

    constructor(viewBinding: ViewBinding, type: Int) {
      this.viewBinding = viewBinding
      this.type = type
    }

    constructor(viewBinding: ViewBinding) {
      this.viewBinding = viewBinding
    }
  }

  val mListObserver: SimpleListObserver<Any?> = SimpleListObserver()

  private var runningTransaction = false
  private var transactionModified = false
  private var isBound = false

  private var mItemClickListener: ItemClickListener<Item, Holder>? = null
  private var mHeaderClickListener: HeaderClickListener<Item, Holder>? = null
  private var mFooterClickListener: FooterClickListener<Item, Holder>? = null

  var isSupportDoubleClick = false
  var isSupportLongClick = false

  private var mCachedViews: ArrayList<RecyclerView.ViewHolder>? = null

  open fun initCachedView() {
    if (DEBUG) {
      val recyclerView = getRecyclerView()
      if (recyclerView != null) {
        recyclerView.setRecyclerListener(RecyclerView.RecyclerListener {
          Timber.tag(TAG).i("%s Recycler:%s,view:%s,position:%d", TAG, it.hashCode(), it.itemView.hashCode(), it.adapterPosition)
        })

        //拿到mRecycler字段
        val recyclerViewClass = RecyclerView::class.java
        val field = recyclerViewClass.getDeclaredField("mRecycler")
        field.isAccessible = true
        val mRecycler = field.get(recyclerView) as RecyclerView.Recycler

        //拿到mRecycler中的mCachedViews字段
        val recyclerClass = RecyclerView.Recycler::class.java
        val mCachedViewsField = recyclerClass.getDeclaredField("mCachedViews")
        mCachedViewsField.isAccessible = true
        mCachedViews = mCachedViewsField.get(mRecycler) as ArrayList<RecyclerView.ViewHolder>
      }
    }
  }

  open fun getRecyclerView(): RecyclerView? {
    return null
  }


  override fun addListener(listener: ListObserverListener<Any?>) {
    mListObserver.addListener(listener)
  }

  override fun removeListener(listener: ListObserverListener<Any?>): Boolean {
    mListObserver.removeListener(listener)
    return true
  }
  //</editor-fold >

  //<editor-fold desc="设置item监听器">
  override fun setFooterClickListener(footerClickListener: FooterClickListener<Item, Holder>) {
    mFooterClickListener = footerClickListener
  }

  override fun setHeaderClickListener(headerClickListener: HeaderClickListener<Item, Holder>) {
    mHeaderClickListener = headerClickListener
  }


  override fun setItemClickedListener(listener: ItemClickListener<Item, Holder>) {
    mItemClickListener = listener
  }
  //</editor-fold >


  //<editor-fold desc="基本方法">
  open fun getItemPosition(`object`: Any): Int {
    return PagerAdapter.POSITION_UNCHANGED
  }

  abstract fun get(position: Int): Item?

  abstract fun getCount(): Int

  val internalCount: Int
    get() = headersCount + getCount() + footersCount

  //ListView使用
  val internalItemViewTypeCount: Int
    get() = getItemViewTypeCount() + footersCount + headersCount

  protected open fun onCreateViewHolder(parent: ViewGroup, itemType: Int): XViewHolder {
    return ViewHolderHelper.createViewHolder(parent, mItemLayouts[itemType].layoutId);
  }

  private fun onCreateDropDownViewHolder(parent: ViewGroup, itemType: Int): XViewHolder {
    return onCreateViewHolder(parent, itemType)
  }

  protected abstract fun onBindViewHolder(holder: Holder, item: Item?, position: Int)

  protected open fun onBindViewHolder(holder: Holder, item: Item?, position: Int, payloads: List<*>) {
  }

  protected open fun onBindHeaderViewHolder(holder: XViewHolder, position: Int) {}

  protected open fun onBindFooterViewHolder(holder: XViewHolder, position: Int) {}


  private fun onBindDropDownViewHolder(holder: Holder, item: Item?, position: Int) {
    onBindViewHolder(holder, item, position)
  }

  abstract fun notifyDataSetChanged()

  open fun getItemId(position: Int): Long {
    return 0
  }

  fun areAllItemsEnabled(): Boolean {
    return true
  }

  //子类重写,item是否可用
  open fun isEnabled(position: Int): Boolean {
    return true
  }

  //子类重写，header是否可用
  open fun isHeaderEnabled(position: Int): Boolean {
    return true
  }

  //子类重写,footer是否可用
  open fun isFooterEnabled(position: Int): Boolean {
    return true
  }

  open fun getItemViewType(position: Int): Int {
    return COMMON_ITEM_TYPE
  }

  open fun getItemViewTypeCount(): Int {
    return 1
  }


  open fun hasStableIds(): Boolean {
    initCachedView()
    return false
  }
  //</editor-fold >


  //<editor-fold desc="添加布局方法">
  private fun addHeaderHolder(type: Int, viewHolder: Holder) {
    tryThrowAlreadyBoundException(
        "Cannot bind a header holder post-bind due to limitations of view types and recycling.")
    if (mHeaderHolders.indexOfKey(type) < 0) {
      tryThrowAlreadyBoundException("type exits")
    }
    mHeaderHolders.put(type, viewHolder)
    onItemRangeInserted(headersCount - 1, 1)
  }

  private fun addFooterHolder(type: Int, viewHolder: Holder) {
    tryThrowAlreadyBoundException(
        "Cannot bind a footer holder post-bind due to limitations of view types and recycling.")
    check(mFooterHolders.indexOfKey(type) < 0) { "type exits" }
    mFooterHolders.put(type, viewHolder)
    onItemRangeInserted(footerStartIndex - 1 + footersCount, 1)
  }

  fun addItemLayout(layoutConfig: LayoutConfig) {
    tryThrowAlreadyBoundException(
        "Cannot bind a header holder post-bind due to limitations of view types and recycling.")
    check(mItemLayouts.indexOfKey(layoutConfig.type) < 0) { "type exits" }
    mItemLayouts.put(layoutConfig.type, layoutConfig)
  }

  fun addItemLayout(@LayoutRes layoutId: Int) {
    tryThrowAlreadyBoundException(
        "Cannot bind a header holder post-bind due to limitations of view types and recycling.")
    val layoutConfig = LayoutConfig(layoutId)
    check(mItemLayouts.indexOfKey(layoutConfig.type) < 0) { "type exits" }
    mItemLayouts.put(layoutConfig.type, layoutConfig)
  }

  fun addItemLayout(@LayoutRes layoutId: Int, type: Int) {
    tryThrowAlreadyBoundException(
        "Cannot bind a header holder post-bind due to limitations of view types and recycling.")
    val layoutConfig = LayoutConfig(layoutId, type)
    check(mItemLayouts.indexOfKey(layoutConfig.type) < 0) { "type exits" }
    mItemLayouts.put(layoutConfig.type, layoutConfig)
  }


  fun addViewBinding(viewBindingConfig: ViewBindingConfig) {
    tryThrowAlreadyBoundException(
        "Cannot bind a header holder post-bind due to limitations of view types and recycling.")
    check(mViewBindings.indexOfKey(viewBindingConfig.type) < 0) { "type exits" }
    mViewBindings.put(viewBindingConfig.type, viewBindingConfig)
  }

  fun addViewBinding(viewBinding: ViewBinding) {
    tryThrowAlreadyBoundException(
        "Cannot bind a header holder post-bind due to limitations of view types and recycling.")
    val viewBindingConfig = ViewBindingConfig(viewBinding)
    check(mItemLayouts.indexOfKey(viewBindingConfig.type) < 0) { "type exits" }
    mViewBindings.put(viewBindingConfig.type, viewBindingConfig)
  }

  fun addViewBinding(viewBinding: ViewBinding, type: Int) {
    tryThrowAlreadyBoundException(
        "Cannot bind a header holder post-bind due to limitations of view types and recycling.")
    val viewBindingConfig = ViewBindingConfig(viewBinding, type)
    check(mItemLayouts.indexOfKey(viewBindingConfig.type) < 0) { "type exits" }
    mViewBindings.put(viewBindingConfig.type, viewBindingConfig)
  }

  fun inflateView(parent: ViewGroup, layoutResId: Int): View {
    return LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
  }
  //</editor-fold >

  private var mCreateCurrentTimeMillis: Long? = null
  private var mCurrentTimeMillis: Long? = null

  //<editor-fold desc=" adapter实现方法">
  internal fun createViewHolder(parent: ViewGroup, viewType: Int): XViewHolder {
    mCreateCurrentTimeMillis = System.currentTimeMillis()

    val holder: XViewHolder
    if (mItemLayouts.indexOfKey(viewType) >= 0) {
      //createViewHolder中会进行inflate操作
      holder = ViewHolderHelper.createViewHolder(parent, mItemLayouts[viewType].layoutId)
    } else if (mHeaderHolders.indexOfKey(viewType) >= 0) {
      holder = mHeaderHolders[viewType]
    } else if (mFooterHolders.indexOfKey(viewType) >= 0) {
      holder = mFooterHolders[viewType]
    } else {
      //ViewBinding可以提前在外部inflate
      //viewHolder = ViewHolderHelperWrap.createViewHolder(mViewBindings[viewType].viewBinding)
      val viewBinding = onCreateViewBinding(parent, viewType)
      holder = if (viewBinding != null) {
        ViewHolderHelperWrap.createViewHolder(viewBinding)
      } else {
        onCreateViewHolder(parent, viewType)
      }
    }
    holder.itemView.setTag(R.id.com_viewholderTagID, holder)

    logonCreateViewHolderTime("createViewHolder", holder)

    return holder
  }

  open fun onCreateViewBinding(parent: ViewGroup, viewType: Int): ViewBinding? {
    return null
  }

  internal fun createDropDownViewHolder(parent: ViewGroup, viewType: Int): XViewHolder {
    mCreateCurrentTimeMillis = System.currentTimeMillis()

    val holder: XViewHolder = when {
      mItemLayouts.indexOfKey(viewType) >= 0 -> {
        ViewHolderHelper.createViewHolder(parent, mItemLayouts[viewType].layoutId)
      }
      mHeaderHolders.indexOfKey(viewType) >= 0 -> {
        mHeaderHolders[viewType]
      }
      mFooterHolders.indexOfKey(viewType) >= 0 -> {
        mFooterHolders[viewType]
      }
      else -> {
        val viewBinding = onCreateViewBinding(parent, viewType)
        if (viewBinding != null) {
          ViewHolderHelperWrap.createViewHolder(viewBinding)
        } else {
          onCreateDropDownViewHolder(parent, viewType)
        }
      }
    }
    holder.itemView.setTag(R.id.com_viewholderTagID, holder)

    logonCreateViewHolderTime("createDropDownViewHolder", holder)
    return holder
  }

  private fun logCachedViews() {
    if (DEBUG && mCachedViews != null) {
      for (i in 0 until mCachedViews!!.size) {
        var extra = ""
        val viewHolder = mCachedViews!![i]

        if (i == 0) {
          extra = ""
        } else if (i == mCachedViews!!.size - 1) {
          extra = ""
        } else {
          extra = ""
        }
        Timber.tag(TAG).i("%s%s mCachedViews:%s,view:%s,position:%d",
            extra, TAG, viewHolder.hashCode(), viewHolder.itemView.hashCode(), viewHolder.adapterPosition)
      }
    }

  }

  private fun logOnBindViewHolderTime(method: String, holder: XViewHolder, position: Int) {
    if (DEBUG) {
      val spend = System.currentTimeMillis() - mCurrentTimeMillis!!
      Timber.tag(TAG).i("%s %s:%s,view:%s,position:%d,spend:%d",
          TAG, method, holder.hashCode(), holder.itemView.hashCode(), position, spend)
    }
  }

  private fun logonCreateViewHolderTime(method: String, holder: XViewHolder) {
    if (DEBUG) {
      val spend = System.currentTimeMillis() - mCreateCurrentTimeMillis!!
      Timber.tag(TAG).i("%s %s:%s,view:%s",
          TAG, method, holder.hashCode(), holder.itemView.hashCode())
    }
  }


  open fun bindViewHolder(holder: XViewHolder, position: Int) {
    logCachedViews()
    mCurrentTimeMillis = System.currentTimeMillis()

    if (headersCount == 0 && footersCount == 0) {
      //没有头部和底部
      val adjustedPosition = getAdjustedPosition(position)
      holder.itemView.setTag(R.id.com_viewholderIndexID, adjustedPosition)
      onBindViewHolder(holder as Holder, get(adjustedPosition), adjustedPosition)
    } else {
      if (isHeaderPosition(position)) {
        //前面的是header
        onBindHeaderViewHolder(holder, position)
      } else if (isFooterPosition(position)) {
        //后面的是footer
        onBindFooterViewHolder(holder, getAdjustedFooterPosition(position))
      } else {
        //item的位置
        val adjustedPosition = getAdjustedPosition(position)
        holder.itemView.setTag(R.id.com_viewholderIndexID, adjustedPosition)
        onBindViewHolder(holder as Holder, get(adjustedPosition), adjustedPosition)
      }
    }

    //record onBindViewHolder spend time
    logOnBindViewHolderTime("bindViewHolder", holder, position)
  }

  fun bindViewHolder(holder: XViewHolder, position: Int, payloads: List<*>) {
    logCachedViews()
    mCurrentTimeMillis = System.currentTimeMillis()

    if (headersCount == 0 && footersCount == 0) {
      //没有头部和底部
      val adjustedPosition = getAdjustedPosition(position)
      holder.itemView.setTag(R.id.com_viewholderIndexID, adjustedPosition)
      onBindViewHolder(holder as Holder, get(adjustedPosition), adjustedPosition, payloads)
    } else {
      if (isHeaderPosition(position)) {
        //前面的是header
        onBindHeaderViewHolder(holder, position)
      } else if (isFooterPosition(position)) {
        //后面的是footer
        onBindFooterViewHolder(holder, getAdjustedFooterPosition(position))
      } else {
        //item的位置
        val adjustedPosition = getAdjustedPosition(position)
        holder.itemView.setTag(R.id.com_viewholderIndexID, adjustedPosition)
        onBindViewHolder(holder as Holder, get(adjustedPosition), adjustedPosition, payloads)
      }
    }
    //record onBindViewHolder spend time
    logOnBindViewHolderTime("bindViewHolderPayloads", holder, position)
  }

  fun bindDropDownViewHolder(holder: XViewHolder, position: Int) {
    logCachedViews()
    mCurrentTimeMillis = System.currentTimeMillis()

    if (headersCount == 0 && footersCount == 0) {
      val adjustedPosition = getAdjustedPosition(position)
      holder.itemView.setTag(R.id.com_viewholderIndexID, adjustedPosition)
      onBindDropDownViewHolder(holder as Holder, get(adjustedPosition), adjustedPosition)
    } else {
      if (isHeaderPosition(position)) {
        onBindHeaderViewHolder(holder, position)
      } else if (isFooterPosition(position)) {
        onBindFooterViewHolder(holder, getAdjustedFooterPosition(position))
      } else {
        val adjustedPosition = getAdjustedPosition(position)
        holder.itemView.setTag(R.id.com_viewholderIndexID, adjustedPosition)
        onBindDropDownViewHolder(holder as Holder, get(adjustedPosition), adjustedPosition)
      }
    }
    //record onBindViewHolder spend time
    logOnBindViewHolderTime("bindDropDownViewHolder", holder, position)
  }

  fun getInternalItemViewType(position: Int): Int {
    return when {
      isHeaderPosition(position) -> {
        mHeaderHolders.keyAt(position)
      }
      isFooterPosition(position) -> {
        mFooterHolders.keyAt(getAdjustedFooterPosition(position))
      }
      else -> {
        getItemViewType(getAdjustedPosition(position))
      }
    }
  }
  //</editor-fold >
  //<editor-fold desc="点击监听">
  /**
   * ListView的点击事件
   *
   * @param position
   * @param view
   */
  fun onItemClicked(position: Int, view: View) {
    val holder = view.getTag(R.id.com_viewholderTagID) as Holder
    onItemClicked(position, holder)
  }

  /**
   * ListView的长按点击事件
   *
   * @param position position
   * @param view view
   * @return onItemLongClicked
   */
  fun onItemLongClicked(position: Int, view: View): Boolean {
    val holder = view.getTag(R.id.com_viewholderTagID) as Holder
    return onItemLongClicked(position, holder)
  }

  /**
   * RecyclerView的点击事件
   *
   * @param position position
   * @param holder holder
   */
  fun onItemClicked(position: Int, holder: Holder): Boolean {
    if (internalIsEnabled(position)) {
      if (isHeaderPosition(position)) {
        if (mHeaderClickListener != null) {
          mHeaderClickListener!!.onHeaderClicked(this, null, holder, position)
        }
      } else if (isFooterPosition(position)) {
        if (mFooterClickListener != null) {
          mFooterClickListener!!.onFooterClicked(this, null, holder, getAdjustedFooterPosition(position))
        }
      } else {
        if (mItemClickListener != null) {
          val adjusted = getAdjustedPosition(position)
          mItemClickListener?.onItemClicked(this, get(adjusted), holder, adjusted)
        }
      }
    }
    return false;
  }

  /**
   * RecyclerView的双击击事件
   *
   * @param position position
   * @param holder holder
   */
  fun onItemDoubleClicked(position: Int, holder: Holder) {
    if (internalIsEnabled(position)) {
      if (isHeaderPosition(position)) {
        if (mHeaderClickListener != null) {
          mHeaderClickListener!!.onHeaderDoubleClicked(this, null, holder, position)
        }
      } else if (isFooterPosition(position)) {
        if (mFooterClickListener != null) {
          mFooterClickListener!!
              .onFooterDoubleClicked(this, null, holder, getAdjustedFooterPosition(position))
        }
      } else {
        if (mItemClickListener != null) {
          val adjusted = getAdjustedPosition(position)
          mItemClickListener!!.onItemDoubleClicked(this, get(adjusted), holder, adjusted)
        }
      }
    }
  }

  /**
   * RecyclerView的长按点击事件
   *
   * @param position position
   * @param holder holder
   */
  fun onItemLongClicked(position: Int, holder: Holder): Boolean {
    if (internalIsEnabled(position)) {
      if (isHeaderPosition(position)) {
        if (mHeaderClickListener != null) {
          mHeaderClickListener!!.onHeaderLongClicked(this, null, holder, position)
          return true
        }
      } else if (isFooterPosition(position)) {
        if (mFooterClickListener != null) {
          mFooterClickListener!!.onFooterLongClicked(this, null, holder, getAdjustedFooterPosition(position))
          return true
        }
      } else {
        if (mItemClickListener != null) {
          val adjusted = getAdjustedPosition(position)
          mItemClickListener!!.onItemLongClicked(this, get(adjusted), holder, adjusted)
          return true
        }
      }
    }
    return false
  }

  //</editor-fold >
  ///////////////////////////////////////////////////////////////////////////
  // 刷新方法,遍历观察者里面的所有监听器
  ///////////////////////////////////////////////////////////////////////////
  fun onItemRangeChanged(startPosition: Int, itemCount: Int) {
    if (tryTransactionModification()) {
      mListObserver.notifyItemRangeChanged(startPosition, itemCount)
    }
  }

  fun onItemRangeChanged(startPosition: Int, itemCount: Int, payload: Any?) {
    if (tryTransactionModification()) {
      mListObserver.notifyItemRangeChanged(startPosition, itemCount, payload)
    }
  }

  fun onItemRangeInserted(startPosition: Int, itemCount: Int) {
    if (tryTransactionModification()) {
      mListObserver.notifyItemRangeInserted(startPosition, itemCount)
    }
  }

  fun onItemRangeRemoved(startPosition: Int, itemCount: Int) {
    if (tryTransactionModification()) {
      mListObserver.notifyItemRangeRemoved(startPosition, itemCount)
    }
  }

  fun onGenericChange() {
    if (tryTransactionModification()) {
      mListObserver.notifyGenericChange()
    }
  }

  ///////////////////////////////////////////////////////////////////////////
  //操作header和footer位置的方法
  ///////////////////////////////////////////////////////////////////////////
  private val headersCount: Int
    get() = mHeaderHolders.size()

  private val footersCount: Int
    get() = mFooterHolders.size()

  private val footerStartIndex: Int
    get() = headersCount + getCount()

  fun isHeaderPosition(rawPosition: Int): Boolean {
    return rawPosition < headersCount
  }

  fun isFooterPosition(rawPosition: Int): Boolean {
    return rawPosition >= footerStartIndex
  }

  fun getAdjustedPosition(rawPosition: Int): Int {
    return rawPosition - headersCount
  }

  fun getAdjustedFooterPosition(rawPosition: Int): Int {
    return rawPosition - footerStartIndex
  }


  ///////////////////////////////////////////////////////////////////////////
  //
  ///////////////////////////////////////////////////////////////////////////
  fun checkIfBoundAndSet() {
    if (!isBound) {
      setBound(true)
    }
  }

  fun setBound(isBound: Boolean) {
    this.isBound = true
  }

  fun beginTransaction() {
    if (!runningTransaction) {
      runningTransaction = true
      transactionModified = false
    } else {
      throw IllegalStateException("Tried to begin a transaction when one was already running!")
    }
  }

  fun endTransaction() {
    if (runningTransaction) {
      runningTransaction = false
      if (transactionModified) {
        onGenericChange()
      }
    } else {
      throw IllegalStateException(
          "Tried to end a transaction when no transaction was running!")
    }
  }

  fun internalIsEnabled(position: Int): Boolean {
    return when {
      isHeaderPosition(position) -> {
        isHeaderEnabled(position)
      }
      isFooterPosition(position) -> {
        isFooterEnabled(getAdjustedFooterPosition(position))
      }
      else -> {
        isEnabled(getAdjustedPosition(position))
      }
    }
  }

  private fun tryTransactionModification(): Boolean {
    if (runningTransaction) {
      transactionModified = true
      return false
    }
    return true
  }

  private fun tryThrowAlreadyBoundException(message: String) {
    check(!isBound) { message }
  }


  protected val observableListener: ListObserverListener<Item> = object : ListObserverListener<Item> {
    override fun onItemRangeChanged(observer: ListObserver<Item>, startPosition: Int, count: Int,
                                    payload: Any?) {
      this@UniversalAdapter.onItemRangeChanged(startPosition, count, payload)
    }

    override fun onItemRangeChanged(observer: ListObserver<Item>, startPosition: Int, count: Int) {
      this@UniversalAdapter.onItemRangeChanged(startPosition, count)
    }

    override fun onItemRangeInserted(observer: ListObserver<Item>, startPosition: Int, count: Int) {
      this@UniversalAdapter.onItemRangeInserted(startPosition, count)
    }

    override fun onItemRangeRemoved(observer: ListObserver<Item>, startPosition: Int, count: Int) {
      this@UniversalAdapter.onItemRangeRemoved(startPosition, count)
    }

    override fun onGenericChange(observer: ListObserver<Item>) {
      this@UniversalAdapter.onGenericChange()
    }
  }


}