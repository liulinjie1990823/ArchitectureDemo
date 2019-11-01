package com.llj.architecturedemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.llj.architecturedemo.AppMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.architecturedemo.db.entity.MobileEntity;
import com.llj.architecturedemo.ui.view.SecondView;

import org.jetbrains.annotations.Nullable;

import butterknife.internal.DebouncingOnClickListener;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-05-23
 */
public class FifthActivity extends AppMvcBaseActivity implements SecondView {
    @Override
    public int layoutId() {
        return R.layout.second_activity;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        TextView text = findViewById(R.id.tv_second);
        text.setText("FifthActivity");
        findViewById(R.id.tv_second).setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                Intent intent = new Intent(mContext, SecondActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                mContext.startActivity(intent);
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
