package yswl.priv.com.shengqianshopping.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.http.HttpClientProxy;
import yswl.com.klibrary.util.GsonUtil;
import yswl.com.klibrary.util.ToastUtil;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.activity.adapter.ScoreboardActivityAdapter;
import yswl.priv.com.shengqianshopping.base.MToolBarActivity;
import yswl.priv.com.shengqianshopping.bean.HeroeListBean;
import yswl.priv.com.shengqianshopping.bean.ResultUtil;
import yswl.priv.com.shengqianshopping.util.SharedPreUtils;
import yswl.priv.com.shengqianshopping.util.UrlUtil;

/**
 * 英雄榜
 */

public class ScoreboardActivity extends MToolBarActivity implements HttpCallback<JSONObject> {

    @BindView(R.id.scoreboard_recyclerview)
    RecyclerView recyclerview;

    private Unbinder unbinder;
    private List<HeroeListBean> list = new ArrayList<>();//数据
    private ScoreboardActivityAdapter adapter;//适配器

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        unbinder = ButterKnife.bind(this);
        init();
        dataBind();
        eventBind();
    }

    private void init() {
        setTitle("积分排行榜");
    }

    private void dataBind() {
        getData();
        adapter = new ScoreboardActivityAdapter(this, list);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);
    }

    private void eventBind() {

    }

    @Override
    public void onSucceed(int requestId, JSONObject result) {
        if (ResultUtil.isCodeOK(result)) {
//            List<HeroeListBean> tempList = GsonUtil.GsonToList(GsonUtil.getJSONObjectKeyVal(GsonUtil.getJSONObjectKeyVal(result.toString(), ResultUtil.MSG), ResultUtil.LIST), HeroeListBean.class);
            List<HeroeListBean> tempList = HeroeListBean.jsonToList(ResultUtil.analysisData(result).optJSONArray(ResultUtil.LIST));
            if (tempList != null) {
                for (int i = 0; i < tempList.size(); i++) {
                    list.add(tempList.get(i));
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onFail(int requestId, String errorMsg) {
        ToastUtil.showToast(errorMsg);
    }

    //获取数据
    private void getData() {
        String url = UrlUtil.getUrl(ScoreboardActivity.this, R.string.url_hero_list);
        Map<String, Object> map = new HashMap<>();
        map.put("uid", SharedPreUtils.getInstance(ScoreboardActivity.this).getValueBySharedPreferences(SharedPreUtils.UID, ""));
        HttpClientProxy.getInstance().postAsyn(url, 1234, map, ScoreboardActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
