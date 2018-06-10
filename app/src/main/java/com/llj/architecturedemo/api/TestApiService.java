package com.llj.architecturedemo.api;

import com.llj.architecturedemo.db.model.MobileEntity;
import com.llj.lib.net.IResponse;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/6
 */
public interface TestApiService {

    @Headers("Content-Type: application/x-www-form-urlencoded; charset=UTF-8")
    @GET("/api/mobile.php")
    Single<Response<IResponse<MobileEntity>>> getMobile(@Query("mobile") String mobile);
}
