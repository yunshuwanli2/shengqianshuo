package yswl.priv.com.shengqianshopping.manager;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.http.HttpClientProxy;
import yswl.com.klibrary.util.GsonUtil;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.activity.LoginActivity;
import yswl.priv.com.shengqianshopping.bean.UserBean;
import yswl.priv.com.shengqianshopping.util.SharedPreUtils;
import yswl.priv.com.shengqianshopping.util.UrlUtil;

/**
 * 用户管理
 */

public class UserManager {

    //登录
    public static void Login() {


    }


    //将用户信息保存
    public static void saveInfo(Context context, String jsonInfo) {
        SharedPreUtils.getInstance(context).saveValueBySharedPreferences(SharedPreUtils.USERINFO, jsonInfo);
    }

    //用户是否登录
    public static boolean isLogin(Context context) {
        return SharedPreUtils.getInstance(context).getBooleanValueBySharedPreferences(SharedPreUtils.ISONLINE, false);
    }

    public static void saveLogin(Context context) {
        SharedPreUtils.getInstance(context).saveValueBySharedPreferences(SharedPreUtils.ISONLINE, true);
    }
    //用户是否绑定支付宝
    public static boolean isBindZFB(Context context) {
        return SharedPreUtils.getInstance(context).getBooleanValueBySharedPreferences(SharedPreUtils.IS_BIND_ZFB, false);
    }

    public static void bindZFB(Context context,boolean boo) {
        SharedPreUtils.getInstance(context).saveValueBySharedPreferences(SharedPreUtils.IS_BIND_ZFB, boo);
    }

    //用户是否绑定手机认证
    public static boolean isBindPhone(Context context) {
        String boole = SharedPreUtils.getInstance(context).getValueBySharedPreferences(SharedPreUtils.PHONE_STATE, "false");
        if (TextUtils.isEmpty(boole)) return false;
        return Boolean.parseBoolean(boole);
    }

    //用户是否绑定手机认证
    public static void saveBindPhoneState(Context context, String isBind) {
        String boo = "1";
        if (isBind.equalsIgnoreCase("0"))
            boo = "false";
        else if (isBind.equalsIgnoreCase("1"))
            boo = "true";
        else if (isBind.equalsIgnoreCase("true") || isBind.equalsIgnoreCase("false"))
            boo = isBind;
        SharedPreUtils.getInstance(context).saveValueBySharedPreferences(SharedPreUtils.PHONE_STATE, boo);
    }

    public static String getUid(Context context) {
        return SharedPreUtils.getInstance(context).getValueBySharedPreferences(SharedPreUtils.UID, "");
    }

    public static void saveUid(Context context, String uid) {
        SharedPreUtils.getInstance(context).saveValueBySharedPreferences(SharedPreUtils.UID, uid);
    }

    public static String getToken(Context context) {
        return SharedPreUtils.getInstance(context).getValueBySharedPreferences(SharedPreUtils.TOKEN, "");
    }

    public static void saveToken(Context context, String token) {
        SharedPreUtils.getInstance(context).saveValueBySharedPreferences(SharedPreUtils.TOKEN, token);
    }

    public static boolean isBindPhone(String photoState) {
        return !photoState.equalsIgnoreCase("0");
    }

    //在线
    public static boolean isOnlin(Context context) {
        return isLogin(context) && isBindPhone(context);
    }


    //获取储存的用户信息
    public static UserBean getUserInfo(Context context) {
        String userStrinfo = SharedPreUtils.getInstance(context).getValueBySharedPreferences(SharedPreUtils.USERINFO, "");
        try {
            return UserBean.jsonToBean(new JSONObject(userStrinfo));
        } catch (JSONException e) {
            return null;
        }
//        return GsonUtil.GsonToBean(userStrinfo, UserBean.class);
    }

    //调用接口获取用户信息
    public static void rquestUserInfoDetail(Context context, String uid, HttpCallback<JSONObject> httpCallback, int requestId) {
        String url = UrlUtil.getUrl(context, R.string.url_get_user_info);
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        HttpClientProxy.getInstance().postAsynSQS(url, requestId, map, httpCallback);
    }

}
