package com.llj.architecturedemo.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.architecturedemo.AppMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.component.service.arouter.CRouter;
import com.zhangyf.gift.RewardLayout;
import com.zhangyf.gift.bean.GiftIdentify;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.internal.DebouncingOnClickListener;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/1/7
 */
@Route(path = CRouter.APP_REWARD_LAYOUT_ACTIVITY)
public class RewardLayoutActivity extends AppMvcBaseActivity {
    @BindView(R.id.rewardLayout) RewardLayout mRewardLayout;
    @BindView(R.id.tv_click)     TextView     mClick;

    @Override
    public int layoutId() {
        return R.layout.reward_layout_activity;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        mRewardLayout.setGiftAdapter(new RewardLayout.GiftAdapter<Gift>() {
            @Override
            public View onInit(View view, Gift bean) {
                return view;
            }

            @Override
            public View onUpdate(View view, Gift bean) {
                return view;
            }

            @Override
            public void onKickEnd(Gift bean) {

            }

            @Override
            public void onComboEnd(Gift bean) {

            }

            @Override
            public void addAnim(View view) {
                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.gift_in);
                view.startAnimation(animation);

            }

            @Override
            public AnimationSet outAnim() {
                return (AnimationSet) AnimationUtils.loadAnimation(mContext, R.anim.gift_out);
            }

            @Override
            public boolean checkUnique(Gift o, Gift gift) {
                return false;
            }

            @Override
            public Gift generateBean(Gift bean) {
                try {
                    return (Gift) bean.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });


        DebouncingOnClickListener l = new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                mRewardLayout.put(new Gift(0, 0, "林喵喵", "糖果", R.mipmap.ic_launcher, 0));
            }
        };
        mClick.setOnClickListener(l);

    }

    public class Gift implements GiftIdentify, Cloneable {
        private int    userId;
        private int    giftId;
        private String userName;
        private String giftName;
        private int    giftImg;
        private long   giftStayTime;
        private int    giftSendSize = 1;


        private int  giftCount;
        private long latestRefreshTime;
        private int  currentIndex;

        public Gift(int userId, int giftId, String userName, String giftName, int giftImg, long giftStayTime) {
            this.userId = userId;
            this.giftId = giftId;
            this.userName = userName;
            this.giftName = giftName;
            this.giftImg = giftImg;
            this.giftStayTime = giftStayTime;
        }

        @Override
        public int getTheGiftId() {
            return giftId;
        }

        @Override
        public void setTheGiftId(int gid) {
            giftId = gid;
        }

        @Override
        public int getTheUserId() {
            return userId;
        }

        @Override
        public void setTheUserId(int uid) {
            userId = uid;
        }

        @Override
        public int getTheGiftCount() {
            return giftCount;
        }

        @Override
        public void setTheGiftCount(int count) {
            giftCount = count;
        }

        @Override
        public int getTheSendGiftSize() {
            return giftSendSize;
        }

        @Override
        public void setTheSendGiftSize(int size) {
            giftSendSize = size;
        }

        @Override
        public long getTheGiftStay() {
            return giftStayTime;
        }

        @Override
        public void setTheGiftStay(long stay) {
            giftStayTime = stay;
        }

        @Override
        public long getTheLatestRefreshTime() {
            return giftStayTime;
        }

        @Override
        public void setTheLatestRefreshTime(long time) {
            giftStayTime = time;
        }

        @Override
        public int getTheCurrentIndex() {
            return currentIndex;
        }

        @Override
        public void setTheCurrentIndex(int index) {
            currentIndex = index;
        }

        @Override
        public int compareTo(@NonNull GiftIdentify o) {
            return 0;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    @Override
    public void initData() {

    }
}
