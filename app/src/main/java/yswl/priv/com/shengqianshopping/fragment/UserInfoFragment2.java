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
import yswl.priv.com.shengqianshopping.activity.LoginActivity;
import yswl.priv.com.shengqianshopping.activity.ScoreboardActivity;
import yswl.priv.com.shengqianshopping.activity.SettingActivity;
import yswl.priv.com.shengqianshopping.bean.UserBean;
import yswl.priv.com.shengqianshopping.manager.UserManager;
import yswl.priv.com.shengqianshopping.util.SharedPreUtils;

/**
 * Created by kangpAdministrator on 2017/9/27 0027.
 * Emial kangpeng@yunhetong.net
 */

public class UserInfoFragment2 extends MFragment {
    public static final String TAG = UserInfoFragment2.class.getSimpleName();
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
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        initView();
    }


    @OnClick({R.id.user_info_ll_setting, R.id.user_info_ll_tracks, R.id.user_info_ll_balance_of_payments, R.id.user_info_ll_fans, R.id.user_info_ll_order, R.id.user_info_ll_shopping_cart, R.id.user_info_ll_heroes_list, R.id.user_info_ll_invitation, R.id.user_info_ll_customer_service})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_info_ll_setting:
                startActivity(new Intent(activity, SettingActivity.class));
                break;
            case R.id.user_info_ll_tracks:

                break;
            case R.id.user_info_ll_balance_of_payments:

                break;
            case R.id.user_info_ll_fans:

                break;
            case R.id.user_info_ll_order:
                gotoAliOrder();
                break;
            case R.id.user_info_ll_shopping_cart:
                gotoAliShoppingChe();
                break;
            case R.id.user_info_ll_heroes_list:
                startActivity(new Intent(activity, ScoreboardActivity.class));
                break;
            case R.id.user_info_ll_invitation:

                break;
            case R.id.user_info_ll_customer_service:

                break;
        }
    }


    void gotoAliShoppingChe() {
        Map<String, String> exParams = new HashMap<>();
        exParams.put(AlibcConstants.ISV_CODE, "appisvcode");
        AlibcBasePage myCartsPage = new AlibcMyCartsPage();
        AlibcShowParams showParams = new AlibcShowParams(OpenType.H5, true);
        AlibcTrade.show(getActivity(), myCartsPage, showParams, null, exParams,
                new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(TradeResult tradeResult) {
                        L.e(TAG, "购物车操作 -- 返回成功 result : " + tradeResult.resultType.name());
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        L.e(TAG, "购物车操作 -- 返回失败 code:" + code + " msg: " + msg);
                    }
                });

    }

    void gotoAliOrder() {

        Map<String, String> exParams = new HashMap<>();
        exParams.put(AlibcConstants.ISV_CODE, "appisvcode");
        /**
         * @param status   默认跳转页面；填写：0：全部；1：待付款；2：待发货；3：待收货；4：待评价
         * @param allOrder false 进行订单分域（只展示通过当前app下单的订单），true 显示所有订单
         */
        AlibcBasePage ordersPage = new AlibcMyOrdersPage(0, false);
        AlibcShowParams showParams = new AlibcShowParams(OpenType.H5, true);
        AlibcTrade.show(getActivity(), ordersPage, showParams, null, exParams,
                new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(TradeResult tradeResult) {
                        L.e(TAG, "订单操作 -- 返回成功 result : " + tradeResult.resultType.name());
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        L.e(TAG, "订单操作 -- 返回失败 code:" + code + " msg: " + msg);
                    }
                });

    }

    //初始化界面--登录状态设置头像-昵称
    private void initView() {
        if (SharedPreUtils.getInstance(activity).getValueBySharedPreferences(SharedPreUtils.ISONLINE, false)) {
            UserBean userBean = UserManager.getUserInfo(activity);
            tvUserName.setText(userBean.getNickname());
            ImageLoaderProxy.getInstance().displayImage(userBean.getAvatar(), ivHead);
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