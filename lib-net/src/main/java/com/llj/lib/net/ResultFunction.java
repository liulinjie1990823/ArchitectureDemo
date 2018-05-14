package com.llj.lib.net;

import io.reactivex.functions.Function;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/7
 */
public class ResultFunction<T> implements Function<IResponse<T>, T> {

    @Override
    public T apply(IResponse<T> iResponse) throws Exception {
        return iResponse.getData();
    }
}
