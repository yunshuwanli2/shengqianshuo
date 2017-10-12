package yswl.priv.com.shengqianshopping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.auth.third.login.callback.LogoutCallback;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;

import org.json.JSONObject;

import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.util.AppCacheCleanManager;
import yswl.com.klibrary.util.CacheDataManager;
import yswl.com.klibrary.util.MAppInfoUtil;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.base.MToolBarActivity;
import yswl.priv.com.shengqianshopping.manager.UserManager;
import yswl.priv.com.shengqianshopping.util.AlibcUtil;
import yswl.priv.com.shengqianshopping.util.SharedPreUtils;

//设置
public class SettingActivity extends MToolBarActivity {

    public static void startActivity(Activity context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    TextView cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("设置");

        TextView version = findView(R.id.app_version);
        TextView name = findView(R.id.app_name);
        cache = findView(R.id.app_cache);
        version.setText(MAppInfoUtil.getVersionCode(this) + "");
        name.setText(R.string.app_name);
        cache.setText(AppCacheCleanManager.getCacheSize(this));

//        findView(R.id.user_info).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //TODO 个人信息
//                UserInfoActivity.startActivity(SettingActivity.this);
//            }
//        });

        findView(R.id.clear_cache).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 清除缓存
                AppCacheCleanManager.cleanApplicationData(SettingActivity.this);
                cache.setText(0 + "");
            }
        });

        findView(R.id.about_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 关于省多多
            }
        });

        findView(R.id.app_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlibcUtil.logout(SettingActivity.this);
                finish();
            }
        });
    }


}
