package com.llj.architecturedemo.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.llj.architecturedemo.api.TestApiService;
import com.llj.architecturedemo.db.dao.MobileDao;
import com.llj.architecturedemo.db.entity.MobileEntity;
import com.llj.lib.base.mvp.BaseRepository;
import com.llj.lib.base.mvp.IView;
import com.llj.lib.base.net.NetworkBoundResource;
import com.llj.lib.net.response.BaseResponse;
import com.llj.lib.net.response.IResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import retrofit2.Response;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/6
 */
@Singleton
public class MobileRepository extends BaseRepository {
    private final MobileDao      mMobileDao;
    private       TestApiService mApiService;

    @Inject
    MobileRepository(MobileDao mobileDao, TestApiService apiService) {
        this.mMobileDao = mobileDao;
        this.mApiService = apiService;
    }


    public LiveData<IResponse<MobileEntity>> getMobile(String phone, IView view) {

        return new NetworkBoundResource<MobileEntity>() {

            @NonNull
            @Override
            protected LiveData<MobileEntity> loadFromDb() {
                return mMobileDao.selectMobileByPhone("1318888");
            }

            @NonNull
            @Override
            protected int tag() {
                return view.hashCode();
            }

            @Override
            protected boolean shouldFetch(@Nullable MobileEntity mobileEntity) {
                return mobileEntity == null;
            }

            @NonNull
            @Override
            protected IView view() {
                return view;
            }

            @NonNull
            @Override
            protected Single<Response<BaseResponse<MobileEntity>>> createCall() {
                return mApiService.getMobile(phone);
            }

            @Override
            protected void saveCallResult(@NonNull MobileEntity item) {
                mMobileDao.insert(item);
            }

            @Override
            protected void onFetchFailed() {
                super.onFetchFailed();
            }

        }.asLiveData();

//        MutableLiveData<MobileEntity> mutableLiveData = new MutableLiveData<>();
//
//        Single<Response<BaseResponse<MobileEntity>>> mobile = mApiService.getMobile("13188888888")
//                .doOnSubscribe(view)
//                .doFinally(view);
//
//        RxApiManager.get().subscribeApi(
//                mobile,
//                view.bindRequestLifecycle(),
//                new ApiObserver<MobileEntity>(view) {
//                    @Override
//                    public void onSuccess(@NonNull BaseResponse<MobileEntity> response) {
//                        super.onSuccess(response);
//                        mutableLiveData.setValue(response.getData());
//                    }
//                });
//        return mutableLiveData;
    }


    public LiveData<MobileEntity> getTest() {
        MutableLiveData<MobileEntity> mMobileMutableLiveData = new MediatorLiveData<>();
        MutableLiveData<MobileEntity> liveData = new MutableLiveData<>();
        liveData.setValue(new MobileEntity(1));

        MutableLiveData<MobileEntity> liveData2 = new MutableLiveData<>();
        liveData2.setValue(new MobileEntity(2));

//        mMobileMutableLiveData.addSource(liveData, new Observer<MobileEntity>() {
//            @Override
//            public void onChanged(@Nullable MobileEntity mobileEntity) {
//                mMobileMutableLiveData.setValue(mobileEntity);
//            }
//        });
//
//        mMobileMutableLiveData.addSource(liveData2, new Observer<MobileEntity>() {
//            @Override
//            public void onChanged(@Nullable MobileEntity mobileEntity) {
//                mMobileMutableLiveData.setValue(mobileEntity);
//            }
//        });

        return mMobileMutableLiveData;
    }

    private final MediatorLiveData<String> result = new MediatorLiveData<>();

    private MutableLiveData<String> testLive = new MutableLiveData<>();

    public void setQuery(@NonNull String originalInput) {
        result.removeSource(testLive);
        testLive.setValue(originalInput);
        result.addSource(testLive, str -> {
            result.removeSource(testLive);//先移除
            if (str == null) {
            } else {
                result.addSource(testLive, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        result.setValue("成功咯" + s);
                    }
                });//双层嵌套，前提是前面有removeSource
            }
        });
        testLive.setValue("test1");//注意这里和remove就是使用双层嵌套的原因
    }

    public LiveData<String> getResult() {
        return result;
    }


}
