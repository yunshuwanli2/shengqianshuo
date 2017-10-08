package yswl.priv.com.shengqianshopping.bean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import yswl.com.klibrary.util.DateJsonDeserializer;

/**
 * Created by yunshuwanli on 17/10/2.
 * <p>
 * "id": "21777",
 * "iid": "556847869496",
 * "title": "2017秋冬季新款韩版修身纯色长袖T恤女装上衣显瘦百搭打底衫外穿t",
 * "pictUrl": "http://img3.tbcdn.cn/tfscom/i2/1990700767/TB1rBM.dzoIL1JjSZFyXXbFBpXa_!!2-item_pic.png",
 * "smallImages": [
 * <p>
 * <p>
 * "http://img1.tbcdn.cn/tfscom/i2/1990700767/TB2pLP8ah9fF1Jjy1XcXXc_rVXa_!!1990700767.jpg",
 * "http://img2.tbcdn.cn/tfscom/i1/1990700767/TB2QXvxagL8F1JjSszbXXbguVXa_!!1990700767.jpg",
 * "http://img2.tbcdn.cn/tfscom/i1/1990700767/TB2vPRobgAEF1JjSZFLXXbzNXXa_!!1990700767.jpg",
 * "http://img3.tbcdn.cn/tfscom/i3/1990700767/TB2x3f4ajifF1Jjy1zkXXX0pVXa_!!1990700767.jpg"
 * ],
 * "reservePrice": "179.00",
 * "zkFinalPrice": "17.90",
 * "zkFinalPriceWap": "17.90",
 * "userType": "0",
 * "provcity": "广东 东莞",
 * "itemUrl": "h5.m.taobao.com/awp/core/detail.htm?id=556847869496",
 * "clickUrl": "s.click.taobao.com/t?e=m%3D2%26s%3D3e1ORxCeEqpw4vFB6t2Z2ueEDrYVVa64XoO8tOebS%2Bfjf2vlNIV67nED8MRZdlj%2FF%2FSaKyaJTUYBBn4Q5uj1Y6tODEU4CJfre1Q1MVzV3qHc0Kfez9ffgu%2FBas0tvb4eZz42eVlqqJT6BXppXS5x5z0BDlc5vrJjopr%2FSerwmYN%2FC%2F5qkGzwlWSRPFyN02C0lcIHJF5bHPLPoZWH7rRow%2B5w3qTDn2a9N%2BtrLqfdqjYhhQs2DjqgEA%3D%3D",
 * "nick": "为你而生_1314",
 * "sellerId": "1990700767",
 * "volume": "0",
 * "tkRate": "30.00",
 * "shopTitle": "可爱潮流女装店",
 * "type": "4",
 * "status": "1",
 * "category": "16",
 * "couponClickUrl": "uland.taobao.com/coupon/edetail?e=WHpDE6DuosYGQASttHIRqbQn8tItAh%2FkyuIIEoBLnpBO6NH28rRRBywVvi1RXLFZB2f2PaVIFi6TYqxlKMOoXr9fwBwwUiqlLXDsAPgdXB3dedyFw3M0hjd2Naqom0e0",
 * "couponEndTime": "1506700800",
 * "couponStartTime": "1503936000",
 * "couponInfo": "满6元减5元",
 * "couponPrice": "12.9",
 * "couponNum": "5",
 * "couponTotalCount": "1500",
 * "couponRemainCount": "1343"
 */

public class ProductDetail {

    public String id;
    public String iid;//商品id
    public String title;
    public String pictUrl;//商品主图
    public String picUrl;//商品主图
    public String[] smallImages;//商品小图列
    public String reservePrice;//商品一口价格
    public String itemUrl;
    public String provcity;//所在地 杭州
    public String userType;//卖家类型 0:集市 1:商城
    public String clickUrl;//淘客地址
    public String zkFinalPriceWap;//无线端商品折扣价格
    public String zkFinalPrice;//商品折扣价格
    public String nick;//卖家昵称
    public String sellerId;// 卖家ID

    public String getVolume() {
        if (volume == null)
            return "0";
        return volume;
    }

    public String volume;//30天销量
    public String tkRate;//收入比例 20% 百分比
    public String shopTitle;
    public String category;
    public String type;//宝贝类型 1:普通商品 2:鹊桥高佣金商品 3:定向招商商品 4:营销计划商品
    public String status;//宝贝状态 0:失效 1:有效
    public String couponClickUrl;
    public String couponPrice;//券后商品价格
    public String couponInfo;
    public String couponEndTime;
    public String couponStartTime;
    public String couponTotalCount;
    public String couponRemainCount;


    public String couponNum;//优惠券面额价格

    public String getCouponNum() {
        return "券:￥" + couponNum;
    }


    public static List<ProductDetail> jsonToList(JSONArray objarray) {
        if (objarray == null) return null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class,
                new DateJsonDeserializer()).create();
        Type listum = new TypeToken<List<ProductDetail>>() {
        }.getType();
        List<ProductDetail> result = gson.fromJson(objarray.toString(), listum);
        return result;
    }


}
