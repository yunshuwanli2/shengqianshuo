package yswl.com.klibrary.browser;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import yswl.com.klibrary.R;
import yswl.com.klibrary.http.okhttp.MScreenUtils;


/**
 * 进度条
 */
public class WebViewProgressBar extends View {
    public static final String TAG = "WebViewProgressBar";
    public int mScreenW = -1;
    public int mNewDrawWidth = 0;
    public Paint mPaint = new Paint();

    public WebViewProgressBar(Context context) {
        super(context);
        init();
    }

    public WebViewProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WebViewProgressBar(Context context, AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    // public WebViewProgressBar(Context context, AttributeSet attrs,
    // int defStyleAttr, int defStyleRes) {
    // super(context, attrs, defStyleAttr, defStyleRes);
    // init();
    // }

    public void setProgress(int newProgress) {
        if (newProgress == 100) {
            this.setVisibility(View.GONE);
        } else {
            if (this.getVisibility() != View.VISIBLE) {
                this.setVisibility(View.VISIBLE);
            }
        }
        if (mScreenW == -1) {
            init();
        }
        mNewDrawWidth = newProgress * mScreenW / 100;
        mPaint.setColor(getResources().getColor(android.R.color.holo_blue_dark));
        this.invalidate();
    }

    private void init() {
        mScreenW = MScreenUtils.getScreenWidth(getContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, mNewDrawWidth, 5, mPaint);
    }
}
