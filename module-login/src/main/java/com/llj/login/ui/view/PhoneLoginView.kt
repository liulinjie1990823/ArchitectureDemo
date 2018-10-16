package com.llj.login.ui.view

import com.llj.lib.base.mvp.IView
import com.llj.login.ui.model.UserInfoVo

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/18
 */
interface PhoneLoginView :IView{

    fun onSuccessUserInfo(userInfoVo: UserInfoVo?)
}