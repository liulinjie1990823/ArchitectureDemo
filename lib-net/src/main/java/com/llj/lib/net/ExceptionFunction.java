package com.llj.lib.net;

import io.reactivex.Single;
import io.reactivex.functions.Function;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/7
 */
public class ExceptionFunction<R> implements Function<Throwable, Single<R>> {
    @Override
    public Single<R> apply(Throwable throwable) {
        return Single.error(ExceptionHandle.handleException(throwable));
    }
}
