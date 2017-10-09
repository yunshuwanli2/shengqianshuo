package yswl.priv.com.shengqianshopping.bean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import yswl.com.klibrary.util.DateJsonDeserializer;
import yswl.com.klibrary.util.GsonUtil;

/**
 * Created by kangpAdministrator on 2017/10/9 0009.
 * Emial kangpeng@yunhetong.net
 */

public class OrderBean {

    public String order;
    public String amount;
    public String status;
    public String dateTime;

    public static List<OrderBean> jsonToList(JSONArray objarray) {
        if (objarray == null) return null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class,
                new DateJsonDeserializer()).create();
        Type listum = new TypeToken<List<OrderBean>>() {
        }.getType();
        List<OrderBean> result = gson.fromJson(objarray.toString(), listum);
        return result;
    }

}
