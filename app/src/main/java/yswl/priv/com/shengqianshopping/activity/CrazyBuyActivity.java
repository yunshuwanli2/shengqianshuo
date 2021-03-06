package yswl.priv.com.shengqianshopping.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import yswl.com.klibrary.base.MFragment;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.base.MToolBarActivity;
import yswl.priv.com.shengqianshopping.bean.TimeBean;
import yswl.priv.com.shengqianshopping.fragment.ListRecyclerviewFragment;
import yswl.priv.com.shengqianshopping.util.DateUtil;

/**
 * 疯狂抢购
 */
public class CrazyBuyActivity extends MToolBarActivity implements View.OnClickListener {

    public static void startActivity(Activity context) {
        Intent intent = new Intent(context, CrazyBuyActivity.class);
        context.startActivity(intent);
    }

    TextView mYes, mNine, mThirteen, mSevenTeen, mTomorr;
    TextView views[];
    TimeBean times[] = new TimeBean[]{TimeBean.getYTes(), TimeBean.getNine(), TimeBean.getThi(), TimeBean.getSev(), TimeBean.getTomorrow()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crazy_buy);
        setTitle("疯狂抢");

        initUI();
    }


    private void initUI() {
        mYes = findView(R.id.tv_yesterday);
        mNine = findView(R.id.tv_nine);
        mThirteen = findView(R.id.tv_thirteen);
        mSevenTeen = findView(R.id.tv_sevenTeen);
        mTomorr = findView(R.id.tv_tomorrow);
        mYes.setOnClickListener(this);
        mNine.setOnClickListener(this);
        mThirteen.setOnClickListener(this);
        mSevenTeen.setOnClickListener(this);
        mTomorr.setOnClickListener(this);
        views = new TextView[]{mYes, mNine, mThirteen, mSevenTeen, mTomorr};

        mFragments = new MFragment[5];
        mFragments[0] = ListRecyclerviewFragment.newInstance(times[0]);
        mFragments[1] = ListRecyclerviewFragment.newInstance(times[1]);
        mFragments[2] = ListRecyclerviewFragment.newInstance(times[2]);
        mFragments[3] = ListRecyclerviewFragment.newInstance(times[3]);
        mFragments[4] = ListRecyclerviewFragment.newInstance(times[4]);
        setTimeFrame();
        views[0].performClick();
    }

    private void setTimeFrame() {
        mYes.setText("昨日");
        mNine.setText("09:00 \n" + getShowTime(times[1].startTime, times[2].startTime));
        mThirteen.setText("13:00 \n" + getShowTime(times[2].startTime, times[3].startTime));
        mSevenTeen.setText("17:00 \n" + getShowTime(times[3].startTime, times[4].startTime));
        mTomorr.setText("预告");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    String getShowTime(String time, String nextTime) {
        long compareTime = DateUtil.stringToTimeStamp(time);
        long compareNextTime = DateUtil.stringToTimeStamp(nextTime);
        long currentTime = System.currentTimeMillis();
        if (currentTime < compareTime) {
            return "即将开始";
        } else if (compareTime < currentTime && currentTime < compareNextTime) {
            return "正在抢购";
        } else {
            return "已抢购";
        }
    }


    @Override
    public void onClick(View v) {
        int position = 0;
        switch (v.getId()) {
            case R.id.tv_yesterday:
                position = 0;
                break;
            case R.id.tv_nine:
                position = 1;
                break;
            case R.id.tv_thirteen:
                position = 2;
                break;
            case R.id.tv_sevenTeen:
                position = 3;
                break;
            case R.id.tv_tomorrow:
                position = 4;
                break;
        }
        setBottomState(position);
        onTabItemSelected(position);
    }

    public void setBottomState(int position) {
        for (int i = 0; i < views.length; i++) {
            if (i == position) {
                views[position].setSelected(true);
            } else {
                views[i].setSelected(false);
            }
        }
    }

    private MFragment[] mFragments;
    private int index = -1;

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
            case 4:
                fragment = mFragments[4];
                break;
        }
        if (fragment == null) return;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (index != -1) {
            mFragments[index].onPause(); // 暂停当前tab
        }
        if (fragment.isAdded()) {
            fragment.onResume(); // 启动目标tab的onResume()
        } else {
            ft.add(R.id.content, fragment);
        }
        if (index != -1) {
            ft.hide(mFragments[index]);
        }
        ft.show(fragment); // 显示目标tab
        ft.commitAllowingStateLoss();
        index = postion;
    }

}
