package com.yunye.common.utils;

import com.sun.istack.internal.Nullable;
import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间处理工具类
 * @author huangfu
 */
public class DateUtil {
    private static final String NOT_FORMAT_DATE = "yyyyMMdd";
    private static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 转换成对应格式的字符串
     * @param date 时间
     * @param formatStr 转换格式
     * @return 标准的时间格式
     */
    public static String dateFormat(Date date,@Nullable String formatStr){
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        String dateFormat;
        if(StringUtils.isNotBlank(formatStr)){
            dateFormat = localDateTime.format(DateTimeFormatter.ofPattern(formatStr));
        }else{
            dateFormat = localDateTime.format(DateTimeFormatter.ofPattern(NOT_FORMAT_DATE));
        }
        return dateFormat;
    }

    /**
     * 时间运算 天数相加
     * @param dateStr 标准的时间字符串
     * @param dateFormat 时间的格式
     * @param day 相加天数
     * @return 标准时间
     */
    public static Date dateAddDay(String dateStr,String dateFormat,long day){
        if(dateFormat == null){
            dateFormat = FORMAT_DATE_TIME;
        }
        LocalDate parse = LocalDate.parse(dateStr,DateTimeFormatter.ofPattern(dateFormat));
        LocalDate localDateTime = parse.plusDays(day);
        return localDateToDate(localDateTime);
    }

    /**
     * 时间运算 天数相加
     * @param date 标准的时间
     * @param day 相加天数
     * @return 标准时间
     */
    public static Date dateAddDay(Date date,Integer day){
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime calculationDate = localDateTime.plusDays(day);
        return localDateTimeToDate(calculationDate);
    }

    /**
     * 时间运算 天数相加
     * @param dateStr 标准的时间格式
     * @param day 相加天数
     * @param dateFormat 时间格式字符串
     * @return 计算后的时间字符串
     */
    public static String dateAddDayStr(String dateStr,String dateFormat,Integer day){
        if(dateFormat == null){
            dateFormat = FORMAT_DATE_TIME;
        }
        LocalDate parse = LocalDate.parse(dateStr,DateTimeFormatter.ofPattern(dateFormat));
        LocalDate localDateTime = parse.plusDays(day);
        return localDateTime.format(DateTimeFormatter.ofPattern(dateFormat));
    }

    /**
     * 时间运算 天数相加
     * @param date 标准的时间格式
     * @param day 相加天数
     * @param dateFormat 时间格式字符串
     * @return 计算后的时间字符串
     */
    public static String dateAddDayStr(Date date,String dateFormat,Integer day){
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime calculationDate = localDateTime.plusDays(day);
        return calculationDate.format(DateTimeFormatter.ofPattern(dateFormat));
    }

    /**
     * 时间运算 天数相减
     * @param dateStr 标准的时间字符串
     * @param dateFormat 时间的格式
     * @param day 相加天数
     * @return 标准时间
     */
    public static Date dateLessDay(String dateStr,String dateFormat,long day){
        if(dateFormat == null){
            dateFormat = FORMAT_DATE_TIME;
        }
        LocalDate parse = LocalDate.parse(dateStr,DateTimeFormatter.ofPattern(dateFormat));
        LocalDate localDateTime = parse.minusDays(day);
        return localDateToDate(localDateTime);
    }

    /**
     * 时间运算 天数相减
     * @param date 标准的时间
     * @param day 相加天数
     * @return 标准时间
     */
    public static Date dateLessDay(Date date,Integer day){
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime calculationDate = localDateTime.minusDays(day);
        return localDateTimeToDate(calculationDate);
    }

    /**
     * 时间运算 天数相减
     * @param dateStr 标准的时间格式
     * @param day 相加天数
     * @param dateFormat 时间格式字符串
     * @return 计算后的时间字符串
     */
    public static String dateLessDayStr(String dateStr,String dateFormat,Integer day){
        if(dateFormat == null){
            dateFormat = FORMAT_DATE_TIME;
        }
        LocalDate parse = LocalDate.parse(dateStr,DateTimeFormatter.ofPattern(dateFormat));
        LocalDate localDateTime = parse.minusDays(day);
        return localDateTime.format(DateTimeFormatter.ofPattern(dateFormat));
    }

    /**
     * 时间运算 天数相减
     * @param date 标准的时间格式
     * @param day 相加天数
     * @param dateFormat 时间格式字符串
     * @return 计算后的时间字符串
     */
    public static String dateLessDayStr(Date date,String dateFormat,Integer day){
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime calculationDate = localDateTime.minusDays(day);
        return calculationDate.format(DateTimeFormatter.ofPattern(dateFormat));
    }


    /**
     * 字符串转换时间
     * @param dateStr 标准时间字符串
     * @return 标准时间类型
     */
    public static Date strToDate(String dateStr){
        LocalDateTime parse = LocalDateTime.parse(dateStr);
        return localDateTimeToDate(parse);
    }

    /**
     * date转换为java8的localDate
     * @param date 标准时间
     * @return java8的时间类型
     */
    public static LocalDateTime dateToLocalDateTime(Date date){
        //获取时间实例
        Instant instant = date.toInstant();
        //获取时间地区ID
        ZoneId zoneId = ZoneId.systemDefault();
        //转换为LocalDate
        return  instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * 时间转换
     * @param localDateTime 时间
     * @return 标准date
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime){
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * 时间转换
     * @param localDate 时间
     * @return 标准date
     */
    public static Date localDateToDate(LocalDate localDate){
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }
}
