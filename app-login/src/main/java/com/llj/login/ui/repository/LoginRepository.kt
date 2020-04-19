package com.llj.login.ui.repository

import com.llj.component.service.vo.UserInfoVo
import com.llj.lib.base.mvp.BaseRepository
import com.llj.lib.net.response.BaseResponse
import com.llj.login.LoginScope
import com.llj.login.api.LoginApiService
import com.llj.login.ui.model.MobileInfoVo
import io.reactivex.Single
import retrofit2.Response
import java.util.*
import javax.inject.Inject

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/18
 */
@LoginScope
class LoginRepository : BaseRepository {

  val mApiService: LoginApiService

  @Inject
  constructor(mApiService: LoginApiService) : super() {
    this.mApiService = mApiService
  }

  fun phoneLogin(map: HashMap<String, Any>): Single<Response<BaseResponse<UserInfoVo>>> {
    return mApiService.phoneLogin(map)
  }

  fun accountLogin(map: HashMap<String, Any>): Single<Response<BaseResponse<UserInfoVo>>> {
    return mApiService.accountLogin(map)
  }

  fun getMobileInfo(mobile: String): Single<Response<BaseResponse<MobileInfoVo>>> {
    return mApiService.getMobileInfo(mobile)
  }
}