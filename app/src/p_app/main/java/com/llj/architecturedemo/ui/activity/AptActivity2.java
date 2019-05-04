package com.llj.architecturedemo.ui.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.architecturedemo.R;
import com.llj.component.service.ComponentMvcBaseActivity;
import com.llj.component.service.arouter.CRouter;
import com.llj.lib.component.annotation.BindView;
import com.llj.lib.component.annotation.IntentKey;
import com.llj.lib.component.annotation.OnClick;
import com.llj.lib.tracker.PageName;

import org.jetbrains.annotations.Nullable;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/12
 */
@PageName(value = "activity_apt")
@Route(path = CRouter.APP_APT_ACTIVITY)
public class AptActivity2 extends ComponentMvcBaseActivity {
    @BindView(R.id.root) ConstraintLayout mConstraintLayout;

    @IntentKey(name = "key") String key;

    @OnClick({R.id.root})
    public void fabClick() {
        Toast.makeText(this, "Neacy", Toast.LENGTH_LONG).show();
    }

    @Override
    public int layoutId() {
        return R.layout.activity_apt;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
//        NeacyFinder.inject(this);

    }

    @Override
    public void initData() {

    }
}
