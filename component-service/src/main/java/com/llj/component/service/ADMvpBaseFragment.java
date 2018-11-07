package com.llj.component.service;

import android.text.TextUtils;

import com.llj.lib.base.MvpBaseFragment;
import com.llj.lib.base.mvp.IBasePresenter;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/11/5
 */
public abstract class ADMvpBaseFragment<P extends IBasePresenter> extends MvpBaseFragment<P> {


    public boolean checkLogin() {
        if(TextUtils.isEmpty(ComponentApplication.mUserInfoVo.getAccess_token())){
//            CRouter.start(CRouter.LOGIN_PHONE_LOGIN);
            return false;
        }
        return true;
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(ComponentApplication.mUserInfoVo.getAccess_token());
    }
}
