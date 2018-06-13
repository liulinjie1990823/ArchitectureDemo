package com.llj.lib.net;

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
                new MyHttpResultFunction<>(),
                new MyExceptionFunction<>(),
                autoDisposeConverter,
                observer);
    }


    public static class MyHttpResultFunction<T> extends HttpResultFunction<T> {

    }

    public static class MyExceptionFunction<T> extends ExceptionFunction<T> {

    }
}
