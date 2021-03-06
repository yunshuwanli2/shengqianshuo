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
import yswl.priv.com.shengqianshopping.fragment.adapter.AdvanceGridRecyclerFragmentAdapter;
import yswl.priv.com.shengqianshopping.fragment.adapter.GridRecyclerFragmentAdapter;
import yswl.priv.com.shengqianshopping.http.SqsHttpClientProxy;
import yswl.priv.com.shengqianshopping.util.AlibcUtil;
import yswl.priv.com.shengqianshopping.util.UrlUtil;
import yswl.priv.com.shengqianshopping.view.TopButton;

public class AdvanceGridRecyclerFragment extends MFragment implements HttpCallback<JSONObject>, OnRefreshListener, OnLoadMoreListener {


    private static final int REQUEST_ID = 1003;

    private final int REFRESH = 1;//刷新标志
    private final int LOADMORE = 2;//加载更多
    private int GETDTATYPE = REFRESH;//当前获取数据方式（1刷新，2加载更多）
    private String pageNo = "1";
    private boolean ALLOWLOADMORE = true;//是否允许上拉加载

    static TimeBean time = TimeBean.getTomorrow();
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    @BindView(R.id.swipe_target)
    RecyclerView mRecyclerView;
    @BindView(R.id.top_button)
    TopButton topButton;
    AdvanceGridRecyclerFragmentAdapter mAdapter;

    public AdvanceGridRecyclerFragment() {
    }

    public static AdvanceGridRecyclerFragment newInstance() {
        AdvanceGridRecyclerFragment fragment = new AdvanceGridRecyclerFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
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

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new AdvanceGridRecyclerFragmentAdapter();
        mRecyclerView.setAdapter(mAdapter);
        topButton.ToTop(mRecyclerView);
        mAdapter.setOnItemClickListener(new GridRecyclerFragmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                if (mProductList != null && mProductList.size() > 0) {
                    AlibcUtil.openAlibcPage(getActivity(), mProductList.get(0));
                }
            }
        });
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        requestData();
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
        String url = UrlUtil.getUrl(this, R.string.url_crazy_buy_list);
        Map<String, Object> map = new HashMap<>();
        map.put("pageNo", pageNo);
        map.put("pageSize", 20);
        map.put("startTime", time.startTime);
        map.put("endTime", time.endTime);
        SqsHttpClientProxy.postAsynSQS(url, REQUEST_ID, map, this);
    }

    private static final String TAG = AdvanceGridRecyclerFragment.class.getSimpleName();
    List<CrazyProductDetail> mProductList = new ArrayList<>();

    @Override
    public void onSucceed(int requestId, JSONObject result) {
        L.e(TAG, "onSucceed result :" + result);
        if (GETDTATYPE == REFRESH) {
            swipeToLoadLayout.setRefreshing(false);
            mProductList.clear();
        } else {
            swipeToLoadLayout.setLoadingMore(false);
        }
        if (ResultUtil.isCodeOK(result)) {
            List<CrazyProductDetail> tempList = CrazyProductDetail.jsonToList(
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
