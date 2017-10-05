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
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import yswl.com.klibrary.base.MFragment;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.activity.LoginActivity;
import yswl.priv.com.shengqianshopping.activity.SettingActivity;

/**
 * Created by kangpAdministrator on 2017/9/27 0027.
 * Emial kangpeng@yunhetong.net
 */

public class UserInfoFragment2 extends MFragment {

    private Unbinder unbinder;
    @BindView(R.id.user_info_ll_setting)
    LinearLayout lSetting;
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

                break;
            case R.id.user_info_ll_shopping_cart:

                break;
            case R.id.user_info_ll_heroes_list:

                break;
            case R.id.user_info_ll_invitation:

                break;
            case R.id.user_info_ll_customer_service:

                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}