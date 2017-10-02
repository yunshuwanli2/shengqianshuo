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
import yswl.priv.com.shengqianshopping.banner.SortEnum;
import yswl.priv.com.shengqianshopping.bean.CategoryBean;
import yswl.priv.com.shengqianshopping.bean.ProductDetail;
import yswl.priv.com.shengqianshopping.bean.ResultUtil;
import yswl.priv.com.shengqianshopping.bean.SerializableMap;
import yswl.priv.com.shengqianshopping.fragment.adapter.DividerItemDecoration;
import yswl.priv.com.shengqianshopping.fragment.adapter.GridRecyclerFragmentAdapter;
import yswl.priv.com.shengqianshopping.util.UrlUtil;

/**
 *
 */
public class GridRecyclerviewFragment extends MFragment implements HttpCallback<JSONObject> {
    RecyclerView mRecyclerView;
    GridRecyclerFragmentAdapter mAdapter;


    private static final int REQUEST_ID = 1003;

    private static final String ARG_PARAM1 = "param1";

    public SerializableMap getmParam1() {
        return mParam1;
    }

    public void setmParam1(SerializableMap mParam1) {
        this.mParam1 = mParam1;
    }

    private SerializableMap mParam1;//已经封装好的参数


    public GridRecyclerviewFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment BlankFragment.
     */
    public static GridRecyclerviewFragment newInstance2(SerializableMap param1) {
        GridRecyclerviewFragment fragment = new GridRecyclerviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }
    public static GridRecyclerviewFragment newInstance(SerializableMap param1) {
        GridRecyclerviewFragment fragment = new GridRecyclerviewFragment();
        fragment.setmParam1(param1);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (SerializableMap) getArguments().getSerializable(ARG_PARAM1);
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
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new GridRecyclerFragmentAdapter();

        mRecyclerView.setAdapter(mAdapter);
        requestPinkageData();
    }

    /**
     * 1. pid | 不可 | 选品库ID |
     * 1. lastId | 可 | | 默认0 | 由于volume有0值，销量默认－1
     * 1. count | 可 | | 默认20
     * 1. sort | 可 | 排序字段 | popularity/人气-默认 , volume/售量 ， new/最新 , price/价格
     * 1. sortBy | 可 | 排序方式 | 正序:asc 倒序:desc-默认
     */
    public void requestPinkageData() {
        if (mParam1 == null) return;
        Map<String,Object> parm = mParam1.map;
        String url = UrlUtil.getUrl(this, R.string.url_category_list);
        HttpClientProxy.getInstance().postAsyn(url, REQUEST_ID, parm, this);
    }


    private static final String TAG = GridRecyclerviewFragment.class.getSimpleName();
    List<ProductDetail> mProductList;
    @Override
    public void onSucceed(int requestId, JSONObject result) {
        L.e(TAG, "onSucceed result :" + result);
        if (ResultUtil.isCodeOK(result)) {
            mProductList = ProductDetail.jsonToList(
                    ResultUtil.analysisData(result).optJSONArray(ResultUtil.LIST) );
            mAdapter.setmProductList(mProductList);
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onFail(int requestId, String errorMsg) {

    }
}
