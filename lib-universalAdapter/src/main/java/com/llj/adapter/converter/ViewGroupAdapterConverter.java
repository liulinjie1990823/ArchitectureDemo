package com.llj.adapter.converter;

import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
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
import com.llj.adapter.util.UniversalAdapterUtils;

import java.lang.reflect.Field;

/**
 * PROJECT:CommonAdapter
 * DESCRIBE:
 * Created by llj on 2017/2/11.
 */

public class ViewGroupAdapterConverter<Item, Holder extends ViewHolder>
        implements HeaderListenerAdapter,
        FooterListenerAdapter,
        ItemListenerAdapter<Item, Holder>,
        UniversalConverter<Item, Holder> {

    private ViewGroup                      viewGroup;
    private UniversalAdapter<Item, Holder> universalAdapter;
    private ItemClickWrapper<Item, Holder> itemClickWrapper;

    ViewGroupAdapterConverter(@NonNull UniversalAdapter<Item, Holder> adapter, @NonNull ViewGroup viewGroup) {
        adapter.checkIfBoundAndSet();

        setAdapter(adapter);
        this.viewGroup = viewGroup;

        itemClickWrapper = new ItemClickWrapper<>(this);
        populateAll();
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
    public void setAdapter(@NonNull UniversalAdapter<Item, Holder> adapter) {
        if (getAdapter() != null) {
            getAdapter().getListObserver().removeListener(listChangeListener);
        }

        this.universalAdapter = adapter;
        adapter.getListObserver().addListener(listChangeListener);

        populateAll();
    }

    @Override
    public UniversalAdapter<Item, Holder> getAdapter() {
        return universalAdapter;
    }

    public ViewGroup getViewGroup() {
        return viewGroup;
    }

    @Override
    public void cleanup() {
        if (getAdapter() != null) {
            getAdapter().getListObserver().removeListener(listChangeListener);
        }
        this.viewGroup = null;
    }

    private void clear() {
        viewGroup.removeAllViews();
    }

    private void populateAll() {
        if (viewGroup != null) {
            clear();

            if (getAdapter() != null) {
                final int count = getAdapter().getInternalCount();
                for (int i = 0; i < count; i++) {
                    addItem(i);
                }
            }

        }
    }

    private void addItem(int position) {
        ViewHolder holder = getAdapter().createViewHolder(getViewGroup(), universalAdapter.getInternalItemViewType(position));
        getAdapter().bindViewHolder(holder, position);

        View view = holder.itemView;
        UniversalAdapterUtils.setViewHolder(view, holder);

        if (!hasOnClickListeners(view)) {
            itemClickWrapper.register(view);
        }

        getViewGroup().addView(view, position);
    }

    public boolean hasOnClickListeners(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            return getOnClickListenerV14(view) != null;
        } else {
            return getOnClickListenerV(view) != null;
        }
    }

    //Used for APIs lower than ICS (API 14)
    private View.OnClickListener getOnClickListenerV(View view) {
        View.OnClickListener retrievedListener = null;
        String viewStr = "android.view.View";
        Field field;

        try {
            field = Class.forName(viewStr).getDeclaredField("mOnClickListener");
            retrievedListener = (View.OnClickListener) field.get(view);
        } catch (NoSuchFieldException ex) {
            Log.e("Reflection", "No Such Field.");
        } catch (IllegalAccessException ex) {
            Log.e("Reflection", "Illegal Access.");
        } catch (ClassNotFoundException ex) {
            Log.e("Reflection", "Class Not Found.");
        }

        return retrievedListener;
    }

    //Used for new ListenerInfo class structure used beginning with API 14 (ICS)
    private View.OnClickListener getOnClickListenerV14(View view) {
        View.OnClickListener retrievedListener = null;
        String viewStr = "android.view.View";
        String lInfoStr = "android.view.View$ListenerInfo";

        try {
            Field listenerField = Class.forName(viewStr).getDeclaredField("mListenerInfo");
            Object listenerInfo = null;

            if (listenerField != null) {
                listenerField.setAccessible(true);
                listenerInfo = listenerField.get(view);
            }

            Field clickListenerField = Class.forName(lInfoStr).getDeclaredField("mOnClickListener");

            if (clickListenerField != null && listenerInfo != null) {
                retrievedListener = (View.OnClickListener) clickListenerField.get(listenerInfo);
            }
        } catch (NoSuchFieldException ex) {
            Log.e("Reflection", "No Such Field.");
        } catch (IllegalAccessException ex) {
            Log.e("Reflection", "Illegal Access.");
        } catch (ClassNotFoundException ex) {
            Log.e("Reflection", "Class Not Found.");
        }

        return retrievedListener;
    }

    private ListObserverListener<Item> listChangeListener = new SimpleListObserverListener<Item>() {
        @Override
        public void onGenericChange(ListObserver<Item> observer) {
            populateAll();
        }

        @Override
        public void onItemRangeChanged(ListObserver<Item> observer, int startPosition, int itemCount) {
            for (int i = startPosition; i < startPosition + itemCount; i++) {
                ViewGroup childAt = (ViewGroup) getViewGroup().getChildAt(i);
                if (childAt != null) {
                    getAdapter().bindViewHolder(UniversalAdapterUtils.getViewHolder(childAt), i);
                }
            }
        }
    };
}

