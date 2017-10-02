package yswl.com.klibrary.imgloader.UniversalImageLoader;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by kangpAdministrator on 2017/5/2 0002.
 * Emial kangpeng@yunhetong.net
 */

public interface ImagerLoader {
    void init(Context context);
    void displayImage(String url, ImageView view, int default_pic, int fail_pic);
    void displayImage(String url, ImageView view);
}
