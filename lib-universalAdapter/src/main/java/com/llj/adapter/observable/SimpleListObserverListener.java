package com.llj.adapter.observable;

import android.support.annotation.Nullable;

/**
 * PROJECT:CommonAdapter
 *
 * DESCRIBE: 用于PagerAdapterConverter刷新，统一用onGenericChange处理
 *
 * @Created by llj
 * @on 2017/2/11.
 */

public abstract class SimpleListObserverListener<T> implements ListObserverListener<T> {

  @Override
  public void onItemRangeChanged(ListObserver<T> observer, int start, int count,
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
