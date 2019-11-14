package com.llj.lib.statelayout;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.provider.Settings;
import androidx.annotation.IntDef;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * ArchitectureDemo.
 * describe:状态布局
 *
 * @author llj
 * @date 2018/8/14
 */
public class StateLayout extends FrameLayout {
    private static final int STATE_CONTENT  = -1;
    private static final int STATE_LOADING  = 0;
    private static final int STATE_ERROR    = 1;
    private static final int STATE_EMPTY    = 2;
    private static final int STATE_NO_NET   = 3;
    private static final int STATE_TEXT_TIP = 4;

    @IntDef({STATE_CONTENT,
            STATE_LOADING,
            STATE_ERROR,
            STATE_EMPTY,
            STATE_NO_NET,
            STATE_TEXT_TIP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    /**
     * 四种状态布局
     **/
    private View mNoNetworkView, mLoadingView, mErrorView, mEmptyView, mTextTipView;
    /***
     * 存放所有的布局文件
     **/
    private View mContentView;
    /***
     * 当前显示的布局
     */
    private int  mShowState = STATE_CONTENT;

    public StateLayout(Context context) {
        this(context, null);
    }

    public StateLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.StateLayout, defStyleAttr, 0);
        LayoutInflater inflater = LayoutInflater.from(context);
        FrameLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        try {
            //无网络
            if (array.hasValue(R.styleable.StateLayout_noNetLayout)) {
                int noNetId = array.getResourceId(R.styleable.StateLayout_noNetLayout, R.layout.viewstatus_no_netwrok);
                mNoNetworkView = inflater.inflate(noNetId, null);
                addView(mNoNetworkView, params);
                setViewVisibility(mNoNetworkView, false);
            }
            //无数据
            if (array.hasValue(R.styleable.StateLayout_emptyLayout)) {
                int emptyId = array.getResourceId(R.styleable.StateLayout_emptyLayout, R.layout.viewstatus_no_data);
                mEmptyView = inflater.inflate(emptyId, null);
                addView(mEmptyView, params);
                setViewVisibility(mEmptyView, false);
            }
            //加载异常
            if (array.hasValue(R.styleable.StateLayout_errorLayout)) {
                int errorId = array.getResourceId(R.styleable.StateLayout_errorLayout, R.layout.viewstatus_loading_faile);
                mErrorView = inflater.inflate(errorId, null);
                addView(mErrorView, params);
                setViewVisibility(mErrorView, false);
            }
            //加载ing
            if (array.hasValue(R.styleable.StateLayout_loadingLayout)) {
                int loadingId = array.getResourceId(R.styleable.StateLayout_loadingLayout, R.layout.viewstatus_loading);
                mLoadingView = inflater.inflate(loadingId, null);
                addView(mLoadingView, params);
                setViewVisibility(mLoadingView, false);
            }
            //
            if (array.hasValue(R.styleable.StateLayout_textTip)) {
                int loadingId = array.getResourceId(R.styleable.StateLayout_textTip, R.layout.viewstatus_text_tip);
                mTextTipView = inflater.inflate(loadingId, null);
                addView(mTextTipView, params);
                setViewVisibility(mTextTipView, false);
            }
        } finally {
            array.recycle();
        }
    }

    /**
     * 检查某个控件是否为空，并初始化
     *
     * @param state
     */
    private void checkNullAndInflate(@State int state) {
        View pView = null;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        switch (state) {
            case STATE_EMPTY:
                if (mEmptyView == null) {
                    mEmptyView = inflater.inflate(R.layout.viewstatus_no_data, null);
                    pView = mEmptyView;
                }
                break;
            case STATE_ERROR:
                if (mErrorView == null) {
                    mErrorView = inflater.inflate(R.layout.viewstatus_loading_faile, null);
                    pView = mErrorView;
                }
                break;
            case STATE_LOADING:
                if (mLoadingView == null) {
                    mLoadingView = inflater.inflate(R.layout.viewstatus_loading, null);
                    pView = mLoadingView;
                }
                break;
            case STATE_NO_NET:
                if (mNoNetworkView == null) {
                    mNoNetworkView = inflater.inflate(R.layout.viewstatus_no_netwrok, null);
                    pView = mNoNetworkView;
                }
                break;
            case STATE_TEXT_TIP:
                if (mTextTipView == null) {
                    mTextTipView = inflater.inflate(R.layout.viewstatus_text_tip, null);
                    pView = mTextTipView;
                }
                break;
            default:
                break;
        }
        if (pView != null) {
            addView(pView, params);
            setViewVisibility(pView, false);
        }
    }


    public void setEmptyView(ArrayList<Change> changeTexts, OnClickListener listener) {
        checkNullAndInflate(STATE_EMPTY);
        changeTextOrImage(changeTexts, listener);
    }

    /**
     * 显示空视图
     */
    public void showEmptyView() {
        checkNullAndInflate(STATE_EMPTY);

        switchView(nowShowView(mShowState), mEmptyView);

        mShowState = STATE_EMPTY;
    }

    /**
     * 替换原有的文字或者图片,并增加监听
     *
     * @param changes  changes
     * @param listener listener
     */
    private void changeTextOrImage(ArrayList<Change> changes, OnClickListener listener) {
        if (changes != null && changes.size() != 0) {
            for (Change change : changes) {
                if (change == null) {
                    continue;
                }
                int id = getResources().getIdentifier(change.id, "id", getContext().getPackageName());

                if (change.imgRes > 0) {
                    ImageView imageView = findViewById(id);
                    if (imageView != null) {
                        imageView.setTag(id);
                        imageView.setImageResource(change.imgRes);
                        imageView.setOnClickListener(listener);
                    }
                } else {
                    TextView textView = findViewById(id);
                    if (textView != null) {
                        textView.setTag(id);
                        textView.setText(change.text);
                        textView.setOnClickListener(listener);
                    }
                }

            }
        }
    }

    public static class Change {
        //图片或者文字的id
        public String id;
        public String text;
        public int    imgRes;

        public Change(String id, String text) {
            this.id = id;
            this.text = text;
        }

        public Change(String id, int imgRes) {
            this.id = id;
            this.imgRes = imgRes;
        }
    }

    public void setTextTipView(ArrayList<Change> changeTexts, OnClickListener listener) {
        checkNullAndInflate(STATE_TEXT_TIP);
        changeTextOrImage(changeTexts, listener);
    }

    /**
     * 显示纯文本提示
     */
    public void showTextTipView() {
        checkNullAndInflate(STATE_TEXT_TIP);
        switchView(nowShowView(mShowState), mTextTipView);

        mShowState = STATE_TEXT_TIP;
    }

    public void setNoNetView(ArrayList<Change> changeTexts, OnClickListener listener) {
        checkNullAndInflate(STATE_NO_NET);
        changeTextOrImage(changeTexts, listener);
    }

    /**
     * 显示没有网络视图
     */
    public void showNoNetView() {
        checkNullAndInflate(STATE_NO_NET);
        switchView(nowShowView(mShowState), mNoNetworkView);

        mShowState = STATE_NO_NET;
    }

    public void setLoadingView(ArrayList<Change> changeTexts, OnClickListener listener) {
        checkNullAndInflate(STATE_LOADING);
        changeTextOrImage(changeTexts, listener);
    }

    /**
     * 显示加载中布局
     */
    public void showLoadingView() {
        checkNullAndInflate(STATE_LOADING);
        switchView(nowShowView(mShowState), mLoadingView);

        mShowState = STATE_LOADING;
    }

    public void setErrorView(ArrayList<Change> changeTexts, OnClickListener listener) {
        checkNullAndInflate(STATE_ERROR);
        changeTextOrImage(changeTexts, listener);
    }

    /**
     * 显示加载失败视图
     */
    public void showErrorView() {
        checkNullAndInflate(STATE_ERROR);
        switchView(nowShowView(mShowState), mErrorView);

        mShowState = STATE_ERROR;
    }

    /**
     * 显示内容视图
     */
    public void showContentView() {
        switchView(nowShowView(mShowState), mContentView);

        mShowState = STATE_CONTENT;
    }


    /**
     * 打开网络设置界面
     *
     * @param pContext 上下文
     */
    private void openWifiSetting(Context pContext) {
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (pContext != null) {
            pContext.startActivity(intent);
        }
    }

    /**
     * 根据状态返回布局
     *
     * @param state
     *
     * @return
     */
    private View nowShowView(int state) {
        View returnView = null;
        switch (state) {
            case STATE_CONTENT:
                returnView = mContentView;
                break;
            case STATE_EMPTY:
                returnView = mEmptyView;
                break;
            case STATE_ERROR:
                returnView = mErrorView;
                break;
            case STATE_LOADING:
                returnView = mLoadingView;
                break;
            case STATE_NO_NET:
                returnView = mNoNetworkView;
                break;
            default:
                break;
        }
        return returnView;
    }

    /**
     * 从旧布局选择到新布局，可以考虑做动画
     *
     * @param pOldView
     * @param pNewView
     */
    private void switchView(final View pOldView, final View pNewView) {
        setViewVisibility(pOldView, false);
        setViewVisibility(pNewView, true);


//        setViewVisibility(pOldView, true);
//        setViewVisibility(pNewView, true);
//        AlphaAnimation oldAlpha = new AlphaAnimation(1, 0);
//        oldAlpha.setDuration(500);
//        final AlphaAnimation newAlpha = new AlphaAnimation(0, 1);
//        newAlpha.setDuration(500);
//        pOldView.setAnimation(oldAlpha);
//        pNewView.setAnimation(newAlpha);
//        oldAlpha.startNow();
//        newAlpha.startNow();
//        oldAlpha.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                setViewVisibility(pOldView, false);
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        newAlpha.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                setViewVisibility(pNewView, true);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });

    }

    /***
     * 设置一个控件的状态
     *
     * @param view 控件
     * @param visible   true，显示，false不显示
     */
    private void setViewVisibility(View view, boolean visible) {
        if (view != null) {
            view.setVisibility(visible ? VISIBLE : GONE);
        }
    }

    /***
     * 如果没有设置内容布局ID,除了四个状态布局，其它都算是内容布局
     *
     * @param view
     */
    private void checkIsContentView(View view) {
        if (view != null && view != mNoNetworkView && view != mLoadingView && view != mErrorView && view != mEmptyView && view != mTextTipView) {
            mContentView = view;
            int mixCount = 0;
            if (mNoNetworkView != null) {
                mixCount++;
            }
            if (mLoadingView != null) {
                mixCount++;
            }
            if (mErrorView != null) {
                mixCount++;
            }
            if (mEmptyView != null) {
                mixCount++;
            }
            if (mTextTipView != null) {
                mixCount++;
            }
            if (getChildCount() != mixCount) {
                throw new RuntimeException("StateLayout must only one child");
            }

        }
    }

    @Override
    public void addView(View child) {
        checkIsContentView(child);
        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        checkIsContentView(child);
        super.addView(child, index);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        checkIsContentView(child);
        super.addView(child, index, params);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        checkIsContentView(child);
        super.addView(child, params);
    }

    @Override
    public void addView(View child, int width, int height) {
        checkIsContentView(child);
        super.addView(child, width, height);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params) {
        checkIsContentView(child);
        return super.addViewInLayout(child, index, params);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params, boolean preventRequestLayout) {
        checkIsContentView(child);
        return super.addViewInLayout(child, index, params, preventRequestLayout);
    }

    public void checkEmptyView(List list) {
        if (isEmpty(list)) {
            showEmptyView();
        } else {
            showContentView();
        }
    }

    public void checkEmptyView(int size) {
        if (size <= 0) {
            showEmptyView();
        } else {
            showContentView();
        }
    }

    private boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }
}