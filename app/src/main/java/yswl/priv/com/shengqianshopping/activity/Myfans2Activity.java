package yswl.priv.com.shengqianshopping.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.base.MToolBarActivity;
import yswl.priv.com.shengqianshopping.bean.FansBean;
import yswl.priv.com.shengqianshopping.bean.ResultUtil;
import yswl.priv.com.shengqianshopping.http.SqsHttpClientProxy;
import yswl.priv.com.shengqianshopping.manager.UserManager;
import yswl.priv.com.shengqianshopping.util.UrlUtil;

public class Myfans2Activity extends MToolBarActivity implements HttpCallback<JSONObject> {
    public static void startAct(Context context) {
        context.startActivity(new Intent(context, Myfans2Activity.class));
    }

    @BindView(R.id.tv_fans)
    TextView mTvfans;
    @BindView(R.id.tv_amount)
    TextView mTvamount;
    @BindView(R.id.recycler_view)
    RecyclerView mRecycle;
    FansListViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfans2);
        ButterKnife.bind(this);
        setTitle("我的粉丝");
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mAdapter = new FansListViewAdapter();
        mRecycle.setLayoutManager(manager);
        mRecycle.setAdapter(mAdapter);
        requestData();
    }

    private void requestData() {
        String url = UrlUtil.getUrl(this, R.string.url_fans_list);
        String uid = UserManager.getUid(this);
        String token = UserManager.getToken(this);
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("token", token);
        SqsHttpClientProxy.postAsynSQS(url, 100, map, this);
    }

    List<FansBean> mFans;

    @Override
    public void onSucceed(int requestId, JSONObject result) {
        if (requestId == 100 && ResultUtil.isCodeOK(result)) {
            JSONObject jsonObj = result.optJSONObject("message");
            JSONObject jsonTotal = jsonObj.optJSONObject("total");
            JSONArray jsonArrList = jsonObj.optJSONArray("list");
            if (jsonTotal != null) {
                String fans = jsonTotal.optString("friends");
                mTvfans.setText("累计粉丝数：" + fans + "人");
                String amount = jsonTotal.optString("amount");
                mTvamount.setText(amount + "元");
            }
            mFans = FansBean.jsonToList(jsonArrList);
            mAdapter.setList(mFans);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFail(int requestId, String errorMsg) {

    }


    class FansListViewAdapter extends RecyclerView.Adapter<FansListViewAdapter.MyViewHolder> {

        public List<FansBean> getList() {
            return list;
        }

        public void setList(List<FansBean> list) {
            this.list = list;
        }

        private List<FansBean> list;

        public FansListViewAdapter() {
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(Myfans2Activity.this).inflate(R.layout.fans_item, parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.mUid.setText("粉丝ID:" + list.get(position).uid);
            holder.mPhone.setText("电话:" + list.get(position).phone);
            holder.mAmount.setText(list.get(position).reward);
        }

        @Override
        public int getItemCount() {
            return list != null && list.size() > 0 ? list.size() : 0;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.tv_fans_id)
            TextView mUid;
            @BindView(R.id.tv_phone)
            TextView mPhone;
            @BindView(R.id.tv_amount)
            TextView mAmount;

            public MyViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

}
