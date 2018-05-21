package com.llj.architecturedemo;

import com.llj.architecturedemo.model.Mobile;
import com.llj.lib.net.BaseApi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/7
 */
public class TestApi extends BaseApi {
    private static TestApi sTestApi = new TestApi();

    private TestApiService mTestApiService;

    public interface TestApiService {

        @Headers("Content-Type: application/x-www-form-urlencoded; charset=UTF-8")
        @GET("/api/mobile.php")
        Observable<Mobile> getMobile(@Query("mobile") String mobile);
    }

    @Override
    protected void init() {
        initRetrofit(HttpUrl.BASE_URL,new HeaderInterceptor());
        mTestApiService = getRetrofit().create(TestApiService.class);
    }

    public static TestApi getInstance() {
        return sTestApi;
    }

    public Observable<Mobile> getMobile(String mobile) {
        Observable<Mobile> user = mTestApiService.getMobile(mobile);
        return wrapObservable(user);
    }

}
