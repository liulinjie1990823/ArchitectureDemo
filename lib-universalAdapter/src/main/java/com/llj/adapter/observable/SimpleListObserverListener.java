package com.llj.adapter.observable;

import androidx.annotation.Nullable;

/**
 * PROJECT:CommonAdapter
 * DESCRIBE: 用于PagerAdapterConverter刷新，统一用onGenericChange处理
 * Created by llj on 2017/2/11.
 */

public abstract class SimpleListObserverListener<T> implements ListObserverListener<T> {

    @Override
    public void onItemRangeChanged(ListObserver<T> observer, int startPosition, int itemCount,
        @Nullable Object payload) {
        onGenericChange(observer);
    }

    @Override
    public void onItemRangeChanged(ListObserver<T> observer, int startPosition, int itemCount) {
        onGenericChange(observer);
    }

    @Override
    public void onItemRangeInserted(ListObserver<T> observer, int startPosition, int itemCount) {
        onGenericChange(observer);
    }

    @Override
    public void onItemRangeRemoved(ListObserver<T> observer, int startPosition, int itemCount) {
        onGenericChange(observer);
    }
}
