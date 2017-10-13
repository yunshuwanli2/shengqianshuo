package yswl.priv.com.shengqianshopping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
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
import yswl.com.klibrary.base.MActivity;
import yswl.com.klibrary.browser.BrowserActivity;
import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.http.HttpClientProxy;
import yswl.com.klibrary.http.okhttp.MDeviceUtil;
import yswl.com.klibrary.util.L;
import yswl.com.klibrary.util.MAppInfoUtil;
import yswl.com.klibrary.util.ToastUtil;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.bean.CrazyProductDetail;
import yswl.priv.com.shengqianshopping.bean.ProductDetail;
import yswl.priv.com.shengqianshopping.bean.ResultUtil;
import yswl.priv.com.shengqianshopping.fragment.adapter.GridRecyclerFragmentAdapter;
import yswl.priv.com.shengqianshopping.fragment.adapter.ListRecyclerFragmentAdapter;
import yswl.priv.com.shengqianshopping.http.SqsHttpClientProxy;
import yswl.priv.com.shengqianshopping.util.AlibcUtil;
import yswl.priv.com.shengqianshopping.util.UrlUtil;

public class SearchActivity extends MActivity implements HttpCallback<JSONObject>, OnRefreshListener, OnLoadMoreListener {

    public static void startActivity(Activity context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    private final int REFRESH = 1;//刷新标志
    private final int LOADMORE = 2;//加载更多
    private int GETDTATYPE = REFRESH;//当前获取数据方式（1刷新，2加载更多）
    private String lastID = "1";
    private boolean ALLOWLOADMORE = true;//是否允许上拉加载

    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    @BindView(R.id.swipe_target)
    RecyclerView mRecyclerView;
    GridRecyclerFragmentAdapter mAdapter;
    EditText mEdit;
    ImageView mClear;
    ImageView mSearch;
    List<ProductDetail> mProductList = new ArrayList<>();


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
        setHomeAsUpShowAndEnabled(true);
        initRefesh();
        mEdit = findView(R.id.toolbar_search_edit);
        mSearch = findView(R.id.toolbar_search);
        mClear = findView(R.id.toobar_clear);
        mClear.setVisibility(View.GONE);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requstData();
            }
        });
        mEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    mClear.setVisibility(View.VISIBLE);
                } else {
                    mClear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdit.setText("");
            }
        });

    }

    void setHomeAsUpShowAndEnabled(boolean boo) {
        getSupportActionBar().setHomeButtonEnabled(boo); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(boo);
    }

    private void initRefesh() {
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new GridRecyclerFragmentAdapter();
        mAdapter.setOnItemClickListener(new GridRecyclerFragmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                List<ProductDetail> products = mProductList;
                if (products == null || products.size() == 0) return;
                ProductDetail detail = products.get(position);
                AlibcUtil.openBrower(detail, SearchActivity.this);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }


    /**
     * 1. like | 不可 | 查询关键字 | 女装
     * 1. lastId | 可 | | 默认0
     * 1. count | 可 | | 默认20
     */
    private void requstData() {
        if (GETDTATYPE == REFRESH) {
            lastID = "0";
        }
        String key = mEdit.getText().toString().trim();
        if (TextUtils.isEmpty(key)) return;
        String url = UrlUtil.getUrl(this, R.string.url_product_search);
        Map<String, Object> map = new HashMap<>();
        map.put("like", key);
        map.put("count", 20);
        map.put("lastId", lastID);
       SqsHttpClientProxy.postAsynSQS(url, 1002, map, this);
    }

    @Override
    public void onSucceed(int requestId, JSONObject result) {
        if (GETDTATYPE == REFRESH) {
            swipeToLoadLayout.setRefreshing(false);
            mProductList.clear();
        } else {
            swipeToLoadLayout.setLoadingMore(false);
        }
        if (ResultUtil.isCodeOK(result)) {
            lastID = ResultUtil.analysisData(result).optString("lastId");
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
    public void onRefresh() {
        //刷新
        GETDTATYPE = REFRESH;
        ALLOWLOADMORE = true;
        requstData();
    }

    @Override
    public void onLoadMore() {
        GETDTATYPE = LOADMORE;
        if (ALLOWLOADMORE) {
            requstData();
        } else {
            swipeToLoadLayout.setLoadingMore(false);
        }
    }
}
