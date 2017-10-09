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

/**
 * Created by yunshuwanli on 17/10/1.
 */

public class ListRecyclerFragmentAdapter extends RecyclerView.Adapter<ListRecyclerFragmentAdapter.GridRecyHolder> {

    List<ProductDetail> mProductList;

    public List<ProductDetail> getmProductList() {
        return mProductList;
    }

    public void setmProductList(List<ProductDetail> mProductList) {
        this.mProductList = mProductList;
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

    @Override
    public GridRecyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recyclerview_list_item_view, parent, false);
        return new GridRecyHolder(view);
    }

    @Override
    public void onBindViewHolder(GridRecyHolder holder, int position) {
        ProductDetail detail = mProductList.get(position);
        Glide.with(holder.itemView.getContext()).load(detail.pictUrl).into(holder.preview_img);
        holder.produce_buy_count.setText("已抢购:" + detail.getVolume());
        holder.product_desc.setText(detail.title);
        holder.product_price.setText("￥:" + detail.zkFinalPrice);
        holder.product_old_price.setText("￥:" + detail.reservePrice);
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
        TextView produce_buy_count;
        TextView time_start;
        TextView time_end;


        public GridRecyHolder(View view) {
            super(view);
            preview_img = (ImageView) view.findViewById(R.id.iv_product_preview);
            product_desc = (TextView) view.findViewById(R.id.tv_product_desc);
            product_price = (TextView) view.findViewById(R.id.tv_product_price);
            product_old_price = (TextView) view.findViewById(R.id.tv_product_old_price);
            product_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线
            produce_buy_count = (TextView) view.findViewById(R.id.tv_selled_numb);
            time_start = (TextView) view.findViewById(R.id.tv_time_start);
            time_end = (TextView) view.findViewById(R.id.tv_time_end);


        }
    }
}
