package yswl.priv.com.shengqianshopping.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yswl.com.klibrary.base.MFragment;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.banner.SortEnum;
import yswl.priv.com.shengqianshopping.bean.CategoryBean;
import yswl.priv.com.shengqianshopping.bean.SerializableMap;

public class DataGenerator {

    public static final int[] mTabRes = new int[]{R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round};
    public static final int[] mTabResPressed = new int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    public static final String[] mTabTitle = new String[]{"首页", "借款", "认证", "我的"};

    public static MFragment[] getFragments() {
        MFragment fragments[] = new MFragment[4];
        fragments[0] = new HomeFragment2();
        fragments[2] = new RebateFragment();
        fragments[1] = new PinkageFragment();
        fragments[3] = new UserInfoFragment2();
        return fragments;
    }

    public static List<MFragment> getFragments2(List<CategoryBean> categorys) {
        List<MFragment> fragments = new ArrayList<>();
        for (CategoryBean categroybean : categorys) {
            fragments.add(ItemFragment.newInstance(categroybean));
        }

        return fragments;
    }

    public static MFragment[] getRecyclerViewFragments(CategoryBean category) {
        MFragment fragments[] = new MFragment[4];
        Map<String, Object> param = new HashMap<>();
        param.put("pid", category.pid);
        param.put("sort", SortEnum.HOT.getValue());
        GridRecyclerviewFragment fragment = GridRecyclerviewFragment.newInstance(new SerializableMap(param));

        Map<String, Object> param2 = new HashMap<>();
        param2.put("pid", category.pid);
        param2.put("sort", SortEnum.NEW.getValue());
        GridRecyclerviewFragment fragment2 = GridRecyclerviewFragment.newInstance(new SerializableMap(param2));

        Map<String, Object> param3 = new HashMap<>();
        param3.put("pid", category.pid);
        param3.put("sort", SortEnum.VOLUME.getValue());
        GridRecyclerviewFragment fragment3 = GridRecyclerviewFragment.newInstance(new SerializableMap(param3));

        Map<String, Object> param4 = new HashMap<>();
        param4.put("pid", category.pid);
        param4.put("sort", SortEnum.PRICE.getValue());
        GridRecyclerviewFragment fragment4 = GridRecyclerviewFragment.newInstance(new SerializableMap(param4));
        fragments[0] = fragment;
        fragments[1] = fragment2;
        fragments[2] = fragment3;
        fragments[3] = fragment4;
        return fragments;
    }


}