package com.llj.component.service.http

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.webkit.WebSettings
import com.llj.component.service.R
import com.llj.component.service.preference.ConfigPreference
import com.llj.component.service.preference.UserInfoPreference
import com.llj.lib.base.help.DisplayHelper
import com.llj.lib.utils.APackageMangerUtils
import com.llj.lib.utils.APhoneUtils
import com.llj.lib.utils.helper.Utils
import com.meituan.android.walle.WalleChannelReader
import okhttp3.Interceptor
import okhttp3.Response
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.*

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/10/16
 */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val requestBuilder = chain.request().newBuilder()

        val header = getHeader(Utils.getApp())
        if (!header.isEmpty()) {
            for (entry in header.entries) {
                requestBuilder.header(entry.key, entry.value.toString())
            }
        }

        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }


    private fun getHeader(context: Context): HashMap<String, Any> {
        val map = HashMap<String, Any>()

        val accessToken = UserInfoPreference.getInstance().getUserInfo().access_token
        if (accessToken != null) {
            map["Authorization"] = "dmp $accessToken"
        }
        map["User-Agent"] = getUserAgent(context) + " " + Utils.getApp().resources.getString(R.string.service_user_agent)

        map["app-version"] = APackageMangerUtils.getAppVersionName(Utils.getApp())
        map["app-key"] = ComponentConstants.APP_KEY
        map["app-id"] = ComponentConstants.APP_ID
        map["app-channel"] = getChannel(context)
        map["client-id"] = ConfigPreference.getInstance().getClientId()
        map["device-id"] = getDeviceId(context)
        map["city-id"] = ConfigPreference.getInstance().getCityId()
        //        map["app-visit-ciw"] = PageReferHelper.getPagePath()
        //        map["page-id"] = Tracker.getInstance().getCurrentPageId()
        map["view-id"] = ComponentConstants.VIEW_ID

        try {
            map["visit-city-name"] = URLEncoder.encode(ConfigPreference.getInstance().getGpsCityName(), "utf-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            map["visit-city-name"] = ""
        }

        try {
            map["site-city-name"] = URLEncoder.encode(ConfigPreference.getInstance().getCityName(), "utf-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            map["site-city-name"] = ""
        }


        map["os-name"] = "Android"
        map["os-version"] = Build.VERSION.RELEASE
        map["device-name"] = Build.MODEL
        map["manufacturer"] = Build.BRAND
        map["resolution"] = DisplayHelper.SCREEN_WIDTH.toString() + "X" + DisplayHelper.SCREEN_HEIGHT.toString()
        return map
    }

    //获取渠道号
    private fun getChannel(context: Context): String {
        return WalleChannelReader.getChannel(context) ?: "hunbohui"
    }

    //获取ua
    private fun getUserAgent(context: Context): String {
        var userAgent = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(context)
            } catch (e: Exception) {
                userAgent = System.getProperty("http.agent")
            }

        } else {
            userAgent = System.getProperty("http.agent")
        }
        val sb = StringBuffer()
        var i = 0
        val length = userAgent.length
        while (i < length) {
            val c = userAgent[i]
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", c.toInt()))
            } else {
                sb.append(c)
            }
            i++
        }
        return sb.toString()
    }

    //获取DeviceId
    private fun getDeviceId(context: Context): String {
        var deviceId = APhoneUtils.getDeviceId(context) //3.文件中若没有deviceId，则需要读取设备信息
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = "UUID-" + UUID.randomUUID().toString() //4.设备信息读取不到，则需要随机生成
        }
        return deviceId
    }
}