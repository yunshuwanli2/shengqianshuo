package yswl.priv.com.shengqianshopping.activity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
        HttpClientProxy.getInstance().postAsynSQS(url, 1, null, this);
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
