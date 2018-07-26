package com.llj.architecturedemo.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.architecturedemo.MyBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.component.service.arouter.CRouter;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/7/2
 */
@Route(path = CRouter.APP_MEMORY_LEAK_ACTIVITY)
public class MemoryLeakActivity extends MyBaseActivity {
    @Override
    public int layoutId() {
        return R.layout.activity_memory_leak;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        new Handler().postDelayed(() -> {

        }, 10000000);

    }

    class MyHandler extends Handler {
       public MyHandler(Callback callback) {
           super(callback);
       }

       @Override
        public void handleMessage(Message msg) {
            mContext.databaseList();
            super.handleMessage(msg);
        }
    }

    private final Handler dada = new MyHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    @Override
    public void initData() {

    }

}
