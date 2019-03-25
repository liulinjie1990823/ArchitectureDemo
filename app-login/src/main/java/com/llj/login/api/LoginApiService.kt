package com.llj.login.api

import com.llj.component.service.vo.UserInfoVo
import com.llj.lib.net.response.BaseResponse
import com.llj.login.ui.model.MobileInfoVo
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*
import java.util.*

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/18
 */
interface LoginApiService {

    @POST("/api/mobile.php")
    fun phoneLogin(@Body map: HashMap<String, Any>): Single<Response<BaseResponse<UserInfoVo>>>

    @POST("user/account/get-login")
    fun accountLogin(@Body map: HashMap<String, Any>): Single<Response<BaseResponse<UserInfoVo>>>

    @Headers("Domain-Name: https://www.iteblog.com")
    @GET("/api/mobile.php")
    fun getMobileInfo(@Query("mobile") mobile: String): Single<Response<BaseResponse<MobileInfoVo>>>


}