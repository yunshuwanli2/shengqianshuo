package yswl.priv.com.shengqianshopping;

import yswl.com.klibrary.MApplication;
import yswl.priv.com.shengqianshopping.util.AlibcUtil;

/**
 * Created by kangpAdministrator on 2017/9/27 0027.
 * Emial kangpeng@yunhetong.net
 */
public class App extends MApplication {
    static final String TAG = App.class.getSimpleName();

    @Override
    public boolean getDebugSetting() {
        return BuildConfig.debug;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AlibcUtil.initAlibc(this);
    }

    @Override
    public String getBaseUrl_Https() {
        return BuildConfig.base_url;
    }


}
