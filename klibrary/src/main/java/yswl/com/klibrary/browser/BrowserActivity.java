package yswl.com.klibrary.browser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import yswl.com.klibrary.R;
import yswl.com.klibrary.base.MActivity;

/**
 * 内置浏览器
 */
public class BrowserActivity extends MActivity {
    private static final String TAG = "BrowserActivity";
    private static final String URL = "url";
    private static final String TITTLE = "title";
    private WebViewExtraProWrap mWebViewExtra;

    public static void start(String title, String url, Activity context) {
        Intent intent = new Intent(context, BrowserActivity.class);
        intent.putExtra(URL, url);
        intent.putExtra(TITTLE, title);
        context.startActivity(intent);
        context.overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

    public static void start2(String title, String url, Context context) {
        Intent intent = new Intent(context, BrowserActivity.class);
        intent.putExtra(URL, url);
        intent.putExtra(TITTLE, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.browser);
        mWebViewExtra = (WebViewExtraProWrap) findViewById(R.id.ly_browser);
        processIntent(getIntent());
    }


    public void processIntent(Intent intent) {
        if (intent == null)
            return;
        intent.getStringExtra(URL);
        String url = intent.getStringExtra(URL);
        mWebViewExtra.loadUrl(this, url);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        processIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        ActionBarUtil.add(menu, this, 0, Menu.FIRST, 0, R.string.refresh);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Menu.FIRST:
                mWebViewExtra.clearCache(true);
                mWebViewExtra.reload(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
