package com.llj.lib.utils;

import android.app.Activity;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author llj
 */
public class IndicateImageUtilLj {
    private static String TAG = "IndicateImageUtil";
    private Timer        mTimer;
    private TimerTask    mTimerTask;
    private Activity     mActivity;
    private ViewPager    mViewPager;
    private LinearLayout mPointcontainli;
    private int          mSelectdrawable;
    private int          mNormaldrawable;
    private int          mPointNum;

    /**
     * @param activity
     * @param viewPager
     */
    public IndicateImageUtilLj(Activity activity, ViewPager viewPager, LinearLayout pointcontainli) {
        this.mActivity = activity;
        this.mViewPager = viewPager;
        this.mPointcontainli = pointcontainli;
    }

    /**
     * 初始化指示点
     *
     * @param leftmagin      指示点之间的间隔
     * @param pointNum       指示点的个数
     * @param selectdrawable 指示点选中的drawable
     * @param normaldrawable 指示点正常的drawable
     */
    public void initPointView(int leftmagin, int pointNum, int selectdrawable, int normaldrawable) {
        this.mSelectdrawable = selectdrawable;
        this.mNormaldrawable = normaldrawable;
        this.mPointNum = pointNum;
        if (mPointcontainli != null) {
            mPointcontainli.removeAllViews();
            if (mPointNum > 1) {
                LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mParams.leftMargin = leftmagin;
                for (int i = 0; i < mPointNum; i++) {
                    ImageView imageView = new ImageView(mActivity);
                    if (i == 0) {
                        imageView.setImageResource(selectdrawable);
                    } else {
                        imageView.setImageResource(normaldrawable);
                    }
                    imageView.setLayoutParams(mParams);
                    mPointcontainli.addView(imageView);
                }
            } else {
                Log.v(TAG, "<initPointView> mPointNum " + mPointNum);
            }
        } else {
        }
    }

    /**
     * Viewpager选中时指示点的显示,在viewpager的监听回调中调用
     *
     * @param listsize   指示器的个数(图片的张数)
     * @param selectitem 选中项指示点(onPageSelected参数)
     */
    public void onPagerSelected(int selectitem) {
        int position = 0;
        if (mPointNum != 0) {
            position = selectitem % mPointNum;
        }
        if (mPointcontainli != null) {
            if (mPointcontainli.getChildCount() > 1) {
                for (int i = 0; i < mPointcontainli.getChildCount(); i++) {
                    ImageView imageView = (ImageView) mPointcontainli.getChildAt(i);
                    if (i == position) {
                        imageView.setImageResource(mSelectdrawable);
                    } else {
                        imageView.setImageResource(mNormaldrawable);
                    }
                }
            } else {
                Log.v(TAG, "<initPagerSelected> pointcontainli.getChildCount()=" + mPointcontainli.getChildCount());
            }
        } else {
        }
    }

    /**
     *
     */
    public void initTask() {
        stopTask();
        if (mViewPager == null) {
            return;
        }
        if (mViewPager.getAdapter() == null) {
            return;
        }
        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    if (mActivity != null) {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                            }
                        });
                    } else {

                    }
                }
            };
        }
    }

    /**
     *
     */
    public void startRepeat() {
        if (mViewPager != null) {
            if (mPointNum > 1) {
                mTimer = new Timer();
                if (mTimerTask != null) {
                    mTimer.schedule(mTimerTask, 3000, 3000);
                }
            }
        } else {

        }
    }

    /**
     *
     */
    public void stopTask() {
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }
}
