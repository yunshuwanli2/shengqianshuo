package yswl.priv.com.shengqianshopping;

import android.Manifest;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.umeng.socialize.PlatformConfig;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
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

        //微信分享配置appid---appsecret
        PlatformConfig.setWeixin("wx124653c63868deed", "ce81af4b4dc73a075e7d6a57e7528d5c");
    }

    @Override
    public String getBaseUrl_Https() {
        return BuildConfig.base_url;
    }


}
