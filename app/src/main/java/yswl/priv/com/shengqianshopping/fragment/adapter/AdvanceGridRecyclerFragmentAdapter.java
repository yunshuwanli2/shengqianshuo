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
    public void onBindViewHolder(GridRecyHolder holder, int position) {
        ProductDetail detail = mProductList.get(position);
        Glide.with(holder.itemView.getContext()).load(detail.pictUrl).into(holder.preview_img);
        holder.product_desc.setText(detail.title);
        holder.product_price.setText(detail.couponPrice);
        holder.product_old_price.setText(detail.reservePrice);
        holder.total.setText(detail.volume);
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

        public GridRecyHolder(View view) {
            super(view);
            preview_img = (ImageView) view.findViewById(R.id.iv_product_preview);
            product_old_price = (TextView) view.findViewById(R.id.tv_product_old_price);
            product_desc = (TextView) view.findViewById(R.id.tv_product_desc);
            product_price = (TextView) view.findViewById(R.id.tv_product_price);
            total = (TextView) view.findViewById(R.id.tv_total);

        }
    }
}
