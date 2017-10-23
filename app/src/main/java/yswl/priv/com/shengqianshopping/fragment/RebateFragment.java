package yswl.priv.com.shengqianshopping.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.baichuan.android.trade.model.OpenType;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yswl.com.klibrary.base.MFragment;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.util.AlibcUtil;

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
            //TODO 等百川sdk 转链接的权限开通 就用淘宝实时搜索
//            SearchActivity.startActivity(getActivity(), key);

            String url = getActivity().getResources().getString(R.string.url_search_tool, key);
            AlibcUtil.openBrower2(url, OpenType.H5, getActivity());
        }
    }

}