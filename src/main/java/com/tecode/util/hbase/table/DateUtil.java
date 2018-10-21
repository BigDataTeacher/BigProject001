package com.tecode.util.hbase.table;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/10/19.
 */
public class DateUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 把一个日期转换为字符串
     * @param date
     * @return
     */
    public static String dateToString(Date date){
            return sdf.format(date);
    }

    /**
     * 把一个字符串转换为日期
     */
    public static Date stringToDate(String dateStr){
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
