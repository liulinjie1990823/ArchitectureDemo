package com.llj.lib.base.net;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import com.llj.lib.base.mvp.IBaseActivityView;
import com.llj.lib.net.RxApiManager;
import com.llj.lib.net.observer.BaseApiObserver;
import com.llj.lib.net.response.BaseResponse;
import com.llj.lib.net.response.IResponse;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;


/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/5
 */
public abstract class NetworkBoundResource<Data> {

    private final MediatorLiveData<IResponse<Data>> mLiveData = new MediatorLiveData<>();


    @MainThread
    public NetworkBoundResource() {
        LiveData<Data> dbSource = loadFromDb();

        mLiveData.addSource(dbSource, resultType -> {
            mLiveData.removeSource(dbSource);

            if (shouldFetch(resultType)) {
                fetchFromNetwork(dbSource);
            } else {
                mLiveData.addSource(dbSource, resultType1 -> mLiveData.setValue(BaseResponse.success(resultType1)));
            }
        });

    }

    private void fetchFromNetwork(final LiveData<Data> dbSource) {
        IBaseActivityView view = view();

        //Single
        Single<Response<BaseResponse<Data>>> apiCall = createCall()
                .doOnSubscribe(view)
                .doFinally(view);

        //Observer
        BaseApiObserver<Data> baseApiObserver = new BaseApiObserver<Data>(view.getLoadingDialog()) {

            @Override
            public void onSubscribe(@NotNull Disposable d) {
                super.onSubscribe(d);
                view.addDisposable(getRequestId(), getDisposable());
            }

            @Override
            public void onSuccess(@NonNull BaseResponse<Data> response) {
                super.onSuccess(response);

                Data data = processResponse(response);
                if (data != null) {
                    saveCallResult(data);
                }

                AndroidSchedulers.mainThread().scheduleDirect(() ->
                        mLiveData.addSource(loadFromDb(), newData ->
                                mLiveData.setValue(BaseResponse.success(newData))));
            }

            @Override
            public void onError(@NonNull Throwable t) {
                super.onError(t);
            }
        };

        //subscribe
        RxApiManager.get().subscribeApi(
                apiCall,
                view().bindRequestLifecycle(),
                baseApiObserver);
    }


    @NonNull
    @MainThread
    protected abstract LiveData<Data> loadFromDb();


    @MainThread
    protected abstract boolean shouldFetch(@Nullable Data data);


    @NonNull
    @MainThread
    protected abstract IBaseActivityView view();

    @NonNull
    @MainThread
    protected abstract Object tag();

    @NonNull
    @MainThread
    protected abstract Single<Response<BaseResponse<Data>>> createCall();


    @WorkerThread
    protected abstract void saveCallResult(@NonNull Data item);

    @WorkerThread
    protected Data processResponse(IResponse<Data> response) {
        return response.getData();
    }

    protected void onFetchFailed() {
    }


    public LiveData<IResponse<Data>> asLiveData() {
        return mLiveData;
    }
}
