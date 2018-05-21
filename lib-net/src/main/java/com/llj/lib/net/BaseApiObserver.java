package com.llj.lib.net;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/7
 */
public abstract class BaseApiObserver<T> implements Observer<T>, IObserverTag {
    private Disposable     mDisposable;
    private int            mTag;
    private IRequestDialog mIRequestDialog;

    public BaseApiObserver(int tag) {
        mTag = tag;
    }

    public BaseApiObserver(IRequestDialog IRequestDialog) {
        mIRequestDialog = IRequestDialog;
        if (mIRequestDialog != null) {
            setTag(IRequestDialog.getTag());
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void setTag(int tag) {
        this.mTag = tag;
    }

    @Override
    public int getTag() {
        return mTag;
    }

    @Override
    public Disposable getDisposable() {
        return mDisposable;
    }
}
