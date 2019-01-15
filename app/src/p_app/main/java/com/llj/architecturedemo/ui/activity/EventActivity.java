package com.llj.architecturedemo.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.architecturedemo.R;
import com.llj.architecturedemo.vo.Cat;
import com.llj.component.service.ADMvcBaseActivity;
import com.llj.component.service.arouter.CRouter;
import com.llj.lib.base.BaseEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.internal.DebouncingOnClickListener;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/1/8
 */
@Route(path = CRouter.APP_EVENT_ACTIVITY)
public class EventActivity extends ADMvcBaseActivity {
    @BindView(R.id.tv_click) TextView mTextView;

    @Override
    public int layoutId() {
        return R.layout.event_activity;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        mTextView.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                BaseEvent<String> baseEvent = new BaseEvent<>(100);
                BaseEvent<String> baseEvent2 = new BaseEvent<>(100,"",null);
                post(new BaseEvent<String>(100));

                Cat cat=new Cat(1);
                Cat cat2=new Cat(1,"");
                Cat cat3=new Cat(1,"","");
            }
        });
    }

    @Override
    public void initData() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent<String> baseEvent) {

    }
}
