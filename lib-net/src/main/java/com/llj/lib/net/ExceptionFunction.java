package com.llj.lib.net;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/7
 */
public class ExceptionFunction<R> implements Function<Throwable, Observable<R>> {
    @Override
    public Observable<R> apply(Throwable throwable) {
        return Observable.error(ExceptionHandle.handleException(throwable));
    }
}
