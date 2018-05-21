package com.llj.architecturedemo;

import com.llj.architecturedemo.model.User;
import com.llj.lib.net.BaseApi;
import com.llj.lib.net.IResponse;
import com.llj.lib.net.RetrofitService;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/7
 */
public class TestApi extends BaseApi {
    private static TestApi sTestApi = new TestApi();

    private TestApiService mTestApiService;

    public interface TestApiService extends RetrofitService {

        @GET("/user")
        Observable<IResponse<User>> getUser();
    }

    @Override
    protected void init() {
        initRetrofit(HttpUrl.BASE_URL);
        mTestApiService = sTestApi.getRetrofit().create(TestApiService.class);
    }

    public static TestApi getInstance() {
        return sTestApi;
    }

    public  Observable getUser() {
        Observable<IResponse<User>> user = mTestApiService.getUser();
        return wrapObservable(user);

    }

}
