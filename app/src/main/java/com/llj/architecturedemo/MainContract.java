package com.llj.architecturedemo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.llj.architecturedemo.model.Mobile;
import com.llj.lib.base.mvp.BaseViewModel;
import com.llj.lib.base.mvp.IView;
import com.llj.lib.net.BaseApiObserver;
import com.llj.lib.net.IRequestDialog;
import com.llj.lib.net.RxApiManager;
import com.uber.autodispose.AutoDisposeConverter;

import io.reactivex.Observable;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/17
 */
public class MainContract {

    interface View extends IView {
        void toast(Mobile mobile);
    }

    static class ViewModel extends BaseViewModel {


        private MutableLiveData<Mobile> usersLiveData;

        public LiveData<Mobile> getMobile(AutoDisposeConverter<Mobile> autoDisposeConverter, IRequestDialog iRequestDialog) {
            if (usersLiveData == null) {
                usersLiveData = new MutableLiveData<>();
            }

            Observable<Mobile> user = TestApi.getInstance().getMobile("13188888888");
            RxApiManager.get().toSubscribe(user, autoDisposeConverter, new BaseApiObserver<Mobile>(iRequestDialog) {

                @Override
                public void onNext(Mobile mobile) {
                    super.onNext(mobile);
                    usersLiveData.setValue(mobile);
                }
            });
            return usersLiveData;
        }
    }
}

