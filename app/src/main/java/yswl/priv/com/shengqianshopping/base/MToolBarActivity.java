package yswl.priv.com.shengqianshopping.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;


import yswl.com.klibrary.base.MActivity;
import yswl.priv.com.shengqianshopping.R;

public class MToolBarActivity extends MActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        Toolbar toolbar = findView(R.id.base_toolbar);

        setSupportActionBar(toolbar);

        setHomeAsUpShowAndEnabled(true);
        toolbar.setNavigationOnClickListener(this);

    }

    void setHomeAsUpShowAndEnabled(boolean boo){
        getSupportActionBar().setHomeButtonEnabled(boo); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(boo);
    }

    @Override
    public void onClick(View v) {
        navigationBack();
    }

    void navigationBack(){
        this.onBackPressed();
    }
}
