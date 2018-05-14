package com.llj.lib.utils;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

/**
 * 设置亮色的文字点击效果
 * Created by liulj on 15/8/7.
 */
public class MultiActionTextViewUtil {
    private int                    mStartSpan;//可以点击的文字的开始位置
    private int                    mEndSpan;//可以点击的文字的结束位置
    private SpannableStringBuilder mSpannableStringBuilder;//设置的文字
    private int                    mTag;//给点击文字设置的tag
    private TextView               mTargetTextView;//设置文字的textview

    public int getmTag() {
        return mTag;
    }

    public void setmTag(int mTag) {
        this.mTag = mTag;
    }

    public static class Builder {
        private MultiActionTextViewUtil multiActionTextViewUtil;

        public Builder() {
            multiActionTextViewUtil = new MultiActionTextViewUtil();
        }

        public Builder setStartSpan(int startSpan) {
            multiActionTextViewUtil.mStartSpan = startSpan;
            return this;
        }

        public Builder setEndSpan(int endSpan) {
            multiActionTextViewUtil.mEndSpan = endSpan;
            return this;
        }

        public Builder setStringBuilder(SpannableStringBuilder spannableStringBuilder) {
            multiActionTextViewUtil.mSpannableStringBuilder = spannableStringBuilder;
            return this;
        }

        public Builder setTag(int tag) {
            multiActionTextViewUtil.mTag = tag;
            return this;
        }


        public Builder addActionOnTextViewIfWithLink(MultiActionTextViewClickableSpan multiActionTextViewClickableSpan) {
            checkNull();
            //外面实现该类后，再调用如下方法设置multiActionTextViewUtil为成员变量
            multiActionTextViewClickableSpan.setMultiActionTextViewUtil(multiActionTextViewUtil);
            //设置mSpannableStringBuilder上文字的点击监听
            multiActionTextViewUtil.mSpannableStringBuilder.setSpan(multiActionTextViewClickableSpan, multiActionTextViewUtil.mStartSpan, multiActionTextViewUtil.mEndSpan, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        public Builder setTargetTextView(TextView textView, int highLightTextColor) {
            multiActionTextViewUtil.mTargetTextView = textView;
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setText(multiActionTextViewUtil.mSpannableStringBuilder, TextView.BufferType.SPANNABLE);
            textView.setLinkTextColor(highLightTextColor);
            return this;
        }

        public MultiActionTextViewUtil build() {
            return multiActionTextViewUtil;
        }

        /**
         * 检验不为null的方法
         */
        private void checkNull() {
            if (multiActionTextViewUtil.mSpannableStringBuilder == null) {
                throw new IllegalArgumentException("multiActionTextViewUtil.mSpannableStringBuilder can not be null");
            }
            if (multiActionTextViewUtil.mStartSpan == 0) {
                throw new IllegalArgumentException("multiActionTextViewUtil.mStartSpan can not be 0");
            }
            if (multiActionTextViewUtil.mEndSpan == 0) {
                throw new IllegalArgumentException("multiActionTextViewUtil.mEndSpan can not be 0");
            }
        }
    }


    /**
     *
     */
    public static abstract class MultiActionTextViewClickableSpan extends ClickableSpan {

        private boolean                 isUnderLineRequired;
        private MultiActionTextViewUtil multiActionTextViewUtil;

        public MultiActionTextViewClickableSpan(boolean isUnderLineRequired) {
            this.isUnderLineRequired = isUnderLineRequired;
        }

        public void setMultiActionTextViewUtil(MultiActionTextViewUtil multiActionTextViewUtil) {
            this.multiActionTextViewUtil = multiActionTextViewUtil;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(ds.linkColor);
            ds.setUnderlineText(isUnderLineRequired);
        }

        /**
         * 点击的时候默认会回调该方法
         *
         * @param widget
         */
        @Override
        public void onClick(View widget) {
            onTextClick(multiActionTextViewUtil);
        }

        /**
         * 外面需要实现的方法
         *
         * @param multiActionTextViewUtil
         */
        public abstract void onTextClick(MultiActionTextViewUtil multiActionTextViewUtil);
    }
}
