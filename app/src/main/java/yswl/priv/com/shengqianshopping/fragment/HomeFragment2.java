package yswl.priv.com.shengqianshopping.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.umeng.socialize.UMShareAPI;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yswl.com.klibrary.base.MFragment;
import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.activity.AdvanceActivity;
import yswl.priv.com.shengqianshopping.activity.CrazyBuyActivity;
import yswl.priv.com.shengqianshopping.activity.RecommendActivity;
import yswl.priv.com.shengqianshopping.activity.SearchActivity;
import yswl.priv.com.shengqianshopping.activity.Top100Activity;
import yswl.priv.com.shengqianshopping.banner.BannerUtil;
import yswl.priv.com.shengqianshopping.bean.BannerBean;
import yswl.priv.com.shengqianshopping.bean.CategoryBean;
import yswl.priv.com.shengqianshopping.bean.ProductDetail;
import yswl.priv.com.shengqianshopping.bean.ResultUtil;
import yswl.priv.com.shengqianshopping.bean.SortEnum;
import yswl.priv.com.shengqianshopping.fragment.adapter.GridRecyclerAdapter;
import yswl.priv.com.shengqianshopping.fragment.adapter.GridRecyclerFragmentAdapter;
import yswl.priv.com.shengqianshopping.fragment.adapter.HomeFragmentAdapter;
import yswl.priv.com.shengqianshopping.http.SqsHttpClientProxy;
import yswl.priv.com.shengqianshopping.util.AlibcUtil;
import yswl.priv.com.shengqianshopping.util.UrlUtil;
import yswl.priv.com.shengqianshopping.view.DividerGridItemDecoration;
import yswl.priv.com.shengqianshopping.view.SelectionSortView;

public class HomeFragment2 extends MFragment implements HttpCallback<JSONObject>, OnRefreshListener, OnLoadMoreListener, View.OnClickListener {
    private static final String FRAGMENT_TAG = "HomeFragment2";

    private static final int REQUEST_ID_CATEGROY = 100;
    private static final int REQUEST_ID_BANNER = 101;
    private static final int REQUEST_ID_PRUDECT_LIST = 200;


    private BannerUtil banner;
    List<BannerBean> mImags;
    List<CategoryBean> mCategorys;
    CategoryBean mCategory;
    List<ProductDetail> mProductList = new ArrayList<>();
    //    @BindView(R.id.swipe_target)
//    NestedScrollView scollview;
//    @BindView(R.id.convenientBanner)
    ConvenientBanner mConvenientBanner;
    //    @BindView(R.id.home_toolbar)
    LinearLayout mHomeTool;
    //    @BindView(R.id.tv_catetory_title)
    TextView mCatetoryTitle;
    //    @BindView(R.id.swipe_target)
    RecyclerView mRecyclerView;
    //    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    //    @BindView(R.id.sort_hot)
    SelectionSortView sortHot;
    //    @BindView(R.id.sort_new)
    SelectionSortView sortNew;
    //    @BindView(R.id.sort_sell_count)
    SelectionSortView sortSellCount;
    //    @BindView(R.id.sort_price)
    SelectionSortView sortPrice;

    RelativeLayout home_toolbar_search;
    ImageView home_menu;
    LinearLayout ll_fkq;
    LinearLayout ll_tj;
    LinearLayout ll_sort;
    LinearLayout ll_plan;

    private static final int REFRESH = 1;//刷新标志
    private static final int LOADMORE = 2;//加载更多
    private int GETDTATYPE = REFRESH;//当前获取数据方式（1刷新，2加载更多）
    private boolean ALLOWLOADMORE = true;//是否允许上拉加载

    HomeFragmentAdapter mAdapter;
    private int currentPosition = 0;

    private int hotPageNo = 1;
    private int newPageNo = 1;
    private int volumePageNo = 1;
    private int pricePageNo = 1;

    private ImageView lastImg;


    public HomeFragment2() {
    }

    public List<ProductDetail> getProductList() {
        return mProductList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home2, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.swipe_target);
        swipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        home_toolbar_search = (RelativeLayout) view.findViewById(R.id.home_toolbar_search);
        home_menu = (ImageView) view.findViewById(R.id.home_menu);
        mHomeTool = (LinearLayout) view.findViewById(R.id.home_toolbar);
//        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initGridView();
        initBanner();
        requestCategroy();
        requestBanner();

        //设置监听
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        sortHot.setContent("人气");
        sortHot.setPosition(0);
        sortNew.setContent("最新");
        sortNew.setPosition(1);
        sortSellCount.setContent("销量");
        sortSellCount.setPosition(2);
        sortPrice.setContent("价格");
        sortPrice.setPosition(3);
        home_toolbar_search.setOnClickListener(this);
        home_menu.setOnClickListener(this);
        ll_fkq.setOnClickListener(this);
        ll_tj.setOnClickListener(this);
        ll_sort.setOnClickListener(this);
        ll_plan.setOnClickListener(this);
        sortHot.setOnClickListener(this);
        sortNew.setOnClickListener(this);
        sortSellCount.setOnClickListener(this);
        sortPrice.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (banner != null)
            banner.startTurning();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (banner != null)
            banner.stopTurning();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        banner = null;
        mPopupWindow = null;
    }

    private void initGridView() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        manager.setOrientation(OrientationHelper.VERTICAL);
        manager.setSmoothScrollbarEnabled(true);
        manager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(manager);
//        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(getAdapter());
        setHeader(mRecyclerView);
    }

    HomeFragmentAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new HomeFragmentAdapter();
            mAdapter.setOnItemClickListener(new GridRecyclerFragmentAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    List<ProductDetail> products = mAdapter.getmProductList();
                    if (products == null || products.size() == 0) return;
                    ProductDetail detail = products.get(position);
                    AlibcUtil.openBrower3(detail, getActivity());
                }
            });
        }
        return mAdapter;
    }

    private void setHeader(RecyclerView view) {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.home_top_layout, view, false);
        mConvenientBanner = (ConvenientBanner) header.findViewById(R.id.convenientBanner);
        mCatetoryTitle = (TextView) header.findViewById(R.id.tv_catetory_title);
        sortHot = (SelectionSortView) header.findViewById(R.id.sort_hot);
        sortNew = (SelectionSortView) header.findViewById(R.id.sort_new);
        sortSellCount = (SelectionSortView) header.findViewById(R.id.sort_sell_count);
        sortPrice = (SelectionSortView) header.findViewById(R.id.sort_price);
        ll_fkq = (LinearLayout) header.findViewById(R.id.ll_fkq);
        ll_tj = (LinearLayout) header.findViewById(R.id.ll_tj);
        ll_sort = (LinearLayout) header.findViewById(R.id.ll_sort);
        ll_plan = (LinearLayout) header.findViewById(R.id.ll_plan);
        mAdapter.setHeaderView(header);

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
            mPopupWindow.setFocusable(true);
//            mPopupWindow.setAnimationStyle(R.style.home_popwindow_anim_style);
        }


        if (!mPopupWindow.isShowing()) {
            mPopupWindow.showAsDropDown(view);
        }
    }

    private void closePopWindow() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    private void requestCategroy() {
        String url = UrlUtil.getUrl(this, R.string.url_category_type_list);
        Map<String, Object> par = new HashMap<>();
        par.put("type", "1");
        SqsHttpClientProxy.postAsynSQS(url, REQUEST_ID_CATEGROY, par, this);
    }

    private void requestBanner() {
        String url = UrlUtil.getUrl(this, R.string.url_banner_list);
        SqsHttpClientProxy.postAsynSQS(url, REQUEST_ID_BANNER, null, this);
    }

    private void requsetCategoryList(SortEnum sEnum, boolean asc, int pageNo) {
        Map<String, Object> parm = new HashMap<>();
        parm.put("pid", mCategory.pid);
        parm.put("sort", sEnum.getValue());
        parm.put("pageNo", pageNo);
        Log.i("znh", asc + "****pageNo" + pageNo);
        parm.put("pageSize", "20");
        if (asc) {
            parm.put("sortBy", "asc");
        } else {
            parm.put("sortBy", "desc");
        }
        String url = UrlUtil.getUrl(this, R.string.url_category_list);
        SqsHttpClientProxy.postAsynSQS(url, REQUEST_ID_PRUDECT_LIST, parm, this);
    }

    void initBanner() {
        if (null == banner) {
            banner = new BannerUtil(getActivity());
        }
        banner.setConvenientBanner(mConvenientBanner);
        banner.loadPic(mImags);
    }


    @Override
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
            case R.id.sort_hot:
                if (lastImg != null) {
                    lastImg.setVisibility(View.INVISIBLE);
                }
                lastImg = sortHot.getImgStatus();
                sortHot.onClick(currentPosition);
                currentPosition = 0;
                hotPageNo = 1;
                requsetCategoryList(SortEnum.HOT, sortHot.isSortAsc(), hotPageNo);
                break;
            case R.id.sort_new:
                if (lastImg != null) {
                    lastImg.setVisibility(View.INVISIBLE);
                }
                lastImg = sortNew.getImgStatus();
                sortNew.onClick(currentPosition);
                currentPosition = 1;
                newPageNo = 1;
                requsetCategoryList(SortEnum.NEW, sortNew.isSortAsc(), newPageNo);
                break;
            case R.id.sort_sell_count:
                if (lastImg != null) {
                    lastImg.setVisibility(View.INVISIBLE);
                }
                lastImg = sortSellCount.getImgStatus();
                sortSellCount.onClick(currentPosition);
                currentPosition = 2;
                volumePageNo = 1;
                requsetCategoryList(SortEnum.VOLUME, sortSellCount.isSortAsc(), volumePageNo);
                break;
            case R.id.sort_price:
                if (lastImg != null) {
                    lastImg.setVisibility(View.INVISIBLE);
                }
                lastImg = sortPrice.getImgStatus();
                sortPrice.onClick(currentPosition);
                currentPosition = 3;
                pricePageNo = 1;
                requsetCategoryList(SortEnum.PRICE, sortPrice.isSortAsc(), pricePageNo);
                break;
        }
    }


    @Override
    public void onSucceed(int requestId, final JSONObject result) {
        if (requestId == REQUEST_ID_PRUDECT_LIST) {
            if (GETDTATYPE == REFRESH) {
                swipeToLoadLayout.setRefreshing(false);
                mProductList.clear();
            } else {
                swipeToLoadLayout.setLoadingMore(false);
            }
        }
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

                    if (mCategorys != null && mCategorys.size() > 0) {
                        mCategory = mCategorys.get(0);
                        updateBottonItem(mCategory);
                    }
                    break;
                case REQUEST_ID_PRUDECT_LIST:
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            List<ProductDetail> tempList = ProductDetail.jsonToList(
                                    ResultUtil.analysisData(result).optJSONArray(ResultUtil.LIST));
                            if (tempList != null && tempList.size() > 0) {
                                mProductList.addAll(tempList);
                            } else {
                                ALLOWLOADMORE = false;
                            }
                            handle.sendEmptyMessage(0);
                        }
                    });
                    GETDTATYPE = REFRESH;
                    switch (currentPosition) {
                        case 0:
                            hotPageNo++;
                            break;
                        case 1:
                            newPageNo++;
                            break;
                        case 2:
                            volumePageNo++;
                            break;
                        case 3:
                            pricePageNo++;
                            break;
                    }
                default:
                    break;
            }

        }
    }

    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                getAdapter().setmProductList(mProductList);
                getAdapter().notifyDataSetChanged();
//                getAdapter().notifyItemRangeInserted(mProductList.size(),0);
            }
        }
    };

    void updateBottonItem(CategoryBean categoryBean) {
        mCatetoryTitle.setText(categoryBean.title);
        requsetCategoryList(SortEnum.HOT, sortHot.isSortAsc(), hotPageNo);
        //设置默认--人气
        if (lastImg != null) {
            lastImg.setVisibility(View.INVISIBLE);
        }
        sortHot.setDefault();
        sortNew.hideImg();
        sortSellCount.hideImg();
        sortPrice.hideImg();
        lastImg = sortHot.getImgStatus();
        currentPosition = 0;
    }

    @Override
    public void onFail(int requestId, String errorMsg) {
        if (requestId == REQUEST_ID_PRUDECT_LIST) {
            if (GETDTATYPE == REFRESH) {
                swipeToLoadLayout.setRefreshing(false);
            } else {
                swipeToLoadLayout.setLoadingMore(false);
            }
            GETDTATYPE = REFRESH;
        }

    }


    @Override
    public void onLoadMore() {
        //加载
        GETDTATYPE = LOADMORE;
        if (ALLOWLOADMORE) {
            switch (currentPosition) {
                case 0:
                    requsetCategoryList(SortEnum.HOT, sortHot.isSortAsc(), hotPageNo);
                    break;
                case 1:
                    requsetCategoryList(SortEnum.NEW, sortNew.isSortAsc(), newPageNo);
                    break;
                case 2:
                    requsetCategoryList(SortEnum.VOLUME, sortSellCount.isSortAsc(), volumePageNo);
                    break;
                case 3:
                    requsetCategoryList(SortEnum.PRICE, sortPrice.isSortAsc(), pricePageNo);
                    break;
            }
        } else {
            swipeToLoadLayout.setLoadingMore(false);
        }
    }

    @Override
    public void onRefresh() {
        //刷新
        GETDTATYPE = REFRESH;
        ALLOWLOADMORE = true;
        switch (currentPosition) {
            case 0:
                hotPageNo = 1;
                requsetCategoryList(SortEnum.HOT, sortHot.isSortAsc(), hotPageNo);
                break;
            case 1:
                newPageNo = 1;
                requsetCategoryList(SortEnum.NEW, sortNew.isSortAsc(), newPageNo);
                break;
            case 2:
                volumePageNo = 1;
                requsetCategoryList(SortEnum.VOLUME, sortSellCount.isSortAsc(), volumePageNo);
                break;
            case 3:
                pricePageNo = 1;
                requsetCategoryList(SortEnum.PRICE, sortPrice.isSortAsc(), pricePageNo);
                break;
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}
