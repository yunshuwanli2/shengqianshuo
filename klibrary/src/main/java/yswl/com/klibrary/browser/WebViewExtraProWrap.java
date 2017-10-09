package yswl.com.klibrary.browser;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.RelativeLayout;


/**
 * webview 扩展
 */
public class WebViewExtraProWrap extends RelativeLayout implements WebViewExtra.Progress {
    public static final String TAG = "WebViewExtraProWrap";
    private WebViewExtra mWebViewExtraNormal;
    private WebViewProgressBar mWebViewProgressBar;
    public LayoutParams lp1 = new LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT);
    public LayoutParams lp2 = new LayoutParams(
            LayoutParams.MATCH_PARENT, 5);
    public String url;

    public WebViewExtraProWrap(Context context) {
        super(context);
        init(context);
    }

    public WebViewExtraProWrap(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WebViewExtraProWrap(Context context, AttributeSet attrs,
                               int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // public WebViewExtraProWrap(Context context, AttributeSet attrs,
    // int defStyleAttr, int defStyleRes) {
    // super(context, attrs, defStyleAttr, defStyleRes);
    // init(context);
    // }

    private void init(Context context) {
        mWebViewExtraNormal = new WebViewExtra(context);
        this.addView(mWebViewExtraNormal, lp1);
        mWebViewExtraNormal.setProgress(this);
        mWebViewExtraNormal.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        mWebViewProgressBar = new WebViewProgressBar(context);
        this.addView(mWebViewProgressBar, lp2);
    }

    @Override
    public void onProgressChanged(int newProgress) {
        if (mWebViewProgressBar != null)
            mWebViewProgressBar.setProgress(newProgress);
    }

    public void loadJs(String url) {
        if (mWebViewExtraNormal != null)
            mWebViewExtraNormal.loadUrl(url);
    }

    public void clearCache(boolean b) {
        if (mWebViewExtraNormal != null)
            mWebViewExtraNormal.clearCache(b);
    }

    public void reload(Activity ac) {
        if (mWebViewExtraNormal != null && !TextUtils.isEmpty(this.url)) {
            mWebViewExtraNormal.clearHistory();
            // this.loadUrl(ac, url);
            mWebViewExtraNormal.reload();
        }
    }

    public void loadUrl(Activity activity, String url) {
        this.url = url;
        if (mWebViewExtraNormal != null)
            mWebViewExtraNormal.loadUrl(activity, url);
    }

    public void addJavascriptInterface(Object obj, String interfaceName) {
        if (mWebViewExtraNormal != null)
            mWebViewExtraNormal.addJavascriptInterface(obj, interfaceName);
    }

    public WebSettings getNormalSettings() {
        if (mWebViewExtraNormal != null)
            return mWebViewExtraNormal.getSettings();
        return null;
    }

//	public void setmNormalInputLister(
//			WebViewExtra.MWebViewExtraInputMethodCallBack mInputLister) {
//		if (mWebViewExtraNormal != null)
//			mWebViewExtraNormal.setmInputLister(mInputLister);
//	}

    public int getWebViewScrollX() {
        return mWebViewExtraNormal.getScrollX();
    }

    public int getWebViewScrollY() {
        return mWebViewExtraNormal.getScrollY();
    }

    public void setWebViewOnFocusChangeListener(
            OnFocusChangeListener onfocuschangelistener) {
        if (mWebViewExtraNormal != null)
            mWebViewExtraNormal.setOnFocusChangeListener(onfocuschangelistener);
    }

//	public void setmNormalCallBackLister(
//			MWebViewExtraCallBack mMWebViewExtraCallBack) {
//		if (mWebViewExtraNormal != null)
//			mWebViewExtraNormal
//					.setmWebViewCallBackLister(mMWebViewExtraCallBack);
//	}

    public void hideSoftKeyboard() {
        if (mWebViewExtraNormal != null)
            mWebViewExtraNormal.hideSoftKeyboard();
    }

    public float getWebViewScale() {
        if (mWebViewExtraNormal != null) {
            return mWebViewExtraNormal.getmScale();
        }
        return 1;
    }

    public void enableJs() {
        if (mWebViewExtraNormal != null) {
            mWebViewExtraNormal.getSettings().setJavaScriptEnabled(true);
        }
    }

    public void disableZoom() {
        if (mWebViewExtraNormal != null) {
            WebSettings settings = mWebViewExtraNormal.getSettings();
            settings.setBuiltInZoomControls(false);
            settings.setDisplayZoomControls(false);
            settings.setSupportZoom(false);
            //触发onScaleChanged
            mWebViewExtraNormal.setInitialScale(1);
//			settings.setUseWideViewPort(false);
//			settings.setLoadWithOverviewMode(false);
        }
    }
}
