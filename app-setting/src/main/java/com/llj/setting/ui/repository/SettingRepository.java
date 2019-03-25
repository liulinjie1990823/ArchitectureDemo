package com.llj.setting.ui.repository;

import com.llj.component.service.vo.UserInfoVo;
import com.llj.lib.base.mvp.BaseRepository;
import com.llj.lib.net.response.BaseResponse;
import com.llj.setting.api.SettingApiService;
import com.llj.setting.ui.model.MobileInfoVo;

import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import retrofit2.Response;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/25
 */
@Singleton
public class SettingRepository extends BaseRepository {

    private SettingApiService mApiService;

    @Inject
    public SettingRepository(SettingApiService apiService) {
        mApiService = apiService;
    }

    public Single<Response<BaseResponse<UserInfoVo>>> phoneLogin(HashMap<String, Object> map) {
        return mApiService.phoneLogin(map);
    }

    public Single<Response<BaseResponse<UserInfoVo>>> accountLogin(HashMap<String, Object> map) {
        return mApiService.accountLogin(map);
    }

    public Single<Response<BaseResponse<MobileInfoVo>>> getMobileInfo(String mobile) {
        return mApiService.getMobileInfo(mobile);
    }
}
