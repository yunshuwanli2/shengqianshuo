package yswl.priv.com.shengqianshopping.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bigkoo.convenientbanner.ConvenientBanner;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yswl.com.klibrary.base.MFragment;
import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.http.HttpClientProxy;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.activity.AdvanceActivity;
import yswl.priv.com.shengqianshopping.activity.CrazyBuyActivity;
import yswl.priv.com.shengqianshopping.activity.RecommendActivity;
import yswl.priv.com.shengqianshopping.activity.SearchActivity;
import yswl.priv.com.shengqianshopping.activity.Top100Activity;
import yswl.priv.com.shengqianshopping.banner.BannerBean;
import yswl.priv.com.shengqianshopping.banner.BannerUtil;
import yswl.priv.com.shengqianshopping.bean.CategoryBean;
import yswl.priv.com.shengqianshopping.bean.ResultUtil;
import yswl.priv.com.shengqianshopping.fragment.adapter.GridRecyclerAdapter;
import yswl.priv.com.shengqianshopping.util.UrlUtil;

public class HomeFragment2 extends MFragment implements HttpCallback<JSONObject>, View.OnClickListener {
    private static final String FRAGMENT_TAG = "HomeFragment2_ItemFragment";

    private static final int REQUEST_ID_CATEGROY = 100;
    private static final int REQUEST_ID_BANNER = 101;

    ConvenientBanner mConvenientBanner;
    private BannerUtil banner;
    List<BannerBean> mImags;
    List<CategoryBean> mCategorys;


    public HomeFragment2() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home2, container, false);
    }


    //    LinearLayout mCrazyBuy, mAdvise, mSort, mPlan;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initUI(view);
        initToolBar(view);
        initBanner();
        requestCategroy();
        requestBanner();
    }

    private void initUI(View view) {
        mConvenientBanner = (ConvenientBanner) view.findViewById(R.id.convenientBanner);
        view.findViewById(R.id.ll_fkq).setOnClickListener(this);
        view.findViewById(R.id.ll_tj).setOnClickListener(this);
        view.findViewById(R.id.ll_sort).setOnClickListener(this);
        view.findViewById(R.id.ll_plan).setOnClickListener(this);
    }

    private void initToolBar(View view) {
        view.findViewById(R.id.home_toolbar_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.startActivity(getActivity());
            }
        });
        final ImageView imageView = (ImageView) view.findViewById(R.id.home_mune);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindow popupWindow = new PopupWindow(getActivity());
                popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_grid_recyclerview, null);
                RecyclerView recyView = (RecyclerView) view.findViewById(R.id.recycler_view);
                GridRecyclerAdapter adapter = new GridRecyclerAdapter();
                adapter.setCategoryList(mCategorys);
                adapter.setOnItemClickListener(new GridRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View view, int position) {
                        if (mCategorys != null) ;
                        //TODO

                    }
                });
                recyView.setAdapter(adapter);
                popupWindow.setContentView(view);
                popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
                popupWindow.setOutsideTouchable(false);
                popupWindow.setFocusable(true);
                popupWindow.showAsDropDown(imageView);

            }
        });


    }


    private void requestCategroy() {
        String url = UrlUtil.getUrl(this, R.string.url_category_type_list);
        Map<String, Object> par = new HashMap<>();
        par.put("type", "1");
        HttpClientProxy.getInstance().postAsyn(url, REQUEST_ID_CATEGROY, par, this);
    }

    private void requestBanner() {
        String url = UrlUtil.getUrl(this, R.string.url_banner_list);
        HttpClientProxy.getInstance().postAsyn(url, REQUEST_ID_BANNER, null, this);
    }

    void initBanner() {
        if (null == banner) {
            banner = new BannerUtil();
        }
        banner.setConvenientBanner(mConvenientBanner);
        banner.loadPic(mImags);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_fkq:
                //TODO 疯狂抢购页
                CrazyBuyActivity.startActivity(getActivity());
                break;
            case R.id.ll_tj:
                //TODO 小编推荐页
                RecommendActivity.startActivity(getActivity());
                break;
            case R.id.ll_sort:
                //TODO top100排名页
                Top100Activity.startActivity(getActivity());
                break;
            case R.id.ll_plan:
                //TODO 预告页
                AdvanceActivity.startActivity(getActivity());
                break;

        }
    }


    @Override
    public void onSucceed(int requestId, final JSONObject result) {
        if (ResultUtil.isCodeOK(result)) {

            switch (requestId) {
                case REQUEST_ID_BANNER:
                    mImags = BannerBean.jsonToList(
                            ResultUtil.analysisData(result).optJSONArray(ResultUtil.LIST));
                    banner.loadPic(mImags);
                    break;
                case REQUEST_ID_CATEGROY:
                    mCategorys = CategoryBean.jsonToList(
                            ResultUtil.analysisData(result).optJSONArray(ResultUtil.LIST));

                    //TODO 赋值
                    if (mCategorys != null && mCategorys.size() > 0) {
                        addProductListModule(mCategorys.get(0));
                    }
                    break;
            }
        }

    }

    private void addProductListModule(CategoryBean category) {
        getChildFragmentManager().beginTransaction().replace(R.id.content, ItemFragment.newInstance(category), FRAGMENT_TAG).commit();
    }

    @Override
    public void onFail(int requestId, String errorMsg) {

    }


}
