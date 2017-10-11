package yswl.priv.com.shengqianshopping;

import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import yswl.com.klibrary.MApplication;

/**
 * Created by kangpAdministrator on 2017/9/27 0027.
 * Emial kangpeng@yunhetong.net
 */
public class App extends MApplication {
    static final String TAG = App.class.getSimpleName();

    @Override
    public boolean getDebugSetting() {
        return true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CustomActivityOnCrash.install(this);
        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                //初始化成功，设置相关的全局配置参数
                AlibcTradeSDK.setForceH5(true);
            }

            @Override
            public void onFailure(int code, String msg) {
                //初始化失败，可以根据code和msg判断失败原因，详情参见错误说明
                Toast.makeText(App.this, "TaeSDK 初始化失败 -- " + msg + "  " + code, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public String getBaseUrl_Https() {
        return BuildConfig.base_url;
    }


}
