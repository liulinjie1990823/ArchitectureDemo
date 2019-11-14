package com.llj.lib.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.llj.lib.base.R;
import com.llj.lib.utils.ADisplayUtils;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/24
 */
public class CommonToolbar extends LinearLayout {
    private RelativeLayout mContainRl;
    private TextView       mLeftTextView;
    private TextView       mCenterTextView;
    private TextView       mRightTextView;
    private TextView       mRightTwoTextView;
    private View           mLineView;
    private ImageView      mCenterIv;

    private int mDefaultDrawablePadding;
    private int mDefaultLeftOrRightPadding;

    public CommonToolbar(Context context) {
        super(context);
        initViews(context, null);
    }

    public CommonToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
    }

    public CommonToolbar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(context, attrs);
    }

    private void initViews(Context context, AttributeSet attrs) {
        mDefaultDrawablePadding = ADisplayUtils.dp2px(context, 5);
        mDefaultLeftOrRightPadding = ADisplayUtils.dp2px(context, 15);
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(getContext()).inflate(R.layout.common_titlebar_layout, this);
        mCenterIv =  findViewById(R.id.iv_center);
        mContainRl =  findViewById(R.id.height_wrap_rl);
        mLeftTextView =  findViewById(R.id.tv_left_text);
        mCenterTextView =  findViewById(R.id.tv_center_text);
        mRightTextView =  findViewById(R.id.tv_right_text);
        mRightTwoTextView =  findViewById(R.id.tv_right_two_text);
        mLineView = findViewById(R.id.v_line);
        initAttrs(context, attrs);
    }

    @SuppressWarnings("deprecation")
    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonToolbar);
            // 设置比重值
            RelativeLayout.LayoutParams mLeftLiParams = (android.widget.RelativeLayout.LayoutParams) mLeftTextView.getLayoutParams();
            RelativeLayout.LayoutParams mRightLiParams = (android.widget.RelativeLayout.LayoutParams) mRightTextView.getLayoutParams();
            RelativeLayout.LayoutParams mRightTwoLiParams = (android.widget.RelativeLayout.LayoutParams) mRightTwoTextView.getLayoutParams();
            RelativeLayout.LayoutParams mCenterTextViewParams = (android.widget.RelativeLayout.LayoutParams) mCenterTextView.getLayoutParams();

            mContainRl.getLayoutParams().height = typedArray.getDimensionPixelSize(R.styleable.CommonToolbar_toolbar_li_contain_height, 88);
            // mContain.setLayoutParams(mlp);
            mLeftTextView.setLayoutParams(mLeftLiParams);
            mRightTextView.setLayoutParams(mRightLiParams);
            mRightTwoTextView.setLayoutParams(mRightTwoLiParams);
            mCenterTextView.setLayoutParams(mCenterTextViewParams);
            // 设置左边textview的左右padding
            int leftTextLeftPadding = typedArray.getDimensionPixelSize(R.styleable.CommonToolbar_toolbar_lefttext_padding_left, mDefaultLeftOrRightPadding);
            int leftTextRightPadding = typedArray.getDimensionPixelSize(R.styleable.CommonToolbar_toolbar_lefttext_padding_right, mDefaultLeftOrRightPadding);
            mLeftTextView.setPadding(leftTextLeftPadding, 0, leftTextRightPadding, 0);
            // 设置右边textview的左右padding
            int rightTextLeftPadding = typedArray.getDimensionPixelSize(R.styleable.CommonToolbar_toolbar_lefttext_padding_left, mDefaultLeftOrRightPadding);
            int rightTextRightPadding = typedArray.getDimensionPixelSize(R.styleable.CommonToolbar_toolbar_righttext_padding_right, mDefaultLeftOrRightPadding);
            mRightTextView.setPadding(rightTextLeftPadding, 0, rightTextRightPadding, 0);
            mRightTwoTextView.setPadding(rightTextLeftPadding, 0, rightTextRightPadding, 0);
            //设置textview中文字和Drawable的padding
            mLeftTextView.setCompoundDrawablePadding(typedArray.getDimensionPixelSize(R.styleable.CommonToolbar_toolbar_lefttext_drawable_padding, mDefaultDrawablePadding));
            mRightTextView.setCompoundDrawablePadding(typedArray.getDimensionPixelSize(R.styleable.CommonToolbar_toolbar_righttext_drawable_padding, mDefaultDrawablePadding));
            mRightTwoTextView.setCompoundDrawablePadding(typedArray.getDimensionPixelSize(R.styleable.CommonToolbar_toolbar_right_two_text_drawable_padding, mDefaultDrawablePadding));
            // 设置text文字
            mLeftTextView.setText(typedArray.getString(R.styleable.CommonToolbar_toolbar_lefttext));
            mCenterTextView.setText(typedArray.getString(R.styleable.CommonToolbar_toolbar_centertext));
            mRightTextView.setText(typedArray.getString(R.styleable.CommonToolbar_toolbar_righttext));
            mRightTwoTextView.setText(typedArray.getString(R.styleable.CommonToolbar_toolbar_right_two_text));
            // 设置text大小
            // 默认传入的是sp单位，由于这里textSize已经的px单位，所以需要制定传入的单位为px
            mLeftTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimensionPixelSize(R.styleable.CommonToolbar_toolbar_lefttext_size, 24));
            mCenterTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimensionPixelSize(R.styleable.CommonToolbar_toolbar_centertext_size, 24));
            mRightTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimensionPixelSize(R.styleable.CommonToolbar_toolbar_righttext_size, 24));
            mRightTwoTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimensionPixelSize(R.styleable.CommonToolbar_toolbar_right_two_text_size, 24));
            // 设置textcolor
            if (typedArray.getColorStateList(R.styleable.CommonToolbar_toolbar_lefttext_color) != null) {
                mLeftTextView.setTextColor(typedArray.getColorStateList(R.styleable.CommonToolbar_toolbar_lefttext_color));
            }
            if (typedArray.getColorStateList(R.styleable.CommonToolbar_toolbar_centertext_color) != null) {
                mCenterTextView.setTextColor(typedArray.getColorStateList(R.styleable.CommonToolbar_toolbar_centertext_color));
            }
            if (typedArray.getColorStateList(R.styleable.CommonToolbar_toolbar_righttext_color) != null) {
                mRightTextView.setTextColor(typedArray.getColorStateList(R.styleable.CommonToolbar_toolbar_righttext_color));
            }
            if (typedArray.getColorStateList(R.styleable.CommonToolbar_toolbar_right_two_text_color) != null) {
                mRightTwoTextView.setTextColor(typedArray.getColorStateList(R.styleable.CommonToolbar_toolbar_right_two_text_color));
            }
            // 设置左右drawable和中间text的background
            if (typedArray.getDrawable(R.styleable.CommonToolbar_toolbar_lefttext_drawable_left) != null) {
                Drawable leftDrawable = typedArray.getDrawable(R.styleable.CommonToolbar_toolbar_lefttext_drawable_left);
                mLeftTextView.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, null, null);
            }
            //
            if (typedArray.getDrawable(R.styleable.CommonToolbar_toolbar_centertext_background_drawable) != null)
                mCenterTextView.setBackgroundDrawable(typedArray.getDrawable(R.styleable.CommonToolbar_toolbar_centertext_background_drawable));
            //
            if (typedArray.getDrawable(R.styleable.CommonToolbar_toolbar_righttext_drawable_right) != null) {
                Drawable rightDrawable = typedArray.getDrawable(R.styleable.CommonToolbar_toolbar_righttext_drawable_right);
                mRightTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable, null);
            }
            if (typedArray.getDrawable(R.styleable.CommonToolbar_toolbar_right_two_text_drawable_right) != null) {
                Drawable rightDrawable = typedArray.getDrawable(R.styleable.CommonToolbar_toolbar_right_two_text_drawable_right);
                mRightTwoTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable, null);
            }
            //
            if (typedArray.getDrawable(R.styleable.CommonToolbar_toolbar_line_background) != null)
                mLineView.setBackgroundDrawable(typedArray.getDrawable(R.styleable.CommonToolbar_toolbar_line_background));
            // 设置text显示隐藏
            mLeftTextView.setVisibility(typedArray.getInt(R.styleable.CommonToolbar_toolbar_lefttext_visibility, 0));
            mCenterTextView.setVisibility(typedArray.getInt(R.styleable.CommonToolbar_toolbar_centertext_visibility, 0));
            mRightTextView.setVisibility(typedArray.getInt(R.styleable.CommonToolbar_toolbar_righttext_visibility, 0));
            mRightTwoTextView.setVisibility(typedArray.getInt(R.styleable.CommonToolbar_toolbar_right_two_text_visibility, 0));
            mLineView.setVisibility(typedArray.getInt(R.styleable.CommonToolbar_toolbar_line_visibility, 0));
            typedArray.recycle();
        }

    }

    public ImageView getmCenterIv() {
        return mCenterIv;
    }

    public void setmCenterIv(ImageView mCenterIv) {
        this.mCenterIv = mCenterIv;
    }

    public TextView getLeftTextView() {
        return mLeftTextView;
    }

    public TextView getCenterTextView() {
        return mCenterTextView;
    }

    public TextView getRightTextView() {
        return mRightTextView;
    }

    public TextView getRightTwoTextView() {
        return mRightTwoTextView;
    }

    public void setLeftTextOnClickListener(OnClickListener onClickListener) {
        mLeftTextView.setOnClickListener(onClickListener);
    }

    public void setRightTextOnClickListener(OnClickListener onClickListener) {
        mRightTextView.setOnClickListener(onClickListener);
    }

    public void setRightTwoTextOnClickListener(OnClickListener onClickListener) {
        mRightTwoTextView.setOnClickListener(onClickListener);
    }

    public void setCenterTextOnClickListener(OnClickListener onClickListener) {
        mCenterTextView.setOnClickListener(onClickListener);
    }

    public void setAllText(String left, String center, String right) {
        setAllText(left, center, right, "");
    }

    public void setAllText(String left, String center, String right, String rightTwo) {
        mLeftTextView.setText(left);
        mCenterTextView.setText(center);
        mRightTextView.setText(right);
        mRightTwoTextView.setText(rightTwo);
    }

    /**
     * @param str
     */
    public void setLeftText(String str) {
        if (!TextUtils.isEmpty(str)) {
            getLeftTextView().setText(str);
        }
    }

    /**
     * 设置左边图片以及间距
     *
     * @param res
     */
    public void setLeftDrawable(@DrawableRes int res) {
        getLeftTextView().setCompoundDrawablesWithIntrinsicBounds(res, 0, 0, 0);
    }

    /**
     * @param paddingLeft
     */
    public void setLeftTextPadding(int paddingLeft) {
        if (paddingLeft != 0) {
            paddingLeft = ADisplayUtils.dp2px(getContext(), paddingLeft);
            getLeftTextView().setPadding(paddingLeft, 0, 0, 0);
        }
    }

    /**
     * @param textSize
     */
    public void setLeftTextSize(int textSize) {
        getLeftTextView().setTextSize(textSize);
    }

    /**
     * @param textColor
     */
    public void setLeftTextColor(int textColor) {
        getLeftTextView().setTextColor(textColor);
    }

    /**
     * 设置左边文字，padding
     *
     * @param str
     * @param paddingLeft
     */
    public void setLeftText(String str, int paddingLeft) {
        setLeftText(str);
        setLeftTextPadding(paddingLeft);
    }

    /**
     * 设置左边文字，padding，文字大小
     *
     * @param str
     * @param paddingLeft
     */
    public void setLeftText(String str, int paddingLeft, int textSize) {
        setLeftText(str);
        setLeftTextPadding(paddingLeft);
        setLeftTextSize(textSize);
    }

    /**
     * 设置左边文字，padding,文字颜色,文字大小
     *
     * @param str
     * @param paddingLeft
     * @param textColor   二进制颜色值
     */
    public void setLeftText(String str, int paddingLeft, int textSize, int textColor) {
        setLeftText(str);
        setLeftTextPadding(paddingLeft);
        setLeftTextSize(textSize);
        setLeftTextColor(textColor);
    }


    /**
     * 设置左边图片以及间距
     *
     * @param res
     * @param paddingLeft
     */
    public void setLeftDrawable(@DrawableRes int res, int paddingLeft) {
        if (res != 0) {
            getLeftTextView().setCompoundDrawablesWithIntrinsicBounds(res, 0, 0, 0);
            if (paddingLeft != 0) {
                paddingLeft = ADisplayUtils.dp2px(getContext(), paddingLeft);
            }
            getLeftTextView().setPadding(paddingLeft, 0, 0, 0);
        }
    }

    /**
     * @param res
     * @param drawablePadding
     */
    public void setLeftTextWithDrawable(@DrawableRes int res, int drawablePadding) {
        if (res != 0) {
            getLeftTextView().setCompoundDrawablesWithIntrinsicBounds(res, 0, 0, 0);
            if (drawablePadding != 0) {
                drawablePadding = ADisplayUtils.dp2px(getContext(), drawablePadding);
            }
            getLeftTextView().setCompoundDrawablePadding(drawablePadding);
        }
    }

    /**
     * 设置右边文字，padding
     *
     * @param str
     */
    public void setRightText(String str) {
        if (!TextUtils.isEmpty(str)) {
            getRightTextView().setText(str);
        }
    }

    /**
     * 设置右边文字，padding
     *
     * @param str
     */
    public void setRightTwoText(String str) {
        if (!TextUtils.isEmpty(str)) {
            getRightTwoTextView().setText(str);
        }
    }

    /**
     * 设置右边文字，padding
     *
     * @param str
     */
    public void setRightText(String str, int paddingRight) {
        if (!TextUtils.isEmpty(str)) {
            getRightTextView().setText(str);
            if (paddingRight != 0) {
                paddingRight = ADisplayUtils.dp2px(getContext(), paddingRight);
                getRightTextView().setPadding(0, 0, paddingRight, 0);
            }
        }
    }

    /**
     * 设置右边文字，padding
     *
     * @param str
     */
    public void setRightTwoText(String str, int paddingRight) {
        if (!TextUtils.isEmpty(str)) {
            getRightTwoTextView().setText(str);
            if (paddingRight != 0) {
                paddingRight = ADisplayUtils.dp2px(getContext(), paddingRight);
                getRightTwoTextView().setPadding(0, 0, paddingRight, 0);
            }
        }
    }

    /**
     * 设置右边文字，padding,文字颜色
     *
     * @param str
     * @param paddingRight
     * @param textSize
     */
    public void setRightText(String str, int paddingRight, int textSize) {
        setRightText(str, paddingRight);

        if (!TextUtils.isEmpty(str)) {
            if (textSize != 0) {
                getRightTextView().setTextSize(textSize);
            }
        }
    }

    /**
     * 设置右边文字，padding,文字颜色
     *
     * @param str
     * @param paddingRight
     * @param textSize
     */
    public void setRightTwoText(String str, int paddingRight, int textSize) {
        setRightText(str, paddingRight);

        if (!TextUtils.isEmpty(str)) {
            if (textSize != 0) {
                getRightTwoTextView().setTextSize(textSize);
            }
        }
    }

    /**
     * 设置右边文字，padding,文字颜色
     *
     * @param str
     * @param paddingRight
     * @param textSize
     */
    public void setRightText(String str, int paddingRight, int textSize, int textColor) {
        setRightText(str, paddingRight);

        if (!TextUtils.isEmpty(str)) {
            if (textSize != 0) {
                getRightTextView().setTextSize(textSize);
            }
            if (textColor != 0) {
                getRightTextView().setTextColor(textColor);
            }
        }
    }

    /**
     * 设置右边文字，padding,文字颜色
     *
     * @param str
     * @param paddingRight
     * @param textSize
     */
    public void setRightTwoText(String str, int paddingRight, int textSize, int textColor) {
        setRightText(str, paddingRight);

        if (!TextUtils.isEmpty(str)) {
            if (textSize != 0) {
                getRightTwoTextView().setTextSize(textSize);
            }
            if (textColor != 0) {
                getRightTwoTextView().setTextColor(textColor);
            }
        }
    }


    /**
     * 设置右边的图片
     *
     * @param res
     */
    public void setRightDrawable(@DrawableRes int res) {
        getRightTextView().setCompoundDrawablesWithIntrinsicBounds(0, 0, res, 0);
    }

    /**
     * 设置右边的图片
     *
     * @param res
     */
    public void setRightTwoDrawable(@DrawableRes int res) {
        getRightTwoTextView().setCompoundDrawablesWithIntrinsicBounds(0, 0, res, 0);
    }


    /**
     * 设置右边的图片
     *
     * @param res
     * @param paddingRight
     */
    public void setRightDrawable(@DrawableRes int res, int paddingRight) {
        getRightTextView().setCompoundDrawablesWithIntrinsicBounds(0, 0, res, 0);
        if (paddingRight != 0) {
            paddingRight = ADisplayUtils.dp2px(getContext(), paddingRight);
        }
        getRightTextView().setPadding(0, 0, paddingRight, 0);
    }

    /**
     * 设置右边的图片
     *
     * @param res
     * @param paddingRight
     */
    public void setRightTwoDrawable(@DrawableRes int res, int paddingRight) {
        getRightTwoTextView().setCompoundDrawablesWithIntrinsicBounds(0, 0, res, 0);
        if (paddingRight != 0) {
            paddingRight = ADisplayUtils.dp2px(getContext(), paddingRight);
        }
        getRightTwoTextView().setPadding(0, 0, paddingRight, 0);
    }

    public void setRightDrawablePadding(int drawablePadding) {
        getRightTextView().setCompoundDrawablePadding(ADisplayUtils.dp2px(getContext(), drawablePadding));
    }

    public void setRightTwoDrawablePadding(int drawablePadding) {
        getRightTwoTextView().setCompoundDrawablePadding(ADisplayUtils.dp2px(getContext(), drawablePadding));
    }

    public void setCenterText(String str) {
        if (!TextUtils.isEmpty(str)) {
            getCenterTextView().setText(str);
        }
    }

    public void setCenterText(String str, int textSize) {
        if (!TextUtils.isEmpty(str)) {
            getCenterTextView().setText(str);
            if (textSize != 0) {
                getCenterTextView().setTextSize(textSize);
            }
        }
    }

    public void setCenterText(String str, int textColor, int textSize) {
        if (!TextUtils.isEmpty(str)) {
            getCenterTextView().setText(str);
            if (textSize != 0) {
                getCenterTextView().setTextSize(textSize);
            }
            if (textColor != 0) {
                getCenterTextView().setTextColor(textColor);
            }
        }
    }
}
