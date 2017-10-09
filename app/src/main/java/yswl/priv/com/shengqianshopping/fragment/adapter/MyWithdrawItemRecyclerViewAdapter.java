package yswl.priv.com.shengqianshopping.fragment.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.bean.OrderBean;
import yswl.priv.com.shengqianshopping.fragment.WithDrawItemFragment.OnListFragmentInteractionListener;

public class MyWithdrawItemRecyclerViewAdapter extends RecyclerView.Adapter<MyWithdrawItemRecyclerViewAdapter.ViewHolder> {

    public void setmValues(List<OrderBean> mValues) {
        this.mValues = mValues;
    }

    private List<OrderBean> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyWithdrawItemRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_withdraw, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mOrder.setText(mValues.get(position).order);
        holder.mAmount.setText(mValues.get(position).amount);
        holder.mStatus.setText(mValues.get(position).status);
        holder.mDateTime.setText(mValues.get(position).dateTime);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues != null && mValues.size() > 0 ? mValues.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mOrder;
        public final TextView mAmount;
        public final TextView mStatus;
        public final TextView mDateTime;
        public OrderBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mOrder = (TextView) view.findViewById(R.id.tv_order);
            mAmount = (TextView) view.findViewById(R.id.tv_amount);
            mStatus = (TextView) view.findViewById(R.id.tv_status);
            mDateTime = (TextView) view.findViewById(R.id.tv_dateTime);
        }

    }
}
