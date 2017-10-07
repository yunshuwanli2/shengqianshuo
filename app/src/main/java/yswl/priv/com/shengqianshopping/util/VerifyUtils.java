package yswl.priv.com.shengqianshopping.util;

import android.support.annotation.NonNull;

import java.util.regex.Pattern;

/**
 * 校验工具类
 */

public class VerifyUtils {


    /**
     * 简单手机正则校验
     *
     * @param MobileNo 手机号
     * @return
     */
    public static boolean isValidMobileNo(@NonNull String MobileNo) {

        String regPattern = "^1[3-9]\\d{9}$";
        return Pattern.matches(regPattern, MobileNo);

    }
}
