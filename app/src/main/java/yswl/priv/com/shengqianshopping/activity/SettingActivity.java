package yswl.priv.com.shengqianshopping.activity;

import android.os.Bundle;

import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.base.MToolBarActivity;

//设置
public class SettingActivity extends MToolBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("设置");
    }
}
