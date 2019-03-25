package com.llj.login.ui.view

import com.llj.component.service.vo.UserInfoVo
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