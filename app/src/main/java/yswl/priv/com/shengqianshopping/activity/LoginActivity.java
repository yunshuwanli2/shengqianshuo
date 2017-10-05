package yswl.priv.com.shengqianshopping.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.ali.auth.third.ui.context.CallbackContext;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.alibaba.baichuan.android.trade.callback.AlibcLoginCallback;

import yswl.com.klibrary.util.L;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.base.MToolBarActivity;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends MToolBarActivity {


    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("登录");

        Button mLogin = findView(R.id.btn_login);
        mLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


    }

    private void login() {
        final AlibcLogin alibcLogin = AlibcLogin.getInstance();
        alibcLogin.showLogin(LoginActivity.this, new AlibcLoginCallback() {

            @Override
            public void onSuccess() {
                Toast.makeText(LoginActivity.this, "登录成功 ", Toast.LENGTH_LONG).show();
                //获取淘宝用户信息
                L.i(TAG, "获取淘宝用户信息: " + AlibcLogin.getInstance().getSession());

            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(LoginActivity.this, "登录失败 ", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CallbackContext.onActivityResult(requestCode, resultCode, data);
    }

}

