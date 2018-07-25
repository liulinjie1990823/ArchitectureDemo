package com.llj.adapter.converter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;

import com.llj.adapter.CommonConverter;
import com.llj.adapter.UniversalAdapter;
import com.llj.adapter.ViewHolder;
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
import com.llj.adapter.observable.SimpleListObserverListener;
import com.llj.adapter.util.ThreadingUtils;
import com.llj.adapter.util.UniversalAdapterUtils;

/**
 * PROJECT:CommonAdapter
 * DESCRIBE:
 * Created by llj on 2017/2/11.
 */

public class BaseAdapterConverter<Item, Holder extends ViewHolder> extends BaseAdapter implements HeaderFooterListenerAdapter<Item, Holder>, CommonConverter<Item, Holder> {

    private UniversalAdapter<Item, Holder> universalAdapter;

    BaseAdapterConverter(@NonNull UniversalAdapter<Item, Holder> universalAdapter, AdapterView<? super BaseAdapter> adapterView) {
        universalAdapter.checkIfBoundAndSet();
        setAdapter(universalAdapter);
        adapterView.setAdapter(this);

        // Spinners don't like on item click listeners.
        // We will still delegate calls to it since you're clicking on an item to select it...
        if (!(adapterView instanceof Spinner)) {
            adapterView.setOnItemClickListener(internalItemClickListener);
        } else {
            adapterView.setOnItemSelectedListener(internalItemSelectedListener);
        }
        adapterView.setOnItemLongClickListener(internalLongClickListener);

        notifyDataSetChanged();
    }

    @Override
    public void setAdapter(@NonNull UniversalAdapter<Item, Holder> universalAdapter) {
        if (getAdapter() != null) {
            getAdapter().getListObserver().removeListener(internalListObserverListener);
        }

        this.universalAdapter = universalAdapter;
        universalAdapter.getListObserver().addListener(internalListObserverListener);
    }

    @Override
    public UniversalAdapter<Item, Holder> getAdapter() {
        return universalAdapter;
    }

    @Override
    public void cleanup() {
        getAdapter().getListObserver().removeListener(internalListObserverListener);
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void setFooterClickListener(FooterClickListener footerClickListener) {
        getAdapter().setFooterClickListener(footerClickListener);
    }

    @Override
    public void setFooterLongClickListener(FooterLongClickListener footerLongClickListener) {
        getAdapter().setFooterLongClickListener(footerLongClickListener);
    }

    @Override
    public void setHeaderClickListener(HeaderClickListener headerClickListener) {
        getAdapter().setHeaderClickListener(headerClickListener);
    }

    @Override
    public void setHeaderLongClickListener(HeaderLongClickListener headerLongClickListener) {
        getAdapter().setHeaderLongClickListener(headerLongClickListener);
    }

    @Override
    public void setItemClickedListener(ItemClickedListener<Item, Holder> listener) {
        getAdapter().setItemClickedListener(listener);
    }

    @Override
    public void setItemDoubleClickedListener(ItemDoubleClickedListener<Item, Holder> listener) {
        getAdapter().setItemDoubleClickedListener(listener);
    }

    @Override
    public void setItemLongClickedListener(ItemLongClickedListener<Item, Holder> longClickedListener) {
        getAdapter().setItemLongClickedListener(longClickedListener);
    }


    @Override
    public void notifyDataSetChanged() {
        getAdapter().notifyDataSetChanged();
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public int getViewTypeCount() {
        return getAdapter().getInternalItemViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        return getAdapter().getInternalItemViewType(position);
    }

    @Override
    public int getCount() {
        return getAdapter().getInternalCount();
    }

    @Override
    public Item getItem(int position) {
        return getAdapter().get(position);
    }

    @Override
    public long getItemId(int position) {
        return getAdapter().getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView != null) {
            viewHolder = getViewHolder(convertView);
        }

        if (viewHolder == null) {
            int viewType = getItemViewType(position);
            viewHolder = getAdapter().createViewHolder(parent, viewType);
            setViewHolder(viewHolder.itemView, viewHolder);
        }

        getAdapter().bindViewHolder(viewHolder, position);

        return viewHolder.itemView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView != null) {
            viewHolder = getViewHolder(convertView);
        }

        if (viewHolder == null) {
            int viewType = getItemViewType(position);
            viewHolder = getAdapter().createDropDownViewHolder(parent, viewType);
            setViewHolder(viewHolder.itemView, viewHolder);
        }

        getAdapter().bindDropDownViewHolder(viewHolder, position);

        return viewHolder.itemView;
    }

    @Override
    public boolean isEnabled(int position) {
        return getAdapter().internalIsEnabled(position);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return getAdapter().areAllItemsEnabled();
    }

    protected void superNotifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    protected Holder getViewHolder(View view) {
        try {
            return UniversalAdapterUtils.getViewHolder(view);
        } catch (ClassCastException ex) {
            // Don't care. Just don't crash. We'll just ignore convertView.
        }

        return null;
    }

    protected void setViewHolder(View view, ViewHolder holder) {
        UniversalAdapterUtils.setViewHolder(view, holder);
    }

    /**
     * Calls {@link #superNotifyDataSetChanged()} on the UI thread.
     */
    protected void superNotifyDataSetChangedOnUIThread() {
        ThreadingUtils.runOnUIThread(superDataSetChangedRunnable);
    }

    private final Runnable superDataSetChangedRunnable = new Runnable() {
        @Override
        public void run() {
            superNotifyDataSetChanged();
        }
    };

    private final ListObserverListener<Item>          internalListObserverListener = new SimpleListObserverListener<Item>() {
        @Override
        public void onGenericChange(ListObserver<Item> listObserver) {
            superNotifyDataSetChangedOnUIThread();
        }
    };
    //选中事件
    private final AdapterView.OnItemSelectedListener  internalItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            getAdapter().onItemClicked(position, view);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
    //点击事件
    private final AdapterView.OnItemClickListener     internalItemClickListener    = new AdapterView.OnItemClickListener() {
        @SuppressWarnings("unchecked")
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            getAdapter().onItemClicked(position, view);
        }
    };
    //长按事件
    private final AdapterView.OnItemLongClickListener internalLongClickListener    = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            return getAdapter().onItemLongClicked(position, view);
        }
    };
}

