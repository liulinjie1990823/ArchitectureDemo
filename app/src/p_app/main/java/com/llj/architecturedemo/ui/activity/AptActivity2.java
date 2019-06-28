package com.llj.architecturedemo.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.lib.base.annotation.Jump;
import com.example.lib.base.annotation.JumpKey;
import com.llj.architecturedemo.R;
import com.llj.architecturedemo.vo.Animal;
import com.llj.architecturedemo.vo.Cat;
import com.llj.architecturedemo.vo.User;
import com.llj.component.service.ComponentMvcBaseActivity;
import com.llj.component.service.arouter.CRouter;
import com.llj.lib.base.event.BaseEvent;
import com.llj.lib.component.annotation.BindView;
import com.llj.lib.component.annotation.IntentKey;
import com.llj.lib.component.annotation.OnClick;
import com.llj.lib.tracker.PageName;

import org.jetbrains.annotations.Nullable;

import timber.log.Timber;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/12
 */
@PageName(value = "activity_apt")
@Jump(ciw = "ciw://AptActivity2", route = CRouter.APP_APT_ACTIVITY2, needLogin = true, desc = "AptActivity2")
@Route(path = CRouter.APP_APT_ACTIVITY2)
public class AptActivity2 extends ComponentMvcBaseActivity {
    @BindView(R.id.root) ConstraintLayout mConstraintLayout;

    @IntentKey(name = "key") String key;

    @JumpKey(ciw = "name", name = CRouter.KEY_NICKNAME)
    @Autowired(name = CRouter.KEY_NICKNAME) String mName;

    @JumpKey(ciw = "boolean1", name = "BOOLEAN", required = true) boolean mBoolean;
    @JumpKey(ciw = "short1", name = "SHORT")                      short   mShort;
    @JumpKey(ciw = "int1", name = "INT")                          int     mInt;
    @JumpKey(ciw = "long1", name = "LONG")                        long    mLong;
    @JumpKey(ciw = "float1", name = "FLOAT")                      float   mFloat;
    @JumpKey(ciw = "double1", name = "DOUBLE")                    double  mDouble;
    @JumpKey(ciw = "char1", name = "CHAR")                        char    mChar;
    @JumpKey(ciw = "animal", name = "ANIMAL")                     Animal  mAnimal;
    @JumpKey(ciw = "user", name = "USER")                         User    mUser;
    @JumpKey(ciw = "cat", name = "CAT")                           Cat     mCat;

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
