package yswl.com.klibrary.imgloader;

import android.content.Context;
import android.widget.ImageView;

import yswl.com.klibrary.imgloader.UniversalImageLoader.ImagerLoader;
import yswl.com.klibrary.imgloader.UniversalImageLoader.ImagerLoaderImpl;


/**
 * Created by kangpAdministrator on 2017/5/2 0002.
 * Emial kangpeng@yunhetong.net
 */

public class ImageLoaderProxy implements ImagerLoader {
    private ImagerLoader imagerLoader;
    private static ImageLoaderProxy proxy;

    private ImageLoaderProxy() {
        imagerLoader = new ImagerLoaderImpl();
    }

    public static ImageLoaderProxy getInstance() {
        if (null == proxy) {
            synchronized (ImageLoaderProxy.class) {
                if (null == proxy) {
                    proxy = new ImageLoaderProxy();
                }
            }
        }
        return proxy;
    }

    @Override
    public void init(Context context) {
        imagerLoader.init(context);
    }

    @Override
    public void displayImage(String url, ImageView view, int default_pic, int fail_pic) {
        imagerLoader.displayImage(url, view, default_pic, fail_pic);
    }

    @Override
    public void displayImage(String url, ImageView view) {
        imagerLoader.displayImage(url, view);
    }
}
