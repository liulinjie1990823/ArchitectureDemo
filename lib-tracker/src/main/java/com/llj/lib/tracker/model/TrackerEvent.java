package com.llj.lib.tracker.model;

import android.support.annotation.StringDef;

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

    public static final String PAGE_START = "PageStart";
    public static final String PAGE_END   = "PageEnd";

    @StringDef({APP_START, APP_END, APP_CLICK,PAGE_START,PAGE_END})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    //事件名称
    public @Type String eventType = APP_CLICK;

    public String uid;
    public long   dateTime;
    //页面名称
    public String pageName;
    public String pageTitle;

    public String eventName;

    public String extraData;

    public TrackerEvent() {
    }

    public TrackerEvent(String eventType, String pageName, long dateTime) {
        this.eventType = eventType;
        this.dateTime = dateTime;
        this.pageName = pageName;
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
