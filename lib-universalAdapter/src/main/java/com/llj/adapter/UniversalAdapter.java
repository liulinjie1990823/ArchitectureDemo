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
import com.llj.adapter.listener.FooterListenerAdapter;
import com.llj.adapter.listener.FooterLongClickListener;
import com.llj.adapter.listener.HeaderClickListener;
import com.llj.adapter.listener.HeaderListenerAdapter;
import com.llj.adapter.listener.HeaderLongClickListener;
import com.llj.adapter.listener.ItemClickedListener;
import com.llj.adapter.listener.ItemDoubleClickedListener;
import com.llj.adapter.listener.ItemListenerAdapter;
import com.llj.adapter.listener.ItemLongClickedListener;
import com.llj.adapter.observable.ListObserver;
import com.llj.adapter.observable.ListObserverListener;
import com.llj.adapter.observable.SimpleListObserver;
import com.llj.adapter.util.ViewHolderHelper;

import java.util.List;

/**
 * PROJECT:UniversalAdapter
 * DESCRIBE:
 * Created by llj on 2017/1/14.
 */

public abstract class UniversalAdapter<Item, Holder extends ViewHolder>
        implements ListObserver,
        HeaderListenerAdapter,
        FooterListenerAdapter,
        ItemListenerAdapter<Item, Holder> {

    public static final int COMMON_ITEM = 0;

    private final SparseArray<ViewHolder>   mHeaderHolders = new SparseArray<>();
    private final SparseArray<ViewHolder>   mFooterHolders = new SparseArray<>();
    private final SparseArray<LayoutConfig> mItemLayouts   = new SparseArray<>();

    public static class LayoutConfig {
        public @LayoutRes int layoutId;
        public            int type = COMMON_ITEM;

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

    //<editor-fold desc="刷新监听">
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

    //</editor-fold >

    //<editor-fold desc="设置item监听器">
    @Override
    public void setFooterClickListener(FooterClickListener footerClickListener) {
        mFooterClickListener = footerClickListener;
    }

    @Override
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
    //</editor-fold >

    //<editor-fold desc="基本方法">
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_UNCHANGED;
    }

    @Nullable
    public abstract Item get(int position);

    public abstract int getCount();

    public int getInternalCount() {
        return getHeadersCount() + getCount() + getFootersCount();
    }

    //ListView使用
    public int getInternalItemViewTypeCount() {
        return getItemViewTypeCount() + getFootersCount() + getHeadersCount();
    }

    protected Holder onCreateViewHolder(@NonNull ViewGroup parent, int itemType) {
        return null;
    }

    protected abstract void onBindViewHolder(@NonNull Holder viewHolder, @Nullable Item item, int position);


    protected void onBindViewHolder(@NonNull Holder viewHolder, @Nullable Item item, int position, @NonNull List payloads) {
    }

    protected void onBindHeaderViewHolder(@NonNull ViewHolder holder, int position) {
    }

    protected void onBindFooterViewHolder(@NonNull ViewHolder holder, int position) {
    }

    public ViewHolder onCreateDropDownViewHolder(ViewGroup parent, int itemType) {
        return onCreateViewHolder(parent, itemType);
    }

    public void onBindDropDownViewHolder(Holder viewHolder, @Nullable Item item, int position) {
        onBindViewHolder(viewHolder, item, position);
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

    //</editor-fold >

    //<editor-fold desc="添加布局方法">
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
        onItemRangeInserted(getFooterStartIndex() - 1 + getFootersCount(), 1);
    }

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

    //</editor-fold >

    //<editor-fold desc=" adapter实现方法">
    @NonNull
    public ViewHolder createViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        if (mItemLayouts.indexOfKey(viewType) >= 0) {
            viewHolder = ViewHolderHelper.createViewHolder(parent, mItemLayouts.get(viewType).layoutId);
        } else if (mHeaderHolders.indexOfKey(viewType) >= 0) {
            viewHolder = mHeaderHolders.get(viewType);
        } else if (mFooterHolders.indexOfKey(viewType) >= 0) {
            viewHolder = mFooterHolders.get(viewType);
        } else {
            viewHolder = onCreateViewHolder(parent, viewType);
        }
        viewHolder.itemView.setTag(R.id.com_viewholderTagID, viewHolder);
        return viewHolder;
    }

    @NonNull
    public ViewHolder createDropDownViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        if (mItemLayouts.indexOfKey(viewType) >= 0) {
            viewHolder = ViewHolderHelper.createViewHolder(parent, mItemLayouts.get(viewType).layoutId);
        } else if (mHeaderHolders.indexOfKey(viewType) >= 0) {
            viewHolder = mHeaderHolders.get(viewType);
        } else if (mFooterHolders.indexOfKey(viewType) >= 0) {
            viewHolder = mFooterHolders.get(viewType);
        } else {
            viewHolder = onCreateDropDownViewHolder(parent, viewType);
        }
        viewHolder.itemView.setTag(R.id.com_viewholderTagID, viewHolder);
        return viewHolder;
    }


    public void bindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (getHeadersCount() == 0 && getFootersCount() == 0) {
            //没有头部和底部
            int adjustedPosition = getAdjustedPosition(position);
            viewHolder.itemView.setTag(R.id.com_viewholderIndexID, adjustedPosition);
            onBindViewHolder((Holder) viewHolder, get(adjustedPosition), adjustedPosition);
        } else {
            if (isHeaderPosition(position)) {
                //前面的是header
                onBindHeaderViewHolder(viewHolder, position);
            } else if (isFooterPosition(position)) {
                //后面的是footer
                onBindFooterViewHolder(viewHolder, getAdjustedFooterPosition(position));
            } else {
                //item的位置
                int adjustedPosition = getAdjustedPosition(position);
                viewHolder.itemView.setTag(R.id.com_viewholderIndexID, adjustedPosition);
                onBindViewHolder((Holder) viewHolder, get(adjustedPosition), adjustedPosition);
            }
        }
    }

    public void bindViewHolder(@NonNull ViewHolder viewHolder, int position, @NonNull List payloads) {
        if (getHeadersCount() == 0 && getFootersCount() == 0) {
            //没有头部和底部
            int adjustedPosition = getAdjustedPosition(position);
            viewHolder.itemView.setTag(R.id.com_viewholderIndexID, adjustedPosition);
            onBindViewHolder((Holder) viewHolder, get(adjustedPosition), adjustedPosition, payloads);
        } else {
            if (isHeaderPosition(position)) {
                //前面的是header
                onBindHeaderViewHolder(viewHolder, position);
            } else if (isFooterPosition(position)) {
                //后面的是footer
                onBindFooterViewHolder(viewHolder, getAdjustedFooterPosition(position));
            } else {
                //item的位置
                int adjustedPosition = getAdjustedPosition(position);
                viewHolder.itemView.setTag(R.id.com_viewholderIndexID, adjustedPosition);
                onBindViewHolder((Holder) viewHolder, get(adjustedPosition), adjustedPosition, payloads);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void bindDropDownViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (getHeadersCount() == 0 && getFootersCount() == 0) {
            int adjustedPosition = getAdjustedPosition(position);
            viewHolder.itemView.setTag(R.id.com_viewholderIndexID, adjustedPosition);
            onBindDropDownViewHolder((Holder) viewHolder, get(adjustedPosition), adjustedPosition);
        } else {
            if (isHeaderPosition(position)) {
                onBindHeaderViewHolder(viewHolder, position);
            } else if (isFooterPosition(position)) {
                onBindFooterViewHolder(viewHolder, getAdjustedFooterPosition(position));
            } else {
                int adjustedPosition = getAdjustedPosition(position);
                viewHolder.itemView.setTag(R.id.com_viewholderIndexID, adjustedPosition);
                onBindDropDownViewHolder((Holder) viewHolder, get(adjustedPosition), adjustedPosition);
            }
        }

    }


    public int getInternalItemViewType(int position) {
        int viewType;
        if (isHeaderPosition(position)) {
            viewType = mHeaderHolders.keyAt(position);
        } else if (isFooterPosition(position)) {
            viewType = mFooterHolders.keyAt(getAdjustedFooterPosition(position));
        } else {
            viewType = getItemViewType(getAdjustedPosition(position));
        }
        return viewType;
    }

    //</editor-fold >

    //<editor-fold desc="点击监听">

    /**
     * ListView的点击事件
     *
     * @param position
     * @param view
     */
    public void onItemClicked(int position, View view) {
        ViewHolder holder = (ViewHolder) view.getTag(R.id.com_viewholderTagID);
        onItemClicked(position, holder);
    }

    /**
     * ListView的长按点击事件
     *
     * @param position position
     * @param view     view
     *
     * @return onItemLongClicked
     */
    public boolean onItemLongClicked(int position, View view) {
        ViewHolder holder = (ViewHolder) view.getTag(R.id.com_viewholderTagID);
        return onItemLongClicked(position, holder);
    }

    /**
     * RecyclerView的点击事件
     *
     * @param position position
     * @param holder   holder
     */
    public void onItemClicked(int position, ViewHolder holder) {
        if (internalIsEnabled(position)) {
            if (isHeaderPosition(position)) {
                if (mHeaderClickListener != null) {
                    mHeaderClickListener.onHeaderClicked(this, holder, position);
                }
            } else if (isFooterPosition(position)) {
                if (mFooterClickListener != null) {
                    mFooterClickListener.onFooterClicked(this, holder, getAdjustedFooterPosition(position));
                }
            } else {
                if (mItemClickedListener != null) {
                    int adjusted = getAdjustedPosition(position);
                    mItemClickedListener.onItemClicked(this, get(adjusted), (Holder) holder, adjusted);
                }
            }
        }
    }

    /**
     * RecyclerView的双击击事件
     *
     * @param position position
     * @param holder   holder
     */
    public void onItemDoubleClicked(int position, ViewHolder holder) {
        if (internalIsEnabled(position)) {
            if (isHeaderPosition(position)) {
                if (mHeaderClickListener != null) {
                    mHeaderClickListener.onHeaderDoubleClicked(this, holder, position);
                }
            } else if (isFooterPosition(position)) {
                if (mFooterClickListener != null) {
                    mFooterClickListener.onFooterDoubleClicked(this, holder, getAdjustedFooterPosition(position));
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
     * RecyclerView的长按点击事件
     *
     * @param position position
     * @param holder   holder
     *
     * @return onItemLongClicked
     */
    public boolean onItemLongClicked(int position, ViewHolder holder) {
        if (internalIsEnabled(position)) {
            if (isHeaderPosition(position)) {
                if (mHeaderLongClickedListener != null) {
                    return mHeaderLongClickedListener.onHeaderLongClicked(this, holder, position);
                }
            } else if (isFooterPosition(position)) {
                if (mFooterLongClickedListener != null) {
                    return mFooterLongClickedListener.onFooterLongClicked(this, holder, getAdjustedFooterPosition(position));
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


    //</editor-fold >

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
    //操作header和footer位置的方法
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
        return rawPosition >= getFooterStartIndex();
    }

    public int getAdjustedPosition(int rawPosition) {
        return rawPosition - getHeadersCount();
    }

    public int getAdjustedFooterPosition(int rawPosition) {
        return rawPosition - getFooterStartIndex();
    }

    private int getFooterStartIndex() {
        return getHeadersCount() + getCount();
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
        if (isHeaderPosition(position)) {
            return isHeaderEnabled(position);
        } else if (isFooterPosition(position)) {
            return isFooterEnabled(getAdjustedFooterPosition(position));
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
