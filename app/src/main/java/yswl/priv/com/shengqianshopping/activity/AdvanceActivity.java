package yswl.priv.com.shengqianshopping.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.base.MToolBarActivity;

/**
 *  预告
 */
public class AdvanceActivity extends MToolBarActivity {

    public static void startActivity(Activity context) {
        Intent intent = new Intent(context, AdvanceActivity.class);
        context.startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance);



    }


}
