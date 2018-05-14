package com.llj.lib.net;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/7
 */
public interface IResponse<T> {

    int getCode();

    String getMsg();

    T getData();

    boolean isOk();
}
