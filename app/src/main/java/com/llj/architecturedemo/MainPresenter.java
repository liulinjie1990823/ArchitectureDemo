package com.llj.architecturedemo;

import com.llj.architecturedemo.model.Mobile;
import com.llj.lib.base.mvp.BasePresenter;
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
        Observable<Mobile> user = TestApi.getInstance().getMobile("13188888888");
        RxApiManager.get().toSubscribe(user,bindLifecycle(),dad);
    }

    private ApiObserver<Mobile> dad =new ApiObserver<Mobile>(1){

        @Override
        public void onNext(Mobile tiResponse) {
            super.onNext(tiResponse);
        }
    };

}
