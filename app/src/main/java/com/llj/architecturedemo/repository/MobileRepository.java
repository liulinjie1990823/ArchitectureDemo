package com.llj.architecturedemo.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.llj.architecturedemo.api.TestApiService;
import com.llj.architecturedemo.db.dao.MobileDao;
import com.llj.architecturedemo.db.model.MobileEntity;
import com.llj.lib.net.BaseApiObserver;
import com.llj.lib.net.IRequestDialog;
import com.llj.lib.net.Response;
import com.llj.lib.net.RxApiManager;
import com.uber.autodispose.AutoDisposeConverter;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/6
 */
@Singleton
public class MobileRepository {
    private final MobileDao      mMobileDao;
    private       TestApiService mApiService;


    private MediatorLiveData<MobileEntity> mMobileMutableLiveData;

    @Inject
    MobileRepository(MobileDao mobileDao, TestApiService apiService) {
        this.mMobileDao = mobileDao;
        this.mApiService = apiService;
    }


    public LiveData<MobileEntity> getMobile(AutoDisposeConverter<MobileEntity> autoDisposeConverter, IRequestDialog iRequestDialog) {
        if (mMobileMutableLiveData == null) {
            mMobileMutableLiveData = new MediatorLiveData<>();
        }
        Observable<MobileEntity> mobile = mApiService.getMobile("13188888888");

        RxApiManager.get().toSubscribe(mobile, autoDisposeConverter, new BaseApiObserver<MobileEntity>(iRequestDialog) {

            @Override
            public void onNext(Response<MobileEntity> response) {
                super.onNext(response);
                mMobileMutableLiveData.setValue(response.getData());
            }
        });
        return mMobileMutableLiveData;
    }


    public LiveData<MobileEntity> getTest() {
        if (mMobileMutableLiveData == null) {
            mMobileMutableLiveData = new MediatorLiveData<>();
        }
        MutableLiveData<MobileEntity> liveData = new MutableLiveData<>();
        liveData.setValue(new MobileEntity(1));

        MutableLiveData<MobileEntity> liveData2 = new MutableLiveData<>();
        liveData2.setValue(new MobileEntity(2));

        mMobileMutableLiveData.addSource(liveData, new Observer<MobileEntity>() {
            @Override
            public void onChanged(@Nullable MobileEntity mobileEntity) {
                mMobileMutableLiveData.setValue(mobileEntity);
            }
        });

        mMobileMutableLiveData.addSource(liveData2, new Observer<MobileEntity>() {
            @Override
            public void onChanged(@Nullable MobileEntity mobileEntity) {
                mMobileMutableLiveData.setValue(mobileEntity);
            }
        });

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
