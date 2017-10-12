package yswl.priv.com.shengqianshopping.fragment.adapter;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.bean.CrazyProductDetail;
import yswl.priv.com.shengqianshopping.bean.ProductDetail;
import yswl.priv.com.shengqianshopping.util.DateUtil;

/**
 * Created by yunshuwanli on 17/10/1.
 */

public class ListRecyclerFragmentAdapter extends RecyclerView.Adapter<ListRecyclerFragmentAdapter.GridRecyHolder> {

    List<CrazyProductDetail> mProductList;


    boolean mShowShade;

    public List<CrazyProductDetail> getmProductList() {
        return mProductList;
    }

    public void setmProductList(List<CrazyProductDetail> mProductList) {
        this.mProductList = mProductList;
    }

    public void setmShowShade(boolean mShowShade) {
        this.mShowShade = mShowShade;
    }

    private GridRecyclerFragmentAdapter.OnItemClickListener onItemClickListener;

    public GridRecyclerFragmentAdapter.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(GridRecyclerFragmentAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ListRecyclerFragmentAdapter() {
    }

    RecyclerView recyclerView;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    @Override
    public void onViewDetachedFromWindow(GridRecyHolder holder) {
        super.onViewDetachedFromWindow(holder);

    }

    @Override
    public GridRecyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recyclerview_list_item_view, parent, false);
        return new GridRecyHolder(view);
    }

    @Override
    public void onBindViewHolder(GridRecyHolder holder, int position) {
        CrazyProductDetail detail = mProductList.get(position);
        Glide.with(holder.itemView.getContext()).load(detail.pictUrl).into(holder.preview_img);
        holder.produce_buy_count.setText("已抢购:" + detail.soldNum);
        holder.product_desc.setText(detail.title);
        holder.product_price.setText("¥:" + detail.zkFinalPrice);
        holder.product_old_price.setText(detail.reservePrice);

        holder.time_start.setText("开始时间 " + detail.startTime);
        holder.time_end.setText("结束时间 " + detail.endTime);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null && v != null && recyclerView != null) {
                    int position = recyclerView.getChildAdapterPosition(v);
                    onItemClickListener.onItemClick(recyclerView, v, position);
                }
            }
        });
        if (mShowShade) {
            holder.showShade.setVisibility(View.VISIBLE);
        } else {
            holder.showShade.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return (mProductList != null && mProductList.size() > 0) ? mProductList.size() : 0;
    }


    class GridRecyHolder extends RecyclerView.ViewHolder {
        final ImageView preview_img;
        final TextView product_old_price;
        final TextView product_desc;
        final TextView product_price;
        final TextView produce_buy_count;
        final TextView time_start;
        final TextView time_end;
        final View itemView;
        TextView showShade;

        public GridRecyHolder(View view) {
            super(view);
            itemView = view;
            preview_img = (ImageView) view.findViewById(R.id.iv_product_preview);
            product_desc = (TextView) view.findViewById(R.id.tv_product_desc);
            product_price = (TextView) view.findViewById(R.id.tv_product_price);
            product_old_price = (TextView) view.findViewById(R.id.tv_product_old_price);
            product_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线
            produce_buy_count = (TextView) view.findViewById(R.id.tv_selled_numb);
            time_start = (TextView) view.findViewById(R.id.tv_time_start);
            time_end = (TextView) view.findViewById(R.id.tv_time_end);
            showShade = (TextView) view.findViewById(R.id.tv_shade);
        }
    }
}
