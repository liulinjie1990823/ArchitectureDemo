package com.llj.setting.ui.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.component.service.arouter.CRouter;
import com.llj.component.service.vo.UserInfoVo;
import com.llj.setting.R;
import com.llj.setting.SettingMvpBaseActivity;
import com.llj.setting.ui.model.MobileInfoVo;
import com.llj.setting.ui.presenter.SettingPresenter;
import com.llj.setting.ui.view.SettingView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/25
 */
@Route(path = CRouter.QRCODE_ACTIVITY)
public class QrCodeActivity extends SettingMvpBaseActivity<SettingPresenter> implements SettingView {

    @Override
    public int layoutId() {
        return R.layout.setting_qrcode_activity;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }

    @NotNull
    @Override
    public HashMap<String, Object> getParams1() {
        return null;
    }

    @Override
    public void onDataSuccess1(UserInfoVo result) {

    }

    @NotNull
    @Override
    public HashMap<String, Object> getParams2() {
        return null;
    }

    @Override
    public void onDataSuccess2(MobileInfoVo result) {

    }

    @Override
    public void onDataError(int tag, @NotNull Throwable e) {

    }
}
