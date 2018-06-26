package com.llj.lib.net.response;

import android.support.annotation.Nullable;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/7
 */
public class BaseResponse<T> implements IResponse<T> {
    private int    code;
    private String msg;
    private T      data;

    public BaseResponse(int code, String msg, T data) {
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
    public T getData() {
        return data;
    }


    @Override
    public boolean isOk() {
        return code == 0;
    }


    public static <T> BaseResponse<T> success(@Nullable T data) {
        return new BaseResponse<>(0, "", data);
    }
}
