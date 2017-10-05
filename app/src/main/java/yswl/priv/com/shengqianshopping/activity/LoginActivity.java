package yswl.priv.com.shengqianshopping.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.ali.auth.third.ui.context.CallbackContext;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.alibaba.baichuan.android.trade.callback.AlibcLoginCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.http.HttpClientProxy;
import yswl.com.klibrary.util.GsonUtil;
import yswl.com.klibrary.util.L;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.base.MToolBarActivity;
import yswl.priv.com.shengqianshopping.bean.ResultUtil;
import yswl.priv.com.shengqianshopping.bean.UserBean;
import yswl.priv.com.shengqianshopping.util.SharedPreUtils;
import yswl.priv.com.shengqianshopping.util.UrlUtil;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends MToolBarActivity implements HttpCallback<JSONObject> {


    private static final String TAG = "LoginActivity";
    private UserBean userBean;
    private String nick;
    private String headImg;

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
        Log.i("znh", "--" + android.os.Process.myTid());
        alibcLogin.showLogin(LoginActivity.this, new AlibcLoginCallback() {

            @Override
            public void onSuccess() {
                //获取淘宝用户信息
                L.i(TAG, "获取淘宝用户信息: " + AlibcLogin.getInstance().getSession());
                nick = AlibcLogin.getInstance().getSession().nick;
                headImg = AlibcLogin.getInstance().getSession().avatarUrl;
                String url = UrlUtil.getUrl(LoginActivity.this, R.string.url_login);
                Map<String, Object> map = new HashMap<>();
                map.put("channel", "1");
                map.put("channelUid", AlibcLogin.getInstance().getSession().userid);
                map.put("channelNickname", nick);
                map.put("channelAvatar", headImg);
                map.put("aFrom", "");
                HttpClientProxy.getInstance().postAsyn(url, 1002, map, LoginActivity.this);
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(LoginActivity.this, "授权失败 ", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CallbackContext.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSucceed(int requestId, JSONObject result) {
        Toast.makeText(LoginActivity.this, "登录成功 ", Toast.LENGTH_LONG).show();
        try {
            //保存data
            userBean = GsonUtil.GsonToBean(GsonUtil.getJSONObjectKeyVal(result.toString(), ResultUtil.analysisData(result).getString(ResultUtil.MSG)), UserBean.class);
            SharedPreUtils.getInstance(LoginActivity.this).saveValueBySharedPreferences(SharedPreUtils.UID, userBean.getUid());
            SharedPreUtils.getInstance(LoginActivity.this).saveValueBySharedPreferences(SharedPreUtils.TOKEN, userBean.getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SharedPreUtils.getInstance(LoginActivity.this).saveValueBySharedPreferences(SharedPreUtils.HEADIMG, headImg);
        SharedPreUtils.getInstance(LoginActivity.this).saveValueBySharedPreferences(SharedPreUtils.NICK, nick);
        SharedPreUtils.getInstance(LoginActivity.this).saveValueBySharedPreferences(SharedPreUtils.ISONLINE, true);
        finish();
    }

    @Override
    public void onFail(int requestId, String errorMsg) {
        Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_LONG).show();
    }
}

