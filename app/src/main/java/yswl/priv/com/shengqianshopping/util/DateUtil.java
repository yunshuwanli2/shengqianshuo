package yswl.priv.com.shengqianshopping.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import yswl.com.klibrary.util.L;

/**
 * Created by yunshuwanli on 17/10/4.
 */

public class DateUtil {
    public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT2 = "yyyy-MM-dd";
    public static final String FORMAT3 = "yyyy-MM-dd HH:mm";

    public static int compare_date(String DATE2) {
        DateFormat df = new SimpleDateFormat(FORMAT, Locale.CHINA);
        try {
            Date nowTime = new Date();
            Date dt2 = df.parse(DATE2);
            if (nowTime.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (nowTime.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    static final String TAG = "DateUtil";

    public static String longToStringDate(long time) {
        Date date = new Date(time);//取时间
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT3, Locale.CHINA);
        return formatter.format(date);
    }

    public static String getTodayFixedTime(int hour) {
        return getTodayFixedTime(hour, 0, 0);
    }

    public static String getTodayFixedTime(int hour, int minute, int second) {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        Date date1 = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT, Locale.CHINA);
        return formatter.format(date1);

    }


    public static String getTomorroFixedTime(int hour) {
        return getTomorroFixedTime(hour, 0, 0);
    }

    public static String getTomorroFixedTime(int hour, int minute, int second) {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, +1); //明天就是+1 昨天就是-1
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        Date date1 = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT, Locale.CHINA);
        return formatter.format(date1);
    }


    //string转时间戳
    public static long stringToTimeStamp(String time) {
        long result = 0;
        try {
            SimpleDateFormat format = new SimpleDateFormat(FORMAT);
            Date date = format.parse(time);
            result = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    //获取当天凌晨时间戳
    public static long getTodayZero() {
        Date date = new Date();
        long l = 24 * 60 * 60 * 1000; //每天的毫秒数
        //date.getTime()是现在的毫秒数，它 减去 当天零点到现在的毫秒数（ 现在的毫秒数%一天总的毫秒数，取余。），理论上等于零点的毫秒数，不过这个毫秒数是UTC+0时区的。
        //减8个小时的毫秒值是为了解决时区的问题。
        return (date.getTime() - (date.getTime() % l) - 8 * 60 * 60 * 1000);
    }

}
