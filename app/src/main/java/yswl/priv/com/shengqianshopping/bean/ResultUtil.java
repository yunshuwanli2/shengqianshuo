package yswl.priv.com.shengqianshopping.bean;

import org.json.JSONObject;

import yswl.com.klibrary.MApplication;
import yswl.com.klibrary.util.ToastUtil;
import yswl.priv.com.shengqianshopping.activity.LoginActivity;
import yswl.priv.com.shengqianshopping.manager.UserManager;
import yswl.priv.com.shengqianshopping.util.AlibcUtil;

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
        } else if (jsonObject.optInt(CODE) == 1203) {//登录失效
            UserManager.saveLogin(MApplication.getApplication(), false);
            LoginActivity.startActivity(MApplication.getApplication());
        }
        ToastUtil.showToast(jsonObject.optString(MSG));
        return false;
    }

    public static JSONObject analysisData(JSONObject jsonObject) {
        return jsonObject.optJSONObject(MSG);
    }
}
