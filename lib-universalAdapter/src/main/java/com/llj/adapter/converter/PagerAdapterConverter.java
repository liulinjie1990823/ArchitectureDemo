package com.llj.adapter.converter;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.llj.adapter.UniversalConverter;
import com.llj.adapter.UniversalAdapter;
import com.llj.adapter.ViewHolder;
import com.llj.adapter.listener.FooterClickListener;
import com.llj.adapter.listener.FooterListenerAdapter;
import com.llj.adapter.listener.FooterLongClickListener;
import com.llj.adapter.listener.HeaderClickListener;
import com.llj.adapter.listener.HeaderListenerAdapter;
import com.llj.adapter.listener.HeaderLongClickListener;
import com.llj.adapter.listener.ItemClickWrapper;
import com.llj.adapter.listener.ItemClickedListener;
import com.llj.adapter.listener.ItemDoubleClickedListener;
import com.llj.adapter.listener.ItemListenerAdapter;
import com.llj.adapter.listener.ItemLongClickedListener;
import com.llj.adapter.observable.ListObserver;
import com.llj.adapter.observable.ListObserverListener;
import com.llj.adapter.observable.SimpleListObserverListener;
import com.llj.adapter.util.ThreadingUtils;
import com.llj.adapter.util.UniversalAdapterUtils;

/**
 * PROJECT:UniversalAdapter
 * DESCRIBE:
 * Created by llj on 2017/2/11.
 */

public class PagerAdapterConverter<Item, Holder extends ViewHolder> extends PagerAdapter
        implements HeaderListenerAdapter,
        FooterListenerAdapter,
        ItemListenerAdapter<Item, Holder>,
        UniversalConverter<Item, Holder> {

    private UniversalAdapter<Item, Holder> mUniversalAdapter;
    private ItemClickWrapper<Item, Holder> mItemClickedWrapper;

    PagerAdapterConverter(@NonNull UniversalAdapter<Item, Holder> universalAdapter, ViewPager viewPager) {
        universalAdapter.checkIfBoundAndSet();
        mItemClickedWrapper = new ItemClickWrapper<>(this);

        setAdapter(universalAdapter);
        viewPager.setAdapter(this);

        superNotifyDataSetChanged();
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

    ///////////////////////////////////////////////////////////////////////////
    // 
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public UniversalAdapter<Item, Holder> getAdapter() {
        return mUniversalAdapter;
    }


    @Override
    public void setAdapter(@NonNull UniversalAdapter<Item, Holder> listAdapter) {
        if (getAdapter() != null) {
            getAdapter().getListObserver().removeListener(mInternalListObserverListener);
        }
        this.mUniversalAdapter = listAdapter;
        listAdapter.getListObserver().addListener(mInternalListObserverListener);
    }

    @Override
    public void cleanup() {
        getAdapter().getListObserver().removeListener(mInternalListObserverListener);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 
    ///////////////////////////////////////////////////////////////////////////


    @Override
    public int getItemPosition(Object object) {
        return getAdapter().getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewHolder holder = getAdapter().createViewHolder(container, getAdapter().getInternalItemViewType(position));
        getAdapter().bindViewHolder(holder, position);

        View view = holder.itemView;
        UniversalAdapterUtils.setViewHolder(view, holder);
        mItemClickedWrapper.register(view);

        container.addView(view);
        return holder.itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return getAdapter().getInternalCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 刷新方法
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void notifyDataSetChanged() {
        mUniversalAdapter.notifyDataSetChanged();
    }

    private final ListObserverListener<Item> mInternalListObserverListener = new SimpleListObserverListener<Item>() {
        @Override
        public void onGenericChange(ListObserver<Item> listObserver) {
            superNotifyDataSetChangedOnUIThread();
        }
    };

    protected void superNotifyDataSetChangedOnUIThread() {
        ThreadingUtils.runOnUIThread(mSuperDataSetChangedRunnable);
    }

    private final Runnable mSuperDataSetChangedRunnable = new Runnable() {
        @Override
        public void run() {
            superNotifyDataSetChanged();
        }
    };

    protected void superNotifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}

