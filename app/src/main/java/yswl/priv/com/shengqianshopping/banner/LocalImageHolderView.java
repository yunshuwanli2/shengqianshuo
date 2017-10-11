package yswl.priv.com.shengqianshopping.banner;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;

import yswl.priv.com.shengqianshopping.util.AlibcUtil;

/**
 * 本地图片加载
 * 是没有点击事件的
 */
public class LocalImageHolderView implements Holder<BannerBean> {
    private ImageView imageView;
    Activity mActivity;

    public LocalImageHolderView(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    /**
     * 加载本地图片
     *
     * @param context
     * @param position
     * @param data
     */
    @Override
    public void UpdateUI(final Context context, final int position, final BannerBean data) {
        if (data != null) {
            if (!data.imgUrl.startsWith("http")) {
                try {
                    //加载resId
                    Glide.with(context).load(Integer.valueOf(data.imgUrl)).into(imageView);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } else {
                Glide.with(context).load(data.imgUrl).into(imageView);
            }

            if (!TextUtils.isEmpty(data.link)) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //链接跳转
                        String linkUrl = data.link;
                        AlibcUtil.openBrower2(linkUrl, mActivity);

                    }
                });
            }
        }
    }
}
