package com.llj.architecturedemo.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.lib.jump.annotation.Jump;
import com.llj.architecturedemo.R;
import com.llj.architecturedemo.vo.Cat;
import com.llj.component.service.ComponentMvcBaseActivity;
import com.llj.component.service.arouter.CInner;
import com.llj.component.service.arouter.CRouter;
import com.llj.lib.base.event.BaseEvent;
import com.llj.lib.jni.test.JniTest;
import com.llj.lib.utils.LogUtil;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.internal.DebouncingOnClickListener;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/1/8
 */
@Jump(ciw = CInner.CIW_EVENT_ACTIVITY, route = CRouter.APP_EVENT_ACTIVITY, desc = "EventActivity")
@Route(path = CRouter.APP_EVENT_ACTIVITY)
public class EventActivity extends ComponentMvcBaseActivity {

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
                BaseEvent<String> baseEvent2 = new BaseEvent<>(100, "", null);
                post(new BaseEvent<String>(100));

                Cat cat = new Cat(1);
                Cat cat2 = new Cat(1, "");
                Cat cat3 = new Cat(1, "", "");
            }
        });

        JniTest jniTest = new JniTest();

        mTextView.setText(jniTest.stringFromJNI());

        mContext.databaseList();

        LogUtil.DEBUGLLJ = true;
        jniTest.test2();
        LogUtil.i(getMTagLog(), jniTest.test3() + "");
        LogUtil.i(getMTagLog(), jniTest.test4() + "");
        LogUtil.i(getMTagLog(), jniTest.stringFromJNI2());
    }

    @Override
    public void initData() {

    }

}
