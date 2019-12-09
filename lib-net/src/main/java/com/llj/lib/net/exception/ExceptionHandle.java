package com.llj.lib.net.exception;

import android.net.ParseException;
import com.google.gson.JsonParseException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.PortUnreachableException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import org.json.JSONException;
import retrofit2.HttpException;

/**
 * ArchitectureDemo
 *
 * describe：异常统一处理类
 *
 * @author liulj
 * @date 2018/5/7
 */
public class ExceptionHandle {

  //对应HTTP的状态码
  private static final int UNAUTHORIZED          = 401;
  private static final int FORBIDDEN             = 403;
  private static final int NOT_FOUND             = 404;
  private static final int REQUEST_TIMEOUT       = 408;
  private static final int INTERNAL_SERVER_ERROR = 500;
  private static final int BAD_GATEWAY           = 502;
  private static final int SERVICE_UNAVAILABLE   = 503;
  private static final int GATEWAY_TIMEOUT       = 504;

  public static ApiException handleException(Throwable e) {
    ApiException ex;
    //HTTP错误
    if (e instanceof HttpException) {
      HttpException httpException = (HttpException) e;
      ex = new ApiException(ApiException.HTTP_ERROR, e);
      switch (httpException.code()) {
        case UNAUTHORIZED:
        case FORBIDDEN:
        case NOT_FOUND:
        case REQUEST_TIMEOUT:
        case GATEWAY_TIMEOUT:
        case INTERNAL_SERVER_ERROR:
        case BAD_GATEWAY:
        case SERVICE_UNAVAILABLE:
        default:
          ex.setDisplayMessage("网络错误");  //均视为网络错误
          break;
      }
      return ex;
    } else if (e instanceof BusinessException) {    //服务器返回的错误
      BusinessException resultException = (BusinessException) e;
      ex = new ApiException(resultException.getCode(), resultException);
      ex.setDisplayMessage(resultException.getMessage());
      return ex;
    } else if (e instanceof JsonParseException
        || e instanceof JSONException
        || e instanceof ParseException) {
      ex = new ApiException(ApiException.PARSE_ERROR, e);
      ex.setDisplayMessage("解析错误");//均视为解析错误
      return ex;
    } else if (e instanceof ConnectException
        || e instanceof BindException
        || e instanceof NoRouteToHostException
        || e instanceof PortUnreachableException
        || e instanceof SocketException
        || e instanceof UnknownServiceException
        || e instanceof UnknownHostException) {
      ex = new ApiException(ApiException.NETWORK_ERROR, e);
      ex.setDisplayMessage("连接错误");//均视为网络错误
      return ex;
    } else {
      ex = new ApiException(ApiException.UNKNOWN, e);
      ex.setDisplayMessage("未知错误");//未知错误
      return ex;
    }
  }
}
