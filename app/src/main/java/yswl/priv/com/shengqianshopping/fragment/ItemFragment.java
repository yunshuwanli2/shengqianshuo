package yswl.priv.com.shengqianshopping.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
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

        mFragments = DataGenerator.getRecyclerViewFragments(mCategory);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.content, mFragments[0])
                .commitAllowingStateLoss();
    }

    private MFragment[] mFragments;
    private int index = 0;
    @OnClick({R.id.tv_hot,R.id.tv_new,R.id.tv_price,R.id.tv_sell_count})
    public void onClick(View v) {
        int postion = 0;
        MFragment fragment = null;
        int id = v.getId();
        switch (id) {
            case R.id.tv_hot:
                fragment = mFragments[0];
                postion = 0;
                break;
            case R.id.tv_new:
                fragment = mFragments[1];
                postion = 1;
                break;
            case R.id.tv_sell_count:
                fragment = mFragments[2];
                postion = 2;
                break;
            case R.id.tv_price:
                fragment = mFragments[3];
                postion = 3;
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
