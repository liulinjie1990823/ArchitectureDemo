package com.llj.lib.utils;

import java.util.regex.Pattern;

/**
 * Created by liulj on 16/4/18.
 */
public class ARegexUtils {

    /**
     * Regex of simple mobile.
     */
    public static final String REGEX_MOBILE_SIMPLE = "^[1]\\d{10}$";

    /**
     * 匹配手机号码的正则表达式
     * <br>支持130——139、150——153、155——159、180、183、185、186、188、189号段
     */
    private static final String REGEX_MOBILE_EXACT = "^[1][1,2,3,4,5,6,7,8,9][0-9]{9}$";

    /**
     * Regex of telephone number.
     */
    public static final String REGEX_TEL = "^0\\d{2,3}[- ]?\\d{7,8}";

    /**
     * Regex of id card number which length is 15.
     */
    public static final  String REGEX_ID_CARD15      = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
    /**
     * Regex of id card number which length is 18.
     */
    public static final  String REGEX_ID_CARD18      = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";
    /**
     * Regex of email.
     */
    public static final  String REGEX_EMAIL          = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    /**
     * Regex of url.
     */
    public static final  String REGEX_URL            = "[a-zA-z]+://[^\\s]*";
    /**
     * Regex of Chinese character.
     */
    public static final  String REGEX_ZH             = "^[\\u4e00-\\u9fa5]+$";
    /**
     * Regex of ip address.
     */
    public static final  String REGEX_IP             = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
    /**
     * 数字或字母
     */
    private static final String REGEX_NUMBER_OR_WORD = "^[A-Za-z0-9\\-]+$";

    /**
     * 匹配邮编的正则表达式
     */
    private static final String REGEX_ZIP_CODE = "^\\d{6}$";

    /**
     * Regex of double-byte characters.
     */
    public static final String REGEX_DOUBLE_BYTE_CHAR     = "[^\\x00-\\xff]";
    /**
     * Regex of blank line.
     */
    public static final String REGEX_BLANK_LINE           = "\\n\\s*\\r";
    /**
     * Regex of QQ number.
     */
    public static final String REGEX_QQ_NUM               = "[1-9][0-9]{4,}";
    /**
     * Regex of postal code in China.
     */
    public static final String REGEX_CHINA_POSTAL_CODE    = "[1-9]\\d{5}(?!\\d)";
    /**
     * Regex of positive integer.
     */
    public static final String REGEX_POSITIVE_INTEGER     = "^[1-9]\\d*$";
    /**
     * Regex of negative integer.
     */
    public static final String REGEX_NEGATIVE_INTEGER     = "^-[1-9]\\d*$";
    /**
     * Regex of integer.
     */
    public static final String REGEX_INTEGER              = "^-?[1-9]\\d*$";
    /**
     * Regex of non-negative integer.
     */
    public static final String REGEX_NOT_NEGATIVE_INTEGER = "^[1-9]\\d*|0$";
    /**
     * Regex of non-positive integer.
     */
    public static final String REGEX_NOT_POSITIVE_INTEGER = "^-[1-9]\\d*|0$";
    /**
     * Regex of positive float.
     */
    public static final String REGEX_POSITIVE_FLOAT       = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";
    /**
     * Regex of negative float.
     */
    public static final String REGEX_NEGATIVE_FLOAT       = "^-[1-9]\\d*\\.\\d*|-0\\.\\d*[1-9]\\d*$";

    public static boolean isEmail(String string) {
        return isMatch(REGEX_EMAIL, string);
    }

    public static boolean isMobileSimple(String string) {
        return isMatch(REGEX_MOBILE_SIMPLE, string);
    }

    public static boolean isMobileExact(String string) {
        return isMatch(REGEX_MOBILE_EXACT, string);
    }


    public static boolean isMobileOrEmail(String string) {
        return isMobileSimple(string) || isEmail(string);
    }

    public static boolean isTel(final CharSequence input) {
        return isMatch(REGEX_TEL, input);
    }

    public static boolean isIDCard15(final CharSequence input) {
        return isMatch(REGEX_ID_CARD15, input);
    }

    public static boolean isIDCard18(final CharSequence input) {
        return isMatch(REGEX_ID_CARD18, input);
    }

    public static boolean isURL(final CharSequence input) {
        return isMatch(REGEX_URL, input);
    }

    public static boolean isZh(final CharSequence input) {
        return isMatch(REGEX_ZH, input);
    }

    public static boolean isIP(final CharSequence input) {
        return isMatch(REGEX_IP, input);
    }

    public static boolean isPositiveInteger(CharSequence input) {
        return isMatch(REGEX_POSITIVE_INTEGER, input);
    }

    public static boolean isZipCode(CharSequence input) {
        return isMatch(REGEX_ZIP_CODE, input);
    }

    public static boolean isNumberOrWord(CharSequence input) {
        return isMatch(REGEX_NUMBER_OR_WORD, input);
    }

    /**
     * Return whether input matches the regex.
     *
     * @param regex The regex.
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    private static boolean isMatch(final String regex, final CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }
}
