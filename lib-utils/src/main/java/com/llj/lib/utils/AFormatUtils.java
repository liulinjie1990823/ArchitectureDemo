package com.llj.lib.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.format.Formatter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * <p>
 * <li>
 * BigDecimal.ROUND_HALF_UP,四舍五入，一般用这个</li>
 * <li>
 * BigDecimal.ROUND_HALF_DOWN,四舍五舍</li>
 * <li>
 * BigDecimal.ROUND_UP,舍入远离零的舍入模式。在丢弃非零部分之前始终增加数字(始终对非零舍弃部分前面的数字加1)。注意,
 * 此舍入模式始终不会减少计算值的大小。</li>
 * <li>
 * BigDecimal.ROUND_DOWN,接近零的舍入模式。在丢弃某部分之前始终不增加数字(从不对舍弃部分前面的数字加1,即截短)。注意,
 * 此舍入模式始终不会增加计算值的大小。</li>
 * <li>
 * BigDecimal.ROUND_CEILING,接近正无穷大的舍入模式。如果 BigDecimal 为正,则舍入行为与 ROUND_UP
 * 相同;如果为负,则舍入行为与 ROUND_DOWN 相同。注意,此舍入模式始终不会减少计算值。</li>
 * <li>
 * BigDecimal.ROUND_FLOOR,接近负无穷大的舍入模式。如果 BigDecimal 为正,则舍入行为与 ROUND_DOWN
 * 相同;如果为负,则舍入行为与 ROUND_UP 相同。注意,此舍入模式始终不会增加计算值。</li>
 * </p>
 *
 * @author liulj
 */
public class AFormatUtils {
//    /**
//     * 设置四舍五入，并保留小数位数
//     *
//     * @param str       需要转换的数字
//     * @param remainNum 需要保留的位数
//     *                  <p>
//     *                  00204.5455353保留两位是204.55
//     *                  </p>
//     * @return 转换后的字符串，小数左边多余的0会自动去掉
//     */
//    public static String formatHalfUp(long needFormat, int remainNum) {
//        BigDecimal bigDecimal = new BigDecimal(needFormat);
//        return bigDecimal.setScale(remainNum, BigDecimal.ROUND_HALF_UP).toPlainString();
//
//    }

    /**
     * 设置四舍五入，并保留小数位数
     * 00204.5455353保留两位是204.55
     *
     * @param needFormat 需要操作的小数
     * @param remainNum  需要保留的位数
     * @return 转换后的字符串，小数左边多余的0会自动去掉
     */
    public static String formatHalfUp(double needFormat, int remainNum) {
        BigDecimal bigDecimal = new BigDecimal(needFormat);
        return bigDecimal.setScale(remainNum, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    /**
     * 远离0
     *
     * @param needFormat
     * @param remainNum
     * @return
     */
    public static String formatRoundUp(double needFormat, int remainNum) {
        BigDecimal bigDecimal = new BigDecimal(needFormat);
        return bigDecimal.setScale(remainNum, BigDecimal.ROUND_UP).toPlainString();
    }

    /**
     * 远离0
     *
     * @param needFormat
     * @param remainNum
     * @return
     */
    public static String formatRoundDown(double needFormat, int remainNum) {
        BigDecimal bigDecimal = new BigDecimal(needFormat);
        return bigDecimal.setScale(remainNum, BigDecimal.ROUND_DOWN).toPlainString();
    }

    /**
     * 默认五舍六入(如果需要四舍五入,需要设置setRoundingMode(RoundingMode.HALF_UP); )
     * #.##(可以确保小数点左边不存在多余的0)1.126->1.13 1.100->1.1
     * 00.00(小数点后面至少有两位)1.126->01.13
     * 000000.50823f使用默认转换后是0.51
     *
     * @param formatType
     * @param data
     * @return
     */
    public static String decimalFormat(int formatType, double data) {
        String pattern;
        switch (formatType) {
            case 0:
                pattern = "#.##";
                break;
            case 1:
                pattern = "00.00";
                break;
            case 2:
                pattern = "#.#";
                break;
            case 3:
                pattern = "0.0";
                break;
            case 4:
                pattern = "0.##";
                break;
            case 5:
                pattern = "#.00";
                break;
            default:
                pattern = "0.0";
                break;
        }
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(data);
    }

    /**
     * 默认保留两位小数
     *
     * @param data
     * @return
     */
    public static String decimalFormat(double data) {
        return decimalFormat(0, data);
    }

    /**
     * 1.格式化double类型的数据，无四舍五入
     *
     * @param pattern 格式如00.00，##.00，区别是在格式位数多余的情况下##将会使多余的位数空着，00将会使多余的位数补上0。
     *                如果number位数多，将会无条件舍去
     * @param number  要转换的数字
     * @return 对应格式的字符串
     */
    public static String decimalFormat(String pattern, double number) {
        DecimalFormat df1 = new DecimalFormat(pattern);
        return df1.format(number);
    }

    /**
     * 2.格式化double类型的数据，无四舍五入
     *
     * @param pattern 格式如00.00，##.00，区别是在格式位数多余的情况下##将会使多余的位数空着，00将会使多余的位数补上0。
     *                如果number位数多，将会无条件舍去
     * @param number  要转换的数字
     * @return 对应格式的字符串
     */
    public static String decimalFormat(String pattern, long number) {
        DecimalFormat df1 = new DecimalFormat(pattern);
        return df1.format(number);
    }

    /**
     * 使用java正则表达式去掉小数点后面多余的.与0
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");// 去掉多余的0
            s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
        }
        return s;
    }

    /**
     * 将double类型数据转换为百分比格式，并保留小数点前IntegerDigits位和小数点后FractionDigits位
     *
     * @param data
     * @param integerDigits
     * @param fractionDigits
     * @return
     */
    public static String getPercentFormat(double data, int integerDigits, int fractionDigits) {
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumIntegerDigits(integerDigits);// 小数点前保留几位
        nf.setMinimumFractionDigits(fractionDigits);// 小数点后保留几位
        String str = nf.format(data);
        return str;
    }


    public void formatFileSize(@Nullable Context context, long sizeBytes) {
        Formatter.formatFileSize(context, sizeBytes);
    }


}
