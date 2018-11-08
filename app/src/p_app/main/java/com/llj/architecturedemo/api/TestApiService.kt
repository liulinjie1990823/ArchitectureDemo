package com.llj.architecturedemo.api

import com.llj.architecturedemo.db.entity.MobileEntity
import com.llj.architecturedemo.ui.model.BabyHomeModuleVo
import com.llj.architecturedemo.ui.model.PersonalCenterCountVo
import com.llj.architecturedemo.ui.model.PersonalCenterVo
import com.llj.architecturedemo.ui.model.TabListVo
import com.llj.lib.net.response.BaseResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.*

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/6
 */
interface TestApiService {

    @GET("/api/mobile.php")
    fun getMobile(@Query("mobile") mobile: String): Single<Response<BaseResponse<MobileEntity>>>


    @POST("/common/tabbar/get-tabbar")
    fun getTabBar(@Body map: HashMap<String, Any>): Single<Response<BaseResponse<TabListVo?>>>

    @POST("mobile/baby/get-home/")
    fun getBabyHome(@Body map: HashMap<String, Any>): Single<Response<BaseResponse<List<BabyHomeModuleVo?>?>>>

    @POST("mobile/wedding/get-home/")
    fun getWeddingHome(@Body map: HashMap<String, Any>): Single<Response<BaseResponse<List<BabyHomeModuleVo?>?>>>

    @POST("user/qrcode")
    fun getQrCode(@Body map: HashMap<String, Any>): Single<Response<BaseResponse<String?>>>

    @POST("user/account/get-mashup")
    fun getPersonalCenterInfo(@Body map: HashMap<String, Any>): Single<Response<BaseResponse<PersonalCenterVo?>>>

    @POST("user/account/get-mashup-num")
    fun getPersonalCenterCount(@Body map: HashMap<String, Any>): Single<Response<BaseResponse<PersonalCenterCountVo?>>>
}
