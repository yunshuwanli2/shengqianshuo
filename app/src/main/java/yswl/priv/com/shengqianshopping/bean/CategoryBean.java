package yswl.priv.com.shengqianshopping.bean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import yswl.com.klibrary.util.DateJsonDeserializer;

/**
 * Created by kangpAdministrator on 2017/9/30 0030.
 * Emial kangpeng@yunhetong.net
 */

public class CategoryBean implements Serializable{
   public String pid;
   public String title;

    public static List<CategoryBean> jsonToList(JSONArray objarray) {
        if (objarray == null) return null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class,
                new DateJsonDeserializer()).create();
        Type listum = new TypeToken<List<CategoryBean>>() {
        }.getType();
        List<CategoryBean> result = gson.fromJson(objarray.toString(), listum);
        return result;
    }

    /*public static ContractAttachments jsonToBean(JSONObject json) {
        JSONObject dataJson = json.optJSONObject(MResultUtil.DATA);
        if (null == dataJson) return null;

        try {
            Gson gson = new GsonBuilder().registerTypeAdapter(Date.class,
                    new DateJsonDeserializer()).create();
            Type data = new TypeToken<ContractAttachments>() {
            }.getType();
            ContractAttachments result = gson.fromJson(dataJson.toString(), data);
            return result;
        } catch (Exception e) {
            Lx.e(TAG, "Exception");
        }
        return null;
    }*/
}
