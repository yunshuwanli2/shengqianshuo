package yswl.priv.com.shengqianshopping.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import yswl.com.klibrary.base.MFragment;
import yswl.com.klibrary.browser.BrowserActivity;
import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.http.HttpClientProxy;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.activity.BalanceOfPaymentDetailActivity;
import yswl.priv.com.shengqianshopping.activity.BindPhoneActivity;
import yswl.priv.com.shengqianshopping.activity.BindZFBActivity;
import yswl.priv.com.shengqianshopping.activity.InvitationActivity;
import yswl.priv.com.shengqianshopping.activity.LoginActivity;
import yswl.priv.com.shengqianshopping.activity.Myfans2Activity;
import yswl.priv.com.shengqianshopping.activity.ScoreboardActivity;
import yswl.priv.com.shengqianshopping.activity.SettingActivity;
import yswl.priv.com.shengqianshopping.bean.ResultUtil;
import yswl.priv.com.shengqianshopping.bean.UserBean;
import yswl.priv.com.shengqianshopping.event.UserInfoRequestEvent;
import yswl.priv.com.shengqianshopping.event.UserInfoEvent;
import yswl.priv.com.shengqianshopping.manager.UserManager;
import yswl.priv.com.shengqianshopping.util.AlibcUtil;
import yswl.priv.com.shengqianshopping.util.UrlUtil;

/**
 * Created by kangpAdministrator on 2017/9/27 0027.
 * Emial kangpeng@yunhetong.net
 */

public class UserCenterFragment extends MFragment implements HttpCallback<JSONObject> {
    public static final String TAG = UserCenterFragment.class.getSimpleName();

    /***************EvenBus start***********************/
    @Subscribe(sticky = true)
    public void onEvent(UserInfoEvent event) {
        EventBus.getDefault().removeStickyEvent(UserInfoEvent.class);
        updateUI(event.user);
    }

    public static void publishUpdateUserInfoEvent(UserBean userinfo) {
        EventBus.getDefault().postSticky(new UserInfoEvent(userinfo));
    }

    @Subscribe(sticky = true)
    public void onEvent(UserInfoRequestEvent event) {
        EventBus.getDefault().removeStickyEvent(UserInfoRequestEvent.class);
        requestUserInfo();
    }

    public static void publishUserInfoRequestEvent() {
        EventBus.getDefault().removeStickyEvent(UserInfoRequestEvent.class);
        EventBus.getDefault().postSticky(new UserInfoRequestEvent());
    }


    /***************EvenBus end***********************/

    public static MFragment newInstance() {
        return new UserCenterFragment();
    }

    @BindView(R.id.iv_head)
    ImageView ivHead;

    @BindView(R.id.tv_user_name)
    TextView tvUserName;

    @BindView(R.id.tv_balance)
    TextView tvBalance;

    @BindView(R.id.tv_save_money)
    TextView tvSaveMoney;

    private Unbinder unbinder;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, view);
        activity = getActivity();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        LinearLayout ll_head = (LinearLayout) view.findViewById(R.id.ll_head);
//        ImageView head = (ImageView) view.findViewById(R.id.iv_head);
//        TextView username = (TextView) view.findViewById(R.id.tv_user_name);

        if (!isLogin()) {
            LoginActivity.startActivity(getActivity());
        } else if (!isAuth()) {
            BindPhoneActivity.startActivity(getActivity());
        } else {
            updateUI(UserManager.getUserInfo(activity));
        }
    }

    private final int GET_USERINFO_REQUESTID = 1003;


    void updateUI(UserBean userBean) {
        if (userBean == null) return;
        tvUserName.setText(userBean.getNickname());
        Glide.with(activity).load(userBean.getAvatar()).into(ivHead);
        if (userBean.getAsset() != null) {
            tvBalance.setText("¥ " + userBean.getAsset().getRemainder());
            tvSaveMoney.setText("已用省钱说节省:" + userBean.getAsset().getRevenue());
        }
    }

    public boolean isLogin() {
        return UserManager.isLogin(getContext());
    }

    public boolean isAuth() {
        return UserManager.isBindPhone(getContext());
    }


    void requestUserInfo() {
        String uid = UserManager.getUid(getActivity());
        UserManager.rquestUserInfoDetail(getActivity(), uid, this, GET_USERINFO_REQUESTID);
    }

    @Override
    public void onSucceed(int requestId, JSONObject result) {
        if (requestId == GET_USERINFO_REQUESTID && ResultUtil.isCodeOK(result)) {
            Log.i("znh", result.toString() + "----用户详细信息");
//            UserBean userInfo = UserBean.jsonToBean(ResultUtil.analysisData(result));
//            updateUI(userInfo);
            //保存状态
            UserManager.saveLogin(getActivity());
            UserManager.saveInfo(getActivity(), ResultUtil.analysisData(result).toString());
        }
    }

    @Override
    public void onFail(int requestId, String errorMsg) {

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.user_info_ll_setting,
            R.id.user_info_ll_balance_of_payments, R.id.user_info_ll_fans,
            R.id.user_info_ll_order, R.id.user_info_ll_shopping_cart,
            R.id.user_info_ll_heroes_list, R.id.user_info_ll_invitation,
            R.id.user_info_ll_customer_service, R.id.ll_sqtx})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_info_ll_setting:
                SettingActivity.startActivity(getActivity());
                break;
//            case R.id.user_info_ll_tracks:
//                //TODO 我的足迹
//                break;
            case R.id.user_info_ll_balance_of_payments:
                //TODO 收支明细
                BalanceOfPaymentDetailActivity.startAct(getContext());
                break;
            case R.id.user_info_ll_fans:
                Myfans2Activity.startAct(getActivity());
                break;
            case R.id.user_info_ll_order:
                AlibcUtil.gotoAliOrder(getActivity());
                break;
            case R.id.user_info_ll_shopping_cart:
                AlibcUtil.gotoAliShoppingChe(getActivity());
                break;
            case R.id.user_info_ll_heroes_list:
                startActivity(new Intent(activity, ScoreboardActivity.class));
                break;
            case R.id.user_info_ll_invitation:
                //TODO 邀请
//                InvitationActivity.startAct(getContext());
                break;
            case R.id.user_info_ll_customer_service:
                BrowserActivity.start2("常见问题", "http://api.saveduoduo.com/config/faq", getContext());
                break;
            case R.id.ll_sqtx:
                //TODO 申请提现
//                if (UserManager.isBindZFB(getActivity())) {
//                    //提现服务
//                } else {
//                    BindZFBActivity.startActivityForResult(this);
//                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Activity.RESULT_FIRST_USER && resultCode == Activity.RESULT_OK) {
            //
        }
    }

    void requestTX() {
        String url = UrlUtil.getUrl(this, R.string.url_withdraw_request);
        String uid = UserManager.getUid(getActivity());
        String amount = "";
        String phone = "";
        UserBean userInfo = UserManager.getUserInfo(getActivity());
        if (userInfo != null)
            phone = userInfo.getPhone();
//        HttpClientProxy.getInstance().postAsynSQS(url, map, 122, this);
    }
}