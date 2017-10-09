package yswl.priv.com.shengqianshopping.fragment.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import yswl.priv.com.shengqianshopping.R;
import yswl.priv.com.shengqianshopping.bean.BalanceDetailItemBean;
import yswl.priv.com.shengqianshopping.fragment.BalanceDetailItemFragment.OnListFragmentInteractionListener;

import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    public void setmValues(List<BalanceDetailItemBean> mValues) {
        this.mValues = mValues;
    }

    private List<BalanceDetailItemBean> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<BalanceDetailItemBean> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public MyItemRecyclerViewAdapter(OnListFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitle.setText(mValues.get(position).title);
        holder.mContentView.setText(mValues.get(position).content);
        holder.mTime.setText(mValues.get(position).title);

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
        public final TextView mTitle;
        public final TextView mContentView;
        public final TextView mTime;
        public BalanceDetailItemBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = (TextView) view.findViewById(R.id.tv_title);
            mContentView = (TextView) view.findViewById(R.id.tv_content);
            mTime = (TextView) view.findViewById(R.id.tv_time);
        }

    }
}
