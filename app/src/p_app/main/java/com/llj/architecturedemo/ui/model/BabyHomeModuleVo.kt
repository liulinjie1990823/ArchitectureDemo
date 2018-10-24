package com.llj.architecturedemo.ui.model

/**
 * BabyBazaar.
 * describe:
 * author llj
 * date 2018/10/22
 */
data class BabyHomeModuleVo(val page_name: String? = null) {

    /**
     * page_name : 移动端-首页
     * page_ename : mobile_index
     * block_name : 轮播图
     * block_ename : banner
     * block_tmpl : banner
     * more_title :
     * more_link :
     */

    val page_ename: String? = null
    val block_name: String? = null
    val block_desc: String? = null
    val block_ename: String? = null
    val block_tmpl: String? = null
    val more_title: String? = null
    val more_link: String? = null
    val data: List<BabyHomeModuleItemVo?>? = null

}
