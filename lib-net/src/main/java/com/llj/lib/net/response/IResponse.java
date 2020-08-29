package com.llj.lib.net.response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * describe
 *
 * @author liulinjie
 * @date 2020/8/29 1:44 PM
 */
public interface IResponse<Data> {


  @NonNull
  Status getStatus();

  int getCode();

  @Nullable
  String getMessage();

  @Nullable
  Data getData();

  boolean isOk();
}
