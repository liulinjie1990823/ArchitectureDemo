package com.llj.component.service.vo


/**
 * @author llj
 * @date 2018/1/16
 */

class UserInfoVo {

    /**
     * 用户id
     */
    var uid: Long = 0
    /**
     * 用户id
     */
    var uname: String? = null
    /**
     * 真实姓名
     */
    var real_name: String? = null
    /**
     * 绑定状态
     */
    var bind_status: String? = null
    /**
     * 最后登录ip
     */
    var last_login_ip: String? = null
    /**
     * 最后登录时间
     */
    var last_login_time: String? = null

    var status: String? = null
    /**
     * 邮箱
     */
    var email: String? = null
    /**
     * 手机
     */
    var phone: String? = null
    /**
     * 城市id
     */
    var city_id: String? = null
        get() = "330100"
    /**
     * 用户禁言、发帖状态
     */
    var setting_status: String? = null
    /**
     * 绑定实付完成
     */
    var perfect_bind: String? = null
    var creative_score: String? = null
    /**
     * 注册ip
     */
    var regip: String? = null
    /**
     * 来源
     */
    var sougrce: String? = null
    /**
     * 注册时间
     */
    var create_time: String? = null
    var mark_statusl: String? = null
    var honor_value: String? = null
    var qrcode_img: String? = null
    var gender: String? = null
    /**
     * 居住城市
     */
    var live_city: String? = null
    var qq: String? = null
    var wechat: String? = null
    var user_level: String? = null
    /**
     * 用户等级英文
     */
    var ulevel: String? = null
    /**
     * 用户等级中文
     */
    var ulevel_name: String? = null
    /**
     * 普通积分
     */
    var putong_score: String? = null
    /**
     * 婚钻积分
     */
    var hunzuan_score: String? = null
    /**
     * 用户头像
     */
    var avatar: String? = null
    /**
     * 登录ip
     */
    var client_ip: String? = null
    /**
     * 访问凭证
     */
    var access_token: String? = null
    var isIs_set_pwd: Boolean = false //是否设置了密码
    /**
     * 访问凭证过期时间
     */

    /**
     * 用户地址信息
     */
    var address: UserAddressInfo? = null
    /**
     * 支付信息
     */
    var alipay: UserAlipayInfo? = null


    var love_id: String? = null
    var hbh_id: String? = null
    var referer: String? = null
    var reason: String? = null
    var auto_login_key: String? = null

    var token_expire_time: Long = 0

    var token_expice_time: String? = null


    inner class UserAddressInfo {
        var uid: String? = null
        var consignee: String? = null
        var district: String? = null
        var address: String? = null
        var zipcode: String? = null
        var tel: String? = null
        var mobile: String? = null
        var status: String? = null
        var city: String? = null
        var province: String? = null
        var address_id: String? = null
        var district_id: String? = null
    }

    inner class UserAlipayInfo {
        var bindAlipayAccount: String? = null
        var createdAt: String? = null
        var createdBy: String? = null
        var delFlag: String? = null
        var id: String? = null
        var idStr: String? = null
        var legalizeIdcard: String? = null
        var legalizeName: String? = null
        var legalizeSign: String? = null
        var uid: String? = null
        var updatedAt: String? = null
        var updatedBy: String? = null
        var userLegalizeId: String? = null
    }
}
