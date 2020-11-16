package com.nynu.goule.utils;


import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private DateUtil() {

    }


    /* 日期格式 */
    public interface DATE_PATTERN {
        String HHMMSS = "HHmmss";
        String HH_MM_SS = "HH:mm:ss";
        String YYYYMMDD = "yyyyMMdd";
        String YYYY_MM_DD = "yyyy-MM--dd";
        String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
        String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
        String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
        String YYYYMMDDHH = "yyyyMMddHH";
        String YYYY = "yyyy";
        String YYYYMM = "yyyyMM";
    }
    //获取当前时间
    public static Timestamp getCurrentTimestamp(){
        Format format = new SimpleDateFormat(DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);
        String temp = format.format(new Date());
        Timestamp time = Timestamp.valueOf(temp);
        return time;
    }
}
