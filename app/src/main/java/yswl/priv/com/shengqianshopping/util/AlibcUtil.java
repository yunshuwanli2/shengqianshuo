package yswl.priv.com.shengqianshopping.util;

import android.app.Activity;
import android.content.Context;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyCartsPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyOrdersPage;

import java.util.HashMap;
import java.util.Map;

import yswl.com.klibrary.util.L;
import yswl.priv.com.shengqianshopping.bean.ProductDetail;

/**
 * Created by kangpAdministrator on 2017/10/9 0009.
 * Emial kangpeng@yunhetong.net
 */

public class AlibcUtil {

    private static final String TAG = AlibcUtil.class.getSimpleName();

    //打开详情
    public static void openAlibcPage(Activity context, ProductDetail detail) {
        Map<String, String> exParams = new HashMap<>();
        exParams.put(AlibcConstants.ISV_CODE, "saveduoduo");

        //商品详情page
        AlibcBasePage detailPage = new AlibcDetailPage(detail.iid);
        //设置页面打开方式
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

}