package yswl.priv.com.shengqianshopping.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import yswl.com.klibrary.base.MFragment;
import yswl.com.klibrary.browser.BrowserActivity;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.util.AlibcUtil;
import yswl.priv.com.shengqianshopping.util.UrlUtil;

/**
 * Created by kangpAdministrator on 2017/9/27 0027.
 * Emial kangpeng@yunhetong.net
 */

public class RebateFragment extends MFragment {
    EditText mEdit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rebate, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mEdit = (EditText) view.findViewById(R.id.al_search_input);
//        view.findViewById(R.id.introduce_fanli).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BrowserActivity.start("返利说明", "https://www.baidu.com", getActivity());
//            }
//        });
        view.findViewById(R.id.al_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = mEdit.getText().toString().trim();
                if (TextUtils.isEmpty(key)) {
                    return;
                }
                String url = getActivity().getResources().getString(R.string.url_search_tool, key);
                AlibcUtil.openBrower2(url, getActivity());
            }
        });

    }
}
