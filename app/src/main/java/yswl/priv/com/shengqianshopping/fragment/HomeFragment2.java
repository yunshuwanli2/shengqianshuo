package yswl.priv.com.shengqianshopping.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;

import org.json.JSONObject;

import java.security.acl.Group;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yswl.com.klibrary.base.MFragment;
import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.http.HttpClientProxy;
import yswl.com.klibrary.util.L;
import yswl.com.klibrary.util.ToastUtil;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.activity.AdvanceActivity;
import yswl.priv.com.shengqianshopping.activity.CrazyBuyActivity;
import yswl.priv.com.shengqianshopping.activity.RecommendActivity;
import yswl.priv.com.shengqianshopping.activity.SearchActivity;
import yswl.priv.com.shengqianshopping.activity.Top100Activity;
import yswl.priv.com.shengqianshopping.banner.BannerBean;
import yswl.priv.com.shengqianshopping.banner.BannerUtil;
import yswl.priv.com.shengqianshopping.banner.SortEnum;
import yswl.priv.com.shengqianshopping.bean.CategoryBean;
import yswl.priv.com.shengqianshopping.bean.ProductDetail;
import yswl.priv.com.shengqianshopping.bean.ResultUtil;
import yswl.priv.com.shengqianshopping.fragment.adapter.GridRecyclerAdapter;
import yswl.priv.com.shengqianshopping.fragment.adapter.GridRecyclerFragmentAdapter;
import yswl.priv.com.shengqianshopping.util.AlibcUtil;
import yswl.priv.com.shengqianshopping.util.UrlUtil;
import yswl.priv.com.shengqianshopping.view.FullyGridLayoutManager;

public class HomeFragment2 extends MFragment implements HttpCallback<JSONObject> {
    private static final String FRAGMENT_TAG = "HomeFragment2_ItemFragment";

    private static final int REQUEST_ID_CATEGROY = 100;
    private static final int REQUEST_ID_BANNER = 101;


    private BannerUtil banner;
    List<BannerBean> mImags;
    List<CategoryBean> mCategorys;
    CategoryBean mCategory;
    List<ProductDetail> mProductList;

    @BindView(R.id.scollview)
    ScrollView scollview;
    @BindView(R.id.convenientBanner)
    ConvenientBanner mConvenientBanner;
    @BindView(R.id.home_toolbar)
    LinearLayout mHomeTool;
    @BindView(R.id.tv_catetory_title)
    TextView mCatetoryTitle;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    GridRecyclerFragmentAdapter mAdapter;

    public HomeFragment2() {
    }

    public List<ProductDetail> getProductList() {
        return mProductList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home2, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initBanner();
        requestCategroy();
        requestBanner();

        initGridView();
    }

    private void initGridView() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(getActivity(), 2);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setNestedScrollingEnabled(false);
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new GridRecyclerFragmentAdapter();
        mAdapter.setOnItemClickListener(new GridRecyclerFragmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                ToastUtil.showToast("点击了" + position);
                List<ProductDetail> products = getProductList();
                if (products == null || products.size() == 0) return;
                ProductDetail detail = products.get(position);
                AlibcUtil.openAlibcPage(getActivity(), detail);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

    }

    PopupWindow mPopupWindow;
    RecyclerView mMenuRecyView;

    private void showMenuPopWindow(final View view) {
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(getActivity());
            mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setAnimationStyle(android.R.style.Animation_Translucent);

            View ui = LayoutInflater.from(getContext()).inflate(R.layout.menu_grid_recyclerview, null);
            mMenuRecyView = (RecyclerView) ui.findViewById(R.id.recycler_view);
            ui.findViewById(R.id.close_menu).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closePopWindow();
                }
            });
            GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
            manager.setOrientation(OrientationHelper.VERTICAL);
            mMenuRecyView.setHasFixedSize(true);
            mMenuRecyView.setNestedScrollingEnabled(false);
            mMenuRecyView.setLayoutManager(manager);
            GridRecyclerAdapter adapter = new GridRecyclerAdapter();
            adapter.setCategoryList(mCategorys);
            adapter.setOnItemClickListener(new GridRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    if (mCategorys != null) {
                        mCategory = mCategorys.get(position);
                        updateBottonItem(mCategory);
                        closePopWindow();
                    }
                }
            });
            mMenuRecyView.setAdapter(adapter);
            mPopupWindow.setContentView(ui);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    scollview.setAlpha(1.0f);
                }
            });
            mPopupWindow.setFocusable(true);
        }

        scollview.setAlpha(0.2f);
        if (!mPopupWindow.isShowing())
            mPopupWindow.showAsDropDown(view);
    }

    private void closePopWindow() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            mPopupWindow = null;

        }
    }

    private void requestCategroy() {
        String url = UrlUtil.getUrl(this, R.string.url_category_type_list);
        Map<String, Object> par = new HashMap<>();
        par.put("type", "1");
        HttpClientProxy.getInstance().postAsynSQS(url, REQUEST_ID_CATEGROY, par, this);
    }

    private void requestBanner() {
        String url = UrlUtil.getUrl(this, R.string.url_banner_list);
        HttpClientProxy.getInstance().postAsynSQS(url, REQUEST_ID_BANNER, null, this);
    }

    private void requsetCategoryList(SortEnum sEnum) {
        Map<String, Object> parm = new HashMap<>();
        parm.put("pid", mCategory.pid);
        parm.put("sort", sEnum.getValue());
        String url = UrlUtil.getUrl(this, R.string.url_category_list);
        HttpClientProxy.getInstance().postAsynSQS(url, 200, parm, this);
    }

    void initBanner() {
        if (null == banner) {
            banner = new BannerUtil();
        }
        banner.setConvenientBanner(mConvenientBanner);
        banner.loadPic(mImags);
    }

    @OnClick({R.id.home_toolbar_search, R.id.home_menu, R.id.ll_fkq,
            R.id.ll_tj, R.id.ll_sort, R.id.ll_plan, R.id.tv_hot,
            R.id.tv_new, R.id.tv_price, R.id.tv_sell_count})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_toolbar_search:
                SearchActivity.startActivity(getActivity());
                break;
            case R.id.home_menu:
                if (mCategorys != null)
                    showMenuPopWindow(mHomeTool);
                break;
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
            case R.id.tv_hot:
                requsetCategoryList(SortEnum.HOT);
                break;
            case R.id.tv_new:
                requsetCategoryList(SortEnum.NEW);
                break;
            case R.id.tv_sell_count:
                requsetCategoryList(SortEnum.VOLUME);
                break;
            case R.id.tv_price:
                requsetCategoryList(SortEnum.PRICE);
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

                    //TODO 默认 赋值
                    if (mCategorys != null && mCategorys.size() > 0) {
                        mCategory = mCategorys.get(0);
                        updateBottonItem(mCategory);
                    }
                    break;
                case 200:
                    if (ResultUtil.isCodeOK(result)) {
                        mProductList = ProductDetail.jsonToList(
                                ResultUtil.analysisData(result).optJSONArray(ResultUtil.LIST));
                        mAdapter.setmProductList(mProductList);
                        mAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }

    }

    void updateBottonItem(CategoryBean categoryBean) {
        mCatetoryTitle.setText(categoryBean.title);
        requsetCategoryList(SortEnum.HOT);
    }
//    private void addProductListModule(CategoryBean category) {
//        getChildFragmentManager().beginTransaction().replace(R.id.content, ItemFragment.newInstance(category), FRAGMENT_TAG).commit();
//    }

    @Override
    public void onFail(int requestId, String errorMsg) {
    }


}
