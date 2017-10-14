package yswl.priv.com.shengqianshopping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.http.okhttp.MSPUtils;
import yswl.com.klibrary.util.ToastUtil;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.base.MToolBarActivity;
import yswl.priv.com.shengqianshopping.bean.ResultUtil;
import yswl.priv.com.shengqianshopping.bean.UserBean;
import yswl.priv.com.shengqianshopping.http.SqsHttpClientProxy;
import yswl.priv.com.shengqianshopping.manager.UserManager;
import yswl.priv.com.shengqianshopping.util.UrlUtil;

/**
 * 提现申请
 */

public class ApplyForCashWithdrawalActivity extends MToolBarActivity implements HttpCallback<JSONObject> {
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.edt_apply_for_money)
    EditText edtApplyForMoney;
    @BindView(R.id.tv_zfb_account)
    TextView tvZfbAccount;
    @BindView(R.id.tv_zfb_name)
    TextView tvName;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    private double remainder = 0.0;//余额

    public static void startActivity(Activity context) {
        Intent intent = new Intent(context, ApplyForCashWithdrawalActivity.class);
        context.startActivity(intent);
    }


    UserBean userIfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applyfor_cash_withdrawal);
        ButterKnife.bind(this);
        setTitle("申请提现");
        init();

    }

    private void init() {
        userIfo = UserManager.getUserInfo(this);
        if (userIfo == null) return;
        remainder = Double.parseDouble(userIfo.asset.remainder);
        tvBalance.setText(Html.fromHtml("<font color='#e0ff3366'>" + remainder + "</font>元"));
        tvName.setText(userIfo.getAlipay().name);
        tvZfbAccount.setText(userIfo.getAlipay().account);
    }

    @OnClick({R.id.btn_submit})
    public void onClick(View view) {
        if (checkData()) {
            submitData();
        }
    }


    @Override
    public void onSucceed(int requestId, JSONObject result) {
        if (requestId == 122 && ResultUtil.isCodeOK(result)) {
            ToastUtil.showToast("申请提现已提交,请耐心等待1-2个工作日");
            finish();
        }
    }

    @Override
    public void onFail(int requestId, String errorMsg) {
        ToastUtil.showToast(errorMsg);
    }

    // 校验数据
    private boolean checkData() {
        if (TextUtils.isEmpty(edtApplyForMoney.getText().toString())) {
            ToastUtil.showToast("请填写提现金额");
            return false;
        } else if (TextUtils.isEmpty(edtPhone.getText().toString())) {
            ToastUtil.showToast("请填手机号");
            return false;
        } else if (Double.parseDouble(edtApplyForMoney.getText().toString()) < 10) {
            ToastUtil.showToast("输入金额必须大于10");
            return false;
        } else if (Double.parseDouble(edtApplyForMoney.getText().toString()) > remainder) {
            ToastUtil.showToast("输入金额必须小于您的余额");
            return false;
        }
        return true;
    }

    // 提交数据--提现申请
    private void submitData() {
        String url = UrlUtil.getUrl(this, R.string.url_withdraw_request);
        String uid = UserManager.getUid(this);
        String token = UserManager.getToken(this);
        String amount = edtApplyForMoney.getText().toString();
        String phone = edtPhone.getText().toString();
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("uid", uid);
        paramsMap.put("token", token);
        paramsMap.put("amount", amount);
        paramsMap.put("phone", phone);
        SqsHttpClientProxy.postAsynSQS(url, 122, paramsMap, this);
    }


}
