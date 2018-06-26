package com.llj.lib.net.observer;

import io.reactivex.disposables.Disposable;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/10
 */
public interface IObserverTag extends ITag {

    Disposable getDisposable();
}
