package yswl.priv.com.shengqianshopping.banner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import yswl.com.klibrary.util.DateJsonDeserializer;


public class BannerBean {

    public String id;
    public String imgUrl;
    public String link;

    public BannerBean(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public static List<BannerBean> jsonToList(JSONArray objarray) {
        if (objarray == null)
            return null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class,
                new DateJsonDeserializer()).create();
        Type listum = new TypeToken<List<BannerBean>>() {
        }.getType();
        List<BannerBean> result = gson.fromJson(objarray.toString(), listum);
        return result;
    }
}
