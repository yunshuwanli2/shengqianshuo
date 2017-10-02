package yswl.priv.com.shengqianshopping.bean;/*
package com.yswl.priv.shengqianshapping.bean;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import yswl.com.klibrary.debug.L;
import yswl.com.klibrary.util.ToastUtil;

*/
/**
 * 服务器数据结果解析工具
 *
 * @author nixn@yunhetong.net
 *//*

public class MResultUtil {
    public static final String TAG = "ResultUtil";
    */
/**
     * 数据标志
     **//*

    public static final String DATA = "message";
    public static final String CODE = "errorCode";

    public static boolean isSuccess(JSONObject json) {
        return isSuccess(json, true);
    }

    public static boolean isCodeOK(JSONObject json) {
        if (json == null)
            return false;
        return json.optInt(CODE, -1) == 100;
    }

    public static boolean isSuccess(JSONObject json, boolean notice) {
        if (json == null)
            return false;
        if (!(isCodeOK(json))) {
            L.e(TAG, "isSuccess false");
            if (notice) {
//                String msg = json.optString(MSG);
//                if (TextUtils.isEmpty(msg) || "null".equals(msg)) {
//                    msg = "异常操作";
//                }
//                ToastUtil.showToast(msg);
            }
            return false;
        }
        return true;
    }

    */
/**
     * 获得数据说明
     *
     * @param json
     * @return
     *//*

    public static String getMSG(JSONObject json) {
        if (json == null)
            return null;
        return json.optString(MSG);
    }

    */
/**
     * 获得数据说明
     *
     * @param json
     * @return
     *//*

    public static String getMSG(JSONObject json, String df) {
        if (json == null)
            return df;
        String result = json.optString(MSG, df);
        return result;
    }

    @Deprecated
    public static Object getDATA(JSONObject json) {
        if (json == null)
            return null;
        return json.opt(DATA);
    }

    public static int getDATAINT(JSONObject json, String key, int defaultValue) {
        if (json == null)
            return defaultValue;
        return json.optInt(key, defaultValue);
    }

    public static Object getDATA(JSONObject json, String key) {
        if (json == null)
            return null;
        return json.opt(key);
    }

    @Deprecated
    public static JSONObject getObjDATA(JSONObject json) {
        Object obj = getDATA(json);
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        return null;
    }

    public static JSONObject getObjDATA(JSONObject json, String key) {
        Object obj = getDATA(json, key);
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        return null;
    }

    public static JSONArray getArrayObjDATA(JSONObject json) {
        Object obj = getDATA(json);
        if (obj instanceof JSONArray) {
            return (JSONArray) obj;
        }
        return null;
    }

    public static JSONArray getArrayObjDATA(JSONObject json, String key) {
        Object obj = getDATA(json, key);
        if (obj instanceof JSONArray) {
            return (JSONArray) obj;
        }
        return null;
    }


    public static boolean isAcOverdue(Fragment fragment) {
        if (fragment != null && fragment.isAdded()
                && fragment.getActivity() != null
                && !fragment.getActivity().isFinishing()) {
            return false;
        }
        return true;
    }
}
*/
