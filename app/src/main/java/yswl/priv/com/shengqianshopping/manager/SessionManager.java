package yswl.priv.com.shengqianshopping.manager;

import android.text.TextUtils;

import yswl.com.klibrary.http.okhttp.MSPUtils;
import yswl.priv.com.shengqianshopping.bean.Session;
import yswl.priv.com.shengqianshopping.bean.UserBean;
import yswl.priv.com.shengqianshopping.util.SharedPreUtils;

/**
 * Created by yunshuwanli on 17/10/18.
 */

public class SessionManager {


    public static void saveUserInfo(Session session) {
        MSPUtils.put(SharedPreUtils.CACHE, SharedPreUtils.SESSION, MSPUtils.ObjToStr(session));
    }


    public static Session getSession() {
        String sessStr = MSPUtils.getString(SharedPreUtils.CACHE, SharedPreUtils.SESSION, "");
        if (TextUtils.isEmpty(sessStr)) return null;
        Object object = MSPUtils.StrToObj(sessStr);
        Session session = null;
        if (object instanceof UserBean)
            session = (Session) object;
        return session;
    }


}
