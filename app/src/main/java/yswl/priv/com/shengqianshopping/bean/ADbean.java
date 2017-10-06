package yswl.priv.com.shengqianshopping.bean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Date;

import yswl.com.klibrary.util.DateJsonDeserializer;

/**
 * Created by yunshuwanli on 17/10/6.
 *  final String url = obj.optString("imgUrl");
 final String link = obj.optString("link");
 String time_s = obj.optString("s");
 */

public class ADbean implements Serializable{
    public String imgUrl;
    public String link;
    public String s;


    public static ADbean jsonToBean(JSONObject json) {
        JSONObject dataJson = json.optJSONObject(ResultUtil.MSG);
        if (null == dataJson) return null;

        try {
            Gson gson = new GsonBuilder().registerTypeAdapter(Date.class,
                    new DateJsonDeserializer()).create();
            Type data = new TypeToken<ADbean>() {
            }.getType();
            ADbean result = gson.fromJson(dataJson.toString(), data);
            return result;
        } catch (Exception e) {
        }
        return null;
    }
}
