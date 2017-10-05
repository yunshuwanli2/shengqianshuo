package yswl.priv.com.shengqianshopping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yswl.com.klibrary.base.MActivity;
import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.http.HttpClientProxy;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.bean.ProductDetail;
import yswl.priv.com.shengqianshopping.bean.ResultUtil;
import yswl.priv.com.shengqianshopping.util.UrlUtil;

public class SearchActivity extends MActivity implements HttpCallback<JSONObject> {

    public static void startActivity(Activity context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    EditText mEdit;
    ImageView mClear;
    ImageView mSearch;
    List<ProductDetail> mProductList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);

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

    /**
     * 1. like | 不可 | 查询关键字 | 女装
     * 1. lastId | 可 | | 默认0
     * 1. count | 可 | | 默认20
     */
    private void requstData() {
        String key = mEdit.getText().toString().trim();
        if (TextUtils.isEmpty(key)) return;

        String url = UrlUtil.getUrl(this, R.string.url_product_search);
        Map<String, Object> map = new HashMap<>();
        map.put("like", key);
        map.put("count", 20);
        HttpClientProxy.getInstance().postAsyn(url, 1002, map, this);
    }

    @Override
    public void onSucceed(int requestId, JSONObject result) {
        if (ResultUtil.isCodeOK(result)) {
            mProductList = ProductDetail.jsonToList(
                    ResultUtil.analysisData(result).optJSONArray(ResultUtil.LIST));

        }
    }

    @Override
    public void onFail(int requestId, String errorMsg) {

    }
}
