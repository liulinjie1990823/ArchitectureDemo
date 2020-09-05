package com.llj.application.preference

import com.llj.application.vo.CityVo
import com.llj.lib.utils.ASpUtils

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/10/17
 */
class ConfigPreference {
    companion object {
        private var mConfigPreference: ConfigPreference? = null

        private const val FILE_NAME = "pf_config"

        private const val KEY_CLIENT_ID = "client_id"
        private const val KEY_CITY_ID = "city_id" //城市id
        private const val KEY_CITY_NAME = "city_name" //城市中文名
        private const val KEY_CITY_E_NAME = "city_e_name" //城市英文名
        private const val KEY_IS_EXHIBITION_CITY = "exhibitionCity" //上一个版本号
        private const val KEY_GPS_CITY_NAME = "gps_city_name" //上一个版本号
        private const val KEY_TAB_LIST = "key_tab_list"
        private const val KEY_TAB_LIST_UPDATE_DATE = "key_tab_list_update_date"


        fun getInstance(): ConfigPreference {
            if (mConfigPreference == null) {
                mConfigPreference = ConfigPreference()
            }
            return mConfigPreference as ConfigPreference
        }
    }

    private fun getSharedPreferences(): ASpUtils {
        return ASpUtils.getInstance(FILE_NAME)
    }

    fun saveTabListUpdateDate(data: String) {
        getSharedPreferences().put(KEY_TAB_LIST_UPDATE_DATE, data, true)
    }

    fun getTabListUpdateDate(): String {
        return getSharedPreferences().getString(KEY_TAB_LIST_UPDATE_DATE, "")
    }

    fun saveTabList(data: String) {
        getSharedPreferences().put(KEY_TAB_LIST, data, true)
    }

    fun getTabList(): String {
        return getSharedPreferences().getString(KEY_CLIENT_ID, "")
    }


    fun saveClientId(clientId: String) {
        getSharedPreferences().put(KEY_CLIENT_ID, clientId, true)
    }

    fun getClientId(): String {
        return getSharedPreferences().getString(KEY_CLIENT_ID, "")
    }

    fun saveCurrentCity(city: CityVo) {
        getSharedPreferences().put(KEY_CITY_ID, city.citySiteId)
        getSharedPreferences().put(KEY_CITY_NAME, city.cityName)
        getSharedPreferences().put(KEY_CITY_E_NAME, city.cityEname)
        getSharedPreferences().put(KEY_IS_EXHIBITION_CITY, city.exhibitionCity)
    }

    fun getCityId(): String {
        return getSharedPreferences().getString(KEY_CITY_ID, "110900")
    }

    fun getCityName(): String {
        return getSharedPreferences().getString(KEY_CITY_NAME, "")
    }

    fun getCityEName(): String {
        return getSharedPreferences().getString(KEY_CITY_E_NAME, "")
    }

    fun getExhibitionCity(): Int {
        return getSharedPreferences().getInt(KEY_IS_EXHIBITION_CITY)
    }

    fun saveGpsCityName(clientId: String) {
        getSharedPreferences().put(KEY_GPS_CITY_NAME, clientId, true)
    }

    fun getGpsCityName(): String {
        return getSharedPreferences().getString(KEY_GPS_CITY_NAME, "")
    }
}
