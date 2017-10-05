package yswl.priv.com.shengqianshopping.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 缓存类
 */
public class SharedPreUtils {

    public final static String CACHE = "cache";

    public final static String UID = "uid";//

    public final static String TOKEN = "token";//

    public final static String NICK = "Nick";//昵称

    public final static String HEADIMG = "headImg";//头像

    public final static String ISONLINE = "isONLine";//在线状态

    private SharedPreferences sp;

    static private SharedPreUtils instance;

    static public SharedPreUtils getInstance(Context context) {
        if (instance == null)
            instance = new SharedPreUtils(context);
        return instance;
    }

    private SharedPreUtils(Context context) {
        sp = context.getSharedPreferences(CACHE, Context.MODE_PRIVATE);
    }

    /**
     * SharedPreferences通过key取值
     *
     * @param key
     * @param dfValue 默认值
     * @return
     */
    public String getValueBySharedPreferences(String key, String dfValue) {
        String value = sp.getString(key, dfValue);
        if ("null".equals(value)) {
            value = dfValue;
        }
        return value;
    }

    /**
     * SharedPreferences通过key存value
     *
     * @param key
     * @param value
     */
    public void saveValueBySharedPreferences(String key, String value) {
        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * SharedPreferences通过key取值
     *
     * @param key
     * @param dfValue 默认值
     * @return
     */
    public boolean getValueBySharedPreferences(String key, boolean dfValue) {
        boolean value = sp.getBoolean(key, dfValue);
        return value;
    }

    /**
     * SharedPreferences通过key存value
     *
     * @param key
     * @param value
     */
    public void saveValueBySharedPreferences(String key, boolean value) {
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 清空所有数据
     */
    public void clearAllData() {
        Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}
