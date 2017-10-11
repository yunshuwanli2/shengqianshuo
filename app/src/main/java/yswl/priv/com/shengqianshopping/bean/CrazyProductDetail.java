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
 * Created by kangpAdministrator on 2017/10/11 0011.
 * Emial kangpeng@yunhetong.net
 * "iid": "522184410785",
 * "title": "柠檬树装饰全包装修 特权定金",
 * "totalAmount": "20",
 * "clickUrl": "http://item.taobao.com/item.htm?id=522184410785",
 * "categoryName": "家纺家居",
 * "zkFinalPrice": "1",
 * "endTime": "1507737599",
 * "startTime": "1507651200",
 * "soldNum": "20",
 * "reservePrice": "10.00",
 * "pictUrl":
 */

public class CrazyProductDetail {
    public String iid;
    public String title;
    public String totalAmount;
    public String clickUrl;
    public String categoryName;
    public String zkFinalPrice;
    public String endTime;
    public String startTime;
    public String soldNum;
    public String reservePrice;
    public String pictUrl;

    public static List<CrazyProductDetail> jsonToList(JSONArray objarray) {
        if (objarray == null) return null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class,
                new DateJsonDeserializer()).create();
        Type listum = new TypeToken<List<CrazyProductDetail>>() {
        }.getType();
        List<CrazyProductDetail> result = gson.fromJson(objarray.toString(), listum);
        return result;
    }

}
