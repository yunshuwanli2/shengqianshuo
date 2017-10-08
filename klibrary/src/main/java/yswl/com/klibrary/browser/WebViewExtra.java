package yswl.com.klibrary.browser;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.HttpAuthHandler;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import yswl.com.klibrary.MApplication;
import yswl.com.klibrary.http.okhttp.MScreenUtils;
import yswl.com.klibrary.util.MKeyBoardUtils;


/**
 * webview 扩展
 */
public class WebViewExtra extends WebView {
    private static final String TAG = "WebViewExtra";
    private Progress mProgress;
    public float mScale;

//    public MWebViewExtraInputMethodCallBack mInputLister;
//    public void setmInputLister(MWebViewExtraInputMethodCallBack mInputLister) {
//        this.mInputLister = mInputLister;
//    }
//
//    public MWebViewExtraCallBack mMWebViewExtraCallBack;
//    public void setmWebViewCallBackLister(
//            MWebViewExtraCallBack mMWebViewExtraCallBack) {
//        this.mMWebViewExtraCallBack = mMWebViewExtraCallBack;
//    }

    public WebViewExtra(Context context) {
        super(context);
        init();
    }

    public WebViewExtra(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WebViewExtra(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public WebViewExtra(Context context, AttributeSet attrs, int defStyle,
                        boolean privateBrowsing) {
        super(context, attrs, defStyle, privateBrowsing);
        init();
    }

    public float getmScale() {
        return mScale;
    }

    public void setmScale(float mScale) {
        this.mScale = mScale;
    }

    public void setProgress(Progress progress) {
        this.mProgress = progress;
    }

    private void init() {
        if (MApplication.getApplication().getDebugSetting()
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        setWebViewClient();
        setWebChromeClient();
        WebSettings settings = this.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setUserAgentString(settings.getUserAgentString() + MScreenUtils.getScreenInfo(MApplication.getApplication()));
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        // // this.getSettings().set
        // this.setInitialScale(1);
        this.getSettings().setSupportZoom(true);
        this.setScrollContainer(false);
        this.setScrollbarFadingEnabled(false);
        // this.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        this.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_INSET);
        settings.setDefaultTextEncodingName("UTF-8");
        // settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        settings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        settings.setDisplayZoomControls(true);
        settings.setSupportZoom(true); // 支持缩放
        settings.setUseWideViewPort(true);
        //
        this.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        this.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    }


    boolean pagenotfound = false;

    public void setWebViewClient() {
        this.setWebViewClient(new WebViewClient() {

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                super.onReceivedHttpAuthRequest(view, handler, host, realm);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
            }

            @Override
            public void onScaleChanged(WebView view, float oldScale,
                                       float newScale) {
                setmScale(newScale);
                super.onScaleChanged(view, oldScale, newScale);
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
//                if (mInputLister != null && mInputLister.MKeyEvent(view, event)) {
//                    return true;
//                }
                return super.shouldOverrideKeyEvent(view, event);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // handler.cancel(); // Android默认的处理方式
                handler.proceed(); // 接受所有网站的证书
                // handleMessage(Message msg); // 进行其他处理
                // super.onReceivedSslError(view, handler, error);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // Toast.makeText(activity, "Oh no! " + description,
                // Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
                super.onReceivedLoginRequest(view, realm, account, args);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                return super.shouldInterceptRequest(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                if (checkUrl(url)) {
                }
                super.onLoadResource(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //附件的url 规范
                if (checkUrl(url)) {
                }
                view.loadUrl(url);
                return true;
            }
        });

    }

    private boolean checkUrl(String url) {
        boolean result = false;
//        if (!TextUtils.isEmpty(url) && (url.startsWith(LxUrlUtil.getJx())
//                || url.startsWith(LxUrlUtil.getHTTPSWWW())
//                || url.startsWith(LxUrlUtil.getJx())
//                || url.startsWith(LxUrlUtil.getHTTPWWW()))) {
//            if (url.contains(LxUrlUtil.getMobileLayoutForWebView())) {
//                ToastUtil.showToast(MAppManager.getApplication().getResources().getString(R.string.offline_notice));
//            }
//            result = true;
//        } else if (!TextUtils.isEmpty(url) && url.startsWith(LxUrlUtil.getWebCasUrlForWebView())) {
//            NewCallBack.safeGotoLoginStati(WebViewExtra.this.getContext());
//        } else {
//            Lx.d(TAG, "不允许操作:" + url);
//        }
        return result;
    }

    public void setWebChromeClient() {
        this.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                // TODO Auto-generated method stub
                super.onReceivedIcon(view, icon);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                CharSequence pnotfound = "The page cannot be found";
                if (title.contains(pnotfound)) {
                    pagenotfound = true;
                    view.stopLoading();
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (mProgress != null)
                    mProgress.onProgressChanged(newProgress);
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return super.onConsoleMessage(consoleMessage);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
//                if (mMWebViewExtraCallBack != null) {
//                    return mMWebViewExtraCallBack.MonJsAlert(view, url,
//                            message, result);
//                }
                return false;
            }
        });

    }

    public interface Progress {
        void onProgressChanged(int newProgress);
    }

    public void loadUrl(Context context, String url) {
        super.loadUrl(url);
    }


    public static void clearSeesion() {
        CookieSyncManager.createInstance(MApplication.getApplication());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();// 移除
        CookieSyncManager.getInstance().sync();
    }

    public void hideSoftKeyboard() {
        Context context = this.getContext();
        if (context instanceof Activity) {
            Activity ac = (Activity) context;
            MKeyBoardUtils.hideSoftKeyboard(ac);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
//        outAttrs.imeOptions = outAttrs.imeOptions | EditorInfo.IME_ACTION_DONE;
//        if (mInputLister != null) {
//            return new BaseInputConnection(this, true) {
//                @Override
//                public boolean commitText(CharSequence text,
//                                          int newCursorPosition) {
//                    if (mInputLister != null)
//                        mInputLister.MCommitText(text, newCursorPosition);
//                    return true;
//                }
//
//                @Override
//                public boolean sendKeyEvent(KeyEvent event) {
//                    return super.sendKeyEvent(event);
//                }
//
//            };
//
//        }
        return super.onCreateInputConnection(outAttrs);
    }

//    public interface MWebViewExtraCallBack {
//        public boolean MonJsAlert(WebView view, String url, String message,
//                                  JsResult result);
//
//        public void MBeforeLoadUrl(String url);
//    }
//
//    public interface MWebViewExtraInputMethodCallBack {
//        public boolean MKeyEvent(WebView view, KeyEvent event);
//
//        public boolean MCommitText(CharSequence text, int newCursorPosition);
//    }

    @Override
    public void reload() {
        super.reload();
    }
}
