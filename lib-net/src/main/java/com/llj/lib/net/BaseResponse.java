package com.llj.lib.net;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/7
 */
public class BaseResponse<T> implements IResponse<T>  {
    private int    code;
    private String msg;
    private T      data;

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getMsg() {
        return null;
    }

    @Override
    public T getData() {
        return null;
    }


    @Override
    public boolean isOk() {
        return code == 0;
    }
}
