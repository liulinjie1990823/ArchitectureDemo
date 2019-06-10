package com.llj.lib.utils;

import android.text.InputFilter;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;

/**
 * project:babyphoto_app
 * describe:
 * Created by llj on 2017/7/21.
 */

public class AInputFilterUtils {

    public static InputFilter getLengthFilter(int wordNum) {
        return new InputFilter.LengthFilter(wordNum);
    }

    public static InputFilter getDecimalFilter(final int digits) {
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                // 删除等特殊字符，直接返回
                if ("".equals(source.toString())) {
                    return null;
                }
                String dValue = dest.toString();
                String[] splitArray = dValue.split("\\.");
                if (splitArray.length > 1) {
                    String dotValue = splitArray[1];
                    int diff = dotValue.length() + 1 - digits;
                    if (diff > 0) {
                        return source.subSequence(start, end - diff);
                    }
                }
                return null;
            }
        };
    }


    public static InputFilter getEmojiFilter() {
        return new InputFilter() {
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
    }

    public static InputFilter getWnrCharFilter() {
        return CharFilter.wnrCharFilter();
    }


    public static class CharFilter implements InputFilter {

        private final char[] filterChars;

        public static CharFilter newlineCharFilter() {
            return new CharFilter(new char[]{'\n'});
        }

        public static CharFilter whitespaceCharFilter() {
            return new CharFilter(new char[]{' '});
        }

        public static CharFilter returnCharFilter() {
            return new CharFilter(new char[]{'\r'});
        }

        public static CharFilter wnrCharFilter() {
            return new CharFilter(new char[]{' ', '\n', '\r'});
        }

        private CharFilter(char[] filterChars) {
            this.filterChars = filterChars == null ? new char[0] : filterChars;
        }

        /**
         * @param source 输入的文字
         * @param start  输入-0，删除-0
         * @param end    输入-文字的长度，删除-0
         * @param dest   原先显示的内容
         * @param dstart 输入-原光标位置，删除-光标删除结束位置
         * @param dend   输入-原光标位置，删除-光标删除开始位置
         *
         * @return null表示原始输入，""表示不接受输入，其他字符串表示变化值
         */
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (needFilter(source)) {
                SpannableStringBuilder builder = new SpannableStringBuilder();
                int abStart = start;
                for (int i = start; i < end; i++) {
                    if (isFilterChar(source.charAt(i))) {
                        if (i != abStart) {
                            builder.append(source.subSequence(abStart, i));
                        }
                        abStart = i + 1;
                    }
                }

                if (abStart < end) {
                    builder.append(source.subSequence(abStart, end));
                }
                return builder;
            }

            return null;
        }

        private boolean needFilter(CharSequence source) {
            String s = source.toString();
            for (char filterChar : filterChars) {
                if (s.indexOf(filterChar) >= 0) {
                    return true;
                }
            }
            return false;
        }

        private boolean isFilterChar(char c) {
            for (char filterChar : filterChars) {
                if (filterChar == c) {
                    return true;
                }
            }
            return false;
        }
    }
}
