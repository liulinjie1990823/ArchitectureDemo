package com.llj.lib.net;

import android.support.annotation.NonNull;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/7
 */
public abstract class BaseApiObserver<Data> implements SingleObserver<BaseResponse<Data>>, IObserverTag {
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
    public void onSubscribe(@NonNull Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onSuccess(@NonNull BaseResponse<Data> response) {

    }

    @Override
    public void onError(@NonNull Throwable t) {
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
