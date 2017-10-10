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
import yswl.priv.com.shengqianshopping.bean.CategoryBean;
import yswl.priv.com.shengqianshopping.bean.ProductDetail;

/**
 * Created by yunshuwanli on 17/10/1.
 */

public class GridRecyclerAdapter extends RecyclerView.Adapter<GridRecyclerAdapter.GridRecyHolder> {

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

    List<CategoryBean> mCategorys;
    RecyclerView recyclerView;
    public List<CategoryBean> getmProductList() {
        return mCategorys;
    }

    public void setCategoryList(List<CategoryBean> categorys) {
        this.mCategorys = categorys;
    }

    public GridRecyclerAdapter() {
    }


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
                inflate(R.layout.grid_item_text, parent, false);
        return new GridRecyHolder(view);
    }

    @Override
    public void onBindViewHolder(GridRecyHolder holder, final int position) {
        CategoryBean detail = mCategorys.get(position);
        holder.itemText.setText(detail.title);
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
        return (mCategorys != null && mCategorys.size() > 0) ? mCategorys.size() : 0;
    }


    class GridRecyHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView itemText;

        public GridRecyHolder(View view) {
            super(view);
            itemView = view;
            itemText = (TextView) view.findViewById(R.id.home_menu_item);

        }
    }
}
