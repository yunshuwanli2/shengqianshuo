package yswl.priv.com.shengqianshopping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.http.HttpClientProxy;
import yswl.com.klibrary.util.ToastUtil;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.base.MToolBarActivity;
import yswl.priv.com.shengqianshopping.bean.ResultUtil;
import yswl.priv.com.shengqianshopping.fragment.UserCenterFragment;
import yswl.priv.com.shengqianshopping.http.SqsHttpClientProxy;
import yswl.priv.com.shengqianshopping.manager.UserManager;
import yswl.priv.com.shengqianshopping.util.SharedPreUtils;
import yswl.priv.com.shengqianshopping.util.TimerUtil;
import yswl.priv.com.shengqianshopping.util.UrlUtil;
import yswl.priv.com.shengqianshopping.util.VerifyUtils;

/**
 * 绑定手机号
 */

public class BindPhoneActivity extends MToolBarActivity implements HttpCallback<JSONObject> {

    public static void startActivity(Activity context) {
        Intent intent = new Intent(context, BindPhoneActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.bind_phone_edt_phone)
    EditText edtPhone;//手机号码
    @BindView(R.id.bind_phone_edt_vcode)
    EditText edtVcode;//验证码
    @BindView(R.id.bind_phone_btn_get_vcode)
    Button btnGetVcode;//发送验证码
    @BindView(R.id.bind_phone_btn_bind_phone)
    Button btnBindPhone;//绑定手机号码


    private Unbinder unbinder;
    private boolean hasSend = false;//是否发送过验证码
    private final int GET_VCODE_REQUEST_ID = 12356;//获取验证码REQUEST_ID
    private final int BIND_PHONE_REQUEST_ID = 12357;//绑定手机号REQUEST_ID

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);
        unbinder = ButterKnife.bind(this);
        init();
        dataBind();
        eventBind();
    }

    private void init() {
        setTitle("绑定手机号");
        btnGetVcode.setClickable(false);
        btnBindPhone.setClickable(false);
        btnGetVcode.setPressed(true);
        btnBindPhone.setPressed(true);
        edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11) {
                    btnGetVcode.setClickable(true);
                    btnGetVcode.setPressed(false);
                } else {
                    btnGetVcode.setClickable(false);
                    btnGetVcode.setPressed(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtVcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (hasSend && s.length() > 4) {
                    btnBindPhone.setClickable(true);
                    btnBindPhone.setPressed(false);
                } else {
                    btnBindPhone.setClickable(false);
                    btnBindPhone.setPressed(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void dataBind() {

    }

    private void eventBind() {

    }

    @Override
    public void onSucceed(int requestId, JSONObject result) {
        if (ResultUtil.isCodeOK(result)) {
            if (requestId == GET_VCODE_REQUEST_ID) {
                //发送验证码成功
                ToastUtil.showToast("验证码已发送");
                TimerUtil.verifyCodeTimer(btnGetVcode, 60, true);
                hasSend = true;
            } else if (requestId == BIND_PHONE_REQUEST_ID) {
                //绑定成功
                ToastUtil.showToast("绑定手机号成功");
                //需要刷新用户信息 手机号有更新
                UserManager.saveBindPhoneState(this, "true");
//                UserCenterFragment.publishUserInfoRequestEvent();
                finish();
            }
        }
    }

    @Override
    public void onFail(int requestId, String errorMsg) {
        ToastUtil.showToast(errorMsg);
    }

    @OnClick({R.id.bind_phone_btn_get_vcode, R.id.bind_phone_btn_bind_phone})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bind_phone_btn_get_vcode:
                getVcode();
                break;
            case R.id.bind_phone_btn_bind_phone:
                bindPhone();
                break;
        }

    }


    //获取验证码
    private void getVcode() {
        if (TextUtils.isEmpty(edtPhone.getText().toString())) {
            ToastUtil.showToast("手机号为空");
            return;
        } else if (!VerifyUtils.isValidMobileNo(edtPhone.getText().toString())) {
            ToastUtil.showToast("手机号格式错误");
            return;
        }
        String url = UrlUtil.getUrl(BindPhoneActivity.this, R.string.url_verification_code);
        Map<String, Object> map = new HashMap<>();
        map.put("uid", UserManager.getUid(this));
        map.put("token", UserManager.getToken(this));
        map.put("phone", edtPhone.getText().toString());
        SqsHttpClientProxy.postAsynSQS(url, GET_VCODE_REQUEST_ID, map, BindPhoneActivity.this);
    }


    //绑定
    private void bindPhone() {
        if (TextUtils.isEmpty(edtPhone.getText().toString())) {
            ToastUtil.showToast("手机号为空");
            return;
        } else if (!VerifyUtils.isValidMobileNo(edtPhone.getText().toString())) {
            ToastUtil.showToast("手机号格式错误");
            return;
        } else if (!hasSend) {
            ToastUtil.showToast("请获取手机验证码");
            return;
        } else if (TextUtils.isEmpty(edtVcode.getText().toString())) {
            ToastUtil.showToast("验证码为空");
            return;
        }
        String url = UrlUtil.getUrl(BindPhoneActivity.this, R.string.url_check_verification_code);
        Map<String, Object> map = new HashMap<>();
        map.put("phone", edtPhone.getText().toString());
        map.put("code", edtVcode.getText().toString());
        SqsHttpClientProxy.postAsynSQS(url, BIND_PHONE_REQUEST_ID, map, BindPhoneActivity.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        TimerUtil.RemoveRunnable();
    }

}
