package yswl.priv.com.shengqianshopping.fragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import yswl.com.klibrary.base.MFragment;
import yswl.priv.com.shengqianshopping.bean.CategoryBean;
import yswl.priv.com.shengqianshopping.fragment.ItemFragment;
import yswl.priv.com.shengqianshopping.fragment.RebateFragment;


public class CommonFragmentPagerAdapter extends FragmentPagerAdapter {
    //    private final String TAG="CommonFragmentPagerAdapter";
    private List<MFragment> fragmentList;

    public CommonFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fragmentList = new ArrayList<>();
    }

    public void setFragmentList(List<CategoryBean> categorys) {
        for (CategoryBean catb : categorys) {
            this.fragmentList.add(ItemFragment.newInstance(catb));
        }
    }

    @Override
    public Fragment getItem(int arg0) {
//        Lx.d(TAG,"getItem "+arg0);
        if (arg0 < fragmentList.size() && arg0 >= 0) {
            return fragmentList.get(arg0);
        } else {
            return null;
        }

    }


    @Override
    public CharSequence getPageTitle(int position) {
        MFragment fragment = fragmentList.get(position);
        if (((ItemFragment) fragment).getmCategory() != null)
            return ((ItemFragment) fragment).getmCategory().title;
        return "";

    }

    @Override
    public int getCount() {
        int count = fragmentList != null ? fragmentList.size() : 0;
        return count;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return super.isViewFromObject(view, object);
    }
}
