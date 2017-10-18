package yswl.priv.com.shengqianshopping.bean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import yswl.com.klibrary.util.DateJsonDeserializer;

/**
 * Created by kangpAdministrator on 2017/10/18 0018.
 * Emial kangpeng@yunhetong.net
 * 1.   "uid",
 * 1.                 "phone":"",
 * 1.                 "reward":""
 */

public class FansBean {
    public String uid;
    public String phone;
    public String reward;

    public static List<FansBean> jsonToList(JSONArray objarray) {
        if (objarray == null || objarray.length() == 0)
            return null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class,
                new DateJsonDeserializer()).create();
        Type listum = new TypeToken<List<FansBean>>() {
        }.getType();
        List<FansBean> result = gson.fromJson(objarray.toString(), listum);
        return result;
    }

}
