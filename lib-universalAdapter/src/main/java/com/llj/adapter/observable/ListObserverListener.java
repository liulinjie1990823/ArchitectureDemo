package com.llj.adapter.observable;

import androidx.annotation.Nullable;

/**
 * Interface which listens to {@link ListObserver} changes.
 *
 * @param <T> The type of items stored in the observed list.
 */
public interface ListObserverListener<T> {
    void onItemRangeChanged(ListObserver<T> observer, int startPosition, int itemCount,@Nullable Object payload);

    void onItemRangeChanged(ListObserver<T> observer, int startPosition, int itemCount);

    void onItemRangeInserted(ListObserver<T> observer, int startPosition, int itemCount);

    void onItemRangeRemoved(ListObserver<T> observer, int startPosition, int itemCount);

    void onGenericChange(ListObserver<T> observer);
}
