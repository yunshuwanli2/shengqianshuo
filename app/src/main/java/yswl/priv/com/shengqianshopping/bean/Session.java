package yswl.priv.com.shengqianshopping.bean;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Date;

import yswl.com.klibrary.http.okhttp.MSPUtils;
import yswl.com.klibrary.util.DateJsonDeserializer;
import yswl.priv.com.shengqianshopping.util.SharedPreUtils;

/**
 * Created by yunshuwanli on 17/10/18.
 * <p>
 * "uid":"18716253",
 * "token":"skdfk232312ijakshfsaesdc332dfs",
 * "phoneStatus":"0"
 */

public class Session {

    public String uid;
    public String token;
    public String phoneStatus;

    public boolean isBindPhone() {
        return phoneStatus.equals("1");
    }


    public static Session jsonToBean(JSONObject json) {
        if (null == json) return null;

        try {
            Gson gson = new GsonBuilder().registerTypeAdapter(Date.class,
                    new DateJsonDeserializer()).create();
            Type data = new TypeToken<Session>() {
            }.getType();
            Session result = gson.fromJson(json.toString(), data);
            return result;
        } catch (Exception e) {
        }
        return null;
    }




}
