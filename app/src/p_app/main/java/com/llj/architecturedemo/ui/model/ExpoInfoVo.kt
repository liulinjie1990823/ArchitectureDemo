package com.llj.architecturedemo.ui.model

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/11/5
 */
data class ExpoInfoVo(val latitude: String? = null) {
    /**
     * latitude : string 纬度
     * longitude: string 经度
     * isOpen : false 是否有广场
     * plazaId : 0 爱情广场id
     * range : 0 活动半径
     * h5Link 链接
     */


  val longitude: String? = null
  val isOpen: Boolean = false
  val plazaId: Long = 0
  val range: Double = 0.toDouble()
  val h5Link: String? = null
  val isPreheat: Boolean = false
  val photoLink: String? = null
}
