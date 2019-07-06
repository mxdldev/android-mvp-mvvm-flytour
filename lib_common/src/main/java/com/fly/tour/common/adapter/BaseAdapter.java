package com.fly.tour.common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: <BaseAdapter><br>
 * Author:      mxdl<br>
 * Date:        2018/1/15<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BaseAdapter<E, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected Context mContext;
    protected List<E> mList;
    protected OnItemClickListener mItemClickListener;
    protected OnItemLongClickListener mOnItemLongClickListener;

    public BaseAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<E>();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutid = onBindLayout();
        View view = LayoutInflater.from(mContext).inflate(layoutid, parent, false);
        return onCreateHolder(view);
    }
    protected abstract int onBindLayout();
    protected abstract VH onCreateHolder(View view);
    protected abstract void onBindData(VH holder, E e,int positon);

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        final E e = mList.get(position);
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(e,position);
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return mOnItemLongClickListener.onItemLongClick(e,position);
                }
            });
        }
        onBindData(holder, e,position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addAll(List<E> list) {
        if (list != null && list.size() > 0) {
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }
    public void refresh(List<E> list){
        mList.clear();
        if (list != null && list.size() > 0) {
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }
    public void remove(int positon) {
        mList.remove(positon);
        notifyDataSetChanged();
    }
    public void remove(E e) {
        mList.remove(e);
        notifyDataSetChanged();
    }
    public void add(E e) {
        mList.add(e);
        notifyDataSetChanged();
    }
    public void addLast(E e) {
        add(e);
    }
    public void addFirst(E e) {
        mList.add(0,e);
        notifyDataSetChanged();
    }
    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    public List<E> getDataList() {
        return mList;
    }

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

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public List<E> getListData(){
        return mList;
    }
}
