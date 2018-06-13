package com.llj.lib.net;

import io.reactivex.Single;
import io.reactivex.functions.Function;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/7
 */
public class ExceptionFunction<Data> implements Function<Throwable, Single<BaseResponse<Data>>> {
    @Override
    public Single<BaseResponse<Data>> apply(Throwable throwable) {
        return Single.error(ExceptionHandle.handleException(throwable));
    }
}
