package com.llj.architecturedemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.internal.DebouncingOnClickListener;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.llj.architecturedemo.MainMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.architecturedemo.R2.id;
import com.llj.architecturedemo.vo.Cat;
import com.llj.application.router.CJump;
import com.llj.application.router.CRouter;
import com.llj.lib.base.event.BaseEvent;
import com.llj.lib.jni.test.JniTest;
import com.llj.lib.jump.annotation.Jump;
import com.llj.lib.utils.LogUtil;
import org.jetbrains.annotations.Nullable;

/**
 * ArchitectureDemo. describe: author llj date 2019/1/8
 */
@Jump(outPath = CJump.CIW_EVENT_ACTIVITY, inPath = CRouter.APP_EVENT_ACTIVITY, desc =
    "EventActivity")
@Route(path = CRouter.APP_EVENT_ACTIVITY)
public class EventActivity extends MainMvcBaseActivity {

  @BindView(id.tv_click) TextView mTextView;

  @Override
  public int layoutId() {
    return R.layout.event_activity;
  }

  @Override
  public void initViews(@Nullable Bundle savedInstanceState) {
    mTextView.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View v) {
        BaseEvent baseEvent = new BaseEvent(100);
        BaseEvent baseEvent2 = new BaseEvent(100, "", null);
        post(new BaseEvent(100));

        Cat cat = new Cat(1);
        Cat cat2 = new Cat(1, "");
        Cat cat3 = new Cat(1, "", "");
      }
    });

    JniTest jniTest = new JniTest();
    ARouter.getInstance().build(CRouter.APP_MAIN_ACTIVITY)
        .withTransition(R.anim.fade_in, R.anim.no_fade)
        .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
        .navigation(mContext);

    mTextView.setText(jniTest.stringFromJNI());

    mContext.databaseList();

    LogUtil.DEBUGLLJ = true;
    jniTest.test2();
    LogUtil.i(mTagLog, jniTest.test3() + "");
    LogUtil.i(mTagLog, jniTest.test4() + "");
    LogUtil.i(mTagLog, jniTest.stringFromJNI2());
  }

  @Override
  public void initData() {

  }

}
