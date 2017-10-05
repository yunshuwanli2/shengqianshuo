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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yswl.com.klibrary.base.MFragment;
import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.http.HttpClientProxy;
import yswl.com.klibrary.util.L;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.bean.ProductDetail;
import yswl.priv.com.shengqianshopping.bean.ResultUtil;
import yswl.priv.com.shengqianshopping.bean.SerializableMap;
import yswl.priv.com.shengqianshopping.fragment.adapter.AdvanceGridRecyclerFragmentAdapter;
import yswl.priv.com.shengqianshopping.fragment.adapter.GridRecyclerFragmentAdapter2;
import yswl.priv.com.shengqianshopping.util.UrlUtil;

/**
 * TOP100
 */
public class AdvanceGridRecyclerFragment extends MFragment implements HttpCallback<JSONObject> {
    RecyclerView mRecyclerView;
    AdvanceGridRecyclerFragmentAdapter mAdapter;


    private static final int REQUEST_ID = 1003;


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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);

        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new AdvanceGridRecyclerFragmentAdapter();

        mRecyclerView.setAdapter(mAdapter);
        requestData();
    }


    /**
     * 1. pageNo | 可 | 页码 | 默认为1
     * 1. pageSize | 可 | 每页数量 | 默认为20
     * 1. startTime | 不可 | 查询开始时间 | 2017-09-24 15:00:00
     * 1. endTime | 不可 | 查询结束时间 | 2017-09-24 18:00:00
     */
    public void requestData() {
        String url = UrlUtil.getUrl(this, R.string.url_crazy_buy_list);
        Map<String, Object> map = new HashMap<>();
        map.put("pageNo", 1);
        map.put("pageSize", 20);
//        map.put("startTime", "2017-10-05 00:00:00");
//        map.put("endTime", "2017-10-05 09:00:00");
        HttpClientProxy.getInstance().postAsyn(url, REQUEST_ID, map, this);
    }


    private static final String TAG = AdvanceGridRecyclerFragment.class.getSimpleName();
    List<ProductDetail> mProductList;

    @Override
    public void onSucceed(int requestId, JSONObject result) {
        L.e(TAG, "onSucceed result :" + result);
        if (ResultUtil.isCodeOK(result)) {
            mProductList = ProductDetail.jsonToList(
                    ResultUtil.analysisData(result).optJSONArray(ResultUtil.LIST));
            mAdapter.setmProductList(mProductList);
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onFail(int requestId, String errorMsg) {

    }
}
