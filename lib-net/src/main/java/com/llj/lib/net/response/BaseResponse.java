package com.llj.lib.net.response;

import androidx.annotation.NonNull;
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

  /**
   * 0是正常码
   */
  @NonNull
  private Status status;
  private int    code;
  @SerializedName("message")
  @Nullable
  private String msg;
  @Nullable
  private Data   data;

  public BaseResponse(@NonNull Status status, int code, @Nullable String msg, @Nullable Data data) {
    this.status = status;
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  @NonNull
  @Override
  public Status getStatus() {
    return status;
  }


  @Override
  public int getCode() {
    return code;
  }

  @Nullable
  @Override
  public String getMessage() {
    return msg;
  }

  @Nullable
  @Override
  public Data getData() {
    return data;
  }

  public void setData(@Nullable Data data) {
    this.data = data;
  }

  @Override
  public boolean isOk() {
    return code == 0;
  }

  public static <Data> BaseResponse<Data> loading(@Nullable Data data) {
    return new BaseResponse<>(Status.LOADING, 0, "", data);
  }

  public static <Data> BaseResponse<Data> success(@Nullable Data data) {
    return new BaseResponse<>(Status.SUCCESS, 0, "", data);
  }

  public static <Data> BaseResponse<Data> error(@Nullable Data data, Throwable throwable) {
    if (throwable instanceof ApiException) {
      ApiException apiException = (ApiException) throwable;
      return new BaseResponse<>(Status.ERROR, apiException.getCode(),
          apiException.getDisplayMessage(), data);
    }
    return new BaseResponse<>(Status.ERROR, -10086, "未知错误", data);
  }
}
