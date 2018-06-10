package com.llj.lib.net;

import com.uber.autodispose.AutoDisposeConverter;

import java.util.HashMap;
import java.util.Set;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

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


    public <Data> void subscribeApi(Single<Response<IResponse<Data>>> observable,
                                      AutoDisposeConverter<IResponse<Data>> autoDisposeConverter,
                                      BaseApiObserver<Data> observer) {

        if (observer.getRequestTag() > 0) {
            add(observer.getRequestTag(), observer.getDisposable());
        }

        observable
                .subscribeOn(Schedulers.io())//指定io
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunction<>())
                .onErrorResumeNext(new ExceptionFunction<>())
                .as(autoDisposeConverter)
                .subscribe(observer);
    }

}
