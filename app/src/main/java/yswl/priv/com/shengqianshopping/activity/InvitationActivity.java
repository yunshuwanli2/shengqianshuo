package yswl.priv.com.shengqianshopping.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.base.MToolBarActivity;

public class InvitationActivity extends MToolBarActivity {
    public static void startAct(Context context) {
        context.startActivity(new Intent(context, InvitationActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);
    }

}
