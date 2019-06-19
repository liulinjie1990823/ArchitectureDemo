package com.llj.architecturedemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.architecturedemo.AppMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.architecturedemo.db.entity.MobileEntity;
import com.llj.architecturedemo.ui.view.SecondView;
import com.llj.component.service.arouter.CRouter;
import com.llj.component.service.tracker.ITrackerReport;

import org.jetbrains.annotations.Nullable;

import butterknife.internal.DebouncingOnClickListener;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-05-23
 */
@Route(path = CRouter.APP_FIRST_ACTIVITY)
public class SecondActivity extends AppMvcBaseActivity implements SecondView, ITrackerReport {
    @Override
    public int layoutId() {
        return R.layout.second_activity;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        TextView text = findViewById(R.id.tv_second);
        text.setText("SecondActivity");
        WebSettings.getDefaultUserAgent(this);
        findViewById(R.id.tv_second).setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {

                setTrackerData(v,"text","http://www.baidu.com");

                Intent intent = new Intent(mContext, FirstActivity.class);
//                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void toast(@Nullable MobileEntity mobile) {

    }
}
