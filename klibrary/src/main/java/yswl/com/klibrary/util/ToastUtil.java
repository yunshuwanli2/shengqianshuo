package yswl.com.klibrary.util;

import android.widget.Toast;

import yswl.com.klibrary.MApplication;


public class ToastUtil {
    private static Toast mToast = null;

    private static long lastClickTime;

    // 防止连续点击按钮
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1900) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    static {
        mToast = Toast.makeText(MApplication.getApplication(), "", Toast.LENGTH_SHORT);
    }

    public static void showToast(String str) {
        try {
            mToast.setText(str);
            mToast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showToast(int resId) {
        try {
            mToast.setText(resId);
            mToast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
