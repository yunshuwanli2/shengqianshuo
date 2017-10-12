package yswl.priv.com.shengqianshopping.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import yswl.com.klibrary.MApplication;
import yswl.com.klibrary.http.okhttp.MSPUtils;

/**
 * 缓存类
 */
public class SharedPreUtils {

    public final static String CACHE = "cache";

    public final static String UID = "uid";//用户id

    public final static String TOKEN = "token";//令牌

    public final static String PHONE_STATE = "phoneStatus";//绑定手机状态

    public final static String USERINFO = "userInfo";//用户信息

    public final static String ISONLINE = "isONLine";//在线状态

    public final static String IS_BIND_ZFB = "zfbStatus";//在线状态


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
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public static void put(String fileName, String key, Object object) {
        SharedPreferences sp = MApplication.getApplication().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        MSPUtils.SharedPreferencesCompat.apply(editor);
    }


    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(String fileName, String key, Object defaultObject) {
        SharedPreferences sp = MApplication.getApplication().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
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
            return dfValue;
        }
        return value;
    }

    public boolean getBooleanValueBySharedPreferences(String key, boolean dfValue) {
        boolean value = sp.getBoolean(key, dfValue);
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
