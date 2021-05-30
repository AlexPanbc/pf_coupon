package com.coupon.utils;

import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
    private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal();
    private static final Object object = new Object();


    public static String dateToString(Date date, String pattern) {
        String dateString = null;
        if (date != null) {
            try {
                dateString = getDateFormat(pattern).format(date);
            } catch (Exception var4) {
            }
        }

        return dateString;
    }

    private static SimpleDateFormat getDateFormat(String pattern) throws RuntimeException {
        SimpleDateFormat dateFormat = (SimpleDateFormat)threadLocal.get();
        if (dateFormat == null) {
            synchronized(object) {
                if (dateFormat == null) {
                    dateFormat = new SimpleDateFormat(pattern);
                    dateFormat.setLenient(false);
                    threadLocal.set(dateFormat);
                }
            }
        }
        dateFormat.applyPattern(pattern);
        return dateFormat;
    }

    /**
     * 获取一天的开始时间
     *
     * @return
     */
    public static Date getDayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取一天的结束时间
     *
     * @return
     */
    public static Date getDayEnd() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }


    //获取某段时间内的所有日期

    public static List<String> findDates(String dStart, String dEnd) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        List<String> dateList=null;
        try {
            Calendar cStart = Calendar.getInstance();
            cStart.setTime(sdf.parse(dStart));
            dateList = new ArrayList<>();
            dateList.add(dStart);
            while (sdf.parse(dEnd).after(cStart.getTime())) {
                cStart.add(Calendar.DAY_OF_MONTH, 1);
                dateList.add(sdf.format(cStart.getTime()));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return dateList;
    }
}
