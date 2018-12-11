package com.llj.component.service.preference

import android.text.TextUtils
import com.llj.component.service.vo.UserInfoVo
import com.llj.lib.utils.AGsonUtils
import com.llj.lib.utils.ASpUtils

/**
 * ArchitectureDemo.
 * describe: 用户信息
 * author llj
 * date 2018/10/16
 */
class UserInfoPreference {

    companion object {
        private var mUserInfoPreference: UserInfoPreference? = null

        public const val FILE_NAME = "pf_userInfo"

        private const val KEY = "userInfo"

        fun getInstance(): UserInfoPreference {
            if (mUserInfoPreference == null) {
                mUserInfoPreference = UserInfoPreference()
            }
            return mUserInfoPreference as UserInfoPreference
        }
    }

    private fun getSharedPreferences(): ASpUtils {
        return ASpUtils.getInstance(FILE_NAME)
    }

    fun saveUserInfo(userInfo: UserInfoVo?) {
        if (userInfo == null) {
            return
        }
        val sharedPreferences = getSharedPreferences()
        sharedPreferences.put(KEY, AGsonUtils.toJson(userInfo), false)

    }

    fun getUserInfo(): UserInfoVo {
        val sharedPreferences = getSharedPreferences()
        val userInfoVo = sharedPreferences.getString(KEY, null)
        if (TextUtils.isEmpty(userInfoVo)) {
            return UserInfoVo()
        }
        return AGsonUtils.getObject(userInfoVo, UserInfoVo::class.java)
    }
}