package yswl.com.klibrary.http.okhttp;

import org.json.JSONObject;

import java.util.Map;

import yswl.com.klibrary.http.CallBack.HttpCallback;


public interface IRequestMethod {
//        void getSyn(String url, int requestId, ArrayMap<String, Object> params);
//
//        void postJSONSyn(String url, int requestId, ArrayMap<String, Object> params);
//
//        void postFormSyn(String url, int requestId, ArrayMap<String, Object> params);

    void getAsyn(String url, int requestId, Map<String, Object> params, final HttpCallback<JSONObject> httpCallback);

    void postJSONAsyn(String url, int requestId, Map<String, Object> params, final HttpCallback<JSONObject> httpCallback);

    void postAsyn(String url, int requestId, Map<String, Object> params, final HttpCallback<JSONObject> httpCallback);
}