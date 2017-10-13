package yswl.priv.com.shengqianshopping.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import yswl.com.klibrary.base.MFragment;
import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.http.HttpClientProxy;
import yswl.com.klibrary.util.L;
import yswl.com.klibrary.util.ToastUtil;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.bean.ProductDetail;
import yswl.priv.com.shengqianshopping.bean.ResultUtil;
import yswl.priv.com.shengqianshopping.bean.SerializableParamsMap;
import yswl.priv.com.shengqianshopping.fragment.adapter.GridRecyclerFragmentAdapter;
import yswl.priv.com.shengqianshopping.http.SqsHttpClientProxy;
import yswl.priv.com.shengqianshopping.util.AlibcUtil;
import yswl.priv.com.shengqianshopping.util.UrlUtil;
import yswl.priv.com.shengqianshopping.view.DividerGridItemDecoration;

/**
 *
 */
public class GridRecyclerviewFragment extends MFragment implements HttpCallback<JSONObject>, OnRefreshListener, OnLoadMoreListener, GridRecyclerFragmentAdapter.OnItemClickListener {

    private static final String TAG = GridRecyclerviewFragment.class.getSimpleName();
    private static final int REQUEST_ID = 1003;
    private static final int REQUEST_ID_RECOM = 1004;
    private static final String ARG_PARAM1 = "param1";


    private SerializableParamsMap mParam1;//已经封装好的参数
    RecyclerView mRecyclerView;

    GridRecyclerFragmentAdapter mAdapter;
    List<ProductDetail> mProductList = new ArrayList<>();

    private SwipeToLoadLayout swipeToLoadLayout;
    private final int REFRESH = 1;//刷新标志
    private final int LOADMORE = 2;//加载更多
    private int GETDTATYPE = REFRESH;//当前获取数据方式（1刷新，2加载更多）
    private boolean ALLOWLOADMORE = true;//是否允许上拉加载
    private String lastId = "0";

    private boolean asc = false;

    public List<ProductDetail> getmProductList() {
        return mProductList;
    }

    public SerializableParamsMap getmParam1() {
        return mParam1;
    }

    public void setmParam1(SerializableParamsMap mParam1) {
        this.mParam1 = mParam1;
    }


    public GridRecyclerviewFragment() {
    }

    public static GridRecyclerviewFragment newInstance(SerializableParamsMap param1) {
        GridRecyclerviewFragment fragment = new GridRecyclerviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (SerializableParamsMap) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grid_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        swipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        //设置监听
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.swipe_target);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        manager.setOrientation(OrientationHelper.VERTICAL);
//        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity(), 10, R.color.white));
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new GridRecyclerFragmentAdapter();
        mAdapter.setOnItemClickListener(new GridRecyclerFragmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                List<ProductDetail> products = getmProductList();
                if (products == null || products.size() == 0) return;
                ProductDetail detail = products.get(position);
                AlibcUtil.openBrower(detail, getActivity());
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        requestData();
    }


    /**
     * 1. pid | 不可 | 选品库ID |
     * 1. lastId | 可 | | 默认0 | 由于volume有0值，销量默认－1
     * 1. count | 可 | | 默认20
     * 1. sort | 可 | 排序字段 | popularity/人气-默认 , volume/售量 ， new/最新 , price/价格
     * 1. sortBy | 可 | 排序方式 | 正序:asc 倒序:desc-默认
     */
    public void requestData() {

        if (mParam1 != null) {
            Map<String, Object> parm = mParam1.map;
            if (GETDTATYPE == REFRESH) {
                if ("volume".equals(parm.get("sort"))) {
                    lastId = "-1";
                } else {
                    lastId = "0";
                }
            }
            parm.put("lastId", lastId);
            parm.put("count", "20");
            if (asc) {
                parm.put("sortBy", "asc");
            } else {
                parm.put("sortBy", "desc");
            }
            String url = UrlUtil.getUrl(this, R.string.url_category_list);
            SqsHttpClientProxy.postAsynSQS(url, REQUEST_ID, parm, this);
        }

    }


    @Override
    public void onSucceed(int requestId, JSONObject result) {
        if (GETDTATYPE == REFRESH) {
            swipeToLoadLayout.setRefreshing(false);
            mProductList.clear();
        } else {
            swipeToLoadLayout.setLoadingMore(false);
        }
        L.e(TAG, "onSucceed result :" + result);
        if (ResultUtil.isCodeOK(result)) {
            lastId = ResultUtil.analysisData(result).optString("lastId");
            List<ProductDetail> tempList = ProductDetail.jsonToList(
                    ResultUtil.analysisData(result).optJSONArray(ResultUtil.LIST));
            if (tempList != null && tempList.size() > 0) {
                for (int i = 0; i < tempList.size(); i++) {
                    mProductList.add(tempList.get(i));
                }
                mAdapter.setmProductList(mProductList);
                mAdapter.notifyDataSetChanged();
            } else {
                ALLOWLOADMORE = false;
            }
            GETDTATYPE = REFRESH;
        }

    }

    @Override
    public void onFail(int requestId, String errorMsg) {
        if (GETDTATYPE == REFRESH) {
            swipeToLoadLayout.setRefreshing(false);
        } else {
            swipeToLoadLayout.setLoadingMore(false);
        }
        GETDTATYPE = REFRESH;
    }

    @Override
    public void onLoadMore() {
        //加载
        GETDTATYPE = LOADMORE;
        if (ALLOWLOADMORE) {
            requestData();
        } else {
            swipeToLoadLayout.setLoadingMore(false);
        }
    }

    @Override
    public void onRefresh() {
        //刷新
        GETDTATYPE = REFRESH;
        ALLOWLOADMORE = true;
        requestData();
    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int position) {


    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }
}
