package yswl.com.klibrary.util;

import android.annotation.SuppressLint;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * 用于url参数加密 的类
 *
 * @author Administrator
 */
public class UrlParamsConfig {

    private static final String TAG = UrlParamsConfig.class.getSimpleName();

    /**
     * ①添加参数的封装方法（TreeMap默认将会按照字典排序） 排序的为：普通参数，api_key
     */
    private static final String getBeforeSign(Map<String, String> params) {
        if (params == null) return null;
        StringBuffer orgin = new StringBuffer("");
        Map<String, String> treeMap = new TreeMap<String, String>();
        treeMap.putAll(params);
        Iterator<String> iter = treeMap.keySet().iterator();
        while (iter.hasNext()) {
            String name = (String) iter.next();
            try {// 对value进行编码
                orgin.append(name).append("=" + URLEncoder.encode(params.get(name), "utf-8") + "&");// 对特殊字符，中文进行转码
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return (String) orgin.subSequence(0, orgin.length() - 1);
    }

    //省钱说加密规则：
    //将传入map 排序 按照key的asc规则
    //各个值的拼接在一起生成MD5 作为校验参数
    public static final StringBuffer getBeforeSign3(Map<String, Object> params) {
        if (params == null) return null;
        StringBuffer orgin = new StringBuffer("");
        Map<String, Object> treeMap = new TreeMap<>();
        treeMap.putAll(params);
        Iterator<String> iter = treeMap.keySet().iterator();
        while (iter.hasNext()) {
            String keyname = iter.next();
            try {// 对value进行编码
                if (params.get(keyname) instanceof String) {
                    orgin.append(URLEncoder.encode(params.get(keyname).toString(), "utf-8"));
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return orgin;
    }

    private static final String getBeforeSign2(Map<String, String> params) {
        StringBuffer orgin = new StringBuffer("");
        if (params == null)
            return null;
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<String, String>(params);
        Set<Entry<String, String>> entrys = sortedParams.entrySet();

        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Entry<String, String> param : entrys) {
            basestring.append(param.getKey()).append("=").append(param.getValue() + "&");
        }
        return (String) basestring.subSequence(0, orgin.length() - 1);
    }

    /**
     * ② 获得signature(请传入按照字典排好序,拼接加上api_sign)
     * 例：a=123&w=yoojaesuk&w1=100&api_key=321
     */
    @SuppressLint("DefaultLocale")
    private static String getSignature(String s, String api_sign) {
        L.e(TAG, "-------MD5加密前的参数-------" + s + api_sign);
        L.e(TAG, "-------MD5加密后的参数-------" + MD5Util.MD5(s + api_sign).toLowerCase());
        return MD5Util.MD5(s + api_sign).toLowerCase();
    }

    /**
     * ③获得封装过的请求URL，参数（原始url ip端口,业务参数treemap,token）
     * 参1例：http://192.168.0.148:68/user/login 参2例：普通参数，api_key,排好序的map
     */
    public static String getURLEncapsulation(String url, Map<String, String> params_apikey, String api_sign) {
        String URLreturn = null;
        if (params_apikey == null || params_apikey.size() == 0) {// 无参数请求

        } else {// 带参数请求
            URLreturn = url + "?" + UrlParamsConfig.getBeforeSign(params_apikey) + "&api_sign=" + UrlParamsConfig.getSignature(UrlParamsConfig.getBeforeSign(params_apikey), api_sign);
        }
        L.e(TAG, "-------完整url-------" + URLreturn);
        return URLreturn;
    }

}
