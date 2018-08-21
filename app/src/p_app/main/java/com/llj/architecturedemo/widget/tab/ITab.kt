package com.llj.architecturedemo.widget.tab

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/16
 */
interface ITab {
    var name: String?
    var type: String?

    companion object {
        const val SHOW_TYPE_ALBUM = "album"
        const val SHOW_TYPE_PRODUCT = "product"
        const val SHOW_TYPE_STORE = "store"
    }
}
