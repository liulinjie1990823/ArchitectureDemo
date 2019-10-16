package com.llj.lib.tracker.model;

import android.support.annotation.StringDef;

import com.llj.lib.tracker.ITracker;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ArchitectureDemo.
 * describe:
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

    public static final String APP_CLICK   = "Click";
    public static final String TYPE_ACTION = "Action";

    @StringDef({APP_APPEAR, APP_DISAPPEAR, PAGE_APPEAR, PAGE_DISAPPEAR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PageType {
    }

    @StringDef({APP_CLICK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ActionType {
    }

    //事件类型
    public String eventType = TYPE_PAGE;
    //事件名称
    public String eventName = APP_CLICK;
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

    public TrackerEvent(String eventType, String eventName, String pageName, long dateTime) {
        this.eventType = eventType;
        this.eventName = eventName;
        this.dateTime = dateTime;
        this.pageName = pageName;
    }

    public TrackerEvent(String eventType, String eventName, ITracker iTracker, long dateTime) {
        this.eventType = eventType;
        this.eventName = eventName;
        this.dateTime = dateTime;
        this.pageName = iTracker.getPageName();
        this.pageId = iTracker.getPageId();
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
