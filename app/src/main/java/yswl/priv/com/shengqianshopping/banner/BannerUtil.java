package yswl.priv.com.shengqianshopping.banner;

import android.app.Activity;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import yswl.com.klibrary.MApplication;
import yswl.com.klibrary.http.okhttp.MScreenUtils;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.bean.BannerBean;

/**
 * author  : kangpeng on 2016/4/8 0008.
 * email   : kangpeng@yunhetong.net
 */
public class BannerUtil {

    private static final String TAG = "BannerUtil";
    Activity mActivity;
    private ConvenientBanner<BannerBean> convenientBanner;//轮播图

    public BannerUtil(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void setConvenientBanner(ConvenientBanner<BannerBean> convenientBanner) {
        this.convenientBanner = convenientBanner;
    }

    public void loadPic(List<BannerBean> imgs) {
        if (imgs == null || imgs.size() == 0) {
            setDefulBanner(imgs);
        } else {
            setLocalPic(imgs);
        }
    }

    /**
     * 初始化轮播图
     */
    private void setDefulBanner(List<BannerBean> imgs) {
        imgs = new ArrayList<>();
        imgs.add(new BannerBean("" + R.mipmap.bg_tool));
        imgs.add(new BannerBean("" + R.mipmap.bg_tool));
        imgs.add(new BannerBean("" + R.mipmap.bg_tool));
        setLocalPic(imgs);
    }

    private void setLocalPic(List<BannerBean> localImages) {
        convenientBanner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView(mActivity);
            }
        }, localImages);
        convenientBanner.setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});
    }

    /**
     * 在act 屏幕唤醒时 onResume运行
     */
    public void startTurning() {
        if (null != convenientBanner)
            convenientBanner.startTurning(5000);
    }

    /**
     * act 不属于前台进程时 停止轮播
     * onPause
     */
    public void stopTurning() {
        if (null != convenientBanner)
            convenientBanner.stopTurning();
    }


    public static int computeHomeBannerSize() {
        int h = BigDecimal.valueOf(0.4444).multiply(BigDecimal.valueOf(MScreenUtils.getScreenWidth(MApplication.getApplication()))).intValue();
        return h;
    }

    public static int computeSetMealBannerSize() {
        int h = BigDecimal.valueOf(0.237).multiply(BigDecimal.valueOf(MScreenUtils.getScreenWidth(MApplication.getApplication()))).intValue();
        return h;
    }
}
