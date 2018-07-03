package com.llj.architecturedemo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.architecturedemo.MyBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.component.service.arouter.CRouter;
import com.llj.lib.base.listeners.OnMyClickListener;

import butterknife.BindView;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/7/3
 */
@Route(path = CRouter.APP_SECOND_ACTIVITY)
public class SecondActivity extends MyBaseActivity {

    @BindView(R.id.tv_click) TextView mTvClick;

    public static void start(Context context) {
        Intent intent = new Intent(context, SecondActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_second;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        mTvClick.setOnClickListener(new OnMyClickListener() {
            @Override
            public void onCanClick(View v) {
                MainActivity.start(mContext);
            }
        });

    }

    @Override
    public void initData() {


    }

}
