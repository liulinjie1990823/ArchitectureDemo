package com.llj.lib.net;

import java.util.HashMap;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/10
 */
public class RxApiManager implements RxActionManager<Object> {
    private static RxApiManager sInstance = null;

    private HashMap<Object, Disposable> mArrayMap;

    public static RxApiManager get() {
        if (sInstance == null) {
            synchronized (RxApiManager.class) {
                if (sInstance == null) {
                    sInstance = new RxApiManager();
                }
            }
        }
        return sInstance;
    }

    public RxApiManager() {
        mArrayMap = new HashMap<>();
    }

    @Override
    public void add(Object tag, Disposable disposable) {
        mArrayMap.put(tag, disposable);
    }

    @Override
    public void remove(Object tag) {
        if (!mArrayMap.isEmpty()) {
            mArrayMap.remove(tag);
        }
    }

    @Override
    public void removeAll() {
        if (!mArrayMap.isEmpty()) {
            mArrayMap.clear();
        }
    }

    @Override
    public void cancel(Object tag) {
        if (mArrayMap.isEmpty() || mArrayMap.get(tag) == null) {
            return;
        }
        if (!mArrayMap.get(tag).isDisposed()) {
            mArrayMap.get(tag).dispose();
            mArrayMap.remove(tag);
        }
    }

    @Override
    public void cancelAll() {
        if (mArrayMap.isEmpty()) {
            return;
        }
        Set<Object> keys = mArrayMap.keySet();
        for (Object apiKey : keys) {
            cancel(apiKey);
        }
    }


    public <T> void toSubscribe(Observable<T> observable, ObservableTransformer transformer, BaseApiObserver<T> observer) {
        if (observer.getTag() > 0) {
            add(observer.getTag(), observer.getDisposable());
        }
        observable.compose(transformer)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
