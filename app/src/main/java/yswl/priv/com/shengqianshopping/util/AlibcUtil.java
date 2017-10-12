package yswl.priv.com.shengqianshopping.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.ali.auth.third.login.callback.LogoutCallback;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyCartsPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyOrdersPage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;

import java.util.HashMap;
import java.util.Map;

import yswl.com.klibrary.browser.BrowserActivity;
import yswl.com.klibrary.util.L;
import yswl.priv.com.shengqianshopping.App;
import yswl.priv.com.shengqianshopping.MainActivityV3;
import yswl.priv.com.shengqianshopping.activity.BindPhoneActivity;
import yswl.priv.com.shengqianshopping.activity.LoginActivity;
import yswl.priv.com.shengqianshopping.activity.SettingActivity;
import yswl.priv.com.shengqianshopping.bean.CrazyProductDetail;
import yswl.priv.com.shengqianshopping.bean.ProductDetail;
import yswl.priv.com.shengqianshopping.fragment.UserCenterFragment;
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
//                AlibcTradeSDK.setForceH5(true);
            }

            @Override
            public void onFailure(int code, String msg) {
                //初始化失败，可以根据code和msg判断失败原因，详情参见错误说明
                Toast.makeText(context, "TaeSDK 初始化失败 -- " + msg + "  " + code, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //打开优惠券/或其他淘宝地址
    public static void openBrower2(String url, Activity context) {
        if (!UserManager.isLogin(context)) {
            LoginActivity.startActivity(context);
            return;
        } else if (!UserManager.isBindPhone(context)) {
            BindPhoneActivity.startActivity(context);
            return;
        }
        Map<String, String> exParams = new HashMap<>();
        exParams.put(AlibcConstants.ISV_CODE, "saveduoduo");

        //商品详情page
        AlibcPage detailPage = new AlibcPage(url);
        AlibcShowParams showParams = new AlibcShowParams(OpenType.Native, true);
        AlibcTrade.show(context, detailPage, showParams, null, exParams, new AlibcTradeCallback() {
            @Override
            public void onTradeSuccess(TradeResult tradeResult) {
                L.e(TAG, tradeResult.resultType.name());
            }

            @Override
            public void onFailure(int code, String msg) {
            }
        });
    }

    //打开优惠券
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

    //打开商品详情
    public static void openAlibcPage(Activity context, ProductDetail detail) {
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
            }

            @Override
            public void onFailure(int code, String msg) {
                //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
            }
        });
    }

    //打开商品详情详情
    public static void openAlibcPage(Activity context, CrazyProductDetail detail) {
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
            }

            @Override
            public void onFailure(int code, String msg) {
                //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
            }
        });
    }

    public static void gotoAliOrder(Activity context) {
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
                        L.e(TAG, "订单操作 -- 返回成功 result : " + tradeResult.resultType.name());
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        L.e(TAG, "订单操作 -- 返回失败 code:" + code + " msg: " + msg);
                    }
                });

    }

    public static void gotoAliShoppingChe(Activity context) {
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
                        L.e(TAG, "购物车操作 -- 返回成功 result : " + tradeResult.resultType.name());
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
                SharedPreUtils.getInstance(activity).clearAllData();
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
}
