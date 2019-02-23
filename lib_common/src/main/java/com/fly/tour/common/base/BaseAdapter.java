package com.fly.tour.common.base;

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
 * Author:      gxl<br>
 * Date:        2019/1/15<br>
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

    //绑定布局文件
    protected abstract int onBindLayout();

    //创建一个holder
    protected abstract VH onCreateHolder(View view);

    //绑定数据
    protected abstract void onBindData(VH holder, E e);

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        final E e = mList.get(position);
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(e);
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return mOnItemLongClickListener.onItemLongClick(e);
                }
            });
        }
        onBindData(holder, e);
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
        if (list != null && list.size() > 0) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }
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

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener<E> {
        void onItemClick(E e);
    }

    public interface OnItemLongClickListener<E> {
        boolean onItemLongClick(E e);
    }

}
