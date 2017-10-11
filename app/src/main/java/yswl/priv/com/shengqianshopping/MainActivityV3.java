package yswl.priv.com.shengqianshopping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;

import yswl.com.klibrary.MApplication;
import yswl.com.klibrary.base.MActivity;
import yswl.com.klibrary.base.MFragment;
import yswl.com.klibrary.manager.ActivityManager;
import yswl.priv.com.shengqianshopping.activity.BindPhoneActivity;
import yswl.priv.com.shengqianshopping.activity.LoginActivity;
import yswl.priv.com.shengqianshopping.base.MToolBarActivity;
import yswl.priv.com.shengqianshopping.event.ExitEvent;
import yswl.priv.com.shengqianshopping.event.UserInfoRequestEvent;
import yswl.priv.com.shengqianshopping.fragment.DataGenerator;
import yswl.priv.com.shengqianshopping.manager.UserManager;
import yswl.priv.com.shengqianshopping.util.AlibcUtil;


public class MainActivityV3 extends MActivity {

    public static void startAct(Context context) {
        Intent intent = new Intent(context, MainActivityV3.class);
        context.startActivity(intent);
    }

    public static void startActJumpAD(Context context, String url) {
        Intent intent = new Intent(context, MainActivityV3.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    BottomNavigationBar bottomNavigationBar;
    int lastSelectedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_v3);

        String url = getIntent().getStringExtra("url");
        if (!TextUtils.isEmpty(url)) {
            AlibcUtil.openBrower2(url, this);
        }


        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                lastSelectedPosition = position;
                onTabItemSelected(position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

        mFragments = DataGenerator.getFragments();
        bottomNavigationBar.setMode(1);
        bottomNavigationBar.setBackgroundStyle(0);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_navigation_home, "首页").setActiveColorResource(R.color.colorAccent)/*.setBadgeItem(numberBadgeItem)*/)
                .addItem(new BottomNavigationItem(R.mipmap.ic_navigation_pinkage, "包邮").setActiveColorResource(R.color.colorAccent))
                .addItem(new BottomNavigationItem(R.mipmap.ic_navigation_rebate, "返利").setActiveColorResource(R.color.colorAccent)/*.setBadgeItem(shapeBadgeItem)*/)
                .addItem(new BottomNavigationItem(R.mipmap.ic_navigation_my, "我的").setActiveColorResource(R.color.colorAccent))
                .setFirstSelectedPosition(lastSelectedPosition > 3 ? 3 : lastSelectedPosition)
                .initialise();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content, mFragments[0], mFragments[0].getClass().getSimpleName());
        ft.commit();

    }

    private MFragment[] mFragments;
    private int index = 0;

    private void onTabItemSelected(int postion) {
        if (postion == index) return;
        if (postion == 3) {
            if (!UserManager.isLogin(this)) {
                bottomNavigationBar.selectTab(index);
                LoginActivity.startActivity(this);
                return;
            } else if (!UserManager.isBindPhone(this)) {
                bottomNavigationBar.selectTab(index);
                BindPhoneActivity.startActivity(this);
                return;
            }
        }
        Fragment fragment = null;
        switch (postion) {
            case 0:
                fragment = mFragments[0];
                break;
            case 1:
                fragment = mFragments[1];
                break;
            case 2:
                fragment = mFragments[2];
                break;
            case 3:
                fragment = mFragments[3];
                break;
        }
        if (fragment == null) return;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        mFragments[index].onPause(); // 暂停当前tab
        if (fragment.isAdded()) {
            fragment.onResume(); // 启动目标tab的onResume()
        } else {
            ft.add(R.id.content, fragment);
        }

        ft.hide(mFragments[index]);
        ft.show(fragment); // 显示目标tab
        ft.commitAllowingStateLoss();
        index = postion;


    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true)
    public void onEvent(ExitEvent event) {
        EventBus.getDefault().removeStickyEvent(ExitEvent.class);
        gotoHomeTab();
    }

    public static void publishHomeTabEvent() {
        EventBus.getDefault().removeStickyEvent(ExitEvent.class);
        EventBus.getDefault().postSticky(new ExitEvent());
    }


    public void gotoHomeTab() {
        bottomNavigationBar.selectTab(0, true);
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    private Handler handler = new MyHandler(this);
    private long exitTime = 0;
    public static final int EXITWHAT = 10086;
    public static final int EixtDely = 1000;

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > EixtDely) {
            handler.sendEmptyMessage(EXITWHAT);
            exitTime = System.currentTimeMillis();
        } else {
            handler.removeMessages(EXITWHAT);
            MApplication.AppExit(this);
            AlibcUtil.destory();
        }
    }

    static class MyHandler extends Handler {
        WeakReference<Activity> weak = null;

        public MyHandler(Activity activity) {
            weak = new WeakReference<>(activity);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EXITWHAT:
                    Activity activity = weak.get();
                    if (activity != null) {
                        Toast.makeText(activity, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }

    }

}
