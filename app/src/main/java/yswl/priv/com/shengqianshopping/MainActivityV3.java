package yswl.priv.com.shengqianshopping;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import yswl.com.klibrary.base.MFragment;
import yswl.priv.com.shengqianshopping.base.MToolBarActivity;
import yswl.priv.com.shengqianshopping.fragment.DataGenerator;


public class MainActivityV3 extends MToolBarActivity {

    BottomNavigationBar bottomNavigationBar;
    int lastSelectedPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_v3);

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
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher_round, "首页").setActiveColorResource(R.color.colorAccent)/*.setBadgeItem(numberBadgeItem)*/)
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher_round, "包邮").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher_round, "返利").setActiveColorResource(R.color.colorPrimaryDark)/*.setBadgeItem(shapeBadgeItem)*/)
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher_round, "我的").setActiveColorResource(R.color.colorPrimary))
                .setFirstSelectedPosition(lastSelectedPosition > 3 ? 3 : lastSelectedPosition)
                .initialise();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content, mFragments[0], mFragments[0].getClass().getSimpleName());
        ft.commit();

    }

    private MFragment[] mFragments;
    private int index = 0;
    private void onTabItemSelected(int postion) {
        if (index == postion) return;
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

}
