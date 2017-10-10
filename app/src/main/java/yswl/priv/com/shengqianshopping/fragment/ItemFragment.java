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
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yswl.com.klibrary.base.MFragment;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.bean.CategoryBean;


public class ItemFragment extends MFragment implements View.OnClickListener {

    private static final int REQUEST_ID = 1003;

    private static final String ARG_PARAM1 = "param1";


    private boolean hotAsc = false;//升序:asc 降序:desc-默认--人气
    private boolean newAsc = false;//升序:asc 降序:desc-默认--最新
    private boolean volumeAsc = false;//升序:asc 降序:desc-默认--销量
    private boolean priceAsc = false;//升序:asc 降序:desc-默认--价格

    private Drawable drawableAsc;
    private Drawable drawableDesc;
    private int currentPosition = 0;
    private TextView lastTv;


    public CategoryBean getmCategory() {
        return mCategory;
    }

    public void setmCategory(CategoryBean mCategory) {
        this.mCategory = mCategory;
    }

    private CategoryBean mCategory;


    public ItemFragment() {

    }

    @SuppressLint("ValidFragment")
    public ItemFragment(CategoryBean param1) {
        this.mCategory = param1;
    }

    public static ItemFragment newInstance2(CategoryBean param1) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public static ItemFragment newInstance(CategoryBean param1) {
        ItemFragment fragment = new ItemFragment(param1);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategory = (CategoryBean) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item, container, false);
    }

    @BindView(R.id.tv_hot)
    TextView hotProduct;
    @BindView(R.id.tv_new)
    TextView newProduct;
    @BindView(R.id.tv_price)
    TextView priceProduct;
    @BindView(R.id.tv_sell_count)
    TextView sellCountProduct;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        drawableAsc = ContextCompat.getDrawable(getActivity(), R.mipmap.icon_ace);
        drawableDesc = ContextCompat.getDrawable(getActivity(), R.mipmap.icon_desc);
        drawableAsc.setBounds(0, 0, drawableAsc.getMinimumWidth(), drawableAsc.getMinimumHeight());//对图片进行压缩
        drawableDesc.setBounds(0, 0, drawableDesc.getMinimumWidth(), drawableDesc.getMinimumHeight());//对图片进行压缩

        mFragments = DataGenerator.getRecyclerViewFragments(mCategory);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.content, mFragments[0])
                .commitAllowingStateLoss();
        currentPosition = 0;
        hotProduct.setCompoundDrawables(null, null, drawableDesc, null);
        lastTv = hotProduct;
    }

    private MFragment[] mFragments;
    private int index = 0;

    @OnClick({R.id.tv_hot, R.id.tv_new, R.id.tv_price, R.id.tv_sell_count})
    public void onClick(View v) {
        int postion = 0;
        MFragment fragment = null;
        int id = v.getId();
        switch (id) {
            case R.id.tv_hot:
                fragment = mFragments[0];
                postion = 0;
                if (currentPosition == 0) {
                    hotAsc = !hotAsc;
                    ((GridRecyclerviewFragment) fragment).setAsc(hotAsc);
                } else {
                    currentPosition = 0;
                    if (lastTv != null) {
                        lastTv.setCompoundDrawables(null, null, null, null);
                    }
                    lastTv = hotProduct;
                }
                if (hotAsc) {
                    hotProduct.setCompoundDrawables(null, null, drawableAsc, null);
                } else {
                    hotProduct.setCompoundDrawables(null, null, drawableDesc, null);
                }
                ((GridRecyclerviewFragment) fragment).requestData();
                break;
            case R.id.tv_new:
                fragment = mFragments[1];
                postion = 1;
                if (currentPosition == 1) {
                    newAsc = !newAsc;
                    ((GridRecyclerviewFragment) fragment).setAsc(newAsc);
                } else {
                    currentPosition = 1;
                    if (lastTv != null) {
                        lastTv.setCompoundDrawables(null, null, null, null);
                    }
                    lastTv = newProduct;
                }
                if (newAsc) {
                    newProduct.setCompoundDrawables(null, null, drawableAsc, null);
                } else {
                    newProduct.setCompoundDrawables(null, null, drawableDesc, null);
                }
                ((GridRecyclerviewFragment) fragment).requestData();
                break;
            case R.id.tv_sell_count:
                fragment = mFragments[2];
                postion = 2;
                if (currentPosition == 2) {
                    volumeAsc = !volumeAsc;
                    ((GridRecyclerviewFragment) fragment).setAsc(volumeAsc);
                } else {
                    currentPosition = 2;
                    if (lastTv != null) {
                        lastTv.setCompoundDrawables(null, null, null, null);
                    }
                    lastTv = sellCountProduct;
                }
                if (volumeAsc) {
                    sellCountProduct.setCompoundDrawables(null, null, drawableAsc, null);
                } else {
                    sellCountProduct.setCompoundDrawables(null, null, drawableDesc, null);
                }
                ((GridRecyclerviewFragment) fragment).requestData();
                break;
            case R.id.tv_price:
                fragment = mFragments[3];
                postion = 3;
                if (currentPosition == 3) {
                    priceAsc = !priceAsc;
                    ((GridRecyclerviewFragment) fragment).setAsc(priceAsc);
                } else {
                    currentPosition = 3;
                    if (lastTv != null) {
                        lastTv.setCompoundDrawables(null, null, null, null);
                    }
                    lastTv = priceProduct;
                }
                if (priceAsc) {
                    priceProduct.setCompoundDrawables(null, null, drawableAsc, null);
                } else {
                    priceProduct.setCompoundDrawables(null, null, drawableDesc, null);
                }
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
