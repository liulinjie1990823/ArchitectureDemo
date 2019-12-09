package com.llj.lib.net.response;

import com.llj.lib.net.exception.BusinessException;
import io.reactivex.functions.Function;
import retrofit2.Response;

/**
 * ArchitectureDemo
 *
 * describe:处理http状态码层面的逻辑
 *
 * @author liulj
 * @date 2018/5/7
 */
public class HttpResponseFunction<T> implements
    Function<Response<BaseResponse<T>>, BaseResponse<T>> {

  @Override
  public BaseResponse<T> apply(Response<BaseResponse<T>> response) throws Exception {

    BaseResponse<T> body = response.body();
    //有body且http状态码在指定范围内的
    if (body != null && isSuccessful(response)) {

      if (body.isOk()) {
        //code为0
        return body;
      }
      // //code非0，业务异常，但是有body数据的
      throw new BusinessException(body.getCode(), body.getMessage());
    }
    return body;
  }

  private boolean isSuccessful(Response response) {
    //200..299
    if (response.isSuccessful()) {
      return true;
    }
    //Not Modified
    if (response.code() == 304) {
      return true;
    }
    return false;
  }
}
