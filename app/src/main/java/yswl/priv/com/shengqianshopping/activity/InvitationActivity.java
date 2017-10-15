package yswl.priv.com.shengqianshopping.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import yswl.com.klibrary.util.L;
import yswl.com.klibrary.util.MD5Util;
import yswl.com.klibrary.util.UrlParamsConfig;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.base.MToolBarActivity;
import yswl.priv.com.shengqianshopping.bean.UserBean;
import yswl.priv.com.shengqianshopping.http.SqsHttpClientProxy;
import yswl.priv.com.shengqianshopping.manager.UserManager;
import yswl.priv.com.shengqianshopping.util.SharedPreUtils;
import yswl.priv.com.shengqianshopping.util.UMShareUtils;
import yswl.priv.com.shengqianshopping.util.UrlUtil;

/**
 * 有奖邀请
 */
public class InvitationActivity extends MToolBarActivity {


    @BindView(R.id.invitation_tv_reward_prompt)
    TextView vRewardPrompt;
    @BindView(R.id.invitation_btn_invitation)
    Button tnInvitation;
    @BindView(R.id.invitation_tv_invitation_people_num)
    TextView vInvitationPeopleNum;
    @BindView(R.id.invitation_tv_reward_num)
    TextView tvRewardNum;

    private Unbinder unbinder;
    private UserBean userInfo;

    public static void startAct(Context context) {
        context.startActivity(new Intent(context, InvitationActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);
        unbinder = ButterKnife.bind(this);
        init();
        eventBind();
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
        userInfo = UserManager.getUserInfo(this);
        setTitle("有奖邀请");
        vRewardPrompt.setText(Html.fromHtml("最高可获得<font color='#ff5b68'><big>¥200</big></font>元奖励"));
        vInvitationPeopleNum.setText(userInfo.getInvite().totol + "人");
        tvRewardNum.setText("¥" + userInfo.getInvite().reward);


    }

    private void eventBind() {
        tnInvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享
                String token = SharedPreUtils.getInstance(InvitationActivity.this).getValueBySharedPreferences(SharedPreUtils.TOKEN, "");
                String rnd = System.currentTimeMillis() + "";
                Map<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("uid", userInfo.getUid());
                paramsMap.put("token", token);
                paramsMap.put("rnd", rnd);
                String url = String.format(UrlUtil.getUrl(InvitationActivity.this, R.string.url_sqs_share), userInfo.getUid(), token, rnd, getMdk(paramsMap));
                UMShareUtils.share(InvitationActivity.this, url,"XXX","XXXXXXXXXXXXXXXXXXXXX");
            }
        });
    }

    private static String getMdk(Map<String, Object> params) {
        StringBuffer beforeSign = UrlParamsConfig.getBeforeSign3(params);
        beforeSign.append(SqsHttpClientProxy.SQS_SECRET);
        L.e("SqsHttpClientProxy", "排序：拼接: " + beforeSign.toString());
        String afterSign = MD5Util.MD5(beforeSign.toString());
        return afterSign;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
