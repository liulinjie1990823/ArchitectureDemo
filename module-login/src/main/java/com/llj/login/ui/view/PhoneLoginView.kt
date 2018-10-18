package com.llj.login.ui.view

import com.llj.component.service.vo.UserInfoVo
import com.llj.lib.base.mvp.IView

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/18
 */
interface PhoneLoginView :IView{

    fun onSuccessUserInfo(userInfoVo: UserInfoVo?)
}