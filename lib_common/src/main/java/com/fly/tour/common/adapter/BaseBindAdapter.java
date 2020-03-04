package com.fly.tour.common.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Description: <BaseBindAdapter><br>
 * Author:      mxdl<br>
 * Date:        2019/7/4<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BaseBindAdapter<T, B extends ViewDataBinding> extends RecyclerView.Adapter {
    protected Context context;
    protected ObservableArrayList<T> items;
    protected OnItemClickListener mItemClickListener;
    protected OnItemLongClickListener mOnItemLongClickListener;
    public BaseBindAdapter(Context context, ObservableArrayList<T> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items != null && items.size() > 0 ? items.size() : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        B binding = DataBindingUtil.inflate(LayoutInflater.from(this.context), this.getLayoutItemId(viewType), parent, false);
       return new BaseBindingViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        B binding = DataBindingUtil.getBinding(holder.itemView);
        this.onBindItem(binding, this.items.get(position),position);
    }


    public class BaseBindingViewHolder extends RecyclerView.ViewHolder {
        public BaseBindingViewHolder(View itemView) {
            super(itemView);
        }
    }
    @LayoutRes
    protected abstract int getLayoutItemId(int viewType);

    protected abstract void onBindItem(B binding, T item,int position);

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener<E> {
        void onItemClick(E e, int position);
    }
    public interface OnItemLongClickListener<E> {
        boolean onItemLongClick(E e, int postion);
    }

}
