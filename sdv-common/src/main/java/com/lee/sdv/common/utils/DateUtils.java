/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期工具类
 * @author lipeng
 */
public class DateUtils {
    private final static Logger LOG = LoggerFactory.getLogger(DateUtils.class);

    /** 日期格式，yyyyMMdd: "yyyy-MM-dd" */
    public static final String yyyy_MM_dd = "yyyy-MM-dd";

    public static final String yyyyMMdd = "yyyyMMdd";
    // 信用卡过期日期显示
    public static final String MM_yyyy = "MM/yyyy";

    /** 日期格式，yyyyMMddhhmmss: "yyyy-MM-dd kk:mm:ss" */
    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    /** 日期格式，yyyyMMddhhmmss: "yyyyMMddHHmmss" */
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";

    /** 日期格式，yyyyMMddhhmmss: "yyyyMMdd_HHmmss" */
    public static final String yyyyMMdd_HHmmss = "yyyyMMdd HHmmss";

    /** 日期格式，yyyyMMddhhmmss: "yyyy-MM-dd kk:mm:ss" */
    public static final String yyyy_MM_dd_HHmmss = "yyyy-MM-dd HHmmss";

    /** 日期格式，yyyyMMddhhmmss: "yyyy-MM-dd kk:mm:ss" */
    public static final String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";

    /** 日期格式，yyyyMMddhhmmss: "kk:mm:ss" */
    public static final String HH_mm_ss = "HH:mm:ss";

    /** 日期格式，YYYY: "yyyy" */
    public static final String YYYY = "yyyy";

    public static long getServerTime() {
        return System.currentTimeMillis();
    }

    /**
     * 格式化日期,默认返回yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * 格式化显示当前日期
     * @param format
     * @return
     */
    public static String format(String format) {
        return format(new Date(), format);
    }

    /**
     * 日期格式化
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e) {
            LOG.warn("日期格式化失败.{}", e.getMessage());
        }
        return null;
    }

    /**
     * 时间格式化， 传入毫秒
     * @param time
     * @return
     */
    public static String dateFormat(long time) {
        return format(new Date(time), yyyy_MM_dd_HH_mm_ss);
    }


    /**
     * 获取某个时间偏移几天的date 该方法在反欺诈运行时会被优先load，所以加了改方法
     *
     * @param date
     * @param dayOffset 如果是正数,表示往后推dayOffset个天,如果是负数表示往前推dayOffset天.
     * @return
     */
    public static Date getDayOffsetDate(Date date, int dayOffset) {
        return getOffsetDate(date, dayOffset, 1);
    }

    /**
     * 获取某个时间的几个小时偏移的date
     *
     * @param date
     * @param hourOffset 如果是正数,表示往后推hourOffset个小时,如果是负数表示往前推hourOffset个小时.
     * @return
     */
    public static Date getHourOffsetDate(Date date, int hourOffset) {
        return getOffsetDate(date, hourOffset, 2);
    }

    /**
     * 获取某个时间的几个分钟偏移的date
     *
     * @param date
     * @param minuteOffset 如果是正数,表示往后推minuteOffset个分钟,如果是负数表示往前推minuteOffset个分钟.
     * @return
     */
    public static Date getMinuteOffsetDate(Date date, int minuteOffset) {
        return getOffsetDate(date, minuteOffset, 3);
    }

    /**
     * 获取某个时间的offset个时间单位偏移的date
     *
     * @param date
     * @param offset 如果是正数,表示往后推offset个时间单位,如果是负数表示往前推offset个时间单位.
     * @param type   1偏移天 2偏移小时 3偏移分钟.
     * @return
     */
    public static Date getOffsetDate(Date date, int offset, int type) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (type == 1) {
            c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + offset);
        } else if (type == 2) {
            c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) + offset);
        } else if (type == 3) {
            c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + offset);
        }
        return c.getTime();
    }

    /**
     * 将字符串型按一定的格式转换成日期型
     *
     * @param sDate   //字符串型
     * @param pattern //格式
     * @return //返回日期型
     */
    public static Date strToDate(String sDate, String pattern) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            date = format.parse(sDate);
        } catch (Exception e) {
            LOG.error("日期装换错误,日期=" + sDate + "格式=" + pattern, e);
        }
        return date;
    }
}
