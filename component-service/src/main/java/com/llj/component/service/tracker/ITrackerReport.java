package com.llj.component.service.tracker;

import android.view.View;

import com.llj.lib.tracker.model.TrackerExtraData;
import com.llj.lib.utils.AGsonUtils;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/14
 */
public interface ITrackerReport {


    default void setTrackerData(View v, String eventName, String url) {
        v.setTag(com.llj.lib.tracker.R.id.tracker_event_name, eventName);
        TrackerExtraData data = new TrackerExtraData(url);
        String json = AGsonUtils.getGson().toJson(data);
        v.setTag(com.llj.lib.tracker.R.id.tracker_extra_data, json);
    }
}
