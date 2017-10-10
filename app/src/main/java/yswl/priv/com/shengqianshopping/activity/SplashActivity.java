package yswl.priv.com.shengqianshopping.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import yswl.com.klibrary.base.MActivity;
import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.http.HttpClientProxy;
import yswl.priv.com.shengqianshopping.MainActivityV3;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.bean.ADbean;
import yswl.priv.com.shengqianshopping.bean.ResultUtil;
import yswl.priv.com.shengqianshopping.util.UrlUtil;

public class SplashActivity extends MActivity implements HttpCallback<JSONObject> {
    public static void startAct(Context context, ADbean aDbean) {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.putExtra("param", aDbean);
        context.startActivity(intent);

    }

    TextView mTextView;
    ImageView screenImg;
    CountDownTimer timer;
    ADbean aDbean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        screenImg = findView(R.id.splash_screen);
        mTextView = findView(R.id.timer);
        mTextView.setVisibility(View.GONE);
        request();
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1f);
        alphaAnimation.setDuration(3000);
        screenImg.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
//                if (aDbean != null) {
//                    SplashActivity.startAct(SplashActivity.this, aDbean);
//                } else {
//                    MainActivityV3.startAct(SplashActivity.this);
//                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }

        });


    }


    private void request() {
        String url = UrlUtil.getUrl(this, R.string.url_splash_screen);
        HttpClientProxy.getInstance().postAsynSQS(url, 1, null, this);
    }


    @Override
    public void onSucceed(int requestId, JSONObject result) {
        if (requestId == 1 && ResultUtil.isCodeOK(result)) {
            aDbean = ADbean.jsonToBean(result);
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (timer != null) {
                        timer.cancel();
                        MainActivityV3.startAct(SplashActivity.this);
                    }
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
                        Uri content_url = Uri.parse(aDbean.link);
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
                mTextView.setVisibility(View.VISIBLE);
                timer = new CountDownTimer((time + 1) * 1000, 1000) {
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
    }

    @Override
    public void onFail(int requestId, String errorMsg) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}