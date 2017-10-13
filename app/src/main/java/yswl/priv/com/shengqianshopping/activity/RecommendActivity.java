package yswl.priv.com.shengqianshopping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yswl.com.klibrary.base.MFragment;
import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.http.HttpClientProxy;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.base.MToolBarActivity;
import yswl.priv.com.shengqianshopping.bean.CategoryBean;
import yswl.priv.com.shengqianshopping.bean.ResultUtil;
import yswl.priv.com.shengqianshopping.bean.SerializableParamsMap;
import yswl.priv.com.shengqianshopping.fragment.GridRecyclerviewFragment;
import yswl.priv.com.shengqianshopping.http.SqsHttpClientProxy;
import yswl.priv.com.shengqianshopping.util.UrlUtil;

/**
 * 小编推荐
 */
public class RecommendActivity extends MToolBarActivity implements HttpCallback<JSONObject> {


    private static final int REQUEST_ID_RECOM = 1004;

    public static void startActivity(Activity context) {
        Intent intent = new Intent(context, RecommendActivity.class);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        String title = "小编推荐";
        setTitle(title);
        requestRecommed();


    }

    /**
     * 单独接口：
     * 小编推荐
     */
    public void requestRecommed() {
        Map<String, Object> parm = new HashMap<>();
        parm.put("type", "3");
        String url = UrlUtil.getUrl(this, R.string.url_category_type_list);
        SqsHttpClientProxy.postAsynSQS(url, REQUEST_ID_RECOM, parm, this);
    }


    @Override
    public void onSucceed(int requestId, JSONObject result) {
        if (requestId == REQUEST_ID_RECOM && ResultUtil.isCodeOK(result)) {
            List<CategoryBean> caregorys = CategoryBean.
                    jsonToList(ResultUtil.analysisData(result).optJSONArray(ResultUtil.LIST));
            if (caregorys == null || caregorys.size() == 0) return;
            CategoryBean category = caregorys.get(0);
            Map<String, Object> mParam = new HashMap<>();
            mParam.put("pid", category.pid);
            MFragment fragment = GridRecyclerviewFragment.newInstance(new SerializableParamsMap(mParam));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content, fragment).commit();
        }
    }

    @Override
    public void onFail(int requestId, String errorMsg) {

    }
}
