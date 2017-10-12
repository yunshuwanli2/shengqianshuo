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

    //昨天时间段 今日0-9点
    public static TimeBean getYTes() {
        return new TimeBean(DateUtil.getTodayFixedTime(0), DateUtil.getTodayFixedTime(9));
    }

    //9时间段
    public static TimeBean getNine() {
        return new TimeBean(DateUtil.getTodayFixedTime(9), DateUtil.getTodayFixedTime(12, 59, 59));
    }


    public static TimeBean getThi() {
        return new TimeBean(DateUtil.getTodayFixedTime(13), DateUtil.getTodayFixedTime(16, 59, 59));
    }


    public static TimeBean getSev() {
        return new TimeBean(DateUtil.getTodayFixedTime(17), DateUtil.getTodayFixedTime(23, 59, 59));
    }

    public static TimeBean getTomorrow() {
        return new TimeBean(DateUtil.getTomorroFixedTime(0), DateUtil.getTomorroFixedTime(23, 59, 59));
    }

}
