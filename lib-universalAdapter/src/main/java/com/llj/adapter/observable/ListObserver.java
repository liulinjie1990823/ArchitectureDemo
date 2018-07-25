package com.llj.adapter.observable;

/**
 * PROJECT:UniversalAdapter
 * DESCRIBE:
 * Created by llj on 2017/1/14.
 */

public interface ListObserver<T> {
    void addListener(ListObserverListener<T> listener);

    boolean removeListener(ListObserverListener<T> listener);
}