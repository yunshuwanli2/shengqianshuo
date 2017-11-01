package yswl.priv.com.shengqianshopping.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yswl.com.klibrary.base.MFragment;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.bean.CategoryBean;
import yswl.priv.com.shengqianshopping.view.SelectionSortView;


public class ItemFragment extends MFragment implements View.OnClickListener {

    private static final int REQUEST_ID = 1003;

    private static final String ARG_PARAM1 = "param1";

    private int currentPosition = 0;


    public CategoryBean getmCategory() {
        return mCategory;
    }


    private CategoryBean mCategory;

    public ItemFragment() {
    }

    public static ItemFragment newInstance(CategoryBean param1) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCategory = (CategoryBean) savedInstanceState.getSerializable(ARG_PARAM1);
        }
        if (mCategory == null && getArguments() != null) {
            mCategory = (CategoryBean) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            outState.putSerializable(ARG_PARAM1, mCategory);
        }

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null)
            mCategory = (CategoryBean) savedInstanceState.getSerializable(ARG_PARAM1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item, container, false);
    }

    @BindView(R.id.sort_hot)
    SelectionSortView sortHot;
    @BindView(R.id.sort_new)
    SelectionSortView sortNew;
    @BindView(R.id.sort_sell_count)
    SelectionSortView sortSellCount;
    @BindView(R.id.sort_price)
    SelectionSortView sortPrice;
    private ImageView lastImg;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        sortHot.setContent("人气");
        sortHot.setPosition(0);
        sortNew.setContent("最新");
        sortNew.setPosition(1);
        sortSellCount.setContent("销量");
        sortSellCount.setPosition(2);
        sortPrice.setContent("价格");
        sortPrice.setPosition(3);
        mFragments = DataGenerator.getRecyclerViewFragments(mCategory);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.content, mFragments[0])
                .commitAllowingStateLoss();
        sortHot.setDefault();
        if (lastImg != null) {
            lastImg.setVisibility(View.INVISIBLE);
        }
        sortNew.hideImg();
        sortSellCount.hideImg();
        sortPrice.hideImg();
        lastImg = sortHot.getImgStatus();
        currentPosition = 0;
    }

    private MFragment[] mFragments;
    private int index = 0;

    @OnClick({R.id.sort_hot, R.id.sort_new, R.id.sort_price, R.id.sort_sell_count})
    public void onClick(View v) {
        int postion = 0;
        MFragment fragment = null;
        int id = v.getId();
        switch (id) {
            case R.id.sort_hot:
                fragment = mFragments[0];
                postion = 0;
                if (lastImg != null) {
                    lastImg.setVisibility(View.INVISIBLE);
                }
                lastImg = sortHot.getImgStatus();
                sortHot.onClick(currentPosition);
                currentPosition = 0;
                ((GridRecyclerviewFragment) fragment).setAsc(sortHot.isSortAsc());
                ((GridRecyclerviewFragment) fragment).requestData();
                break;
            case R.id.sort_new:
                fragment = mFragments[1];
                postion = 1;
                if (lastImg != null) {
                    lastImg.setVisibility(View.INVISIBLE);
                }
                lastImg = sortNew.getImgStatus();
                sortNew.onClick(currentPosition);
                currentPosition = 1;
                ((GridRecyclerviewFragment) fragment).setAsc(sortNew.isSortAsc());
                ((GridRecyclerviewFragment) fragment).requestData();
                break;
            case R.id.sort_sell_count:
                fragment = mFragments[2];
                postion = 2;
                if (lastImg != null) {
                    lastImg.setVisibility(View.INVISIBLE);
                }
                lastImg = sortSellCount.getImgStatus();
                sortSellCount.onClick(currentPosition);
                currentPosition = 2;
                ((GridRecyclerviewFragment) fragment).setAsc(sortSellCount.isSortAsc());
                ((GridRecyclerviewFragment) fragment).requestData();
                break;
            case R.id.sort_price:
                fragment = mFragments[3];
                postion = 3;
                if (lastImg != null) {
                    lastImg.setVisibility(View.INVISIBLE);
                }
                lastImg = sortPrice.getImgStatus();
                sortPrice.onClick(currentPosition);
                currentPosition = 3;
                ((GridRecyclerviewFragment) fragment).setAsc(sortPrice.isSortAsc());
                ((GridRecyclerviewFragment) fragment).requestData();
                break;
        }
        if (fragment == null) return;
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
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
