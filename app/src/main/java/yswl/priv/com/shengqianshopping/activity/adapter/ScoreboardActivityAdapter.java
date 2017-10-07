package yswl.priv.com.shengqianshopping.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import yswl.com.klibrary.imgloader.ImageLoaderProxy;
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
        if (!TextUtils.isEmpty(list.get(position).getAvatar())) {
            Glide.with(context).load(list.get(position).getAvatar()).into(holder.imgHead);
        }
        holder.tvNickname.setText(list.get(position).getNickname());
        holder.tvIntegral.setText(list.get(position).getIntegral());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.scoreboard_item_img_head)
        ImageView imgHead;
        @BindView(R.id.scoreboard_item_tv_nickname)
        TextView tvNickname;
        @BindView(R.id.scoreboard_item_tv_integral)
        TextView tvIntegral;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
