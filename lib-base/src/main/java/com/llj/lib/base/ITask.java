package com.llj.lib.base;

import io.reactivex.disposables.Disposable;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/7/5
 */
public interface ITask {

    void addDisposable(int tag, Disposable disposable);


    void removeDisposable(int tag);

    void removeAllDisposable();
}
