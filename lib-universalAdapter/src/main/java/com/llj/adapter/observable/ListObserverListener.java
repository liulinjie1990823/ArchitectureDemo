package com.llj.adapter.observable;


import androidx.annotation.Nullable;

/**
 * Interface which listens to {@link ListObserver} changes.
 *
 * @param <T> The type of items stored in the observed list.
 */
public interface ListObserverListener<T> {

  void onItemRangeChanged(ListObserver<T> observer, int start, int count, @Nullable Object payload);

  void onItemRangeChanged(ListObserver<T> observer, int start, int count);

  void onItemRangeInserted(ListObserver<T> observer, int start, int count);

  void onItemRangeRemoved(ListObserver<T> observer, int start, int count);

  void onGenericChange(ListObserver<T> observer);
}
