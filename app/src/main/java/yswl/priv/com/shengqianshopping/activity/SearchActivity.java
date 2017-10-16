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
import butterknife.OnClick;
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
import yswl.priv.com.shengqianshopping.bean.TBProductDetail;
import yswl.priv.com.shengqianshopping.fragment.adapter.GridRecyclerFragmentAdapter;
import yswl.priv.com.shengqianshopping.fragment.adapter.ListRecyclerFragmentAdapter;
import yswl.priv.com.shengqianshopping.http.SqsHttpClientProxy;
import yswl.priv.com.shengqianshopping.util.AlibcUtil;
import yswl.priv.com.shengqianshopping.util.UrlUtil;

/**
 * 两种方式搜索
 * 一：首页搜索栏进入 无关键字
 * 二：返利工具进入 携带关键字
 */
public class SearchActivity extends MActivity implements HttpCallback<JSONObject>, OnRefreshListener, OnLoadMoreListener {

    public static void startActivity(Activity context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    /**
     * 返利页进入 携带关键字
     *
     * @param context
     * @param key
     */
    public static void startActivity(Activity context, String key) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra("key", key);
        context.startActivity(intent);
    }

    private static boolean IS_TB_SEARCH = false;
    private static final int REFRESH = 1;//刷新标志
    private static final int LOADMORE = 2;//加载更多
    private int pageNo = 1;
    private boolean ALLOWLOADMORE = true;//是否允许上拉加载

    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    @BindView(R.id.swipe_target)
    RecyclerView mRecyclerView;
    @BindView(R.id.toobar_clear)
    ImageView mClear;
    @BindView(R.id.toolbar_search_edit)
    EditText mEdit;
    @BindView(R.id.toolbar_search)
    ImageView mSearch;

    GridRecyclerFragmentAdapter mAdapter;
    List<ProductDetail> mProductList = new ArrayList<>();
    List<TBProductDetail> mTBProductList = new ArrayList<>();

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

        //由判断是否搜索淘宝实时数据
        String key = getIntent().getStringExtra("key");
        if (!TextUtils.isEmpty(key)) {
            IS_TB_SEARCH = true;
            mEdit.setText(key);
            requstData(REFRESH);
        }

        initToolbar();
        initRefesh();
        initUI();
    }

    void initToolbar() {
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
        setHomeAsUpShowAndEnabled(true);
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

    void initUI() {
        mClear.setVisibility(View.GONE);
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


    }

    @OnClick({R.id.toobar_clear, R.id.toolbar_search})
    public void onClick(View v) {
        if (v.getId() == R.id.toobar_clear) {
            mEdit.setText("");
        } else if (v.getId() == R.id.toolbar_search) {
            requstData(REFRESH);
        }
    }

    /**
     * 由首页搜索栏 请求
     */
    private void requstData(int tag) {
        if (tag == REFRESH) {
            pageNo = 1;
        }
        String key = mEdit.getText().toString().trim();
        if (TextUtils.isEmpty(key)) return;
        String url = "";
        if (IS_TB_SEARCH) {
            url = UrlUtil.getUrl(this, R.string.url_product_search);
        } else {
            url = UrlUtil.getUrl(this, R.string.url_tb_search);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("search", key);
        map.put("pageSize", 20);
        map.put("pageNo", pageNo);
        SqsHttpClientProxy.postAsynSQS(url, 1002, map, this);
    }

    @Override
    public void onSucceed(int requestId, JSONObject result) {
        swipeToLoadLayout.setRefreshing(false);
        pageNo++;
        if (requestId == REFRESH) {
            mProductList.clear();
            mTBProductList.clear();
        }
        if (ResultUtil.isCodeOK(result)) {
            if (IS_TB_SEARCH) {
                List<TBProductDetail> tempList = TBProductDetail.jsonToList(
                        ResultUtil.analysisData(result).optJSONArray(ResultUtil.LIST));
                if (tempList != null && tempList.size() > 0) {
                    mTBProductList.addAll(tempList);
                    mAdapter.setmProductList(mProductList);
                    mAdapter.notifyDataSetChanged();
                } else {
                    ALLOWLOADMORE = false;
                }
            } else {
                List<ProductDetail> tempList = ProductDetail.jsonToList(
                        ResultUtil.analysisData(result).optJSONArray(ResultUtil.LIST));
                if (tempList != null && tempList.size() > 0) {
                    mProductList.addAll(tempList);
                    mAdapter.setmProductList(mProductList);
                    mAdapter.notifyDataSetChanged();
                } else {
                    ALLOWLOADMORE = false;
                }
            }

        }
    }

    @Override
    public void onFail(int requestId, String errorMsg) {
        swipeToLoadLayout.setLoadingMore(false);
    }


    @Override
    public void onRefresh() {
        //刷新
        ALLOWLOADMORE = true;
        requstData(REFRESH);
    }

    @Override
    public void onLoadMore() {
        if (ALLOWLOADMORE) {
            requstData(LOADMORE);
        } else {
            swipeToLoadLayout.setLoadingMore(false);
        }
    }
}
