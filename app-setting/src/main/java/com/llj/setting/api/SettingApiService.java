package com.llj.setting.api;

import com.llj.application.vo.UserInfoVo;
import com.llj.lib.net.response.BaseResponse;
import com.llj.setting.ui.model.MobileInfoVo;

import java.util.HashMap;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * ArchitectureDemo.
 * describe:
 * @author llj
 * date 2019/3/25
 */
public interface SettingApiService {

    @POST("/api/mobile.php")
    Single<Response<BaseResponse<UserInfoVo>>> phoneLogin(@Body HashMap<String, Object> map);

    @POST("user/account/get-login")
    Single<Response<BaseResponse<UserInfoVo>>> accountLogin(@Body  HashMap<String, Object> map);

    @Headers("Domain-Name: https://www.iteblog.com")
    @GET("/api/mobile.php")
    Single<Response<BaseResponse<MobileInfoVo>>> getMobileInfo(@Query("mobile") String mobile);
}
