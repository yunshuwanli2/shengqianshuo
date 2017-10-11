package yswl.priv.com.shengqianshopping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.base.MToolBarActivity;
import yswl.priv.com.shengqianshopping.bean.UserBean;
import yswl.priv.com.shengqianshopping.manager.UserManager;
import yswl.priv.com.shengqianshopping.util.SharedPreUtils;

public class UserInfoActivity extends MToolBarActivity implements HttpCallback<JSONObject> {

    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;
    @BindView(R.id.user_info_img_head)
    ImageView imgHead;
    @BindView(R.id.user_info_tv_user_name)
    TextView tvUserName;
    @BindView(R.id.user_info_tv_phone)
    TextView tvPhone;
    @BindView(R.id.user_info_tv_QQ_num)
    TextView tvQQNum;
    @BindView(R.id.user_info_tv_email)
    TextView tvEmail;
    @BindView(R.id.user_info_tv_taobao_account)
    TextView tvTaobaoAccount;
    private Unbinder unbinder;

    public static void startActivity(Activity context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        unbinder = ButterKnife.bind(this);
        requestUserInfo();
        setUserInfo();
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


    //设置用户信息
    private void setUserInfo() {
        UserBean userBean = UserManager.getUserInfo(this);
        Glide.with(this).load(userBean.avatar).into(imgHead);
        tvUserName.setText(userBean.getNickname());
        tvPhone.setText(userBean.getPhone());
        if (TextUtils.isEmpty(userBean.getQq())) {
            tvQQNum.setBackgroundResource(R.mipmap.ic_allow_right);
        } else {
            tvQQNum.setText(userBean.getQq());
        }
        if (TextUtils.isEmpty(userBean.getEmail())) {
            tvEmail.setBackgroundResource(R.mipmap.ic_allow_right);
        } else {
            tvEmail.setText(userBean.getEmail());
        }
        tvTaobaoAccount.setText(userBean.getAlipay().aliAccount);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
