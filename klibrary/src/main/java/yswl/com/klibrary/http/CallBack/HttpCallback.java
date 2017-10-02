package yswl.com.klibrary.http.CallBack;

/**
 * Created by kangpAdministrator on 2017/4/27 0027.
 * Emial kangpeng@yunhetong.net
 */

public interface HttpCallback<T> {
    void onSucceed(int requestId, T result);

    void onFail(int requestId, String errorMsg);
}
