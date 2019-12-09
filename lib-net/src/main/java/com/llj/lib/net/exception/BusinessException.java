package com.llj.lib.net.exception;

import androidx.annotation.Nullable;

/**
 * ArchitectureDemo
 *
 * describe:业务异常
 *
 * @author liulj
 * @date 2018/5/7
 */
public class BusinessException extends Exception {

  private int    code;
  private String message;

  public BusinessException(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  @Nullable
  @Override
  public String getMessage() {
    return message;
  }
}
