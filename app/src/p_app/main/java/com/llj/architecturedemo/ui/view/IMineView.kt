package com.llj.architecturedemo.ui.view

import com.llj.architecturedemo.ui.model.ExpoInfoVo
import com.llj.architecturedemo.ui.model.PersonalCenterCountVo
import com.llj.architecturedemo.ui.model.PersonalCenterVo
import com.llj.lib.base.mvp.IBaseActivityComposeView4
import com.llj.lib.net.response.BaseResponse

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
interface IMineView : IBaseActivityComposeView4<BaseResponse<PersonalCenterVo?>,
        BaseResponse<PersonalCenterCountVo?>,
        BaseResponse<String?>,
        BaseResponse<ExpoInfoVo?>> {


}