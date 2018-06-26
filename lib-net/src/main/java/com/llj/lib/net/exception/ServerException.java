package com.llj.lib.net.exception;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/8
 */
public class ServerException extends RuntimeException {
    private int code;
    private String msg;

    public ServerException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}