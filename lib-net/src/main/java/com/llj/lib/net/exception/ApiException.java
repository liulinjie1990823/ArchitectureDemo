package com.llj.lib.net.exception;

/**
 * ArchitectureDemo
 *
 * describe:接口异常的最后统一类
 *
 * @author liulj
 * @date 2018/5/7
 */
public class ApiException extends Exception {

  public static final int UNKNOWN       = 1000;//未知错误
  public static final int PARSE_ERROR   = 1001;//解析错误
  public static final int NETWORK_ERROR = 1002;//网络连接错误
  public static final int HTTP_ERROR    = 1003;//http状态码异常

  private int    code;
  private String displayMessage;

  public ApiException(int code, Throwable throwable) {
    super(throwable);
    this.code = code;

  }

  public void setDisplayMessage(String displayMessage) {
    this.displayMessage = displayMessage;
  }

  public String getDisplayMessage() {
    return displayMessage;
  }

  public int getCode() {
    return code;
  }
}
