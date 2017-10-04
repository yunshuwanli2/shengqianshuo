package yswl.priv.com.shengqianshopping.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.base.MToolBarActivity;
import yswl.priv.com.shengqianshopping.fragment.GridRecyclerviewFragment2;

public class Top100Activity extends MToolBarActivity {

    public static void startActivity(Activity context) {
        Intent intent = new Intent(context, Top100Activity.class);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top100);
        setTitle("实时疯抢榜TOP100");

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, GridRecyclerviewFragment2.newInstance()).commit();

    }
}
