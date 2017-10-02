package yswl.priv.com.shengqianshopping.banner;

/**
 * Created by yunshuwanli on 17/10/1.
 * 排序枚举
 *
 */

public enum SortEnum {
    HOT("人气","popularity"),VOLUME("销量","volume"),NEW("最新","new"),PRICE("价格","price");

    private String value;
    private String key;

    private SortEnum(String key, String value) {
        this.key = key;
        this.value=value;
    }

    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }
}
