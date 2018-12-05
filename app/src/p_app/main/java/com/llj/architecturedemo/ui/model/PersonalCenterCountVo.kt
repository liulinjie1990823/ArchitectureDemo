package com.llj.architecturedemo.ui.model

import com.llj.component.service.vo.TrackerDo

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/11/5
 */
data class PersonalCenterCountVo(val cart_num: Int = 0) {
    val comment_num: Int = 0 //评价数量
    val notice_num: Int = 0 //用户未读消息总数
    val header_lists: List<HeaderMenu>? = null //按钮

    data class HeaderMenu(val title: String? = null) : TrackerDo() {
        val num: Int = 0 //数量
        val showNum: String? = null //数量
        val link: String? = null //点击链接
        val need_login: String? = null //是否需要登录
        val type: String? = null //类型
    }
}
