package com.llj.lib.base.event;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-26
 */
public class BaseEvent<T> {
    private int    code;
    private String pageName;
    private String message;
    private String delayMessage;//用于在页面resume后调用
    private T      data;

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

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageName() {
        return pageName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDelayMessage() {
        return delayMessage;
    }

    public void setDelayMessage(String delayMessage) {
        this.delayMessage = delayMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
