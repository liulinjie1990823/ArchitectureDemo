package com.llj.lib.net;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/7
 */
public interface IResponse<Data> {

    int getCode();

    String getMsg();

    Data getData();

    boolean isOk();
}
