package yswl.priv.com.shengqianshopping.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.HttpAuthHandler;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ali.auth.third.login.callback.LogoutCallback;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.AlibcTaokeParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyCartsPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyOrdersPage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yswl.com.klibrary.browser.BrowserActivity;
import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.util.L;
import yswl.priv.com.shengqianshopping.App;
import yswl.priv.com.shengqianshopping.MainActivityV3;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.activity.BindPhoneActivity;
import yswl.priv.com.shengqianshopping.activity.LoginActivity;
import yswl.priv.com.shengqianshopping.activity.SettingActivity;
import yswl.priv.com.shengqianshopping.bean.CrazyProductDetail;
import yswl.priv.com.shengqianshopping.bean.ProductDetail;
import yswl.priv.com.shengqianshopping.fragment.UserCenterFragment;
import yswl.priv.com.shengqianshopping.http.SqsHttpClientProxy;
import yswl.priv.com.shengqianshopping.manager.UserManager;

/**
 * Created by kangpAdministrator on 2017/10/9 0009.
 * Emial kangpeng@yunhetong.net
 */

public class AlibcUtil {

    private static final String TAG = AlibcUtil.class.getSimpleName();

    public static void initAlibc(final Context context) {
        AlibcTradeSDK.asyncInit(context, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                //初始化成功，设置相关的全局配置参数
                AlibcTradeSDK.setForceH5(true);
            }

            @Override
            public void onFailure(int code, String msg) {
                //初始化失败，可以根据code和msg判断失败原因，详情参见错误说明
                Toast.makeText(context, "TaeSDK 初始化失败 -- " + msg + "  " + code, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void openBrower3(ProductDetail detail, Activity context) {
        String jumpUrl = detail.couponClickUrl;
        if (TextUtils.isEmpty(jumpUrl)) {
            jumpUrl = detail.clickUrl;
        }
        if (TextUtils.isEmpty(jumpUrl)) {
            jumpUrl = detail.itemUrl;
        }
        openBrower2(jumpUrl, context);
    }

    public static void openBrower3(String url, final Activity context) {
        if (!UserManager.isLogin(context)) {
            LoginActivity.startActivity(context);
            return;
        } else if (!UserManager.isBindPhone(context)) {
            BindPhoneActivity.startActivity(context);
            return;
        }
        Map<String, String> exParams = new HashMap<>();
        exParams.put(AlibcConstants.ISV_CODE, "saveduoduo");
        AlibcPage detailPage = new AlibcPage(url);

        WebView webView = new WebView(context);
        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        webView.setScrollContainer(false);
        webView.setScrollbarFadingEnabled(false);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setBuiltInZoomControls(false); // 设置显示缩放按钮
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
//        settings.setJavaScriptEnabled(true);
        //
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        WebChromeClient webChromeClient = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                CharSequence pnotfound = "The page cannot be found";
                if (title.contains(pnotfound)) {
                    view.stopLoading();
                }
            }


        };

        WebViewClient webViewClient = new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // handler.cancel(); // Android默认的处理方式
                handler.proceed(); // 接受所有网站的证书
                // handleMessage(Message msg); // 进行其他处理
                // super.onReceivedSslError(view, handler, error);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                return super.shouldInterceptRequest(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //获取cookies
                if (url.contains("tbopen")) {
                    return false;
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }
        };

        AlibcShowParams showParams = new AlibcShowParams(OpenType.Native, false);
        AlibcTrade.show(context, webView, webViewClient, webChromeClient, detailPage, showParams, null, exParams, new AlibcTradeCallback() {
            @Override
            public void onTradeSuccess(TradeResult tradeResult) {
                //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
            }

            @Override
            public void onFailure(int code, String msg) {
                //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
            }
        });
    }

    /**
     * 同步一下cookie
     */
    public void synCookies(Context context, String url) {

        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
//        cookieManager.removeSessionCookie();//移除
        cookieManager.setCookie(url, AlibcLogin.getInstance().getSession().toString());
        CookieSyncManager.getInstance().sync();
    }

    //打开优惠券/或其他淘宝地址
    public static void openBrower2(String url, final Activity context) {
        openBrower2(url, null, context);
    }

    //打开优惠券/或其他淘宝地址
    public static void openBrower2(String url, OpenType type, final Activity context) {
        if (!UserManager.isLogin(context)) {
            LoginActivity.startActivity(context);
            return;
        } else if (!UserManager.isBindPhone(context)) {
            BindPhoneActivity.startActivity(context);
            return;
        }
        Map<String, String> exParams = new HashMap<>();
        exParams.put(AlibcConstants.ISV_CODE, "saveduoduo");
//        AlibcTaokeParams taokeParams = new AlibcTaokeParams("x", "x", "x");
        AlibcPage detailPage = new AlibcPage(url);
        OpenType openType = type == null ? OpenType.Native : type;
        AlibcShowParams showParams = new AlibcShowParams(openType, true);
        AlibcTrade.show(context, detailPage, showParams, null, exParams, new AlibcTradeCallback() {
            @Override
            public void onTradeSuccess(TradeResult tradeResult) {
                L.e(TAG, "---优惠券进入商品详情---： " + tradeResult.resultType.name());
                if (tradeResult.payResult != null) {
                    List li2 = tradeResult.payResult.paySuccessOrders;
                    StringBuilder sbl = new StringBuilder();
                    if (li2 != null) {
                        for (int i = 0; i < li2.size(); i++) {
                            sbl.append(li2.get(i).toString());
                            sbl.append("|");
                            L.e(TAG, "---优惠券进入商品详情---支付成功的订单详情： " + li2.get(i).toString());
                        }
                    }
                    sbl.deleteCharAt(sbl.length() - 1);
                    requestAfterBuy(context, sbl.toString());
                }

            }

            @Override
            public void onFailure(int code, String msg) {
            }
        });
    }

    public static void openBrower(ProductDetail detail, Activity context) {
        String jumpUrl = detail.couponClickUrl;
        if (TextUtils.isEmpty(jumpUrl)) {
            jumpUrl = detail.clickUrl;
        }
        if (TextUtils.isEmpty(jumpUrl)) {
            jumpUrl = detail.itemUrl;
        }
        openBrower2(jumpUrl, context);
    }


    //打开商品详情详情
    public static void openAlibcPage(final Activity context, CrazyProductDetail detail) {
        if (!UserManager.isLogin(context)) {
            LoginActivity.startActivity(context);
            return;
        } else if (!UserManager.isBindPhone(context)) {
            BindPhoneActivity.startActivity(context);
            return;
        }
        Map<String, String> exParams = new HashMap<>();
        exParams.put(AlibcConstants.ISV_CODE, "saveduoduo");
        AlibcBasePage detailPage = new AlibcDetailPage(detail.iid);
        AlibcShowParams showParams = new AlibcShowParams(OpenType.H5, true);
        AlibcTrade.show(context, detailPage, showParams, null, exParams, new AlibcTradeCallback() {

            @Override
            public void onTradeSuccess(TradeResult tradeResult) {
                L.e(TAG, tradeResult.resultType.name());
                //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                List li2 = tradeResult.payResult.paySuccessOrders;
                StringBuilder sbl = new StringBuilder();
                if (li2 != null) {
                    for (int i = 0; i < li2.size(); i++) {
                        sbl.append(li2.get(i).toString());
                        sbl.append("|");
                        L.e(TAG, "---订单操作---支付成功的订单详情： " + li2.get(i).toString());
                    }
                }
                sbl.deleteCharAt(sbl.length() - 1);
                requestAfterBuy(context, sbl.toString());
            }

            @Override
            public void onFailure(int code, String msg) {
                //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
            }
        });
    }

    public static void gotoAliOrder(final Activity context) {
        if (!UserManager.isLogin(context)) {
            LoginActivity.startActivity(context);
            return;
        } else if (!UserManager.isBindPhone(context)) {
            BindPhoneActivity.startActivity(context);
            return;
        }
        Map<String, String> exParams = new HashMap<>();
        exParams.put(AlibcConstants.ISV_CODE, "saveduoduo");
        /**
         * @param status   默认跳转页面；填写：0：全部；1：待付款；2：待发货；3：待收货；4：待评价
         * @param allOrder false 进行订单分域（只展示通过当前app下单的订单），true 显示所有订单
         */
        AlibcBasePage ordersPage = new AlibcMyOrdersPage(0, false);
        AlibcShowParams showParams = new AlibcShowParams(OpenType.H5, true);
        AlibcTrade.show(context, ordersPage, showParams, null, exParams,
                new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(TradeResult tradeResult) {
                        // TYPECART,
                        // TYPEPAY;
                        L.e(TAG, "订单操作 -- 返回成功 result : " + tradeResult.resultType.name());
                        L.e(TAG, "---订单操作---： " + tradeResult.resultType.name());
                        if (tradeResult.payResult != null) {
                            List li2 = tradeResult.payResult.paySuccessOrders;
                            StringBuilder sbl = new StringBuilder();
                            if (li2 != null) {
                                for (int i = 0; i < li2.size(); i++) {
                                    sbl.append(li2.get(i).toString());
                                    sbl.append("|");
                                    L.e(TAG, "---订单操作---支付成功的订单详情： " + li2.get(i).toString());
                                }
                            }
                            sbl.deleteCharAt(sbl.length() - 1);
                            requestAfterBuy(context, sbl.toString());
                        }

                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        L.e(TAG, "订单操作 -- 返回失败 code:" + code + " msg: " + msg);
                    }
                });

    }

    public static void gotoAliShoppingChe(final Activity context) {
        if (!UserManager.isLogin(context)) {
            LoginActivity.startActivity(context);
            return;
        } else if (!UserManager.isBindPhone(context)) {
            BindPhoneActivity.startActivity(context);
            return;
        }
        Map<String, String> exParams = new HashMap<>();
        exParams.put(AlibcConstants.ISV_CODE, "saveduoduo");
        AlibcBasePage myCartsPage = new AlibcMyCartsPage();
        AlibcShowParams showParams = new AlibcShowParams(OpenType.H5, true);
        AlibcTrade.show(context, myCartsPage, showParams, null, exParams,
                new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(TradeResult tradeResult) {
                        // TYPECART,
                        // TYPEPAY;
                        L.e(TAG, "购物车操作 -- 返回成功 result : " + tradeResult.resultType.name());
                        L.e(TAG, "---购物车操作---： " + tradeResult.resultType.name());
                        if (tradeResult.payResult != null) {
                            List li2 = tradeResult.payResult.paySuccessOrders;
                            StringBuilder sbl = new StringBuilder();
                            if (li2 != null) {
                                for (int i = 0; i < li2.size(); i++) {
                                    sbl.append(li2.get(i).toString());
                                    sbl.append("|");
                                    L.e(TAG, "---购物车操作---支付成功的订单详情： " + li2.get(i).toString());
                                }
                            }
                            sbl.deleteCharAt(sbl.length() - 1);
                            requestAfterBuy(context, sbl.toString());
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        L.e(TAG, "购物车操作 -- 返回失败 code:" + code + " msg: " + msg);
                    }
                });

    }


    public static void logout(final Activity activity) {
        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        alibcLogin.logout(activity, new LogoutCallback() {
            @Override
            public void onSuccess() {
                //清除数据
                UserManager.saveLogin(activity, false);
                MainActivityV3.publishHomeTabEvent();
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(activity, "退出登录失败 " + code + msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void destory() {
        AlibcTradeSDK.destory();
    }

    //
    private static void requestAfterBuy(Context context, String orderId) {
        String url = UrlUtil.getUrl(context, R.string.url_buy);
        Map<String, Object> map = new HashMap<>();
        map.put("uid", UserManager.getUid(context));
        map.put("token", UserManager.getToken(context));
        map.put("tid", orderId);
        SqsHttpClientProxy.postAsynSQS(url, 1, map, new HttpCallback<JSONObject>() {
            @Override
            public void onSucceed(int requestId, JSONObject result) {
                L.e(TAG, "----订单数据上传成功----");
            }

            @Override
            public void onFail(int requestId, String errorMsg) {
                L.e(TAG, "----订单数据上传失败----");
            }
        });
    }
}
