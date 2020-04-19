package com.llj.architecturedemo.repository

import com.llj.architecturedemo.AppScope
import com.llj.architecturedemo.api.TestApiService
import com.llj.architecturedemo.ui.model.BabyHomeModuleVo
import com.llj.architecturedemo.ui.model.PersonalCenterCountVo
import com.llj.architecturedemo.ui.model.PersonalCenterVo
import com.llj.architecturedemo.ui.model.TabListVo
import com.llj.lib.base.mvp.BaseRepository
import com.llj.lib.net.response.BaseResponse
import io.reactivex.Single
import retrofit2.Response
import java.util.*
import javax.inject.Inject

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/10/24
 */
@AppScope
class HomeRepository @Inject constructor(private val mApiService: TestApiService)
  : BaseRepository() {

  //获取首页tab
  fun getTabBar(map: HashMap<String, Any>): Single<Response<BaseResponse<TabListVo?>>> {
    return mApiService.getTabBar(map)
  }

  //获取婴芭莎首页数据
  fun getBabyHome(map: HashMap<String, Any>): Single<Response<BaseResponse<List<BabyHomeModuleVo?>?>>> {
    return mApiService.getBabyHome(map)
  }

  //获取婚芭莎首页数据
  fun getWeddingHome(map: HashMap<String, Any>): Single<Response<BaseResponse<List<BabyHomeModuleVo?>?>>> {
    return mApiService.getWeddingHome(map)
  }


  fun getQrCode(map: HashMap<String, Any>): Single<Response<BaseResponse<String?>>> {
    return mApiService.getQrCode(map)
  }

  //获取个人中心数据
  fun getPersonalCenterInfo(map: HashMap<String, Any>): Single<Response<BaseResponse<PersonalCenterVo?>>> {
    return mApiService.getPersonalCenterInfo(map)
  }

  //获取个人中心数量
  fun getPersonalCenterCount(map: HashMap<String, Any>): Single<Response<BaseResponse<PersonalCenterCountVo?>>> {
    return mApiService.getPersonalCenterCount(map)
  }


}