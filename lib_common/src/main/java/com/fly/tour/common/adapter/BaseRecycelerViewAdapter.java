package com.fly.tour.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: <BaseRecycelerViewAdapter><br>
 * Author: mxdl<br>
 * Date: 2018/6/11<br>
 * Version: V1.0.0<br>
 * Update: <br>
 */
public abstract class BaseRecycelerViewAdapter<E, VH extends RecyclerView.ViewHolder>
    extends RecyclerView.Adapter<VH> {

  protected Context mContext;
  protected List<E> mList;
  protected OnItemClickListener mOnItemClickListener;
  protected OnLongItemClickListener mOnLongItemClickListener;

  public abstract int onBindLayout();

  public abstract VH onCreateViewHolder(View view);

  public abstract void convert(VH holder, int position);

  public BaseRecycelerViewAdapter(Context context) {
    this.mContext = context;
    this.mList = new ArrayList<E>();
  }

  @Override
  public VH onCreateViewHolder(ViewGroup parent, int viewType) {
    int layout = onBindLayout();
    View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
    return onCreateViewHolder(view);
  }

  @Override
  public void onBindViewHolder(VH holder, final int position) {
    if (mOnItemClickListener != null) {
      holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          mOnItemClickListener.onItemClick(position);
        }
      });
    }

    if (mOnLongItemClickListener != null) {
      holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
          return mOnLongItemClickListener.onLongItemClick(position);
        }
      });
    }

    convert(holder, position);
  }

  public E remove(int position) {
    E remove = mList.remove(position);
    notifyDataSetChanged();
    return remove;
  }

  public void clear() {
    mList.clear();
    notifyDataSetChanged();
  }

  public void addAll(List<E> list) {
    mList.addAll(list);
    notifyDataSetChanged();
  }

  public List<E> getList() {
    return mList;
  }

  public E get(int position) {
    return mList.get(position);
  }


  @Override
  public int getItemCount() {
    return mList.size();
  }

  public void setOnItemClickListener(OnItemClickListener listener) {
    this.mOnItemClickListener = listener;
  }

  public void setOnLongItemClickListener(OnLongItemClickListener listener) {
    this.mOnLongItemClickListener = listener;
  }


  public interface OnItemClickListener {
    void onItemClick(int position);
  }

  public interface OnLongItemClickListener {
    boolean onLongItemClick(int position);
  }
}
