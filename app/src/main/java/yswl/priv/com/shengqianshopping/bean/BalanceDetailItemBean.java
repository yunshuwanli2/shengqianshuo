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

public class BalanceDetailItemBean {

    public final String title;
    public final String content;
    public final String dateTime;

    public BalanceDetailItemBean(String title, String content, String dateTime) {
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
    }


    public static List<BalanceDetailItemBean> jsonToList(JSONArray objarray) {
        if (objarray == null) return null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class,
                new DateJsonDeserializer()).create();
        Type listum = new TypeToken<List<BalanceDetailItemBean>>() {
        }.getType();
        List<BalanceDetailItemBean> result = gson.fromJson(objarray.toString(), listum);
        return result;
    }
}