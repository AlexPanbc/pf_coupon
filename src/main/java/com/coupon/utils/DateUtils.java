package com.coupon.utils;


import com.yuelvhui.util.date.CalendarUtil;
import com.yuelvhui.util.date.DateUtil;
import com.yuelvhui.util.string.StringUtil;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类
 *
 * @Author wangxm
 * @Date 2019/8/30 14:25
 * @Param
 * @Return
 */
public class DateUtils extends DateUtil {

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

    /**
     * 获取指定时间N天后的0点
     *
     * @return
     */
    public static Date getSpecialBegin(Date date, int day) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date begin = cal.getTime();
        Date specialBegin = addDay(begin, day);
        return specialBegin;
    }

    /**
     * 日期格式化
     *
     * @return
     */
    public static Date parseStringToDate(String date, String pattern) {
        Date date1 = null;
        SimpleDateFormat simdate = new SimpleDateFormat(pattern);
        if (date != null) {
            try {
                date1 = simdate.parse(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return date1;
    }

    /**
     * date转LocalDateTime
     *
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        if (null == date) {
            return null;
        }
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
    }

    /**
     * LocalDateTime转date
     *
     * @return
     */
    public static Date localDateTimeToDate(LocalDateTime dateTime) {
        if (null == dateTime) {
            return null;
        }
        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.systemDefault());
        Date date = Date.from(zonedDateTime.toInstant());
        return date;
    }

    /**
     * date转LocalDate
     *
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
        if (null == date) {
            return null;
        }
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        return localDate;
    }

    /**
     * LocalDate转date
     *
     * @return
     */
    public static Date localDateToDate(LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(zoneId);
        Date date = Date.from(zonedDateTime.toInstant());
        return date;
    }

    /**
     * LocalDateTime转string
     * 当前时间转换成yyyy-MM-dd HH:mm:ss 格式时间戳
     *
     * @return
     */
    public static String localDateTimeToStr() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(CalendarUtil.TIME_PATTERN);
        String dateStr = LocalDateTime.now().format(df);
        return dateStr;
    }

    /**
     * LocalDateTime转string
     *
     * @return
     */
    public static String localDateTimeToStr(LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(CalendarUtil.TIME_PATTERN);
        String dateStr = localDateTime.format(df);
        return dateStr;
    }

    /**
     * LocalDate转string
     *
     * @return
     */
    public static String localDateToStr(LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(CalendarUtil.DATE_FMT_3);
        String dateStr = localDate.format(df);
        return dateStr;
    }

    /**
     * string转LocalDateTime
     *
     * @return
     */
    public static LocalDateTime strToLocalDateTime(String date) {
        if (StringUtil.isBlank(date)) {
            return null;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(CalendarUtil.TIME_PATTERN);
        LocalDateTime localDateTime = LocalDateTime.parse(date, df);
        return localDateTime;
    }

    /**
     * string转LocalDate
     *
     * @return
     */
    public static LocalDate strToLocalDate(String date) {
        if (StringUtil.isBlank(date)) {
            return null;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(CalendarUtil.TIME_PATTERN);
        LocalDateTime localDateTime = LocalDateTime.parse(date, df);
        LocalDate localDate = localDateTime.toLocalDate();
        return localDate;
    }

    /**
     * 时间戳转时间字符串
     *
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds)));
    }

    /**
     * LocalDateTime转string
     *
     * @return
     */
    public static String localDateToString(LocalDateTime localDate, String calendar) {
        if (null == localDate) {
            return null;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(calendar);
        String dateStr = localDate.format(df);
        return dateStr;
    }

    /**
     * LocalDateTime转string
     * yyyy-MM-dd
     *
     * @return
     */
    public static String localDateTimeToStrFmt(LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(CalendarUtil.DATE_FMT_3);
        String dateStr = localDateTime.format(df);
        return dateStr;
    }


}
