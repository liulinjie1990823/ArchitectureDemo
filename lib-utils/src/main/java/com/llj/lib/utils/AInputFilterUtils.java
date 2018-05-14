package com.llj.lib.utils;

import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;

/**
 * project:babyphoto_app
 * describe:
 * Created by llj on 2017/7/21.
 */

public class AInputFilterUtils {


    public static InputFilter getTwoPointFilter() {
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String destString = dest.toString();
                int posDot = destString.indexOf(".");
                if (posDot <= 0) {
                    return source;
                }
                if (destString.length() - posDot > 2) {
                    return "";
                } else {
                    return source;
                }
            }
        };
    }


    public static InputFilter getLengthFilter(int wordNum) {
        return new InputFilter.LengthFilter(wordNum);
    }

    //过滤表情字符
    public static InputFilter getEmojiFilter() {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                StringBuffer buffer = new StringBuffer();
                for (int i = start; i < end; i++) {
                    char codePoint = source.charAt(i);
                    if (!AEmojiUtils.isEmojiCharacter(codePoint)) {
                        buffer.append(codePoint);
                    } else {
                        i++;
                        continue;
                    }
                }
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(buffer);
                    TextUtils.copySpansFrom((Spanned) source, start, end, null, sp, 0);
                    return sp;
                } else {
                    return buffer;
                }
            }
        };

        return filter;
    }
}
