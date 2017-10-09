package yswl.priv.com.shengqianshopping.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yswl.com.klibrary.base.MFragment;
import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.http.HttpClientProxy;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.bean.BalanceDetailItemBean;
import yswl.priv.com.shengqianshopping.bean.ResultUtil;
import yswl.priv.com.shengqianshopping.fragment.adapter.MyItemRecyclerViewAdapter;
import yswl.priv.com.shengqianshopping.manager.UserManager;
import yswl.priv.com.shengqianshopping.util.UrlUtil;

/**
 * 收入明细
 */
public class BalanceDetailItemFragment extends MFragment implements HttpCallback<JSONObject>, OnRefreshListener, OnLoadMoreListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private SwipeToLoadLayout swipeToLoadLayout;
    private final int REFRESH = 1;//刷新标志
    private final int LOADMORE = 2;//加载更多
    private int GETDTATYPE = REFRESH;//当前获取数据方式（1刷新，2加载更多）
    private String lastId = "0";
    private boolean ALLOWLOADMORE = true;//是否允许上拉加载

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BalanceDetailItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static BalanceDetailItemFragment newInstance(int columnCount) {
        BalanceDetailItemFragment fragment = new BalanceDetailItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    MyItemRecyclerViewAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        //获取控件
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.swipe_target);
        swipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        //设置监听
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);

        // Set the adapter
//        if (view instanceof RecyclerView) {
        Context context = view.getContext();
//           = (RecyclerView) view;
//            if (mColumnCount <= 1) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
//                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//            }
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new MyItemRecyclerViewAdapter();
        recyclerView.setAdapter(mAdapter);
//        }
        requestData();
        return view;
    }

    private void requestData() {
        if (GETDTATYPE == REFRESH) {
            lastId = "0";
        }
        String url = UrlUtil.getUrl(getActivity(), R.string.url_balance_detail);
        Map<String, Object> map = new HashMap<>();
        map.put("uid", UserManager.getUserInfo(getContext()).getUid());
        map.put("lastId", lastId);
        map.put("count", "20");
        HttpClientProxy.getInstance().postAsynSQS(url, 100, map, this);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    List<BalanceDetailItemBean> balanceDetailItemBeans = new ArrayList<>();

    @Override
    public void onSucceed(int requestId, JSONObject result) {
        if (GETDTATYPE == REFRESH) {
            swipeToLoadLayout.setRefreshing(false);
            balanceDetailItemBeans.clear();
        } else {
            swipeToLoadLayout.setLoadingMore(false);
        }
        if (ResultUtil.isCodeOK(result)) {
            JSONArray jsonArr = ResultUtil.analysisData(result).optJSONArray(ResultUtil.LIST);
            lastId = ResultUtil.analysisData(result).optString("lastId");
            List<BalanceDetailItemBean> tempList = BalanceDetailItemBean.jsonToList(jsonArr);
            if (tempList != null && tempList.size() > 0) {
                for (int i = 0; i < tempList.size(); i++) {
                    balanceDetailItemBeans.add(tempList.get(i));
                }
                mAdapter.setmValues(balanceDetailItemBeans);
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

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(BalanceDetailItemBean item);
    }
}
