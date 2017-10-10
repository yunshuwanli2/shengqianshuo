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
import yswl.priv.com.shengqianshopping.bean.ProductDetail;
import yswl.priv.com.shengqianshopping.util.AlibcUtil;

/**
 * Created by yunshuwanli on 17/10/1.
 */

public class AdvanceGridRecyclerFragmentAdapter extends RecyclerView.Adapter<AdvanceGridRecyclerFragmentAdapter.GridRecyHolder> {

    List<ProductDetail> mProductList;

    public List<ProductDetail> getmProductList() {
        return mProductList;
    }

    public void setmProductList(List<ProductDetail> mProductList) {
        this.mProductList = mProductList;
    }

    public AdvanceGridRecyclerFragmentAdapter() {
    }

    @Override
    public GridRecyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.advance_recycle_grid_item_view, parent, false);
        return new GridRecyHolder(view);
    }

    @Override
    public void onBindViewHolder(final GridRecyHolder holder, int position) {
        final ProductDetail detail = mProductList.get(position);
        Glide.with(holder.itemView.getContext()).load(detail.pictUrl).into(holder.preview_img);
        holder.product_desc.setText(detail.title);
        holder.product_price.setText(detail.getZkFinalPrice());
        holder.product_old_price.setText(detail.getReservePrice());
        holder.total.setText("总库存:" + detail.getVolume());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null && v != null && recyclerView != null) {
                    int position = recyclerView.getChildAdapterPosition(v);
                    onItemClickListener.onItemClick(recyclerView, v, position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return (mProductList != null && mProductList.size() > 0) ? mProductList.size() : 0;
    }


    class GridRecyHolder extends RecyclerView.ViewHolder {
        ImageView preview_img;
        TextView product_old_price;
        TextView product_desc;
        TextView product_price;
        TextView total;
        View itemView;

        public GridRecyHolder(View view) {
            super(view);
            itemView = view;
            preview_img = (ImageView) view.findViewById(R.id.iv_product_preview);
            product_old_price = (TextView) view.findViewById(R.id.tv_product_old_price);
            product_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线
            product_desc = (TextView) view.findViewById(R.id.tv_product_desc);
            product_price = (TextView) view.findViewById(R.id.tv_produce_price);
            total = (TextView) view.findViewById(R.id.tv_total);

        }
    }
    public interface OnItemClickListener {
        void onItemClick(RecyclerView parent, View view, int position);
    }

    private GridRecyclerFragmentAdapter.OnItemClickListener onItemClickListener;

    public GridRecyclerFragmentAdapter.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(GridRecyclerFragmentAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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
}
