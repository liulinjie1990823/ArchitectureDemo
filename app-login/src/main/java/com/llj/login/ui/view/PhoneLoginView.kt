package com.llj.login.ui.view

import com.llj.application.vo.UserInfoVo
import com.llj.lib.base.mvp.IBaseActivityView

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/18
 */
interface PhoneLoginView :IBaseActivityView{

    fun onSuccessUserInfo(userInfoVo: UserInfoVo?)
}