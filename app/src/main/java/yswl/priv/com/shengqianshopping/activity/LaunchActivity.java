package yswl.priv.com.shengqianshopping.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

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

public class LaunchActivity extends MActivity implements HttpCallback<JSONObject> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        request();
        ImageView imageView = findView(R.id.launch);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1f);
        alphaAnimation.setDuration(3000);
        imageView.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                if (aDbean != null) {
                    SplashActivity.startAct(LaunchActivity.this, aDbean);
                } else {
                    MainActivityV3.startAct(LaunchActivity.this);
                }
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
        HttpClientProxy.getInstance().postAsyn(url, 1, null, this);
    }


    ADbean aDbean;

    @Override
    public void onSucceed(int requestId, JSONObject result) {
        if (requestId == 1 && ResultUtil.isCodeOK(result)) {
            aDbean = ADbean.jsonToBean(result);

        }
    }

    @Override
    public void onFail(int requestId, String errorMsg) {

    }
}
