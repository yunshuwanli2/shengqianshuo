package yswl.priv.com.shengqianshopping.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
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
import yswl.priv.com.shengqianshopping.bean.ADbean;
import yswl.priv.com.shengqianshopping.bean.ResultUtil;
import yswl.priv.com.shengqianshopping.util.UrlUtil;

public class SplashActivity extends MActivity {
    public static void startAct(Context context, ADbean aDbean) {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.putExtra("param", aDbean);
        context.startActivity(intent);

    }

    TextView mTextView;
    ImageView screenImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ADbean aDbean = getData();
        screenImg = findView(R.id.splash_screen);
        mTextView = findView(R.id.timer);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivityV3.startAct(SplashActivity.this);
            }
        });

        if (!TextUtils.isEmpty(aDbean.imgUrl)) {
            Glide.with(this).asDrawable().load(aDbean.imgUrl).into(screenImg);
        }
        if (!TextUtils.isEmpty(aDbean.link)) {
            screenImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse("https://www.baidu.com");
                    intent.setData(content_url);
                    startActivity(intent);
//                        BrowserActivity.start(
//                                MAppInfoUtil.getAppName(v.getContext())
//                                , "https://www.baidu.com", SplashActivity.this);
                }
            });
        }

        if (!TextUtils.isEmpty(aDbean.s)) {
            int time = Integer.parseInt(aDbean.s);
            CountDownTimer timer = new CountDownTimer((time + 1) * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTextView.setText("跳过" + millisUntilFinished / 1000 + "");
                }

                @Override
                public void onFinish() {
                    MainActivityV3.startAct(SplashActivity.this);
                }
            };
            timer.start();
        }


    }


    public ADbean getData() {
        ADbean adbean = (ADbean) getIntent().getSerializableExtra("param");
        return adbean;
    }
}
