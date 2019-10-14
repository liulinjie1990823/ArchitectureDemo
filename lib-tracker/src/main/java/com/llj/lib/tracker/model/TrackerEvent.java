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

    public static final String APP_START = "AppStart";
    public static final String APP_END   = "AppEnd";
    public static final String APP_CLICK = "AppClick";

    public static final String PAGE_APPEAR    = "PageAppear";
    public static final String PAGE_DISAPPEAR = "PageDisappear";

    @StringDef({APP_START, APP_END, PAGE_APPEAR, PAGE_DISAPPEAR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PageType {
    }

    @StringDef({APP_CLICK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ActionType {
    }

    //事件名称
    public String eventType = APP_CLICK;

    public String uid;
    public long   dateTime;
    //页面名称
    public String pageName;
    public String pageId;
    public String pageTitle;

    public String eventName;

    public String extraData;

    public TrackerEvent() {
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public TrackerEvent(String eventType, String pageName, long dateTime) {
        this.eventType = eventType;
        this.dateTime = dateTime;
        this.pageName = pageName;
    }

    public TrackerEvent(String eventType, ITracker iTracker, long dateTime) {
        this.eventType = eventType;
        this.dateTime = dateTime;
        this.pageName = iTracker.getPageName();
        this.pageId = iTracker.getPageId();
    }

    @Override
    public String toString() {
        return "TrackerEvent{" +
                "uid='" + uid + '\'' +
                ", dateTime=" + dateTime +
                ", pageName='" + pageName + '\'' +
                ", pageTitle='" + pageTitle + '\'' +
                ", eventType='" + eventType + '\'' +
                ", eventName='" + eventName + '\'' +
                ", extraData='" + extraData + '\'' +
                '}';
    }
}
