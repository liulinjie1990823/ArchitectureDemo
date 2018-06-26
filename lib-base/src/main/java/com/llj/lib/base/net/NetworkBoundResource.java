package com.llj.lib.base.net;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.llj.lib.base.mvp.IView;
import com.llj.lib.net.RxApiManager;
import com.llj.lib.net.observer.BaseApiObserver;
import com.llj.lib.net.response.BaseResponse;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import retrofit2.Response;


/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/5
 */
public abstract class NetworkBoundResource<Data> {

    private final MediatorLiveData<BaseResponse<Data>> mLiveData = new MediatorLiveData<>();


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
        Single<Response<BaseResponse<Data>>> apiCall = createCall();

        RxApiManager.get().subscribeApi(
                apiCall,
                view().bindRequestLifecycle(),
                new BaseApiObserver<Data>(view()) {
                    @Override
                    public void onSuccess(@NonNull BaseResponse<Data> response) {
                        super.onSuccess(response);

                        saveCallResult(processResponse(response));

                        AndroidSchedulers.mainThread().scheduleDirect(() ->
                                mLiveData.addSource(loadFromDb(), newData ->
                                        mLiveData.setValue(BaseResponse.success(newData))));
                    }
                });

    }


    @NonNull
    @MainThread
    protected abstract LiveData<Data> loadFromDb();


    @MainThread
    protected abstract boolean shouldFetch(@Nullable Data data);


    @NonNull
    @MainThread
    protected abstract IView view();

    @NonNull
    @MainThread
    protected abstract Single<Response<BaseResponse<Data>>> createCall();


    @WorkerThread
    protected abstract void saveCallResult(@NonNull Data item);

    @WorkerThread
    protected Data processResponse(BaseResponse<Data> response) {
        return response.getData();
    }

    protected void onFetchFailed() {
    }


    public LiveData<BaseResponse<Data>> asLiveData() {
        return mLiveData;
    }
}
