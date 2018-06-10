package com.llj.lib.net;

import io.reactivex.functions.Function;
import retrofit2.Response;

/**
 * ArchitectureDemo
 * describe:处理http状态码层面的逻辑
 * author liulj
 * date 2018/5/7
 */
public class HttpResultFunction<T> implements Function<Response<IResponse<T>>, IResponse<T>>{
    @Override
    public IResponse<T> apply(Response<IResponse<T>> response) throws Exception {
        return response.body();
    }
}
