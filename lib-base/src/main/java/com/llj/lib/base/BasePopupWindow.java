package com.llj.lib.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import butterknife.ButterKnife;

/**
 * dmp_hunbohui
 * describe:
 * author liulj
 * date 2017/12/14
 */

public abstract class BasePopupWindow extends PopupWindow {

    public Context mContext;
    private float mShowAlpha = 1.0f;

    /**
     * <p>Create a new empty, non focusable popup window. The dimension of the
     * window must be passed to this constructor.</p>
     * <p>
     * <p>The popup does not provide any background. This should be handled
     * by the content view.</p>
     *
     * @param width  the popup's width
     * @param height the popup's height
     */
    public BasePopupWindow(int width, int height, Context context) {
        super(width, height);
        this.mContext = context;
        initParams(context);
        bindViews();
        initViews();
    }

    /**
     * @param context
     */
    private void initParams(final Context context) {
        // 在PopupWindow里面就加上下面代码，让键盘弹出时，不会挡住pop窗口。
        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setElevation(20);
        }
        // 这个设置了可以按返回键dismiss
        setFocusable(true);
        // 可以点击外面dismiss需要一下两个条件
        setBackgroundDrawable(new ColorDrawable());
        setOutsideTouchable(true);
        // 添加pop窗口关闭事件
        setOnDismissListener(() -> {
            backgroundAlpha(context, 1f);
            if (mOnDismissListener != null) {
                mOnDismissListener.onDismiss();
            }
        });
    }

    private void bindViews() {
        View view = View.inflate(mContext, getLayoutId(), null);
        setContentView(view);
        ButterKnife.bind(this, view);
    }

    abstract public int getLayoutId();


    private OnDismissListener mOnDismissListener;

    public void setOnMyDismissListener(OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
    }


    public void setShowAlpha(float bgAlpha) {
        this.mShowAlpha = bgAlpha;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity, float showAlpha) {
        backgroundAlpha(mContext, showAlpha);
        this.showAsDropDown(anchor, xoff, yoff, gravity);
    }

    public void showAsDropDown(View anchor, float showAlpha) {
        backgroundAlpha(mContext, showAlpha);
        this.showAsDropDown(anchor);
    }

    /**
     * 根据view来设置位置
     *
     * @param anchor
     * @param xoff
     * @param yoff
     * @param showAlpha
     */
    public void showAsDropDown(View anchor, int xoff, int yoff, float showAlpha) {
        backgroundAlpha(mContext, showAlpha);
        this.showAsDropDown(anchor, xoff, yoff);
    }

    /**
     * 根据全屏来设置位置
     *
     * @param parent    可以是屏幕内的任何的view,为的是获得token
     * @param gravity
     * @param x
     * @param y
     * @param showAlpha
     */
    public void showAtLocation(View parent, int gravity, int x, int y, float showAlpha) {
        backgroundAlpha(mContext, showAlpha);
        this.showAtLocation(parent, gravity, x, y);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Context context, float bgAlpha) {
        mShowAlpha = bgAlpha;
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        if (mShowAlpha != lp.alpha) {
            lp.alpha = bgAlpha; // 0.0-1.0
            ((Activity) context).getWindow().setAttributes(lp);
        }
    }


    @Override
    public void showAsDropDown(View anchor) {
        onShow();
        showAsDropDownCompat(anchor);
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        onShow();
        showAsDropDownCompat(anchor);
        super.showAsDropDown(anchor, xoff, yoff, gravity);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        onShow();
        showAsDropDownCompat(anchor);
        super.showAsDropDown(anchor, xoff, yoff);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        onShow();
        super.showAtLocation(parent, gravity, x, y);
    }

    private void showAsDropDownCompat(View anchor) {
        if (Build.VERSION.SDK_INT == 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
    }


    private void onShow() {
    }

    private void initViews() {
    }

}
