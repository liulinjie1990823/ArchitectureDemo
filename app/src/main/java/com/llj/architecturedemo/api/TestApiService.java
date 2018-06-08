package com.llj.architecturedemo.api;

import com.llj.architecturedemo.db.model.MobileEntity;

import io.reactivex.Observable;
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
    Observable<MobileEntity> getMobile(@Query("mobile") String mobile);
}
