package com.llj.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.llj.adapter.observable.ObservableList;
import com.llj.adapter.observable.ObservableListWrapper;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * PROJECT:UniversalAdapter
 * DESCRIBE:
 * ObservableList：实现该接口以封装对集合的操作
 * Created by llj on 2017/2/10.
 */

public abstract class ListBasedAdapter<Item, Holder extends ViewHolder> extends UniversalAdapter<Item, Holder> implements ObservableList<Item> {
    private List<Item> mList;
    private int        mClickPosition;

    public ListBasedAdapter() {
        this(null);
    }

    public ListBasedAdapter(List<Item> list) {
        setItemsList(list);
    }

    public ListBasedAdapter(ObservableListWrapper<Item> list) {
        setItemsList(list);
    }


    public int getClickPosition() {
        return mClickPosition;
    }

    public void setClickPosition(int clickPosition) {
        mClickPosition = clickPosition;
    }

    public Item getClickItem() {
        if (size() > mClickPosition)
            return get(mClickPosition);
        return null;
    }

    protected void setItemsList(ObservableList<Item> list) {
        if (list != null) {
            list.getListObserver().addListener(observableListener);
        }
        setItemsList((List<Item>) list);
    }

    protected void unbindList() {
        if (mList instanceof ObservableList<?>) {
            ((ObservableList<Item>) mList).getListObserver().removeListener(observableListener);
        }
    }

    protected void setItemsList(List<Item> list) {
        unbindList();
        if (list == null) {
            list = new LinkedList<>();
        }
        mList = list;
        notifyDataSetChanged();
    }

    public List<Item> getItemsList() {
        return mList;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public int size() {
        return mList.size();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isEmpty() {
        return mList.isEmpty();
    }


    ///////////////////////////////////////////////////////////////////////////
    // 增删改对应刷新操作
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void add(int index, Item object) {
        mList.add(index, object);
        onItemRangeInserted(index, 1);
    }

    @Override
    public boolean add(Item object) {
        int location = mList.size();
        final boolean result = mList.add(object);
        onItemRangeInserted(location, 1);
        return result;
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends Item> collection) {
        int location = mList.size();
        if (mList.addAll(collection)) {
            onItemRangeInserted(location, collection.size());
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends Item> collection) {
        if (mList.addAll(index, collection)) {
            onItemRangeInserted(index, collection.size());
            return true;
        }
        return false;
    }

    @Override
    public Item remove(int index) {
        Item result = mList.remove(index);
        onItemRangeRemoved(index, 1);
        return result;
    }

    @Override
    public boolean remove(@NonNull Object object) {
        int location = mList.indexOf(object);
        if (location >= 0) {
            mList.remove(location);
            onItemRangeRemoved(location, 1);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> collection) {
        boolean result = mList.removeAll(collection);
        if (result) {
            onGenericChange();
        }
        return result;
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> collection) {
        boolean result = mList.retainAll(collection);
        if (result) {
            onGenericChange();
        }
        return result;
    }

    @Override
    public void clear() {
        int count = size();
        mList.clear();
        onItemRangeRemoved(0, count);
    }


    @Override
    public Item set(int index, Item object) {
        Item result = mList.set(index, object);
        if (!result.equals(object)) {
            onItemRangeChanged(index, 1);
        }
        return result;
    }

    @Nullable
    @Override
    public Item get(int position) {
        return mList.get(position);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public boolean contains(Object object) {
        return mList.contains(object);
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> collection) {
        return mList.containsAll(collection);
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return mList.toArray();
    }

    @NonNull
    @Override
    public <T> T[] toArray(@NonNull T[] array) {
        return mList.toArray(array);
    }


    @Override
    public int indexOf(Object object) {
        return mList.indexOf(object);
    }

    @Override
    public int lastIndexOf(Object object) {
        return mList.lastIndexOf(object);
    }

    @NonNull
    @Override
    public Iterator<Item> iterator() {
        return mList.iterator();
    }

    @NonNull
    @Override
    public ListIterator<Item> listIterator() {
        return mList.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<Item> listIterator(int index) {
        return mList.listIterator();
    }

    @NonNull
    @Override
    public List<Item> subList(int start, int end) {
        return mList.subList(start, end);
    }


    ///////////////////////////////////////////////////////////////////////////
    // 
    ///////////////////////////////////////////////////////////////////////////
    @Override
    protected Holder onCreateViewHolder(ViewGroup parent, int itemType) {
        return null;
    }


    @Override
    public void notifyDataSetChanged() {
        onGenericChange();
    }
}
