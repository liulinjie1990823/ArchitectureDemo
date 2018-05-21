package com.llj.architecturedemo;

import com.llj.architecturedemo.model.User;
import com.llj.lib.base.mvp.BasePresenter;
import com.llj.lib.net.IResponse;
import com.llj.lib.net.RxApiManager;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/17
 */
public class MainPresenter extends BasePresenter<MainContract.View,MainContract.Model> {

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
        super(model, rootView);
    }

    public void toast(){
        Observable user = TestApi.getInstance().getUser();
        RxApiManager.get().toSubscribe(user,bindLifecycle(),dad);
    }

    private ApiObserver<IResponse<User>> dad=new ApiObserver<IResponse<User>>(1){

        @Override
        public void onNext(IResponse<User> tiResponse) {
            super.onNext(tiResponse);
        }
    };

}
