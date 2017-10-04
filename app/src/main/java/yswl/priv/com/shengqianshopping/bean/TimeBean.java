package yswl.priv.com.shengqianshopping.bean;

import java.io.Serializable;

import yswl.priv.com.shengqianshopping.util.DateUtil;

/**
 * Created by yunshuwanli on 17/10/4.
 */

public class TimeBean implements Serializable {
    public String title;
    public String startTime;
    public String endTime;

    public TimeBean(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static TimeBean getYTes() {
        return new TimeBean(DateUtil.getTodayFixedTime(0), DateUtil.getTodayFixedTime(9));
    }

    public static TimeBean getYTes2() {
        return new TimeBean(DateUtil.getTodayFixedTime2("00:00:00"), DateUtil.getTodayFixedTime2("09:00:00"));
    }

    public static TimeBean getNine() {
        return new TimeBean(DateUtil.getTodayFixedTime(9), DateUtil.getTomorroFixedTime(0));
    }

    public static TimeBean getNine2() {
        return new TimeBean(DateUtil.getTodayFixedTime2("09:00:00"), DateUtil.getTomorroFixedTime2("00:00:00"));
    }

    public static TimeBean getThi() {
        return new TimeBean(DateUtil.getTodayFixedTime(13), DateUtil.getTodayFixedTime(0));
    }

    public static TimeBean getThi2() {
        return new TimeBean(DateUtil.getTodayFixedTime2("13:00:00"), DateUtil.getTomorroFixedTime2("00:00:00"));
    }

    public static TimeBean getSev() {
        return new TimeBean(DateUtil.getTodayFixedTime(17), DateUtil.getTomorroFixedTime(0));
    }

    public static TimeBean getSev2() {
        return new TimeBean(DateUtil.getTodayFixedTime2("17:00:00"), DateUtil.getTomorroFixedTime2("00:00:00"));
    }

    public static TimeBean getTom() {
        return new TimeBean(DateUtil.getTomorroFixedTime(0), DateUtil.getTomorroFixedTime(9));
    }

    public static TimeBean getTom2() {
        return new TimeBean(DateUtil.getTomorroFixedTime2("00:00:00"), DateUtil.getTomorroFixedTime2("09:00:00"));
    }

}
