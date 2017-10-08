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

import java.util.List;

import yswl.com.klibrary.base.MFragment;
import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.http.HttpClientProxy;
import yswl.com.klibrary.util.L;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.bean.ProductDetail;
import yswl.priv.com.shengqianshopping.bean.ResultUtil;
import yswl.priv.com.shengqianshopping.bean.SerializableParamsMap;
import yswl.priv.com.shengqianshopping.fragment.adapter.GridRecyclerFragmentAdapter2;
import yswl.priv.com.shengqianshopping.util.UrlUtil;

/**
 * TOP100
 */
public class GridRecyclerviewFragment2 extends MFragment implements HttpCallback<JSONObject> {
    RecyclerView mRecyclerView;
    GridRecyclerFragmentAdapter2 mAdapter;


    private static final int REQUEST_ID = 1003;

    private static final String ARG_PARAM1 = "param1";

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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);

        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new GridRecyclerFragmentAdapter2();

        mRecyclerView.setAdapter(mAdapter);
        requestData();
    }


    public void requestData() {
        String url = UrlUtil.getUrl(this, R.string.url_top_list);
        HttpClientProxy.getInstance().postAsynSQS(url, REQUEST_ID, null, this);
    }


    private static final String TAG = GridRecyclerviewFragment2.class.getSimpleName();
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
