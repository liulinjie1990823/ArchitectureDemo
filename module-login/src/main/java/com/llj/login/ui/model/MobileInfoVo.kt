package com.llj.login.ui.model

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/10/18
 */
data class MobileInfoVo(val id: String? = null,
                        val phone: String? = null,
                        val province: String? = null,
                        val city: String? = null,
                        val service_provider: String? = null,
                        val city_code: String? = null,
                        val postcode: String? = null,
                        val ret: Int = 0,
                        val searchStr: String? = null,
                        val operator: String? = null,
                        val from: String? = null,
                        val ip: String? = null,
                        val ua: String? = null)
