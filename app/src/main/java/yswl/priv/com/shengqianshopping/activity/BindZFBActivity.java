package yswl.priv.com.shengqianshopping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yswl.com.klibrary.base.MFragment;
import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.http.HttpClientProxy;
import yswl.com.klibrary.util.ToastUtil;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.base.MToolBarActivity;
import yswl.priv.com.shengqianshopping.http.SqsHttpClientProxy;
import yswl.priv.com.shengqianshopping.manager.UserManager;
import yswl.priv.com.shengqianshopping.util.SharedPreUtils;
import yswl.priv.com.shengqianshopping.util.UrlUtil;

public class BindZFBActivity extends MToolBarActivity implements HttpCallback<JSONObject> {
    public static void startActivityForResult(MFragment fragment) {
        Intent intent = new Intent(fragment.getContext(), BindZFBActivity.class);
        fragment.startActivityForResult(intent, Activity.RESULT_FIRST_USER);
    }

    @BindView(R.id.et_name)
    EditText name;

    @BindView(R.id.et_zfb_numb)
    EditText zfbNumb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_zfb);
        ButterKnife.bind(this);
        setTitle("个人财务设置");


    }

    @OnClick({R.id.btn_submit})
    public void onClick(View view) {
        requestData();
    }

    private void requestData() {
        String uid = UserManager.getUid(this);
        String nameS = name.getText().toString().trim();
        String zfb = zfbNumb.getText().toString().trim();

        if (TextUtils.isEmpty(nameS)) {
            ToastUtil.showToast("请填写支付宝名称");
            return;
        }
        if (TextUtils.isEmpty(zfb)) {
            ToastUtil.showToast("请填写支付宝账号");
            return;
        }
        String url = UrlUtil.getUrl(this, R.string.url_bind_zfb);
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("aliAccount", zfb);
        map.put("aliRealName", nameS);
        SqsHttpClientProxy.postAsynSQS(url, 100, map, this);
    }

    @Override
    public void onSucceed(int requestId, JSONObject result) {
        Intent mIntent = new Intent();
        this.setResult(Activity.RESULT_OK, mIntent);
        UserManager.bindZFB(BindZFBActivity.this, true);
        finish();
    }

    @Override
    public void onFail(int requestId, String errorMsg) {

    }
}
