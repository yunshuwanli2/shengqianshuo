package yswl.priv.com.shengqianshopping.http;


import android.os.Build;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.http.HttpClientProxy;
import yswl.com.klibrary.http.okhttp.MDeviceUtil;
import yswl.com.klibrary.util.L;
import yswl.com.klibrary.util.MAppInfoUtil;
import yswl.com.klibrary.util.MD5Util;
import yswl.com.klibrary.util.UrlParamsConfig;

/**
 * Created by kangpAdministrator on 2017/10/13 0013.
 * Emial kangpeng@yunhetong.net
 */

public class SqsHttpClientProxy {
    public static final String SQS_SECRET = "18555a3d6070754e4ca3bd4603432668";//省钱说密钥

    public static void postAsynSQS(String url, final int requestId, Map<String, Object> paramsMap, final HttpCallback<JSONObject> httpCallback) {
        if (paramsMap == null) {
            paramsMap = new HashMap<>();
        }
        paramsMap.put("deviceToken", MDeviceUtil.getMAC());
        paramsMap.put("deviceType", "2");
        paramsMap.put("osVersion", Build.VERSION.RELEASE);
        paramsMap.put("appVersion", MAppInfoUtil.getVersionCode());
        paramsMap.put("mdk", getMdk(paramsMap));
        HttpClientProxy.getInstance().postAsyn(url, requestId, paramsMap, httpCallback);
    }

    private static String getMdk(Map<String, Object> params) {
        StringBuffer beforeSign = UrlParamsConfig.getBeforeSign3(params);
        beforeSign.append(SQS_SECRET);
        L.e("SqsHttpClientProxy", "排序：拼接: " + beforeSign.toString());
        String afterSign = MD5Util.MD5(beforeSign.toString());
        return afterSign;
    }
}
