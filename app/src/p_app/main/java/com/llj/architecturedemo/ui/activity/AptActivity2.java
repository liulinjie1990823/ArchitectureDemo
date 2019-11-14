package com.llj.architecturedemo.ui.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.lib.jump.annotation.Jump;
import com.llj.lib.jump.annotation.JumpKey;
import com.llj.architecturedemo.AppMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.architecturedemo.vo.Animal;
import com.llj.architecturedemo.vo.Cat;
import com.llj.architecturedemo.vo.User;
import com.llj.component.service.arouter.CRouter;
import com.llj.lib.base.event.BaseEvent;
import com.llj.lib.component.annotation.BindView;
import com.llj.lib.component.annotation.IntentKey;
import com.llj.lib.component.annotation.OnClick;
import com.llj.lib.tracker.PageName;

import org.jetbrains.annotations.Nullable;

import timber.log.Timber;

/**
 * ArchitectureDemo. describe: author llj date 2019/3/12
 */
@PageName(value = "activity_apt")
@Jump(outPath = "outPath://AptActivity2", inPath = CRouter.APP_APT_ACTIVITY2, needLogin = true, desc = "AptActivity2")
@Route(path = CRouter.APP_APT_ACTIVITY2)
public class AptActivity2 extends AppMvcBaseActivity {

  @BindView(R.id.root) ConstraintLayout mConstraintLayout;

  @IntentKey(name = "outKey") String mKey;

  @JumpKey(outKey = "value", inKey = CRouter.KEY_NICKNAME)
  @Autowired(name = CRouter.KEY_NICKNAME) String mName;

  @JumpKey(outKey = "boolean1", inKey = "BOOLEAN", required = true) boolean mBoolean;
  @JumpKey(outKey = "short1", inKey = "SHORT")                      short   mShort;
  @JumpKey(outKey = "int1", inKey = "INT")                          int     mInt;
  @JumpKey(outKey = "long1", inKey = "LONG")                        long    mLong;
  @JumpKey(outKey = "float1", inKey = "FLOAT")                      float   mFloat;
  @JumpKey(outKey = "double1", inKey = "DOUBLE")                    double  mDouble;
  @JumpKey(outKey = "char1", inKey = "CHAR")                        char    mChar;
  @JumpKey(outKey = "animal", inKey = "ANIMAL")                     Animal  mAnimal;
  @JumpKey(outKey = "user", inKey = "USER")                         User    mUser;
  @JumpKey(outKey = "cat", inKey = "CAT")                           Cat     mCat;

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
    //NeacyFinder.inject(this);

    findViewById(R.id.text1).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Timber.e("onClick text1");
        BaseEvent baseEvent = new BaseEvent();
        baseEvent.setCode(1);
        post(baseEvent);

      }
    });
    findViewById(R.id.text2).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Timber.e("onClick text1");
        BaseEvent<String> baseEvent = new BaseEvent<>();
        baseEvent.setCode(1);
        baseEvent.setData("cadadada");
        post(baseEvent);
      }
    });

    findViewById(R.id.text3).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Timber.e("onClick text1");
        BaseEvent<Integer> baseEvent = new BaseEvent<>();
        baseEvent.setCode(1);
        baseEvent.setData(10086);
        post(baseEvent);
      }
    });
  }

  @Override
  public void initData() {

  }

  @Override
  public <T> void onReceiveEvent(@NonNull BaseEvent<T> event) {
    super.onReceiveEvent(event);
  }


}
