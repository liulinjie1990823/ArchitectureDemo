package com.llj.login.api

import com.llj.lib.net.response.BaseResponse
import com.llj.login.ui.model.UserInfoVo
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import java.util.*

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/18
 */
interface LoginApiService {

    @GET("/api/mobile.php")
    fun phoneLogin(@Body map: HashMap<String, Any>): Single<Response<BaseResponse<UserInfoVo>>>

    @GET("/api/mobile.php")
    fun accountLogin(@Body map: HashMap<String, Any>): Single<Response<BaseResponse<UserInfoVo>>>

}