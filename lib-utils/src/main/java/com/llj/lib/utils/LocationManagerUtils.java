package com.llj.lib.utils;

import android.location.LocationManager;

import java.util.List;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/3/8.
 */

public class LocationManagerUtils {
    /**
     * @param locationManager
     * @return
     */
    public static String getProvider(LocationManager locationManager) {
        String provider = "";
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            //优先使用gps
            provider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        }
        return provider;
    }
}
