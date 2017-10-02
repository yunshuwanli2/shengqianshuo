package yswl.priv.com.shengqianshopping.util;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;


import yswl.com.klibrary.MApplication;
import yswl.priv.com.shengqianshopping.BuildConfig;


/**
 * 路径 管理
 *
 */
public class UrlUtil {

    public static String getHTTPSWWW() {
        String url =  BuildConfig.base_url;
        return url;
    }

    public static String getHTTPWWW() {
        String url = BuildConfig.base_url;
        return url;
    }

    public static String getUrl(Activity ac, int res) {
        return getHTTPSWWW() + ac.getResources().getString(res);
    }
    public static String getUrl(int res) {
        return getHTTPSWWW() +  MApplication.getApplication().getResources().getString(res);
    }
    public static String getUrl(Activity ac, int res, Object... formatArgs) {
        return getHTTPSWWW() + ac.getResources().getString(res, formatArgs);
    }

    public static String getUrl(Context ac, int res, Object... formatArgs) {
        return getHTTPSWWW() + ac.getResources().getString(res, formatArgs);
    }

    public static String getUrl(Context ac, int res) {
        return getHTTPSWWW() + ac.getResources().getString(res);
    }

    public static String getUrl(Fragment ac, int res) {
        return getHTTPSWWW() + ac.getResources().getString(res);
    }

    public static String getUrl(Fragment ac, int res, Object... formatArgs) {
        return getHTTPSWWW() + ac.getResources().getString(res, formatArgs);
    }

    public static String getUrl(FragmentActivity ac, int res) {
        return getHTTPSWWW() + ac.getResources().getString(res);
    }

    public static String setParam(String url, String key, String value) {
        if (TextUtils.isEmpty(value))
            return url;
        if (url.contains("?")) {
            url = url + "&" + key + "=" + value;
        } else {
            url = url + "?" + key + "=" + value;
        }
        return url;
    }

    public static String setParam(String url, String key, int value) {
        if (value == -1)
            return url;
        if (url.contains("?")) {
            url = url + "&" + key + "=" + value;
        } else {
            url = url + "?" + key + "=" + value;
        }
        return url;
    }

    public static String setParam(String url, String key, long value) {
        if (value == -1)
            return url;
        if (url.contains("?")) {
            url = url + "&" + key + "=" + value;
        } else {
            url = url + "?" + key + "=" + value;
        }
        return url;
    }


}
