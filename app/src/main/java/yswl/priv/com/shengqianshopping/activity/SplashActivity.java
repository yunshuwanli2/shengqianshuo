package yswl.priv.com.shengqianshopping.activity;

import android.content.pm.ApplicationInfo;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONObject;
import org.w3c.dom.Text;

import yswl.com.klibrary.base.MActivity;
import yswl.com.klibrary.browser.BrowserActivity;
import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.http.HttpClientProxy;
import yswl.com.klibrary.util.MAppInfoUtil;
import yswl.priv.com.shengqianshopping.MainActivityV3;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.bean.ResultUtil;
import yswl.priv.com.shengqianshopping.util.UrlUtil;

public class SplashActivity extends MActivity implements HttpCallback<JSONObject> {

    TextView mTextView;
    ImageView screenImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        screenImg = findView(R.id.splash_screen);
        mTextView = findView(R.id.timer);
        request();
    }

    private void request() {
        String url = UrlUtil.getUrl(this, R.string.url_splash_screen);
        HttpClientProxy.getInstance().postAsyn(url, 1, null, this);
    }

    @Override
    public void onSucceed(int requestId, JSONObject result) {
        if (requestId == 1 && ResultUtil.isCodeOK(result)) {
            JSONObject obj = ResultUtil.analysisData(result);
            final String url = obj.optString("imgUrl");
            final String link = obj.optString("link");
            String time_s = obj.optString("s");

            if (!TextUtils.isEmpty(url)) {
                Glide.with(this).asDrawable().load(url).into(screenImg);
            }
            if (TextUtils.isEmpty(link)) {
                screenImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BrowserActivity.start(
                                MAppInfoUtil.getAppName(v.getContext())
                                , "https://www.baidu.com", SplashActivity.this);
                    }
                });
            }

            if (!TextUtils.isEmpty(time_s)) {
                int time = Integer.parseInt(time_s);
                //意思就是每隔xxx会回调一次方法onTick，然后xxx之后会回调onFinish方法。
                CountDownTimer timer = new CountDownTimer(time * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        mTextView.setText(millisUntilFinished / 1000 + "");
                    }

                    @Override
                    public void onFinish() {
                        MainActivityV3.startAct(SplashActivity.this);
                    }
                };
                timer.start();
            }

        }
    }


    @Override
    public void onFail(int requestId, String errorMsg) {

    }
}
