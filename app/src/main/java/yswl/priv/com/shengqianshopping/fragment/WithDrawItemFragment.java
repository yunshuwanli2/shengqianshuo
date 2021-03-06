package yswl.priv.com.shengqianshopping.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yswl.com.klibrary.base.MFragment;
import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.http.HttpClientProxy;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.bean.OrderBean;
import yswl.priv.com.shengqianshopping.bean.ResultUtil;
import yswl.priv.com.shengqianshopping.fragment.adapter.MyWithdrawItemRecyclerViewAdapter;
import yswl.priv.com.shengqianshopping.http.SqsHttpClientProxy;
import yswl.priv.com.shengqianshopping.manager.UserManager;
import yswl.priv.com.shengqianshopping.util.UrlUtil;

/**
 * 提现 订单
 */
public class WithDrawItemFragment extends MFragment implements HttpCallback<JSONObject> {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WithDrawItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static WithDrawItemFragment newInstance(int columnCount) {
        WithDrawItemFragment fragment = new WithDrawItemFragment();
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

    MyWithdrawItemRecyclerViewAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list_withdraw, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mAdapter = new MyWithdrawItemRecyclerViewAdapter();
            recyclerView.setAdapter(mAdapter);
        }
        requstDate();
        return view;
    }

    private void requstDate() {
        String url = UrlUtil.getUrl(getActivity(), R.string.url_withdraw_detail);
        Map<String, Object> map = new HashMap<>();
        map.put("uid", UserManager.getUid(getActivity()));
        map.put("token", UserManager.getToken(getActivity()));
        SqsHttpClientProxy.postAsynSQS(url, 100, map, this);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    List<OrderBean> orders;

    @Override
    public void onSucceed(int requestId, JSONObject result) {
        if (ResultUtil.isCodeOK(result)) {
            JSONArray jsonArr = ResultUtil.analysisData(result).optJSONArray(ResultUtil.LIST);
            orders = OrderBean.jsonToList(jsonArr);
            mAdapter.setmValues(orders);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFail(int requestId, String errorMsg) {

    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(OrderBean item);
    }
}
