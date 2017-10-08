package yswl.priv.com.shengqianshopping.manager;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.http.HttpClientProxy;
import yswl.com.klibrary.util.GsonUtil;
import yswl.priv.com.shengqianshopping.R;
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
    public static void getUser(Context context, String uid, HttpCallback<JSONObject> httpCallback, int requestId) {
        String url = UrlUtil.getUrl(context, R.string.url_get_user_info);
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        HttpClientProxy.getInstance().postAsynSQS(url, requestId, map, httpCallback);
    }

}
