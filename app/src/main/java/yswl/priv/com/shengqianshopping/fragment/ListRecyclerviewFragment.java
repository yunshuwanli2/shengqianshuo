package yswl.priv.com.shengqianshopping.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import yswl.com.klibrary.base.MFragment;
import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.http.HttpClientProxy;
import yswl.com.klibrary.util.L;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.bean.CrazyProductDetail;
import yswl.priv.com.shengqianshopping.bean.ResultUtil;
import yswl.priv.com.shengqianshopping.bean.TimeBean;
import yswl.priv.com.shengqianshopping.http.SqsHttpClientProxy;
import yswl.priv.com.shengqianshopping.util.DateUtil;
import yswl.priv.com.shengqianshopping.view.DividerItemDecoration;
import yswl.priv.com.shengqianshopping.fragment.adapter.GridRecyclerFragmentAdapter;
import yswl.priv.com.shengqianshopping.fragment.adapter.ListRecyclerFragmentAdapter;
import yswl.priv.com.shengqianshopping.util.AlibcUtil;
import yswl.priv.com.shengqianshopping.util.UrlUtil;

/**
 * TOP100
 */
public class ListRecyclerviewFragment extends MFragment implements HttpCallback<JSONObject>, OnRefreshListener, OnLoadMoreListener {


    private static final int REQUEST_ID = 1003;
    private static final String ARG_PARAM1 = "param1";

    private final int REFRESH = 1;//刷新标志
    private final int LOADMORE = 2;//加载更多
    private int GETDTATYPE = REFRESH;//当前获取数据方式（1刷新，2加载更多）
    private String pageNo = "1";
    private boolean ALLOWLOADMORE = true;//是否允许上拉加载

    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    @BindView(R.id.swipe_target)
    RecyclerView mRecyclerView;
    ListRecyclerFragmentAdapter mAdapter;

    public TimeBean getmParam1() {
        return mParam1;
    }

    public void setmParam1(TimeBean mParam1) {
        this.mParam1 = mParam1;
    }

    private TimeBean mParam1;//已经封装好的参数


    public ListRecyclerviewFragment() {
    }


    public static ListRecyclerviewFragment newInstance(TimeBean param1) {
        ListRecyclerviewFragment fragment = new ListRecyclerviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (TimeBean) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grid_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        //设置监听
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ListRecyclerFragmentAdapter();
        boolean showShade = isShowShade(mParam1.startTime);
        mAdapter.setmShowShade(showShade);
        mAdapter.setOnItemClickListener(new GridRecyclerFragmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                List<CrazyProductDetail> products = getmProductList();
                if (products == null || products.size() == 0) return;
                CrazyProductDetail detail = products.get(position);
                AlibcUtil.openAlibcPage(getActivity(), detail);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        requestData();
    }

    public List<CrazyProductDetail> getmProductList() {
        return mProductList;
    }

    /**
     * 1. pageNo | 可 | 页码 | 默认为1
     * 1. pageSize | 可 | 每页数量 | 默认为20
     * 1. startTime | 不可 | 查询开始时间 | 2017-09-24 15:00:00
     * 1. endTime | 不可 | 查询结束时间 | 2017-09-24 18:00:00
     */
    public void requestData() {
        if (GETDTATYPE == REFRESH) {
            pageNo = "1";
        } else {
            int temp = Integer.parseInt(pageNo);
            pageNo = ++temp + "";
        }
        if (mParam1 == null) return;
        String url = UrlUtil.getUrl(this, R.string.url_crazy_buy_list);
        Map<String, Object> map = new HashMap<>();
        map.put("pageNo", pageNo);
        map.put("pageSize", 20);
        map.put("startTime", mParam1.startTime);
        map.put("endTime", mParam1.endTime);
        SqsHttpClientProxy.postAsynSQS(url, REQUEST_ID, map, this);
    }


    private static final String TAG = ListRecyclerviewFragment.class.getSimpleName();
    List<CrazyProductDetail> mProductList = new ArrayList<>();

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
            List<CrazyProductDetail> tempList = CrazyProductDetail.jsonToList(
                    ResultUtil.analysisData(result).optJSONArray(ResultUtil.LIST));
            if (tempList != null && tempList.size() > 0) {
                mProductList.addAll(tempList);
                mAdapter.setmProductList(mProductList);
                mAdapter.notifyDataSetChanged();

            } else {
                ALLOWLOADMORE = false;
            }
            GETDTATYPE = REFRESH;
        }
    }

    boolean isShowShade(String timeStr) {
        if (TextUtils.isEmpty(timeStr)) return false;
        long compareTime = DateUtil.stringToTimeStamp(timeStr);
        long currentTime = System.currentTimeMillis();
        if (currentTime < compareTime)
            return true;
        return false;

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
