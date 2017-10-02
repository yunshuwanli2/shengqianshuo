package yswl.com.klibrary.http.okhttp;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import yswl.com.klibrary.MApplication;
import yswl.com.klibrary.http.Session;
import yswl.com.klibrary.http.SessionManager;

/**
 * Created by nixn@yunhetong.com on 2016/10/11.
 */

public class MyCookieJar implements CookieJar {
    public static final String TAG = "MyCookieJar";
    private final ArrayMap<String, List<Cookie>> cookieStore = new ArrayMap<>();
    public static Cookie sessionCookie;

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            Cookie sessionCookies = getSessionCookie(cookies);
            if (sessionCookies != null) {
                    sessionCookie = sessionCookies;
                    SessionManager.initSession(sessionCookies);
            }
        }
        cookieStore.put(url.host(), cookies);
    }



    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = new ArrayList<>();
        List<Cookie> cookiesBefore = cookieStore.get(url.host());
        if (cookiesBefore != null) {
            cookies.addAll(cookiesBefore);
        }
        if (sessionCookie != null && !TextUtils.isEmpty(sessionCookie.value())) {
            Cookie sessionBefore = getSessionCookie(cookies);
            if (sessionBefore != null) {
                String oldSeesionId = sessionBefore.value();
                if (!sessionCookie.value().equalsIgnoreCase(oldSeesionId)) {
                    cookies.remove(sessionBefore);
                    cookies.add(sessionCookie);
                }
            }else{
                cookies.add(sessionCookie);
            }
        }
        requestSessionLog(cookies, url);
        return cookies;
    }


    private Cookie getSessionCookie(List<Cookie> cookies) {
        Cookie sessionCookie = null;
        if (cookies != null && cookies.size() > 0) {
            for (Cookie c : cookies) {
                if (Session.JSESSIONID.equals(c.name())) {
                    sessionCookie = c;
                }
            }
        }
        return sessionCookie;
    }

    private void requestSessionLog(List<Cookie> cookies, HttpUrl url) {
        if (MApplication.getApplication().getDebugSetting()) {
            for (Cookie c : cookies) {
                if (Session.JSESSIONID.equals(c.name())) {
                }
            }
        }
    }
}
