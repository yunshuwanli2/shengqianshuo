package yswl.priv.com.shengqianshopping.fragment.adapter;

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

/**
 * Created by yunshuwanli on 17/10/1.
 */

public class GridRecyclerFragmentAdapter2 extends RecyclerView.Adapter<GridRecyclerFragmentAdapter2.GridRecyHolder> {

    List<ProductDetail> mProductList;

    public List<ProductDetail> getmProductList() {
        return mProductList;
    }


    private GridRecyclerFragmentAdapter.OnItemClickListener onItemClickListener;

    public GridRecyclerFragmentAdapter.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(GridRecyclerFragmentAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public void setmProductList(List<ProductDetail> mProductList) {
        this.mProductList = mProductList;
    }

    public GridRecyclerFragmentAdapter2() {
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
    public GridRecyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recyclerview_grid_item_view2, parent, false);
        return new GridRecyHolder(view);
    }

    @Override
    public void onBindViewHolder(GridRecyHolder holder, int position) {
        ProductDetail detail = mProductList.get(position);
        int top = position + 1;
        holder.top_numb.setText("TOP" + top);
        Glide.with(holder.itemView.getContext()).load(detail.pictUrl).into(holder.preview_img);
        holder.produce_buy_count.setText(detail.getVolume());
        holder.product_desc.setText(detail.title);
        holder.product_price.setText(detail.couponPrice);
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
        TextView top_numb;
        TextView product_desc;
        TextView product_price;
        TextView produce_buy_count;
        View itemView;

        public GridRecyHolder(View view) {
            super(view);
            itemView = view;
            preview_img = (ImageView) view.findViewById(R.id.iv_product_preview);
            top_numb = (TextView) view.findViewById(R.id.tv_top_numb);
            product_desc = (TextView) view.findViewById(R.id.tv_product_desc);
            product_price = (TextView) view.findViewById(R.id.tv_product_price);
            produce_buy_count = (TextView) view.findViewById(R.id.tv_number_people);

        }
    }
}
