package com.llj.architecturedemo.ui.model

import com.llj.component.service.vo.TrackerDo
import com.llj.component.service.vo.UserInfoVo

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/11/5
 */
data class PersonalCenterVo(val server_tel: String? = null) {
    val user: UserInfoVo? = null //个人信息
    val activity: ActivityVo? = null //签到
    val ads: List<AdVo>? = null //广告位
    val cart_num: Int = 0 //购物车数量
    val comment_num: Int = 0 //评价数量
    val header_lists: List<HeaderMenuVo>? = null //头部菜单
    val new_menus: List<ToolsVo>? = null //小工具

    //签到活动
    data class ActivityVo(val is_start: Boolean = false) : TrackerDo() {
        val is_sign: Boolean = false
        val is_expo_day: Boolean = false
        val corner: String? = null
        val href: String? = null
    }

    data class ToolsVo(val menu_type: String? = null) {
        val title: String? = null //标题
        val lists: List<ToolsMenuVo>? = null
    }

    //小工具按钮
    data class ToolsMenuVo(val title: String? = null) : TrackerDo() {
        val sub_title: String? = null //标题
        val status_name: String? = null //状态
        val link: String? = null //链接
        val wap_link: String? = null //链接
        val image: String? = null //图片
        val need_login: String? = null //是否需要登录
    }

    //header按钮
    data class HeaderMenuVo(val title: String? = null) : TrackerDo() {
        val num: Int = 0 //数量
        val link: String? = null //链接
        val need_login: String? = null //是否需要登录
    }

    //广告位
    data class AdVo(val title: String? = null) : TrackerDo() {
        val link: String? = null
        val wap_link: String? = null
        val image: String? = null
        val position: String? = null //top,middle,bottom

        companion object {
            const val POSITION_TOP = "top"
            const val POSITION_BOTTOM = "bottom"
            const val POSITION_MIDDLE = "middle"
        }
    }
}
