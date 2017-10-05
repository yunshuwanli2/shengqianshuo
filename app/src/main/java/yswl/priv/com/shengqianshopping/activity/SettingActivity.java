package yswl.priv.com.shengqianshopping.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.auth.third.login.callback.LogoutCallback;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;

import yswl.com.klibrary.util.AppCacheCleanManager;
import yswl.com.klibrary.util.CacheDataManager;
import yswl.com.klibrary.util.MAppInfoUtil;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.base.MToolBarActivity;

//设置
public class SettingActivity extends MToolBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("设置");

        TextView version = findView(R.id.app_version);
        TextView name = findView(R.id.app_name);
        TextView cache = findView(R.id.app_cache);
        version.setText(MAppInfoUtil.getVersionCode(this));
        name.setText(R.string.app_name);
        cache.setText(AppCacheCleanManager.getCacheSize(this));

        findView(R.id.user_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 个人信息
            }
        });

        findView(R.id.clear_cache).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 清除缓存
                AppCacheCleanManager.cleanApplicationData(SettingActivity.this);
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
                logout();

            }
        });
    }

    public void logout() {
        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        alibcLogin.logout(SettingActivity.this, new LogoutCallback() {
            @Override
            public void onSuccess() {
                //TODO 发出退出全局信息
                Toast.makeText(SettingActivity.this, "退出登录成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(SettingActivity.this, "退出登录失败 " + code + msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
