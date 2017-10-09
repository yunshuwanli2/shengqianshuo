package yswl.priv.com.shengqianshopping.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import yswl.com.klibrary.base.MFragment;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.base.MToolBarActivity;
import yswl.priv.com.shengqianshopping.bean.BalanceDetailItemBean;
import yswl.priv.com.shengqianshopping.bean.OrderBean;
import yswl.priv.com.shengqianshopping.fragment.BalanceDetailItemFragment;
import yswl.priv.com.shengqianshopping.fragment.WithDrawItemFragment;
import yswl.priv.com.shengqianshopping.view.SlidingTabLayout;


public class BalanceOfPaymentDetailActivity extends MToolBarActivity {
    public static void startAct(Context context) {
        context.startActivity(new Intent(context, BalanceOfPaymentDetailActivity.class));
    }

    @BindView(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTabLayout;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private BalanceOfPaymentAdapter mMyFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_of_payment_detail);
        setTitle("收支明细");
        ButterKnife.bind(this);
        mSlidingTabLayout.setCustomTabView(R.layout.slide_tab_view, R.id.slib_item_text);
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorAccent);
            }

            @Override
            public int getDividerColor(int position) {
                return getResources().getColor(R.color.colorAccent);
            }
        });
        mSlidingTabLayout.setTabTitleTextSize(15);
        mSlidingTabLayout.setTitleTextColor(getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.background_dark));
        List<MFragment> fragments = new ArrayList<>();
        fragments.add(new BalanceDetailItemFragment());
        fragments.add(new WithDrawItemFragment());
        mMyFragmentPagerAdapter = new BalanceOfPaymentAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mMyFragmentPagerAdapter);
        mSlidingTabLayout.setViewPager(mViewPager);

    }


    class BalanceOfPaymentAdapter extends FragmentPagerAdapter {
        List<MFragment> fragments;

        public BalanceOfPaymentAdapter(FragmentManager fm, List<MFragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0)
                return "收支明细";
            return "提现历史";
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments != null && fragments.size() > 0 ? fragments.size() : 0;
        }
    }

}
