package com.llj.architecturedemo;

import com.llj.architecturedemo.db.model.MobileEntity;
import com.llj.lib.base.mvp.BaseViewModel;
import com.llj.lib.base.mvp.IView;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/17
 */
public class MainContract {

    interface View extends IView {
        void toast(MobileEntity mobile);
    }

    static class ViewModel extends BaseViewModel {


//        private MutableLiveData<Mobile> usersLiveData;
//
//        public LiveData<Mobile> getMobile(AutoDisposeConverter<Mobile> autoDisposeConverter, IRequestDialog iRequestDialog) {
//            if (usersLiveData == null) {
//                usersLiveData = new MutableLiveData<>();
//            }
//
//            Observable<Mobile> user = TestApi.getInstance().getMobile("13188888888");
//            RxApiManager.get().toSubscribe(user, autoDisposeConverter, new BaseApiObserver<Mobile>(iRequestDialog) {
//
//                @Override
//                public void onNext(Mobile mobile) {
//                    super.onNext(mobile);
//                    usersLiveData.setValue(mobile);
//                }
//            });
//            return usersLiveData;
//        }
    }
}

