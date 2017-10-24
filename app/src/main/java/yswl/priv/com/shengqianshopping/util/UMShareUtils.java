package yswl.priv.com.shengqianshopping.util;

import android.app.Activity;
import android.content.Context;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import yswl.com.klibrary.util.ToastUtil;
import yswl.priv.com.shengqianshopping.R;

/**
 * 友盟分享
 */

public class UMShareUtils {

    //分享
    public static void share(final Activity activity, String url, String title, String description) {
        UMImage thumb = new UMImage(activity, R.mipmap.app_icon);
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription(description);//描述
        new ShareAction(activity)
                .withMedia(web)
                .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        ToastUtil.showToast("分享成功");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        ToastUtil.showToast("分享失败");
                        if (SHARE_MEDIA.WEIXIN == share_media || SHARE_MEDIA.WEIXIN_CIRCLE == share_media) {
                            if (!UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.WEIXIN)) {
                                ToastUtil.showToast("未安装微信应用");
                            }
                        }
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                    }
                })
                .open();
    }
}
