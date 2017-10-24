package yswl.priv.com.shengqianshopping.fragment.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.bean.ProductDetail;
import yswl.priv.com.shengqianshopping.util.MTextViewUtil;

/**
 * Created by Administrator on 2017/10/22.
 */

public class HomeFragmentAdapter extends RecyclerView.Adapter<HomeFragmentAdapter.GridRecyHolder> {

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

    List<ProductDetail> mProductList = new ArrayList<>();
    private boolean isScrolling = false;

    public void setScrolling(boolean scrolling) {
        isScrolling = scrolling;
    }

    public List<ProductDetail> getmProductList() {
        return mProductList;
    }

    public void setmProductList(List<ProductDetail> productList) {
        this.mProductList.clear();
        this.mProductList.addAll(productList);
//        notifyItemInserted(getItemCount());
    }
//
//    public void clearDate() {
//        if (mProductList != null)
//            this.mProductList.clear();
//    }
//
//    public void addDate(List<ProductDetail> productList) {
//        this.mProductList.addAll(productList);
//    }

    public HomeFragmentAdapter() {

    }

    RecyclerView recyclerView;
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private View mHeaderView;

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public void onViewAttachedToWindow(HomeFragmentAdapter.GridRecyHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(holder.getLayoutPosition() == 0);
        }
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    @Override
    public HomeFragmentAdapter.GridRecyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) return new Holder(mHeaderView);
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recyclerview_grid_item_view, parent, false);
        return new HomeFragmentAdapter.GridRecyHolder(view);
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public void onBindViewHolder(HomeFragmentAdapter.GridRecyHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;
        final int pos = getRealPosition(holder);
        ProductDetail detail = mProductList.get(pos);
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
        return mHeaderView == null ? mProductList.size() : mProductList.size() + 1;
//        return (mProductList != null && mProductList.size() > 0) ? mProductList.size() : 0;
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

    class Holder extends GridRecyHolder {

        public Holder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) return;

        }
    }
}
