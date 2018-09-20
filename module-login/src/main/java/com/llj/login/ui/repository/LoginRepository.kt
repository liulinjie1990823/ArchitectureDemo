package com.llj.login.ui.repository

import com.llj.lib.base.mvp.BaseRepository
import com.llj.lib.net.response.BaseResponse
import com.llj.login.api.LoginApiService
import com.llj.login.ui.model.UserInfoVo
import io.reactivex.Single
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/18
 */
@Singleton
class LoginRepository @Inject constructor(private val mApiService: LoginApiService)
    : BaseRepository() {

    public fun phoneLogin(map: HashMap<String, Any>): Single<Response<BaseResponse<UserInfoVo>>> {
        return mApiService.phoneLogin(map)
    }

    public fun accountLogin(map: HashMap<String, Any>): Single<Response<BaseResponse<UserInfoVo>>> {
        return mApiService.accountLogin(map)
    }

}