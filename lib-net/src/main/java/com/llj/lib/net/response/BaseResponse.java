package com.llj.lib.net.response;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * ArchitectureDemo describe: author liulj date 2018/5/7
 */
public class BaseResponse<Data> implements IResponse<Data> {

  private int    code;
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
    return 0;
  }

  @Override
  public String getMsg() {
    return msg;
  }

  @Override
  public Data getData() {
    return data;
  }


  @Override
  public boolean isOk() {
    return code == 0;
  }


  public static <Data> BaseResponse<Data> success(@Nullable Data data) {
    return new BaseResponse<>(0, "", data);
  }
}
