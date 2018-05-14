package com.llj.lib.net;

import io.reactivex.disposables.Disposable;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/10
 */


public interface RxActionManager<T> {

    void add(T tag, Disposable disposable);

    void remove(T tag);

    void removeAll();

    void cancel(T tag);

    void cancelAll();
}