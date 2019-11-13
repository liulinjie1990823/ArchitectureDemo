package com.llj.lib.net.response;

import io.reactivex.functions.Function;
import retrofit2.Response;

/**
 * ArchitectureDemo describe:处理http状态码层面的逻辑 author liulj date 2018/5/7
 */
public class HttpResponseFunction<T> implements
    Function<Response<BaseResponse<T>>, BaseResponse<T>> {

  @Override
  public BaseResponse<T> apply(Response<BaseResponse<T>> response) throws Exception {
    return response.body();
  }
}
