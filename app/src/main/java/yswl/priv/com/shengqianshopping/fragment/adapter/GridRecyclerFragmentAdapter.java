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
import yswl.priv.com.shengqianshopping.util.MTextViewUtil;

/**
 * Created by yunshuwanli on 17/10/1.
 */
public class GridRecyclerFragmentAdapter extends RecyclerView.Adapter<GridRecyclerFragmentAdapter.GridRecyHolder> {

    public interface OnItemClickListener {
        void onItemClick(RecyclerView parent, View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    List<ProductDetail> mProductList;
    protected boolean isScrolling = false;

    public void setScrolling(boolean scrolling) {
        isScrolling = scrolling;
    }

    public List<ProductDetail> getmProductList() {
        return mProductList;
    }

    public void setmProductList(List<ProductDetail> mProductList) {
        this.mProductList = mProductList;
    }

    public void addDate(List<ProductDetail> mProductList) {
        this.mProductList.addAll(mProductList);
    }

    public GridRecyclerFragmentAdapter() {
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
                inflate(R.layout.recyclerview_grid_item_view, parent, false);
        return new GridRecyHolder(view);
    }

    @Override
    public void onBindViewHolder(GridRecyHolder holder, final int position) {
        ProductDetail detail = mProductList.get(position);
        if (!isScrolling) {
            Glide.with(holder.itemView.getContext()).load(detail.pictUrl).into(holder.preview_img);
        }
        holder.produce_buy_count.setText(detail.getVolume());
        holder.product_desc.setText(detail.title);
        if ("0".equals(detail.userType))//0是淘宝 1是天猫
            MTextViewUtil.setCompoundDrawablesLeft(holder.product_desc, R.mipmap.ic_drawleft_tb);
        else
            MTextViewUtil.setCompoundDrawablesLeft(holder.product_desc, R.mipmap.ic_drawleft_tm);

        if (null == detail.couponNum || "0".equalsIgnoreCase(detail.couponNum)) {
            holder.coup_price.setVisibility(View.GONE);
            holder.product_price.setText(detail.getZkFinalPrice());

        } else {
            holder.coup_price.setVisibility(View.VISIBLE);
            holder.coup_price.setText(detail.getCouponNum());
            holder.product_price.setText(detail.getCouponPrice());
        }


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


    static class GridRecyHolder extends RecyclerView.ViewHolder {
        final View itemView;
        final ImageView preview_img;
        final TextView coup_price;
        final TextView product_desc;
        final TextView product_price;
        final TextView produce_buy_count;

        public GridRecyHolder(View view) {
            super(view);
            itemView = view;
            preview_img = (ImageView) view.findViewById(R.id.iv_product_preview);
            coup_price = (TextView) view.findViewById(R.id.tv_coup_price);
            product_desc = (TextView) view.findViewById(R.id.tv_product_desc);
            product_price = (TextView) view.findViewById(R.id.tv_product_price);
            produce_buy_count = (TextView) view.findViewById(R.id.tv_number_people);

        }
    }
}
