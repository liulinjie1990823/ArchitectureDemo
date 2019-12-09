package com.llj.lib.net.response;

import androidx.annotation.Nullable;
import com.google.gson.annotations.SerializedName;
import com.llj.lib.net.exception.ApiException;

/**
 * ArchitectureDemo
 *
 * describe:
 *
 * @author liulj
 * @date 2018/5/7
 */
public class BaseResponse<Data> implements IResponse<Data> {

  private int    code;//0是正常码
  @SerializedName("message")
  private String msg;
  private Data   data;

  public BaseResponse(int code, String msg, Data data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }


  @Override
  public int getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return msg;
  }

  @Override
  public Data getData() {
    return data;
  }

  public void setData(Data data) {
    this.data = data;
  }

  @Override
  public boolean isOk() {
    return code == 0;
  }


  public static <Data> BaseResponse<Data> success(@Nullable Data data) {
    return new BaseResponse<>(0, "", data);
  }

  public static <Data> BaseResponse<Data> error(@Nullable Data data, Throwable throwable) {
    if (throwable instanceof ApiException) {
      ApiException apiException = (ApiException) throwable;
      return new BaseResponse<>(apiException.getCode(), apiException.getDisplayMessage(), data);
    }

    return new BaseResponse<>(-10086, "未知错误", data);
  }
}
