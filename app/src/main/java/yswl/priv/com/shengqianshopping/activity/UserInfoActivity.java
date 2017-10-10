package yswl.priv.com.shengqianshopping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.json.JSONObject;

import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.base.MToolBarActivity;
import yswl.priv.com.shengqianshopping.manager.UserManager;
import yswl.priv.com.shengqianshopping.util.SharedPreUtils;

public class UserInfoActivity extends MToolBarActivity implements HttpCallback<JSONObject> {
    public static void startActivity(Activity context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        requestUserInfo();
    }

    void requestUserInfo() {
        String uid = SharedPreUtils.getInstance(this).getValueBySharedPreferences(SharedPreUtils.UID, "");
        UserManager.rquestUserInfoDetail(this, uid, this, 1009);
    }

    @Override
    public void onSucceed(int requestId, JSONObject result) {

    }

    @Override
    public void onFail(int requestId, String errorMsg) {

    }
}
