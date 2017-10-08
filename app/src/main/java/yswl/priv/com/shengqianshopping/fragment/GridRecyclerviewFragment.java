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

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;

import org.json.JSONObject;

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
import yswl.priv.com.shengqianshopping.util.UrlUtil;

/**
 *
 */
public class GridRecyclerviewFragment extends MFragment implements HttpCallback<JSONObject> {

    private static final String TAG = GridRecyclerviewFragment.class.getSimpleName();
    private static final int REQUEST_ID = 1003;
    private static final int REQUEST_ID_RECOM = 1004;
    private static final String ARG_PARAM1 = "param1";


    private SerializableParamsMap mParam1;//已经封装好的参数
    RecyclerView mRecyclerView;

    GridRecyclerFragmentAdapter mAdapter;
    List<ProductDetail> mProductList;

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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);

        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new GridRecyclerFragmentAdapter();
        mAdapter.setOnItemClickListener(new GridRecyclerFragmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                ToastUtil.showToast("点击了" + position);
                List<ProductDetail> products = getmProductList();
                if (products == null || products.size() == 0) return;
                ProductDetail detail = products.get(position);
                openAlibcPage(detail);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        requestData();
    }


    void openAlibcPage(ProductDetail detail) {
        Map<String, String> exParams = new HashMap<>();
        exParams.put(AlibcConstants.ISV_CODE, "saveduoduo");

        //商品详情page
        AlibcBasePage detailPage = new AlibcDetailPage(detail.iid);
        //设置页面打开方式
        AlibcShowParams showParams = new AlibcShowParams(OpenType.H5, true);
        AlibcTrade.show(getActivity(), detailPage, showParams, null, exParams, new AlibcTradeCallback() {

            @Override
            public void onTradeSuccess(TradeResult tradeResult) {
                L.e(TAG,tradeResult.resultType.name());
                //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
            }

            @Override
            public void onFailure(int code, String msg) {
                //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
            }
        });
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
            String url = UrlUtil.getUrl(this, R.string.url_category_list);
            HttpClientProxy.getInstance().postAsynSQS(url, REQUEST_ID, parm, this);
        }

    }


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
