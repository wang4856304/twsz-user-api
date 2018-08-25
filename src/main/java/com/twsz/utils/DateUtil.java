package com.twsz.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * }
 *
 * @author Administrator
 * @Description:
 * @date 2018/8/17 11:58
 */
public class DateUtil {

    public static final int YYYYMMDDHHMMSS = 1;
    public static final int  YYYY_MM_DD_HH_MM_SS= 2;
    public static final int YYYYMMDD = 3;

    public static String formartDate(Date date, int formatFalg) {
        SimpleDateFormat format = null;
        String dateStr = "";
        if (YYYYMMDDHHMMSS == formatFalg) {
            format = new SimpleDateFormat("yyyyMMddHHmmss");
            dateStr = format.format(date);
        }
        if (YYYY_MM_DD_HH_MM_SS == formatFalg) {
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateStr = format.format(date);
        }
        if (YYYYMMDD == formatFalg) {
            format = new SimpleDateFormat("yyyyMMdd");
            dateStr = format.format(date);
        }
        return dateStr;
    }

    /**
     * 当前时间往后推day天
     * @param day
     * @return
     */
    public static Long delayTime(int day) {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return calendar.getTimeInMillis();
    }

    public static void main(String args[]) {
        Long sss = delayTime(30);
        System.out.println(formartDate(new Date(), 2));
    }
}
