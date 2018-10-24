package com.llj.architecturedemo.repository

import com.llj.architecturedemo.api.TestApiService
import com.llj.architecturedemo.ui.model.BabyHomeModuleVo
import com.llj.architecturedemo.ui.model.TabListVo
import com.llj.lib.base.mvp.BaseRepository
import com.llj.lib.net.response.BaseResponse
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/10/24
 */
@Singleton
class HomeRepository @Inject constructor(private val mApiService: TestApiService)
    : BaseRepository() {


    fun getTabBar(): Single<Response<BaseResponse<TabListVo>>> {
        return mApiService.getTabBar()
    }

    fun getBabyHome(): Single<Response<BaseResponse<List<BabyHomeModuleVo?>?>>> {
        return mApiService.getBabyHome()
    }
}