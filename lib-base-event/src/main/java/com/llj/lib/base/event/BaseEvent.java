package com.llj.lib.base.event;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-26
 */
public class BaseEvent<T> {
    private int code;
    private String message;
    private T data;

    public BaseEvent() {
    }

    public BaseEvent(int code) {
        this.code = code;
    }

    public BaseEvent(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
