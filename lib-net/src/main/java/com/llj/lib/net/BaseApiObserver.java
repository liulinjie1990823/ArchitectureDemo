package com.llj.lib.net;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/7
 */
public abstract class BaseApiObserver<T> implements Observer<Response<T>>, IObserverTag {
    private Disposable     mDisposable;
    private int            mTag;
    private IRequestDialog mIRequestDialog;

    public BaseApiObserver(int tag) {
        mTag = tag;
    }

    public BaseApiObserver(IRequestDialog IRequestDialog) {
        mIRequestDialog = IRequestDialog;
        if (mIRequestDialog != null) {
            setRequestTag(IRequestDialog.getRequestTag());
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onNext(Response<T> response) {

    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void setRequestTag(int tag) {
        this.mTag = tag;
    }

    @Override
    public int getRequestTag() {
        return mTag;
    }

    @Override
    public Disposable getDisposable() {
        return mDisposable;
    }
}
