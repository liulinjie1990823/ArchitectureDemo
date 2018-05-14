package com.llj.lib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.text.format.DateFormat;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 日期转换工具类milliseconds->date->str->date->milliseconds;date->calendar
 *
 * @author llj 2014/7/12
 */
public class ATimeUtils {

    public static final String FORMAT_ZERO       = "yyyy:MM:dd HH:mm:ss";
    public static final String FORMAT_ONE        = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String FORMAT_TWO        = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_THREE      = "yyyy/MM/dd HH:mm:ss";
    public static final String FORMAT_FOUR       = "yyyy年MM月dd日 HH时mm分ss秒 E";
    public static final String FORMAT_FIVE       = "yyyy/MM/dd E";
    public static final String FORMAT_SIX        = "yyyy/MM/dd";
    public static final String FORMAT_SEVEN      = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_EIGHT      = "yyyy年MM月dd日";
    public static final String FORMAT_NINE       = "yyyy-MM-dd";
    public static final String FORMAT_TEN        = "mm:ss";
    public static final String FORMAT_CLEVEN     = "yy/MM/dd HH:mm:ss";
    public static final String FORMAT_TWELVE     = "yy/MM/dd";
    public static final String FORMAT_THIRTEEN   = "HH:mm";
    public static final String FORMAT_FOURTEEN    = "yy/MM/dd HH:mm";
    public static final String FORMAT_FIFTEEN     = "HH:mm:ss";
    public static final String FORMAT_SIZTEEN     = "yyyyMMdd";
    public static final String FORMAT_SEVENTEEN   = "yyyy年MM月";
    public static final String FORMAT_EIGHTEEN    = "yyyy.MM.dd";
    public static final String FORMAT_NINETEEN    = "MM月dd日 HH:mm";
    public static final String FORMAT_TWENTY      = "yyyy:M:d HH:mm:ss";
    public static final String FORMAT_TWENTYONE   = "yyyy年M月";
    public static final String FORMAT_TWENTYTWO   = "yyyy/MM/dd HH:mm";
    public static final String FORMAT_TWENTYTHREE = "MM月dd日";
    /**
     * Number of milliseconds in a standard second.
     */
    public static final long   MILLIS_PER_SECOND  = 1000;
    /**
     * Number of milliseconds in a standard minute.
     */
    public static final long   MILLIS_PER_MINUTE  = 60 * MILLIS_PER_SECOND;
    /**
     * Number of milliseconds in a standard hour.
     */
    public static final long   MILLIS_PER_HOUR    = 60 * MILLIS_PER_MINUTE;
    /**
     * Number of milliseconds in a standard day.
     */
    public static final long   MILLIS_PER_DAY     = 24 * MILLIS_PER_HOUR;

    public static final long TIME_LESS_THAN_TIME_STAMP = -28800001;

    public static final long DOUBLE_CURRENT_TIME_MILLS = System.currentTimeMillis() * 2;

    public static final long TIME_MINIMAL_TIME_STAMP = -28800000;

    /**
     * 在给定的时间上加上一天
     * ideoL
     */
    public static Calendar addCalendarDay(long mill) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mill);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar;
    }

    /**
     * 在给定的时间上加上一个月
     *
     * @param mill
     * @return
     */
    public static Calendar addCalendarMonth(long mill) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mill);
        calendar.add(Calendar.MONTH, 1);
        return calendar;
    }

    /**
     * 在给定的时间上加上一年
     *
     * @param mill
     * @return
     */
    public static Calendar addCalendarYear(long mill) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mill);
        calendar.add(Calendar.YEAR, 1);
        return calendar;
    }

    public static long getTodayLastMills(Long mill) {
        return ATimeUtils.addCalendarDay(ATimeUtils.removeHms(mill)).getTimeInMillis() - 1;
    }

    public static long getLastDayLastMills(Long mill) {
        return removeHms(mill) - 1;
    }

    public static long getTodayFirstMills(Long mill) {
        return removeHms(mill);
    }

    /**
     * 去掉当天时间的时分秒的毫秒值，相当于回到0点
     *
     * @param mill
     * @return
     */
    public static long removeHms(Long mill) {
        if (mill == null) {
            return 0L;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
        String dateStr = simpleDateFormat.format(mill);

        simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
        try {
            return simpleDateFormat.parse(dateStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mill;
    }

    public static boolean isSameDay(final Date date1, final Date date2) {
        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(getCalendar(date1), getCalendar(date2));
    }

    public static boolean isSameDay(final Long date1, final Long date2) {
        return isSameDay(getCalendar(date1), getCalendar(date2));
    }

    private static Calendar getCalendar(Long mills) {
        final Calendar calendar = Calendar.getInstance();
        if (!ANumberUtils.isPositiveNum(mills))
            calendar.setTime(new Date(0));
        else
            calendar.setTime(new Date(mills));
        return calendar;
    }

    private static Calendar getCalendar(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        else
            calendar.setTime(new Date(0));
        return calendar;
    }

    public static boolean isSameDay(final Calendar cal1, final Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    public static boolean isSameMonth(final Date date1, final Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameMonth(cal1, cal2);
    }

    public static boolean isSameMonth(final Calendar cal1, final Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH));
    }

    public static boolean isSameLocalTime(final Calendar cal1, final Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return (cal1.get(Calendar.MILLISECOND) == cal2.get(Calendar.MILLISECOND) &&
                cal1.get(Calendar.SECOND) == cal2.get(Calendar.SECOND) &&
                cal1.get(Calendar.MINUTE) == cal2.get(Calendar.MINUTE) &&
                cal1.get(Calendar.HOUR_OF_DAY) == cal2.get(Calendar.HOUR_OF_DAY) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.getClass() == cal2.getClass());
    }

    public static SimpleCalendar getSimpleCalendar(Calendar calendar, long millis) {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        calendar.setTimeInMillis(millis);
        return new SimpleCalendar(millis, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));

    }

    public static int getDiffMonth(Calendar big, Calendar small) {
        if (big == null || small == null) {
            return 0;
        }
        int bigYear = big.get(Calendar.YEAR);
        int bigMonth = big.get(Calendar.MONTH);
        int smallYear = small.get(Calendar.YEAR);
        int smallMonth = small.get(Calendar.MONTH);

        return Math.abs(bigYear * 12 + bigMonth - (smallYear * 12 + smallMonth));
    }

    public static SimpleCalendar getDiffData(Calendar big, Calendar small) {
        if (big == null || small == null) {
            return null;
        }
        SimpleCalendar simpleCalendar = new SimpleCalendar();
        Calendar trueBig;
        Calendar trueSmall;
        if (big.getTimeInMillis() > small.getTimeInMillis()) {
            trueBig = big;
            trueSmall = small;
        } else {
            trueBig = small;
            trueSmall = big;
        }

        int bigYear = trueBig.get(Calendar.YEAR);
        int bigMonth = trueBig.get(Calendar.MONTH);
        int bigDay = trueBig.get(Calendar.DAY_OF_MONTH);
        int bigHour = trueBig.get(Calendar.HOUR_OF_DAY);
        int bigMinute = trueBig.get(Calendar.MINUTE);
        int bigSecond = trueBig.get(Calendar.SECOND);
        int bigMill = trueBig.get(Calendar.MILLISECOND);

        int smallYear = trueSmall.get(Calendar.YEAR);
        int smallMonth = trueSmall.get(Calendar.MONTH);
        int smallDay = trueSmall.get(Calendar.DAY_OF_MONTH);
        int smallHour = trueSmall.get(Calendar.HOUR_OF_DAY);
        int smallMinute = trueSmall.get(Calendar.MINUTE);
        int smallSecond = trueSmall.get(Calendar.SECOND);
        int smallMill = trueSmall.get(Calendar.MILLISECOND);


        int diffMill;
        if (bigMill > smallMill) {
            diffMill = bigMill - smallMill;
        } else {
            if (bigSecond < 1) {
                if (bigMinute < 1) {
                    if (bigHour < 1) {
                        bigDay = bigDay - 1;
                        bigHour = bigHour + 24 - 1;
                    } else {
                        bigHour = bigHour - 1;
                    }
                    bigMinute = bigMinute + 60 - 1;
                } else {
                    bigMinute = bigMinute - 1;
                }
                bigSecond = bigSecond + 60 - 1;
            } else {
                bigSecond = bigSecond - 1;
            }

            diffMill = bigMill + 1000 - smallMill;
        }
        simpleCalendar.millSecond = diffMill;
        //
        int diffSecond;
        if (bigSecond > smallSecond) {
            diffSecond = bigSecond - smallSecond;
        } else {
            if (bigMinute < 1) {
                if (bigHour < 1) {
                    bigDay = bigDay - 1;
                    bigHour = bigHour + 24 - 1;
                } else {
                    bigHour = bigHour - 1;
                }
                bigMinute = bigMinute + 60 - 1;
            } else {
                bigMinute = bigMinute - 1;
            }
            diffSecond = bigSecond + 60 - smallSecond;
        }
        simpleCalendar.second = diffSecond;
        //
        int diffMinute;
        if (bigMinute > smallMinute) {
            diffMinute = bigMinute - smallMinute;
        } else {
            if (bigHour < 1) {
                bigDay = bigDay - 1;
                bigHour = bigHour + 24 - 1;
            } else {
                bigHour = bigHour - 1;
            }
            diffMinute = bigMinute + 60 - smallMinute;
        }
        simpleCalendar.minute = diffMinute;
        //
        int diffHour;
        if (bigHour > smallHour) {
            diffHour = bigHour - smallHour;
        } else {
            bigDay = bigDay - 1;
            diffHour = bigHour + 24 - smallHour;
        }
        simpleCalendar.hour = diffHour;


        int diffYear;
        if (bigMonth > smallMonth) {
            diffYear = bigYear - smallYear;
        } else {
            diffYear = bigYear - smallYear - 1;
        }
        simpleCalendar.year = diffYear;

        //月
        int diffMonth;
        if (bigMonth > smallMonth) {
            diffMonth = bigYear * 12 + bigMonth - (smallYear * 12 + smallMonth);
        } else {
            diffMonth = bigYear * 12 + bigMonth - (smallYear * 12 + smallMonth) - 1;
        }
        simpleCalendar.month = diffMonth;

        trueSmall.add(Calendar.MONTH, diffMonth);


        //天
        int diffDay;
        if (bigDay > smallDay) {
            diffDay = bigDay - smallDay;
        } else {
            int day = trueSmall.getActualMaximum(Calendar.DATE);
            diffDay = day + bigDay - smallDay;
        }
        simpleCalendar.day = diffDay;

        return simpleCalendar;
    }

    /**
     * 1.Date转换为Calendar
     *
     * @param date
     * @return calendar
     */
    public static Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * @param milliseconds
     * @return
     */
    public static Calendar millToCalendar(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return calendar;
    }

    /**
     * 2.将当前Date转换成指定格式的字符串
     *
     * @param format 1："yyyy年MM月dd日 HH时mm分ss秒" 2:"yyyy-MM-dd HH:mm:ss"
     *               3:"yyyy/MM/dd HH:mm:ss" 4:"yyyy年MM月dd日 HH时mm分ss秒 E "
     *               5:"yyyy/MM/dd E" 6."yyyy/MM/dd"
     * @return dateStr 指定格式字符串
     */
    public static String getCurrentTimeString(String format) {
        return dateToString(format, new Date());
    }

    /**
     * 3.获取指定时间与当前时间毫秒数差的绝对值
     *
     * @param milliseconds 指定毫秒数
     * @return 毫秒数绝对值, 是毫秒，不是秒
     */
    public static Long getDifferMilliseconds(long milliseconds) {
        Long currentMilliseconds = new Date().getTime();
        Long differMilliseconds = milliseconds - currentMilliseconds;
        return Math.abs(differMilliseconds);
    }

    /**
     * 4.获取返回时间于当前时间对比后的值
     *
     * @param date 获取的时间
     * @return 返回时间于当前时间对比后的值
     */
    public static final String getTimeDiff(Date date) {
        Calendar currentDate = Calendar.getInstance();// 获取当前时间

        long diff = currentDate.getTimeInMillis() - date.getTime();
        if (diff < 0)
            return 0 + "秒钟前";
        else if (diff < 60000)
            return diff / 1000 + "秒钟前";
        else if (diff < 3600000)
            return diff / 60000 + "分钟前";
        else if (diff < 86400000)
            return diff / 3600000 + "小时前";
        else {
            String newDate = DateFormat.format("yyyy-MM-dd kk:mm", date).toString();
            String year = currentDate.get(Calendar.YEAR) + "";// 获取当前年份
            if (newDate.contains(year)) {
                //同一年的,只显示月,日,时,分
                return newDate.substring(5);
            } else {
                //不同年的显示年月日时分秒
                return newDate;
            }
        }
    }

    /**
     * 5. 显示秒前，分前，小时前，天前，周前，一个月前，二个月前，三个月前，或者（自定义格式的年月日时分秒）
     *
     * @param beforeTimeMill
     * @param format
     * @return
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("SimpleDateFormat")
    public static String getTimeString(Long beforeTimeMill, int format) {
        Date currentDate = new Date();
        Date beforeDate = new Date(beforeTimeMill);
        if (currentDate.getYear() == beforeDate.getYear()) {
            //同一年
            int month = currentDate.getMonth() - beforeDate.getMonth();
            if (month > 3 || month < 0) {
                // 三个月后就显示年月日，时分秒
                return millisecondsToString(format, beforeTimeMill);
            } else {
                //三个月内的
                //计算日的相差
                int day = currentDate.getDate() - beforeDate.getDate();
                if (month == 3) {
                    if (day >= 0) {
                        return "三个月前";
                    } else {
                        return "二个月前";
                    }
                } else if (month == 2) {
                    if (day >= 0) {
                        return "二个月前";
                    } else {
                        return "一个月前";
                    }
                } else if (month == 1) {
                    if (day >= 0) {
                        return "一个月前";
                    } else {
                        //一个月内
                        return getDateString(currentDate.getTime() - beforeTimeMill);
                    }
                } else if (month == 0) {
                    return getDateString(currentDate.getTime() - beforeTimeMill);
                }
            }
        }
        //不同年
        return millisecondsToString(format, beforeTimeMill);
    }

    private static String getDateString(long time) {
        long days = time / (1000 * 60 * 60 * 24);
        if (days >= 7) {
            return days / 7 + "周前";
        } else {
            if (days > 0) {
                return days + "天前";
            } else {
                long hours = time / (1000 * 60 * 60);
                if (hours > 0) {
                    return hours + "小时前";
                } else {
                    long minutes = time / (1000 * 60);
                    if (minutes > 0) {
                        return minutes + "分钟前";
                    } else {
                        long seconds = time / 1000 - minutes * 60;
                        if (seconds <= 0) {
                            seconds = 10;
                        }
                        return seconds + "秒钟前";
                    }
                }
            }
        }
    }

    public static int[] getNeturalAge(Calendar calendarBirth, Calendar calendarNow) {
        int diffYears = 0, diffMonths, diffDays;
        int dayOfBirth = calendarBirth.get(Calendar.DAY_OF_MONTH);
        int dayOfNow = calendarNow.get(Calendar.DAY_OF_MONTH);
        if (dayOfBirth <= dayOfNow) {
            diffMonths = getMonthsOfAge(calendarBirth, calendarNow);
            diffDays = dayOfNow - dayOfBirth;
            if (diffMonths == 0)
                diffDays++;
        } else {
            if (isEndOfMonth(calendarBirth)) {
                if (isEndOfMonth(calendarNow)) {
                    diffMonths = getMonthsOfAge(calendarBirth, calendarNow);
                    diffDays = 0;
                } else {
                    calendarNow.add(Calendar.MONTH, -1);
                    diffMonths = getMonthsOfAge(calendarBirth, calendarNow);
                    diffDays = dayOfNow + 1;
                }
            } else {
                if (isEndOfMonth(calendarNow)) {
                    diffMonths = getMonthsOfAge(calendarBirth, calendarNow);
                    diffDays = 0;
                } else {
                    calendarNow.add(Calendar.MONTH, -1);// 上个月
                    diffMonths = getMonthsOfAge(calendarBirth, calendarNow);
                    // 获取上个月最大的一天
                    int maxDayOfLastMonth = calendarNow.getActualMaximum(Calendar.DAY_OF_MONTH);
                    if (maxDayOfLastMonth > dayOfBirth) {
                        diffDays = maxDayOfLastMonth - dayOfBirth + dayOfNow;
                    } else {
                        diffDays = dayOfNow;
                    }
                }
            }
        }
        // 计算月份时，没有考虑年
        diffYears = diffMonths / 12;
        diffMonths = diffMonths % 12;
        return new int[]{diffYears, diffMonths, diffDays};
    }

    /**
     * 获取两个日历的月份之差
     *
     * @param calendarBirth
     * @param calendarNow
     * @return
     */
    public static int getMonthsOfAge(Calendar calendarBirth, Calendar calendarNow) {
        return (calendarNow.get(Calendar.YEAR) - calendarBirth
                .get(Calendar.YEAR)) * 12 + calendarNow.get(Calendar.MONTH)
                - calendarBirth.get(Calendar.MONTH);
    }

    /**
     * 判断这一天是否是月底
     *
     * @param calendar
     * @return
     */
    public static boolean isEndOfMonth(Calendar calendar) {
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        if (dayOfMonth == calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
            return true;
        return false;
    }

    /**
     * @param minDate
     * @param maxDate
     * @return
     * @throws ParseException
     */
    public static ArrayMap<String, List<String>> getMonthBetween(long minDate, long maxDate) throws ParseException {
        ArrayMap<String, List<String>> yearMap = new ArrayMap<>();

        Calendar min = Calendar.getInstance();
        min.setTime(new Date(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
        Calendar max = Calendar.getInstance();
        max.setTime(new Date(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = max;
        while (curr.after(min)) {
            String year = curr.get(Calendar.YEAR) + "年";
            String month = year + (curr.get(Calendar.MONTH) + 1) + "月";


            if (!yearMap.containsKey(year)) {
                List<String> monthList = new ArrayList<>();
                monthList.add(month);
                yearMap.put(year, monthList);
            } else {
                if (!yearMap.get(year).contains(month))
                    yearMap.get(year).add(month);
            }
            //加一个月
            curr.add(Calendar.MONTH, -1);
        }
        return yearMap;
    }

    /**
     * 6.
     *
     * @param context
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    @SuppressWarnings("deprecation")
    public static String getNewTimeString(Context context, Long milliseconds) {
        Date afterDate = new Date();
        Long beforeTime = milliseconds;
        Date beforeDate = new Date(beforeTime);
        if (beforeDate.getYear() == afterDate.getYear()) {
            if (beforeDate.getDate() == afterDate.getDate() && beforeDate.getMonth() == afterDate.getMonth()) {
                if ((afterDate.getTime() - beforeTime) < 60 * 60 * 1000) {
                    if ((afterDate.getTime() - beforeTime) < 60 * 1000) {
                        int seconds = (int) ((afterDate.getTime() - beforeTime) / 1000);
                        if (seconds <= 0) {
                            seconds = 10;
                        }
                        return seconds + "秒钟前";
                    } else {
                        return (afterDate.getTime() - beforeTime) / (60 * 1000) + "分钟前";
                    }
                } else {
                    SimpleDateFormat sDate = new SimpleDateFormat("HH:mm:ss");
                    String values = sDate.format(beforeDate);
                    return "今天  " + values;
                }
            } else {
                SimpleDateFormat sDate = new SimpleDateFormat("MM-dd HH:mm:ss");
                String values = sDate.format(beforeDate);
                return values;
            }
        } else {
            SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd");
            String values = sDate.format(beforeDate);
            return values;
        }
    }

    /**
     * 7.获得对应天的星座
     *
     * @param month 月份
     * @param day   天
     * @return 星座
     */
    public static final String getConstellation(int month, int day) {
        if ((month == 3 && day > 20) || (month == 4 && day < 20))
            return "白羊座";
        if ((month == 4 && day > 19) || (month == 5 && day < 21))
            return "金牛座";
        if ((month == 5 && day > 20) || (month == 6 && day < 20))
            return "双子座";
        if ((month == 6 && day > 21) || (month == 7 && day < 23))
            return "巨蟹座";
        if ((month == 7 && day > 22) || (month == 8 && day < 23))
            return "狮子座";
        if ((month == 8 && day > 22) || (month == 9 && day < 23))
            return "处女座";
        if ((month == 9 && day > 20) || (month == 10 && day < 23))
            return "天秤座";
        if ((month == 10 && day > 22) || (month == 11 && day < 22))
            return "天蝎座";
        if ((month == 11 && day > 21) || (month == 12 && day < 22))
            return "射手座";
        if ((month == 12 && day > 21) || (month == 1 && day < 20))
            return "摩羯座";
        if ((month == 1 && day > 19) || (month == 2 && day < 19))
            return "水瓶座";
        if ((month == 2 && day > 18) || (month == 3 && day < 21))
            return "双鱼座";
        return null;
    }

    /**
     * 8.将指定milliseconds转换成指定格式的字符串
     *
     * @param format 1："yyyy年MM月dd日 HH时mm分ss秒" 2:"yyyy-MM-dd HH:mm:ss"
     *               3:"yyyy/MM/dd HH:mm:ss" 4:"yyyy年MM月dd日 HH时mm分ss秒 E "
     *               5:"yyyy/MM/dd E" 6."yyyy/MM/dd"7.yyyy-MM-dd HH:mm
     * @return dateStr 指定格式字符串
     */
    public static final String millisecondsToString(int format, Long milliseconds) {
        if (milliseconds == null) {
            milliseconds = System.currentTimeMillis();
        }
        return dateToString(format, new Date(milliseconds));
    }


    public static String getCustomerTime(long mills) {
        Calendar calendarToday = Calendar.getInstance();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);
        if (calendarToday.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) && calendarToday.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) {
            if (calendarToday.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)) {
                return "今天" + dateToString(13, new Date(mills));
            }
            calendarToday.add(Calendar.DAY_OF_MONTH, -1);
            if (calendarToday.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)) {
                return "昨天" + dateToString(13, new Date(mills));
            }
            return dateToString(FORMAT_NINETEEN, new Date(mills));
        }
        return millisecondsToString(8, mills);
    }

    // --------------------------------------------------------------------------------------------------------

    public static String dateToString(String format, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static String millisecondsToString(String format, Long mills) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        if (ANumberUtils.isEmpty(mills))
            return simpleDateFormat.format(new Date(0));
        return simpleDateFormat.format(new Date(mills));
    }

    /**
     * <p>
     * 9.将指定Date转换成指定格式的字符串
     * </p>
     *
     * @param format 格式 <li>1."yyyy年MM月dd日 HH时mm分ss秒"</li> <li>
     *               2."yyyy-MM-dd HH:mm:ss"</li> <li>3."yyyy/MM/dd HH:mm:ss"</li>
     *               <li>4."yyyy年MM月dd日 HH时mm分ss秒 E "</li> <li>5."yyyy/MM/dd E"</li>
     *               <li>
     *               6."yyyy/MM/dd"</li> <li>7."yyyy-MM-dd HH:mm"</li> <li>
     *               8."yyyy年MM月dd日"</li> <li>9."yyyy-MM-dd"</li>
     * @return dateStr 指定格式字符串
     */
    @SuppressLint("SimpleDateFormat")
    public static String dateToString(int format, Date date) {
        SimpleDateFormat simpleDateFormat;
        String dateStr = null;
        switch (format) {
            case 0:
                simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
                dateStr = simpleDateFormat.format(date);
                break;
            case 1:
                simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
                dateStr = simpleDateFormat.format(date);
                break;
            case 2:
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateStr = simpleDateFormat.format(date);
                break;
            case 3:
                simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                dateStr = simpleDateFormat.format(date);
                break;
            case 4:
                simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E ");
                dateStr = simpleDateFormat.format(date);
                break;
            case 5:
                simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd E");
                dateStr = simpleDateFormat.format(date);
                break;
            case 6:
                simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                dateStr = simpleDateFormat.format(date);
                break;
            case 7:
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                dateStr = simpleDateFormat.format(date);
                break;
            case 8:
                simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                dateStr = simpleDateFormat.format(date);
                break;
            case 9:
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateStr = simpleDateFormat.format(date);
                break;
            case 10:
                simpleDateFormat = new SimpleDateFormat("mm:ss");
                dateStr = simpleDateFormat.format(date);
                break;
            case 11:
                simpleDateFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
                dateStr = simpleDateFormat.format(date);
                break;
            case 12:
                simpleDateFormat = new SimpleDateFormat("yy/MM/dd");
                dateStr = simpleDateFormat.format(date);
                break;
            case 13:
                simpleDateFormat = new SimpleDateFormat("HH:mm");
                dateStr = simpleDateFormat.format(date);
                break;
            case 14:
                simpleDateFormat = new SimpleDateFormat("yy/MM/dd HH:mm");
                dateStr = simpleDateFormat.format(date);
                break;
            case 15:
                simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                dateStr = simpleDateFormat.format(date);
                break;
            case 16:
                simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                dateStr = simpleDateFormat.format(date);
                break;
            case 17:
                simpleDateFormat = new SimpleDateFormat("yyyy年MM月");
                dateStr = simpleDateFormat.format(date);
                break;
            case 18:
                simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
                dateStr = simpleDateFormat.format(date);
                break;
            case 19:
                simpleDateFormat = new SimpleDateFormat("MM月dd日 HH:mm");
                dateStr = simpleDateFormat.format(date);
                break;
            case 20:
                simpleDateFormat = new SimpleDateFormat("yyyy:M:d HH:mm:ss");
                dateStr = simpleDateFormat.format(date);
                break;
        }

        return dateStr;
    }

    /**
     * 10.字符串转换为date,也可以使用androidAPI中的DateFormat来实现
     *
     * @param format  格式 <li>1."yyyy年MM月dd日 HH时mm分ss秒"</li> <li>
     *                2."yyyy-MM-dd HH:mm:ss"</li> <li>3."yyyy/MM/dd HH:mm:ss"</li>
     *                <li>4."yyyy年MM月dd日 HH时mm分ss秒 E "</li> <li>5."yyyy/MM/dd E"</li>
     *                <li>
     *                6."yyyy/MM/dd"</li> <li>7."yyyy-MM-dd HH:mm"</li> <li>
     *                8."yyyy年MM月dd日"</li> <li>9."yyyy-MM-dd"</li>
     * @param dateStr 时间字符串
     * @return date格式
     * @throws ParseException
     */
    @SuppressLint("SimpleDateFormat")
    public static final Date stringToDate(int format, String dateStr) throws ParseException {
        Date date = null;
        if (!TextUtils.isEmpty(dateStr)) {
            SimpleDateFormat simpleDateFormat;
            switch (format) {
                case 1:
                    simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
                    date = simpleDateFormat.parse(dateStr);
                    break;
                case 2:
                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    date = simpleDateFormat.parse(dateStr);
                    break;
                case 3:
                    simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    date = simpleDateFormat.parse(dateStr);
                    break;
                case 4:
                    simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E ");
                    date = simpleDateFormat.parse(dateStr);
                    break;
                case 5:
                    simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd E");
                    date = simpleDateFormat.parse(dateStr);
                    break;
                case 6:
                    simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    date = simpleDateFormat.parse(dateStr);
                    break;
                case 7:
                    simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
                    date = simpleDateFormat.parse(dateStr);
                    break;
                case 8:
                    simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd");
                    date = simpleDateFormat.parse(dateStr);
                    break;
                case 9:
                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    date = simpleDateFormat.parse(dateStr);
                    break;
                case 10:
                    simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                    date = simpleDateFormat.parse(dateStr);
                    break;
                case 11:
                    simpleDateFormat = new SimpleDateFormat("yyyy年MM月");
                    date = simpleDateFormat.parse(dateStr);
                    break;
            }
        }
        return date;
    }

    /**
     * 11.字符串转换为Milliseconds,
     * <p/>
     * 因为在中国,计算的是dateStr到1970.1.1 08:00之间的毫秒数,如果是正常的时间,有年月日时分秒,计算的毫秒数是正确的
     * 如果只有时分秒,如21:41:05,默认会加上年月日:1970.1.1,结果就是1970.1.1 21:41:05,这样计算到1970.1.1 08:00之间的毫秒数
     * 不是用户真正想得到的毫秒数,比用户想得到的毫秒数少了8个小时
     *
     * @param format  格式 1："yyyy年MM月dd日 HH时mm分ss秒" 2:"yyyy-MM-dd HH:mm:ss"
     *                3:"yyyy/MM/dd HH:mm:ss" 4:"yyyy年MM月dd日 HH时mm分ss秒 E "
     *                5:"yyyy/MM/dd E" 6."yyyy/MM/dd"
     * @param dateStr 时间字符串
     * @return Milliseconds
     * @throws ParseException
     */
    public static final long stringToMilliseconds(int format, String dateStr) {
        try {
            return stringToDate(format, dateStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 12.返回一个日时分秒的集合
     *
     * @param type         倒计时类型1.日时分秒，2.日时分，3.时分秒
     * @param milliseconds 毫秒数
     * @return 日时分秒的集合1.日时分秒集合大小为8；2.日时分集合大小为6；3.时分秒集合大小为6
     */
    public static final List<String> countDownString(int type, long milliseconds) {
        List<String> list = new ArrayList<String>();
        long day = 0;
        long hour = 0;
        long minute = 0;
        long second = 0;
        second = milliseconds;
        day = second / (1000 * 60 * 60 * 24);
        hour = (second - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        minute = (second - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60);
        second = (second - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / (1000);
        switch (type) {
            case 1:
                // 日时分秒
                addDateList(day, list);
                addDateList(hour, list);
                addDateList(minute, list);
                addDateList(second, list);
                break;
            case 2:
                // 日时分
                addDateList(day, list);
                addDateList(hour, list);
                addDateList(minute, list);
                break;
            case 3:
                // 时分秒
                addDateList(hour, list);
                addDateList(minute, list);
                addDateList(second, list);
                break;
        }
        return list;
    }

    // -------------------------------------------------------------------------------------------------------->

    /**
     * 13. 在小于10的数前面加0
     *
     * @param date int类型数据
     * @return
     */
    public static final String to0Date(int date) {
        String str = null;
        if (date < 10) {
            str = "0" + date;
        } else {
            str = "" + date;
        }
        return str;
    }

    /**
     * 往集合里添加日时分秒数据
     *
     * @param date
     * @param list
     */
    private static final void addDateList(long date, List<String> list) {
        String str1 = null;
        String str2 = null;
        if (date == 0) {
            list.add("0");
            list.add("0");
        } else if (date > 0 && date < 10) {
            list.add("0");
            list.add(date + "");
        } else if (date >= 10) {
            String str = date + "";
            str1 = str.split("")[1];
            str2 = str.split("")[2];
            list.add(str1);
            list.add(str2);
        }
    }

    /**
     * @param mill
     * @return
     */
    public static String awayFromFuture(long mill) {
        BigInteger bigInteger = BigInteger.valueOf(mill);
        if (bigInteger.subtract(BigInteger.valueOf(30 * 24 * 60 * 60).multiply(BigInteger.valueOf(1000))).signum() > 0) {
            String month = bigInteger.divide(BigInteger.valueOf(30 * 24 * 60 * 60).multiply(BigInteger.valueOf(1000))).toString();
            return month + "月";
        } else if (mill > 24 * 60 * 60 * 1000) {
            long day = mill / (24 * 60 * 60 * 1000);
            return day + "天";
        } else if (mill > 60 * 60 * 1000) {
            long hour = mill / (60 * 60 * 1000);
            return hour + "小时";
        } else if (mill > 60 * 1000) {
            long minute = mill / (60 * 1000);
            return minute + "分钟";
        } else if (mill > 1000) {
            long seconds = mill / (1000);
            return seconds + "秒";
        }
        return null;
    }

    /**
     * @param num
     * @return
     */
    public static String append0IfLessThan9(int num) {
//        if (num < 1) {
//            throw new RuntimeException(" error");
//        }
        if (num < 10) {
            return "0" + num;
        }
        return "" + num;
    }

    public static class SimpleCalendar {
        public long millis;
        public int year       = -1;
        public int month      = -1;
        public int day        = -1;
        public int hour       = -1;
        public int minute     = -1;
        public int second     = -1;
        public int millSecond = -1;

        public SimpleCalendar() {
        }

        public SimpleCalendar(long millis, int year, int month, int day) {
            this.millis = millis;
            this.year = year;
            this.month = month;
            this.day = day;
        }

        public SimpleCalendar(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }
    }

    /**
     * 获取当天0点的时间戳
     *
     * @return
     */
    public static long getDateMiles() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime().getTime();
    }

    /**
     * 计算两个日期之间相差的整月数,日期是××××年××月××日００时００分００秒 如果不足一个月按照一个月计算
     */
    public static int betweenMonths(Date begin, Date end) {
        Calendar begingc = Calendar.getInstance();
        begingc.setTime(begin);
        int beginYear = begingc.get(Calendar.YEAR);
        int beginMonth = begingc.get(Calendar.MONTH);
        int beginDay = begingc.get(Calendar.DAY_OF_MONTH);

        Calendar endgc = Calendar.getInstance();
        endgc.setTime(end);
        int endYear = endgc.get(Calendar.YEAR);
        int endMonth = endgc.get(Calendar.MONTH);
        int endDay = endgc.get(Calendar.DAY_OF_MONTH);

        int between = (endYear - beginYear) * 12 + (endMonth - beginMonth);

        if (endDay > beginDay)
            between = between + 1;
        else if (endDay < beginDay)
            between = between - 1;
        return between;

    }

    /**
     * 获取两个时间差多少天小时分钟
     *
     * @param endDate
     * @param nowDate
     * @return
     */
    public static String getDatePoor(Date endDate, Date nowDate) {

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + " 天 " + hour + " 小时 " + min + " 分钟";
    }
}
