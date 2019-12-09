package com.llj.lib.net.response;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/7
 */
public interface IResponse<Data> {

    int getCode();

    String getMessage();

    Data getData();

    boolean isOk();
}
