package com.llj.lib.base.event;

/**
 * ArchitectureDemo. describe:
 *
 * @author llj
 * @date 2019-06-26
 */
public class BaseEvent {

  private int    code;
  private String pageName;
  private String message;
  private String resumedMessage;//用于在页面resume后调用
  private Object data;

  public BaseEvent() {
  }

  public BaseEvent(int code) {
    this.code = code;
  }

  public BaseEvent(int code, String message, Object data) {
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

  public String getResumedMessage() {
    return resumedMessage;
  }

  public void setResumedMessage(String resumedMessage) {
    this.resumedMessage = resumedMessage;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
}
