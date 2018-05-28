package com.llj.architecturedemo;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.llj.architecturedemo.model.Mobile;
import com.llj.architecturedemo.model.User;
import com.llj.lib.base.mvp.BasePresenter;
import com.llj.lib.net.RxApiManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/17
 */
public class MainPresenter extends BasePresenter<MainContract.View, MainContract.Model> {

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
        super(model, rootView);
    }

    public void toast() {
        Observable<Mobile> user = TestApi.getInstance().getMobile("13188888888");
        RxApiManager.get().toSubscribe(user, bindLifecycle(), dad);
    }

    private ApiObserver<Mobile> dad = new ApiObserver<Mobile>(mView) {

        @Override
        public void onNext(Mobile tiResponse) {
            super.onNext(tiResponse);
        }
    };

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        super.onCreate(owner);
        getUsers(owner);
    }

    private MutableLiveData<List<User>> usersLiveData;

    public LiveData<List<User>> getUsers(LifecycleOwner owner) {
        if (usersLiveData == null) {
            usersLiveData = new MutableLiveData<>();
        }
        List<User> users = new ArrayList<>();
        users.add(new User());
        usersLiveData.setValue(users);
        usersLiveData.observe(owner, users1 -> mView.toast());
        return usersLiveData;
    }

}
