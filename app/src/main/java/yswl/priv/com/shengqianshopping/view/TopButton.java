package yswl.priv.com.shengqianshopping.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 返回顶部按钮
 */

public class TopButton extends android.support.v7.widget.AppCompatButton implements View.OnClickListener {

    RecyclerView myRecyclerView;

    public TopButton(Context context) {
        super(context);
    }

    public TopButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TopButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void ToTop(RecyclerView recyclerView) {
        myRecyclerView = recyclerView;
        this.setOnClickListener(this);
        myRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //行数
                int count = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                if (count > 21) {
                    TopButton.this.setVisibility(View.VISIBLE);
                } else {
                    TopButton.this.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        myRecyclerView.scrollToPosition(0);
    }
}
