package yswl.com.klibrary.http;


import android.os.Build;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import yswl.com.klibrary.http.okhttp.MSPUtils;
import yswl.com.klibrary.http.okhttp.MyCookieJar;


/**
 * session管理
 *
 * @author nixn@yunhetong.net
 */
public class SessionManager {
    public static final String TAG = "SessionManager";
    public static volatile Session session;

    public static Session getSingleSession() {
        if (session == null) {
            synchronized (Session.class) {
                if (session == null)
                    session = new Session();
            }
        }
        return session;
    }

    public static String getSessionId() {
        synchronized (SessionManager.class) {
            String sessionId = getMemSeesionId();
            if (TextUtils.isEmpty(sessionId) && MyCookieJar.sessionCookie != null) {
                initSession(MyCookieJar.sessionCookie);
                sessionId = getMemSeesionId();
            }
            if (TextUtils.isEmpty(sessionId)) {
                sessionId = MSPUtils.getString(TAG, null);
            }
            return sessionId;
        }
    }

    private static String getMemSeesionId() {
        synchronized (SessionManager.class) {
            Session session = getSingleSession();
            String sessionId = session == null ? null : session.sessionId;
            return sessionId;
        }
    }

    private static String getSessionCookieForWebView() {
        synchronized (SessionManager.class) {
            Session session = getSingleSession();
            return session.webviewCookies;
        }
    }

    public static synchronized void clear() {
        synchronized (SessionManager.class) {
            session = null;
            MSPUtils.remove(TAG);
        }
    }

    public static void initSession(okhttp3.Cookie cookie) {
        synchronized (SessionManager.class) {
            if (TextUtils.isEmpty(cookie.value()) || SessionManager.getSingleSession().sessionId == cookie.value()) {
                return;
            }
            SessionManager.getSingleSession().sessionId = cookie.value();
            String setCookies = cookie.toString();
            SessionManager.getSingleSession().webviewCookies = setCookies;
            synCookies();
            if (!TextUtils.isEmpty(cookie.value()))
                MSPUtils.put(TAG, cookie.value());
        }
    }

    private static void synCookies() {
        String sesion = SessionManager.getSessionCookieForWebView();
        String url = HttpClientProxy.BASE_URL;
        if (!TextUtils.isEmpty(sesion)) {
            synCookies2(sesion, url);
        }
    }

    private static void synCookies2(String session, String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        String old = cookieManager.getCookie(url);
        if (session.equals(old)) return;
        cookieManager.setCookie(HttpClientProxy.BASE_URL, session);
        if (Build.VERSION.SDK_INT >= 21) {
            cookieManager.flush();
        } else {
            CookieSyncManager.getInstance().sync();
        }
    }

}
