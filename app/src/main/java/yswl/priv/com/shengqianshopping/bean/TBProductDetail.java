package yswl.priv.com.shengqianshopping.bean;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import yswl.com.klibrary.util.DateJsonDeserializer;

/**
 * "iid":"559258795815",
 * "title":"La pagayo/帕佳图萌萌哒零钱包",
 * "zkFinalPrice":"1",
 * "userType":"0",
 * "volume":"234",
 * "itemUrl":"",
 * "clickUrl":"",
 * "reservePrice":"299.00",
 * "pictUrl":"http://img4.tbcdn.cn/tfscom/gju3.alicdn.com/tps/i1/0/TB21eMXdjuhSKJjSspaXXXFgFXa_!!0-juitemmedia.jpg"
 */

public class TBProductDetail {
    public String iid;//商品id
    public String title;
    public String pictUrl;//商品主图
    public String reservePrice;//商品一口价格
    public String itemUrl;
    public String volume;//30天销量
    public String userType;//卖家类型 0:集市 1:商城
    public String clickUrl;//淘客地址
    public String zkFinalPrice;//商品折扣价格

    //除了疯狂抢之外 打开的url 优先级 领券->>-商品详情
    public String getFinalUrl() {
        if (TextUtils.isEmpty(clickUrl))
            return clickUrl;
        if (TextUtils.isEmpty(itemUrl))
            return itemUrl;
        return pictUrl;
    }

    public String getReservePrice() {
        return "¥" + reservePrice;
    }

    public String getZkFinalPrice() {
        return "¥" + zkFinalPrice;
    }

    public String getVolume() {
        if (volume == null)
            return "0";
        return volume;
    }

    public static List<TBProductDetail> jsonToList(JSONArray objarray) {
        if (objarray == null) return null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class,
                new DateJsonDeserializer()).create();
        Type listum = new TypeToken<List<TBProductDetail>>() {
        }.getType();
        List<TBProductDetail> result = gson.fromJson(objarray.toString(), listum);
        return result;
    }


}
