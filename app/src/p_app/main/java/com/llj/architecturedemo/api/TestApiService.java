package com.llj.architecturedemo.api;

import com.llj.architecturedemo.db.entity.MobileEntity;
import com.llj.lib.net.response.BaseResponse;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/6
 */
public interface TestApiService {

    @GET("/api/mobile.php")
    Single<Response<BaseResponse<MobileEntity>>> getMobile(@Query("mobile") String mobile);
}
