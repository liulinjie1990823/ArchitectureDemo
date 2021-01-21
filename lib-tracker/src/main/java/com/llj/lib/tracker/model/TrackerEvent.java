package com.llj.lib.tracker.model;

import androidx.annotation.StringDef;
import com.llj.lib.tracker.ITracker;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ArchitectureDemo. describe:
 *
 * @author llj
 * @date 2019-06-19
 */
public class TrackerEvent {

  public static final String TYPE_PAGE      = "Page";
  public static final String APP_APPEAR     = "AppAppear";
  public static final String APP_DISAPPEAR  = "AppDisappear";
  public static final String PAGE_APPEAR    = "PageAppear";
  public static final String PAGE_DISAPPEAR = "PageDisappear";

  public static final String TYPE_ACTION  = "Action";
  public static final String ACTION_CLICK = "Click";
  public static final String ACTION_SHOW  = "show";


  //生命周期事件
  @StringDef({APP_APPEAR, APP_DISAPPEAR, PAGE_APPEAR, PAGE_DISAPPEAR})
  @Retention(RetentionPolicy.SOURCE)
  public @interface PageType {

  }

  //动作事件
  @StringDef({ACTION_CLICK, ACTION_SHOW})
  @Retention(RetentionPolicy.SOURCE)
  public @interface ActionType {

  }

  @StringDef({TYPE_PAGE, TYPE_ACTION})
  @Retention(RetentionPolicy.SOURCE)
  public @interface EventType {

  }

  //事件类型
  public String eventType = TYPE_PAGE;
  //事件名称
  public String eventName = ACTION_SHOW;
  //页面名称
  public String pageName;
  public String pageId;
  public String pageTitle;
  public long   dateTime;

  public String uid;
  public String extraData;

  public boolean sync = true;

  public TrackerEvent() {
  }

  public void setPageId(String pageId) {
    this.pageId = pageId;
  }

  public TrackerEvent(@EventType String eventType, String eventName, String pageName,
      long dateTime) {
    this.eventType = eventType;
    this.eventName = eventName;
    this.dateTime = dateTime;
    this.pageName = pageName;
  }

  public TrackerEvent(@EventType String eventType, String eventName, String pageName) {
    this.eventType = eventType;
    this.eventName = eventName;
    this.pageName = pageName;
  }

  public TrackerEvent(@EventType String eventType, String eventName, ITracker tracker,
      long dateTime) {
    this.eventType = eventType;
    this.eventName = eventName;
    this.pageName = tracker.getPageName();
    this.pageId = tracker.getPageId();
    this.dateTime = dateTime;
  }

  public TrackerEvent(@EventType String eventType, String eventName, ITracker tracker) {
    this.eventType = eventType;
    this.eventName = eventName;
    this.pageName = tracker.getPageName();
    this.pageId = tracker.getPageId();
    this.dateTime = System.currentTimeMillis();
  }

  @Override
  public String toString() {
    return "TrackerEvent{" +
        "eventType='" + eventType + '\'' +
        ", eventName='" + eventName + '\'' +
        ", pageName='" + pageName + '\'' +
        ", pageId='" + pageId + '\'' +
        ", pageTitle='" + pageTitle + '\'' +
        ", dateTime=" + dateTime +
        ", uid='" + uid + '\'' +
        ", extraData='" + extraData + '\'' +
        ", sync=" + sync +
        '}';
  }
}
