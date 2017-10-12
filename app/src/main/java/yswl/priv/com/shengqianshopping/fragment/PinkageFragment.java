package yswl.priv.com.shengqianshopping.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yswl.com.klibrary.base.MFragment;
import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.http.HttpClientProxy;
import yswl.com.klibrary.util.L;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.bean.CategoryBean;
import yswl.priv.com.shengqianshopping.bean.ResultUtil;
import yswl.priv.com.shengqianshopping.fragment.adapter.CommonFragmentPagerAdapter;
import yswl.priv.com.shengqianshopping.util.UrlUtil;
import yswl.priv.com.shengqianshopping.view.SlidingTabLayout;

/**
 * 包邮
 */
public class PinkageFragment extends MFragment implements HttpCallback<JSONObject> {

    private static final int REQUEST_ID_CATEGROY_TYPE2 = 102;
    private static final String TAG = PinkageFragment.class.getSimpleName();
    public static PinkageFragment newInstance(String param1, String param2) {
        PinkageFragment fragment = new PinkageFragment();
        return fragment;
    }
    SlidingTabLayout mSlidingTabLayout;
    ViewPager mViewPager;
    private CommonFragmentPagerAdapter mMyFragmentPagerAdapter;

    public PinkageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pinkage, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initUI(view);
        requestCategroy();
    }


    private void initUI(View view) {
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);


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
        mMyFragmentPagerAdapter = new CommonFragmentPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mMyFragmentPagerAdapter);


//        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.color5_4081ff));

    }

    private void requestCategroy() {
        String url = UrlUtil.getUrl(this, R.string.url_category_type_list);
        Map<String, Object> par = new HashMap<>();
        par.put("type", "2");//包邮品类
        HttpClientProxy.getInstance().postAsynSQS(url, REQUEST_ID_CATEGROY_TYPE2, par, this);
    }

    List<CategoryBean> mCategorys_type2;

    @Override
    public void onSucceed(int requestId, JSONObject result) {
        L.e(TAG, "onSucceed -- result :" + result);
        if (ResultUtil.isCodeOK(result)) {
            mCategorys_type2 = CategoryBean.jsonToList(
                    ResultUtil.analysisData(result).optJSONArray(ResultUtil.LIST));
            if (mCategorys_type2 != null) {
                mMyFragmentPagerAdapter.setFragmentList(mCategorys_type2);
                mMyFragmentPagerAdapter.notifyDataSetChanged();
                mViewPager.setOffscreenPageLimit(mCategorys_type2.size());
                mSlidingTabLayout.setViewPager(mViewPager);

            }
        }

    }

    @Override
    public void onFail(int requestId, String errorMsg) {

    }
}
