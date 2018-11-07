package com.llj.architecturedemo.ui.model

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/10/24
 */
 class TabVo {

    //    var title: String? = null,
    //    var  default_img: String? = null,
    //    var  hover_img: String? = null,
    //    var  default_img_id: Int = 0,
    //    var  hover_img_id: Int = 0,
    //    var  type: String? = null,
    //    var  link: String? = null


    var title: String? = null
    var default_img: String? = null
    var hover_img: String? = null
    var default_img_id:  Int = 0
    var hover_img_id: Int = 0
    var type: String? = null
    var link: String? = null

    constructor(title: String?, default_img_id: Int, hover_img_id: Int, type: String?) {
        this.title = title
        this.default_img_id = default_img_id
        this.hover_img_id = hover_img_id
        this.type = type
    }
}