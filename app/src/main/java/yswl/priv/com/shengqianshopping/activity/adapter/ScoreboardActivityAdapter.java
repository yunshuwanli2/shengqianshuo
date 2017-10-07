package yswl.priv.com.shengqianshopping.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.zip.Inflater;

import butterknife.ButterKnife;
import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.bean.HeroeListBean;

/**
 * 积分榜适配器
 */

public class ScoreboardActivityAdapter extends RecyclerView.Adapter<ScoreboardActivityAdapter.MyViewHolder> {

    private Context context;
    private List<HeroeListBean> list;

    public ScoreboardActivityAdapter(Context context, List<HeroeListBean> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.scoreboard_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {



        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
