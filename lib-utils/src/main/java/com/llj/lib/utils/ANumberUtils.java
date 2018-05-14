package com.llj.lib.utils;

import android.text.TextUtils;

/**
 * Created by liulj on 16/8/26.
 */

public class ANumberUtils {
    /**
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (TextUtils.isEmpty(str))
            return false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param month
     * @return
     */
    public static String add0WhenLessThan10(int month) {
        if (month < 10 && month > 0) {
            return "0" + month;
        }
        return "" + month;
    }

    public static boolean isEmpty(Long number) {
        return number == null || number == 0;
    }

    /**
     * 是否是正数
     *
     * @return
     */
    public static boolean isPositiveNum(Long number) {
        return number != null && number > 0;
    }

    public static boolean isPositiveNum(Integer number) {
        return number != null && number > 0;
    }

    public static boolean isEmpty(Integer number) {
        return number == null || number == 0;
    }

    public static boolean isEmpty(Short number) {
        return number == null || number == 0;
    }

    public static boolean isEqual(Long number1, Long number2) {
        if (number1 != null && number2 != null && number1.longValue() == number2.longValue()) {
            return true;
        }
        return false;
    }

    /**
     * 经纬度的double数据转成int型
     * 最多保留小数点后6位
     *
     * @param doubleNum 经纬度
     * @return
     */
    public static Integer doubleStringToInteger(String doubleNum) {
        if (doubleNum == null || doubleNum.length() == 0) {
            return null;
        }

        try {
            String[] nums = doubleNum.split("\\.");
            if (nums.length == 1) {
                return Integer.parseInt(nums[0]);
            } else {
                return (int) (Double.parseDouble(doubleNum) * (Math.pow(10, 6)));
            }
        } catch (Exception e) {
            return null;
        }
    }


    public static int wrapInteger(Integer data) {
        return data == null ? 0 : data;
    }
}
