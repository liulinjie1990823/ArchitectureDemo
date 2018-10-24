package com.llj.architecturedemo.api

import com.llj.architecturedemo.db.entity.MobileEntity
import com.llj.architecturedemo.ui.model.BabyHomeModuleVo
import com.llj.architecturedemo.ui.model.TabListVo
import com.llj.lib.net.response.BaseResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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
    fun getTabBar(): Single<Response<BaseResponse<TabListVo>>>


    @POST("mobile/get-home/")
    fun getBabyHome(): Single<Response<BaseResponse<List<BabyHomeModuleVo?>?>>>
}
