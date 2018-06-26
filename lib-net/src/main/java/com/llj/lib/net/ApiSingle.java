package com.llj.lib.net;

import com.llj.lib.net.exception.ExceptionFunction;
import com.llj.lib.net.observer.BaseApiObserver;
import com.llj.lib.net.response.BaseResponse;
import com.llj.lib.net.response.HttpResponseFunction;
import com.uber.autodispose.AutoDisposeConverter;

import io.reactivex.Single;
import retrofit2.Response;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/10
 */
public abstract class ApiSingle<Data> extends Single<Response<BaseResponse<Data>>> {


    public void subscribeApi(
            AutoDisposeConverter<BaseResponse<Data>> autoDisposeConverter,
            BaseApiObserver<Data> observer) {
        RxApiManager.get().subscribeApi(this,
                new MyHttpResponseFunction<>(),
                new MyExceptionFunction<>(),
                autoDisposeConverter,
                observer);
    }


    public static class MyHttpResponseFunction<T> extends HttpResponseFunction<T> {

    }

    public static class MyExceptionFunction<T> extends ExceptionFunction<T> {

    }
}
