package com.llj.lib.net.exception;

import com.llj.lib.net.response.BaseResponse;

import io.reactivex.Single;
import io.reactivex.functions.Function;

/**
 * ArchitectureDemo
 * describe 请求错误处理
 * author liulj
 * date 2018/5/7
 */
public class ExceptionFunction<T> implements Function<Throwable, Single<BaseResponse<T>>> {
    @Override
    public Single<BaseResponse<T>> apply(Throwable throwable) {
        return Single.error(ExceptionHandle.handleException(throwable));
    }
}
