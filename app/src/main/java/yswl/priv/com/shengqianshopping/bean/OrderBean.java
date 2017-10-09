package yswl.priv.com.shengqianshopping.bean;

import org.json.JSONArray;

import java.util.List;

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
        return GsonUtil.jsonToList(objarray, OrderBean.class);
    }

}
