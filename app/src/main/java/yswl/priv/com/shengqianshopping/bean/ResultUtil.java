package yswl.priv.com.shengqianshopping.bean;

import org.json.JSONObject;

import yswl.com.klibrary.util.ToastUtil;

/**
 * Created by kangpAdministrator on 2017/9/30 0030.
 * Emial kangpeng@yunhetong.net
 */

public class ResultUtil {
    public static final String MSG = "message";
    public static final String CODE = "errorCode";
    public static final String LIST = "list";

    public static boolean isCodeOK(JSONObject jsonObject) {
        if (jsonObject.optInt(CODE) == 100) {
            return true;
        } else {
            ToastUtil.showToast(jsonObject.optString(MSG));
        }
        return false;
    }

    public static JSONObject analysisData(JSONObject jsonObject) {
        return jsonObject.optJSONObject(MSG);
    }
}
