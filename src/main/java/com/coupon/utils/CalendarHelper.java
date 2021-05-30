package com.coupon.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @program: pf_coupon
 * @description:
 * @author: dupulei
 * @create: 2020-12-23 10:33
 **/
public class CalendarHelper {

    /**
     * 获取当前月第一天
     *
     * @param month
     * @return
     * @throws ParseException
     */
    public static String firstDayOfMonth(String month, String format) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date thisDate = sdf.parse(month);
        Calendar cld = Calendar.getInstance();
        cld.setTime(thisDate);
        cld.set(Calendar.DAY_OF_MONTH, 1);

        SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd");
        return sdfFormat.format(cld.getTime());
    }

    /**
     * 获取当前月最后一天
     *
     * @param month
     * @return
     * @throws ParseException
     */
    public static String lastDayOfMonth(String month, String format) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date thisDate = sdf.parse(month);
        Calendar cld = Calendar.getInstance();
        cld.setTime(thisDate);

        //月份+1，天设置为0。下个月第0天，就是这个月最后一天
        cld.add(Calendar.MONTH, 1);
        cld.set(Calendar.DAY_OF_MONTH, 0);

        SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd");
        return sdfFormat.format(cld.getTime());
    }

    /**
     * 获取指定日期下个月的第一天
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static String firstDayOfNextMonth(String dateStr, String format) {

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {

            Date date = sdf.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.MONTH, 1);

            SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd");
            return sdfFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
