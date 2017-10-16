package yswl.priv.com.shengqianshopping.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yswl.com.klibrary.base.MFragment;
import yswl.com.klibrary.browser.BrowserActivity;
import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.http.HttpClientProxy;
import yswl.com.klibrary.util.ToastUtil;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.activity.SearchActivity;
import yswl.priv.com.shengqianshopping.http.SqsHttpClientProxy;
import yswl.priv.com.shengqianshopping.util.AlibcUtil;
import yswl.priv.com.shengqianshopping.util.UrlUtil;

/**
 * Created by kangpAdministrator on 2017/9/27 0027.
 * Emial kangpeng@yunhetong.net
 */

public class RebateFragment extends MFragment {
    @BindView(R.id.al_search_input)
    EditText mEdit;
    @BindView(R.id.title)
    TextView title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rebate, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        title.setText("淘宝搜索");
//        view.findViewById(R.id.introduce_fanli).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BrowserActivity.start("返利说明", "https://www.baidu.com", getActivity());
//            }
//        });


    }

    @OnClick({R.id.al_search})
    public void onClick(View v) {
        if (v.getId() == R.id.al_search) {
            String key = mEdit.getText().toString().trim();
            if (TextUtils.isEmpty(key)) {
                return;
            }
            SearchActivity.startActivity(getActivity(), key);
        }
    }

}