package yswl.priv.com.shengqianshopping.util;

import android.os.Handler;
import android.text.Html;
import android.widget.Button;

/**
 * 计时器
 */

public class TimerUtil {


    // 定时器参数
    private static Button mBtn;
    private static int mTime;
    private static Handler handler = new Handler();
    private static boolean isSending = false;


    /**
     * 验证码定时器
     *
     * @param btn  目标按钮
     * @param time 计时总时间(s)
     */
    public static void verifyCodeTimer(Button btn, int time, boolean ispress) {
        mBtn = btn;
        if (isSending) {
            mBtn.setClickable(false);
        } else {
        }
        // 根据是否在发送开启定时器
        if (ispress) {
            mTime = time;
            handler.postDelayed(runnable, 1000);
        } else {
            if (!isSending) {
                handler.removeCallbacks(runnable);
            }
        }
    }

    private static Runnable runnable = new Runnable() {

        @Override
        public void run() {
            if (mBtn != null) {
                mBtn.setText(Html.fromHtml("<font color='#089aff'>" + mTime
                        + " 秒" + "</font>"));
                mBtn.setClickable(false);
                isSending = true;
                --mTime;
                if (mTime < -1) {
                    // 移除定时器
                    handler.removeCallbacks(runnable);
                    mBtn.setText("重新获取");
                    isSending = false;
                    mBtn.setClickable(true);
                } else {
                    handler.postDelayed(runnable, 1000);
                }
            }
        }
    };

    /**
     * 移除定时器
     */
    public static void RemoveRunnable() {
        isSending = false;
        handler.removeCallbacks(runnable);
    }

}
