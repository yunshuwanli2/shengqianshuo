package yswl.priv.com.shengqianshopping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.ali.auth.third.core.model.User;
import com.ali.auth.third.ui.context.CallbackContext;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.alibaba.baichuan.android.trade.callback.AlibcLoginCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import yswl.com.klibrary.MApplication;
import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.http.HttpClientProxy;
import yswl.com.klibrary.util.GsonUtil;
import yswl.com.klibrary.util.L;
import yswl.com.klibrary.util.ToastUtil;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.base.MToolBarActivity;
import yswl.priv.com.shengqianshopping.bean.ResultUtil;
import yswl.priv.com.shengqianshopping.bean.UserBean;
import yswl.priv.com.shengqianshopping.fragment.UserCenterFragment;
import yswl.priv.com.shengqianshopping.manager.UserManager;
import yswl.priv.com.shengqianshopping.util.SharedPreUtils;
import yswl.priv.com.shengqianshopping.util.UrlUtil;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends MToolBarActivity implements HttpCallback<JSONObject> {
    public static void startActivity(Activity context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    private static final String TAG = "LoginActivity";
    private final int LOGIN_REQUESTID = 1002;

    private String phoneStatus = "0";// 用户是否进行手机号绑定 0未绑定 1已绑定

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
                L.i(TAG, "获取淘宝用户信息: " + AlibcLogin.getInstance().getSession());
                String nick = AlibcLogin.getInstance().getSession().nick;
                String headImg = AlibcLogin.getInstance().getSession().avatarUrl;
                String url = UrlUtil.getUrl(LoginActivity.this, R.string.url_login);
                Map<String, Object> map = new HashMap<>();
                map.put("channel", "1");
                map.put("channelUid", AlibcLogin.getInstance().getSession().userid);
                map.put("channelNickname", nick);
                map.put("channelAvatar", headImg);
                map.put("aFrom", "");
                HttpClientProxy.getInstance().postAsynSQS(url, LOGIN_REQUESTID, map, LoginActivity.this);
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtil.showToast("授权失败 请重试");
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CallbackContext.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSucceed(int requestId, final JSONObject result) {
        if (requestId == LOGIN_REQUESTID && ResultUtil.isCodeOK(result)) {
            String uid = GsonUtil.getJSONObjectKeyVal(GsonUtil.getJSONObjectKeyVal(result.toString(), ResultUtil.MSG), "uid");
            String token = GsonUtil.getJSONObjectKeyVal(GsonUtil.getJSONObjectKeyVal(result.toString(), ResultUtil.MSG), "token");
            String phoneStatus = GsonUtil.getJSONObjectKeyVal(GsonUtil.getJSONObjectKeyVal(result.toString(), ResultUtil.MSG), "phoneStatus");

            UserManager.saveToken(this, token);
            UserManager.saveUid(this, uid);
            UserManager.saveBindPhoneState(this, phoneStatus);

            requestUserInfo();


        } else if (requestId == GET_USERINFO_REQUESTID && ResultUtil.isCodeOK(result)) {
            Log.i("znh", result.toString() + "----用户详细信息");
            UserBean userInfo = UserBean.jsonToBean(ResultUtil.analysisData(result));
            if (!UserManager.isBindPhone(this)) {
                BindPhoneActivity.startActivity(this);
                UserCenterFragment.publishUpdateUserInfoEvent(userInfo);
            }

            MApplication.getApplication().getGolbalHander().post(new Runnable() {
                @Override
                public void run() {
                    UserManager.saveLogin(LoginActivity.this);
                    UserManager.saveInfo(LoginActivity.this, ResultUtil.analysisData(result).toString());
                }
            });
            finish();
        }
    }

    @Override
    public void onFail(int requestId, String errorMsg) {
        Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_LONG).show();
    }

    private final int GET_USERINFO_REQUESTID = 1003;

    void requestUserInfo() {
        String uid = UserManager.getUid(this);
        UserManager.rquestUserInfoDetail(this, uid, this, GET_USERINFO_REQUESTID);
    }
}

