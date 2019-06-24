package com.fly.tour.common.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Description: <BaseListViewAdapter><br>
 * Author:      mxdl<br>
 * Date:        2018/6/11<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BaseListViewAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> list;
    public BaseListViewAdapter(Context context, List<T> mDatas) {
        this.mContext = context;
        this.list = mDatas;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list != null && list.size() > 0) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return inflateView(position, convertView, parent);
    }
    public abstract View inflateView(int position, View convertView, ViewGroup parent);
}
