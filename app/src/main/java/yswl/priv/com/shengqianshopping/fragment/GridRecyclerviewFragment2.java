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
import java.util.HashMap;
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
import yswl.priv.com.shengqianshopping.fragment.adapter.GridRecyclerFragmentAdapter2;
import yswl.priv.com.shengqianshopping.util.AlibcUtil;
import yswl.priv.com.shengqianshopping.util.UrlUtil;
import yswl.priv.com.shengqianshopping.view.DividerGridItemDecoration;

/**
 * TOP100
 */
public class GridRecyclerviewFragment2 extends MFragment implements HttpCallback<JSONObject>, OnRefreshListener, OnLoadMoreListener {
    RecyclerView mRecyclerView;
    GridRecyclerFragmentAdapter2 mAdapter;


    private static final int REQUEST_ID = 1003;

    private static final String ARG_PARAM1 = "param1";

    private SwipeToLoadLayout swipeToLoadLayout;
    private final int REFRESH = 1;//刷新标志
    private final int LOADMORE = 2;//加载更多
    private int GETDTATYPE = REFRESH;//当前获取数据方式（1刷新，2加载更多）
    private boolean ALLOWLOADMORE = true;//是否允许上拉加载
    private String lastId = "0";


    public List<ProductDetail> getmProductList() {
        return mProductList;
    }

    public SerializableParamsMap getmParam1() {
        return mParam1;
    }

    public void setmParam1(SerializableParamsMap mParam1) {
        this.mParam1 = mParam1;
    }

    private SerializableParamsMap mParam1;//已经封装好的参数


    public GridRecyclerviewFragment2() {
    }


    public static GridRecyclerviewFragment2 newInstance() {
        GridRecyclerviewFragment2 fragment = new GridRecyclerviewFragment2();
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
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.swipe_target);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity(), 10, R.color.white));
        mAdapter = new GridRecyclerFragmentAdapter2();
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


    public void requestData() {
        if (GETDTATYPE == REFRESH) {
            lastId = "0";
        }
        Map<String, Object> map = new HashMap<>();
        map.put("lastId", lastId);
        map.put("count", "20");
        String url = UrlUtil.getUrl(this, R.string.url_top_list);
        HttpClientProxy.getInstance().postAsynSQS(url, REQUEST_ID, map, this);
    }


    private static final String TAG = GridRecyclerviewFragment2.class.getSimpleName();
    List<ProductDetail> mProductList = new ArrayList<>();

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
}
