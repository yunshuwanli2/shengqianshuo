package yswl.priv.com.shengqianshopping.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcMyCartsPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyOrdersPage;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import yswl.com.klibrary.base.MFragment;
import yswl.com.klibrary.imgloader.ImageLoaderProxy;
import yswl.com.klibrary.util.L;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.activity.BalanceOfPaymentDetailActivity;
import yswl.priv.com.shengqianshopping.activity.InvitationActivity;
import yswl.priv.com.shengqianshopping.activity.LoginActivity;
import yswl.priv.com.shengqianshopping.activity.MyFansActivity;
import yswl.priv.com.shengqianshopping.activity.ScoreboardActivity;
import yswl.priv.com.shengqianshopping.activity.SettingActivity;
import yswl.priv.com.shengqianshopping.bean.UserBean;
import yswl.priv.com.shengqianshopping.manager.UserManager;
import yswl.priv.com.shengqianshopping.util.AlibcUtil;
import yswl.priv.com.shengqianshopping.util.SharedPreUtils;

/**
 * Created by kangpAdministrator on 2017/9/27 0027.
 * Emial kangpeng@yunhetong.net
 */

public class UserCenterFragment extends MFragment {
    public static final String TAG = UserCenterFragment.class.getSimpleName();
    @BindView(R.id.iv_head)
    ImageView ivHead;

    @BindView(R.id.tv_user_name)
    TextView tvUserName;

    @BindView(R.id.tv_balance)
    TextView tvBalance;

    @BindView(R.id.tv_integral)
    TextView tvIntegral;


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
        LinearLayout ll_head = (LinearLayout) view.findViewById(R.id.ll_head);
        ImageView head = (ImageView) view.findViewById(R.id.iv_head);
        TextView username = (TextView) view.findViewById(R.id.tv_user_name);
        ll_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UserManager.isOnline(getActivity()))
                    startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        initView();
    }


    @OnClick({R.id.ll_head, R.id.user_info_ll_setting, R.id.user_info_ll_tracks,
            R.id.user_info_ll_balance_of_payments, R.id.user_info_ll_fans,
            R.id.user_info_ll_order, R.id.user_info_ll_shopping_cart,
            R.id.user_info_ll_heroes_list, R.id.user_info_ll_invitation,
            R.id.user_info_ll_customer_service})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_head:
                if (!UserManager.isOnline(getActivity()))
                    LoginActivity.startActivity(getActivity());
                break;
            case R.id.user_info_ll_setting:
                startActivity(new Intent(activity, SettingActivity.class));
                break;
            case R.id.user_info_ll_tracks:
                //TODO 我的足迹

                break;
            case R.id.user_info_ll_balance_of_payments:
                //TODO 收支明细
                if (UserManager.isOnline(getContext()))
                    BalanceOfPaymentDetailActivity.startAct(getContext());
                break;
            case R.id.user_info_ll_fans:
                //TODO 我的粉丝
                if (UserManager.isOnline(getContext()))
                    MyFansActivity.startAct(getActivity());
                else
                    LoginActivity.startActivity(getActivity());
                break;
            case R.id.user_info_ll_order:
                AlibcUtil.gotoAliOrder(getActivity());
                break;
            case R.id.user_info_ll_shopping_cart:
                AlibcUtil.gotoAliShoppingChe(getActivity());
                break;
            case R.id.user_info_ll_heroes_list:
                if (UserManager.isOnline(getContext()))
                    startActivity(new Intent(activity, ScoreboardActivity.class));
                break;
            case R.id.user_info_ll_invitation:
                //TODO 邀请
                InvitationActivity.startAct(getContext());
                break;
            case R.id.user_info_ll_customer_service:
                //TODO 客服
                break;
        }
    }


    //初始化界面--登录状态设置头像-昵称
    private void initView() {
        if (SharedPreUtils.getInstance(activity).getBooleanValueBySharedPreferences(SharedPreUtils.ISONLINE, false)) {
            UserBean userBean = UserManager.getUserInfo(activity);
            tvUserName.setText(userBean.getNickname());
            Glide.with(activity).load(userBean.getAvatar()).into(ivHead);
            tvBalance.setText("￥" + userBean.getAsset().getRemainder());
            tvIntegral.setText(userBean.getAsset().getIntegral() + "");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}