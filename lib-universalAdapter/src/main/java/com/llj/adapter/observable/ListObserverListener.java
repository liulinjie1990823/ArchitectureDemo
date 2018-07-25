package com.llj.adapter.observable;

/**
 * Interface which listens to {@link ListObserver} changes.
 *
 * @param <T> The type of items stored in the observed list.
 */
public interface ListObserverListener<T> {
    void onItemRangeChanged(ListObserver<T> observer, int startPosition, int itemCount);

    void onItemRangeInserted(ListObserver<T> observer, int startPosition, int itemCount);

    void onItemRangeRemoved(ListObserver<T> observer, int startPosition, int itemCount);

    void onGenericChange(ListObserver<T> observer);
}
