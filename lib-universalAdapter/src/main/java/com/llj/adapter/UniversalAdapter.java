package com.llj.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.llj.adapter.listener.FooterClickListener;
import com.llj.adapter.listener.FooterLongClickListener;
import com.llj.adapter.listener.HeaderClickListener;
import com.llj.adapter.listener.HeaderFooterListenerAdapter;
import com.llj.adapter.listener.HeaderLongClickListener;
import com.llj.adapter.listener.ItemClickedListener;
import com.llj.adapter.listener.ItemDoubleClickedListener;
import com.llj.adapter.listener.ItemLongClickedListener;
import com.llj.adapter.observable.ListObserver;
import com.llj.adapter.observable.ListObserverListener;
import com.llj.adapter.observable.SimpleListObserver;
import com.llj.adapter.util.ViewHolderHelper;

/**
 * PROJECT:UniversalAdapter
 * DESCRIBE:
 * Created by llj on 2017/1/14.
 */

public abstract class UniversalAdapter<Item, Holder extends ViewHolder> implements ListObserver, HeaderFooterListenerAdapter<Item, Holder> {

    public static final int COMMON_ITEM = 0;

    private final SparseArray<ViewHolder>   mHeaderHolders = new SparseArray<>();
    private final SparseArray<ViewHolder>   mFooterHolders = new SparseArray<>();
    private final SparseArray<LayoutConfig> mItemLayouts   = new SparseArray<>();

    public static class LayoutConfig {
        public @LayoutRes int layoutId;
        public int type = COMMON_ITEM;

        public LayoutConfig(int layoutId, int type) {
            this.layoutId = layoutId;
            this.type = type;
        }

        public LayoutConfig(int layoutId) {
            this.layoutId = layoutId;
        }
    }

    private boolean runningTransaction;
    private boolean transactionModified;
    private boolean isBound;

    private SimpleListObserver<Item> listObserver;//刷新监听器观察者

    private ItemClickedListener<Item, Holder>       mItemClickedListener;
    private ItemDoubleClickedListener<Item, Holder> mItemDoubleClickedListener;
    private HeaderClickListener                     mHeaderClickListener;
    private FooterClickListener                     mFooterClickListener;

    private ItemLongClickedListener<Item, Holder> mItemLongClickedListener;
    private FooterLongClickListener               mFooterLongClickedListener;
    private HeaderLongClickListener               mHeaderLongClickedListener;

    private boolean mIsSupportDoubleClick;
    private boolean mIsSupportLongClick;

    public UniversalAdapter() {
        listObserver = new SimpleListObserver<>();
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void addListener(ListObserverListener listener) {
        getListObserver().addListener(listener);
    }

    @Override
    public boolean removeListener(ListObserverListener listener) {
        getListObserver().removeListener(listener);
        return true;
    }

    public ListObserver<Item> getListObserver() {
        return listObserver;
    }

    ///////////////////////////////////////////////////////////////////////////
    //设置监听器
    ///////////////////////////////////////////////////////////////////////////
    public void setFooterClickListener(FooterClickListener footerClickListener) {
        mFooterClickListener = footerClickListener;
    }

    public void setFooterLongClickListener(FooterLongClickListener footerLongClickListener) {
        mFooterLongClickedListener = footerLongClickListener;

    }

    @Override
    public void setHeaderClickListener(HeaderClickListener headerClickListener) {
        mHeaderClickListener = headerClickListener;
    }

    @Override
    public void setHeaderLongClickListener(HeaderLongClickListener headerLongClickedListener) {
        mHeaderLongClickedListener = headerLongClickedListener;

    }

    @Override
    public void setItemClickedListener(ItemClickedListener<Item, Holder> listener) {
        mItemClickedListener = listener;

    }

    @Override
    public void setItemDoubleClickedListener(ItemDoubleClickedListener<Item, Holder> itemDoubleClickedListener) {
        mItemDoubleClickedListener = itemDoubleClickedListener;
    }

    @Override
    public void setItemLongClickedListener(ItemLongClickedListener<Item, Holder> longClickedListener) {
        mItemLongClickedListener = longClickedListener;

    }
    ///////////////////////////////////////////////////////////////////////////
    //基本方法
    ///////////////////////////////////////////////////////////////////////////

    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_UNCHANGED;
    }

    @Nullable
    public abstract Item get(int position);

    public abstract int getCount();

    protected Holder onCreateViewHolder(@NonNull ViewGroup parent, int itemType) {
        return null;
    }

    //必须实现的方法
    protected abstract void onBindViewHolder(@NonNull Holder viewHolder, @Nullable Item item, int position);

    protected void onBindHeaderViewHolder(@NonNull ViewHolder holder, int position) {
    }

    protected void onBindFooterViewHolder(@NonNull ViewHolder holder, int position) {
    }

    public abstract void notifyDataSetChanged();

    public long getItemId(int position) {
        return 0;
    }

    public boolean areAllItemsEnabled() {
        return true;
    }

    //子类重写,item是否可用
    public boolean isEnabled(int position) {
        return true;
    }

    //子类重写，header是否可用
    public boolean isHeaderEnabled(int position) {
        return true;
    }

    //子类重写,footer是否可用
    public boolean isFooterEnabled(int position) {
        return true;
    }

    public int getItemViewType(int position) {
        return 0;
    }

    public int getItemViewTypeCount() {
        return 1;
    }

    public boolean hasStableIds() {
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 操作header和footer的方法
    ///////////////////////////////////////////////////////////////////////////
    public void addHeaderHolder(int type, ViewHolder viewHolder) {
        tryThrowAlreadyBoundException("Cannot bind a header holder post-bind due to limitations of view types and recycling.");
        if (mHeaderHolders.indexOfKey(type) < 0) {
            tryThrowAlreadyBoundException("type exits");
        }
        mHeaderHolders.put(type, viewHolder);
        onItemRangeInserted(getHeadersCount() - 1, 1);
    }


    public void addFooterHolder(int type, ViewHolder viewHolder) {
        tryThrowAlreadyBoundException("Cannot bind a footer holder post-bind due to limitations of view types and recycling.");
        if (mFooterHolders.indexOfKey(type) >= 0) {
            throw new IllegalStateException("type exits");
        }
        mFooterHolders.put(type, viewHolder);
        onItemRangeInserted(getFooterStartIndex() + getFootersCount(), 1);
    }

    /**
     * 配合ViewHolderHelper一起使用
     *
     * @param layoutConfig 统一使用ViewHolderHelper为holder
     */
    public void addItemLayout(LayoutConfig layoutConfig) {
        tryThrowAlreadyBoundException("Cannot bind a header holder post-bind due to limitations of view types and recycling.");
        if (mItemLayouts.indexOfKey(layoutConfig.type) >= 0) {
            throw new IllegalStateException("type exits");
        }
        mItemLayouts.put(layoutConfig.type, layoutConfig);
    }

    public void addItemLayout(@LayoutRes int layoutId) {
        tryThrowAlreadyBoundException("Cannot bind a header holder post-bind due to limitations of view types and recycling.");
        LayoutConfig layoutConfig = new LayoutConfig(layoutId);

        if (mItemLayouts.indexOfKey(layoutConfig.type) >= 0) {
            throw new IllegalStateException("type exits");
        }
        mItemLayouts.put(layoutConfig.type, layoutConfig);
    }

    public View inflateView(ViewGroup parent, int layoutResId) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
    }
    ///////////////////////////////////////////////////////////////////////////
    // adapter实现方法
    ///////////////////////////////////////////////////////////////////////////


    /**
     * 这个方法
     *
     * @param parent   parent
     * @param viewType viewType
     * @return ViewHolder
     */
    public ViewHolder createViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        if (mHeaderHolders.indexOfKey(viewType) >= 0) {
            viewHolder = mHeaderHolders.get(viewType);
        } else if (mFooterHolders.indexOfKey(viewType) >= 0) {
            viewHolder = mFooterHolders.get(viewType);
        } else if (mItemLayouts.indexOfKey(viewType) >= 0) {
            viewHolder = ViewHolderHelper.createViewHolder(parent, mItemLayouts.get(viewType).layoutId);
        } else {
            viewHolder = onCreateViewHolder(parent, viewType);
        }
        viewHolder.itemView.setTag(R.id.com_viewholderTagID, viewHolder);
        return viewHolder;
    }


    public void bindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (position < getHeadersCount()) {
            //前面的是header
            onBindHeaderViewHolder(viewHolder, position);
        } else if (position > getFooterStartIndex()) {
            //后面的是footer
            onBindFooterViewHolder(viewHolder, position - getFooterStartIndex() - 1);
        } else {
            //item的位置
            int adjustedPosition = getAdjustedPosition(position);
            viewHolder.itemView.setTag(R.id.com_viewholderIndexID, adjustedPosition);
            onBindViewHolder((Holder) viewHolder, get(adjustedPosition), adjustedPosition);
        }
    }

    public ViewHolder createDropDownViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        if (mHeaderHolders.indexOfKey(viewType) >= 0) {
            viewHolder = mHeaderHolders.get(viewType);
        } else if (mFooterHolders.indexOfKey(viewType) >= 0) {
            viewHolder = mFooterHolders.get(viewType);
        } else if (mItemLayouts.indexOfKey(viewType) >= 0) {
            viewHolder = new ViewHolder(inflateView(parent, mItemLayouts.get(viewType).layoutId));
        } else {
            viewHolder = onCreateDropDownViewHolder(parent, viewType);
        }
        viewHolder.itemView.setTag(R.id.com_viewholderTagID, viewHolder);
        return viewHolder;
    }

    public ViewHolder onCreateDropDownViewHolder(ViewGroup parent, int itemType) {
        return onCreateViewHolder(parent, itemType);
    }

    @SuppressWarnings("unchecked")
    public void bindDropDownViewHolder(ViewHolder viewHolder, int position) {
        if (position < getHeadersCount()) {
            onBindHeaderViewHolder(viewHolder, position);
        } else if (position > getFooterStartIndex()) {
            onBindFooterViewHolder(viewHolder, position - getFooterStartIndex() - 1);
        } else {
            onBindDropDownViewHolder((Holder) viewHolder, position - getHeadersCount());
        }
    }

    public void onBindDropDownViewHolder(Holder viewHolder, int position) {
        onBindViewHolder(viewHolder, get(position), position);
    }


    public int getInternalItemViewType(int position) {
        int viewType;
        if (position < getHeadersCount()) {
            viewType = mHeaderHolders.keyAt(position);
        } else if (position > getFooterStartIndex()) {
            viewType = mFooterHolders.keyAt(position);
        } else {
            viewType = getItemViewType(position - getHeadersCount());
        }
        return viewType;
    }

    public int getInternalCount() {
        return getHeadersCount() + getCount() + getFootersCount();
    }

    /**
     * ListView使用
     *
     * @return count
     */
    public int getInternalItemViewTypeCount() {
        return getItemViewTypeCount() + getFootersCount() + getHeadersCount();
    }
    ///////////////////////////////////////////////////////////////////////////
    //事件方法
    ///////////////////////////////////////////////////////////////////////////

    /**
     * ListView的点击事件
     *
     * @param position position
     * @param view     view
     */
    public void onItemClicked(int position, View view) {
        ViewHolder holder = (ViewHolder) view.getTag(R.id.com_viewholderTagID);
        onItemClicked(position, holder);
    }

    /**
     * RecyclerView的点击事件
     *
     * @param position position
     * @param holder   holder
     */
    public void onItemClicked(int position, ViewHolder holder) {
        if (internalIsEnabled(position)) {
            if (position < getHeadersCount()) {
                if (mHeaderClickListener != null) {
                    mHeaderClickListener.onHeaderClicked(this, holder, position);
                }
            } else if (position > getFooterStartIndex()) {
                if (mFooterClickListener != null) {
                    mFooterClickListener.onFooterClicked(this, holder, position - getFooterStartIndex() - 1);
                }
            } else {
                if (mItemClickedListener != null) {
                    int adjusted = getAdjustedPosition(position);
                    mItemClickedListener.onItemClicked(this, get(adjusted), (Holder) holder, adjusted);
                }
            }
        }
    }

    public boolean isSupportDoubleClick() {
        return mIsSupportDoubleClick;
    }

    public void setSupportDoubleClick(boolean supportDoubleClick) {
        mIsSupportDoubleClick = supportDoubleClick;
    }

    public boolean isSupportLongClick() {
        return mIsSupportLongClick;
    }

    public void setSupportLongClick(boolean supportLongClick) {
        mIsSupportLongClick = supportLongClick;
    }

    /**
     * RecyclerView的点击事件
     *
     * @param position position
     * @param holder   holder
     */
    public void onItemDoubleClicked(int position, ViewHolder holder) {
        if (internalIsEnabled(position)) {
            if (position < getHeadersCount()) {
                if (mHeaderClickListener != null) {
                    mHeaderClickListener.onHeaderDoubleClicked(this, holder, position);
                }
            } else if (position > getFooterStartIndex()) {
                if (mFooterClickListener != null) {
                    mFooterClickListener.onFooterDoubleClicked(this, holder, position - getFooterStartIndex() - 1);
                }
            } else {
                if (mItemDoubleClickedListener != null) {
                    int adjusted = getAdjustedPosition(position);
                    mItemDoubleClickedListener.onItemDoubleClicked(this, get(adjusted), (Holder) holder, adjusted);
                }
            }
        }
    }

    /**
     * ListView的长按点击事件
     *
     * @param position position
     * @param view     view
     * @return onItemLongClicked
     */
    @SuppressWarnings("unchecked")
    public boolean onItemLongClicked(int position, View view) {
        ViewHolder holder = (ViewHolder) view.getTag(R.id.com_viewholderTagID);
        return onItemLongClicked(position, holder);
    }

    /**
     * RecyclerView的长按点击事件
     *
     * @param position position
     * @param holder   holder
     * @return onItemLongClicked
     */
    public boolean onItemLongClicked(int position, ViewHolder holder) {
        if (internalIsEnabled(position)) {
            if (position < getHeadersCount()) {
                if (mHeaderLongClickedListener != null) {
                    return mHeaderLongClickedListener.onHeaderLongClicked(this, holder, position);
                }
            } else if (position > getFooterStartIndex()) {
                if (mFooterLongClickedListener != null) {
                    return mFooterLongClickedListener.onFooterLongClicked(this, holder, position - getFooterStartIndex() - 1);
                }
            } else {
                if (mItemLongClickedListener != null) {
                    int adjusted = getAdjustedPosition(position);
                    return mItemLongClickedListener.onItemLongClicked(this, get(adjusted), (Holder) holder, adjusted);
                }
            }
        }
        return false;
    }


    ///////////////////////////////////////////////////////////////////////////
    // 刷新方法,遍历观察者里面的所有监听器
    ///////////////////////////////////////////////////////////////////////////
    public void onItemRangeChanged(int startPosition, int itemCount) {
        if (tryTransactionModification()) {
            this.listObserver.notifyItemRangeChanged(startPosition, itemCount);
        }
    }

    public void onItemRangeInserted(int startPosition, int itemCount) {
        if (tryTransactionModification()) {
            this.listObserver.notifyItemRangeInserted(startPosition, itemCount);
        }
    }

    public void onItemRangeRemoved(int startPosition, int itemCount) {
        if (tryTransactionModification()) {
            this.listObserver.notifyItemRangeRemoved(startPosition, itemCount);
        }
    }

    public void onGenericChange() {
        if (tryTransactionModification()) {
            this.listObserver.notifyGenericChange();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    //操作header和footer的方法
    ///////////////////////////////////////////////////////////////////////////
    public int getHeadersCount() {
        return mHeaderHolders.size();
    }

    public int getFootersCount() {
        return mFooterHolders.size();
    }

    public boolean isHeaderPosition(int rawPosition) {
        return rawPosition < getHeadersCount();
    }

    public boolean isFooterPosition(int rawPosition) {
        return rawPosition > getFooterStartIndex();
    }

    public int getAdjustedPosition(int rawPosition) {
        return rawPosition - getHeadersCount();
    }

    private int getFooterStartIndex() {
        return getHeadersCount() + getCount() - 1;
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    public void checkIfBoundAndSet() {
        if (!isBound) {
            setBound(true);
        }
    }

    void setBound(boolean isBound) {
        this.isBound = true;
    }


    public void beginTransaction() {
        if (!runningTransaction) {
            runningTransaction = true;
            transactionModified = false;
        } else {
            throw new IllegalStateException("Tried to begin a transaction when one was already running!");
        }
    }

    public void endTransaction() {
        if (runningTransaction) {
            runningTransaction = false;
            if (transactionModified) {
                onGenericChange();
            }
        } else {
            throw new IllegalStateException("Tried to end a transaction when no transaction was running!");
        }
    }

    public boolean internalIsEnabled(int position) {
        if (position < getHeadersCount()) {
            return isHeaderEnabled(position);
        } else if (position > getFooterStartIndex()) {
            return isFooterEnabled(position - getFooterStartIndex() - 1);
        } else {
            return isEnabled(getAdjustedPosition(position));
        }
    }

    private boolean tryTransactionModification() {
        if (runningTransaction) {
            transactionModified = true;
            return false;
        }
        return true;
    }

    private void tryThrowAlreadyBoundException(String message) {
        if (isBound) {
            throw new IllegalStateException(message);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    protected final ListObserverListener<Item> observableListener = new ListObserverListener<Item>() {

        @Override
        public void onItemRangeChanged(ListObserver<Item> observer, int startPosition, int itemCount) {
            UniversalAdapter.this.onItemRangeChanged(startPosition, itemCount);
        }

        @Override
        public void onItemRangeInserted(ListObserver<Item> observer, int startPosition, int itemCount) {
            UniversalAdapter.this.onItemRangeInserted(startPosition, itemCount);
        }

        @Override
        public void onItemRangeRemoved(ListObserver<Item> observer, int startPosition, int itemCount) {
            UniversalAdapter.this.onItemRangeRemoved(startPosition, itemCount);
        }

        @Override
        public void onGenericChange(ListObserver<Item> observer) {
            UniversalAdapter.this.onGenericChange();
        }
    };
}
