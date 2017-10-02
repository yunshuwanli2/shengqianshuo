package yswl.com.klibrary.imgloader.UniversalImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import yswl.com.klibrary.R;

/**
 * Created by kangpAdministrator on 2017/5/2 0002.
 * Emial kangpeng@yunhetong.net
 */

public class ImagerLoaderImpl implements ImagerLoader {

    @Override
    public void init(Context context) {
        final ImageLoaderConfiguration.Builder configBuild = new ImageLoaderConfiguration.Builder(context);
        configBuild.imageDownloader(new AuthImageDownloader(context));//https
        configBuild.threadPriority(Thread.NORM_PRIORITY - 2);
        configBuild.denyCacheImageMultipleSizesInMemory();
        configBuild.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        configBuild.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        configBuild.tasksProcessingOrder(QueueProcessingType.LIFO);

//		configBuild.writeDebugLogs(); // Remove for release app
        // configBuild.memoryCacheExtraOptions(maxImageWidthForMemoryCache,
        // maxImageHeightForMemoryCache)
        // Initialize ImageLoader with configuration. init ImageLoaderEngine
        //
        Thread initImageLoadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ImageLoader.getInstance().init(configBuild.build());
            }
        });
        initImageLoadThread.setName("initImageLoadThread");
        initImageLoadThread.start();
    }

    @Override
    public void displayImage(String uri, ImageView view, int default_pic, int fail_pic) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(default_pic)
                .showImageForEmptyUri(default_pic).showImageOnFail(default_pic)
                .cacheInMemory(true).cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer()).build();
        ImageLoader.getInstance().displayImage(uri, view, options);
    }

    @Override
    public void displayImage(String url, ImageView view) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .cacheInMemory(true).cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer()).build();
        ImageLoader.getInstance().displayImage(url, view, options);
    }
}
